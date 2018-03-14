package net.lemonsoft.LemonDataGrab.MainControlMachine.Core;

import com.google.gson.Gson;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENResponseError;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 核心类 - 响应处理核心类
 * Created by LiuRi on 16/5/8.
 */
public class LCResponse {

    /**
     * 输出错误 - URL未监听
     *
     * @param response 要输出错误的响应对象
     */
    public static void outErrorURLNotListening(HttpServletResponse response) {
        LCResponse.outError(response, LENResponseError.REQUEST_URL_NOT_LISTENING);
    }

    /**
     * 输出错误 - 请求参数不完全
     *
     * @param response 要输出错误的响应对象
     */
    public static void outErrorRequestParameterIncomplete(HttpServletResponse response) {
        LCResponse.outError(response, LENResponseError.REQUEST_PARAMETER_ERROR);
    }

    /**
     * 输出错误 - 系统内部错误
     *
     * @param response 要输出错误的响应对象
     */
    public static void outErrorSystemException(HttpServletResponse response) {
        LCResponse.outError(response, LENResponseError.REQUEST_SYSTEM_EXCEPTION);
    }

    /**
     * 对外输出成功的信息
     *
     * @param response 输出信息的响应对象
     * @param result   输出的结果对象
     */
    public static void outSuccess(HttpServletResponse response, Object result) {
        out(response, true, 0, "", result);
    }

    /**
     * 对外输出错误的信息
     *
     * @param response  输出信息的响应对象
     * @param errorCode 错误的错误码
     * @param info      错误的说明信息
     */
    public static void outError(HttpServletResponse response, int errorCode, String info) {
        out(response, false, errorCode, info, "");
    }

    /**
     * 对外输出错误的新出现
     *
     * @param response 输出信息的响应对象
     * @param error    错误的信息枚举对象
     */
    public static void outError(HttpServletResponse response, LENResponseError error) {
        outError(response, error.getErrorCode(), error.getInfo());
    }

    /**
     * 通过HTTP响应对象对外打印制定的对象的JSON数据
     *
     * @param response 要输出的HTTP响应对象
     * @param info     要打印的对象
     */
    public static void out(HttpServletResponse response, boolean isSucceed, int errorCode, String info, Object result) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", isSucceed);
        resultMap.put("error", errorCode);
        resultMap.put("info", info);
        resultMap.put("result", result);
        try {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.println(new Gson().toJson(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
