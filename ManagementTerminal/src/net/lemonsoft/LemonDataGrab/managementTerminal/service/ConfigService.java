package net.lemonsoft.LemonDataGrab.managementTerminal.service;

import net.lemonsoft.LemonDataGrab.managementTerminal.dao.ConfigDao;
import net.lemonsoft.LemonDataGrab.managementTerminal.entity.ConfigEntity;

/**
 * 业务层 - 配置
 * Created by liuri on 6/1/16.
 */
public class ConfigService extends Service<ConfigEntity, ConfigDao> {

    /**
     * 通过配置键来获取 值
     *
     * @param key 配置项的键
     * @return 配置项对象
     * @throws Exception
     */
    public ConfigEntity getConfigByKey(String key) throws Exception {
        return this.getDao().getConfigByKey(key);
    }

    /**
     * 判断是否有这个配置项
     *
     * @param key 配置项的键
     * @return 是否有这个配置项的布尔值
     */
    public boolean isHaveTheConfigByKey(String key) {
        return this.getDao().isHaveTheConfigByKey(key);
    }

    /**
     * 通过配置项的键来删除配置项
     *
     * @param key 要删除配置项的键
     * @return 删除配置项的成功结果布尔值
     */
    public boolean deleteConfigByKey(String key) {
        return this.getDao().deleteConfigByKey(key);
    }

}
