package net.lemonsoft.DataGrab.viewController;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.lemonsoft.DataGrab.Main;
import net.lemonsoft.DataGrab.entity.TaskCellEntity;
import net.lemonsoft.DataGrab.entity.TaskEntity;
import net.lemonsoft.DataGrab.service.TaskService;
import net.lemonsoft.DataGrab.tool.DateTool;
import net.lemonsoft.DataGrab.tool.HttpTool;
import net.lemonsoft.DataGrab.tool.SystemInfoTool;
import net.lemonsoft.DataGrab.tool.URLTool;
import net.sf.json.JSONObject;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * 主界面 - 视图控制器
 * Created by lemonsoft on 2016/8/22.
 */
public class MainViewController implements Initializable {

    private ObservableList<TaskCellEntity> data;

    @FXML
    private TableView<TaskCellEntity> rootTableView;
    @FXML
    private Label user_idLabel;
    @FXML
    private Label user_nameLabel;
    @FXML
    private Label user_userGroupLabel;
    @FXML
    private Label user_scoreLabel;
    @FXML
    private Button user_signOutButton;
    @FXML
    private Label task_infoLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = rootTableView.getItems();
        refreshTaskStateLabel();
    }

    public void onIoSessionClosed() {
        Main.commandMain.defaultMainStage.hide();
        Main.commandMain.defaultLoginStage.show();
    }

    public void addTask(TaskEntity taskEntity) {
        TaskService.addTask(taskEntity);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rootTableView.getItems().add(new TaskCellEntity(taskEntity.getFingerprint(), taskEntity.getName(), DateTool.timeStamp2Date(String.valueOf(taskEntity.getPublishTime() * 1000L), "yyyy-MM-dd HH:mm:ss"), TaskService.getStateText(taskEntity.getState())));
            }
        });
    }

    public void setUserInfo(String id, String name, String score, String userGroupName) {
        user_idLabel.setText(String.format("用户ID：%s", id));
        user_nameLabel.setText(String.format("用户姓名：%s", name));
        user_scoreLabel.setText(String.format("积分：%s", score));
        user_userGroupLabel.setText(String.format("用户组：%s", userGroupName));
    }

    /**
     * 登录成功的回调函数
     */
    public void onLoginSuccess() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HttpTool.get(String.format("%s?sessionFingerprint=%s", URLTool.URL_USER_INFO_GET, SystemInfoTool.sharedInstance().getSessionFingerprint()));
                JSONObject u = JSONObject.fromObject(result).getJSONObject("result").getJSONObject("data");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setUserInfo(u.getString("id"), u.getString("name"), u.getString("score"), u.getString("userGroupName"));
                        refreshTaskList();
                    }
                });
            }
        }).run();
    }

    public void refreshTaskStateLabel() {
        task_infoLabel.setText(String.format("正在执行第 %d 条任务，共收到服务器 %d 条任务", TaskService.pointerIndex() + 1, TaskService.countTask()));
    }

    /**
     * 注销用户 - GUI调用
     */
    public void signOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("用户注销");
        alert.setContentText("您确认注销退出登录吗？当前的任务将会被停止。");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.OK) {
            HttpTool.get(String.format("%s?sessionFingerprint=%s", URLTool.URL_USER_SIGN_OUT, SystemInfoTool.sharedInstance().getSessionFingerprint()));
            SystemInfoTool.sharedInstance().setSessionFingerprint("");
            TaskService.removeAll();
        }
    }

    /**
     * 刷新任务列表
     */
    public void refreshTaskList() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rootTableView.getItems().clear();
                for (TaskEntity taskEntity : TaskService.taskList) {
                    rootTableView.getItems().add(new TaskCellEntity(taskEntity.getFingerprint(), taskEntity.getName(), DateTool.timeStamp2Date(String.valueOf(taskEntity.getPublishTime()), "yyyy-MM-dd HH:mm:ss"), TaskService.getStateText(taskEntity.getState())));
                }
            }
        });
    }

}
