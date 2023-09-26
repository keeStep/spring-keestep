package org.kee.spring.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * <p>自动装配时，可以在字段或参数上使用此注释作为候选bean的限定符。它还可以用于注释其他自定义注释，然后将其用作限定符。
 *
 * @author Eric
 * @date 2023/9/26 23:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Documented  // 如果一个类型声明用Documented进行了注释，那么它的注释将成为被注释元素的公共API的一部分。
@Inherited //  表示自动继承注释类型。
        // 如果在注释类型声明中存在Inherited元注释，并且用户在类声明中查询注释类型，而类声明中没有针对该类型的注释，则将自动查询该类的超类以获取注释类型。
        // 这个过程将重复进行，直到找到该类型的注释，或者到达类层次结构的顶端(Object)。
        // 如果没有超类具有此类型的注释，则查询将表明所讨论的类没有此类注释。
public @interface Qualifier {

    String value() default "";
}
