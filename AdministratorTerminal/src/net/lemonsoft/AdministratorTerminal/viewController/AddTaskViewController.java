package net.lemonsoft.AdministratorTerminal.viewController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.lemonsoft.AdministratorTerminal.tool.DateTool;
import net.lemonsoft.AdministratorTerminal.tool.HttpTool;
import net.lemonsoft.AdministratorTerminal.tool.SystemInfoTool;
import net.lemonsoft.AdministratorTerminal.tool.URLTool;
import net.sf.json.JSONObject;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * 添加任务 - 视图控制器
 * Created by LiuRi on 16/8/23.
 */
public class AddTaskViewController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private DatePicker publishDatePicker;
    @FXML
    private ChoiceBox publishHourChoiceBox;
    @FXML
    private ChoiceBox publishMinuteChoiceBox;
    @FXML
    private ChoiceBox publishSecondChoiceBox;
    @FXML
    private ChoiceBox repeatDayChoiceBox;
    @FXML
    private ChoiceBox repeatHourChoiceBox;
    @FXML
    private ChoiceBox repeatMinuteChoiceBox;
    @FXML
    private ChoiceBox repeatSecondChoiceBox;
    @FXML
    private TextField repeatCountTextField;
    @FXML
    private TextArea executeCodeTextArea;
    @FXML
    private ChoiceBox expiredDayChoiceBox;
    @FXML
    private ChoiceBox expiredHourChoiceBox;
    @FXML
    private ChoiceBox expiredMinuteChoiceBox;
    @FXML
    private ChoiceBox expiredSecondChoiceBox;
    @FXML
    private ChoiceBox<Integer> levelChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    public void initView() {
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
        publishHourChoiceBox.getItems().addAll(twentyFourList);
        publishMinuteChoiceBox.getItems().addAll(sixtyList);
        publishSecondChoiceBox.getItems().addAll(sixtyList);
        publishHourChoiceBox.getSelectionModel().select(8);
        publishMinuteChoiceBox.getSelectionModel().select(0);
        publishSecondChoiceBox.getSelectionModel().select(0);

        repeatDayChoiceBox.getItems().addAll(oneHundredList);
        repeatHourChoiceBox.getItems().addAll(twentyFourList);
        repeatMinuteChoiceBox.getItems().addAll(sixtyList);
        repeatSecondChoiceBox.getItems().addAll(sixtyList);
        repeatDayChoiceBox.getSelectionModel().select(1);
        repeatHourChoiceBox.getSelectionModel().select(0);
        repeatMinuteChoiceBox.getSelectionModel().select(0);
        repeatSecondChoiceBox.getSelectionModel().select(0);

        expiredDayChoiceBox.getItems().addAll(oneHundredList);
        expiredHourChoiceBox.getItems().addAll(twentyFourList);
        expiredMinuteChoiceBox.getItems().addAll(sixtyList);
        expiredSecondChoiceBox.getItems().addAll(sixtyList);
        expiredDayChoiceBox.getSelectionModel().select(1);
        expiredHourChoiceBox.getSelectionModel().select(0);
        expiredMinuteChoiceBox.getSelectionModel().select(0);
        expiredSecondChoiceBox.getSelectionModel().select(0);

        publishDatePicker.setValue(LocalDate.now());

        for (int i = 0; i < 5; i++)
            levelChoiceBox.getItems().add(i);
        levelChoiceBox.getSelectionModel().select(0);
    }

    public void addTask() {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", nameField.getText());
        params.put("sessionFingerprint", SystemInfoTool.sharedInstance().getSessionFingerprint());
        params.put("fingerprint", UUID.randomUUID().toString());
        params.put("description", descriptionTextArea.getText());
        Long publishTime = DateTool.localDateToUtilDate(publishDatePicker.getValue()).getTime() / 1000;
        publishTime += publishHourChoiceBox.getSelectionModel().getSelectedIndex() * 60 * 60;
        publishTime += publishMinuteChoiceBox.getSelectionModel().getSelectedIndex() * 60;
        publishTime += publishSecondChoiceBox.getSelectionModel().getSelectedIndex();
        params.put("publishTime", String.valueOf(publishTime));
        Long repeatInterval = repeatDayChoiceBox.getSelectionModel().getSelectedIndex() * 24L * 60 * 60;
        repeatInterval += repeatHourChoiceBox.getSelectionModel().getSelectedIndex() * 60 * 60;
        repeatInterval += repeatMinuteChoiceBox.getSelectionModel().getSelectedIndex() * 60;
        repeatInterval += repeatSecondChoiceBox.getSelectionModel().getSelectedIndex();
        params.put("distributeRepeatInterval", String.valueOf(repeatInterval));
        params.put("distributeRepeatCount", repeatCountTextField.getText());
        params.put("distributedNumber", "0");
        params.put("executeScript", executeCodeTextArea.getText());
        params.put("state", "0");
        Long expiredTime = expiredDayChoiceBox.getSelectionModel().getSelectedIndex() * 24L * 60 * 60;
        expiredTime += expiredHourChoiceBox.getSelectionModel().getSelectedIndex() * 60 * 60;
        expiredTime += expiredMinuteChoiceBox.getSelectionModel().getSelectedIndex() * 60;
        expiredTime += expiredSecondChoiceBox.getSelectionModel().getSelectedIndex();
        params.put("expired", String.valueOf(expiredTime));
        params.put("level", String.valueOf(levelChoiceBox.getSelectionModel().getSelectedIndex()));
        String result = HttpTool.post(URLTool.URL_TASK_ADD_TASK, params);
        JSONObject jsonObject = JSONObject.fromObject(result);
        Alert alert;
        if (jsonObject.getBoolean("success")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("任务添加成功!");
            reSetView();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(String.format("任务添加失败,%s", jsonObject.getString("info")));
        }
        alert.setTitle("添加新任务");
        alert.showAndWait();
    }

    public void reSetView() {
        nameField.setText("");
        descriptionTextArea.setText("");
        publishDatePicker.setValue(LocalDate.now());
        publishHourChoiceBox.getSelectionModel().select(8);
        publishMinuteChoiceBox.getSelectionModel().select(0);
        publishSecondChoiceBox.getSelectionModel().select(0);
        repeatDayChoiceBox.getSelectionModel().select(1);
        repeatHourChoiceBox.getSelectionModel().select(0);
        repeatMinuteChoiceBox.getSelectionModel().select(0);
        repeatSecondChoiceBox.getSelectionModel().select(0);
        repeatCountTextField.setText("0");
        executeCodeTextArea.setText("");
        expiredDayChoiceBox.getSelectionModel().select(1);
        expiredHourChoiceBox.getSelectionModel().select(0);
        expiredMinuteChoiceBox.getSelectionModel().select(0);
        expiredSecondChoiceBox.getSelectionModel().select(0);
    }

}
