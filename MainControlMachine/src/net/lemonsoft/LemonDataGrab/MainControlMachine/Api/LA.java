package net.lemonsoft.LemonDataGrab.MainControlMachine.Api;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LESession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEUser;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEUserGroup;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENSystemUserGroupId;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * API - 所有API的父类,所有的API都直接或间接的继承自本类
 * Created by LiuRi on 16/5/8.
 */
public class LA<ST> {

    private Class<ST> serviceClass;

    public LA() {
        this.serviceClass = null;
        Class clazz = this.getClass();
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            this.serviceClass = (Class<ST>) types[0];
        }
    }

    /**
     * 获取Service层的实例对象
     *
     * @return 对应的Service层实例对象
     */
    public ST getService() {
        try {
            return serviceClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查指定的客户端指纹是否有效,即是否存在
     *
     * @param clientFingerprint 要检查的客户端指纹
     * @return 检查结果的布尔值
     */
    public boolean checkClientFingerprintValidity(String clientFingerprint) {
        return new LSClient().isHaveClientByClientFingerprint(clientFingerprint);
    }

    /**
     * 获取指定的客户端指纹的客户端对象
     *
     * @param clientFingerprint 客户端指纹
     * @return 客户端对象
     * @throws Exception
     */
    public LEClient getClientByClientFingerprint(String clientFingerprint) throws Exception {
        return new LSClient().getClientByClientFingerprint(clientFingerprint);
    }

    /**
     * 通过会话指纹获取对应的用户对象
     *
     * @param sessionFingerprint 要获取对应用户的会话指纹
     * @return 获取到的用户对象
     * @throws Exception
     */
    public LEUser getUserBySessionFingerprint(String sessionFingerprint) throws Exception {
        LSSession lsSession = new LSSession();
        LESession leSession = lsSession.getSessionBySessionFingerprint(sessionFingerprint);
        LSUser lsUser = new LSUser();
        LEUser leUser = lsUser.getByPrimaryKey(leSession.getUser());
        return leUser;
    }

    /**
     * 通过会话指纹获取指定的会话对象
     * @param sessionFingerprint 要获取的会话的会话指纹
     * @return 获取到的会话对象
     * @throws Exception
     */
    public LESession getSessionBySessionFingerprint(String sessionFingerprint) throws Exception {
        LSSession lsSession = new LSSession();
        return lsSession.getSessionBySessionFingerprint(sessionFingerprint);
    }

    /**
     * 判断指定的会话指纹对应的用户是否为管理员用户
     * @param sessionFingerprint 要判断身份的会话指纹
     * @return 是否为管理员的布尔值
     */
    public boolean checkIsAdministratorBySessionFingerprint(String sessionFingerprint) {
        try {
            LEUser leUser = this.getUserBySessionFingerprint(sessionFingerprint);
            LESession leSession  = this.getSessionBySessionFingerprint(sessionFingerprint);
            return Objects.equals(leUser.getUsergroup(), LENSystemUserGroupId.ADMINISTRATOR_GROUP_ID.getGroupId())
                    && leSession.getState() > 0;// 是管理员用户组并且当前处于在线状态
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkIsAdministratorBySessionFingerprintNoOnline(String sessionFingerprint) {
        try {
            LEUser leUser = this.getUserBySessionFingerprint(sessionFingerprint);
            LESession leSession  = this.getSessionBySessionFingerprint(sessionFingerprint);
            return Objects.equals(leUser.getUsergroup(), LENSystemUserGroupId.ADMINISTRATOR_GROUP_ID.getGroupId());// 是管理员用户组并且当前处于在线状态
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}