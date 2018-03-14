package net.lemonsoft.LemonDataGrab.MainControlMachine.Core;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Dao.LDTask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LETask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENTaskState;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSOnlineSession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSTask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUDatabase;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LULog;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUTime;
import org.apache.mina.util.ConcurrentHashSet;
import org.w3c.dom.Element;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 核心类 - 任务管理器
 * Created by LiuRi on 6/28/16.
 */
public class LCTaskManager {

    // 查询发布时间小于现在系统时间 + 向后延后时间、发布时间大于现在系统时间 - 向前兼容的时间的所有数据,并且结果按照是否置顶提权倒序、下次发布时间的正序排列
    private static final String QUERY_ALL_NOT_DISTRIBUTE_TASKS_SQL = "SELECT" +
            "  task_id," +
            "  task_name," +
            "  task_fingerprint," +
            "  task_description," +
            "  task_createTime," +
            "  task_publishTime," +
            "  task_distributeRepeatInterval," +
            "  task_distributeRepeatCount," +
            "  task_distributedNumber," +
            "  task_executeScript," +
            "  task_state," +
            "  task_expired," +
            "  task_level," +
            "  task_session" +
            " FROM ldg_task" +
            " WHERE task_expired > %d AND" +    // 当前时间
            "      task_state = " + LENTaskState.TASK_STATE_WAIT_FOR_DISTRIBUTE.getStateCode() + " AND" +
            "      task_publishTime < %d + %d AND" +    // 当前时间  ,  向后延后的时间间隔
            "      task_publishTime > %d - %d" +    // 当前时间  ,  向前兼容的时间间隔
            " ORDER BY task_level DESC, task_publishTime ASC";

    private static final String SET_REFRESH_TIME_TO_READY_TO_DISTRIBUTE_SQL =
            "UPDATE ldg_task SET task_state = " + LENTaskState.TASK_STATE_READY_TO_DISTRIBUTE.getStateCode() +
                    " WHERE task_expired > %d AND" +    // 当前时间
                    "      task_state = " + LENTaskState.TASK_STATE_WAIT_FOR_DISTRIBUTE.getStateCode() + " AND" +
                    "      task_publishTime < %d + %d AND" +    // 当前时间  ,  向后延后的时间间隔
                    "      task_publishTime > %d - %d";    // 当前时间  ,  向前兼容的时间间隔

    // 设置当前时间已经超时且任务代码为1.待分发、2.被分发 的任务为采集超时状态
    private static final String SET_EXPIRED_TASKS_STATE_SQL = "UPDATE ldg_task SET task_state = " + LENTaskState.TASK_STATE_GRAB_RESULT_TIMEOUT.getStateCode() +
            " WHERE ? > ldg_task.task_expired AND task_state < " + LENTaskState.TASK_STATE_GRAB_RESULT_SUCCESS.getStateCode();

    // 设置当前时间没有超时且任务状态为1.待分发、2.被分发 的任务为待分发0 的状态
    private static final String RESET_TASKS_STATE_WITH_NO_EXPIRED = "UPDATE ldg_task " +
            "SET ldg_task.task_state = " + LENTaskState.TASK_STATE_WAIT_FOR_DISTRIBUTE.getStateCode() +
            " WHERE ldg_task.task_expired > ?" +
            "      AND ldg_task.task_state < " + LENTaskState.TASK_STATE_GRAB_RESULT_SUCCESS.getStateCode();

    private static final String SET_TASK_STATE_ALREADY_DISTRIBUTED_SQL =// 设置当前已经分发的任务的状态为已分发(2)
            "UPDATE ldg_task SET task_state = " + LENTaskState.TASK_STATE_ALREADY_DISTRIBUTED.getStateCode() +
                    " WHERE task_id IN (%s)";

    private static final String SET_TASK_STATE_READY_TO_DISTRIBUTE_BY_TID_SQL =// 设置指定的任务为准备分发状态
            "UPDATE ldg_task SET task_state = " + LENTaskState.TASK_STATE_READY_TO_DISTRIBUTE.getStateCode() + " WHERE task_id = %d";// 任务的TID

