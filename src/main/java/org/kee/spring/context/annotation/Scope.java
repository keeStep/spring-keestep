package org.kee.spring.context.annotation;

import java.lang.annotation.*;

/**
 * <p>标记Bean作用域
 *
 * @author Eric
 * @date 2023/9/20 23:13
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    String value() default "singleton";
}
