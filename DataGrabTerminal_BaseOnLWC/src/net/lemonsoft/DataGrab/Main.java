package net.lemonsoft.DataGrab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.lemonsoft.DataGrab.tool.HttpTool;
import net.lemonsoft.DataGrab.tool.URLTool;
import net.lemonsoft.DataGrab.viewController.LoginViewController;
import net.lemonsoft.DataGrab.viewController.MainViewController;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static Main commandMain;

    public Stage defaultLoginStage;
    public Stage defaultMainStage;

    public LoginViewController defaultLoginViewController;
    public MainViewController defaultMainViewController;

    @Override
    public void start(Stage primaryStage) throws Exception {
//        MainManager manager = MainManager.defaultManager();
//        SubController controller = manager.createSubController();
//        controller.setCommunicationHandler("task", new CommunicationHandler() {
//            @Override
//            public Object onMessage(Object data) {
//                System.out.println("GET:" + data);
//                return "receiveOK";
//            }
//        });
//        Tty tty = controller.getConsole().getDefaultTty();
//        tty.executeJavaScript("var a = new Browser();a.show();a.operate.loadURL('http://www.taobao.com' , function(){var re = a.dataGet.getImgDomURL('body > div.cup.J_Cup > div.screen.J_Screen > div.sa-rect > div > div.core.J_Core > div.ca-extra > div.tbh-member.J_Module.tbh-loaded.member-bg-default > div > div.member-bd > a > img');Communication.call('task',re);DataCollection.put('imgSrc',re);} , function(){console.log('failed');});");

        commandMain = this;
        Main.commandMain.createLogin();
        Main.commandMain.createMain();
        this.defaultLoginStage.show();
//        HttpTool.download("/Users/liuri/Documents/a.db", "http://localhost:8080/dl?fingerprint=9f264607-9f0a-4f52-90ec-41cfe0920e3f_0");
    }

    /**
     * 创建登录界面
     */
    public void createLogin() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/Login.fxml"));
        Scene rootScene = null;
        try {
            rootScene = new Scene(loader.load());
            rootScene.getStylesheets().add(getClass().getResource("style/Login.css").toString());
            this.defaultLoginStage = new Stage();
            this.defaultLoginStage.setScene(rootScene);
            this.defaultLoginStage.setTitle("登录 - 分布式网络数据采集器[采集端]");
            this.defaultLoginStage.setResizable(false);
            this.defaultLoginViewController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建主界面
     */
    public void createMain() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/Main.fxml"));
        Scene rootScene = null;
        try {
            rootScene = new Scene(loader.load());
            rootScene.getStylesheets().add(getClass().getResource("style/Main.css").toString());
            this.defaultMainStage = new Stage();
            this.defaultMainStage.setScene(rootScene);
            this.defaultMainStage.setTitle("主界面 - 分布式网络数据采集器[采集端]");
            this.defaultMainStage.setResizable(false);
            this.defaultMainViewController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void up() {
//        HttpClient httpclient = new DefaultHttpClient();
//        try {
//            HttpPost httppost = new HttpPost(URLTool.URL_RESULT_RESPONSE);
//            FileBody data = new FileBody(new File("/Users/lemonsoft/lwc_data/7b397f01-69a1-47e6-843d-f3552939a8bd_1.csv"));
//            FileBody log = new FileBody(new File("/Users/lemonsoft/lwc_data/7b397f01-69a1-47e6-843d-f3552939a8bd_1_log.csv"));
//            StringBody sessionFingerprint = new StringBody("ee533376-06d3-4bfb-bb40-af5f4292d562");
//            StringBody fingerprint = new StringBody("7b397f01-69a1-47e6-843d-f3552939a8bd_23");
//            StringBody state = new StringBody("1");
//            MultipartEntity reqEntity = new MultipartEntity();
//            reqEntity.addPart("data", data);//file1为请求后台的File upload;属性
//            reqEntity.addPart("log", log);//file2为请求后台的File upload;属性
//            reqEntity.addPart("sessionFingerprint", sessionFingerprint);//filename1为请求后台的普通参数;属性
//            reqEntity.addPart("fingerprint", fingerprint);//filename1为请求后台的普通参数;属性
//            reqEntity.addPart("state", state);//filename1为请求后台的普通参数;属性
//            httppost.setEntity(reqEntity);
//            HttpResponse response = httpclient.execute(httppost);
//            int statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode == HttpStatus.SC_OK) {
//                System.out.println("服务器正常响应.....");
//                HttpEntity resEntity = response.getEntity();
//                System.out.println(" = = = = =  11 = " + EntityUtils.toString(resEntity));//httpclient自带的工具类读取返回数据
//                System.out.println(" = = = = = = " + resEntity.getContent());
//                EntityUtils.consume(resEntity);
//            }
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            try {
//                httpclient.getConnectionManager().shutdown();
//            } catch (Exception ignore) {
//
//            }
//        }
    }

//    public void init() throws Exception {
//        // On Mac OS X Chromium engine must be initialized in non-UI thread.
//        if (Environment.isMac()) {
//            BrowserCore.initialize();
//        }
//    }
}
