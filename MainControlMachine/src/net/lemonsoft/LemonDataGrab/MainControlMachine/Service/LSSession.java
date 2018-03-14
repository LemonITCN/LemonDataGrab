package net.lemonsoft.LemonDataGrab.MainControlMachine.Service;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Dao.LDSession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LESession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENSessionState;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUDatabase;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

/**
 * Created by LiuRi on 5/16/16.
 */
public class LSSession extends LS<LESession, LDSession> {

    /**
     * 通过客户端指纹和登录的用户ID来获取指定的会话对象
     *
     * @param clientFingerprint 客户端指纹
     * @param uid               用户的ID
     * @return 查询到的用户对象
     * @throws Exception
     */
    public LESession getSessionByClientFingerprintAndUid(String clientFingerprint, Long uid) throws Exception {
        return this.getDao().getSessionByClientFingerprintAndUid(clientFingerprint , uid);
    }

    /**
     * 通过客户端指纹来查询会话对象表
     *
     * @param clientFingerprint 要查询的客户端指纹字符串
     * @return 查询到的会话对象List
     * @throws Exception
     */
    public ArrayList<LESession> getSessionListByClientFingerprint(String clientFingerprint) throws Exception {
        return this.getDao().getSessionListByClientFingerprint(clientFingerprint);
    }

    /**
     * 通过用户的ID来查询会话对象List
     *
     * @param userId 要查询的会话的所属用户的ID
     * @return 查询到的会话对象List
     * @throws Exception
     */
    public ArrayList<LESession> getSessionListByUid(String userId) throws Exception {
        return this.getDao().getSessionListByUid(userId);
    }

    /**
     * 通过会话指纹来获取会话对象
     *
     * @param sessionFingerprint 要获取的会话对象的会话指纹
     * @return 查询到的会话指纹
     * @throws Exception
     */
    public LESession getSessionBySessionFingerprint(String sessionFingerprint) throws Exception {
        return this.getDao().getSessionBySessionFingerprint(sessionFingerprint);
    }

    /**
     * 通过会话指纹移除指定的会话
     *
     * @param sessionFingerprint 要移除的会话的会话指纹
     * @return 移除的成功的布尔值
     */
    public boolean removeSessionBySessionFingerprint(String sessionFingerprint) {
        return this.getDao().removeSessionBySessionFingerprint(sessionFingerprint);
    }

    /**
     * 设置会话为过期状态
     * @param sessionFingerprint 要设置过期状态的会话的指纹
     * @throws Exception
     */
    public boolean setSessionExpiredBySessionFingerprint(String sessionFingerprint) throws Exception {
        LESession leSession = this.getDao().getSessionBySessionFingerprint(sessionFingerprint);
        leSession.setState(LENSessionState.SIGN_OUT.getStateCode());
        leSession.setIp("none");
        return this.getDao().update(leSession);
    }

    /**
     * 通过会话指纹判断是否存在这个会话对象
     *
     * @param sessionFingerprint 要判断是否存在的会话的会话指纹
     * @return 是否存在这个会话的布尔值
     */
    public boolean isHaveTheSessionBySessionFingerprint(String sessionFingerprint) {
        return this.getDao().isHaveTheSessionBySessionFingerprint(sessionFingerprint);
    }

    /**
     * 重置所有会话的状态，该函数为ServletContextListener调用，设置所有的会话状态为已下线
     * @return 设置成功的布尔值
     */
    public void resetAllSessionState(){
        this.getDao().resetAllSessionState();
    }
}
