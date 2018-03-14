package net.lemonsoft.LemonDataGrab.MainControlMachine.Service;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Dao.LDTask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Entity.LETask;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUDatabase;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

/**
 * 业务层 - 任务相关
 * Created by LiuRi on 5/29/16.
 */
public class LSTask extends LS<LETask, LDTask> {

    /**
     * 通过任务指纹获取任务对象
     *
     * @param fingerprint 任务的指纹
     * @return 获取到的任务对象
     * @throws Exception
     */
    public LETask getTaskByFingerprint(String fingerprint) throws Exception {
        LETask task = this.getDao().getTaskByFingerprint(fingerprint);
        task.setExecuteScript(getLocalScript(fingerprint));
        return task;
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
    public ArrayList<Map<String, Object>> getTaskListByDate(String startDateStamp, String endDateStamp, String startIndex, String count) throws Exception {
        ArrayList<Map<String, Object>> taskList = this.getDao().getTaskListByDate(Long.valueOf(startDateStamp), Long.valueOf(endDateStamp), Long.valueOf(startIndex), Long.valueOf(count));
        return taskList;
    }

    /**
     * 获取指定日期范围内的所有任务的数量
     *
     * @param startDateStamp 开始日期的时间戳
     * @param endDateStamp   结束日期的时间戳
     * @return 查询到的任务的数量
     */
    public Integer countTaskByDate(String startDateStamp, String endDateStamp) {
        return this.getDao().countTaskByDdate(Long.valueOf(startDateStamp), Long.valueOf(endDateStamp));
    }

    @Override
    public boolean add(LETask entity) {
        String script = entity.getExecuteScript();
        entity.setExecuteScript("");
        outScriptToLocal(entity.getFingerprint(), script);
        return super.add(entity);
    }

    @Override
    public boolean update(LETask entity) {
        String script = entity.getExecuteScript();
        entity.setExecuteScript("");
        outScriptToLocal(entity.getFingerprint(), script);
        return super.update(entity);
    }

    @Override
    public LETask getByPrimaryKey(Object primaryKey) {
        LETask task = super.getByPrimaryKey(primaryKey);
        task.setExecuteScript(getLocalScript(task.getFingerprint()));
        return task;
    }

    @Override
    public ArrayList<LETask> getByPrimaryKeys(Object... primaryKeys) {
        ArrayList<LETask> tasks = super.getByPrimaryKeys(primaryKeys);
        for (LETask task : tasks) {
            task.setExecuteScript(getLocalScript(task.getFingerprint()));
        }
        return tasks;
    }

    @Override
    public ArrayList<LETask> getAllDataByScope(int start, int count) {
        ArrayList<LETask> tasks = super.getAllDataByScope(start, count);
        for (LETask task : tasks) {
            task.setExecuteScript(getLocalScript(task.getFingerprint()));
        }
        return tasks;
    }

    /**
     * 把执行脚本存储到本地
     */
    public static void outScriptToLocal(String fingerprint, String contentScript) {
        String root = Thread.currentThread().getContextClassLoader().getResource("") + File.pathSeparator;
        root = root.split(":")[1] + "scripts" + File.separator;
        File script = new File(root + fingerprint + ".js");
        try {
            script.mkdirs();
            if (script.canRead())
                script.delete();
            OutputStream os = new FileOutputStream(script);
            os.write(contentScript.getBytes());
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取本地指定指纹的执行脚本
     */
    public static String getLocalScript(String fingerprint) {
        String root = Thread.currentThread().getContextClassLoader().getResource("") + File.pathSeparator;
        root = root.split(":")[1] + "scripts" + File.separator;

        FileInputStream file = null;
        BufferedReader reader = null;
        InputStreamReader inputFileReader = null;
        String content = "";
        String tempString = null;
        try {
            file = new FileInputStream(root + fingerprint + ".js");
            inputFileReader = new InputStreamReader(file, "utf-8");
            reader = new BufferedReader(inputFileReader);
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                content += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return content;
    }

}
