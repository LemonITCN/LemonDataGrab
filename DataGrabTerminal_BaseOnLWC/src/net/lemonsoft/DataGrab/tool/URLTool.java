package net.lemonsoft.DataGrab.tool;

/**
 * 工具类 - URL相关
 * Created by LiuRi on 6/19/16.
 */
public class URLTool {

    //    public static final String URL_HOST = "127.0.0.1";
    public static final String URL_HOST = "47.95.7.38";

    public static final Integer URL_HTTP_PORT = 8080;
    public static final Integer URL_MINA_PORT = 3385;

    public static final String URL_CLIENT_SIGN_UP = createURL("Client/SignUp");
    public static final String URL_USER_SIGN_UP = createURL("User/SignUp");
    public static final String URL_USER_SIGN_IN = createURL("User/SignIn");
    public static final String URL_USER_SIGN_OUT = createURL("User/SignOut");
    public static final String URL_USER_INFO_GET = createURL("User/GetInfo");
    public static final String URL_RESULT_RESPONSE = createURL("Result/Response");

    public static final String URL_RESPONSE_LOCAL_TASK_LIST = createURL("Task/ResponseLocalTaskList");
    public static final String URL_RESPONSE_LOCAL_TASK_DETAIL = createURL("Task/ResponseLocalTaskDetail");

    private static String createURL(String paramStr) {
        return String.format("http://%s:%d/%s", URL_HOST, URL_HTTP_PORT, paramStr);
    }

}
