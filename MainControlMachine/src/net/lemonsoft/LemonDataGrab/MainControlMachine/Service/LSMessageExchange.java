package net.lemonsoft.LemonDataGrab.MainControlMachine.Service;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMOnlineSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lemonsoft on 2016/11/30.
 */
public class LSMessageExchange {

    // <消息Key , 消息的去向目标会话指纹>
    private final static Map<String, String> messageRouterPool = new HashMap<>();

    public static boolean containMessageKey(String msgKey) {
        return messageRouterPool.containsKey(msgKey);
    }

    public static LMOnlineSession getOnlineSessionByKey(String key) {
        return LSOnlineSession.sharedInstance().getTerminalBySessionFingerprint(messageRouterPool.get(key));
    }

    public static void put(String key, String aimSessionFingerprint) {
        messageRouterPool.put(key, aimSessionFingerprint);
    }

}
