package net.lemonsoft.LemonDataGrab.MainControlMachine.Core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

/**
 * 核心 - 配置
 * Created by LiuRi on 16/5/8.
 */
public class LCConfig {

    private Element apiConfigElement;

    private Document document;

    private static LCConfig clientInstance = null;

    public static LCConfig sharedInstance() {
        if (clientInstance == null) {
            clientInstance = new LCConfig();
        }
        return clientInstance;
    }

    public LCConfig() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(this.getClass().getClassLoader().getResource("lemonKit-config.xml").getPath());
            apiConfigElement = (Element) document.getElementsByTagName("LK-Api").item(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Document getDocument() {
        return document;
    }

    public Element getApiConfigElement(){
        return apiConfigElement;
    }

    public ArrayList<String> getApiClassList() {
        ArrayList<String> classList = new ArrayList<String>();
        NodeList nodeList = apiConfigElement.getElementsByTagName("apiClass");
        for (int index = 0; index < nodeList.getLength(); index++) {
            classList.add(nodeList.item(index).getTextContent());
        }
        return classList;
    }


}
