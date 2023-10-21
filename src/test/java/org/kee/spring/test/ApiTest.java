package org.kee.spring.test;

import org.junit.Before;
import org.junit.Test;
import org.kee.spring.context.support.ClassPathXmlApplicationContext;
import org.kee.spring.core.convert.converter.Converter;
import org.kee.spring.core.convert.support.String2NumberConverterFactory;
import org.kee.spring.jdbc.support.JdbcTemplate;
import org.kee.spring.test.bean.Husband;
import org.kee.spring.test.converter.String2IntegerConverter;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Unit test for simple App.
 */
public class ApiTest {

    private JdbcTemplate jdbcTemplate;

    @Before
    public void init() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
    }


    @Test
    public void ddlTest() throws SQLException {
        jdbcTemplate.execute(
                "CREATE TABLE `city` (\n" +
                "    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "    `city_name` varchar(100) DEFAULT NULL,\n" +
                "    PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB COMMENT '城市信息表'");
    }
}

