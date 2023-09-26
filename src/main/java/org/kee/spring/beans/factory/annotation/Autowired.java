package org.kee.spring.beans.factory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p> Marks a constructor, field, setter method or config method as to be
 *  autowired by Spring's dependency injection facilities.
 *
 * @author Eric
 * @date 2023/9/26 23:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
public @interface Autowired {
}
