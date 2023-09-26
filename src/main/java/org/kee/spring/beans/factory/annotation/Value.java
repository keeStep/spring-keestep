package org.kee.spring.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * <p>字段或方法/构造函数参数级别的注释，指示受影响参数的默认值表达式。
 *
 * @author Eric
 * @date 2023/9/26 23:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Documented // 如果一个类型声明用Documented进行了注释，那么它的注释将成为被注释元素的公共API的一部分。
public @interface Value {

    String value();
}
