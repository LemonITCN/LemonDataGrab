package net.lemonsoft.LemonDataGrab.MainControlMachine.Handler;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LESession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENSessionState;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMOnlineSession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSOnlineSession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSSession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LULog;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.HashMap;

/**
 * 处理类 -  mina相关
 * Created by LiuRi on 6/18/16.
 */
public class LHMina extends IoHandlerAdapter {

    public static final String SESSION_SIGN_UP = "SESSION_SIGN_UP";
    public static final String SESSION_BIND_SUCCESS = "SESSION_BIND_SUCCESS";
    public static final String SESSION_SEND_TASK = "SESSION_SEND_TASK";
    public static final String SESSION_QUERY_LOCAL_TASK_LIST = "SESSION_QUERY_LOCAL_TASK_LIST";
    public static final String SESSION_QUERY_LOCAL_TASK_LIST_RESPONSE = "SESSION_QUERY_LOCAL_TASK_LIST_RESPONSE";
    public static final String SESSION_QUERY_LOCAL_TASK_DETAIL = "SESSION_QUERY_LOCAL_TASK_DETAIL";
    public static final String SESSION_QUERY_LOCAL_TASK_DETAIL_RESPONSE = "SESSION_QUERY_LOCAL_TASK_DETAIL_RESPONSE";

    private LSSession lsSession;

    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("SESSION CREATED!!");
    }

    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        System.out.println("SESSION OEPNED!!!");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {

        super.sessionIdle(session, status);
        try {
            if (!LSOnlineSession.sharedInstance().containTheSession(session)) {
                session.closeNow();// 未注册设备,关闭连接
            }
        } catch (Exception e) {
            e.printStackTrace();
            LULog.info(String.format("一个连接,IP:%s,因长时间未发送会话指纹而被断开", session.getRemoteAddress()));
            session.closeNow();
        }
    }

    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        String sessionFingerprint = LSOnlineSession.sharedInstance().getSessionFingerprintBySession(session);
        try {
            if (sessionFingerprint != null) {
                LSOnlineSession.sharedInstance().removeSessionBySessionFingerprint(sessionFingerprint);
                if (lsSession == null)
                    lsSession = new LSSession();
                lsSession.setSessionExpiredBySessionFingerprint(sessionFingerprint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LULog.info(String.format("IP:%s 的终端会话已经断开!", session.getRemoteAddress()));
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        System.out.println("MESSAGE SENT!!!");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        LULog.info("[收到来自客户端的消息] " + message);
        HashMap<String, Object> msgObject = new Gson().fromJson((String) message, new TypeToken<HashMap<String, Object>>() {
        }.getType());
        Boolean success = (Boolean) msgObject.get("success");
        String info = (String) msgObject.get("info");
        LinkedTreeMap<String, Object> data = (LinkedTreeMap<String, Object>) msgObject.get("data");
        if (success) {
            // 成功信息
            switch (info) {
                case SESSION_SIGN_UP:
                    if (!LSOnlineSession.sharedInstance().
                            containTheTerminalBySessionFingerprint((String) data.get("sessionFingerprint"))) {
                        // 当前这个会话指纹没有绑定过长连接终端
                        LMOnlineSession lmOnlineSession = new LMOnlineSession();
                        lmOnlineSession.setSession(session);
                        lmOnlineSession.setSessionFingerprint((String) data.get("sessionFingerprint"));
                        LSOnlineSession.sharedInstance().outSuccessOnSession(session, SESSION_BIND_SUCCESS, new HashMap<String, Object>());
                        LULog.info("已经批准了指定设备登录,IP:" + session.getRemoteAddress());
                        if (lsSession == null)
                            lsSession = new LSSession();
                        LESession leSession = lsSession.getSessionBySessionFingerprint((String) data.get("sessionFingerprint"));
                        leSession.setIp(lmOnlineSession.getSession().getRemoteAddress().toString());
                        if ((Boolean) data.get("isDataGrabTerminal")) {// 数据采集终端
                            LSOnlineSession.sharedInstance().addDataGrabTerminal(lmOnlineSession);
                            leSession.setState(LENSessionState.SIGN_IN_DATA_GRAB.getStateCode());
                        } else {// 管理终端
                            LSOnlineSession.sharedInstance().addManagementTerminal(lmOnlineSession);
                            leSession.setState(LENSessionState.SIGN_IN_ADMINISTRATOR.getStateCode());
                        }
                        lsSession.update(leSession);
                    } else {
                        // 这个会话指纹绑定过一个会话终端,那么断开
                        session.closeNow();
                    }
                    break;
                default:
            }
        }
    }
}
