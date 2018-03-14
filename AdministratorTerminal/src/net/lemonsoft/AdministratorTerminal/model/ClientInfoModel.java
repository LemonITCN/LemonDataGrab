package net.lemonsoft.AdministratorTerminal.model;

/**
 * 模型 - 客户端信息
 * Created by LiuRi on 16/9/10.
 */
public class ClientInfoModel {

    private String sessionId;
    private String clientId;
    private String userId;
    private String clientSystem;
    private String clientDeviceName;
    private String userName;
    private String userUsergroup;
    private String clientIp;
    private String sessionState;

    public ClientInfoModel(String sessionId, String clientId, String userId, String clientSystem,
                           String clientDeviceName, String userName, String userUsergroup,
                           String clientIp, String sessionState) {
        this.sessionId = sessionId;
        this.clientId = clientId;
        this.userId = userId;
        this.clientSystem = clientSystem;
        this.clientDeviceName = clientDeviceName;
        this.userName = userName;
        this.userUsergroup = userUsergroup;
        this.clientIp = clientIp;
        this.sessionState = sessionState;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientSystem() {
        return clientSystem;
    }

    public void setClientSystem(String clientSystem) {
        this.clientSystem = clientSystem;
    }

    public String getClientDeviceName() {
        return clientDeviceName;
    }

    public void setClientDeviceName(String clientDeviceName) {
        this.clientDeviceName = clientDeviceName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUsergroup() {
        return userUsergroup;
    }

    public void setUserUsergroup(String userUsergroup) {
        this.userUsergroup = userUsergroup;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSessionState() {
        return sessionState;
    }

    public void setSessionState(String sessionState) {
        this.sessionState = sessionState;
    }

}
