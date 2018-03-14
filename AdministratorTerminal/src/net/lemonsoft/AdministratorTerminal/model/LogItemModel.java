package net.lemonsoft.AdministratorTerminal.model;

/**
 * 模型类 - 日志项目
 * Created by LiuRi on 16/11/1.
 */
public class LogItemModel {

    private String type;
    private String content;
    private String time;

    public LogItemModel(String type, String content, String time) {
        this.type = type;
        this.content = content;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
