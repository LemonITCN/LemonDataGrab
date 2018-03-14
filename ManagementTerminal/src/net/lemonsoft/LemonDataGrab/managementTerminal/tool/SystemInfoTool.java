package net.lemonsoft.LemonDataGrab.managementTerminal.tool;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * 工具类 - 系统信息
 * Created by liuri on 6/1/16.
 */
public class SystemInfoTool {

    private static final String IDENTITY = "SYSTEM_IDENTITY";
    private static final String CLIENT_FINGERPRINT = "SYSTEM_CLIENT_FINGERPRINT";
    private static final String SESSION_FINGERPRINT = "SYSTEM_SESSION_FINGERPRINT";

    private static final String TERMINAL_TYPE = "1";

    private static final String APP_VERSION = "1.0";

    private static final String APP_NAME = "分布式网络数据采集器·管理终端";

    public String getAppName() {
        return APP_NAME;
    }

    private static SystemInfoTool systemInfoTool = null;

    public static SystemInfoTool sharedInstance() {
        if (systemInfoTool == null)
            systemInfoTool = new SystemInfoTool();
        return systemInfoTool;
    }

    private SystemInfoTool() {

    }

    /**
     * 获取设备名称
     *
     * @return 设备名称字符串
     */
    public String getDeviceName() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "UNKNOWN NAME DEVICE";
        }
    }

    /**
     * 获取系统名称
     *
     * @return 系统名称字符串
     */
    public String getSystemName() {
        return System.getProperty("os.name") + " " + System.getProperty("os.version");
    }

    /**
     * 获取设备标识
     *
     * @return 设备标识字符串
     */
    public String getIdentity() {
        try {
            if (ConfigTool.sharedInstance().contain(IDENTITY)) {
                // 存在这个配置
                return ConfigTool.sharedInstance().getOrDefault(IDENTITY, "");
            } else {
                // 不存在这个配置项
                String newIdentity = UUID.randomUUID().toString();
                ConfigTool.sharedInstance().set(IDENTITY, newIdentity);
                return newIdentity;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取终端类型
     *
     * @return 终端类型字符串
     */
    public String getTerminalType() {
        return TERMINAL_TYPE;
    }

    /**
     * 获取软件版本号
     *
     * @return 软件版本号字符串
     */
    public String getAppVersion() {
        return APP_VERSION;
    }

    /**
     * 获取本地的客户端指纹
     *
     * @return 本地的客户端指纹
     */
    public String getClientFingerprint() {
        try {
            return ConfigTool.sharedInstance().getOrDefault(CLIENT_FINGERPRINT, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取本地存储的绘画指纹
     *
     * @return 本地存储的会话指纹
     */
    public String getSessionFingerprint() {
        try {
            return ConfigTool.sharedInstance().getOrDefault(SESSION_FINGERPRINT, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 设置本地存储的客户端指纹
     *
     * @param clientFingerprint 本地存储的客户端指纹
     */
    public void setClientFingerprint(String clientFingerprint) {
        try {
            ConfigTool.sharedInstance().set(CLIENT_FINGERPRINT, clientFingerprint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置本地存储的会话指纹
     *
     * @param sessionFingerprint 本地存储的会话指纹
     */
    public void setSessionFingerprint(String sessionFingerprint) {
        try {
            ConfigTool.sharedInstance().set(SESSION_FINGERPRINT, sessionFingerprint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个随机的UUID字符串
     *
     * @return UUID字符串
     */
    public String createUUID() {
        return UUID.randomUUID().toString();
    }

}
