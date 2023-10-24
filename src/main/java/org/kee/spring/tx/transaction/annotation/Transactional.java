package org.kee.spring.tx.transaction.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/23 23:59
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transactional {

    Class<? extends Throwable>[] rollbackFor() default {};
}
