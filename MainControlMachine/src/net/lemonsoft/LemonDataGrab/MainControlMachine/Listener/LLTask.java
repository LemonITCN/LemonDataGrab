package net.lemonsoft.LemonDataGrab.MainControlMachine.Listener;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Core.LCTaskManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletContextListener - 任务处理机制加载器
 * Created by 1em0nsOft on 2016/8/7.
 */
public class LLTask implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            LCTaskManager.sharedInstance().resetDBTaskState();// 修复数据库中已分发和待分发的状态的任务
            LCTaskManager.sharedInstance().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            LCTaskManager.sharedInstance().end();
            LCTaskManager.sharedInstance().resetDBTaskState();// 修复数据库中已分发和待分发的状态的任务
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
