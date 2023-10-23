package org.kee.spring.tx.transaction.annotation;

import org.kee.spring.tx.transaction.interceptor.TransactionAttribute;

import java.lang.reflect.AnnotatedElement;

/**
 * <p>事务注解解析器
 *
 * @author Eric
 * @date 2023/10/24 0:03
 */
public interface TransactionAnnotationParser {

    /**
     * 解析 事务注解
     */
    TransactionAttribute parseTransactionAnnotation(AnnotatedElement element);
}
