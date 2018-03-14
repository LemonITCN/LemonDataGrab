package net.lemonsoft.DataGrab.service;

import com.google.gson.Gson;
import javafx.application.Platform;
import net.lemonsoft.DataGrab.Main;
import net.lemonsoft.DataGrab.entity.ResultEntity;
import net.lemonsoft.DataGrab.entity.TaskEntity;
import net.lemonsoft.DataGrab.tool.HttpTool;
import net.lemonsoft.DataGrab.tool.SystemInfoTool;
import net.lemonsoft.DataGrab.tool.URLTool;
import net.lemonsoft.lwc.core.MainManager;
import net.lemonsoft.lwc.core.SubController;
import net.lemonsoft.lwc.core.Tty;
import net.lemonsoft.lwc.core.handler.CommunicationHandler;
import net.sf.json.JSONObject;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * service层 - 任务相关
 * Created by lemonsoft on 2016/8/23.
 */
public class TaskService {

    public static List<TaskEntity> taskList;//任务列表
    private static String[] typeArr = new String[]{"wait", "running", "uploading", "success", "fail", "uploadFail"};
    private static Integer pointer;// 任务执行序号指针
    private static MainManager mainManager;
    private static SubController subController;
    private static boolean isRunning;
    private static TaskEntity currentRunTask;
    private static Tty currentTty;

    private static final String TASK_SUCCESS = "success";
    private static final String TASK_FAILED = "failed";

    static {
        taskList = new ArrayList<>();
        pointer = -1;
        isRunning = false;
    }

    /**
     * 添加一个任务
     *
     * @param taskEntity 任务实体
     */
    public static void addTask(TaskEntity taskEntity) {
        taskList.add(taskEntity);
    }

    /**
     * 删除指定索引的任务
     *
     * @param index 要删除的服务的索引
     */
    public static void removeTaskByIndex(Integer index) {
        taskList.remove(index);
    }

    /**
     * 移除所有的任务
     */
    public static void removeAll() {
        taskList.clear();
    }

    /**
     * 查询当前任务列表中任务的数量
     *
     * @return 当前所有任务的数量
     */
    public static Integer countTask() {
        return taskList.size();
    }

    /**
     * 获取当前正在处理的任务指针所对应的索引
     *
     * @return 当前正在处理的任务的索引值
     */
    public static Integer pointerIndex() {
        return pointer;
    }

    public static String getStateText(Integer stateIndex) {
        return (stateIndex < typeArr.length && stateIndex >= 0) ? typeArr[stateIndex] : "sysERR";
    }

    public synchronized static SubController createTasker() {
//        if (mainManager == null){
        mainManager = new MainManager();
        //  首先关闭一次所有浏览器
        subController = mainManager.createSubController();
        subController.setCommunicationHandler("task", new CommunicationHandler() {
            @Override
            public Object onMessage(Object o) {
                switch ((String) o) {
                    case TASK_SUCCESS:
                        currentRunTask.setState(2);
                        uploadResult(true, currentRunTask);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                subController.closeAllBrowser();
                                subController.removeAllLogs();
                                subController.removeAllData();
                                subController.getConsole().removeTtyById(currentTty.getId());
                            }
                        });
                        isRunning = false;
                        executeNextTask();
                        break;
                }
                return null;
            }
        });
//        }
        return subController;
    }

    public synchronized static void executeNextTask() {
        if ((countTask() - 1 > pointer) && !isRunning) {// 当前没有任务运行，且当前有未处理的任务
            pointer++;
            createTasker();
            TaskEntity taskEntity = taskList.get(pointer);
            currentRunTask = taskEntity;
            subController.removeAllLogs();
            subController.removeAllData();
            isRunning = true;
            subController.setLocal_file_name(taskEntity.getFingerprint());
            taskEntity.setState(1);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    currentTty = subController.getConsole().createATty("taskTTY");
                    try {
                        currentTty.executeJavaScript(taskEntity.getExecuteScript());
                    } catch (Exception e) {
                        // 处理TTY的异常
                        String data = new Gson().toJson(subController.getDataCollectionPool());
                        String log = new Gson().toJson(subController.getLogList());
                        currentRunTask.setState(2);
                        uploadResult(false, currentRunTask);
                        isRunning = false;
                        executeNextTask();
                    }
                }
            });
        }
        Main.commandMain.defaultMainViewController.refreshTaskList();
    }

    public static void uploadResult(boolean isSuccess, TaskEntity task) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<>();
                params.put("sessionFingerprint", SystemInfoTool.sharedInstance().getSessionFingerprint());
                params.put("fingerprint", task.getFingerprint());
                params.put("state", isSuccess ? "1" : "0");
                HashMap<String, String> files = new HashMap<>();
//                files.put("log", System.getProperty("user.home") + File.separator + "lwc_data" + File.separator + task.getFingerprint() + "_log.csv");
//                files.put("data", System.getProperty("user.home") + File.separator + "lwc_data" + File.separator + task.getFingerprint() + ".csv");
                files.put("data", System.getProperty("user.home") + File.separator + "lwc_data" + File.separator + task.getFingerprint() + ".db");
                ResultEntity resultEntity = new ResultEntity(task.getFingerprint(), null, null, SystemInfoTool.sharedInstance().getSessionFingerprint());
                resultEntity.setName(task.getName());
                resultEntity.setPublishTime(Math.toIntExact(task.getPublishTime()));
                // 下面这行注释掉，先不存到本地
//                new ResultService().add(resultEntity);// 保存采集结果到本地数据库
                try {
                    String result = HttpTool.post(URLTool.URL_RESULT_RESPONSE, params, files);
                    JSONObject resultObject = JSONObject.fromObject(result);
                    if (resultObject.getBoolean("success"))
                        task.setState(3);// success c: 3
                    else
                        task.setState(5);// upload fail c: 5
                } catch (Exception e) {
                    // upload fail c: 5
                    task.setState(5);
                    e.printStackTrace();
                }
            }
        }).run();
    }

    /**
     * 向服务器上传本地的已采集任务指纹列表
     *
     * @param msgKey 消息的回传关键字
     */
    public static void uploadLocalTaskList(String msgKey) {
        List<ResultEntity> resultEntities = new ResultService().getAllDataByScope(0, 10000000);
        List<Map<String, Object>> result = new ArrayList<>();
        for (ResultEntity resultEntity : resultEntities) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", resultEntity.getName());
            item.put("publishTime", resultEntity.getPublishTime());
            item.put("fingerprint", resultEntity.getFingerprint());
            result.add(item);
        }
        HashMap<String, String> data = new HashMap<>();
        data.put("data", new Gson().toJson(result));
        data.put("messageKey", msgKey);
        data.put("sessionFingerprint", SystemInfoTool.sharedInstance().getSessionFingerprint());
        HttpTool.post(URLTool.URL_RESPONSE_LOCAL_TASK_LIST, data);
    }

    /**
     * 向服务器上传本地的已经采集的指定任务的采集结果
     *
     * @param msgKey          消息的回传关键字
     * @param taskFingerprint 要获取的任务的指纹
     */
    public static void uploadLocalTaskDetail(String msgKey, String taskFingerprint) {
        try {
            ResultEntity resultEntity = new ResultService().getByFingerprint(taskFingerprint);
            HashMap<String, String> data = new HashMap<>();
            data.put("data", new Gson().toJson(resultEntity));
            data.put("messageKey", msgKey);
            data.put("sessionFingerprint", SystemInfoTool.sharedInstance().getSessionFingerprint());
            HttpTool.post(URLTool.URL_RESPONSE_LOCAL_TASK_DETAIL, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}