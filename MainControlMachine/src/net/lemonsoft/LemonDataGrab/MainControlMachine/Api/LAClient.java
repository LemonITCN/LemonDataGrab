package net.lemonsoft.LemonDataGrab.MainControlMachine.Api;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANApi;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENResponseError;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMData;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMInfo;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Processor.LPClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSClient;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Scanner;

/**
 * API - 客户端
 * Created by LiuRi on 16/5/8.
 */
public class LAClient extends LA<LSClient> {

    @LANApi(URL = "/Client/SignUp",
            parameters = {"identity", "system", "terminalType", "device", "version"},
            parameterIntros = {"设备的唯一标识字符串", "客户端的类型,比如iOS还是Android", "设备的名称,如iPhone6sPlus", "设备的软件版本号,如1.0"},
            description = "注册客户端的接口",
            errors = {1002})
    public Object signUpClient(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        String identity = (String) parameters.get("identity");
        String system = (String) parameters.get("system");
        Long terminalType = Long.parseLong((String) parameters.get("terminalType"));
        String device = (String) parameters.get("device");
        String version = (String) parameters.get("version");
        if (this.getService().isHaveClientByIdentity(identity)) {
            // 这个设备标识已经注册过客户端
            LEClient client = this.getService().getClientByIdentity(identity);
            LPClient.clearSensitiveData(client);
            return new LMInfo(client.getClientFingerprint());
        } else {
            // 设备标识没有注册过
            LEClient client = new LEClient();
            client.setClientFingerprint(this.getService().createNewClientFingerprint());
            client.setDevice(device);
            client.setIdentity(identity);
            client.setSystem(system);
            client.setTerminalType(terminalType);
            client.setVersion(version);
            client.setRegistrationTime(LUTime.getUnixTimeStamp());
            if (this.getService().add(client)) {
                // 注册客户端成功
                return new LMInfo(client.getClientFingerprint());
            }
            return LENResponseError.REQUEST_SYSTEM_EXCEPTION;// 数据库中插入数据失败
        }
    }

    @LANApi(URL = "/Client/GetOnlineList",
            parameters = {"sessionFingerprint", "startIndex", "count"},
            parameterIntros = {"获取在线客户端列表的管理员会话指纹", "获取列表的起始索引", "获取的数据数量"},
            description = "获取在线客户端列表",
            errors = {1002})
    public Object getOnlineClientList(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            return new LMData(getService()
                    .getOnlineClientInfoList((String) parameters.get("startIndex"), (String) parameters.get("count")));
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

    @LANApi(URL = "/Client/CountOnline",
            parameters = {"sessionFingerprint"},
            parameterIntros = {"获取在线客户端数量的管理员会话指纹"},
            description = "获取在线客户端的数量",
            errors = {1002})
    public Object countOnlineClient(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            return new LMData(getService().countOnlineClient());
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

    @LANApi(URL = "/Client/CountOnlineWithType",
            parameters = {"sessionFingerprint"},
            parameterIntros = {"获取各个分类的在线客户端数量的管理员会话指纹"},
            description = "获取各个分类的在线客户端的数量",
            errors = {1002})
    public Object countOnlineClientWithType(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            return new LMData(getService().countOnlineClientWithType());
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

}
