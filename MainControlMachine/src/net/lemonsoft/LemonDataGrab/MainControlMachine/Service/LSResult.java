package net.lemonsoft.LemonDataGrab.MainControlMachine.Service;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Dao.LDResult;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LEResult;

/**
 * Created by LiuRi on 5/29/16.
 */
public class LSResult extends LS<LEResult, LDResult> {

    /**
     * 获取指定指纹对应的采集结果数据
     *
     * @param fingerprint 任务的指纹
     * @return 查询到的采集结果，如果没有，则返回null
     */
    public LEResult getByFingerprint(String fingerprint) throws Exception {
        return this.getDao().getByFingerprint(fingerprint);
    }

}
