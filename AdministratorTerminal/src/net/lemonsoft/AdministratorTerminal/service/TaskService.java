package net.lemonsoft.AdministratorTerminal.service;

import com.sun.javafx.tk.Toolkit;
import net.lemonsoft.AdministratorTerminal.model.TaskDetail;
import net.lemonsoft.AdministratorTerminal.model.TaskInfoModel;
import net.lemonsoft.AdministratorTerminal.tool.DateTool;
import net.lemonsoft.AdministratorTerminal.tool.HttpTool;
import net.lemonsoft.AdministratorTerminal.tool.URLTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * service层 - 任务相关
 * Created by lemonsoft on 2016/8/23.
 */
public class TaskService {

    private static TaskService defaultService;

    private static final String[] STATE_ARR = new String[]{"待分发", "分发中", "分发完毕", "采集成功", "采集超时", "采集出错"};

    private TaskService() {
    }

    public static TaskService defaultService() {
        if (defaultService == null)
            defaultService = new TaskService();
        return defaultService;
    }

    /**
     * 获取指定日期范围之内的任务数量
     *
     * @param startDate 要获取的日期范围的开始日期UNIX时间戳
     * @param endDate   要获取的日期范围的结束日期的UNIX时间戳
     * @return 指定日期范围之内的任务数量
     */
    public Integer countTaskWithDate(Long startDate, Long endDate) {
        HashMap<String, String> params = Service.getParamsMapWithSessionFingerprint();
        params.put("startDate", String.valueOf(startDate));
        params.put("endDate", String.valueOf(endDate));
        String result = HttpTool.post(URLTool.URL_TASK_COUNT_BY_DATE, params);
        return JSONObject.fromObject(result).getJSONObject("result").getInt("data");
    }

    /**
     * 查询指定日期范围之内的所有任务信息
     *
     * @param startDate 查询任务信息的起始日期
     * @param endDate   查询任务信息的结束日期
     * @return 查询到日期列表
     */
    public ArrayList<TaskInfoModel> queryTaskInfoWithDate(Long startDate, Long endDate, Integer startIndex, Integer count) {
        HashMap<String, String> params = Service.getParamsMapWithSessionFingerprint();
        params.put("startDate", String.valueOf(startDate));
        params.put("endDate", String.valueOf(endDate));
        params.put("startIndex", String.valueOf(startIndex));
        params.put("count", String.valueOf(count));
        String result = HttpTool.post(URLTool.URL_TASK_INFO_LIST_BY_DATE, params);
        JSONArray data = JSONObject.fromObject(result).getJSONObject("result").getJSONArray("data");
        ArrayList<TaskInfoModel> re = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JSONObject item = data.getJSONObject(i);

            re.add(new TaskInfoModel(item.getString("taskId"), item.getString("taskFingerprint"),
                    item.getString("taskName"), STATE_ARR[item.getInt("taskState")],
                    DateTool.timeStamp2Date(item.getString("taskPublishTime"), "yyyy-MM-dd HH:mm:SS"),
                    item.getString("taskSessionId"), item.getString("clientIp")));
        }
        return re;
    }

    /**
     * 查询指定指纹对应的任务指纹对应的任务详情
     *
     * @param taskFingerprint 要查询的任务的任务指纹
     * @return 查询出的任务详情模型对象
     */
    public TaskDetail queryTaskDetailWithFingerprint(String taskFingerprint) {
        HashMap<String, String> parameters = Service.getParamsMapWithSessionFingerprint();
        parameters.put("taskFingerprint", taskFingerprint);
        String result = HttpTool.post(URLTool.URL_TASK_DETAIL_BY_FINGERPRINT, parameters);
        JSONObject data = JSONObject.fromObject(result).getJSONObject("result").getJSONObject("data");
        JSONObject task = data.getJSONObject("task");
        TaskDetail taskDetail = new TaskDetail();
        taskDetail.setTid(task.getString("tid"));
        taskDetail.setName(task.getString("name"));
        taskDetail.setFingerprint(task.getString("fingerprint"));
        taskDetail.setDescription(task.getString("description"));
        taskDetail.setCreateTime(task.getLong("createTime"));
        taskDetail.setPublishTime(task.getLong("publishTime"));
        taskDetail.setDistributeRepeatInterval(task.getLong("distributeRepeatInterval"));
        taskDetail.setDistributeRepeatCount(task.getLong("distributeRepeatCount"));
        taskDetail.setDistributedNumber(task.getLong("distributedNumber"));
        taskDetail.setExecuteScript(task.getString("executeScript"));
        taskDetail.setState(task.getInt("state"));
        taskDetail.setLevel(task.getInt("level"));
        taskDetail.setExpired(task.getLong("expired"));
        taskDetail.setSession(task.getLong("session"));
        taskDetail.setCanEdit(data.getBoolean("canEdit"));
        return taskDetail;
    }

    /**
     * 更新指定任务的信息
     *
     * @param taskDetail 要更新成的任务详情对象
     * @return 服务器返回的字符串信息
     */
    public String updateTaskInfo(TaskDetail taskDetail) {
        HashMap<String, String> params = Service.getParamsMapWithSessionFingerprint();
        params.put("taskFingerprint", taskDetail.getFingerprint());
        params.put("name", taskDetail.getName());
        params.put("description", taskDetail.getDescription());
        params.put("publishTime", taskDetail.getPublishTime() + "");
        params.put("executeScript", taskDetail.getExecuteScript());
        params.put("expired", taskDetail.getExpired() + "");
        params.put("level", taskDetail.getLevel() + "");
        String result = HttpTool.post(URLTool.URL_TASK_UPDATE, params);
        JSONObject resultObj = JSONObject.fromObject(result);
        return resultObj.getBoolean("success") ?
                "SUCCESS:" + resultObj.getJSONObject("result").getString("info") : "ERROR:" + resultObj.getString("info");
    }

}
