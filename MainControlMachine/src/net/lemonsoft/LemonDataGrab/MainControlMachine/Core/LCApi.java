package net.lemonsoft.LemonDataGrab.MainControlMachine.Core;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMApi;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LULog;

import javax.servlet.http.HttpServletRequest;

/**
 * 核心类 - API入口Servlet
 * Created by LiuRi on 16/4/21.
 */
public class LCApi extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String uri = request.getRequestURI();// 获取URI
        if (LCApiPool.isListeningTheURL(uri)) {
            //监听这个URI中
            LMApi apiModel = LCApiPool.getApiModel(uri);
            if (this.checkParametersCompleted(request, apiModel.getParameters())) {
                // 传入参数值完整,执行方法
                try {
                    LCApiPool.invokeApiMethod(apiModel, request, response);// 执行API方法
                } catch (Exception e) {
                    e.printStackTrace();
                    LCResponse.outErrorSystemException(response);// 输出系统内部错误信息
                }
            } else {// 传入的参数值不完整
                LCResponse.outErrorRequestParameterIncomplete(response);// 输出请求参数不完整信息
            }
        } else {
            // 未监听这个URI
            LCResponse.outErrorURLNotListening(response);
            LULog.warn("未监听该URL:" + request.getRequestURL());
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        response.setContentType("application/json;charset=utf-8");
        this.doPost(request, response);
    }

    /**
     * 检查参数是否完整
     *
     * @param request        要对其进行检查的请求对象
     * @param parametersList 要检查的参数列表
     * @return 检查结果的boolean值, 完整为true, 否则为false
     */
    private boolean checkParametersCompleted(HttpServletRequest request, String[] parametersList) {
        for (String parameter : parametersList) {
            if (request.getParameter(parameter) == null) {
                return false;
            }
        }
        return true;
    }

}
