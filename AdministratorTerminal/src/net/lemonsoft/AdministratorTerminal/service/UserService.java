package net.lemonsoft.AdministratorTerminal.service;

import net.lemonsoft.AdministratorTerminal.model.UserInfoModel;
import net.lemonsoft.AdministratorTerminal.tool.DateTool;
import net.lemonsoft.AdministratorTerminal.tool.HttpTool;
import net.lemonsoft.AdministratorTerminal.tool.URLTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 用户管理服务层
 * Created by LiuRi on 16/9/10.
 */
public class UserService {

    private static UserService defaultUserService;

    /**
     * 默认的用户管理服务 - 单例方法
     *
     * @return 默认的用户管理服务对象
     */
    public static UserService defaultUserService() {
        if (defaultUserService == null)
            defaultUserService = new UserService();
        return defaultUserService;
    }

    /**
     * 获取所有用户数量
     *
     * @return 当前系统的所有用户的数量数值
     */
    public Integer countAllUser() {
        HashMap<String, String> params = Service.getParamsMapWithSessionFingerprint();
        String result = HttpTool.post(URLTool.URL_USER_COUNT_ALL_USER, params);
        return JSONObject.fromObject(result).getJSONObject("result").getInt("data");
    }

    /**
     * 查询用户信息列表
     *
     * @param startIndex 获取列表的起始索引
     * @param count      获取信息的数量
     * @return 获取到的信息模型对象的list
     */
    public ArrayList<UserInfoModel> queryUserInfo(Integer startIndex, Integer count) {
        HashMap<String, String> params = Service.getParamsMapWithSessionFingerprint();
        params.put("startIndex", String.valueOf(startIndex));
        params.put("count", String.valueOf(count));
        String result = HttpTool.post(URLTool.URL_USER_GET_USER_INFO_LIST, params);
        JSONArray data = JSONObject.fromObject(result).getJSONArray("result");
        ArrayList<UserInfoModel> re = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JSONObject dataItem = data.getJSONObject(i);
            re.add(new UserInfoModel(dataItem.getString("userId"), dataItem.getString("userName"),
                    dataItem.getString("userPhone"), dataItem.getString("userEmail"),
                    dataItem.getString("userUsername"), dataItem.getString("userScore"),
                    dataItem.getString("usergroupId"), dataItem.getString("usergroupName")));
        }
        return re;
    }

    /**
     * 添加一个用户
     *
     * @param number      要添加用户的账号
     * @param name        添加的用户的真实姓名
     * @param userGroupId 添加的用户的用户组ID
     * @return 失败的原因, 若添加成功则返回null
     */
    public String addUser(String number, String name, String userGroupId) {
        HashMap<String, String> params = Service.getParamsMapWithSessionFingerprint();
        params.put("number", number);
        params.put("name", name);
        params.put("userGroup", userGroupId);
        String result = HttpTool.post(URLTool.URL_USER_ADD, params);
        JSONObject resultObj = JSONObject.fromObject(result);
        return resultObj.getBoolean("success") ? null : resultObj.getString("info");
    }

    /**
     * 查询指定的会话ID的会话信息
     *
     * @param sessionId 会话ID
     * @return 会话信息对象
     */
    public String querySessionInfoStrWithId(Long sessionId) {
        HashMap<String, String> params = Service.getParamsMapWithSessionFingerprint();
        params.put("sessionId", sessionId + "");
        String result = HttpTool.post(URLTool.URL_SESSION_QUERY_WITH_ID, params);
        JSONObject resultObj = JSONObject.fromObject(result).getJSONObject("result").getJSONObject("data");
        JSONObject session = resultObj.getJSONObject("session");
        JSONObject user = resultObj.getJSONObject("user");
        JSONObject client = resultObj.getJSONObject("client");
        return String.format("登录用户名：%s\n登录用户邮箱：%s\n登录用户电话：%s\n客户端操作系统：%s\n客户端设备名称：%s\n客户端软件版本：%s\n客户端IP地址：%s\n会话创建时间：%s",
                user.getString("name"), user.getString("email"), user.getString("phone"),
                client.getString("system"), client.getString("device"), client.getString("version"),
                session.getString("ip"), DateTool.timeStamp2Date(session.getLong("setupTime") * 1000, "yyyy-MM-dd : HH:mm:ss"));
    }

}
