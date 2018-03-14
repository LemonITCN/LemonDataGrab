package net.lemonsoft.AdministratorTerminal.tool;

import javax.print.DocFlavor;

/**
 * 工具类 - URL相关
 * Created by LiuRi on 6/19/16.
 */
public class URLTool {

//    public static final String URL_HOST = "127.0.0.1";
    public static final String URL_HOST = "47.95.7.38";

    public static final Integer URL_HTTP_PORT = 8080;
    public static final Integer URL_MINA_PORT = 3385;

    public static final String URL_RESULT_DOWNLOAD = createURL("dl?fingerprint=");

    public static final String URL_CLIENT_SIGN_UP = createURL("Client/SignUp");
    public static final String URL_CLIENT_ONLINE_COUNT_WITH_TYPE = createURL("Client/CountOnlineWithType");
    public static final String URL_CLIENT_ONLINE_COUNT_ALL = createURL("Client/CountOnline");
    public static final String URL_CLIENT_GET_ONLINE_INFO_LIST = createURL("Client/GetOnlineList");

    public static final String URL_USER_SIGN_UP = createURL("User/SignUp");
    public static final String URL_USER_SIGN_IN = createURL("User/SignIn");
    public static final String URL_USER_SIGN_OUT = createURL("User/SignOut");
    public static final String URL_USER_INFO_GET = createURL("User/GetInfo");
    public static final String URL_USER_COUNT_ALL_USER = createURL("User/CountAll");
    public static final String URL_USER_GET_USER_INFO_LIST = createURL("User/GetUserList");
    public static final String URL_USER_ADD = createURL("User/AddUser");
    public static final String URL_SESSION_QUERY_WITH_ID = createURL("User/GetSessionInfoWithId");

    public static final String URL_USER_GROUP_GET_ALL_LIST = createURL("UserGroup/GetAll");

    public static final String URL_TASK_ADD_TASK = createURL("Task/AddTask");
    public static final String URL_TASK_COUNT_BY_DATE = createURL("Task/CountTask");
    public static final String URL_TASK_INFO_LIST_BY_DATE = createURL("Task/GetTaskList");
    public static final String URL_TASK_DETAIL_BY_FINGERPRINT = createURL("Task/GetInfo");// 查询任务详情信息'
    public static final String URL_TASK_UPDATE = createURL("Task/UpdateTask");

    public static final String URL_RESULT_QUERY = createURL("Result/query");// 查询采集结果
    public static final String URL_QUERY_LOCAL_LIST = createURL("Task/QueryLocalTaskList");// 查询采集终端本地的任务列表
    public static final String URL_QUERY_LOCAL_DETAIL = createURL("Task/QueryLocalTaskDetail");

    private static String createURL(String paramStr) {
        return String.format("http://%s:%d/%s", URL_HOST, URL_HTTP_PORT, paramStr);
    }

}
