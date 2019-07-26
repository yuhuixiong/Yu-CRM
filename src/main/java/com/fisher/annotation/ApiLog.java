package com.fisher.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLog {
    /**
     * 模块
     * @return
     */
    String title() default "";
    /**
     * 功能
     * @return
     */
    String action() default "";
}
