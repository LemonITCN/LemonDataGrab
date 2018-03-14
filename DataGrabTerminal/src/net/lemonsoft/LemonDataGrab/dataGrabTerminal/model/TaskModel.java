package net.lemonsoft.LemonDataGrab.dataGrabTerminal.model;

/**
 * 自定义模型 - 任务模型
 * Created by LiuRi on 5/14/16.
 */
public class TaskModel {

    /**
     * 任务标识
     */
    private String identity;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务描述
     */
    private String description;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
