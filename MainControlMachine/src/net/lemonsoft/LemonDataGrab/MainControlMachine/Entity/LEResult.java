package net.lemonsoft.LemonDataGrab.MainControlMachine.Entity;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntity;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntityProperty;

/**
 * Created by LiuRi on 5/28/16.
 */
@LANEntity(tableName = "ldg_result", description = "任务结果表")
public class LEResult implements LE {

    @LANEntityProperty(columnName = "resu_id", description = "结果ID", primaryKey = true, autoIncrement = true)
    private Long rid;

    @LANEntityProperty(columnName = "resu_fingerprint", description = "结果指纹")
    private String fingerprint;

    @LANEntityProperty(columnName = "resu_recoveryTime", description = "回收时间")
    private Long recoveryTime;

    @LANEntityProperty(columnName = "resu_data", description = "数据")
    private String data;

    @LANEntityProperty(columnName = "resu_log", description = "采集数据过程中产生的日志")
    private String log;

    @LANEntityProperty(columnName = "resu_sessionFingerprint", description = "通信会话指纹")
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
