package net.lemonsoft.LemonDataGrab.MainControlMachine.Api;

import com.sun.org.apache.regexp.internal.RE;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANApi;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LESession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEUser;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEUserGroup;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENResponseError;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENSessionState;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENSystemUserGroupId;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMData;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMInfo;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Processor.LPClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Processor.LPUser;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.*;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUString;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

/**
 * API - 用户
 * Created by LiuRi on 16/5/11.
 */
public class LAUser extends LA<LSUser> {

    @LANApi(URL = "/User/SignUp",
            parameters = {"number", "name", "password", "clientFingerprint"},
            parameterIntros = {"用户的账号,可以是邮箱,手机号码,自定义账号", "用户的真实姓名", "用户的密码", "客户端指纹"},
            description = "注册用户的接口",
            errors = {1002, 1003, 2001})
    public Object signUpUser(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) {
        String number = (String) parameters.get("number");
        String name = (String) parameters.get("name");
        String password = (String) parameters.get("password");
        String clientFingerprint = (String) parameters.get("clientFingerprint");
        if (this.checkClientFingerprintValidity(clientFingerprint)) {
            // 客户端指纹存在
            if (this.getService().isHaveTheNumber(number)) {
                // 账号已经被注册过
                return LENResponseError.USER_SIGNUP_NUMBER_ALREADY_EXISTS;
            } else {
                // 账号不存在,开始注册
                LEUser leUser = new LEUser();
                this.getService().setUserNumber(leUser, number);
                leUser.setName(name);
                leUser.setUsergroup(LENSystemUserGroupId.BASIC_USER_GROUP_ID.getGroupId());
                leUser.setScore(0);
                leUser.setPassword(LUString.md5(password));
                leUser.setRegistrationTime(LUTime.getUnixTimeStamp());
                leUser.setIdentity(UUID.randomUUID().toString());
                if (this.getService().add(leUser)) {
                    // 插入数据成功 , 注册成功
                    return new LMInfo(String.format("注册成功,欢迎您,%s", name));
                } else {
                    // 插入数据库失败
                    return LENResponseError.REQUEST_SYSTEM_EXCEPTION;// 返回系统错误
                }
            }
        }
        return LENResponseError.REQUEST_CLIENT_FINGERPRINT_INVALID;
    }

    private static final String DEFAULT_USER_PASSWORD = "ldg123456";

