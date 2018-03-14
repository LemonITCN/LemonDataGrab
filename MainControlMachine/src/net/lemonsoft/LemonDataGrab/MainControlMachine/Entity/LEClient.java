package net.lemonsoft.LemonDataGrab.MainControlMachine.Entity;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntity;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntityProperty;

/**
 * 实体类 - 客户端
 * Created by LiuRi on 16/4/24.
 */
@LANEntity(tableName = "ldg_client" , description = "客户端表")
public class LEClient implements LE {

    @LANEntityProperty(columnName = "clie_id" , description = "客户端ID" , primaryKey = true , autoIncrement = true)
    private Long cid;

    @LANEntityProperty(columnName = "clie_identity" , description = "客户端身份唯一标识")
    private String identity;

    @LANEntityProperty(columnName = "clie_clientFingerprint" , description = "客户端指纹")
    private String clientFingerprint;

    @LANEntityProperty(columnName = "clie_system" , description = "客户端操作系统")
    private String system;

    @LANEntityProperty(columnName = "clie_terminalType" , description = "终端的类型，如数据抓取终端、管理员终端等，参照终端类型枚举")
    private Long terminalType;

    @LANEntityProperty(columnName = "clie_device" , description = "设备信息，如CPU硬盘等信息")
    private String device;

    @LANEntityProperty(columnName = "clie_version" , description = "客户端软件版本号")
    private String version;

    @LANEntityProperty(columnName = "clie_registrationTime" , description = "客户端注册时间,unix时间戳")
    private Long registrationTime;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getClientFingerprint() {
        return clientFingerprint;
    }

    public void setClientFingerprint(String clientFingerprint) {
        this.clientFingerprint = clientFingerprint;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Long getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Long terminalType) {
        this.terminalType = terminalType;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Long registrationTime) {
        this.registrationTime = registrationTime;
    }

}
