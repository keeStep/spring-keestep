package org.kee.spring.jdbc;

/**
 * <p>未识别的SQL异常
 *
 * @author Eric
 * @date 2023/10/16 23:39
 */
public class UnCategorizedSQLException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public UnCategorizedSQLException(String message) {
        super(message);
    }

    public UnCategorizedSQLException(String task, String sql, Throwable cause) {
        super(sql, cause);
    }
}
