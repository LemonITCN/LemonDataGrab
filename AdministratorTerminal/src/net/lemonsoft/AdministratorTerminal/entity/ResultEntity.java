package net.lemonsoft.AdministratorTerminal.entity;

/**
 * Created by LiuRi on 5/28/16.
 */
public class ResultEntity implements Entity {

    private Long rid;

    private String fingerprint;

    private Long recoveryTime;

    private String data;

    private String log;

    private String sessionFingerprint;

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

    public String getSessionFingerprint() {
        return sessionFingerprint;
    }

    public void setSessionFingerprint(String sessionFingerprint) {
        this.sessionFingerprint = sessionFingerprint;
    }
}
