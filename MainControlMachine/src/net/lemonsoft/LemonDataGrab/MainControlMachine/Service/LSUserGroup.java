package net.lemonsoft.LemonDataGrab.MainControlMachine.Service;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Dao.LDUserGroup;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEUserGroup;

import java.util.ArrayList;

/**
 * Created by LiuRi on 5/16/16.
 */
public class LSUserGroup extends LS<LEUserGroup, LDUserGroup> {

    /**
     * 查询所有的用户组
     *
     * @return 所有的用户组信息list
     */
    public ArrayList<LEUserGroup> queryAllUserGroup() {
        try {
            return this.getDao().getBySQLCondition("");
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
