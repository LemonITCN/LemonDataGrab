package net.lemonsoft.AdministratorTerminal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.lemonsoft.AdministratorTerminal.viewController.AddUserViewController;
import net.lemonsoft.AdministratorTerminal.viewController.LoginViewController;
import net.lemonsoft.AdministratorTerminal.viewController.MainViewController;

import java.io.IOException;

public class Main extends Application {

    public static Main commandMain;

    public Stage defaultLoginStage;
    public Stage defaultMainStage;
    public Stage defaultAddUserStage;

    public LoginViewController defaultLoginViewController;
    public MainViewController defaultMainViewController;
    public AddUserViewController defaultAddUserViewController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        commandMain = this;
        Main.commandMain.createLogin();
        Main.commandMain.createMain();
        Main.commandMain.createAddUser();
        this.defaultLoginStage.show();
    }

    /**
     * 创建登录界面
     */
    public void createLogin(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/Login.fxml"));
        Scene rootScene = null;
        try {
            rootScene = new Scene(loader.load());
            rootScene.getStylesheets().add(getClass().getResource("style/Login.css").toString());
            this.defaultLoginStage = new Stage();
            this.defaultLoginStage.setScene(rootScene);
            this.defaultLoginStage.setTitle("登录 - 分布式网络数据采集器[管理端]");
            this.defaultLoginStage.setResizable(false);
            this.defaultLoginViewController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建主界面
     */
    public void createMain(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/Main.fxml"));
        Scene rootScene = null;
        try {
            rootScene = new Scene(loader.load());
            rootScene.getStylesheets().add(getClass().getResource("style/Main.css").toString());
            this.defaultMainStage = new Stage();
            this.defaultMainStage.setScene(rootScene);
            this.defaultMainStage.setTitle("主界面 - 分布式网络数据采集器[管理端]");
            this.defaultMainStage.setResizable(false);
            this.defaultMainViewController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建添加用户的视图控制器
     */
    public void createAddUser(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/AddUser.fxml"));
        Scene rootScene = null;
        try {
            rootScene = new Scene(loader.load());
            rootScene.getStylesheets().add(getClass().getResource("style/AddUser.css").toString());
            this.defaultAddUserStage = new Stage();
            this.defaultAddUserStage.setScene(rootScene);
            this.defaultAddUserStage.setTitle("添加用户 - 分布式网络数据采集器[管理端]");
            this.defaultAddUserStage.setResizable(false);
            this.defaultAddUserViewController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args );
    }
}
