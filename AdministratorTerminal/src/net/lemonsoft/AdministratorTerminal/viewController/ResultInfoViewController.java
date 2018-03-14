package net.lemonsoft.AdministratorTerminal.viewController;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.lemonsoft.AdministratorTerminal.Main;
import net.lemonsoft.AdministratorTerminal.model.LogItemModel;
import net.lemonsoft.AdministratorTerminal.model.ResultItemModel;
import net.lemonsoft.AdministratorTerminal.model.ResultModel;
import net.lemonsoft.AdministratorTerminal.service.ResultService;
import net.lemonsoft.AdministratorTerminal.tool.DateTool;
import net.lemonsoft.AdministratorTerminal.tool.HttpTool;
import net.lemonsoft.AdministratorTerminal.tool.URLTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.URL;
import java.sql.*;
import java.util.*;

/**
 * 采集结果信息查看视图控制器
 * Created by LiuRi on 16/10/30.
 */
public class ResultInfoViewController implements Initializable {

    @FXML
    private Label recoveryTimeLabel;// 数据回收时间的显示Label
    @FXML
    private TableView<ResultItemModel> result_dataTableView;// 采集数据结果查看tableView
    @FXML
    private TableView<LogItemModel> result_logTableView;// 日志查看tableView

    private String taskFingerprint;
    private String localDBPath;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alert.setTitle("导出文件");
    }

    public List<Map<String, String>> getData() {
        return getDBContent(true);
    }

    public List<Map<String, String>> getLog() {
        return getDBContent(false);
    }

    public List<Map<String, String>> getDBContent(boolean isData) {
        ArrayList<Map<String, String>> data = new ArrayList<>();
        if (taskFingerprint != null) {
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + localDBPath);
                c.setAutoCommit(false);
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + (isData ? "data" : "logs"));
                ResultSetMetaData metaData = rs.getMetaData();
                while (rs.next()) {
                    HashMap<String, String> dataItem = new HashMap<>();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        String columnName = metaData.getColumnName(i);
                        String columnData = rs.getString(columnName);
                        dataItem.put(columnName, columnData);
                    }
                    data.add(dataItem);
                }
                rs.close();
                stmt.close();
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public void loadResult(String taskFingerprint) {
        this.taskFingerprint = taskFingerprint;

        // 从远程加载数据库文件
        String rootPath = System.getProperty("user.home") + File.separator + "lwc_data" + File.separator + "remote" + File.separator;
        localDBPath = rootPath + taskFingerprint + ".db";
        String remotePath = URLTool.URL_RESULT_DOWNLOAD + taskFingerprint;
        new File(rootPath).mkdirs();
        HttpTool.download(localDBPath, remotePath);

        List<Map<String, String>> data = getData();
        List<Map<String, String>> logs = getLog();
        Gson gson = new Gson();

        result_dataTableView.getItems().clear();
        result_logTableView.getItems().clear();

        for (int i = 0; i < data.size(); i++) {
            result_dataTableView.getItems().add(new ResultItemModel(i + "", gson.toJson(data.get(0))));
        }
        for (int i = 0; i < logs.size(); i++) {
            Map<String, String> logItem = logs.get(i);
            result_logTableView.getItems().add(new LogItemModel(logItem.get("state"), logItem.get("content"),
                    DateTool.timeStamp2Date(Long.valueOf(logItem.get("time")), "yyyy-MM-dd HH:mm:ss")));
        }
//        recoveryTimeLabel.setText("数据回收时间：" + DateTool.timeStamp2Date(resultModel.getRecoveryTime() * 1000, "yyyy年MM月dd日 HH:mm:ss"));
    }

    public void loadDataGrabLocalResult(String jsonData) {
//        result_dataTableView.getItems().clear();
//        result_logTableView.getItems().clear();
//        JSONObject dataObj = JSONObject.fromObject(JSONObject.fromObject(jsonData).getString("data").substring(1, JSONObject.fromObject(jsonData).getString("data").length() - 1));
//        data = dataObj;
//        for (Object key : dataObj.keySet()) {
//            result_dataTableView.getItems().add(new ResultItemModel((String) key, dataObj.getString((String) key)));
//        }
//        JSONObject mainObj = JSONObject.fromObject(jsonData);
//        JSONArray logArr = JSONArray.fromObject(mainObj.getString("log").substring(1, mainObj.getString("log").length() - 1));
//        log = logArr;
//        for (int i = 0; i < logArr.size(); i++) {
//            JSONObject logItem = JSONObject.fromObject(logArr.getString(i).substring(1, logArr.getString(i).length() - 1));
//            result_logTableView.getItems().add(new LogItemModel(logItem.getString("type"), logItem.getString("content"),
//                    DateTool.timeStamp2Date(Long.parseLong(logItem.getString("time")), "yyyy-MM-dd HH:mm:ss")));
//        }
//        recoveryTimeLabel.setText("数据回收时间：" + "与客户端即时通信");
    }

    public void exportDataCSV() {
        String exportPath = selectExportPath("csv");
        if (exportPath != "") {
            // 路径选择成功
            StringBuilder stringBuilder = new StringBuilder();
            for (Map<String, String> line : getData()) {
                stringBuilder.append(StringUtils.join(line.values(), ",")).append("\n");
            }
            outFile(exportPath, stringBuilder.toString());
        }
    }

    public void exportDataJSON() {
        String exportPath = selectExportPath("json");
        if (exportPath != "") {
            // 路径选择成功
            outFile(exportPath, new Gson().toJson(getData()));
        }
    }

    public void exportLogCSV() {
        String exportPath = selectExportPath("csv");
        if (!exportPath.equals("")) {
            // 路径选择成功
            StringBuilder stringBuilder = new StringBuilder();
            for (Map<String, String> line : getData()) {
                stringBuilder.append(StringUtils.join(line.values().toArray(), ",")).append("\n");
            }
            outFile(exportPath, stringBuilder.toString());
        }
    }

    /**
     * 导出数据excel
     */
    public void exportDataExcel() {
        String path = selectExportPath("xls");
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("DataSheet1");
        HSSFRow row;
        int i = 0;
        for (Map<String, String> line : getData()) {
            row = sheet.createRow(i++);
            int cell = 0;
            for (String value : line.values())
                row.createCell(cell++).setCellValue(value);
        }
        outExcel(path, book);
    }

    /**
     * 导出日志excel
     */
    public void exportLogExcel() {
        String path = selectExportPath("xls");
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("LogSheet1");
        HSSFRow row;
        int i = 0;
        for (Map<String, String> line : getLog()) {
            row = sheet.createRow(i++);
            int cell = 0;
            for (String value : line.values())
                row.createCell(cell++).setCellValue(value);
        }
        outExcel(path, book);
    }

    public void exportLogJSON() {
        String exportPath = selectExportPath("json");
        if (exportPath != "") {
            // 路径选择成功
            outFile(exportPath, new Gson().toJson(getLog()));
        }
    }

    public String selectExportPath(String fileType) {
        //得到用户导出的文件路径
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(fileType + " files (*." + fileType + ")", "*." + fileType);
        fileChooser.getExtensionFilters().add(extFilter);
        Stage s = new Stage();
        File file = fileChooser.showSaveDialog(s);
        if (file == null) return "";
        return file.getAbsolutePath().replaceAll("." + fileType, "") + "." + fileType;
    }

    private void outFile(String path, String content) {
        try {
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
            alert.setContentText("文件导出成功");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void outExcel(String path, HSSFWorkbook book) {
        if (path != "") {
            try {
                FileOutputStream fout = new FileOutputStream(path);
                book.write(fout);
                fout.close();
                alert.setContentText("文件导出成功");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
