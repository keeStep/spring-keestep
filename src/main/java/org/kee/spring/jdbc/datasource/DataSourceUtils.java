package org.kee.spring.jdbc.datasource;

import org.kee.spring.jdbc.CannotGetJdbcConnectionException;
import org.kee.spring.tx.transaction.support.TransactionSynchronizationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * <p>数据源链接操作工具类
 *
 * @author Eric
 * @date 2023/10/16 23:26
 */
public abstract class DataSourceUtils {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceUtils.class);


    public static Connection getConnection(DataSource dataSource) {
        try {
            return doGetConnection(dataSource);
        } catch (SQLException e) {
            throw new CannotGetJdbcConnectionException("Failed to obtain JDBC Connection", e);
        }
    }


    public static Connection doGetConnection(DataSource dataSource) throws SQLException {
        // 事务操作时，数据库链接保存在线程共享的管理器中（ThreadLocal），需从管理器获取，保证事务执行
        ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);
        
        if (Objects.nonNull(conHolder) && conHolder.hasConnection()) {
            return conHolder.getConnection();
        }
        
        return fetchConnection(dataSource);
    }


    private static Connection fetchConnection(DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();
        if (null == connection) {
            throw new IllegalArgumentException("Datasource return null from getConnection():" + dataSource);
        }

        return connection;
    }

    public static void releaseConnection(Connection conn, DataSource dataSource) {
        try {
            doReleaseConnection(conn, dataSource);
        } catch (SQLException ex) {
            logger.debug("Could not close JDBC Connection", ex);
        } catch (Throwable ex) {
            logger.debug("Unexpected expresion on closing JDBC Connection", ex);
        }
    }

    public static void doReleaseConnection(Connection conn, DataSource dataSource) throws SQLException {
        if (conn == null) {
            return;
        }
        
        doCloseConnection(conn, dataSource);
    }

    public static void doCloseConnection(Connection conn, DataSource dataSource) throws SQLException {
        conn.close();
    }

}
