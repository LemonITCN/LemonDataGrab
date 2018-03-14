package net.lemonsoft.LemonDataGrab.dataGrabTerminal.view.stage;

import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.tool.MinaTool;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.tool.ResourceTool;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.tool.SystemInfoTool;

/**
 * 自定义Stage - 登录
 * Created by LiuRi on 5/16/16.
 */
public class LoginStage extends WebStage {

    private static final String LOGIN_STAGE_HTML_NAME = "module/Login/loginStage.html";

    private final Integer LOGIN_STAGE_WIDTH = 340;// 登录界面宽度
    private final Integer LOGIN_STAGE_HEIGHT = 470;// 登录界面高度

    private static LoginStage loginStage = null;

    public static final LoginStage sharedInstance(){
        if (loginStage == null){
            loginStage = new LoginStage();
        }
        return loginStage;
    }

    private LoginStage() {
        super();
        this.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        this.setSize(LOGIN_STAGE_WIDTH, LOGIN_STAGE_HEIGHT);
        this.setToScreenCenter();
        this.setWindowTitle("登录");
        this.initWithUrl("file://" + ResourceTool.sharedInstance().getResourcePath() + LOGIN_STAGE_HTML_NAME);
        this.setResizable(false);
        this.getBrowser().addScriptContextListener(new ScriptContextAdapter() {
            @Override
            public void onScriptContextCreated(ScriptContextEvent scriptContextEvent) {
                super.onScriptContextCreated(scriptContextEvent);
                JSValue window = getBrowser().executeJavaScriptAndReturnValue("window");
                window.asObject().setProperty("login", new LoginDeal());
            }
        });
    }

    public void onLoginSuccess(){
        MainStage.sharedInstance().show();
        close();
    }

    public class LoginDeal {

        public void success() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    MinaTool.sharedInstance().connectToServer(SystemInfoTool.sharedInstance().getSessionFingerprint());
                }
            });
        }

        public void fail() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    showError("登录失败,无法连接到服务器!");
                }
            });
        }

        public void failInfo(String info) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    showError(info);
                }
            });
        }

    }

}
