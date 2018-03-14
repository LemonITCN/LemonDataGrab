package net.lemonsoft.LemonDataGrab.MainControlMachine.Core;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LUDatabase;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class LCMinaLoader implements ServletContextListener {

    static Logger logger;

    public void contextInitialized(ServletContextEvent sce) {
        List<Map<String, Object>> result = LUDatabase.query("select * from ldg_usergroup");
        System.out.println("the result is " + result);
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }

}
