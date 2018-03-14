package net.lemonsoft.LemonDataGrab.dataGrabTerminal.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解 - 任务API分类
 * Created by LiuRi on 16/4/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface TaskApiType {

    /**
     * API分类的名称
     */
    String name() default "";

    /**
     * API分类的描述
     */
    String description() default "";
}
