package net.lemonsoft.LemonDataGrab.MainControlMachine.Enum;

/**
 * 任务状态枚举
 * Created by 1em0nsOft on 2016/7/31.
 */
public enum  LENTaskState {

    TASK_STATE_WAIT_FOR_DISTRIBUTE(0L),// 任务创建完毕,读入到待分发池中之前
    TASK_STATE_READY_TO_DISTRIBUTE(1L),// 任务已经被分发机制存入到分发池中
    TASK_STATE_ALREADY_DISTRIBUTED(2L),// 任务已经从分发池中被取出,并已经分发到了终端中,同时将该任务放到已分发池中
    TASK_STATE_GRAB_RESULT_SUCCESS(3L),// 任务已经从数据采集终端回传回了采集结果,并且从已分发池中取出
    /**
     * 任务没有在指定的时间范围之内传回采集结果,
     * 或者任务在采集中,采集的设备与主控机断开链接,且没有在指定的时间内再次分发出去,
     * 或者任务在指定时间内因为没有可用的数据采集终端链接进来而没有分发出去,
     * 此时应该把该任务从已分发池中移除
     */
    TASK_STATE_GRAB_RESULT_TIMEOUT(4L),
    TASK_STATE_GRAB_RESULT_FAILED(5L);// 任务已经成功的分发出去,并且数据采集终端在指定的时间内回传回了错误采集的结果,此时应该把该任务从已分发池中移除


    private Long stateCode;

    LENTaskState(Long stateCode) {
        this.stateCode = stateCode;
    }

    public Long getStateCode() {
        return stateCode;
    }

}
