package net.lemonsoft.AdministratorTerminal.viewController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.lemonsoft.AdministratorTerminal.model.TaskDetail;
import net.lemonsoft.AdministratorTerminal.model.TaskInfoModel;
import net.lemonsoft.AdministratorTerminal.service.TaskService;
import net.lemonsoft.AdministratorTerminal.service.UserService;
import net.lemonsoft.AdministratorTerminal.tool.DateTool;
import net.lemonsoft.AdministratorTerminal.tool.ResourceTool;

import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by LiuRi on 16/10/8.
 */
public class TaskInfoPanelViewController implements Initializable {

    @FXML
    private ImageView task_line_icon_0;
    @FXML
    private ImageView task_line_icon_1;
    @FXML
    private ImageView task_line_icon_2;
    @FXML
    private ImageView task_line_icon_3;
    @FXML
    private Label task_line_title_0;
    @FXML
    private Label task_line_title_1;
    @FXML
    private Label task_line_title_2;
    @FXML
    private Label task_line_title_3;
    @FXML
    private Button task_line_detail_2;// 查看采集端信息button
    @FXML
    private Button task_line_detail_3;// 最后一步查看详情代码

    @FXML
    private TextField task_idTextField;
    @FXML
    private TextField task_fingerprintTextField;
    @FXML
    private TextField task_nameTextField;
    @FXML
    private TextArea task_descriptionTextArea;
    @FXML
    private DatePicker task_publishDatePicker;
    @FXML
    private ChoiceBox task_publishHourChoiceBox;
    @FXML
    private ChoiceBox task_publishMinuteChoiceBox;
    @FXML
    private ChoiceBox task_publishSecondChoiceBox;
    @FXML
    private TextArea task_scriptTextArea;
    @FXML
    private DatePicker task_expiredDatePicker;
    @FXML
    private ChoiceBox task_expiredHourChoiceBox;
    @FXML
    private ChoiceBox task_expiredMinuteChoiceBox;
    @FXML
    private ChoiceBox task_expiredSecondChoiceBox;
    @FXML
    private ChoiceBox task_levelChoiceBox;
    @FXML
    private Button submitButton;

    private TaskDetail currentTask;

    private MainViewController mainViewController;

    public MainViewController getMainViewController() {
        return mainViewController;
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Integer> oneHundredList = new ArrayList<>();
        ArrayList<Integer> sixtyList = new ArrayList<>();
        ArrayList<Integer> twentyFourList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            oneHundredList.add(i);
            if (i < 60)
                sixtyList.add(i);
            if (i < 24)
                twentyFourList.add(i);
        }
        task_expiredHourChoiceBox.getItems().addAll(twentyFourList);
        task_expiredMinuteChoiceBox.getItems().addAll(sixtyList);
        task_expiredSecondChoiceBox.getItems().addAll(sixtyList);

        task_publishHourChoiceBox.getItems().addAll(twentyFourList);
        task_publishMinuteChoiceBox.getItems().addAll(sixtyList);
        task_publishSecondChoiceBox.getItems().addAll(sixtyList);

