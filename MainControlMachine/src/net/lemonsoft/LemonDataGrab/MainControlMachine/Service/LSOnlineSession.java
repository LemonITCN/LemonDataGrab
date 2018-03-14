package net.lemonsoft.LemonDataGrab.MainControlMachine.Service;

import com.google.gson.Gson;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Api.LA;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LESession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LETask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Handler.LHMina;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMDataGrabMapper;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Model.LMOnlineSession;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUDatabase;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LULog;
import org.apache.mina.core.session.IoSession;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 业务层 - 在线会话
 * Created by LiuRi on 6/19/16.
 */
public class LSOnlineSession extends LA {

    private LSSession lsSession = new LSSession();
    private LSUser lsUser = new LSUser();
    private LSUserGroup lsUserGroup = new LSUserGroup();

    // 设置指定的任务的会话id
    private static final String SQL_SET_TASK_SESSION_ID =
            "UPDATE ldg_task " +
                    "SET task_session = ? " +
                    "WHERE task_id = ?";

    /**
     * 数据采集终端会话Map
     */
    private Map<String, LMOnlineSession> dataGrabTerminalSessionMap;
    /**
     * 管理终端会话Map
     */
    private Map<String, LMOnlineSession> managementTerminalSessionMap;

    private Map<Long, String> dataGrabTerminalIDMap;

    private Map<Long, String> managementTerminalIDMap;

    private List<LMDataGrabMapper> dataGrabTerminalList;

    private static LSOnlineSession lsOnlineSession = null;

    public static final LSOnlineSession sharedInstance() {
        if (lsOnlineSession == null)
            lsOnlineSession = new LSOnlineSession();
        return lsOnlineSession;
    }

    private LSOnlineSession() {
        dataGrabTerminalSessionMap = new HashMap<String, LMOnlineSession>();
        managementTerminalSessionMap = new HashMap<String, LMOnlineSession>();
        dataGrabTerminalIDMap = new HashMap<Long, String>();
        managementTerminalIDMap = new HashMap<Long, String>();
        dataGrabTerminalList = new CopyOnWriteArrayList<>();
    }

    /**
     * 添加数据采集终端
     *
     * @param lmOnlineSession 数据采集终端对象
     */
    public void addDataGrabTerminal(LMOnlineSession lmOnlineSession) {
        LULog.info("一个数据采集终端连入! IP : " + lmOnlineSession.getSession().getRemoteAddress());
        dataGrabTerminalSessionMap.put(lmOnlineSession.getSessionFingerprint(), lmOnlineSession);
        dataGrabTerminalIDMap.put(lmOnlineSession.getSession().getId(), lmOnlineSession.getSessionFingerprint());
        dataGrabTerminalList.add(0, new LMDataGrabMapper(lmOnlineSession));
    }

    /**
     * 添加管理终端
     *
     * @param lmOnlineSession 管理终端对象
     */
    public void addManagementTerminal(LMOnlineSession lmOnlineSession) {
        LULog.info("一个管理终端连入! IP : " + lmOnlineSession.getSession().getRemoteAddress());
        if (this.checkIsAdministratorBySessionFingerprintNoOnline(lmOnlineSession.getSessionFingerprint())) {// 是管理员终端
            managementTerminalSessionMap.put(lmOnlineSession.getSessionFingerprint(), lmOnlineSession);
            Long id = lmOnlineSession.getSession().getId();
            managementTerminalIDMap.put(lmOnlineSession.getSession().getId(), lmOnlineSession.getSessionFingerprint());
            System.out.println(managementTerminalIDMap.size());
        }
    }

    /**
     * 删除指定的数据采集终端
     *
     * @param sessionFingerprint 数据采集终端的会话指纹
     */
    public void removeDataGrabTerminalBySessionFingerprint(String sessionFingerprint) {
        LMOnlineSession lmOnlineSession;
        if (dataGrabTerminalSessionMap.containsKey(sessionFingerprint)) {
            lmOnlineSession = dataGrabTerminalSessionMap.get(sessionFingerprint);
            dataGrabTerminalIDMap.remove(lmOnlineSession.getSession().getId());
            lmOnlineSession.getSession().closeNow();
            dataGrabTerminalSessionMap.remove(sessionFingerprint);
            for (LMDataGrabMapper dataGrabMapper : dataGrabTerminalList) {// 循环遍历,删除终端
                if (dataGrabMapper.getOnlineSession().getSessionFingerprint().equals(sessionFingerprint)) {
                    dataGrabMapper.removeAllTask();
                    dataGrabTerminalList.remove(dataGrabMapper);// 移除终端会话
                }
            }
            LULog.info(String.format("IP:%s 的数据采集终端会话已经断开!", lmOnlineSession.getSession().getRemoteAddress()));
        }
    }

