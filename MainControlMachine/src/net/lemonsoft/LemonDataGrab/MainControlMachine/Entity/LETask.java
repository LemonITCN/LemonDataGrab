package net.lemonsoft.LemonDataGrab.MainControlMachine.Entity;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntity;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntityProperty;

/**
 * 实体类 - 任务
 * Created by LiuRi on 5/28/16.
 */
@LANEntity(tableName = "ldg_task", description = "任务表")
public class LETask implements LE {

    @LANEntityProperty(columnName = "task_id", description = "任务ID", primaryKey = true, autoIncrement = true)
    private Long tid;

    @LANEntityProperty(columnName = "task_name", description = "任务的名称")
    private String name;

    @LANEntityProperty(columnName = "task_fingerprint", description = "任务的唯一指纹")
    private String fingerprint;

    @LANEntityProperty(columnName = "task_description", description = "任务的简介")
    private String description;

    @LANEntityProperty(columnName = "task_createTime", description = "任务的创建时间")
    private Long createTime;

    @LANEntityProperty(columnName = "task_publishTime", description = "任务的发布时间")
    private Long publishTime;

    @LANEntityProperty(columnName = "task_distributeRepeatInterval", description = "任务的重复派发时间")
    private Long distributeRepeatInterval;

    @LANEntityProperty(columnName = "task_distributeRepeatCount", description = "任务的重复派发次数")
    private Long distributeRepeatCount;

    @LANEntityProperty(columnName = "task_distributedNumber", description = "任务分发序号")
    private Long distributedNumber;

    @LANEntityProperty(columnName = "task_executeScript", description = "任务执行脚本")
    private String executeScript;

    @LANEntityProperty(columnName = "task_state", description = "任务状态")
    private Integer state;

    @LANEntityProperty(columnName = "task_level", description = "任务的级别，默认为0，数值越高，执行的优先级越高")
    private Long level;

    @LANEntityProperty(columnName = "task_expired", description = "过期时间，当任务未能在指定时间发布出去，那么最迟的过期时间段，该字段为时间段，而非时间点")
    private Long expired;

    @LANEntityProperty(columnName = "task_session", description = "当前任务的所处会话ID，如果还没有分发出去，那么该值为0")
    private Long session;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getExpired() {
        return expired;
    }

    public void setExpired(Long expired) {
        this.expired = expired;
    }

    public Long getSession() {
        return session;
    }

    public void setSession(Long session) {
        this.session = session;
    }
}
