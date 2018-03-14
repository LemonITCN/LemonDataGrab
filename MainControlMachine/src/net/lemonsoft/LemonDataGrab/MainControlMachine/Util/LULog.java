package net.lemonsoft.LemonDataGrab.MainControlMachine.Util;

import org.apache.log4j.Logger;

/**
 * 工具类 - 日志相关
 * Created by LiuRi on 16/4/24.
 */
public class LULog {

    private static Logger logger = Logger.getLogger(LULog.class);

    public static void fatal(Object msg){
        logger.fatal(msg);
    }

    public static void error(Object msg){
        logger.error(msg);
    }

    public static void warn(Object msg){
        logger.warn(msg);
    }

    public static void info(Object msg){
        logger.info(msg);
    }

    public static void debug(Object msg){
        logger.info(msg);
    }

}
