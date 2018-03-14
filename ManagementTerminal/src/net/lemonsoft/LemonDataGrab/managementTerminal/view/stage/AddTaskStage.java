package net.lemonsoft.LemonDataGrab.managementTerminal.view.stage;

import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import net.lemonsoft.LemonDataGrab.managementTerminal.tool.ResourceTool;

/**
 * 界面 - 添加新任务界面
 * Created by lemonsoft on 2016/6/5.
 */
public class AddTaskStage extends WebStage {

    private static final Integer STAGE_WIDTH = 460;
    private static final Integer STAGE_HEIGHT = 640;

    private static final String ADD_TASK_STAGE_HTML_NAME = "module/AddTask/addTaskStage.html";

    private static AddTaskStage addTaskStage;

    public static AddTaskStage sharedInstance() {
        if (addTaskStage == null)
            addTaskStage = new AddTaskStage();
        return addTaskStage;
    }

    private AddTaskStage() {
        this.setWindowTitle("添加新任务");
        this.setResizable(false);
        this.setSize(STAGE_WIDTH, STAGE_HEIGHT);
        this.setToScreenCenter();
        this.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                initWithUrl("file://" + ResourceTool.sharedInstance().getResourcePath() + ADD_TASK_STAGE_HTML_NAME);
            }
        });
        this.getBrowser().addScriptContextListener(new ScriptContextAdapter() {
            @Override
            public void onScriptContextCreated(ScriptContextEvent scriptContextEvent) {
                super.onScriptContextCreated(scriptContextEvent);
                JSValue window = getBrowser().executeJavaScriptAndReturnValue("window");
                window.asObject().setProperty("addTask", new AddTaskHandler());
            }
        });

    }

    public class AddTaskHandler {
        /**
         * 添加任务成功的回调函数
         */
        public void success() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    close();
                    MainStage.sharedInstance().showInfo("任务添加成功!");
                }
            });
        }

        /**
         * 添加任务失败的回调函数
         *
         * @param errorReason 发生错误的原因
         */
        public void error(String errorReason) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    showError(String.format("任务添加失败,原因:%s", errorReason));
                }
            });
        }
    }

}
