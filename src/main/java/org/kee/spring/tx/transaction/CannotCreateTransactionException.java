package org.kee.spring.tx.transaction;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/24 0:02
 */
public class CannotCreateTransactionException extends TransactionException {
    public CannotCreateTransactionException(String message) {
        super(message);
    }

    public CannotCreateTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
