package org.kee.spring.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.cj.jdbc.Driver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Unit test for simple App.
 */
public class JdbcTest {

    private DruidDataSource dataSource;
    private Connection connection;
    private Statement statement;




    @Before
    public void init() throws SQLException {
        dataSource = new DruidDataSource();
        dataSource.setDriver(new Driver());
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("123");
        
        connection = dataSource.getConnection().getConnection();
        
        statement = connection.createStatement(); 
    }
    
    
    
    @Test
    public void ddlTest() throws SQLException {
        boolean execute = statement.execute(
                "CREATE TABLE `user` (\n" +
                "    `id` int NOT NULL AUTO_INCREMENT,\n" +
                "    `username` varchar(100) DEFAULT NULL,\n" +
                "    PRIMARY KEY (`id`),\n" +
                "    UNIQUE KEY `uq_user_id` (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=1 ");

        System.out.println(execute);
    }

    @After
    public void destroy() throws SQLException {
        if (null != statement) {
            statement.close();
        }
        
        if (null != connection) {
            connection.close();
        }
        
        if (null != dataSource) {
            dataSource.close();
        }
    }
    
}

