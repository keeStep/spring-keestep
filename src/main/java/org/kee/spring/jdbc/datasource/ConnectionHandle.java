package org.kee.spring.jdbc.datasource;

import java.sql.Connection;

/**
 * 提供给 JDBC Connection 处理器的接口
 * <p>Simple interface to be implemented by handles for a JDBC Connection.
 *
 * @author Eric
 * @date 2023/10/17 23:12
 */
public interface ConnectionHandle {

    Connection getConnection();

    default void releaseConnection(Connection con) {
    }
}
