package net.lemonsoft.LemonDataGrab.MainControlMachine.Api;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANApi;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LESession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LETask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENResponseError;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENTaskState;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Handler.LHMina;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMData;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMInfo;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMOnlineSession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSMessageExchange;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSOnlineSession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSTask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUTask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUTime;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

/**
 * API - 任务
 * Created by LiuRi on 6/4/16.
 */
public class LATask extends LA<LSTask> {

    @LANApi(URL = "/Task/AddTask",
            parameters = {"sessionFingerprint", "name", "description", "publishTime", "distributeRepeatInterval",
                    "distributeRepeatCount", "executeScript", "expired", "level"},
            parameterIntros = {"执行添加任务的管理员的会话指纹", "任务的名称", "任务的描述", "任务的发布时间,当发布时间小于服务器的当前时间时,会将该值与服务器的当前时间保持一致",
                    "任务分发的时间周期间隔", "任务的重复分发次数", "任务的执行脚本", "任务过期的时间段时长", "任务的优先级,默认为0,数值越大,优先级越高"},
            description = "添加采集任务的接口",
            errors = {1002, 1005})
    public Object addTask(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            try {
                String fingerprint = UUID.randomUUID().toString();
                Long createTime = LUTime.getUnixTimeStamp();
                for (int i = 0; i <= Integer.valueOf((String) parameters.get("distributeRepeatCount")); i++) {
                    LETask leTask = LUTask.getTaskByParametersMap(parameters, createTime, fingerprint, (long) i);
                    if (!this.getService().add(leTask)) {
                        // 插入数据库失败了
                        return LENResponseError.REQUEST_SYSTEM_EXCEPTION;
                    }
                }
                return new LMInfo("任务已经成功添加!");// 添加成功了
            } catch (Exception e) {
                e.printStackTrace();
                return LENResponseError.REQUEST_SYSTEM_EXCEPTION;
            }
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

    @LANApi(URL = "/Task/GetTaskList",
            parameters = {"sessionFingerprint", "startDate", "endDate", "startIndex", "count"},
            parameterIntros = {"获取任务列表的管理员的会话指纹", "获取任务列表的起始日期", "获取任务列表的结束日期", "获取整体数据的列表的起始索引", "本次获取的数据的数量"},
            description = "分页获取日期范围内的任务列表",
            errors = {1005})
    public Object getTaskList(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            return new LMData(this.getService().getTaskListByDate((String) parameters.get("startDate"),
                    (String) parameters.get("endDate"), (String) parameters.get("startIndex"),
                    (String) parameters.get("count")));
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }

    }

