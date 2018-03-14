package net.lemonsoft.LemonDataGrab.MainControlMachine.Listener;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Service.LSSession;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by lemonsoft on 2016/9/19.
 */
public class LLSession implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        new LSSession().resetAllSessionState();// 重置所有的用戶状态
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        new LSSession().resetAllSessionState();// 重置所有的用戶状态
    }
}
