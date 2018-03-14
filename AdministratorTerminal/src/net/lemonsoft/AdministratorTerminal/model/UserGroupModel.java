package net.lemonsoft.AdministratorTerminal.model;

/**
 * 模型 - 用户组
 * Created by LiuRi on 16/9/10.
 */
public class UserGroupModel {

    private String id;// 用户组id
    private String name;// 用户组名称
    private String description;// 用户组介绍

    public UserGroupModel(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
