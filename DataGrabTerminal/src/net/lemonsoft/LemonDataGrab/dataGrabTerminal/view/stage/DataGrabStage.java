package net.lemonsoft.LemonDataGrab.dataGrabTerminal.view.stage;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.model.TaskModel;

/**
 * 自定义Stage - 数据采集
 * Created by LiuRi on 5/15/16.
 */
public class DataGrabStage extends Stage {

    private Browser browser;
    private BrowserView browserView;
    private Scene rootScene;

    private TaskModel task;

    public DataGrabStage(TaskModel taskModel) {
        super();
        this.task = taskModel;
        this.setTitle(String.format("数据采集器 - %s", task.getName()));
        this.setX(100);
        this.setY(100);
        this.setWidth(800);
        this.setHeight(600);
        this.browser = new Browser();
        this.browserView = new BrowserView(this.browser);
        this.rootScene = new Scene(this.browserView);
        this.setScene(this.rootScene);
        this.loadUrl("http://www.taobao.com");
    }

    /**
     * 加载指定的URL
     *
     * @param url 要加载的URL路径
     */
    public void loadUrl(String url) {
        this.browser.loadURL(url);
    }

    /**
     * 设置input控件的值
     *
     * @param xpath 要设置的控件的XPath路径
     * @param value 要设置的值
     */
    public void setInputValueByXPath(String xpath, String value) {
        this.getElementByXPath(xpath).setAttribute("value", value);
    }

    public void scrollToY(int y){

    }

    /**
     * 获取指定XPath路径的元素
     *
     * @param xpath 要获取涌入的XPath路径
     * @return 指定XPath路径的元素
     */
    public DOMElement getElementByXPath(String xpath) {
        return this.browser.getDocument().findElement(By.xpath(xpath));
    }

}
