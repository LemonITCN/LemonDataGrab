package net.lemonsoft.LemonDataGrab.managementTerminal;

import com.teamdev.jxbrowser.chromium.BrowserCore;
import com.teamdev.jxbrowser.chromium.internal.Environment;
import javafx.application.Application;
import javafx.stage.Stage;
import net.lemonsoft.LemonDataGrab.managementTerminal.view.stage.LoginStage;

public class Main extends Application {

    public void init() throws Exception {
        // On Mac OS X Chromium engine must be initialized in non-UI thread.
        if (Environment.isMac()) {
            BrowserCore.initialize();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginStage loginStage = LoginStage.sharedInstance();
        loginStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
