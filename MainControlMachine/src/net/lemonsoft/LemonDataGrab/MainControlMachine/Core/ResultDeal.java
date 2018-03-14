package net.lemonsoft.LemonDataGrab.MainControlMachine.Core;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEErrorResult;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEResult;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LESession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LETask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENResponseError;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Enum.LENTaskState;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMInfo;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSErrorResult;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSResult;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSSession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSTask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LULog;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUTime;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResultDeal {

    public Object dealResult(String sessionFingerprint, String fingerprint, String state, FileItem data) throws Exception {
        LSSession lsSession = new LSSession();
        LESession leSession = lsSession.getSessionBySessionFingerprint(sessionFingerprint);
        if (leSession == null) {// 会话指纹已经过期
            return LENResponseError.REQUEST_SESSION_FINGERPRINT_INVALID;
        }
        LSTask lsTask = new LSTask();
        LETask leTask = lsTask.getTaskByFingerprint(fingerprint);
        if (leTask == null) {// 任务指纹过期
            return LENResponseError.TASK_FINGERPRINT_INVALID;
        }
        String root = Thread.currentThread().getContextClassLoader().getResource("") + File.pathSeparator;
        root = root.split(":")[1] + "results" + File.separator;
        new File(root).mkdirs();
        String dataPath = root + data.getName();
        System.out.println("root = = = = = = = = = = = " + root);
        outToFile(data, dataPath);
        if (state.equals("0")) {// 错误数据
            LEErrorResult errorResult = new LEErrorResult();
            errorResult.setSessionFingerprint(leSession.getSessionFingerprint());
            errorResult.setFingerprint(leTask.getFingerprint());
            errorResult.setRecoveryTime(LUTime.getUnixTimeStamp());
            LSErrorResult lsErrorResult = new LSErrorResult();
            if (lsErrorResult.add(errorResult)) {
                return new LMInfo("数据反馈成功");
            }
        } else {// 正常数据
            LEResult result = new LEResult();
            result.setSessionFingerprint(leSession.getSessionFingerprint());
            result.setFingerprint(leTask.getFingerprint());
            result.setLog("");
            result.setData("");
            result.setRecoveryTime(LUTime.getUnixTimeStamp());
            LSResult lsResult = new LSResult();
            leTask.setState(LENTaskState.TASK_STATE_GRAB_RESULT_SUCCESS.getStateCode().intValue());
            lsTask.update(leTask);
            if (lsResult.add(result)) {
                LCTaskManager.sharedInstance().taskAlreadyDistributedPool.remove(leTask.getFingerprint());
                LULog.info("数据反馈成功！指定任务：" + leTask.getFingerprint() + "　已经成功回传采集结果");
                return new LMInfo("数据反馈成功");
            }
        }
        return LENResponseError.REQUEST_SYSTEM_EXCEPTION;
    }

    private void outToFile(FileItem fileItem, String path) throws IOException {
        InputStream is = fileItem.getInputStream();
        FileOutputStream fos = new FileOutputStream(path);
        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            fos.write(buffer, 0, buffer.length);
        }
        fos.flush();
        fos.close();
    }

}