    /**
     * 删除指定的管理终端
     *
     * @param sessionFingerprint 管理终端的会话指纹
     */
    public void removeManagementTerminalBySessionFingerprint(String sessionFingerprint) {
        LMOnlineSession lmOnlineSession;
        if (managementTerminalSessionMap.containsKey(sessionFingerprint)) {
            lmOnlineSession = managementTerminalSessionMap.get(sessionFingerprint);
            managementTerminalIDMap.remove(lmOnlineSession.getSession().getId());
            managementTerminalSessionMap.remove(sessionFingerprint);
            lmOnlineSession.getSession().closeNow();
            LULog.info(String.format("IP:%s 的管理终端会话已经断开!", lmOnlineSession.getSession().getRemoteAddress()));
        }
    }

    /**
     * 移除指定的会话指纹的会话对象
     *
     * @param sessionFingerprint 要移除的会话的会话指纹
     */
    public void removeSessionBySessionFingerprint(String sessionFingerprint) {
        removeDataGrabTerminalBySessionFingerprint(sessionFingerprint);
        removeManagementTerminalBySessionFingerprint(sessionFingerprint);
    }

    /**
     * 移除所有的数据采集终端
     */
    public void removeAllDataGrabTerminals() {
        for (LMOnlineSession lmOnlineSession : dataGrabTerminalSessionMap.values()) {
            lmOnlineSession.getSession().closeNow();
        }
        dataGrabTerminalSessionMap.clear();
        dataGrabTerminalIDMap.clear();
    }

    /**
     * 移除所有的管理终端
     */
    public void removeAllManagementTerminals() {
        for (LMOnlineSession lmOnlineSession : managementTerminalSessionMap.values()) {
            lmOnlineSession.getSession().closeNow();
        }
        managementTerminalSessionMap.clear();
        managementTerminalIDMap.clear();
    }

    /**
     * 获取所有的数据采集终端链表
     *
     * @return 所有数据采集终端的链表
     */
    public ArrayList<LMOnlineSession> getAllDataGrabTerminals() {
        return (ArrayList<LMOnlineSession>) dataGrabTerminalSessionMap.values();
    }

    /**
     * 获取所有的管理终端链表
     *
     * @return 所有管理终端的链表
     */
    public ArrayList<LMOnlineSession> getAllManagementTerminals() {
        return (ArrayList<LMOnlineSession>) managementTerminalSessionMap.values();
    }

    /**
     * 获取指定会话指纹对应的数据采集终端的会话对象
     *
     * @param sessionFingerprint 要获取的数据采集终端的会话指纹
     * @return 获取到的数据采集终端会话对象
     */
    public LMOnlineSession getDataGrabTerminalBySessionFingerprint(String sessionFingerprint) {
        if (dataGrabTerminalSessionMap.containsKey(sessionFingerprint))
            return dataGrabTerminalSessionMap.get(sessionFingerprint);
        return null;
    }

    /**
     * 获取指定会话指纹对应的管理终端的会话对象
     *
     * @param sessionFingerprint 要获取的管理终端的会话指纹
     * @return 获取到的管理终端的会话对象
     */
    public LMOnlineSession getManagementTerminalBySessionFingerprint(String sessionFingerprint) {
        if (managementTerminalSessionMap.containsKey(sessionFingerprint))
            return managementTerminalSessionMap.get(sessionFingerprint);
        return null;
    }

    /**
     * 获取指定会话指纹对应的终端会话
     *
     * @param sessionFingerprint 会话指纹
     * @return 对应的终端会话
     */
    public LMOnlineSession getTerminalBySessionFingerprint(String sessionFingerprint) {
        LMOnlineSession lmOnlineSession = getDataGrabTerminalBySessionFingerprint(sessionFingerprint);
        if (lmOnlineSession == null)
            lmOnlineSession = getManagementTerminalBySessionFingerprint(sessionFingerprint);
        return lmOnlineSession;
    }

