package net.lemonsoft.LemonDataGrab.dataGrabTerminal.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解 - 任务API
 * Created by LiuRi on 16/4/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface TaskApi {

    /**
     * 对应的JavaScript方法的名称
     */
    String jsMethodName() default "";

    /**
     * 对当前任务API的描述
     */
    String description() default "";

    /**
     * 对传入的参数的描述
     */
    String[] parameters() default {};
}
