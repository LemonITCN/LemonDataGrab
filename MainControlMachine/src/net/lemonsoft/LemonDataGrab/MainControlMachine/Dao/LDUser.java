package net.lemonsoft.LemonDataGrab.MainControlMachine.Dao;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEUser;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LS;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by LiuRi on 16/4/24.
 */
public class LDUser extends LD<LEUser> {

    // 查询指定范围的用户信息的sql
    private static final String SQL_QUERY_USER_INFO_LIST =
            "SELECT ldg_user.user_id AS userId, ldg_user.user_name AS userName, ldg_user.user_phone AS userPhone," +
                    "  ldg_user.user_email AS userEmail, ldg_user.user_username AS userUsername, ldg_user.user_score AS userScore," +
                    "  ldg_usergroup.usgr_id AS usergroupId, ldg_usergroup.usgr_name AS usergroupName " +
                    "FROM ldg_user , ldg_usergroup " +
                    "WHERE ldg_user.user_usergroup = ldg_usergroup.usgr_id " +
                    "ORDER BY ldg_user.user_id ASC " +
                    "LIMIT ? , ?";

    // 查询所有用户的数量
    private static final String SQL_QUERY_USER_COUNT =
            "SELECT COUNT(ldg_user.user_id) AS userCount " +
                    "FROM ldg_user";

    /**
     * 是否这个email被占用
     *
     * @param email 要验证的email
     * @return 是否被占用的布尔值
     */
    public boolean isHaveTheEmail(String email) {
        return this.countBySQLCondition("WHERE user_email=?", email) > 0;
    }

    /**
     * 是否这个phone被占用
     *
     * @param phone 要验证的phone
     * @return 是否被占用的布尔值
     */
    public boolean isHaveThePhone(String phone) {
        return this.countBySQLCondition("WHERE user_phone=?", phone) > 0;
    }

    /**
     * 是否这个username被占用
     *
     * @param username 要验证的username
     * @return 是否被占用的布尔值
     */
    public boolean isHaveTheUsername(String username) {
        return this.countBySQLCondition("WHERE user_username=?", username) > 0;
    }

    /**
     * 通过email获取用户实体对象
     *
     * @param email 要获取的用户的email
     * @return 获取到的用户实体对象
     * @throws Exception
     */
    public LEUser getUserByEmail(String email) throws Exception {
        ArrayList<LEUser> result = this.getBySQLCondition("WHERE user_email = ?", email);
        if (result.size() > 0) {// 通过设备标识查询到了客户端对象
            return result.get(0);
        }
        return null;
    }

    /**
     * 通过phone获取用户实体对象
     *
     * @param phone 要获取的用户的phone
     * @return 获取到的用户实体对象
     * @throws Exception
     */
    public LEUser getUserByPhone(String phone) throws Exception {
        ArrayList<LEUser> result = this.getBySQLCondition("WHERE user_phone = ?", phone);
        if (result.size() > 0) {// 通过设备标识查询到了客户端对象
            return result.get(0);
        }
        return null;
    }

    /**
     * 通过username获取用户实体对象
     *
     * @param username 要获取的用户的username
     * @return 获取到的用户实体对象
     * @throws Exception
     */
    public LEUser getUserByUsername(String username) throws Exception {
        ArrayList<LEUser> result = this.getBySQLCondition("WHERE user_username = ?", username);
        if (result.size() > 0) {// 通过设备标识查询到了客户端对象
            return result.get(0);
        }
        return null;
    }

    /**
     * 分页查询所有用户的信息
     *
     * @param startIndex 获取数据的起始索引
     * @param count      获取数据的数量
     * @return 获取到的数据list
     */
    public List<Map<String, Object>> getUserInfoList(Long startIndex, Long count) {
        return LUDatabase.query(SQL_QUERY_USER_INFO_LIST, startIndex, count);
    }

    /**
     * 查询所有用户的数量
     *
     * @return 当前数据库中所有用户的数量
     */
    public Integer countAllUser() {
        return LUDatabase.count(SQL_QUERY_USER_COUNT);
    }

}