    private static final String SET_TASK_STATE_GRAB_RESULT_TIMEOUT_BY_TID_SQL =// 设置指定的任务为采集超时状态
            "UPDATE ldg_task SET task_state = " + LENTaskState.TASK_STATE_GRAB_RESULT_TIMEOUT.getStateCode() + " WHERE task_id = %d";// 任务的TID

    private static final String SET_TASK_STATE_GRAB_WAIT_BY_TID_SQL =// 设置指定的任务为待分发状态
            "UPDATE ldg_task SET task_state = " + LENTaskState.TASK_STATE_WAIT_FOR_DISTRIBUTE.getStateCode() + " WHERE task_id = %d";// 任务的TID

    private Integer TASK_REFRESH_TIME_FRONT;// 任务扫描时,允许向前兼容的时间间隔
    private Integer TASK_REFRESH_TIME_AFTER;// 任务扫描时,允许向后延后的时间间隔
    private Integer TASK_REFRESH_TIME_INTERVAL;// 任务刷新扫描的时间间隔
    private Integer TASK_DISTRIBUTE_TIME_INTERVAL;// 任务分发扫描时间间隔
    private Timer taskRefreshTimer;// 任务刷新扫描计时器
    private Timer taskDistributeTimer;// 任务分发扫描计时器
    private Map<String, LETask> taskPool;// 任务池,所有的任务都要放置到该任务池中
    private Map<String, List<String>> tasksTimeLinePool;// 任务时间线池
    private List<String> rightAwayTaskPool;// 需要立即发送的任务的任务池,存放置顶提权的任务以及未在指定时间发送且没有过期的任务
    private Set<String> taskReadyToDistributePool;// 待分发出去的任务记录,该集合中记录了所有待分发任务,分发出去后,将该任务转移到已分发任务集合。
    public Set<String> taskAlreadyDistributedPool;// 已经分发出去的任务记录,当数据采集终端报告采集成功时,从该集合中移除

    private static LCTaskManager taskManager;

    public static LCTaskManager sharedInstance() throws ClassNotFoundException, NoSuchMethodException {
        if (taskManager == null)
            taskManager = new LCTaskManager();
        return taskManager;
    }

    private LCTaskManager() {
        Element element = (Element) LCConfig.sharedInstance().getDocument().getElementsByTagName("LK-Task").item(0);
        TASK_REFRESH_TIME_FRONT = Integer.valueOf(element.getElementsByTagName("taskRefreshTimeFront").item(0).getTextContent());
        TASK_REFRESH_TIME_AFTER = Integer.valueOf(element.getElementsByTagName("taskRefreshTimeAfter").item(0).getTextContent());
        TASK_REFRESH_TIME_INTERVAL = Integer.valueOf(element.getElementsByTagName("taskRefreshTimeInterval").item(0).getTextContent());
        TASK_DISTRIBUTE_TIME_INTERVAL = Integer.valueOf(element.getElementsByTagName("taskDistributeTimeInterval").item(0).getTextContent());
        LULog.info(String.format("配置读取完毕, REFRESH_TIME_FRONT: %d ," +
                        " REFRESH_TIME_AFTER: %d , " +
                        "REFRESH_TIME_INTERVAL : %d , " +
                        "DISTRIBUTE_TIME_INTERVAL: %d",
                TASK_REFRESH_TIME_FRONT, TASK_REFRESH_TIME_AFTER,
                TASK_REFRESH_TIME_INTERVAL, TASK_DISTRIBUTE_TIME_INTERVAL));
        taskRefreshTimer = new Timer("taskRefreshTimer");
        taskDistributeTimer = new Timer("timeLineManageTimer");
        taskPool = new ConcurrentHashMap<>();
        taskReadyToDistributePool = new ConcurrentHashSet<>();
        taskAlreadyDistributedPool = new ConcurrentHashSet<>();
        tasksTimeLinePool = new ConcurrentHashMap<>();
        rightAwayTaskPool = new CopyOnWriteArrayList<>();
    }

