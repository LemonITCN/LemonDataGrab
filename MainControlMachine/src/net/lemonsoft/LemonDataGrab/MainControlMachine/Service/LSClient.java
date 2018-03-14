package net.lemonsoft.LemonDataGrab.MainControlMachine.Service;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Dao.LDClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 业务层 - 客户端
 * Created by LiuRi on 16/5/7.
 */
public class LSClient extends LS<LEClient, LDClient> {

    /**
     * 通过客户端指纹删除客户端对象
     *
     * @param clientFingerprint 要删除的客户端对象的客户端指纹
     * @return 删除的成功与否的boolean值
     */
    public boolean removeClientByClientFingerprint(String clientFingerprint) {
        return this.getDao().removeClientByClientFingerprint(clientFingerprint);
    }

    /**
     * 通过设备标识删除客户端对象
     *
     * @param identity 要删除的客户端对象的设备标识
     * @return 删除的成功与否的boolean值
     */
    public boolean removeClientByIdentity(String identity) {
        return this.getDao().removeClientByIdentity(identity);
    }

    /**
     * 判断这个设备标识是否已经被注册过设备
     *
     * @param identity 要查询的设备标识
     * @return 是否被注册的boolean值
     */
    public boolean isHaveClientByIdentity(String identity) {
        return this.getDao().isHaveClientByIdentity(identity);
    }

    /**
     * 判断这个客户端指纹是否存在
     *
     * @param clientFingerprint 要查询的客户端指纹
     * @return 是否存在这个客户端指纹的boolean值
     */
    public boolean isHaveClientByClientFingerprint(String clientFingerprint) {
        return this.getDao().isHaveClientByClientFingerprint(clientFingerprint);
    }

    /**
     * 通过设备标识查询客户端
     *
     * @param identity 要查询的客户端的设备标识
     * @return 查询到的客户端对象
     * @throws Exception
     */
    public LEClient getClientByIdentity(String identity) throws Exception {
        return this.getDao().getClientByIdentity(identity);
    }

    /**
     * 通过客户端指纹查询客户端对象
     *
     * @param clientFingerprint 要查询的客户端的客户端指纹
     * @return 查询到的客户端对象
     * @throws Exception
     */
    public LEClient getClientByClientFingerprint(String clientFingerprint) throws Exception {
        return this.getDao().getClientByClientFingerprint(clientFingerprint);
    }

    /**
     * 生成一个客户端指纹字符串
     *
     * @return 生成一个客户端指纹
     */
    public String createNewClientFingerprint() {
        return UUID.randomUUID().toString();
    }

    /**
     * 分页查询在线客户端信息
     *
     * @param startIndex 起始查询的索引
     * @param count      查询的数据量
     * @return 在线客户端信息的list
     */
    public ArrayList<Map<String, Object>> getOnlineClientInfoList(String startIndex, String count) {
        return this.getDao().getOnlineClientInfoList(Long.valueOf(startIndex) , Long.valueOf(count));
    }

    /**
     * 查询所有在线客户端数量
     * @return 在线客户端的数量
     */
    public Integer countOnlineClient() {
        return this.getDao().countOnlineClient();
    }

    /**
     * 查询各个分类的在线客户端的数量
     *
     * @return 查询的数量的结果
     */
    public List<Map<String, Object>> countOnlineClientWithType() {
        return this.getDao().countOnlineClientWithType();
    }

}
