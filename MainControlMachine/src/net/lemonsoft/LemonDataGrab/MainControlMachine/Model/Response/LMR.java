package net.lemonsoft.LemonDataGrab.MainControlMachine.Model.Response;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENResponseError;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LM;

/**
 * 模型类 - 响应 - 所有响应类的父类,所有的响应类必须直接或间接的继承此类
 * Created by LiuRi on 16/4/24.
 */
public abstract class LMR extends LM{

    /**
     * 请求是否成功,成功返回true,失败为false
     */
    private boolean success = true;

    /**
     * 包含的错误码,无错误的时候默认为0
     */
    private int errorCode = 0;

    /**
     * 本次响应携带的说明信息,可空
     */
    private String info = "";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
