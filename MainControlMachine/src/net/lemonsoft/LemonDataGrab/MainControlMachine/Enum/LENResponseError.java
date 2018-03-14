package net.lemonsoft.LemonDataGrab.MainControlMachine.Enum;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.Response.LMRError;

/**
 * 枚举类型 - 响应错误枚举
 * Created by LiuRi on 16/4/25.
 */
public enum LENResponseError {

    REQUEST_PARAMETER_ERROR(new LMRError(1000, "请求失败,请求的参数错误(不合法)或不完整", "用户请求过程中传入的参数与系统要求的参数不匹配,缺少参数或者有非法参数")),

    REQUEST_URL_NOT_LISTENING(new LMRError(1001, "请求失败,您请求的地址不合法,我们无法给予您响应", "用户请求的URI没有被监听,没有对应的响应函数来对其进行处理")),

    REQUEST_SYSTEM_EXCEPTION(new LMRError(1002, "请求失败,系统内部异常", "通常系统内部发生(抛出)了一个未做任何处理的异常,无法对外部做出正确的响应")),

    REQUEST_CLIENT_FINGERPRINT_INVALID(new LMRError(1003, "请求失败,提供的客户端指纹非法或已过期", "客户端提供的客户端指纹不是正确的格式或者已经过期")),

    REQUEST_SESSION_FINGERPRINT_INVALID(new LMRError(1004, "请求失败,请求的会话指纹非法或已过期", "客户端提供的会话指纹不是正确的格式或者已经过期")),

    REQUEST_PERMISSION_INVALID(new LMRError(1005, "请求失败,您的权限无效", "客户端的请求非法,比如非管理员用户想要登录管理员身份,或者用户访问其没有权限的接口请求")),

    USER_SIGNIN_NUMBER_OR_PASSWORD_ERROR(new LMRError(2000, "登录失败,账号或密码错误", "用户登录请求中的账号或者密码存在错误,也有可能是因为用户输入了一个不存在的账号")),

    USER_SIGNUP_NUMBER_ALREADY_EXISTS(new LMRError(2001, "注册失败,您要注册的账号已经被占用,请您尝试使用其他账号", "用户注册请求中的账号已经被其他的用户注册过,无法再次进行注册")),

    TASK_FINGERPRINT_INVALID(new LMRError(3000, "提交失败，任务指纹无效", "任务指纹不存在或者已经过期、无效。")),

    CONNECTION_ERROR_AIM_NOT_ONLINE(new LMRError(4000, "与指定终端链接失败，对方已经离线！", "想请求另一个终端中的数据，但是目标终端已经离线或无法建立链接"));

    private LMRError error;

    LENResponseError(LMRError error) {
        this.error = error;
    }

    public int getErrorCode() {
        return this.error.getErrorCode();
    }

    public String getInfo() {
        return this.error.getInfo();
    }

    public String getReason() {
        return this.error.getReason();
    }

}
