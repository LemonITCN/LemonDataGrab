package net.lemonsoft.LemonDataGrab.dataGrabTerminal.tool;

import net.lemonsoft.LemonDataGrab.dataGrabTerminal.entity.ConfigEntity;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.service.ConfigService;

/**
 * 工具类 - 配置项
 * Created by liuri on 6/1/16.
 */
public class ConfigTool {

    private ConfigService configService;

    private static ConfigTool configTool;

    public static ConfigTool sharedInstance() {
        if (configTool == null)
            configTool = new ConfigTool();
        return configTool;
    }

    private ConfigTool() {
        this.configService = new ConfigService();
    }

    /**
     * 设置配置项
     *
     * @param key   配置项的键
     * @param value 配置项的值
     * @return 配置项设置的成功性
     */
    public boolean set(String key, String value) throws Exception {
        if (configService.isHaveTheConfigByKey(key)) {
            // 存在这个key的配置项
            ConfigEntity configEntity = configService.getConfigByKey(key);
            configEntity.setValue(value);
            return configService.update(configEntity);
        } else {
            // 不存在这个key的配置项
            ConfigEntity configEntity = new ConfigEntity();
            configEntity.setKey(key);
            configEntity.setValue(value);
            return configService.add(configEntity);
        }
    }

    /**
     * 判断是否存在这个配置项
     *
     * @param key 要检查是否存在的配置项
     * @return 是否存在这个配置项的布尔值
     */
    public boolean contain(String key) {
        return configService.isHaveTheConfigByKey(key);
    }

    /**
     * 通过配置键来获取配置值
     *
     * @param key          配置项的键
     * @param defaultValue 当没有这个配置项的时候返回的默认值
     * @return 获取到的配置项的值
     */
    public String getOrDefault(String key, String defaultValue) throws Exception {
        ConfigEntity configEntity = configService.getConfigByKey(key);
        if (configEntity == null)
            return defaultValue;
        return configEntity.getValue();
    }

    /**
     * 移除指定配置项
     *
     * @param key 要移除的配置项的键
     * @return 是否移除成功的布尔值
     */
    public boolean remove(String key) {
        configService.deleteConfigByKey(key);
        return !configService.isHaveTheConfigByKey(key);
    }

}
