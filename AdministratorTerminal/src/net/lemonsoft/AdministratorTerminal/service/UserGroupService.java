package net.lemonsoft.AdministratorTerminal.service;

import com.sun.org.apache.regexp.internal.RE;
import net.lemonsoft.AdministratorTerminal.model.UserGroupModel;
import net.lemonsoft.AdministratorTerminal.tool.HttpTool;
import net.lemonsoft.AdministratorTerminal.tool.URLTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.management.RuntimeErrorException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 业务层 - 用户组
 * Created by LiuRi on 16/9/10.
 */
public class UserGroupService {

    private static UserGroupService defaultUserGroupService;

    public static UserGroupService defaultUserGroupService() {
        if (defaultUserGroupService == null)
            defaultUserGroupService = new UserGroupService();
        return defaultUserGroupService;
    }

    /**
     * 查询所有用户组信息
     *
     * @return 用户组信息list
     */
    public ArrayList<UserGroupModel> queryAllUserGroup() {
        HashMap<String, String> params = Service.getParamsMapWithSessionFingerprint();
        String result = HttpTool.post(URLTool.URL_USER_GROUP_GET_ALL_LIST, params);
        JSONArray data = JSONObject.fromObject(result).getJSONObject("result").getJSONArray("data");
        ArrayList<UserGroupModel> re = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JSONObject dataItem = data.getJSONObject(i);
            re.add(new UserGroupModel(dataItem.getString("ugid"),
                    dataItem.getString("name"), dataItem.getString("description")));
        }
        return re;
    }

}
