package net.lemonsoft.AdministratorTerminal.tool;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import net.lemonsoft.AdministratorTerminal.Main;
import net.lemonsoft.AdministratorTerminal.entity.TaskEntity;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 工具类 - mina框架相关的工具类
 * Created by LiuRi on 6/18/16.
 */
public class MinaTool {

    public static final String SESSION_SIGN_UP = "SESSION_SIGN_UP";
    public static final String SESSION_BIND_SUCCESS = "SESSION_BIND_SUCCESS";
    public static final String SESSION_SEND_TASK = "SESSION_SEND_TASK";
    public static final String SESSION_QUERY_LOCAL_TASK_LIST = "SESSION_QUERY_LOCAL_TASK_LIST";
    public static final String SESSION_QUERY_LOCAL_TASK_LIST_RESPONSE = "SESSION_QUERY_LOCAL_TASK_LIST_RESPONSE";
    public static final String SESSION_QUERY_LOCAL_TASK_DETAIL = "SESSION_QUERY_LOCAL_TASK_DETAIL";
    public static final String SESSION_QUERY_LOCAL_TASK_DETAIL_RESPONSE = "SESSION_QUERY_LOCAL_TASK_DETAIL_RESPONSE";

    private boolean isConnecting = false;
    private IoSession currentSession = null;
    private String currentSessionFingerprint = "";
    private MinaToolHandler handler = null;

    private static MinaTool minaTool = null;

    private MinaTool() {
        handler = new MinaToolHandler();
    }

    public static final MinaTool sharedInstance() {
        if (minaTool == null) {
            minaTool = new MinaTool();
        }
        return minaTool;
    }

    /**
     * 连接到服务器,并传入服务器的会话指纹
     *
     * @param sessionFingerprint
     */
    public void connectToServer(String sessionFingerprint) {
        if (!isConnecting) {
            // 当前没有连接
            isConnecting = true;
            IoConnector connector = new NioSocketConnector();
            connector.setConnectTimeoutMillis(10000);// 10s连接超时
            TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.MAC, LineDelimiter.MAC);
            textLineCodecFactory.setDecoderMaxLineLength(1024 * 1024 * 1000);
            textLineCodecFactory.setEncoderMaxLineLength(1024 * 1024 * 1000);
            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(textLineCodecFactory));
            connector.setHandler(handler);
            connector.connect(new InetSocketAddress(URLTool.URL_HOST, URLTool.URL_MINA_PORT));
            currentSessionFingerprint = sessionFingerprint;
        }
    }

    /**
     * 与服务器断开连接
     */
    public void disconnectToServer() {
        currentSession.closeNow();
    }

    /**
     * 向服务器发送文本数据
     *
     * @param isSuccess 是否成功
     * @param info      携带的信息
     * @param data      携带的数据键值对map
     */
    public void sendToServer(boolean isSuccess, String info, Map<String, Object> data) {
        if (isConnecting && currentSession != null) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("success", isSuccess);
            hashMap.put("info", info);
            hashMap.put("data", data);
            currentSession.write(new Gson().toJson(hashMap));
        }
    }

    /**
     * 向服务器发送成功的消息
     *
     * @param info 携带的信息
     * @param data 携带的数据键值对map
     */
    public void sendSuccessToServer(String info, Map<String, Object> data) {
        this.sendToServer(true, info, data);
    }

    /**
     * 向服务器发送失败的消息
     *
     * @param info 携带的信息
     * @param data 携带的数据键值对map
     */
    public void sendFailedToServer(String info, Map<String, Object> data) {
        this.sendToServer(false, info, data);
    }

    /**
     * 向服务器发送成功的消息
     *
     * @param info       携带的信息
     * @param dataKeys   携带的数据键数组
     * @param dataValues 携带的数据值数组
     */
    public void sendSuccessToServer(String info, String[] dataKeys, Object[] dataValues) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        if (dataKeys.length <= dataValues.length) {
            for (int i = 0; i < dataKeys.length; i++) {
                hashMap.put(dataKeys[i], dataValues[i]);
            }
            this.sendSuccessToServer(info, hashMap);
        }
    }

    /**
     * 向服务器发送失败的消息
     *
     * @param info       携带的信息
     * @param dataKeys   携带的数据键数组
     * @param dataValues 携带的数据值数组
     */
    public void sendFailedToServer(String info, String[] dataKeys, Object[] dataValues) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        if (dataKeys.length <= dataValues.length) {
            for (int i = 0; i < dataKeys.length; i++) {
                hashMap.put(dataKeys[i], dataValues[i]);
            }
            this.sendFailedToServer(info, hashMap);
        }
    }

    private class MinaToolHandler extends IoHandlerAdapter {

        @Override
        public void sessionClosed(IoSession session) throws Exception {
            super.sessionClosed(session);
            isConnecting = false;
            currentSession = null;
            currentSessionFingerprint = null;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Main.commandMain.defaultMainViewController.onIoSessionClosed();
                }
            });
        }

        @Override
        public void sessionOpened(IoSession session) throws Exception {
            super.sessionOpened(session);
            System.out.println("OPEN SESSION@!!!");
            currentSession = session;// 连接成功,保存session
            if (currentSessionFingerprint == null || currentSessionFingerprint.equals("")) {
                session.closeNow();
            } else {
                sendSuccessToServer(SESSION_SIGN_UP, new String[]{"isDataGrabTerminal", "sessionFingerprint"}, new Object[]{false, currentSessionFingerprint});
            }
        }

        @Override
        public void messageReceived(IoSession session, Object message) throws Exception {
            super.messageReceived(session, message);
            // 收到服务器发来的消息
            System.out.println("SERVER SAY: " + message);
            HashMap<String, Object> msgObject = new Gson().fromJson((String) message, new TypeToken<HashMap<String, Object>>() {
            }.getType());
            Boolean success = (Boolean) msgObject.get("success");
            String info = (String) msgObject.get("info");
            Map<String, Object> data = (LinkedTreeMap<String, Object>) msgObject.get("data");
            if (success) {
                switch (info) {
                    case SESSION_BIND_SUCCESS:// 收到登录会话绑定成功的通知
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Main.commandMain.defaultLoginViewController.onLoginSuccess();
                            }
                        });
                        break;
                    case SESSION_QUERY_LOCAL_TASK_LIST_RESPONSE:
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Main.commandMain.defaultMainViewController.defaultQueryLocalListViewController.onLoadSuccess(data);
                            }
                        });
                        break;
                    case SESSION_QUERY_LOCAL_TASK_DETAIL_RESPONSE:
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Main.commandMain.defaultMainViewController.defaultQueryLocalListViewController.onLoadTaskDetailSuccess(data);
                            }
                        });
                        break;
                    default:
                }
            }
        }

        @Override
        public void messageSent(IoSession session, Object message) throws Exception {
            super.messageSent(session, message);
            System.out.println("SAY TO SERVER:" + message);
        }

        @Override
        public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
            super.exceptionCaught(session, cause);
            System.out.println("MINA发生异常" + cause.getLocalizedMessage());

        }
    }

}