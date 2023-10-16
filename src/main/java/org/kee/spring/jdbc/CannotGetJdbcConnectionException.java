package org.kee.spring.jdbc;

import java.sql.SQLException;

/**
 * <p> JDBC链接失败异常
 *
 * @author Eric
 * @date 2023/10/16 23:28
 */
public class CannotGetJdbcConnectionException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CannotGetJdbcConnectionException(String message) {
        super(message);
    }

    public CannotGetJdbcConnectionException(String message, SQLException cause) {
        super(message, cause);
    }
}
