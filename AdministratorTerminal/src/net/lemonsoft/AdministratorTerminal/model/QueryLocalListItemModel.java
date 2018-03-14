package net.lemonsoft.AdministratorTerminal.model;

/**
 * 查询客户端本地任务列表的子项模型类
 * Created by lemonsoft on 2016/12/11.
 */
public class QueryLocalListItemModel {

    private String taskName;
    private String taskFingerprint;
    private String taskPublishTime;

    public QueryLocalListItemModel(String taskName, String taskFingerprint, String taskPublishTime) {
        this.taskName = taskName;
        this.taskFingerprint = taskFingerprint;
        this.taskPublishTime = taskPublishTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskFingerprint() {
        return taskFingerprint;
    }

    public void setTaskFingerprint(String taskFingerprint) {
        this.taskFingerprint = taskFingerprint;
    }

    public String getTaskPublishTime() {
        return taskPublishTime;
    }

    public void setTaskPublishTime(String taskPublishTime) {
        this.taskPublishTime = taskPublishTime;
    }
}
