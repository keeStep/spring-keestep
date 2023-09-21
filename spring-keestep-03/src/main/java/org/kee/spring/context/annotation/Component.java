package org.kee.spring.context.annotation;

import java.lang.annotation.*;

/**
 * <p>标记Bean
 *
 * @author Eric
 * @date 2023/9/20 23:13
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";
}
