package net.lemonsoft.LemonDataGrab.dataGrabTerminal.core.taskNativeApi;

import com.teamdev.jxbrowser.chromium.events.FailLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.core.taskCommandParser.TaskCommandParserStage;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.view.stage.WebStage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 数据采集器
 * Created by LiuRi on 6/27/16.
 */
public class Grabber extends WebStage {

    private String identity;
    private TaskCommandParserStage parser;

    public String getIdentity() {
        return identity;
    }

    /**
     * 采集器的构造方法,传入采集器的标识字符串
     *
     * @param identity 采集器的标识字符串
     */
    public Grabber(String identity, TaskCommandParserStage parser) {
        this.identity = identity;
        this.parser = parser;
    }

    /**
     * 加载指定的URL
     *
     * @param url                 要加载的URL地址
     * @param successCallbackCode 加载成功之后的执行的函数
     * @param errorCallbackCode   加载发生错误时候的回调函数
     */
    public void loadURL(String url , int timeoutInterval, Integer successCallbackCode, Integer errorCallbackCode) {
        final CountDownLatch latch = new CountDownLatch(1);
        LoadAdapter loadAdapter = new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent finishLoadingEvent) {
                super.onFinishLoadingFrame(finishLoadingEvent);
                if (finishLoadingEvent.isMainFrame()) {// 加载成功
                    latch.countDown();
                    parser.executeJsCallbackByCode(successCallbackCode);
                }
            }

            @Override
            public void onFailLoadingFrame(FailLoadingEvent failLoadingEvent) {// 加载失败
                super.onFailLoadingFrame(failLoadingEvent);
                parser.executeJsCallbackByCode(errorCallbackCode);
            }
        };
        this.getBrowser().addLoadListener(loadAdapter);
        this.getBrowser().loadURL(url);
        try {
            if (!latch.await(timeoutInterval , TimeUnit.SECONDS)){
                // 加载超时
                parser.executeJsCallbackByCode(errorCallbackCode);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
