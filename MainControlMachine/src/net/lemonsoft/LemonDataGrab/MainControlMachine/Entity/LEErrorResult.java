package net.lemonsoft.LemonDataGrab.MainControlMachine.Entity;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntity;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntityProperty;

/**
 * 实体类- 错误采集结果
 * Created by lemonsoft on 2016/8/25.
 */
@LANEntity(tableName = "ldg_errorresult", description = "任务错误结果表")
public class LEErrorResult implements LE {

    @LANEntityProperty(columnName = "erre_id", description = "错误结果ID", primaryKey = true, autoIncrement = true)
    private Long rid;

    @LANEntityProperty(columnName = "erre_fingerprint", description = "结果指纹")
    private String fingerprint;

    @LANEntityProperty(columnName = "erre_recoveryTime", description = "回收时间")
    private Long recoveryTime;

    @LANEntityProperty(columnName = "erre_data", description = "数据")
    private String data;

    @LANEntityProperty(columnName = "erre_log", description = "采集数据过程中产生的日志")
    private String log;

    @LANEntityProperty(columnName = "erre_sessionFingerprint", description = "通信会话指纹")
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
