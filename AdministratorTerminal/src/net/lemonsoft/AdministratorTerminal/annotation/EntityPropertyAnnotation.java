package net.lemonsoft.AdministratorTerminal.annotation;

import java.lang.annotation.*;

/**
 * Created by LiuRi on 16/4/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface EntityPropertyAnnotation {

    String columnName() default "";

    String description() default "";

    boolean primaryKey() default false;

    boolean autoIncrement() default false;

}
