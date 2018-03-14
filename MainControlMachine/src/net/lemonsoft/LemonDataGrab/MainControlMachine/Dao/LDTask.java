package net.lemonsoft.LemonDataGrab.MainControlMachine.Dao;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LETask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUDatabase;

import java.util.ArrayList;
import java.util.Map;

/**
 * DAO - 任务
 * Created by LiuRi on 5/29/16.
 */
public class LDTask extends LD<LETask> {

    // 查询指定范围内任务信息的sql
    private static final String SQL_QUERY_TASK_INFO_LIST = "SELECT" +
            "  ldg_task.task_id                      AS taskId," +
            "  ldg_task.task_fingerprint             AS taskFingerprint," +
            "  ldg_task.task_name                    AS taskName," +
            "  ldg_task.task_state                   AS taskState," +
            "  ldg_task.task_publishTime             AS taskPublishTime," +
            "  ldg_task.task_session                 AS taskSessionId," +
            "  ifnull(ldg_session.sess_ip, 'server') AS clientIp " +
            "FROM ldg_task" +
            "  LEFT JOIN ldg_session" +
            "    ON ldg_task.task_session = ldg_session.sess_id " +
            "WHERE" +
            "  ldg_task.task_publishTime > ? AND" +
            "  ldg_task.task_publishTime < ? " +
            "ORDER BY ldg_task.task_id " +
            "LIMIT ?, ?";

    // 查询指定日期范围内的任务数量的sql
    private static final String SQL_QUERY_TASK_COUNT =
            "SELECT COUNT(ldg_task.task_id) AS taskCount " +
                    "FROM ldg_task " +
                    "WHERE" +
                    "  ldg_task.task_publishTime > ? AND" +
                    "  ldg_task.task_publishTime < ?";

    /**
     * 通过任务指纹来获取会话对象
     *
     * @param fingerPrint 任务的指纹
     * @return 查询到的任务对象
     * @throws Exception
     */
    public LETask getTaskByFingerprint(String fingerPrint) throws Exception {
        ArrayList<LETask> result = this.getBySQLCondition("WHERE task_fingerprint = ?", fingerPrint);
        if (result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    /**
     * 获取指定日期范围内的所有任务的列表
     *
     * @param startDateStamp 开始日期的时间戳
     * @param endDateStamp   结束日期的时间戳
     * @param startIndex     开始获取的索引
     * @param count          获取的数量
     * @return 查询到的任务的list
     * @throws Exception
     */
    public ArrayList<Map<String, Object>> getTaskListByDate(Long startDateStamp, Long endDateStamp, Long startIndex, Long count) throws Exception {
        return (ArrayList<Map<String, Object>>) LUDatabase.query(SQL_QUERY_TASK_INFO_LIST, startDateStamp, endDateStamp, startIndex, count);
    }

    /**
     * 获取指定日期范围内的所有任务的数量
     *
     * @param startDateStamp 开始日期的时间戳
     * @param endDateStamp   结束日期的时间戳
     * @return 查询到的任务的数量
     */
    public Integer countTaskByDdate(Long startDateStamp, Long endDateStamp) {
        return LUDatabase.count(SQL_QUERY_TASK_COUNT, startDateStamp, endDateStamp);
    }

}
