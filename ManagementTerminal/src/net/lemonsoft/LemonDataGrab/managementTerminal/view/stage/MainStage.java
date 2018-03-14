package net.lemonsoft.LemonDataGrab.managementTerminal.view.stage;

import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import net.lemonsoft.LemonDataGrab.managementTerminal.tool.ResourceTool;
import net.lemonsoft.LemonDataGrab.managementTerminal.tool.SystemInfoTool;

/**
 * 界面 - 登录后的主界面
 * Created by LiuRi on 6/4/16.
 */
public class MainStage extends WebStage {

    private static final String MAIN_STAGE_HTML_NAME = "module/Main/mainStage.html";

    private static MainStage mainStage;

    public synchronized static MainStage sharedInstance(){
        if (mainStage == null)
            mainStage = new MainStage();
        return mainStage;
    }

    /**
     * 长连接会话被关闭的时候被触发
     */
    public void onIoSessionClosed() {
        LoginStage.sharedInstance().show();
        close();
        AddTaskStage.sharedInstance().close();
    }

    private MainStage() {
        super();
        this.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        this.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (SystemInfoTool.sharedInstance().getSessionFingerprint() == "") {
                    // 没有登录,找不到会话指纹
                    showError("无法检测到您的登录身份信息,请您重新登录!");
                    LoginStage.sharedInstance().show();
                    close();
                } else {
                    // 登录成功
                    initWithUrl("file://" + ResourceTool.sharedInstance().getResourcePath() + MAIN_STAGE_HTML_NAME);
                    setSize(640, 480);
                    setMinWidth(480);
                    setMinHeight(320);
                    setWindowTitle("管理");
                    getBrowser().addScriptContextListener(new ScriptContextAdapter() {// 注入管理事件功能模块
                        @Override
                        public void onScriptContextCreated(ScriptContextEvent scriptContextEvent) {
                            super.onScriptContextCreated(scriptContextEvent);
                            JSValue window = getBrowser().executeJavaScriptAndReturnValue("window");
                            window.asObject().setProperty("topControlAction" , new TopControlButtonAction());
                        }
                    });

                }
            }
        });
    }

    /**
     * 顶部控制面板按钮的事件类
     */
    public class TopControlButtonAction{

        /**
         * 添加一个新任务，调用添加新任务Stage
         */
        public void addTask(){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AddTaskStage.sharedInstance().show();
                }
            });
        }

    }

}
