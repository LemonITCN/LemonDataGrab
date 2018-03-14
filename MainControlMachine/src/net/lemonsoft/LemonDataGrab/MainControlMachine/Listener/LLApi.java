package net.lemonsoft.LemonDataGrab.MainControlMachine.Listener;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation.LANApi;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Core.LCApiPool;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Core.LCConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by LiuRi on 16/5/8.
 */
public class LLApi implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ArrayList<String> classList = LCConfig.sharedInstance().getApiClassList();
        for (String classStr : classList) {
            System.out.println("++++++" + classStr);
            try {
                Class clazz = Class.forName(classStr);
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    LANApi apiAnnotation = method.getAnnotation(LANApi.class);
                    LCApiPool.addApi(apiAnnotation, clazz, method);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
