package net.lemonsoft.DataGrab.entity;

import net.lemonsoft.DataGrab.annotation.EntityAnnotation;
import net.lemonsoft.DataGrab.annotation.EntityPropertyAnnotation;

/**
 * Created by LiuRi on 5/28/16.
 */
@EntityAnnotation(tableName = "ldg_result", description = "任务采集结果表，用来保存所有采集的结果数据，以供后期使用或服务器获取")
public class ResultEntity implements Entity {

    @EntityPropertyAnnotation(columnName = "resu_id", description = "任务采集结果表的主键,id序号", primaryKey = true, autoIncrement = true)
    private Integer id;

    @EntityPropertyAnnotation(columnName = "resu_name", description = "任务的名称")
    private String name;

    @EntityPropertyAnnotation(columnName = "resu_publishTime", description = "任务的发布时间")
    private Integer publishTime;

    @EntityPropertyAnnotation(columnName = "resu_fingerprint", description = "任务的指纹字段")
    private String fingerprint;

    @EntityPropertyAnnotation(columnName = "resu_data", description = "任务的采集结果数据字段")
    private String data;

    @EntityPropertyAnnotation(columnName = "resu_log", description = "任务的采集log字段")
    private String log;

    @EntityPropertyAnnotation(columnName = "resu_sessionFingerprint", description = "任务执行时的会话指纹字段")
    private String sessionFingerprint;

    public ResultEntity() {
    }

    public ResultEntity(String fingerprint, String data, String log, String sessionFingerprint) {
        this.fingerprint = fingerprint;
        this.data = data;
        this.log = log;
        this.sessionFingerprint = sessionFingerprint;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Integer publishTime) {
        this.publishTime = publishTime;
    }
}
