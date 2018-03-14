package net.lemonsoft.LemonDataGrab.MainControlMachine.Model.Response;

/**
 * 模型类 - 响应 - 错误响应
 * Created by LiuRi on 16/4/25.
 */
public class LMRError extends LMR {

    /**
     * 错误响应默认请求结果为false
     */
    private boolean success = false;

    /**
     * 错误的原因,该字段为产生该错误码的原因,通常不返回给用户,用于生成文档以及日志等作用
     */
    private String reason = "";

    /**
     * 构造函数
     *
     * @param errCode 错误码
     * @param errInfo 错误返回信息
     */
    public LMRError(int errCode, String errInfo, String reason) {
        this.setErrorCode(errCode);
        this.setInfo(errInfo);
        this.reason = reason;
    }

    /**
     * 获取错误产生的原因
     *
     * @return 错误产生的原因的字符串
     */
    public String getReason() {
        return reason;
    }
}