    @LANApi(URL = "/User/AddUser",
            parameters = {"number", "name", "userGroup", "sessionFingerprint"},
            parameterIntros = {"用户的账号,可以是邮箱,手机号码,自定义账号", "用户的真实姓名", "新用户的所属用户组ID", "会话指纹,此接口为管理员专用,因此字段必须传入管理员的会话指纹"},
            description = "管理员添加用户的接口,该接口允许添加管理员用户组等任何用户组的用户,默认的密码为ldg123456",
            errors = {1002, 1003, 2001})
    public Object addUser(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            String number = (String) parameters.get("number");
            if (this.getService().isHaveTheNumber(number)) {
                // 账号已经被注册过
                return LENResponseError.USER_SIGNUP_NUMBER_ALREADY_EXISTS;
            } else {
                // 账号不存在,开始注册
                String name = (String) parameters.get("name");
                Long userGroupId = Long.parseLong((String) parameters.get("userGroup"));
                LEUser leUser = new LEUser();
                this.getService().setUserNumber(leUser, number);
                leUser.setName(name);
                leUser.setUsergroup(userGroupId);
                leUser.setScore(0);
                leUser.setPassword(LUString.md5(DEFAULT_USER_PASSWORD));
                leUser.setRegistrationTime(LUTime.getUnixTimeStamp());
                leUser.setIdentity(UUID.randomUUID().toString());
                if (this.getService().add(leUser)) {
                    // 插入数据成功 , 注册成功
                    return new LMInfo(String.format("注册成功,欢迎您,%s", name));
                } else {
                    // 插入数据库失败
                    return LENResponseError.REQUEST_SYSTEM_EXCEPTION;// 返回系统错误
                }
            }
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

    @LANApi(URL = "/User/SignIn",
            parameters = {"number", "password", "clientFingerprint", "isAdministrator"},
            parameterIntros = {"用户的账号,可以是邮箱,手机号码,自定义账号", "用户的真实姓名", "用户的密码", "客户端指纹"},
            description = "用户登录的接口",
            errors = {1002, 1003, 2000})
    public Object signInUser(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        String number = (String) parameters.get("number");
        String password = (String) parameters.get("password");
        String clientFingerprint = (String) parameters.get("clientFingerprint");
        String isAdministrator = (String) parameters.get("isAdministrator");
        if (this.checkClientFingerprintValidity(clientFingerprint)) {
            // 客户端指纹存在
            if (this.getService().isHaveTheNumber(number)) {
                // 存在这个账号,开始登录过程
                if (!this.getService().checkNumberAndPassword(number, password)) {// 验证账号密码
                    // 账号密码不匹配
                    return LENResponseError.USER_SIGNIN_NUMBER_OR_PASSWORD_ERROR;
                }
                LSSession lsSession = new LSSession();
                LEUser leUser = this.getService().getUserByNumber(number);
                LESession leSession = lsSession.getSessionByClientFingerprintAndUid(clientFingerprint, leUser.getUid());
                if (isAdministrator.equals("1") && (leUser.getUsergroup() != LENSystemUserGroupId.ADMINISTRATOR_GROUP_ID.getGroupId())) {
                    // 要登录的是管理员用户,并且登录的用户的用户组不是管理员,那么返回用户非法信息
                    return LENResponseError.REQUEST_PERMISSION_INVALID;
                }
                if (leSession != null) {
                    // 这个客户端登录过这个用户,存在这个会话,那么直接返回
                    return new LMInfo(leSession.getSessionFingerprint());
                }
                // 这个客户端没有登录过这个用户,那么继续登录流程,创建新的会话对象
                leSession = new LESession();
                leSession.setSessionFingerprint(UUID.randomUUID().toString());
                leSession.setClient(this.getClientByClientFingerprint(clientFingerprint).getCid());
                leSession.setUser(this.getService().getUserByNumber(number).getUid());
                Long currentTime = LUTime.getUnixTimeStamp();
                leSession.setSetupTime(currentTime);
                leSession.setLastCommunicationTime(currentTime);
                leSession.setState(LENSessionState.SIGN_OUT.getStateCode());
                if (new LSSession().add(leSession)) {
                    // 会话创建成功
                    return new LMInfo(leSession.getSessionFingerprint());
                } else {
                    // 会话创建失败
                    return LENResponseError.REQUEST_SYSTEM_EXCEPTION;
                }
            } else {
                // 不存在这个账号
                return LENResponseError.USER_SIGNIN_NUMBER_OR_PASSWORD_ERROR;
            }
        } else {
            // 客户端指纹不存在
            return LENResponseError.REQUEST_CLIENT_FINGERPRINT_INVALID;
        }
    }

    @LANApi(URL = "/User/SignOut",
            parameters = {"sessionFingerprint"},
            parameterIntros = {"会话的指纹,要注销的会话的会话指纹"},
            description = "用户注销接口",
            errors = {1002, 1004})
    public Object signOutUser(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        String sessionFingerprint = (String) parameters.get("sessionFingerprint");
        LSSession lsSession = new LSSession();
        LESession leSession = lsSession.getSessionBySessionFingerprint(sessionFingerprint);
        if (leSession == null) {// 会话指纹已经过期
            return LENResponseError.REQUEST_SESSION_FINGERPRINT_INVALID;
        }
        if (lsSession.setSessionExpiredBySessionFingerprint(sessionFingerprint)) {
            LSOnlineSession.sharedInstance().removeSessionBySessionFingerprint(sessionFingerprint);
            return new LMInfo("注销成功");
        }
        return LENResponseError.REQUEST_SYSTEM_EXCEPTION;// 无法注销,返回系统错误
    }

    @LANApi(URL = "/User/GetInfo",
            parameters = {"sessionFingerprint"},
            parameterIntros = {"会话的指纹,要获取的用户的会话指纹"},
            description = "获取当前登录用户基本信息的接口",
            errors = {1002, 1004})
    public Object getUserInfo(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        String sessionFingerprint = (String) parameters.get("sessionFingerprint");
        LSSession lsSession = new LSSession();
        LESession leSession = lsSession.getSessionBySessionFingerprint(sessionFingerprint);
        if (leSession == null) {// 会话指纹已经过期
            return LENResponseError.REQUEST_SESSION_FINGERPRINT_INVALID;
        }
        LSUser lsUser = new LSUser();
        LEUser leUser = lsUser.getByPrimaryKey(leSession.getUser());
        if (leUser == null)
            return LENResponseError.REQUEST_SYSTEM_EXCEPTION;
        LSUserGroup lsUserGroup = new LSUserGroup();
        LEUserGroup leUserGroup = lsUserGroup.getByPrimaryKey(leUser.getUsergroup());
        if (leUserGroup == null)
            return LENResponseError.REQUEST_SYSTEM_EXCEPTION;
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", leUser.getUid());
        data.put("phone", leUser.getPhone());
        data.put("email", leUser.getEmail());
        data.put("username", leUser.getUsername());
        data.put("name", leUser.getName());
        data.put("userGroupName", leUserGroup.getName());
        data.put("score", leUser.getScore());
        return new LMData(data);
    }

    @LANApi(URL = "/User/GetSessionInfoWithId",
            parameters = {"sessionFingerprint", "sessionId"},
            parameterIntros = {"会话的指纹,当前登录的管理员的会话指纹", "要查询的会话信息的id"},
            description = "查询指定会话id的会话信息",
            errors = {1005})
    public Object GetSessionInfoWithId(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            HashMap<String, Object> result = new HashMap<>();
            LESession leSession = new LSSession().getByPrimaryKey(Long.valueOf((String) parameters.get("sessionId")));
            leSession.setSessionFingerprint("");// 置空会话指纹
            LEClient leClient = new LSClient().getByPrimaryKey(leSession.getClient());
            LEUser leUser = new LSUser().getByPrimaryKey(leSession.getUser());
            LPUser.clearSensitiveData(leUser);
            LPClient.clearSensitiveData(leClient);
            result.put("session", leSession);
            result.put("user", leUser);
            result.put("client", leClient);
            return new LMData(result);
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

    @LANApi(URL = "/User/GetUserList",
            parameters = {"sessionFingerprint", "startIndex", "count"},
            parameterIntros = {"会话的指纹,要获取用户信息列表的用户会话的会话指纹", "开始获取数据的索引", "获取数据的数量"},
            description = "分页获取用户信息列表",
            errors = {1005})
    public Object getUserInfoList(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            return this.getService().getUserInfoList((String) parameters.get("startIndex"), (String) parameters.get("count"));
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

    @LANApi(URL = "/User/CountAll",
            parameters = {"sessionFingerprint"},
            parameterIntros = {"会话的指纹,要获取用户总数量的用户会话的会话指纹"},
            description = "获取当前所有用户的总数量",
            errors = {1005})
    public Object coutAllUser(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            return new LMData(this.getService().countAllUser());
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }
}