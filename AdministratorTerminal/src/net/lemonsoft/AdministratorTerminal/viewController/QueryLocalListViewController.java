package net.lemonsoft.AdministratorTerminal.viewController;

import impl.org.controlsfx.skin.MaskerPaneSkin;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import net.lemonsoft.AdministratorTerminal.Main;
import net.lemonsoft.AdministratorTerminal.model.QueryLocalListItemModel;
import net.lemonsoft.AdministratorTerminal.tool.DateTool;
import net.lemonsoft.AdministratorTerminal.tool.HttpTool;
import net.lemonsoft.AdministratorTerminal.tool.SystemInfoTool;
import net.lemonsoft.AdministratorTerminal.tool.URLTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.MasterDetailPane;

import java.awt.peer.FramePeer;
import java.net.URL;
import java.util.*;

/**
 * 查询指定数据采集终端本地采集列表的视图控制器
 * Created by lemonsoft on 2016/12/11.
 */
public class QueryLocalListViewController implements Initializable {

    @FXML
    private Label waitLabel;
    @FXML
    private TableView<QueryLocalListItemModel> mainTableView;
    @FXML
    private StackPane rootPane;

    private MaskerPane maskerPane;

    /**
     * 采集终端主机通信会话ID
     */
    private String sessionId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        waitLabel.setOpacity(1);
        maskerPane = new MaskerPane();
        maskerPane.setText("正在发送指令...");
        maskerPane.setSkin(new MaskerPaneSkin(maskerPane));
        maskerPane.setVisible(false);
        rootPane.getChildren().add(maskerPane);
        this.mainTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    // 鼠标双击了任务
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("sessionFingerprint", SystemInfoTool.sharedInstance().getSessionFingerprint());
                    params.put("sessionId", sessionId);
                    params.put("messageKey", UUID.randomUUID().toString());
                    params.put("taskFingerprint", mainTableView.getItems().get(mainTableView.getSelectionModel().getSelectedIndex()).getTaskFingerprint());
                    HttpTool.post(URLTool.URL_QUERY_LOCAL_DETAIL, params);
                    maskerPane.setVisible(true);
                }
            }
        });
    }

    /**
     * 加载指定会话的本地任务列表
     *
     * @param sessionId 要加载的会话的id
     */
    public void loadWithSessionId(String sessionId) {
        waitLabel.setOpacity(1);
        this.sessionId = sessionId;
        HashMap<String, String> params = new HashMap<>();
        params.put("sessionFingerprint", SystemInfoTool.sharedInstance().getSessionFingerprint());
        params.put("sessionId", sessionId);
        params.put("messageKey", UUID.randomUUID().toString());
        HttpTool.post(URLTool.URL_QUERY_LOCAL_LIST, params);
    }

    /**
     * 加载成功的回传方法，由mina调用
     */
    public void onLoadSuccess(Map<String, Object> data) {
        waitLabel.setOpacity(0);
        mainTableView.getItems().clear();
        JSONArray dataArr = JSONArray.fromObject(data.get("data"));
        for (int i = 0; i < dataArr.size(); i++) {
            JSONObject dataItem = dataArr.getJSONObject(i);
            String pubTime = DateTool.timeStamp2Date(Long.parseLong(dataItem.getString("publishTime")) * 1000, "yyyy-MM-dd HH:mm:ss");
            QueryLocalListItemModel model = new QueryLocalListItemModel(dataItem.getString("name"), dataItem.getString("fingerprint"), pubTime);
            mainTableView.getItems().add(model);
        }
    }

    /**
     * 双击加载任务详情后的回传方法， 由mina调用
     *
     * @param data 回传回来的数据
     */
    public void onLoadTaskDetailSuccess(Map<String, Object> data) {
        maskerPane.setVisible(false);
        Main.commandMain.defaultMainViewController.openResultInfoWithJSONData(data.get("data").toString());
    }

}