    @LANApi(URL = "/Task/CountTask",
            parameters = {"sessionFingerprint", "startDate", "endDate"},
            parameterIntros = {"获取任务数量的管理员的会话指纹", "获取任务列表的起始日期", "获取任务列表的结束日期"},
            description = "获取日期范围内的任务数量",
            errors = {1005})
    public Object countTask(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            return new LMData(this.getService().countTaskByDate((String) parameters.get("startDate"), (String) parameters.get("endDate")));
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

    @LANApi(URL = "/Task/GetInfo",
            parameters = {"sessionFingerprint", "taskFingerprint"},
            parameterIntros = {"获取任务数量的管理员的会话指纹", "要获取的任务的指纹"},
            description = "获取指定任务ID的任务详情信息，通常该接口用于管理员端查看任务详情",
            errors = {1005})
    public Object getInfo(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            LETask task = this.getService().getTaskByFingerprint((String) parameters.get("taskFingerprint"));
            HashMap<String, Object> result = new HashMap<>();
            result.put("task", task);
            // 未放到待分发池中，且没有到发布时间，才允许修改
            result.put("canEdit", ((long) task.getState() == LENTaskState.TASK_STATE_WAIT_FOR_DISTRIBUTE.getStateCode())
                    && task.getPublishTime() > LUTime.getUnixTimeStamp());
            return new LMData(result);
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

    @LANApi(URL = "/Task/UpdateTask",
            parameters = {"sessionFingerprint", "taskFingerprint", "name", "description", "publishTime",
                    "executeScript", "expired", "level"},
            parameterIntros = {"执行添加任务的管理员的会话指纹", "任务的指纹", "任务的名称", "任务的描述",
                    "任务的发布时间,当发布时间小于服务器的当前时间时,会将该值与服务器的当前时间保持一致",
                    "任务的执行脚本", "任务过期的时间UNIX时间戳", "任务的优先级,数值越大,优先级越高"},
            description = "更新采集任务信息的接口",
            errors = {1005})
    public Object updateTask(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            Long currentTime = LUTime.getUnixTimeStamp();
            LETask task = this.getService().getTaskByFingerprint((String) parameters.get("taskFingerprint"));
            if ((long) task.getState() != LENTaskState.TASK_STATE_WAIT_FOR_DISTRIBUTE.getStateCode()
                    || task.getPublishTime() < LUTime.getUnixTimeStamp())
                // 此时这个任务已经被放到待分发池中或者已经被发布出去了，那么，不允许再修改这个任务啦
                return new LMInfo("对不起，虽然更新任务请求已收到，但是无法进行修改，因为您要修改的任务已经被放置到分发队列中。");
            task.setName((String) parameters.get("name"));
            task.setDescription((String) parameters.get("description"));
            task.setPublishTime(Long.valueOf((String) parameters.get("publishTime")));
            task.setExecuteScript((String) parameters.get("executeScript"));
            task.setExpired(Long.valueOf((String) parameters.get("expired")));
            if (currentTime > task.getPublishTime()) {// 如果当前的时间已经超过了要修改的UNIX时间，即已经过时
                task.setPublishTime(currentTime);// 那么设置为现在的时间为发布时间
                task.setExpired(currentTime + 60);// 发布时间+60s作为过期时间
            }
            task.setLevel(Long.valueOf((String) parameters.get("level")));
            if (!this.getService().update(task)) {
                // 更新任务信息的时候修改数据库失败了
                return LENResponseError.REQUEST_SYSTEM_EXCEPTION;
            }
            return new LMInfo("任务信息修改成功!");
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

    @LANApi(URL = "/Task/DeleteTask",
            parameters = {"sessionFingerprint", "taskFingerprint"},
            parameterIntros = {"获取任务数量的管理员的会话指纹", "要删除的任务的指纹"},
            description = "删除指定指纹对应的任务",
            errors = {1005})
    public Object deleteTask(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            if (!this.getService().deleteByPrimaryKey("" + this.getService().getTaskByFingerprint((String) parameters.get("taskFingerprint")).getTid())) {
                // 从数据库中删除任务的时候失败了
                return LENResponseError.REQUEST_SYSTEM_EXCEPTION;
            }
            return new LMInfo("任务删除成功!");
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

    @LANApi(URL = "/Task/QueryLocalTaskList",
            parameters = {"sessionFingerprint", "sessionId", "messageKey"},
            parameterIntros = {"要获取指定终端数据的管理会话指纹", "要获取的终端的会话ID", "本次通信的消息关键字"},
            description = "获取指定采集端的本地存储任务列表",
            errors = {1005, 4000})
    public Object queryLocalTaskList(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            LESession leSession = new LSSession().getByPrimaryKey(parameters.get("sessionId"));
            LMOnlineSession onlineSession = LSOnlineSession.sharedInstance().getDataGrabTerminalBySessionFingerprint(leSession.getSessionFingerprint());
            if (onlineSession == null) {
                // 这个会话不在线
                return LENResponseError.CONNECTION_ERROR_AIM_NOT_ONLINE;
            }
            // 目标终端在线，向指定终端发出请求
            // 首先，为了消息回传做准备，吧消息key和回传数据终端的指纹放到路由表
            LSMessageExchange.put(parameters.get("messageKey").toString(), parameters.get("sessionFingerprint").toString());
            // 然后，开始向指定的终端推送请求
            HashMap<String, Object> data = new HashMap<>();
            data.put("messageKey", parameters.get("messageKey").toString());
            LSOnlineSession.sharedInstance().outSuccessOnSession(onlineSession.getSession(), LHMina.SESSION_QUERY_LOCAL_TASK_LIST, data);
            return new LMInfo("数据获取请求已发出，等待目标终端响应通信中...");
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

    @LANApi(URL = "/Task/ResponseLocalTaskList",
            parameters = {"sessionFingerprint", "messageKey", "data"},
            parameterIntros = {"反馈指定获取任务列表的请求", "要反馈的通信的消息关键字"},
            description = "获取指定采集端的本地存储任务列表",
            errors = {1004})
    public Object responseLocalTaskList(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        LSSession lsSession = new LSSession();
        LESession leSession = lsSession.getSessionBySessionFingerprint((String) parameters.get("sessionFingerprint"));
        if (leSession == null) {// 会话指纹已经过期
            return LENResponseError.REQUEST_SESSION_FINGERPRINT_INVALID;
        }
        LMOnlineSession lmOnlineSession = LSMessageExchange.getOnlineSessionByKey(parameters.get("messageKey").toString());
        HashMap<String, Object> data = new HashMap<>();
        data.put("messageKey", parameters.get("messageKey"));
        data.put("data", parameters.get("data"));
        LSOnlineSession.sharedInstance().outSuccessOnSession(lmOnlineSession.getSession(), LHMina.SESSION_QUERY_LOCAL_TASK_LIST_RESPONSE, data);
        return new LMInfo("数据已经交由服务器处理！");
    }

    @LANApi(URL = "/Task/QueryLocalTaskDetail",
            parameters = {"sessionFingerprint", "sessionId", "messageKey", "taskFingerprint"},
            parameterIntros = {"要获取指定终端数据的管理会话指纹", "要获取的终端的会话ID", "本次通信的消息关键字", "要获取的任务的指纹"},
            description = "获取指定采集端的本地存储任务详情",
            errors = {1005, 4000})
    public Object queryLocalTaskDetail(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            LESession leSession = new LSSession().getByPrimaryKey(parameters.get("sessionId"));
            LMOnlineSession onlineSession = LSOnlineSession.sharedInstance().getDataGrabTerminalBySessionFingerprint(leSession.getSessionFingerprint());
            if (onlineSession == null) {
                // 这个会话不在线
                return LENResponseError.CONNECTION_ERROR_AIM_NOT_ONLINE;
            }
            // 目标终端在线，向指定终端发出请求
            // 首先，为了消息回传做准备，吧消息key和回传数据终端的指纹放到路由表
            LSMessageExchange.put(parameters.get("messageKey").toString(), parameters.get("sessionFingerprint").toString());
            // 然后，开始向指定的终端推送请求
            HashMap<String, Object> data = new HashMap<>();
            data.put("messageKey", parameters.get("messageKey").toString());
            data.put("taskFingerprint", parameters.get("taskFingerprint").toString());
            LSOnlineSession.sharedInstance().outSuccessOnSession(onlineSession.getSession(), LHMina.SESSION_QUERY_LOCAL_TASK_DETAIL, data);
            return new LMInfo("数据获取请求已发出，等待目标终端响应通信中...");
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

    @LANApi(URL = "/Task/ResponseLocalTaskDetail",
            parameters = {"sessionFingerprint", "messageKey", "data"},
            parameterIntros = {"反馈指定获取任务列表的请求", "要反馈的通信的消息关键字"},
            description = "获取指定采集端的本地存储任务列表",
            errors = {1004})
    public Object responseLocalTaskDetail(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        LSSession lsSession = new LSSession();
        LESession leSession = lsSession.getSessionBySessionFingerprint((String) parameters.get("sessionFingerprint"));
        if (leSession == null) {// 会话指纹已经过期
            return LENResponseError.REQUEST_SESSION_FINGERPRINT_INVALID;
        }
        LMOnlineSession lmOnlineSession = LSMessageExchange.getOnlineSessionByKey(parameters.get("messageKey").toString());
        HashMap<String, Object> data = new HashMap<>();
        data.put("messageKey", parameters.get("messageKey"));
        data.put("data", parameters.get("data"));
        LSOnlineSession.sharedInstance().outSuccessOnSession(lmOnlineSession.getSession(), LHMina.SESSION_QUERY_LOCAL_TASK_DETAIL_RESPONSE, data);
        return new LMInfo("数据已经交由服务器处理！");
    }

}

