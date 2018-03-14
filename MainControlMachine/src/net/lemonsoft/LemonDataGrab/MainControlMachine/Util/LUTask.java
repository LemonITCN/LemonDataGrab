package net.lemonsoft.LemonDataGrab.MainControlMachine.Util;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LETask;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by LiuRi on 7/2/16.
 */
public class LUTask {

    public static final LETask getTaskByParametersMap(HashMap<String, Object> parameters, Long createTime, String fingerprint, Long taskDistributedNumber) {
        LETask leTask = new LETask();
        leTask.setName((String) parameters.get("name"));
        leTask.setFingerprint(fingerprint + "_" + taskDistributedNumber);
        leTask.setDescription((String) parameters.get("description"));
        Long publishTime = Long.parseLong((String) parameters.get("publishTime"));
        publishTime = publishTime < createTime ? createTime : publishTime;
        leTask.setCreateTime(createTime);
        leTask.setDistributeRepeatInterval(Long.parseLong((String) parameters.get("distributeRepeatInterval")));
        leTask.setPublishTime(publishTime + taskDistributedNumber * leTask.getDistributeRepeatInterval());
        leTask.setDistributeRepeatCount(Long.parseLong((String) parameters.get("distributeRepeatCount")));
        leTask.setDistributedNumber(taskDistributedNumber);
        leTask.setExecuteScript((String) parameters.get("executeScript"));
        leTask.setState(0);
        leTask.setExpired(Long.parseLong((String) parameters.get("expired")) + leTask.getPublishTime());
        leTask.setLevel(Long.valueOf((String) parameters.get("level")));
        leTask.setSession(0L);
        return leTask;
    }

}
