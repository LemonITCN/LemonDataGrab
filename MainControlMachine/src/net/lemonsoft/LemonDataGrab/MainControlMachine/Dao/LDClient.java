package net.lemonsoft.LemonDataGrab.MainControlMachine.Dao;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Exception.LEXEntityAnnotationIncomplete;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * DAO层 - 客户端
 * Created by LiuRi on 16/5/6.
 */
public class LDClient extends LD<LEClient> {

    // 查询在线客户端信息的SQL
    private static final String SQL_QUERY_ONLINE_CLIENT_INFO = "SELECT" +
            "  ldg_session.sess_id     AS sessionId," +
            "  ldg_session.sess_client AS clientId," +
            "  ldg_session.sess_user   AS userId," +
            "  ldg_client.clie_system  AS clientSystem," +
            "  ldg_client.clie_device  AS clientDeviceName," +
            "  ldg_user.user_name      AS userName," +
            "  ldg_usergroup.usgr_name AS userUsergroup," +
            "  ldg_session.sess_ip     AS clientIp," +
            "  ldg_session.sess_state  AS sessionState " +
            "FROM ldg_session, ldg_client, ldg_user, ldg_usergroup " +
            "WHERE ldg_session.sess_client = ldg_client.clie_id" +
            "      AND ldg_session.sess_user = ldg_user.user_id" +
            "      AND ldg_user.user_usergroup = ldg_usergroup.usgr_id" +
            "      AND ldg_session.sess_state > 0 " +
            "ORDER BY ldg_session.sess_id DESC " +
            "LIMIT ?, ?";

    // 查询所有在线客户端数量的sql
    private static final String SQL_QUERY_ONLINE_CLIENT_COUNT =
            "SELECT COUNT(ldg_session.sess_id) AS sessionCount " +
                    "FROM ldg_session " +
                    "WHERE ldg_session.sess_state > 0";

    // 查询各个分类的在线客户端的数量的sql
    private static final String SQL_QUERY_ONLINE_CLIENT_COUNT_WITH_TYPE = "SELECT" +
            "  COUNT(ldg_session.sess_id) AS sessionCount," +
            "  IF(ldg_session.sess_state = 1, '采集终端', '管理终端')" +
            "                             AS sessionTypeName " +
            "FROM ldg_session " +
            "WHERE ldg_session.sess_state > 0 " +
            "GROUP BY ldg_session.sess_state";

    /**
     * 通过客户端指纹删除客户端对象
     *
     * @param clientFingerprint 要删除的客户端对象的客户端指纹
     * @return 删除的成功与否的boolean值
     */
    public boolean removeClientByClientFingerprint(String clientFingerprint) {
        return this.deleteBySQLCondition("WHERE clie_clientFingerprint = ?", clientFingerprint) > 0;
    }

    /**
     * 通过设备标识删除客户端对象
     *
     * @param identity 要删除的客户端对象的设备标识
     * @return 删除的成功与否的boolean值
     */
    public boolean removeClientByIdentity(String identity) {
        return this.deleteBySQLCondition("WHERE clie_identity = ?", identity) > 0;
    }

    /**
     * 判断这个设备标识是否已经被注册过设备
     *
     * @param identity 要查询的设备标识
     * @return 是否被注册的boolean值
     */
    public boolean isHaveClientByIdentity(String identity) {
        return this.countBySQLCondition("WHERE clie_identity = ?", identity) > 0;
    }

    /**
     * 判断这个客户端指纹是否存在
     *
     * @param clientFingerprint 要查询的客户端指纹
     * @return 是否存在这个客户端指纹的boolean值
     */
    public boolean isHaveClientByClientFingerprint(String clientFingerprint) {
        return this.countBySQLCondition("WHERE clie_clientFingerprint = ?", clientFingerprint) > 0;
    }

    /**
     * 通过设备标识查询客户端
     *
     * @param identity 要查询的客户端的设备标识
     * @return 查询到的客户端对象
     * @throws Exception
     */
    public LEClient getClientByIdentity(String identity) throws Exception {
        ArrayList<LEClient> result = this.getBySQLCondition("WHERE clie_identity = ?", identity);
        if (result.size() > 0) {// 通过设备标识查询到了客户端对象
            return result.get(0);
        }
        return null;
    }

    /**
     * 通过客户端指纹查询客户端对象
     *
     * @param clientFingerprint 要查询的客户端的客户端指纹
     * @return 查询到的客户端对象
     * @throws Exception
     */
    public LEClient getClientByClientFingerprint(String clientFingerprint) throws Exception {
        ArrayList<LEClient> result = this.getBySQLCondition("WHERE clie_clientFingerprint = ?", clientFingerprint);
        if (result.size() > 0) {// 通过客户端指纹查询到了客户端对象
            return result.get(0);
        }
        return null;
    }

    /**
     * 分页查询在线客户端信息
     *
     * @param startIndex 起始查询的索引
     * @param count      查询的数据量
     * @return 在线客户端信息的list
     */
    public ArrayList<Map<String, Object>> getOnlineClientInfoList(Long startIndex, Long count) {
        return (ArrayList<Map<String, Object>>) LUDatabase.query(SQL_QUERY_ONLINE_CLIENT_INFO, startIndex, count);
    }

    /**
     * 查询所有在线客户端数量
     *
     * @return 在线客户端的数量
     */
    public Integer countOnlineClient() {
        return LUDatabase.count(SQL_QUERY_ONLINE_CLIENT_COUNT);
    }

    /**
     * 查询各个分类的在线客户端的数量
     *
     * @return 查询的数量的结果
     */
    public List<Map<String, Object>> countOnlineClientWithType() {
        return LUDatabase.query(SQL_QUERY_ONLINE_CLIENT_COUNT_WITH_TYPE);
    }

}