    /**
     * 获取指定会话对应的会话指纹
     *
     * @param session 会话对象
     * @return 获取到的会话指纹
     */
    public String getSessionFingerprintBySession(IoSession session) {
        String sessionFingerprint = null;
        if (containTheDataGrabTerminalSession(session))
            sessionFingerprint = dataGrabTerminalIDMap.get(session.getId());
        if (containTheManagementTerminalSession(session))
            sessionFingerprint = managementTerminalIDMap.get(session.getId());
        return sessionFingerprint;
    }

    /**
     * 通过会话指纹判断是否存在指定的数据采集终端
     *
     * @param sessionFingerprint 要判断是否存在的数据采集终端对应的会话指纹
     * @return 是否存在这个数据采集终端的布尔值
     */
    public boolean containTheDataGrabTerminalBySessionFingerprint(String sessionFingerprint) {
        return dataGrabTerminalSessionMap.containsKey(sessionFingerprint);
    }

    /**
     * 通过会话指纹判断是否存在指定的管理终端
     *
     * @param sessionFingerprint 要判断是否存在的管理终端对应的会话指纹
     * @return 是否存在这个管理终端的布尔值
     */
    public boolean containTheManagementTerminalBySessionFingerprint(String sessionFingerprint) {
        return managementTerminalSessionMap.containsKey(sessionFingerprint);
    }

    /**
     * 判断是否存在这个会话指纹对应的终端
     *
     * @param sessionFingerprint 要判断的会话指纹
     * @return 是否存在的布尔值
     */
    public boolean containTheTerminalBySessionFingerprint(String sessionFingerprint) {
        return containTheDataGrabTerminalBySessionFingerprint(sessionFingerprint)
                || containTheManagementTerminalBySessionFingerprint(sessionFingerprint);
    }

    /**
     * 向指定的会话指纹的数据采集终端发送消息
     *
     * @param sessionFingerprint 要发送消息的数据采集终端的会话指纹
     * @param isSuccess          是否成功
     * @param info               携带的信息
     * @param data               携带的数据
     */
    public void sendMessageToDataGrabTerminalBySessionFingerprint(String sessionFingerprint, boolean isSuccess, String info, Map<String, String> data) {
        if (dataGrabTerminalSessionMap.containsKey(sessionFingerprint)) {
            LMOnlineSession lmOnlineSession = dataGrabTerminalSessionMap.get(sessionFingerprint);
            HashMap<String, Object> messageMap = new HashMap<String, Object>();
            messageMap.put("success", isSuccess);
            messageMap.put("info", info);
            messageMap.put("data", data);
            lmOnlineSession.getSession().write(messageMap);
        }
    }

    /**
     * 是否存在这个数据采集终端session
     *
     * @param session 要判断的数据采集终端session
     * @return 是否存在的布尔值
     */
    public boolean containTheDataGrabTerminalSession(IoSession session) {
        return dataGrabTerminalIDMap.containsKey(session.getId());
    }

    /**
     * 是否存在这个管理终端session
     *
     * @param session 要判断的管理终端session
     * @return 是否存在的布尔值
     */
    public boolean containTheManagementTerminalSession(IoSession session) {
        return managementTerminalIDMap.containsKey(session.getId());
    }

    /**
     * 是否存在这个session
     *
     * @param session 会话session
     * @return 是否存在的布尔值
     */
    public boolean containTheSession(IoSession session) {
        Long id = session.getId();
        return dataGrabTerminalIDMap.containsKey(session.getId()) || managementTerminalIDMap.containsKey(session.getId());
    }

    /**
     * 查询当前所有数据采集终端的数量
     *
     * @return 数据采集终端的数量
     */
    public Integer countAllDataGrabTerminal() {
        return this.dataGrabTerminalSessionMap.size();
    }

    /**
     * 查询当前所有的管理终端的数量
     *
     * @return 数据采集终端的数量
     */
    public Integer countAllManagementTerminal() {
        return this.managementTerminalSessionMap.size();
    }

    /**
     * 获取所有的终端数量,包括数据采集终端和管理终端
     *
     * @return 所有终端的数量
     */
    public Integer countAllTerminal() {
        return countAllDataGrabTerminal() + countAllManagementTerminal();
    }

