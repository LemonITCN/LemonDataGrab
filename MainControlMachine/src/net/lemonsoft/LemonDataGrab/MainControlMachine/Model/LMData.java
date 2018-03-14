package net.lemonsoft.LemonDataGrab.MainControlMachine.Model;

/**
 * 数据模型 数据
 * Created by lemonsoft on 2016/8/23.
 */
public class LMData extends LM {

    public LMData(Object data) {
        this.data = data;
    }

    private Object data;

    public Object getInfo() {
        return data;
    }

    public void setInfo(Object info) {
        this.data = info;
    }
}
