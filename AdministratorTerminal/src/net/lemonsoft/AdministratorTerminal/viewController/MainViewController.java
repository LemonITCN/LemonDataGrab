package net.lemonsoft.AdministratorTerminal.viewController;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.lemonsoft.AdministratorTerminal.Main;
import net.lemonsoft.AdministratorTerminal.model.ClientInfoModel;
import net.lemonsoft.AdministratorTerminal.model.TaskInfoModel;
import net.lemonsoft.AdministratorTerminal.model.UserInfoModel;
import net.lemonsoft.AdministratorTerminal.service.ClientService;
import net.lemonsoft.AdministratorTerminal.service.TaskService;
import net.lemonsoft.AdministratorTerminal.service.UserService;
import net.lemonsoft.AdministratorTerminal.tool.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * 主界面 - 视图控制器
 * Created by lemonsoft on 2016/8/22.
 */
public class MainViewController implements Initializable {

    private Stage defaultAddTaskStage;
    private AddTaskViewController defaultAddTaskViewController;
    private Stage defaultQueryLocalListStage;
    public QueryLocalListViewController defaultQueryLocalListViewController;

    // 每页获取数据的数据量
    private static final int EVERY_PAGE_DATA_COUNT = 20;

    // 任务选项卡相关的控件
    @FXML
    private TabPane rootTabPane;
    @FXML
    private TableView<TaskInfoModel> task_tableView;
    @FXML
    private DatePicker task_filter_dateStart;// 过滤器 查询日期的开始日期
    @FXML
    private DatePicker task_filter_dateEnd;// 过滤器 查询日期的截止日期
    @FXML
    private Button task_operate_queryInfo;// 任务操作 查询信息
    @FXML
    private ChoiceBox<Integer> task_selectPageChoiceBox;// 页码选择器
    @FXML
    private Label task_pageCountLabel;// 总页码数标签控件
    @FXML
    private Button task_pageFrontButton;// 上一页按钮
    @FXML
    private Button task_pageNextButton;// 下一页按钮

    // 客户端选项卡相关的控件
    @FXML
    private TableView<ClientInfoModel> client_tableView;
    @FXML
    private ChoiceBox<Integer> client_selectPageChoiceBox;// 页码选择器
    @FXML
    private Label client_pageCountLabel;// 总页码数标签控件
    @FXML
    private Button client_pageFrontButton;// 上一页按钮
    @FXML
    private Button client_pageNextButton;// 下一页按钮

