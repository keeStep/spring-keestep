package org.kee.spring.tx.transaction.annotation;

import org.kee.spring.core.annotation.AnnotatedElementUtils;
import org.kee.spring.core.annotation.AnnotationAttributes;
import org.kee.spring.tx.transaction.interceptor.RollbackRuleAttribute;
import org.kee.spring.tx.transaction.interceptor.RuleBasedTransactionAttribute;
import org.kee.spring.tx.transaction.interceptor.TransactionAttribute;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/24 0:05
 */
public class SpringTransactionAnnotationParser implements TransactionAnnotationParser {
    /**
     * 解析 事务注解
     *
     * @param element
     */
    @Override
    public TransactionAttribute parseTransactionAnnotation(AnnotatedElement element) {
        AnnotationAttributes attributes = AnnotatedElementUtils.findMergedAnnotationAttributes(
                element, Transactional.class, false, false);
        if (null != attributes) {
            return parseTransactionAnnotation(attributes);
        } else {
            return null;
        }

    }

    protected TransactionAttribute parseTransactionAnnotation(AnnotationAttributes attributes) {
        RuleBasedTransactionAttribute rbta = new RuleBasedTransactionAttribute();

        List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
        for (Class<?> rbRule : attributes.getClassArray("rollbackFor")) {
            rollbackRules.add(new RollbackRuleAttribute(rbRule));
        }

        rbta.setRollbackRules(rollbackRules);
        return rbta;
    }
}
