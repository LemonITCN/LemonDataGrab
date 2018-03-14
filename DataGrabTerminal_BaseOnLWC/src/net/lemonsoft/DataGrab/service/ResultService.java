package net.lemonsoft.DataGrab.service;

import net.lemonsoft.DataGrab.dao.ResultDao;
import net.lemonsoft.DataGrab.entity.ResultEntity;

/**
 * Created by lemonsoft on 16-11-27.
 */
public class ResultService extends Service<ResultEntity, ResultDao> {

    /**
     * 通过任务的指纹来获取采集的结果对象
     *
     * @param taskFingerprint 要查询的任务的指纹
     * @return 任务采集结果对象
     * @throws Exception
     */
    public ResultEntity getByFingerprint(String taskFingerprint) throws Exception {
        return this.getDao().getByFingerprint(taskFingerprint);
    }

}