    public void start() {
        taskRefreshTimer.schedule(new GenerateTasksFromDB(), 0, TASK_REFRESH_TIME_INTERVAL * 1000);
        taskDistributeTimer.schedule(new TaskTimeLineManagerHandler(), 0, TASK_DISTRIBUTE_TIME_INTERVAL * 1000);
    }

    public void end() {
        taskRefreshTimer.cancel();
        taskDistributeTimer.cancel();
        for (String taskId : taskAlreadyDistributedPool) {
            LETask task = taskPool.get(taskId);
            String sql = String.format(SET_TASK_STATE_GRAB_WAIT_BY_TID_SQL, task.getTid());
            LUDatabase.update(sql);
            LULog.info("[恢复任务状态为待分发 , SQL :]" + sql);
        }
    }

    /**
     * 把已经分发出去的任务重置回待分发状态
     * 通常为采集任务的终端无故断开的时候调用此函数
     *
     * @param task 要重置的任务
     */
    public void resetToReadyToDistribute(LETask task) {
        if (taskAlreadyDistributedPool.contains(task.getFingerprint())) {
            taskAlreadyDistributedPool.remove(task.getFingerprint());
            if (LUTime.getUnixTimeStamp() < task.getExpired()) {// 这个任务当前还没有超时
                String sql = String.format(SET_TASK_STATE_READY_TO_DISTRIBUTE_BY_TID_SQL, task.getTid());
                LUDatabase.update(sql);
                rightAwayTaskPool.add(task.getFingerprint());
                LULog.info("一个任务被重新置为准备分发状态,SQL:" + sql);
            }
        }
    }

    /**
     * 时间线管理类
     */
    private class TaskTimeLineManagerHandler extends TimerTask {

        @Override
        public void run() {
            try {
                Long currentTime = LUTime.getUnixTimeStamp();
                distributeTask(currentTime);
            } catch (Exception e) {
                e.printStackTrace(System.err);
                LULog.error("时间线管理器运行时发生一个异常：" + e.getStackTrace());
            }
        }
    }

    /**
     * 从数据库中获取任务信息后生成任务对象
     */
    private class GenerateTasksFromDB extends TimerTask {

        @Override
        public void run() {
            try {
                Long currentTime = LUTime.getUnixTimeStamp();
                setDBExpiredTasksState(currentTime);// STEP 1
                refreshTimeInfoFromDB(currentTime);// STEP 2 - 3 - 4
                distributeTask(currentTime);// STEP 5 - 6 - 7 - 8 - 9 - 10
            } catch (Exception e) {
                e.printStackTrace();
                LULog.error("任务刷新机制运行时发生异常：" + e.getStackTrace());
            }
        }
    }

    /**
     * 将数据库中当前时间X秒之前的所有待分发的任务(0)全部将其任务状态置为:任务结果采集超时(4)
     *
     * @param time 当前时间的UNIX时间戳
     * @STEP 1
     */
    private void setDBExpiredTasksState(long time) {
        LUDatabase.update(SET_EXPIRED_TASKS_STATE_SQL, time);
    }

