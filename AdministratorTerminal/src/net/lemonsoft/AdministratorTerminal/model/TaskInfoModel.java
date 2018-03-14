package net.lemonsoft.AdministratorTerminal.model;

/**
 * 模型 - 任务信息
 * Created by LiuRi on 16/9/10.
 */
public class TaskInfoModel {

    private String taskId;
    private String taskFingerprint;
    private String taskName;
    private String taskState;
    private String taskPublishTime;
    private String taskSessionId;
    private String clientIp;

    public TaskInfoModel(String taskId, String taskFingerprint, String taskName, String taskState, String taskPublishTime, String taskSessionId, String clientIp) {
        this.taskId = taskId;
        this.taskFingerprint = taskFingerprint;
        this.taskName = taskName;
        this.taskState = taskState;
        this.taskPublishTime = taskPublishTime;
        this.taskSessionId = taskSessionId;
        this.clientIp = clientIp;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskFingerprint() {
        return taskFingerprint;
    }

    public void setTaskFingerprint(String taskFingerprint) {
        this.taskFingerprint = taskFingerprint;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getTaskPublishTime() {
        return taskPublishTime;
    }

    public void setTaskPublishTime(String taskPublishTime) {
        this.taskPublishTime = taskPublishTime;
    }

    public String getTaskSessionId() {
        return taskSessionId;
    }

    public void setTaskSessionId(String taskSessionId) {
        this.taskSessionId = taskSessionId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

}
