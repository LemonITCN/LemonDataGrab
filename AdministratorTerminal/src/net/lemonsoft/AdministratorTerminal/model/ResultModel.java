package net.lemonsoft.AdministratorTerminal.model;

/**
 * 模型类 - 结果信息
 * Created by LiuRi on 16/10/30.
 */
public class ResultModel {

    // 结果ID
    private Long rid;
    // 结果对应的任务的指纹
    private String fingerprint;
    // 结果回收时间
    private Long recoveryTime;
    // 结果回收的数据
    private String data;
    // 结果回收时候带回来的log日志
    private String log;
    // 采集终端会话指纹
    private String sessionFingerprint;

    public String getSessionFingerprint() {
        return sessionFingerprint;
    }

    public void setSessionFingerprint(String sessionFingerprint) {
        this.sessionFingerprint = sessionFingerprint;
    }

    public ResultModel() {
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Long getRecoveryTime() {
        return recoveryTime;
    }

    public void setRecoveryTime(Long recoveryTime) {
        this.recoveryTime = recoveryTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
