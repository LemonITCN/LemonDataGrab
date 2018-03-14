package net.lemonsoft.LemonDataGrab.MainControlMachine.Core;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANApi;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENResponseError;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Exception.LEXURLNotListening;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMApi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 核心类 - API池
 * Created by LiuRi on 16/5/8.
 */
public class LCApiPool {

    /**
     * API的静态存储池
     */
    private static HashMap<String, LMApi> apiPool = new HashMap<String, LMApi>();

    /**
     * 通过URL和API模型对象来添加一个API
     *
     * @param url      API的监听URL
     * @param apiModel API的模型对象
     */
    public static void addApi(String url, LMApi apiModel) {
        LCApiPool.apiPool.put(url, apiModel);
    }

    /**
     * 通过注解对象以及API执行方法来添加一个API
     *
     * @param apiAnnotation API的注解对象
     * @param apiMethod     API的执行方法
     */
    public static void addApi(LANApi apiAnnotation, Class apiClass, Method apiMethod) {
        LMApi apiModel = new LMApi();
        apiModel.setURL(apiAnnotation.URL());
        apiModel.setParameters(apiAnnotation.parameters());
        apiModel.setParameterIntros(apiAnnotation.parameterIntros());
        apiModel.setErrors(apiAnnotation.errors());
        apiModel.setDescription(apiAnnotation.description());
        apiModel.setMethod(apiMethod);
        apiModel.setApiClass(apiClass);
        LCApiPool.apiPool.put(apiModel.getURL(), apiModel);
    }

    /**
     * 判断指定的URL是否在被监听API范围内
     *
     * @param url 要检查的API
     * @return 是否被监听的boolean值
     */
    public static boolean isListeningTheURL(String url) {
        return LCApiPool.apiPool.containsKey(url);
    }

    /**
     * 通过URL获取对应的API信息模型
     *
     * @param url 要查询的API监听URL
     * @return 对应的API模型
     */
    public static LMApi getApiModel(String url) {
        return LCApiPool.apiPool.get(url);
    }

    /**
     * 激活执行指定的API方法
     *
     * @param url      要激活的指定的对应的方法的URL
     * @param request  请求对象
     * @param response 响应对象
     * @throws LEXURLNotListening
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void invokeApiMethod(String url, HttpServletRequest request, HttpServletResponse response) throws LEXURLNotListening, InvocationTargetException, IllegalAccessException, InstantiationException {
        if (LCApiPool.isListeningTheURL(url)) {
            // 监听了这个URL,那么调用对应的方法
            LMApi apiModel = LCApiPool.apiPool.get(url);
            LCResponse.outSuccess(response, apiModel.getMethod().invoke(LCClassInstancePool.getInstanceByClass(apiModel.getClass()), request, response, getParameters(request, apiModel)));
        } else {// 没有监听这个URL
            throw new LEXURLNotListening();
        }
    }

    /**
     * 激活执行指定的API方法
     *
     * @param apiModel 要激活的指定的API方法模型对象
     * @param request  请求对象
     * @param response 响应对象
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void invokeApiMethod(LMApi apiModel, HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Object result = apiModel.getMethod().invoke(LCClassInstancePool.getInstanceByClass(apiModel.getApiClass()), request, response, getParameters(request, apiModel));
        if (result instanceof LENResponseError) {
            // 返回的是错误信息
            LCResponse.outError(response, (LENResponseError) result);
        } else {
            // 没有错误信息,成功
            LCResponse.outSuccess(response, result);
        }
    }

    /**
     * 根据API模型和请求对象生成参数Map
     *
     * @param request  要获取参数的请求对象
     * @param apiModel API的模型对象,读取需要的参数列表
     * @return 生成的参数map
     */
    public static HashMap<String, Object> getParameters(HttpServletRequest request, LMApi apiModel) {
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        for (String parameter : apiModel.getParameters()) {
            System.out.printf("REQ KEY:%s , VALUE:%s" , parameter , request.getParameter(parameter));
            parameters.put(parameter, request.getParameter(parameter));
        }
        return parameters;
    }

}
