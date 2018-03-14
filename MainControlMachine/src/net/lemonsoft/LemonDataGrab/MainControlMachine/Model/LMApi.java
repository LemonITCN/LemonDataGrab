package net.lemonsoft.LemonDataGrab.MainControlMachine.Model;

import java.lang.reflect.Method;

/**
 * 模型类 - API
 * Created by LiuRi on 16/5/8.
 */
public class LMApi extends LM {

    private String URL;// 所属的URL
    private String[] parameters;// 需要传入的参数数组
    private String[] parameterIntros;// 对传入参数的描述数组,必须与参数数组的元素数量一致
    private int[] errors;// 可能出现的错误码数组
    private String description;// 对该API的描述
    private Class apiClass;// 执行方法的所属类
    private Method method;// 执行的方法

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public String[] getParameterIntros() {
        return parameterIntros;
    }

    public void setParameterIntros(String[] parameterIntros) {
        this.parameterIntros = parameterIntros;
    }

    public int[] getErrors() {
        return errors;
    }

    public void setErrors(int[] errors) {
        this.errors = errors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class getApiClass() {
        return apiClass;
    }

    public void setApiClass(Class apiClass) {
        this.apiClass = apiClass;
    }
}
