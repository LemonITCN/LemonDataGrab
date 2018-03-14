package net.lemonsoft.AdministratorTerminal.service;

import net.lemonsoft.AdministratorTerminal.model.ClientInfoModel;
import net.lemonsoft.AdministratorTerminal.tool.HttpTool;
import net.lemonsoft.AdministratorTerminal.tool.SystemInfoTool;
import net.lemonsoft.AdministratorTerminal.tool.URLTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lemonsoft on 2016/8/22.
 */
public class ClientService {

    private static final String CLIENT_FINGERPRINT_KEY = "CLIENT_FINGERPRINT";

    /**
     * 获取客户端指纹
     *
     * @return 客户端指纹字符串
     */
    public static String getClientFingerprint() {
        if (!SystemInfoTool.sharedInstance().getClientFingerprint().equals("")) {
            return SystemInfoTool.sharedInstance().getClientFingerprint();
        } else {
            // request new client fingerprint
            HashMap<String, String> params = new HashMap<>();
            params.put("identity", SystemInfoTool.sharedInstance().getIdentity());
            params.put("system", SystemInfoTool.sharedInstance().getSystemName());
            params.put("terminalType", SystemInfoTool.sharedInstance().getTerminalType());
            params.put("version", SystemInfoTool.sharedInstance().getAppVersion());
            params.put("device", SystemInfoTool.sharedInstance().getDeviceName());
            JSONObject jsonObject = JSONObject.fromObject(HttpTool.post(URLTool.URL_CLIENT_SIGN_UP, params));
            String clientFingerprint = jsonObject.getJSONObject("result").getString("info");
            SystemInfoTool.sharedInstance().setClientFingerprint(clientFingerprint);
            return clientFingerprint;
        }
    }

    private static ClientService defaultClientService;

    public static ClientService defaultClientService() {
        if (defaultClientService == null)
            defaultClientService = new ClientService();
        return defaultClientService;
    }

    /**
     * 获取当前在线的所有客户端的数量
     *
     * @return 当前在线客户端的数量
     */
    public Integer countOnlineClient() {
        HashMap<String, String> params = Service.getParamsMapWithSessionFingerprint();
        String result = HttpTool.post(URLTool.URL_CLIENT_ONLINE_COUNT_ALL, params);
        return JSONObject.fromObject(result).getJSONObject("result").getInt("data");
    }

    /**
     * 分页查询当前在线客户端的信息
     * @param startIndex 要查询信息的分页开始索引
     * @param count 查询的信息数量
     * @return 查询到的所有客户端信息的list
     */
    public ArrayList<ClientInfoModel> queryOnlineClientInfo(Integer startIndex, Integer count) {
        HashMap<String, String> params = Service.getParamsMapWithSessionFingerprint();
        params.put("startIndex", String.valueOf(startIndex));
        params.put("count", String.valueOf(count));
        String result = HttpTool.post(URLTool.URL_CLIENT_GET_ONLINE_INFO_LIST, params);
        JSONArray data = JSONObject.fromObject(result).getJSONObject("result").getJSONArray("data");
        ArrayList<ClientInfoModel> re = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JSONObject dataItem = data.getJSONObject(i);
            re.add(new ClientInfoModel(dataItem.getString("sessionId"), dataItem.getString("clientId")
                    , dataItem.getString("userId"),dataItem.getString("clientSystem"),
                    dataItem.getString("clientDeviceName"), dataItem.getString("userName"),
                    dataItem.getString("userUsergroup"), dataItem.getString("clientIp"),
                    dataItem.getString("sessionState").equals("1") ? "数据采集终端" : "管理终端"));
        }
        return re;
    }

}
