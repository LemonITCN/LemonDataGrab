package net.lemonsoft.AdministratorTerminal.service;

import net.lemonsoft.AdministratorTerminal.model.ResultModel;
import net.lemonsoft.AdministratorTerminal.tool.HttpTool;
import net.lemonsoft.AdministratorTerminal.tool.URLTool;
import net.sf.json.JSONObject;

import java.util.HashMap;

/**
 * 业务层 - 采集结果
 * Created by LiuRi on 16/10/30.
 */
public class ResultService {

    private static ResultService clientResultObj;

    public static synchronized ResultService sharedInstance() {
        if (clientResultObj == null)
            clientResultObj = new ResultService();
        return clientResultObj;
    }

    /**
     * 通过任务指纹查询任务采集结果
     *
     * @param taskFingerprint 要查询的任务指纹
     * @return 查询到的任务采集结果对象
     */
    public ResultModel queryResultWithTaskFingerprint(String taskFingerprint) {
        HashMap<String, String> params = Service.getParamsMapWithSessionFingerprint();
        params.put("taskFingerprint", taskFingerprint);
        String result = HttpTool.post(URLTool.URL_RESULT_QUERY, params);
        JSONObject resultObj = JSONObject.fromObject(result);
        if (!resultObj.getBoolean("success"))
            return null;
        else {
            ResultModel resultModel = new ResultModel();
            JSONObject res = resultObj.getJSONObject("result");
            resultModel.setRid(res.getLong("rid"));
            resultModel.setFingerprint(res.getString("fingerprint"));
            resultModel.setRecoveryTime(res.getLong("recoveryTime"));
            resultModel.setData(res.getString("data"));
            resultModel.setLog(res.getString("log"));
            resultModel.setSessionFingerprint(res.getString("sessionFingerprint"));
            return resultModel;
        }
    }

}
