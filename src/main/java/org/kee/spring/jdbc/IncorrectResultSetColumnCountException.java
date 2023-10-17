package org.kee.spring.jdbc;

/**
 * <p>结果集列数量错误异常
 *
 * @author Eric
 * @date 2023/10/16 23:32
 */
public class IncorrectResultSetColumnCountException extends RuntimeException {

    private final int expectedCount;
    private final int actualCount;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public IncorrectResultSetColumnCountException(int expectedCount, int actualCount) {
        super("Incorrect column count: expected " + expectedCount + ", actual" + actualCount);
        this.expectedCount = expectedCount;
        this.actualCount = actualCount;
    }
}
