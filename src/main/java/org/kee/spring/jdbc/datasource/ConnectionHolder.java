package org.kee.spring.jdbc.datasource;

import cn.hutool.core.lang.Assert;

import java.sql.Connection;

/**
 * 包装JDBC连接的资源持有者
 * 【提供链接的：获取，重置，处理器等】
 * <p> Resource holder wrapping a JDBC {@link Connection}
 *
 * @author Eric
 * @date 2023/10/17 23:06
 */
public class ConnectionHolder {

    private ConnectionHandle connectionHandle;

    private Connection currentConnection;

    public ConnectionHolder(ConnectionHandle connectionHandle) {
        this.connectionHandle = connectionHandle;
    }

    public ConnectionHolder(Connection currentConnection) {
        this.connectionHandle = new SimpleConnectionHandle(currentConnection);
        this.currentConnection = currentConnection;
    }

    public boolean hasConnection() {
        return this.connectionHandle != null;
    }

    public Connection getConnection() {
        Assert.notNull(this.connectionHandle, "Active connection is required.");
        if (null == this.currentConnection) {
            this.currentConnection = this.connectionHandle.getConnection();
        }
        return currentConnection;
    }

    public void setConnection(Connection connection) {
        // 1.释放原来的链接
        if (null != this.currentConnection) {
            if (null != this.connectionHandle) {
                this.connectionHandle.releaseConnection(this.currentConnection);
            }

            currentConnection = null;
        }

        // 2.设置新链接
        if (null != connection) {
            this.connectionHandle = new SimpleConnectionHandle(connection);
        } else {
            this.connectionHandle = null;
        }
    }

    public ConnectionHandle getConnectionHandle() {
        return connectionHandle;
    }
}