    /**
     * 从数据库中查询当前时间的X秒前到现在的,和当前时间Y秒后之内的所有待分发(0)任务
     * 把加入到待分发池中的所有任务的状态,更改为: 准备分发(1)
     * 把查询到的所有待分发任务插入到待分发池中(List)
     *
     * @param time 当前时间的UNIX时间戳
     * @STEP 2 - 3 - 4
     */
    private void refreshTimeInfoFromDB(long time) {
        List<Map<String, Object>> queryResult = LUDatabase.query(String.format(QUERY_ALL_NOT_DISTRIBUTE_TASKS_SQL, time, time, TASK_REFRESH_TIME_AFTER, time, TASK_REFRESH_TIME_FRONT));
        for (Map<String, Object> result : queryResult) {
            result.put("task_executeScript", LSTask.getLocalScript((String) result.get("task_fingerprint")));
        }
        LULog.info("[刷新读取任务函数被触发] 本次共从数据库中查询出" + queryResult.size() + "条待分发任务");
        LUDatabase.update(String.format(SET_REFRESH_TIME_TO_READY_TO_DISTRIBUTE_SQL, time, time, TASK_REFRESH_TIME_AFTER, time, TASK_REFRESH_TIME_FRONT));
        for (Map<String, Object> resultItem : queryResult) {
            try {
                LETask task = new LDTask().initByResultMap(resultItem);// 将map转成对象
                taskPool.put(task.getFingerprint(), task);
                if ((task.getPublishTime() <= time) || task.getLevel() > 0) {// 发布时间小于等于当前时间
                    // 时间过期或即将过期,或者任务要求指定提权
                    rightAwayTaskPool.add(task.getFingerprint());// 放到立即发送池中
                } else {
                    // 普通正常的任务,放到时间线池中和待分发池中
                    taskReadyToDistributePool.add(task.getFingerprint());
                    ArrayList<String> tasks = // 获取时间线中已经放入的任务
                            (ArrayList<String>) tasksTimeLinePool.getOrDefault(String.valueOf(task.getPublishTime()), new ArrayList<String>());
                    if (!tasks.contains(task.getFingerprint()))
                        tasks.add(task.getFingerprint());// 向时间点的List中添加一个任务
                    tasksTimeLinePool.put(String.valueOf(task.getPublishTime()), tasks);// 把任务List放到时间线中
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断当前是否存在 在线的数据采集终端,如果存在,那么继续,如果不存在,那么分发处理逻辑终止
     * 对当前的所有在线数据采集终端,按照当前采集任务数进行数量从小到大的排序
     * 对排序后的数据采集终端进行依次分发任务
     * 把分发出去的任务插入到已分发池中
     * 将刚刚插入到已分发池中的所有任务的任务状态置为: 已经分发到数据采集终端(2)
     * 将已分发池和待分发池以及立即分发池中的所有任务进行遍历,若发现任务的执行时间已经超时,那么将其从池中移除,并且将任务状态置为: 任务结果超时(4)
     *
     * @STEP 5 - 6 - 7 - 8 - 9 - 10
     */
    private synchronized void distributeTask(long time) throws Exception {
        LSOnlineSession lsOnlineSession = LSOnlineSession.sharedInstance();
        LULog.info("[分发函数被触发] 当前在线采集终端数量: " + lsOnlineSession.countAllDataGrabTerminal() +
                " ,当前待分发的任务数量:" + (rightAwayTaskPool.size() + taskReadyToDistributePool.size()) +
                " ,当前已分发池中的任务数量:" + taskAlreadyDistributedPool.size() +
                " ,当前时间线共有待分发时间点数量:" + tasksTimeLinePool.size());
        StringBuilder tasksStr = new StringBuilder();
        if (lsOnlineSession.countAllDataGrabTerminal() > 0) {// STEP 5
            // 存在数据采集终端在线
            for (String taskId : rightAwayTaskPool) {
                LETask task = this.taskPool.get(taskId);
                rightAwayTaskPool.remove(taskId);
                if (time < task.getExpired()) {
                    // 任务还没有过期，那么立即分发
                    lsOnlineSession.sendTask(task);// STEP 6 - 7
                    LULog.info("提权任务已分发, TID:" + task.getTid());
                    tasksStr.append("," + task.getTid());
                    taskAlreadyDistributedPool.add(taskId);// STEP 8
                } else {
                    // 任务已经过期，设置过期状态
                    setTaskToGrabTimeout(task);
                }
            }
            if (tasksTimeLinePool.getOrDefault(time + "", null) != null) {
                LULog.info("当前时间:" + time + " 的时间线中共有" + tasksTimeLinePool.get(time + "").size() + "条任务待分发");
                for (String taskId : tasksTimeLinePool.get(time + "")) {
                    LETask task = this.taskPool.get(taskId);
                    taskReadyToDistributePool.remove(taskId);
                    lsOnlineSession.sendTask(task);// STEP 6 - 7
                    LULog.info("普通任务已分发, TID:" + task.getTid());
                    tasksStr.append("," + task.getTid());
                    taskAlreadyDistributedPool.add(taskId);// STEP 8
                }
                tasksTimeLinePool.remove(time + "");// 移除时间线池中的当前时间线
            }
            if (tasksStr.length() > 0) {// 如果任务字符串的长度大于0，说明存在状态要改变的任务
                tasksStr.replace(0, 1, "");// 把拼装字符串中的第一个,删除
                String sql = String.format(SET_TASK_STATE_ALREADY_DISTRIBUTED_SQL, tasksStr.toString());
                LULog.info("改变任务状态为已分发的SQL生成:" + sql);
                LUDatabase.update(sql);// STEP 9
            }
        }
        // 对已分发池中的所有任务进行过期检查
        for (String taskId : taskAlreadyDistributedPool) {// STEP 10
            LETask task = taskPool.get(taskId);
            if (time >= task.getExpired()) {
                // 当前已分发任务已经超时过期
                if (tasksTimeLinePool.get(task.getPublishTime() + "") != null)
                    tasksTimeLinePool.get(task.getPublishTime() + "").remove(taskId);// 从时间线中移除待分发的任务
                setTaskToGrabTimeout(task);
            }
        }
        // 对待分发池中的所有任务进行过期检查
        for (String taskId : taskReadyToDistributePool) {
            LETask task = taskPool.get(taskId);
            if (time >= task.getExpired()) {
                // 当前待分发任务已经超时过期没有分发出去
                setTaskToGrabTimeout(task);
            } else if (time > task.getPublishTime()) {
                // 任务没有过期但是已经超过正常分发时间，那么把他放到立即分发池中
                changeTaskToRightAwayState(task);
            }
        }
        // 对立即分发池中的所有任务进行过期检查
        for (String taskId : rightAwayTaskPool) {
            LETask task = taskPool.get(taskId);
            if (time >= task.getExpired()) {
                // 当前立即分发任务已经超时过期没有分发出去
                setTaskToGrabTimeout(task);
            }
        }
    }

    /**
     * 设置任务为立即分发状态，通常任务已经超过正常分发时间时候调用
     *
     * @param task 要进行转成立即分发状态的任务对象
     */
    public void changeTaskToRightAwayState(LETask task) {
        taskReadyToDistributePool.remove(task.getFingerprint());// 从待分发池中移除
        tasksTimeLinePool.remove(task.getPublishTime() + "");// 从时间线移除时间点
        rightAwayTaskPool.add(task.getFingerprint());// 加到立即分发池中
    }

    /**
     * 设置指定的任务为采集超时状态
     *
     * @param task 要设置超时状态的任务
     */
    private void setTaskToGrabTimeout(LETask task) {
        String sql = String.format(SET_TASK_STATE_GRAB_RESULT_TIMEOUT_BY_TID_SQL, task.getTid());
        LULog.info("一个任务即将被改变为采集超时,生成的SQL:" + sql);
        LUDatabase.update(sql);
        if (tasksTimeLinePool.get(task.getPublishTime() + "") != null) {
            tasksTimeLinePool.get(task.getPublishTime() + "").remove(task.getFingerprint());
            tasksTimeLinePool.remove(task.getPublishTime() + "");
        }
        taskAlreadyDistributedPool.remove(task.getFingerprint());
        taskReadyToDistributePool.remove(task.getFingerprint());
        rightAwayTaskPool.remove(task.getFingerprint());
        taskPool.remove(task.getFingerprint());
    }

    /**
     * 重置数据库中的任务的状态
     * 把准备分发任务和已分发任务中所有未过期的任务设置成状态0
     * 把准备待分发和已分发的任务设置成采集超时
     * <p>
     * 通常该方法在系统启动和结束的时候调用
     */
    public void resetDBTaskState() {
        Long time = LUTime.getUnixTimeStamp();
        LUDatabase.update(RESET_TASKS_STATE_WITH_NO_EXPIRED, time);
        LUDatabase.update(SET_EXPIRED_TASKS_STATE_SQL, time);
    }

}
