package org.kee.spring.tx.transaction;

/**
 * <p>事务管理核心接口
 * 【获取事务，提交事务，回滚事务】
 *
 * @author Eric
 * @date 2023/10/24 0:01
 */
public interface PlatformTransactionManager {


    TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException;

    void commit(TransactionStatus status) throws TransactionException;

    void rollback(TransactionStatus status) throws TransactionException;
}
