package org.kee.spring.tx.transaction;

import java.io.Flushable;
import java.io.IOException;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/23 23:57
 */
public interface TransactionStatus extends SavepointManager, Flushable {

    /**
     * 是否开启新的事务
     */
    boolean isNewTransaction();

    boolean hasSavepoint();

    void setRollbackOnly();

    boolean isRollbackOnly();

    @Override
    void flush() throws IOException;

    boolean isCompleted();
}
