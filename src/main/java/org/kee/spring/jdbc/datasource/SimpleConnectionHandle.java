package org.kee.spring.jdbc.datasource;

import java.sql.Connection;

/**
 * <p>简单的 JDBC Connection 处理器
 *
 * @author Eric
 * @date 2023/10/17 23:17
 */
public class SimpleConnectionHandle implements ConnectionHandle {

    private final Connection connection;

    public SimpleConnectionHandle(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

}
