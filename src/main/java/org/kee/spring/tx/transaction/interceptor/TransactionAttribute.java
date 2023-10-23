package org.kee.spring.tx.transaction.interceptor;

import org.kee.spring.tx.transaction.TransactionDefinition;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/24 0:04
 */
public interface TransactionAttribute extends TransactionDefinition {
    boolean rollbackOn(Throwable ex);
}
