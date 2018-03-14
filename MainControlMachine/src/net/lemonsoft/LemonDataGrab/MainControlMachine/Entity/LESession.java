package net.lemonsoft.LemonDataGrab.MainControlMachine.Entity;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntity;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntityProperty;

import java.util.Map;

/**
 * 实体类 - 会话
 * Created by LiuRi on 16/4/24.
 */
@LANEntity(tableName = "ldg_session" , description = "会话表" )
public class LESession implements LE {

    @LANEntityProperty(columnName = "sess_id" , description = "会话ID" , primaryKey = true , autoIncrement = true)
    private Long sid;

    @LANEntityProperty(columnName = "sess_sessionFingerprint" , description = "会话指纹")
    private String sessionFingerprint;

    @LANEntityProperty(columnName = "sess_client" , description = "会话参与的客户端")
    private Long client;

    @LANEntityProperty(columnName = "sess_user" , description = "会话参与的用户")
    private Long user;

    @LANEntityProperty(columnName = "sess_setupTime" , description = "会话的建立时间,unix时间戳")
    private Long setupTime;

    @LANEntityProperty(columnName = "sess_lastCommunicationTime" , description = "上次通过此会话进行通信的时间，unix时间戳")
    private Long lastCommunicationTime;

    @LANEntityProperty(columnName = "sess_state" , description = "会话的状态码，记录如是否被激活等状态")
    private Long state;

    @LANEntityProperty(columnName = "sess_ip" , description = "会话所连接进来的终端的IP地址")
    private String ip;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getSessionFingerprint() {
        return sessionFingerprint;
    }

    public void setSessionFingerprint(String sessionFingerprint) {
        this.sessionFingerprint = sessionFingerprint;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(Long setupTime) {
        this.setupTime = setupTime;
    }

    public Long getLastCommunicationTime() {
        return lastCommunicationTime;
    }

    public void setLastCommunicationTime(Long lastCommunicationTime) {
        this.lastCommunicationTime = lastCommunicationTime;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
