package org.kee.spring.tx.transaction;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/24 0:00
 */
public class TransactionException extends RuntimeException{

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
