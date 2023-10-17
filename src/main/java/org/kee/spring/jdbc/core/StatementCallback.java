package org.kee.spring.jdbc.core;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Generic callback interface for code that operates on a JDBC Statement.
 * Allows to execute any number of operations on a single Statement,
 * for example a single {@code executeUpdate} call or repeated
 * {@code executeUpdate} calls with varying SQL.
 *
 * <p>Used internally by JdbcTemplate, but also useful for application code.
 *
 * @author Eric
 * @date 2023/10/17 23:38
 */
public interface StatementCallback<T> {

    T doInStatement(Statement statement) throws SQLException;
}
