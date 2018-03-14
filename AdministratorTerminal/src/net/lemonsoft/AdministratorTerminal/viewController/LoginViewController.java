package net.lemonsoft.AdministratorTerminal.viewController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import net.lemonsoft.AdministratorTerminal.Main;
import net.lemonsoft.AdministratorTerminal.service.ClientService;
import net.lemonsoft.AdministratorTerminal.tool.*;
import net.sf.json.JSONObject;
import org.controlsfx.control.ToggleSwitch;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * 登录视图控制器
 * Created by lemonsoft on 2016/8/22.
 */
public class LoginViewController implements Initializable {

    private static final String USER_NUMBER = "USER_NUMBER";
    private static final String USER_PASSWORD = "USER_PASSWORD";

    @FXML
    private ImageView topLogoImage;
    @FXML
    private TextField numberTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ToggleSwitch rememberNumber;
    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topLogoImage.setPreserveRatio(true);
        topLogoImage.setFitWidth(340);
        try {
            if (!ConfigTool.sharedInstance().getOrDefault(USER_NUMBER, "").equals("")) {
                numberTextField.setText(ConfigTool.sharedInstance().getOrDefault(USER_NUMBER, ""));
                passwordField.setText(ConfigTool.sharedInstance().getOrDefault(USER_PASSWORD, ""));
                rememberNumber.setSelected(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录事件 - GUI调用
     */
    public void login() {
        loginButton.setDisable(true);
        loginButton.setText("正在登录...");
        HashMap<String, String> params = new HashMap<>();
        params.put("number", numberTextField.getText());
        params.put("password", passwordField.getText());
        params.put("clientFingerprint", ClientService.getClientFingerprint());
        params.put("isAdministrator", "1");
        // 登录失败
        final String[] errInfo = {""};

        new Thread(new Runnable() {// 开线程请求网络
            @Override
            public void run() {
                try {
                    String result = HttpTool.post(URLTool.URL_USER_SIGN_IN, params);
                    JSONObject loginResult = JSONObject.fromObject(result);
                    if (loginResult.getBoolean("success")) {
                        SystemInfoTool.sharedInstance().setSessionFingerprint(loginResult.getJSONObject("result").getString("info"));
                        MinaTool.sharedInstance().connectToServer(SystemInfoTool.sharedInstance().getSessionFingerprint());
                        return;
                    }
                    else {
                        errInfo[0] = "登录失败，账号或密码错误！";
                        showLoginError(errInfo[0]);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    errInfo[0] = "登录失败,无法连接到服务器,请检查您的网络设置。";
                    showLoginError(errInfo[0]);
                }
                finally {
                    loginButton.setDisable(false);
                    loginButton.setText("登录");
                }
            }
        }).run();
    }

    public void showLoginError(String errInfo){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("登录失败");
                alert.setContentText(errInfo);
                alert.showAndWait();
                loginButton.setDisable(false);
                loginButton.setText("登录");
            }
        });
    }

    /**
     * 登录成功的回调函数
     */
    public void onLoginSuccess() {
        if (rememberNumber.isSelected()) {
            try {
                ConfigTool.sharedInstance().set(USER_NUMBER, numberTextField.getText());
                ConfigTool.sharedInstance().set(USER_PASSWORD, passwordField.getText());
                loginButton.setDisable(false);
                loginButton.setText("登录");
                Main.commandMain.defaultLoginStage.hide();
                Main.commandMain.defaultMainStage.show();
                Main.commandMain.defaultMainViewController.onLoginSuccess();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
