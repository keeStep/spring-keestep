package org.kee.spring.tx.transaction.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.kee.spring.tx.transaction.PlatformTransactionManager;

import java.io.Serializable;

/**
 * @author zhangdd on 2022/2/23
 */
public class TransactionInterceptor extends TransactionAspectSupport
        implements MethodInterceptor, Serializable {

    public TransactionInterceptor(PlatformTransactionManager ptm, TransactionAttributeSource transactionAttributeSource) {
        setTransactionManager(ptm);
        setTransactionAttributeSource(transactionAttributeSource);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invokeWithinTransaction(invocation.getMethod(),
                invocation.getThis().getClass(), invocation::proceed);
    }
}
