package net.lemonsoft.LemonDataGrab.MainControlMachine.Exception;

/**
 * 自定义异常信息 - 实体对象注解信息不完整
 * Created by LiuRi on 16/4/25.
 */
public class LEXEntityAnnotationIncomplete extends Exception{

    public LEXEntityAnnotationIncomplete(){
        super("实体对象的注解不完整,注解信息用于数据库的相关操作,必须按照格式编写.");
    }

}
