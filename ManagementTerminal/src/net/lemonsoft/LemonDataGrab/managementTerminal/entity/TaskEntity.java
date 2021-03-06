package net.lemonsoft.LemonDataGrab.managementTerminal.entity;

import net.lemonsoft.LemonDataGrab.managementTerminal.annotation.EntityAnnotation;
import net.lemonsoft.LemonDataGrab.managementTerminal.annotation.EntityPropertyAnnotation;

/**
 * 实体类 - 任务
 * Created by LiuRi on 5/28/16.
 */
@EntityAnnotation(tableName = "ldg_task", description = "任务表")
public class TaskEntity implements Entity {

    @EntityPropertyAnnotation(columnName = "task_id", description = "任务ID", primaryKey = true, autoIncrement = true)
    private Long tid;

    @EntityPropertyAnnotation(columnName = "task_name", description = "任务的名称")
    private String name;

    @EntityPropertyAnnotation(columnName = "task_fingerprint", description = "任务的唯一指纹")
    private String fingerprint;

    @EntityPropertyAnnotation(columnName = "task_description", description = "任务的简介")
    private String description;

    @EntityPropertyAnnotation(columnName = "task_createTime", description = "任务的创建时间")
    private Long createTime;

    @EntityPropertyAnnotation(columnName = "task_publishTime", description = "任务的发布时间")
    private Long publishTime;

    @EntityPropertyAnnotation(columnName = "task_distributeRepeatInterval", description = "任务的重复派发时间")
    private Long distributeRepeatInterval;

    @EntityPropertyAnnotation(columnName = "task_distributeRepeatCount", description = "任务的重复派发次数")
    private Long distributeRepeatCount;

    @EntityPropertyAnnotation(columnName = "task_distributedNumber", description = "任务分发序号")
    private Long distributedNumber;

    @EntityPropertyAnnotation(columnName = "task_executeScript", description = "任务执行脚本")
    private String executeScript;

    @EntityPropertyAnnotation(columnName = "task_taskUrl", description = "任务所要执行的页面的URL")
    private String taskUrl;

    @EntityPropertyAnnotation(columnName = "task_loadUrlTimeout", description = "页面加载的超时时间")
    private Long loadUrlTimeout;

    @EntityPropertyAnnotation(columnName = "task_state", description = "任务状态")
    private Integer state;

    @EntityPropertyAnnotation(columnName = "task_theTop", description = "任务是否置顶")
    private Integer theTop;

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }

    public Long getDistributeRepeatInterval() {
        return distributeRepeatInterval;
    }

    public void setDistributeRepeatInterval(Long distributeRepeatInterval) {
        this.distributeRepeatInterval = distributeRepeatInterval;
    }

    public Long getDistributeRepeatCount() {
        return distributeRepeatCount;
    }

    public void setDistributeRepeatCount(Long distributeRepeatCount) {
        this.distributeRepeatCount = distributeRepeatCount;
    }

    public Long getDistributedNumber() {
        return distributedNumber;
    }

    public void setDistributedNumber(Long distributedNumber) {
        this.distributedNumber = distributedNumber;
    }

    public String getExecuteScript() {
        return executeScript;
    }

    public void setExecuteScript(String executeScript) {
        this.executeScript = executeScript;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public Long getLoadUrlTimeout() {
        return loadUrlTimeout;
    }

    public void setLoadUrlTimeout(Long loadUrlTimeout) {
        this.loadUrlTimeout = loadUrlTimeout;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTheTop() {
        return theTop;
    }

    public void setTheTop(Integer theTop) {
        this.theTop = theTop;
    }
}
