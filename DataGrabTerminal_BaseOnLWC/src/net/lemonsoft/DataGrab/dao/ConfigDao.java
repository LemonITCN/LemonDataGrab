package net.lemonsoft.DataGrab.dao;

import net.lemonsoft.DataGrab.entity.ConfigEntity;

import java.util.ArrayList;

/**
 * Dao - 配置
 * Created by liuri on 6/1/16.
 */
public class ConfigDao extends Dao<ConfigEntity> {

    /**
     * 通过配置键来获取 值
     *
     * @param key 配置项的键
     * @return 配置项对象
     * @throws Exception
     */
    public ConfigEntity getConfigByKey(String key) throws Exception {
        ArrayList<ConfigEntity> result = this.getBySQLCondition("WHERE conf_key = ?", key);
        if (result.size() > 0) {// 通过设备标识查询到了客户端对象
            return result.get(0);
        }
        return null;
    }

    /**
     * 判断是否有这个配置项
     *
     * @param key 配置项的键
     * @return 是否有这个配置项的布尔值
     */
    public boolean isHaveTheConfigByKey(String key) {
        return this.countBySQLCondition("WHERE conf_key = ?", key) > 0;
    }

    /**
     * 通过配置项的键来删除配置项
     *
     * @param key 要删除配置项的键
     * @return 删除配置项的成功结果布尔值
     */
    public boolean deleteConfigByKey(String key) {
        return this.deleteBySQLCondition("WHERE conf_key = ?", key) > 0;
    }

}
