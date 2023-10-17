package org.kee.spring.jdbc.datasource;

import org.kee.spring.jdbc.CannotGetJdbcConnectionException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>数据源操作工具类
 *
 * @author Eric
 * @date 2023/10/16 23:26
 */
public abstract class DataSourceUtils {

    public static Connection getConnection(DataSource dataSource) {
        try {
            return doGetConnection(dataSource);
        } catch (SQLException e) {
            throw new CannotGetJdbcConnectionException("Failed to obtain JDBC Connection", e);
        }
    }


    public static Connection doGetConnection(DataSource dataSource) throws SQLException {
        Connection connection = fetchConnection(dataSource);
        // TODO ConnectionHolder
        return connection;
    }


    private static Connection fetchConnection(DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();
        if (null == connection) {
            throw new IllegalArgumentException("Datasource return null from getConnection():" + dataSource);
        }

        return connection;
    }
}
