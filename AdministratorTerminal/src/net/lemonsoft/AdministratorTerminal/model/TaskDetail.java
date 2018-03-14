package net.lemonsoft.AdministratorTerminal.model;

/**
 * 任务详情模型 - 用于修改任务信息前的获取任务详情
 * Created by lemonsoft on 16-10-28.
 */
public class TaskDetail {

    private String tid;
    private String name;
    private String fingerprint;
    private String description;
    private Long createTime;
    private Long publishTime;
    private Long distributeRepeatInterval;
    private Long distributeRepeatCount;
    private Long distributedNumber;
    private String executeScript;
    private Integer state;
    private Integer level;
    private Long expired;
    private Long session;
    private boolean canEdit;

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
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
