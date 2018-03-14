package net.lemonsoft.LemonDataGrab.MainControlMachine.Enum;

/**
 * 自定义枚举 - 系统用户组ID
 * Created by LiuRi on 5/29/16.
 */
public enum LENSystemUserGroupId {

    BASIC_USER_GROUP_ID(1L),// 普通用户组
    ADMINISTRATOR_GROUP_ID(2L);// 管理员用户组

    private Long groupId;

    LENSystemUserGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }
}
