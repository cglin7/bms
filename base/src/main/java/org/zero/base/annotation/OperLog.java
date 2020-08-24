package org.zero.base.annotation;

import java.lang.annotation.*;

/**
  * 自定义操作日志注解
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/21 17:54
  **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {
    String value() default "";
    String type() default "";
}
