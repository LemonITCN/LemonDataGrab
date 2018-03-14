package net.lemonsoft.DataGrab.entity;

import net.lemonsoft.DataGrab.annotation.EntityAnnotation;
import net.lemonsoft.DataGrab.annotation.EntityPropertyAnnotation;

/**
 * 配置信息实体对象
 * Created by liuri on 6/1/16.
 */
@EntityAnnotation(tableName = "ldg_config", description = "配置信息表,做本地键值对配置缓存")
public class ConfigEntity implements Entity {

    @EntityPropertyAnnotation(columnName = "conf_id", description = "配置表的主键,id序号", primaryKey = true, autoIncrement = true)
    private Integer id;

    @EntityPropertyAnnotation(columnName = "conf_key", description = "配置表的键值对中的键")
    private String key;

    @EntityPropertyAnnotation(columnName = "conf_value", description = "配置表的键值对中的值")
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
