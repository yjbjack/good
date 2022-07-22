package com.yjb.goods.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 注解：用于标记被拦截的类或者方法
 * @Target({ElementType.METHOD,ElementType.TYPE})表示该注解的使用范围
 * */

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmit {
    /*
    * 默认失效时间为5s
    * */
     long seconds() default  5;
}
