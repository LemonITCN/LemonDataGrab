package net.lemonsoft.LemonDataGrab.MainControlMachine.Dao;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LESession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUDatabase;

import java.util.ArrayList;

/**
 * 业务层 - 会话
 * Created by LiuRi on 16/4/24.
 */
public class LDSession extends LD<LESession> {

    // 重置所有的会话状态的SQL语句
    private static final String SQL_RESET_ALL_SESSION_STATE = "UPDATE ldg_session SET ldg_session.sess_state = 0;";

    /**
     * 通过客户端指纹和登录的用户ID来获取指定的会话对象
     *
     * @param clientFingerprint 客户端指纹
     * @param uid               用户的ID
     * @return 查询到的用户对象
     * @throws Exception
     */
    public LESession getSessionByClientFingerprintAndUid(String clientFingerprint, Long uid) throws Exception {
        LEClient leClient = new LSClient().getClientByClientFingerprint(clientFingerprint);
        ArrayList<LESession> result = this.getBySQLCondition("WHERE sess_client = ? AND sess_user = ?", leClient.getCid(), uid);
        if (result.size() > 0) {// 通过设备标识查询到了客户端对象
            return result.get(0);
        }
        return null;
    }

    /**
     * 通过客户端指纹来查询会话对象表
     *
     * @param clientFingerprint 要查询的客户端指纹字符串
     * @return 查询到的会话对象List
     * @throws Exception
     */
    public ArrayList<LESession> getSessionListByClientFingerprint(String clientFingerprint) throws Exception {
        LEClient leClient = new LSClient().getClientByClientFingerprint(clientFingerprint);
        return this.getBySQLCondition("WHERE sess_client = ?", leClient.getCid());
    }

    /**
     * 通过用户的ID来查询会话对象List
     *
     * @param userId 要查询的会话的所属用户的ID
     * @return 查询到的会话对象List
     * @throws Exception
     */
    public ArrayList<LESession> getSessionListByUid(String userId) throws Exception {
        return this.getBySQLCondition("WHERE sess_user = ?", userId);
    }

    /**
     * 通过会话指纹来获取会话对象
     *
     * @param sessionFingerprint 要获取的会话对象的会话指纹
     * @return 查询到的会话指纹
     * @throws Exception
     */
    public LESession getSessionBySessionFingerprint(String sessionFingerprint) throws Exception {
        ArrayList<LESession> result = this.getBySQLCondition("WHERE sess_sessionFingerprint = ?", sessionFingerprint);
        if (result.size() > 0) {// 通过设备标识查询到了客户端对象
            return result.get(0);
        }
        return null;
    }

    /**
     * 通过会话指纹移除指定的会话
     *
     * @param sessionFingerprint 要移除的会话的会话指纹
     * @return 移除的成功的布尔值
     */
    public boolean removeSessionBySessionFingerprint(String sessionFingerprint) {
        return this.deleteBySQLCondition("WHERE sess_sessionFingerprint = ?", sessionFingerprint) > 0;
    }

    /**
     * 通过会话指纹判断是否存在这个会话对象
     *
     * @param sessionFingerprint 要判断是否存在的会话的会话指纹
     * @return 是否存在这个会话的布尔值
     */
    public boolean isHaveTheSessionBySessionFingerprint(String sessionFingerprint) {
        return this.countBySQLCondition("WHERE sess_sessionFingerprint = ?", sessionFingerprint) > 0;
    }

    /**
     * 重置所有会话的状态，该函数为ServletContextListener调用，设置所有的会话状态为已下线
     * @return 设置成功的布尔值
     */
    public void resetAllSessionState(){
        LUDatabase.update(SQL_RESET_ALL_SESSION_STATE);
    }

}
