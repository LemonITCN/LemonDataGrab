package net.lemonsoft.LemonDataGrab.MainControlMachine.Service;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Dao.LDUser;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEUser;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUDatabase;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUString;

import java.util.List;
import java.util.Map;

/**
 * 业务层 - 用户
 * Created by LiuRi on 5/16/16.
 */
public class LSUser extends LS<LEUser, LDUser> {

    /**
     * 检查指定账号和密码是否匹配
     *
     * @param number   要检查的账号
     * @param password 要检查的密码
     * @return 检查的结果布尔值
     * @throws Exception
     */
    public boolean checkNumberAndPassword(String number, String password) throws Exception {
        LEUser leUser = this.getUserByNumber(number);
        String pwd = LUString.md5(password);
        return leUser.getPassword().equals(pwd);
    }

    /**
     * 是否存在这个账号(正则识别账号类型)
     *
     * @param number 要验证个账号
     * @return 是否存在这个账号的布尔值
     */
    public boolean isHaveTheNumber(String number) {
        if (LUString.formatCheck_email(number)) {// email格式
            return isHaveTheEmail(number);
        } else if (LUString.formatCheck_phone(number)) {// phone格式
            return isHaveThePhone(number);
        } else {// 普通username
            return isHaveTheUsername(number);
        }
    }

    /**
     * 通过账号来获取用户实体对象(正则识别账号类型)
     *
     * @param number 要获取的用户对象的账号
     * @return 获取到的用户对象
     */
    public LEUser getUserByNumber(String number) throws Exception {
        if (LUString.formatCheck_email(number)) {// email格式
            return getUserByEmail(number);
        } else if (LUString.formatCheck_phone(number)) {// phone格式
            return getUserByPhone(number);
        } else {// 普通username
            return getUserByUsername(number);
        }
    }

    /**
     * 是否这个email被占用
     *
     * @param email 要验证的email
     * @return 是否被占用的布尔值
     */
    public boolean isHaveTheEmail(String email) {
        return this.getDao().isHaveTheEmail(email);
    }

    /**
     * 是否这个phone被占用
     *
     * @param phone 要验证的phone
     * @return 是否被占用的布尔值
     */
    public boolean isHaveThePhone(String phone) {
        return this.getDao().isHaveThePhone(phone);
    }

    /**
     * 是否这个username被占用
     *
     * @param username 要验证的username
     * @return 是否被占用的布尔值
     */
    public boolean isHaveTheUsername(String username) {
        return this.getDao().isHaveTheUsername(username);
    }

    /**
     * 通过email获取用户实体对象
     *
     * @param email 要获取的用户的email
     * @return 获取到的用户实体对象
     * @throws Exception
     */

    public LEUser getUserByEmail(String email) throws Exception {
        return this.getDao().getUserByEmail(email);
    }

    /**
     * 通过phone获取用户实体对象
     *
     * @param phone 要获取的用户的phone
     * @return 获取到的用户实体对象
     * @throws Exception
     */
    public LEUser getUserByPhone(String phone) throws Exception {
        return this.getDao().getUserByPhone(phone);
    }

    /**
     * 通过username获取用户实体对象
     *
     * @param username 要获取的用户的username
     * @return 获取到的用户实体对象
     * @throws Exception
     */
    public LEUser getUserByUsername(String username) throws Exception {
        return this.getDao().getUserByUsername(username);
    }

    /**
     * 设置指定用户的账号信息,通过正则识别账号的类型
     *
     * @param leUser 要设置账号的用户对象
     * @param number 用户账号
     * @return 设置后的用户对象
     */
    public LEUser setUserNumber(LEUser leUser, String number) {
        if (LUString.formatCheck_email(number)) {// email格式
            leUser.setEmail(number);
        } else if (LUString.formatCheck_phone(number)) {// phone格式
            leUser.setPhone(number);
        } else {// 普通username
            leUser.setUsername(number);
        }
        if (leUser.getEmail() == null) {
            leUser.setEmail("");
        }
        if (leUser.getPhone() == null) {
            leUser.setPhone("");
        }
        if (leUser.getUsername() == null) {
            leUser.setUsername("");
        }
        return leUser;
    }

    /**
     * 分页查询所有用户的信息
     *
     * @param startIndex 获取数据的起始索引
     * @param count      获取数据的数量
     * @return 获取到的数据list
     */
    public List<Map<String, Object>> getUserInfoList(String startIndex, String count) {
        return getDao().getUserInfoList(Long.valueOf(startIndex) , Long.valueOf(count));
    }

    /**
     * 查询所有用户的数量
     *
     * @return 当前数据库中所有用户的数量
     */
    public Integer countAllUser() {
        return this.getDao().countAllUser();
    }

}