    /**
     * 向指定的会话输出对象
     *
     * @param ioSession 会话对象
     * @param success   携带的的成功性
     * @param info      携带的信息字段
     * @param data      携带的数据字段,提供键值对的map
     */
    public void outMessageOnSession(IoSession ioSession, boolean success, String info, Map<String, Object> data) {
        Map<String, Object> responseData = new HashMap<String, Object>();
        responseData.put("success", success);
        responseData.put("info", info);
        responseData.put("data", data);
        String json = new Gson().toJson(responseData);
        ioSession.write(json);
        LULog.info("[向客户端输出了一条消息] " + json);
    }

    /**
     * 向指定的会话输出对象
     *
     * @param session    会话对象
     * @param success    携带的成功性布尔值
     * @param info       携带的信息字段
     * @param dataKeys   携带的数据字段的键数组
     * @param dataValues 携带的数据字段的值数组
     */
    public void outMessageOnSession(IoSession session, boolean success, String info, String[] dataKeys, Object[] dataValues) {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        for (int i = 0; i < dataKeys.length; i++) {
            dataMap.put(dataKeys[i], dataValues[i]);
        }
        this.outMessageOnSession(session, success, info, dataMap);
    }

    /**
     * 向指定的会话对象输出成功的信息
     *
     * @param session 输出的会话对象
     * @param info    携带的信息字段
     * @param data    携带的数据字段,提供键值对向map
     */
    public void outSuccessOnSession(IoSession session, String info, Map<String, Object> data) {
        this.outMessageOnSession(session, true, info, data);
    }

    /**
     * 向指定的会话对象输出成功的信息
     *
     * @param session    输出的会话对象
     * @param info       携带的信息字段
     * @param dataKeys   携带的数据字段的键数组
     * @param dataValues 携带的数据字段的值数组
     */
    public void outSuccessOnSession(IoSession session, String info, String[] dataKeys, Object[] dataValues) {
        this.outMessageOnSession(session, true, info, dataKeys, dataValues);
    }

    /**
     * 向指定的会话对象输出失败的信息
     *
     * @param session 输出的会话对象
     * @param info    携带的信息字段
     * @param data    携带的数据字段,提供键值对象map
     */
    public void outFailedOnSession(IoSession session, String info, Map<String, Object> data) {
        this.outMessageOnSession(session, false, info, data);
    }

    /**
     * 向指定的会话对象输出失败的信息
     *
     * @param session    输出的会话对象
     * @param info       携带的信息字段
     * @param dataKeys   携带的数据字段的键数组
     * @param dataValues 携带的数据字段的值数组
     */
    public void outFailedOnSession(IoSession session, String info, String[] dataKeys, Object[] dataValues) {
        this.outMessageOnSession(session, false, info, dataKeys, dataValues);
    }

    /**
     * 自动发送一个新任务给数据采集终端
     *
     * @param task 要发送的任务的实体对象
     * @return 是否成功发送的布尔值
     */
    public boolean sendTask(LETask task) throws Exception {
        if (this.dataGrabTerminalList.size() == 0)
            return false;
        this.resortDataGrabTerminalList();
        this.outSuccessOnSession(this.dataGrabTerminalList.get(0).getOnlineSession().getSession(),
                LHMina.SESSION_SEND_TASK, new String[]{"task"}, new Object[]{task});
        LMDataGrabMapper dataGrabMapper = this.dataGrabTerminalList.get(0);
        LESession leSession = this.getSessionBySessionFingerprint(dataGrabMapper.getOnlineSession().getSessionFingerprint());
        LUDatabase.update(SQL_SET_TASK_SESSION_ID, leSession.getSid(), task.getTid());
        this.dataGrabTerminalList.get(0).addTask(task);
        return true;
    }

    /**
     * 对数据采集终端进行排序,按照当前待处理任务的从小到大顺序
     */
    public void resortDataGrabTerminalList() {
        Collections.sort(this.dataGrabTerminalList, new Comparator<LMDataGrabMapper>() {
            @Override
            public int compare(LMDataGrabMapper o1, LMDataGrabMapper o2) {
                return o1.countToDoTasks() - o2.countToDoTasks();
            }
        });
    }

}
