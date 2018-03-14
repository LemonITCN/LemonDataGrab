package net.lemonsoft.AdministratorTerminal.model;

/**
 * 模型 - 用户信息
 * Created by LiuRi on 16/9/10.
 */
public class UserInfoModel {
    private String userId;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userUsername;
    private String userScore;
    private String usergroupId;
    private String usergroupName;

    public UserInfoModel(String userId, String userName, String userPhone,
                         String userEmail, String userUsername, String userScore,
                         String usergroupId, String usergroupName) {
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userUsername = userUsername;
        this.userScore = userScore;
        this.usergroupId = usergroupId;
        this.usergroupName = usergroupName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }

    public String getUsergroupId() {
        return usergroupId;
    }

    public void setUsergroupId(String usergroupId) {
        this.usergroupId = usergroupId;
    }

    public String getUsergroupName() {
        return usergroupName;
    }

    public void setUsergroupName(String usergroupName) {
        this.usergroupName = usergroupName;
    }
}
