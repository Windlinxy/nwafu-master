package pers.nwafumaster.annotation;

import java.lang.annotation.*;


/**
 * @author Windlinxy
 * @description
 * @create 2023-01-30 19:11
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {
    boolean required() default true;
}

