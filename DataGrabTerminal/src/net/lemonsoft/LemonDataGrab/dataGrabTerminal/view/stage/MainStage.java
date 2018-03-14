package net.lemonsoft.LemonDataGrab.dataGrabTerminal.view.stage;

import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.core.taskCommandParser.TaskCommandParserStage;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.tool.ResourceTool;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.tool.SystemInfoTool;

/**
 * 界面 - 登录后的主界面
 * Created by LiuRi on 6/4/16.
 */
public class MainStage extends TaskCommandParserStage {

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
    }

    private MainStage() {
        super();
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
                    setSize(770, 670);
                    setToScreenCenter();
                    setMinWidth(640);
                    setMinHeight(480);
                    setWindowTitle("任务控制台");
                    getBrowser().addScriptContextListener(new ScriptContextAdapter() {// 注入管理事件功能模块
                        @Override
                        public void onScriptContextCreated(ScriptContextEvent scriptContextEvent) {
                            super.onScriptContextCreated(scriptContextEvent);
                            JSValue window = getBrowser().executeJavaScriptAndReturnValue("window");
                            window.asObject().setProperty("func" , new MainButtonFunc());
                        }
                    });
                }
            }
        });
    }

    public class MainButtonFunc{
        public void signOutSuccess(){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    MainStage.sharedInstance().onIoSessionClosed();
                }
            });
        }
    }

}
