package net.lemonsoft.LemonDataGrab.MainControlMachine.Api;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANApi;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Core.LCTaskManager;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Core.ResultDeal;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEErrorResult;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEResult;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LESession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LETask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENResponseError;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENTaskState;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMData;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMInfo;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSErrorResult;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSResult;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSSession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSTask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LULog;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUTime;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * API - 结果
 * Created by LiuRi on 16/8/24.
 */
public class LAResult extends LA<LSResult> {

    @LANApi(URL = "/Result/Response",
            parameters = {},
            parameterIntros = {"回传采集结果的终端会话指纹", "任务的指纹", "任务的数据", "任务的状态，0失败，1成功，2警告"},
            description = "采集结果数据回传接口",
            errors = {1004, 3000})
    public Object response(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = new ArrayList<>();
        FileItem dataItem = null;
        String sessionFingerprint = null;
        String fingerprint = null;
        String state = null;
        try {
            items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    // 普通的键值对
                    if (item.getFieldName().equals("sessionFingerprint"))
                        sessionFingerprint = item.getString();
                    else if (item.getFieldName().equals("fingerprint"))
                        fingerprint = item.getString();
                    else if (item.getFieldName().equals("state"))
                        state = item.getString();
                } else {
                    // multipart
                    if (item.getFieldName().equals("data"))
                        dataItem = item;
                }
            }
            return new ResultDeal().dealResult(sessionFingerprint, fingerprint, state, dataItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//    @LANApi(URL = "/Result/Response",
//            parameters = {"sessionFingerprint", "fingerprint", "data", "log", "state"},
//            parameterIntros = {"回传采集结果的终端会话指纹", "任务的指纹", "任务的数据", "任务的状态，0失败，1成功，2警告"},
//            description = "采集结果数据回传接口",
//            errors = {1004, 3000})
//    public Object response(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
//
//    }

    @LANApi(URL = "/Result/query",
            parameters = {"sessionFingerprint", "taskFingerprint"},
            parameterIntros = {"获取任务数量的管理员的会话指纹", "要查询结果的任务的指纹"},
            description = "查询指定任务的采集结果",
            errors = {1005})
    public Object resultQuery(HttpServletRequest request, HttpServletResponse response, HashMap<String, Object> parameters) throws Exception {
        if (this.checkIsAdministratorBySessionFingerprint((String) parameters.get("sessionFingerprint"))) {
            // 是管理员
            try {
                return new LSResult().getByFingerprint((String) parameters.get("taskFingerprint"));
            } catch (Exception e) {
                return LENResponseError.REQUEST_SYSTEM_EXCEPTION;
            }
        } else {
            // 不是管理员身份
            return LENResponseError.REQUEST_PERMISSION_INVALID;
        }
    }

}
