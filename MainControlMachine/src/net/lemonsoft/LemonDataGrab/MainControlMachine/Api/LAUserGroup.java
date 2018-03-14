package net.lemonsoft.LemonDataGrab.MainControlMachine.Api;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANApi;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENResponseError;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMData;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSUserGroup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 接口 - 用户组
 * Created by LiuRi on 16/9/10.
 */
public class LAUserGroup extends LA<LSUserGroup> {

    @LANApi(URL = "/UserGroup/GetAll",
            parameters = {"sessionFingerprint"},
            parameterIntros = {"会话的指纹,要获取用户组信息列表的用户会话的会话指纹"},
            description = "获取所有用户组信息列表",
            errors = {1005})
    public Object getUserInfoList(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            return new LMData(this.getService().queryAllUserGroup());
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

}
