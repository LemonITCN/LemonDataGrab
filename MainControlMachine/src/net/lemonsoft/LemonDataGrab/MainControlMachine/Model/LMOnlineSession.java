package net.lemonsoft.LemonDataGrab.MainControlMachine.Model;

import org.apache.mina.core.session.IoSession;

/**
 * 模型类 - 在线会话对象
 * Created by LiuRi on 6/19/16.
 */
public class LMOnlineSession extends LM {

    /**
     * 会话指纹
     */
    private String sessionFingerprint;

    /**
     * 在线会话对象
     */
    private IoSession session;

    public String getSessionFingerprint() {
        return sessionFingerprint;
    }

    public void setSessionFingerprint(String sessionFingerprint) {
        this.sessionFingerprint = sessionFingerprint;
    }

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }
}
