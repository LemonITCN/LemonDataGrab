package net.lemonsoft.LemonDataGrab.dataGrabTerminal.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 工具类 - 资源工具类
 * Created by LiuRi on 5/14/16.
 */
public class ResourceTool {

    private String RESOURCE_PATH = "net/lemonsoft/LemonDataGrab/DataGrabTerminal/resource/";
    private static ResourceTool resourceTool = null;

    public static ResourceTool sharedInstance() {
        if (resourceTool == null) {
            resourceTool = new ResourceTool();
        }
        return resourceTool;
    }

    /**
     * 通过完整的文件名获取输入流
     *
     * @param name 完整的文件名
     * @return 指定文件的输入流
     */
    public InputStream getInputStreamByName(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }

    /**
     * 通过完整的文件名获取文件内容
     *
     * @param name 完整的文件名
     * @return 指定文件的输入流
     * @throws IOException
     */
    public String getContentStringByName(String name) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream stream = this.getInputStreamByName(name);
        int i = -1;
        while ((i = stream.read()) != -1) {
            byteArrayOutputStream.write(i);
        }
        return byteArrayOutputStream.toString();
    }

    /**
     * 通过资源包内的资源名称获取输入流
     *
     * @param resourceName 资源包内的资源文件名
     * @return 指定文件的输入流
     */
    public InputStream getResourceInputStreamByName(String resourceName) {
        return this.getInputStreamByName(String.format("%s%s", this.RESOURCE_PATH, resourceName));
    }

    /**
     * 通过资源包内的资源名称获取文件内容字符串
     *
     * @param resourceName 资源包内的资源文件名
     * @return 指定文件的文件内容
     * @throws IOException
     */
    public String getResourceContentStringByName(String resourceName) throws IOException {
        return this.getContentStringByName(String.format("%s%s", this.RESOURCE_PATH, resourceName));
    }

    /**
     * 获取运行时路径
     *
     * @return 运行时路径
     */
    public String getResourcePath() {
        return ResourceTool.class.getClassLoader().getResource(".").getPath() + RESOURCE_PATH;
//        return Thread.currentThread().getContextClassLoader().getResource(".").getPath() + RESOURCE_PATH;
    }

}
