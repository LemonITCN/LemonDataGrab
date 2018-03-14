package net.lemonsoft.LemonDataGrab.dataGrabTerminal.core.taskCommandParser;

import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.JSFunction;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import javafx.application.Platform;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.core.taskNativeApi.Grabber;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.entity.ResultEntity;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.entity.TaskEntity;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.view.stage.WebStage;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务命令解析器
 * Created by LiuRi on 6/27/16.
 */
public class TaskCommandParserStage extends WebStage {

    private Map<String, Grabber> grabberPool = new HashMap<String, Grabber>();// 数据采集器池
    private Map<Integer, JSFunction> jsCallbackPool = new HashMap<Integer, JSFunction>();// js回调方法池
    private ArrayList<String> logList;// 任务列表
    private ResultEntity currentResult;// 任务结果
    private List<TaskEntity> taskQueue = new ArrayList<TaskEntity>();// 任务队列
    private final TaskCommandParserStage self = this;

    private String currentTaskCommandCode = null;

    public TaskCommandParserStage() {
        super();
        this.getBrowser().addScriptContextListener(new ScriptContextAdapter() {
            @Override
            public void onScriptContextCreated(ScriptContextEvent scriptContextEvent) {
                System.out.println("JS CONTEXT _EVENT_CREATED success");
                super.onScriptContextCreated(scriptContextEvent);
                JSValue window = getBrowser().executeJavaScriptAndReturnValue("window");
                window.asObject().setProperty("grabber", new GrabberHandler());
                window.asObject().setProperty("operate", new OperateHandler());
                window.asObject().setProperty("dataGrab" , new DataGrabHandler());
                window.asObject().setProperty("task", new TaskHandler());
            }
        });
    }

    /**
     * 添加一个任务到任务队列中
     *
     * @param taskEntity 要添加的任务的实体对象
     */
    public synchronized void addTask(TaskEntity taskEntity) {
        if (taskEntity.getTheTop() == 1)// 任务要求提权指定,排到队列的队头
            taskQueue.add(0, taskEntity);
        else// 没有要求提权,放到队尾,顺次排列
            taskQueue.add(taskQueue.size(), taskEntity);
        this.addTaskToUI(taskEntity);
        if (taskQueue.size() > 0)
            this.executeNextTask();
    }

    public synchronized void addTaskToUI(TaskEntity taskEntity){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getBrowser().executeJavaScript("addTaskToUI(" + new Gson().toJson(taskEntity) + ")");
                getBrowser().executeJavaScript("Log.info('接收到服务器新推送的任务:" + taskEntity.getName() + "')");
            }
        });
    }

    /**
     * 从任务队列中取出一个任务
     *
     * @return 队列头部的队列实体对象
     */
    public synchronized TaskEntity takeOutTask() {
        TaskEntity taskEntity = null;
        if (taskQueue.size() > 0) {
            taskEntity = taskQueue.get(0);
            taskQueue.remove(0);
        }
        return taskEntity;
    }

    /**
     * 执行下一个任务
     */
    public synchronized void executeNextTask() {
        TaskEntity taskEntity = this.takeOutTask();
        if (taskEntity != null) {
            jsCallbackPool.clear();
            grabberPool.clear();
            currentResult = new ResultEntity();
            this.getBrowser().executeJavaScript(compressJS(taskEntity.getExecuteScript()));
        }
    }

    /**
     * 压缩JS字符串
     *
     * @param jsStr 要压缩的JS代码字符串
     * @return 压缩后的JS字符串
     */
    public String compressJS(String jsStr) {
        try {
            Reader reader = new StringReader(jsStr);

            JavaScriptCompressor compressor = new JavaScriptCompressor(reader, new ErrorReporter() {
                @Override
                public void warning(String s, String s1, int i, String s2, int i1) {
                }

                @Override
                public void error(String s, String s1, int i, String s2, int i1) {
                }

                @Override
                public EvaluatorException runtimeError(String s, String s1, int i, String s2, int i1) {
                    return null;
                }
            });
            StringWriter writer = new StringWriter();
            compressor.compress(writer, -1, true, false, false, false);
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 执行指定代码对应的js回调函数
     *
     * @param code    要执行的JS回调函数的对应code
     * @param objects 执行时传入的参数对象数组
     */
    public void executeJsCallbackByCode(Integer code, Object... objects) {
        JSValue document = this.getBrowser().executeJavaScriptAndReturnValue("document");
        jsCallbackPool.get(code).invoke(document.asObject(), objects);
    }

    /**
     * 任务命令相关的处理
     */
    public class TaskHandler {

        /**
         * 结束当前任务的解析
         */
        public void end() {
            // 当前的任务结束,判断是否还有其他任务
            executeNextTask();// 尝试触发执行下一个任务
        }
    }

    /**
     * 数据采集器的处理
     */
    public class GrabberHandler {

        /**
         * 创建一个数据采集器
         *
         * @param grabberIdentity 要创建的采集器的标识字符串
         */
        public void create(String grabberIdentity) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("创建了一个grabber!");
                    grabberPool.put(grabberIdentity, new Grabber(grabberIdentity, self));
                }
            });
        }

        /**
         * 显示指定的数据采集终端的面板界面
         *
         * @param grabberIdentity 要显示的采集器的标识字符串
         */
        public void show(String grabberIdentity) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("触发了一次显示grabber");
                        grabberPool.get(grabberIdentity).show();
                        System.out.println("触发了一次显示grabber  OVER");
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * 隐藏指定的数据采集终端的面板界面
         *
         * @param grabberIdentity 要隐藏显示的采集器的标识字符串
         */
        public void hide(String grabberIdentity) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    grabberPool.get(grabberIdentity).close();
                }
            });
        }

        /**
         * 删除指定的数据采集器,删除后该数据采集器将无法使用
         *
         * @param grabberIdentity 要删除的数据采集器的标识字符串
         */
        public void delete(String grabberIdentity) {
            grabberPool.get(grabberIdentity).close();
        }

        /**
         * 在指定的采集器中执行JS代码
         *
         * @param grabberIdentity 要执行js代码的采集器标识
         * @param jsCode          要执行的js代码
         */
        public String executeJS(String grabberIdentity, String jsCode) {
            return grabberPool.get(grabberIdentity).getBrowser().executeJavaScriptAndReturnValue(jsCode).getStringValue();
        }

        public void load(String grabberIdentity){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    grabberPool.get(grabberIdentity).loadURL("http://www.taobao.com" , 10 , 1 , 2);
                }
            });
        }

        /**
         * 加载指定的URL
         *
         * @param grabberIdentity 要加载URL的采集器
         * @param url             要加载的URL地址
         * @param success         加载成功之后的执行的函数
         * @param error           加载发生错误时候的回调函数
         */
        public void loadURL(String grabberIdentity, String url, int timeoutInterval, JSFunction success, JSFunction error) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("LOAD URL :" + url);
                        jsCallbackPool.put(success.hashCode(), success);
                        jsCallbackPool.put(error.hashCode(), error);
                        grabberPool.get(grabberIdentity).loadURL(url, timeoutInterval, success.hashCode(), error.hashCode());
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    /**
     * 网页操作的处理
     */
    public class OperateHandler {
        public void clickDom(String grabberIdentity, String domSelector){
            grabberPool.get(grabberIdentity).getBrowser().getDocument().findElement(By.cssSelector(domSelector)).click();
        }
    }

    /**
     * 数据采集的处理
     */
    public class DataGrabHandler {

    }

}
