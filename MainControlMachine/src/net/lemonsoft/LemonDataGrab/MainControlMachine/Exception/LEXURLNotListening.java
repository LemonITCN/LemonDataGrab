package net.lemonsoft.LemonDataGrab.MainControlMachine.Exception;

/**
 * 自定义异常信息类 - URL没有被监听
 * Created by LiuRi on 16/5/8.
 */
public class LEXURLNotListening extends Exception {

    public LEXURLNotListening() {
        super("当前的URL未被监听,无对应解析函数.");
    }

}
