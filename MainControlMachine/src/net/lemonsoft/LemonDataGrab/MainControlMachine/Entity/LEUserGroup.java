package net.lemonsoft.LemonDataGrab.MainControlMachine.Entity;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntity;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntityProperty;

/**
 * 实体类 - 用户组
 * Created by LiuRi on 16/4/24.
 */
@LANEntity(tableName = "ldg_usergroup" , description = "普通用户组表")
public class LEUserGroup implements LE {

    @LANEntityProperty(columnName = "usgr_id" , description = "用户组id" , primaryKey = true , autoIncrement = true)
    private Long ugid;

    @LANEntityProperty(columnName = "usgr_name" , description = "用户组的名称")
    private String name;

    @LANEntityProperty(columnName = "usgr_description" , description = "用户组的描述")
    private String description;

    public Long getUgid() {
        return ugid;
    }

    public void setUgid(Long ugid) {
        this.ugid = ugid;
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
