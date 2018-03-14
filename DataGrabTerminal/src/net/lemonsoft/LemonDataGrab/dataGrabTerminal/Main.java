package net.lemonsoft.LemonDataGrab.dataGrabTerminal;

import com.teamdev.jxbrowser.chromium.BrowserCore;
import com.teamdev.jxbrowser.chromium.internal.Environment;
import javafx.application.Application;
import javafx.stage.Stage;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.model.TaskModel;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.view.stage.LoginStage;

import java.util.UUID;

public class Main extends Application {

    public void init() throws Exception {
        // On Mac OS X Chromium engine must be initialized in non-UI thread.
        if (Environment.isMac()) {
            BrowserCore.initialize();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Browser browser = new Browser();
//        BrowserView view = new BrowserView(browser);
//
//        Scene scene = new Scene(new BorderPane(view), 700, 500);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//        browser.loadURL("http://www.taobao.com");

        TaskModel taskModel = new TaskModel();
        taskModel.setIdentity(UUID.randomUUID().toString());
        taskModel.setName("测试任务");
        taskModel.setDescription("这是一个测试的任务");

//        TaskStage taskStage = new TaskStage();
//        taskStage.show();
//
//        DataGrabStage dataGrabStage = new DataGrabStage(taskModel);
//        dataGrabStage.show();

        LoginStage.sharedInstance().show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
