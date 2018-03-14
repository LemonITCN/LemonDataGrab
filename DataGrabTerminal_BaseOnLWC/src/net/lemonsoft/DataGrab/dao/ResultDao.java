package net.lemonsoft.DataGrab.dao;

import net.lemonsoft.DataGrab.entity.ResultEntity;

import java.util.ArrayList;

/**
 * Created by lemonsoft on 16-11-27.
 */
public class ResultDao extends Dao<ResultEntity> {

    /**
     * 通过任务的指纹来获取采集的结果对象
     *
     * @param taskFingerprint 要查询的任务的指纹
     * @return 任务采集结果对象
     * @throws Exception
     */
    public ResultEntity getByFingerprint(String taskFingerprint) throws Exception {
        ArrayList<ResultEntity> result = this.getBySQLCondition("WHERE resu_fingerprint = ?", taskFingerprint);
        if (result.size() > 0) {// 通过任务指纹查询到了任务采集结果对象
            return result.get(0);
        }
        return null;
    }

}
