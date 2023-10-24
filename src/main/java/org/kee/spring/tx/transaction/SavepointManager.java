package org.kee.spring.tx.transaction;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/23 23:57
 */
public interface SavepointManager {

    Object createSavepoint() throws TransactionException;

    void rollbackToSavepoint(Object savepoint) throws TransactionException;

    void releaseSavepoint(Object savepoint) throws TransactionException;
}