    // 用户选项卡相关的控件
    @FXML
    private TableView<UserInfoModel> user_tableView;
    @FXML
    private ChoiceBox<Integer> user_selectPageChoiceBox;// 页码选择器
    @FXML
    private Label user_pageCountLabel;// 总页码数标签控件
    @FXML
    private Button user_pageFrontButton;// 上一页按钮
    @FXML
    private Button user_pageNextButton;// 下一页按钮

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
    private Label client_infoLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initTaskTabPane();// 初始化任务选项卡
        this.initClientTabPane();// 初始化客户端选项卡
        this.initUserTabPane();// 初始化用户信息管理选项卡
    }

    public void onIoSessionClosed() {
        Main.commandMain.defaultMainStage.hide();
        Main.commandMain.defaultLoginStage.show();
    }

    /**
     * 初始化任务选项卡
     */
    public void initTaskTabPane() {
        task_filter_dateStart.setValue(LocalDate.now());
        task_filter_dateEnd.setValue(LocalDate.now().plusDays(1));
        task_selectPageChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if (newValue != null) {
                    // 任务页码选择器的值被改变事件
                    task_pageFrontButton.setDisable(newValue - 1 == 0);
                    task_pageNextButton.setDisable(newValue == task_selectPageChoiceBox.getItems().size());
                    queryTaskData();
                }
            }
        });
        task_tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TaskInfoModel>() {
            @Override
            public void changed(ObservableValue<? extends TaskInfoModel> observable, TaskInfoModel oldValue, TaskInfoModel newValue) {
                task_operate_queryInfo.setDisable(newValue == null);
            }
        });
        // 这里主要是监听任务列表的鼠标双击事件
        task_tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    // 鼠标双击了
                    int selectedIndex = task_tableView.getSelectionModel().getSelectedIndex();
                    openTaskInfoPanel(task_tableView.getItems().get(selectedIndex));
                }
            }
        });
    }

    /**
     * 初始化客户端选项卡
     */
    public void initClientTabPane() {
        client_selectPageChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if (newValue != null) {
                    // 客户端页码选择器的值被改变事件
                    client_pageFrontButton.setDisable(newValue - 1 == 0);
                    client_pageNextButton.setDisable(newValue == client_selectPageChoiceBox.getItems().size());
                    queryClientData();
                }
            }
        });
        // 这里主要是监听客户端列表的鼠标双击事件
        client_tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    // 鼠标双击了
                    int selectedIndex = client_tableView.getSelectionModel().getSelectedIndex();
                    ClientInfoModel clientInfoModel = client_tableView.getItems().get(selectedIndex);
                    if (clientInfoModel.getSessionState().contains("数据采集")) {
                        // 是数据采集终端，管理员端无法查看本地任务列表
                        openQueryLocalTaskListPanel(clientInfoModel.getSessionId());
                    }
                }
            }
        });
    }

    /**
     * 初始化用户信息选项卡
     */
    public void initUserTabPane() {
        user_selectPageChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if (newValue != null) {
                    // 用户信息页码选择器的值被改变事件
                    user_pageFrontButton.setDisable(newValue - 1 == 0);
                    user_pageNextButton.setDisable(newValue == user_selectPageChoiceBox.getItems().size());
                    queryUserData();
                }
            }
        });
    }

    /**
     * 添加一个任务 - GUI调用
     */
    public void addTask() {
        if (defaultAddTaskStage == null) {
            this.defaultAddTaskStage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/AddTask.fxml"));
                Scene rootScene = null;
                rootScene = new Scene(loader.load());
                rootScene.getStylesheets().add(getClass().getResource("../style/AddTask.css").toString());
                this.defaultAddTaskStage = new Stage();
                this.defaultAddTaskStage.setScene(rootScene);
                this.defaultAddTaskStage.setTitle("添加任务 - 分布式网络数据采集器[管理端]");
                this.defaultAddTaskStage.setResizable(false);
                this.defaultAddTaskViewController = loader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        defaultAddTaskStage.show();
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
                        Main.commandMain.defaultMainViewController.refreshClientState();
                        Main.commandMain.defaultMainViewController.refreshUserState();
                    }
                });
            }
        }).run();
    }

    /**
     * 刷新客户端状态标签
     */
    public int refreshClientStateLabel() {
        HashMap<String, String> params = new HashMap<>();
        params.put("sessionFingerprint", SystemInfoTool.sharedInstance().getSessionFingerprint());
        String result = HttpTool.post(URLTool.URL_CLIENT_ONLINE_COUNT_WITH_TYPE, params);
        JSONArray dataArray = JSONObject.fromObject(result).getJSONObject("result").getJSONArray("data");
        StringBuilder infoBuilder = new StringBuilder("当前共有");
        int allCount = 0;
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject data = dataArray.getJSONObject(i);
            infoBuilder.append(String.format(" %d 个%s在线;", data.getInt("sessionCount"), data.getString("sessionTypeName")));
            allCount += data.getInt("sessionCount");
        }
        client_infoLabel.setText(infoBuilder.toString());
        return allCount;
    }

    /**
     * 注销用户 - GUI调用
     */
    public void signOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("用户注销");
        alert.setContentText("您确认注销退出登录吗？");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.OK) {
            HttpTool.get(String.format("%s?sessionFingerprint=%s", URLTool.URL_USER_SIGN_OUT, SystemInfoTool.sharedInstance().getSessionFingerprint()));
            SystemInfoTool.sharedInstance().setSessionFingerprint("");
        }
    }

    /**
     * 刷新任务选项卡状态信息
     */
    public void refreshTaskState() {
        task_tableView.getItems().clear();
        task_selectPageChoiceBox.getItems().clear();// 清空分页的选项
        task_selectPageChoiceBox.setDisable(true);// 设置页码选择器状态不可用
        int dataCount = TaskService.defaultService().countTaskWithDate(
                DateTool.localDateToUtilDate(task_filter_dateStart.getValue()).getTime() / 1000,
                DateTool.localDateToUtilDate(task_filter_dateEnd.getValue()).getTime() / 1000);// 获取数据总数量
        for (int i = 0; i < Math.ceil(dataCount * 1.00 / EVERY_PAGE_DATA_COUNT); i++) {
            task_selectPageChoiceBox.getItems().add(i + 1);
            task_selectPageChoiceBox.setDisable(false);// 循环到此,说明有大于0的页码,因此设置页码选择器可用
        }
        task_selectPageChoiceBox.getSelectionModel().select(0);// 默认选择第0页
        task_pageCountLabel.setText(String.format("共 %d 页", task_selectPageChoiceBox.getItems().size()));
    }

    /**
     * 刷新客户端选项卡的状态信息
     */
    public void refreshClientState() {
        client_selectPageChoiceBox.getItems().clear();// 清空分页的选项
        client_selectPageChoiceBox.setDisable(true);// 设置页码选择器状态不可用
        for (int i = 0; i < Math.ceil(ClientService.defaultClientService().countOnlineClient() * 1.0 / EVERY_PAGE_DATA_COUNT); i++) {
            client_selectPageChoiceBox.getItems().add(i + 1);
            client_selectPageChoiceBox.setDisable(false);// 循环到此,说明有大于0的页码,因此设置页码选择器可用
        }
        client_selectPageChoiceBox.getSelectionModel().select(0);// 默认选择第0页
        client_pageCountLabel.setText(String.format("共 %d 页", client_selectPageChoiceBox.getItems().size()));
    }

    /**
     * 刷新用户管理选项卡的信息
     */
    public void refreshUserState() {
        user_selectPageChoiceBox.getItems().clear();
        user_selectPageChoiceBox.setDisable(true);
        for (int i = 0; i < Math.ceil(UserService.defaultUserService().countAllUser() * 1.0 / EVERY_PAGE_DATA_COUNT); i++) {
            user_selectPageChoiceBox.getItems().add(i + 1);
            user_selectPageChoiceBox.setDisable(false);
        }
        user_selectPageChoiceBox.getSelectionModel().select(0);
        user_pageCountLabel.setText(String.format("共 %d 页", user_selectPageChoiceBox.getItems().size()));
    }

    /**
     * 查询任务的数据 - 查询控件中当时的选项进行服务器接口查询
     */
    public void queryTaskData() {
        task_tableView.getItems().clear();
        task_tableView.getItems().addAll(TaskService.defaultService().queryTaskInfoWithDate(
                DateTool.localDateToUtilDate(task_filter_dateStart.getValue()).getTime() / 1000,
                DateTool.localDateToUtilDate(task_filter_dateEnd.getValue()).getTime() / 1000,
                task_selectPageChoiceBox.getSelectionModel().getSelectedIndex() * EVERY_PAGE_DATA_COUNT,
                EVERY_PAGE_DATA_COUNT
        ));
    }

    /**
     * 查询在线客户端的数据 - 查询控件中当时的选项状态进行服务器接口查询
     */
    public void queryClientData() {
        client_tableView.getItems().clear();
        client_tableView.getItems().addAll(ClientService.defaultClientService().queryOnlineClientInfo(
                client_selectPageChoiceBox.getSelectionModel().getSelectedIndex() * EVERY_PAGE_DATA_COUNT,
                EVERY_PAGE_DATA_COUNT
        ));
    }

    /**
     * 查询在用户信息的数据 - 查询控件中当时的选项状态进行服务器接口查询
     */
    public void queryUserData() {
        user_tableView.getItems().clear();
        user_tableView.getItems().addAll(UserService.defaultUserService().queryUserInfo(
                user_selectPageChoiceBox.getSelectionModel().getSelectedIndex() * EVERY_PAGE_DATA_COUNT,
                EVERY_PAGE_DATA_COUNT
        ));
    }

    /**
     * 上一页任务
     */
    public void taskFrontPage() {
        task_selectPageChoiceBox.getSelectionModel().
                select(task_selectPageChoiceBox.getSelectionModel().getSelectedIndex() - 1);
    }

    /**
     * 下一页任务
     */
    public void taskNextPage() {
        task_selectPageChoiceBox.getSelectionModel().
                select(task_selectPageChoiceBox.getSelectionModel().getSelectedIndex() + 1);
    }

    /**
     * 上一页在线客户端
     */
    public void clientFrontPage() {
        client_selectPageChoiceBox.getSelectionModel().
                select(client_selectPageChoiceBox.getSelectionModel().getSelectedIndex() - 1);
    }

    /**
     * 下一页在线客户端
     */
    public void clientNextPage() {
        client_selectPageChoiceBox.getSelectionModel().
                select(client_selectPageChoiceBox.getSelectionModel().getSelectedIndex() + 1);
    }

    /**
     * 上一页用户列表
     */
    public void userFrontPage() {
        user_selectPageChoiceBox.getSelectionModel().
                select(user_selectPageChoiceBox.getSelectionModel().getSelectedIndex() - 1);
    }

    /**
     * 下一页用户列表
     */
    public void userNextPage() {
        user_selectPageChoiceBox.getSelectionModel().
                select(user_selectPageChoiceBox.getSelectionModel().getSelectedIndex() + 1);
    }

    /**
     * 添加用户 - GUI调用
     */
    public void addUser() {
        Main.commandMain.defaultAddUserViewController.initUserGroupChoiceBox();
        Main.commandMain.defaultAddUserStage.show();
    }

    /**
     * 任务信息面板stage
     */
    private static Stage defaultTaskInfoPanelStage;
    private static TaskInfoPanelViewController defaultTaskInfoPanelViewController;

    /**
     * 打开任务信息面板
     *
     * @param taskInfoModel 任务的信息模型
     */
    public void openTaskInfoPanel(TaskInfoModel taskInfoModel) {
        if (defaultTaskInfoPanelStage == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/TaskInfoPanel.fxml"));
            try {
                Scene rootScene = new Scene(loader.load());
                rootScene.getStylesheets().add(getClass().getResource("../style/TaskInfoPanel.css").toString());
                defaultTaskInfoPanelStage = new Stage();
                defaultTaskInfoPanelStage.setScene(rootScene);
                defaultTaskInfoPanelStage.setTitle("任务信息详情");
                defaultTaskInfoPanelStage.setResizable(false);
                defaultTaskInfoPanelStage.initModality(Modality.WINDOW_MODAL);
                defaultTaskInfoPanelViewController = loader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        defaultTaskInfoPanelStage.show();
        defaultTaskInfoPanelViewController.loadTask(taskInfoModel);
        defaultTaskInfoPanelViewController.setMainViewController(this);
    }

    /**
     * 任务信息面板stage
     */
    private static Stage defaultResultInfoStage;
    private static ResultInfoViewController defaultResultInfoViewController;

    private void initResultInfoViewController() {
        if (defaultResultInfoStage == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/ResultInfo.fxml"));
            try {
                Scene rootScene = new Scene(loader.load());
                rootScene.getStylesheets().add(getClass().getResource("../style/ResultInfo.css").toString());
                defaultResultInfoStage = new Stage();
                defaultResultInfoStage.setScene(rootScene);
                defaultResultInfoStage.setTitle("任务采集结果详情");
                defaultResultInfoStage.setResizable(false);
                defaultResultInfoStage.initModality(Modality.WINDOW_MODAL);
                defaultResultInfoViewController = loader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 打开任务信息面板
     *
     * @param taskFingerprint 任务采集结果的信息模型
     */
    public ResultInfoViewController openResultInfo(String taskFingerprint) {
        initResultInfoViewController();
        defaultResultInfoStage.show();
        defaultResultInfoViewController.loadResult(taskFingerprint);
        return defaultResultInfoViewController;
    }

    /**
     * 打开任务信息面板
     *
     * @param data 任务采集结果的json字符串
     */
    public ResultInfoViewController openResultInfoWithJSONData(String data) {
        initResultInfoViewController();
        defaultResultInfoStage.show();
        defaultResultInfoViewController.loadDataGrabLocalResult(data);
        return defaultResultInfoViewController;
    }

    /**
     * 打开查询采集终端本地任务列表的窗口
     *
     * @param sessionId 要查询的指定会话的id
     */
    public void openQueryLocalTaskListPanel(String sessionId) {
        if (defaultQueryLocalListStage == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/QueryLocalList.fxml"));
            try {
                Scene rootScene = new Scene(loader.load());
                rootScene.getStylesheets().add(getClass().getResource("../style/QueryLocalList.css").toString());
                defaultQueryLocalListStage = new Stage();
                defaultQueryLocalListStage.setScene(rootScene);
                defaultQueryLocalListStage.setTitle("查询终端的本地任务列表");
                defaultQueryLocalListStage.setResizable(false);
                defaultQueryLocalListStage.initModality(Modality.WINDOW_MODAL);
                defaultQueryLocalListViewController = loader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        defaultQueryLocalListStage.show();
        defaultQueryLocalListViewController.loadWithSessionId(sessionId);
    }

}
