package net.lemonsoft.LemonDataGrab.dataGrabTerminal.view.stage;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.tool.ConfigTool;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.tool.MinaTool;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.tool.SystemInfoTool;

import java.awt.*;

/**
 * Stage - WebStage
 * Created by LiuRi on 5/18/16.
 */
public class WebStage extends Stage {

    private Browser browser;
    private BrowserView browserView;
    private Scene scene;
    public javafx.stage.Window selfWindow;

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public Browser getBrowser() {
        return browser;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public BrowserView getBrowserView() {
        return browserView;
    }

    public void setBrowserView(BrowserView browserView) {
        this.browserView = browserView;
    }

    public WebStage() {
        super();
        this.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                setToScreenCenter();
            }
        });
        this.selfWindow = this;
        this.browser = new Browser();
        this.browserView = new BrowserView(this.browser);
        this.scene = new Scene(this.browserView);
        this.setScene(scene);
        this.browser.addScriptContextListener(new ScriptContextAdapter() {
            @Override
            public void onScriptContextCreated(ScriptContextEvent scriptContextEvent) {
                super.onScriptContextCreated(scriptContextEvent);
                JSValue window = browser.executeJavaScriptAndReturnValue("window");
                window.asObject().setProperty("config", ConfigTool.sharedInstance());
                window.asObject().setProperty("console", new jsConsole());
                window.asObject().setProperty("system", SystemInfoTool.sharedInstance());
                window.asObject().setProperty("mina", MinaTool.sharedInstance());
                window.asObject().setProperty("alert" , new AlertHandler());
                window.asObject().setProperty("core" , new core());
            }
        });
    }

    public class core{
        public void evalJS(String jsCode){
            browser.executeJavaScript(jsCode);
        }
    }

    public class jsConsole {
        public void log(String message) {
            System.out.println("[JS LOG] " + message);
        }

        public void error(String message) {
            System.err.println("[JS ERR] " + message);
        }
    }

    /**
     * 设置窗口的大小
     *
     * @param width  窗口的宽
     * @param height 窗口的高
     */
    public void setSize(double width, double height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    /**
     * 设置窗口的位置
     *
     * @param x 窗口的X坐标
     * @param y 窗口的Y坐标
     */
    public void setLocation(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * 设置窗体信息,长、宽、坐标
     *
     * @param width  窗体的宽度
     * @param height 窗体的高度
     * @param x      X坐标
     * @param y      Y坐标
     */
    public void setFrame(double width, double height, double x, double y) {
        this.setSize(width, height);
        this.setLocation(x, y);
    }

    /**
     * 加载指定的URL地址
     *
     * @param url 要加载的URL地址
     */
    public void initWithUrl(String url) {
        this.browser.loadURL(url);
    }

    /**
     * 获取屏幕的宽度
     *
     * @return 屏幕宽度的数值
     */
    public double getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    }

    /**
     * 获取屏幕的高度
     *
     * @return 屏幕高度的数值
     */
    public double getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    }

    /**
     * 设置当前窗口位于屏幕中心
     */
    public void setToScreenCenter() {
        this.setLocation((this.getScreenWidth() - this.getWidth()) / 2, (this.getScreenHeight() - this.getHeight()) / 2);
    }

    /**
     * 设置窗口的标题
     *
     * @param title 设置窗口的标题
     */
    public void setWindowTitle(String title) {
        this.setTitle(String.format("%s %s", title, SystemInfoTool.sharedInstance().getAppName()));
    }

    /**
     * 展示一个错误信息提示框
     *
     * @param message 要提示的错误信息
     */
    public void showError(String message) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * 展示一个信息提示框
     *
     * @param message 要提示的信息
     */
    public void showInfo(String message) {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * 展示一个警告对话框
     *
     * @param message 要提示的警告信息
     */
    public void showWarning(String message) {
        alert.setAlertType(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * 展示等待中的信息
     *
     * @param message 要提示的等待中的信息
     */
    public void showWaiting(String message) {
        Label waitLabel = new Label(message);
        waitLabel.setAlignment(Pos.CENTER);
        Scene waitScene = new Scene(waitLabel);
        this.setScene(waitScene);
    }

    /**
     * 隐藏等待信息
     */
    public void hideWaiting() {
        this.setScene(this.scene);
    }

    public class AlertHandler{
        public void showWaiting(String message){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    showWaiting(message);
                }
            });
        }

        public void hideWaiting(){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    hideWaiting();
                }
            });
        }

        public void showError(String message){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    showError(message);
                }
            });
        }

        public void showWarning(String message){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    showWaiting(message);
                }
            });
        }

        public void showInfo(String message){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    showInfo(message);
                }
            });
        }
    }

}