package net.lemonsoft.LemonDataGrab.MainControlMachine.Annotation;

import java.lang.annotation.*;

/**
 * Created by LiuRi on 16/4/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface LANApi {

    /**
     * 被触发的URL
     */
    String URL() default "";

    /**
     * 需要传入的参数,数组
     */
    String[] parameters() default {};

    /**
     * 对传入参数的意义的描述,数组
     */
    String[] parameterIntros() default {};

    /**
     * 可能产生的错误码,数组
     */
    int[] errors() default {};

    /**
     * 对当前API的描述信息
     */
    String description() default "";

}
