package net.lemonsoft.LemonDataGrab.MainControlMachine.Model;

/**
 * 模型类 - 信息 , 只包括一个属性的模型对象
 * Created by LiuRi on 5/29/16.
 */
public class LMInfo {

    public LMInfo(String info) {
        this.info = info;
    }

    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
