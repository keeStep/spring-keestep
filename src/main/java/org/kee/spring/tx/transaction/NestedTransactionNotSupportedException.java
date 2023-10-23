package org.kee.spring.tx.transaction;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/24 0:02
 */
public class NestedTransactionNotSupportedException extends CannotCreateTransactionException {
    public NestedTransactionNotSupportedException(String message) {
        super(message);
    }

    public NestedTransactionNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
