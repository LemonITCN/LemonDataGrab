package net.lemonsoft.DataGrab.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.lemonsoft.DataGrab.tool.ConfigTool;
import net.lemonsoft.DataGrab.tool.HttpTool;
import net.lemonsoft.DataGrab.tool.SystemInfoTool;
import net.lemonsoft.DataGrab.tool.URLTool;
import net.sf.json.JSONObject;

import java.util.HashMap;

/**
 * Created by lemonsoft on 2016/8/22.
 */
public class ClientService {

    private static final String CLIENT_FINGERPRINT_KEY = "CLIENT_FINGERPRINT";

    public static String getClientFingerprint(){
        if (!SystemInfoTool.sharedInstance().getClientFingerprint().equals("")){
            return SystemInfoTool.sharedInstance().getClientFingerprint();
        }
        else {
            // request new client fingerprint
            HashMap<String , String> params = new HashMap<>();
            params.put("identity" , SystemInfoTool.sharedInstance().getIdentity());
            params.put("system" , SystemInfoTool.sharedInstance().getSystemName());
            params.put("terminalType" , SystemInfoTool.sharedInstance().getTerminalType());
            params.put("version" , SystemInfoTool.sharedInstance().getAppVersion());
            params.put("device" , SystemInfoTool.sharedInstance().getDeviceName());
            JSONObject jsonObject = JSONObject.fromObject(HttpTool.post(URLTool.URL_CLIENT_SIGN_UP , params));
            String clientFingerprint = jsonObject.getJSONObject("result").getString("info");
            SystemInfoTool.sharedInstance().setClientFingerprint(clientFingerprint);
            return clientFingerprint;
        }
    }

}
