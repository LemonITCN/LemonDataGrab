package net.lemonsoft.AdministratorTerminal.model;

/**
 * 采集结果项目模型
 * Created by LiuRi on 16/11/1.
 */
public class ResultItemModel {

    private String key;
    private String value;

    public ResultItemModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
