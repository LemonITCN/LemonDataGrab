package net.lemonsoft.LemonDataGrab.dataGrabTerminal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by LiuRi on 16/4/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityAnnotation {

    /**
     * 对应的表名
     */
    String tableName() default "";

    /**
     * 数据库表的描述
     */
    String description() default "";

}
