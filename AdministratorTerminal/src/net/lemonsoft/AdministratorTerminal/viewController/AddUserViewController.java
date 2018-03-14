package net.lemonsoft.AdministratorTerminal.viewController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.lemonsoft.AdministratorTerminal.model.UserGroupModel;
import net.lemonsoft.AdministratorTerminal.service.UserGroupService;
import net.lemonsoft.AdministratorTerminal.service.UserService;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by LiuRi on 16/9/13.
 */
public class AddUserViewController implements Initializable {

    @FXML
    private TextField user_numberField;
    @FXML
    private TextField user_nameField;
    @FXML
    private ChoiceBox<String> user_userGroupChoiceBox;
    @FXML
    private Label user_userGroupDescriptionLabel;

    private ArrayList<UserGroupModel> userGroupList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.user_userGroupChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null && userGroupList != null && userGroupList.size() > 0)
                    user_userGroupDescriptionLabel.setText(userGroupList.get(user_userGroupChoiceBox.getSelectionModel().getSelectedIndex()).getDescription());
            }
        });
    }

    public void initUserGroupChoiceBox() {
        this.userGroupList = UserGroupService.defaultUserGroupService().queryAllUserGroup();
        if (this.userGroupList != null) {
            this.user_userGroupChoiceBox.getItems().clear();
            for (UserGroupModel userGroupModel : userGroupList) {
                this.user_userGroupChoiceBox.getItems().add(userGroupModel.getName());
            }
            if (this.user_userGroupChoiceBox.getItems().size() > 0)
                this.user_userGroupChoiceBox.getSelectionModel().select(0);
        }
    }

    public void submitAddUser() {
        String reason = UserService.defaultUserService().addUser(user_numberField.getText(), user_nameField.getText(),
                userGroupList.get(user_userGroupChoiceBox.getSelectionModel().getSelectedIndex()).getId());
        Alert alert;
        if (reason == null) {
            // 添加成功
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("您已经成功添加用户: " + user_nameField.getText());
            resetForm();// 重置表单
        } else {
            // 添加失败
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("错误: " + reason);
        }
        alert.setTitle("添加新用户");
        alert.showAndWait();
    }

    /**
     * 重置表单
     */
    public void resetForm() {
        user_nameField.setText("");
        user_numberField.setText("");
        initUserGroupChoiceBox();
    }

}
