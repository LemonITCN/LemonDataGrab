package net.lemonsoft.LemonDataGrab.MainControlMachine.Entity;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntity;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANEntityProperty;

/**
 * 实体类 - 用户
 * Created by LiuRi on 16/4/24.
 */
@LANEntity(tableName = "ldg_user" , description = "普通用户表")
public class LEUser implements LE {

    @LANEntityProperty(columnName = "user_id" , description = "用户ID" , primaryKey = true , autoIncrement = true)
    private Long uid;

    @LANEntityProperty(columnName = "user_email" , description = "电子邮件地址")
    private String email;

    @LANEntityProperty(columnName = "user_phone" , description = "电话号码")
    private String phone;

    @LANEntityProperty(columnName = "user_username" , description = "登录用户名")
    private String username;

    @LANEntityProperty(columnName = "user_name" , description = "用户的真实姓名")
    private String name;

    @LANEntityProperty(columnName = "user_usergroup" , description = "用户所属用户组的id")
    private Long usergroup;

    @LANEntityProperty(columnName = "user_score" , description = "用户的经验积分，根据指定的规则进行积分计算")
    private Integer score;

    @LANEntityProperty(columnName = "user_password" , description = "用户密码")
    private String password;

    @LANEntityProperty(columnName = "user_registrationTime" , description = "用户注册的时间")
    private Long registrationTime;

    @LANEntityProperty(columnName = "user_identity" , description = "用户的身份码，注册时由系统生成的UUID，用作用户身份标识")
    private String identity;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(Long usergroup) {
        this.usergroup = usergroup;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Long registionTime) {
        this.registrationTime = registionTime;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