        for (int i = 0; i < 5; i++)
            task_levelChoiceBox.getItems().add(i);
        task_levelChoiceBox.getSelectionModel().select(0);
    }

    public void loadTask(TaskInfoModel taskInfoModel) {
        currentTask =
                TaskService.defaultService().queryTaskDetailWithFingerprint(taskInfoModel.getTaskFingerprint());
        task_idTextField.setText(currentTask.getTid());
        task_fingerprintTextField.setText(currentTask.getFingerprint());
        task_nameTextField.setText(currentTask.getName());
        task_descriptionTextArea.setText(currentTask.getDescription());
        task_descriptionTextArea.setDisable(!currentTask.isCanEdit());
        Date publishDate = new Date(currentTask.getPublishTime() * 1000);
        Calendar publishCalendar = Calendar.getInstance();
        publishCalendar.setTime(publishDate);
        task_publishDatePicker.setValue(publishDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        task_publishDatePicker.setDisable(!currentTask.isCanEdit());
        task_publishHourChoiceBox.getSelectionModel().select(publishCalendar.get(Calendar.HOUR_OF_DAY));
        task_publishHourChoiceBox.setDisable(!currentTask.isCanEdit());
        task_publishMinuteChoiceBox.getSelectionModel().select(publishCalendar.get(Calendar.MINUTE));
        task_publishMinuteChoiceBox.setDisable(!currentTask.isCanEdit());
        task_publishSecondChoiceBox.getSelectionModel().select(publishCalendar.get(Calendar.SECOND));
        task_publishSecondChoiceBox.setDisable(!currentTask.isCanEdit());
        task_scriptTextArea.setText(currentTask.getExecuteScript());
        task_scriptTextArea.setDisable(!currentTask.isCanEdit());
        Date expiredDate = new Date(currentTask.getExpired() * 1000);
        Calendar expiredCalendar = Calendar.getInstance();
        expiredCalendar.setTime(expiredDate);
        task_expiredDatePicker.setValue(expiredDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        task_expiredDatePicker.setDisable(!currentTask.isCanEdit());
        task_expiredHourChoiceBox.getSelectionModel().select(expiredCalendar.get(Calendar.HOUR_OF_DAY));
        task_expiredHourChoiceBox.setDisable(!currentTask.isCanEdit());
        task_expiredMinuteChoiceBox.getSelectionModel().select(expiredCalendar.get(Calendar.MINUTE));
        task_expiredMinuteChoiceBox.setDisable(!currentTask.isCanEdit());
        task_expiredSecondChoiceBox.getSelectionModel().select(expiredCalendar.get(Calendar.SECOND));
        task_expiredSecondChoiceBox.setDisable(!currentTask.isCanEdit());
        task_levelChoiceBox.getSelectionModel().select(currentTask.getLevel());
        task_levelChoiceBox.setDisable(!currentTask.isCanEdit());
        submitButton.setDisable(!currentTask.isCanEdit());
        submitButton.setText(currentTask.isCanEdit() ? "提交修改任务信息" : "任务采集的最晚时间已过，无法修改");

        Label[] stateLabels = {task_line_title_0, task_line_title_1, task_line_title_2, task_line_title_3};
        ImageView[] stateIcons = {task_line_icon_0, task_line_icon_1, task_line_icon_2, task_line_icon_3};
        // 初始化顶部状态轴信息
        for (int i = 0; i < stateIcons.length; i++) {
            stateIcons[i].setPreserveRatio(true);
            stateIcons[i].setFitHeight(40);
            if (i == 0)
                stateIcons[i].setImage(new Image("file://" + ResourceTool.sharedInstance().getResourcePath() + "task_state_line/end_run.png"));
            else if (i == stateIcons.length - 1)
                stateIcons[i].setImage(new Image("file://" + ResourceTool.sharedInstance().getResourcePath() + "task_state_line/end_start.png"));
            else
                stateIcons[i].setImage(new Image("file://" + ResourceTool.sharedInstance().getResourcePath() + "task_state_line/center_start.png"));
        }
        for (int i = 0; i <= (currentTask.getState() >= 3 ? 2 : currentTask.getState()); i++) {
            if (i < 3) {
                // 还没有结果呢
                stateIcons[3].setImage(new Image("file://" + ResourceTool.sharedInstance().getResourcePath() + "task_state_line/end_start.png"));
                if (i < currentTask.getState())// 已完成
                    stateIcons[i].setImage(new Image("file://" + ResourceTool.sharedInstance().getResourcePath() +
                            "task_state_line/" + (i == 0 ? "start" : "center") + "_finish.png"));
                else if (i == currentTask.getState())// 运行中
                    stateIcons[i].setImage(new Image("file://" + ResourceTool.sharedInstance().getResourcePath() +
                            "task_state_line/" + (i == 0 ? "start" : "center") + "_run.png"));
                else
                    stateIcons[i].setImage(new Image("file://" + ResourceTool.sharedInstance().getResourcePath() +
                            "task_state_line/" + (i == 0 ? "start" : "center") + "_start.png"));
            }
        }
        if (currentTask.getState() >= 3) {
            // 已经有结果了
            stateIcons[3].setImage(new Image("file://" + ResourceTool.sharedInstance().getResourcePath() + "task_state_line/end_finish.png"));
            task_line_title_3.setText(currentTask.getState() == 3 ? "任务执行成功，已收到回传采集结果" : (currentTask.getState() == 4 ? "任务结束并超时，在指定时间内未能成功分发任务" : "任务采集失败，采集终端报告了严重错误"));
        }
        task_line_detail_2.setDisable(currentTask.getState() < 2 || currentTask.getSession() <= 0);
        task_line_detail_3.setDisable(currentTask.getState() < 3 || currentTask.getState() == 4);
    }

    /**
     * 查询会话信息 - GUI调用
     */
    public void querySessionInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(UserService.defaultUserService().querySessionInfoStrWithId(currentTask.getSession()));
        alert.setTitle("当前任务的关联会话信息");
        alert.showAndWait();
    }

    /**
     * 查询采集结果 - GUI调用
     */
    public void queryResult() {
        mainViewController.openResultInfo(currentTask.getFingerprint());
    }

    /**
     * 提交更新
     */
    public void submitUpdate() {
        currentTask.setDescription(task_descriptionTextArea.getText());
        Long publishTime = DateTool.localDateToUtilDate(task_publishDatePicker.getValue()).getTime() / 1000;
        publishTime += task_publishHourChoiceBox.getSelectionModel().getSelectedIndex() * 60 * 60;
        publishTime += task_publishMinuteChoiceBox.getSelectionModel().getSelectedIndex() * 60;
        publishTime += task_publishSecondChoiceBox.getSelectionModel().getSelectedIndex();
        currentTask.setPublishTime(publishTime);
        currentTask.setExecuteScript(task_scriptTextArea.getText());
        Long expiredTime = DateTool.localDateToUtilDate(task_expiredDatePicker.getValue()).getTime() / 1000;
        expiredTime += task_expiredHourChoiceBox.getSelectionModel().getSelectedIndex() * 60 * 60;
        expiredTime += task_expiredMinuteChoiceBox.getSelectionModel().getSelectedIndex() * 60;
        expiredTime += task_expiredSecondChoiceBox.getSelectionModel().getSelectedIndex();
        currentTask.setExpired(expiredTime);
        currentTask.setLevel(task_levelChoiceBox.getSelectionModel().getSelectedIndex());
        String result = TaskService.defaultService().updateTaskInfo(currentTask);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("更新任务信息");
        alert.setContentText(result);
        alert.showAndWait();
    }

}
