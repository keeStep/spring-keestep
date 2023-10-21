package org.kee.spring.test;

import org.junit.Before;
import org.junit.Test;
import org.kee.spring.context.support.ClassPathXmlApplicationContext;
import org.kee.spring.jdbc.support.JdbcTemplate;

import java.util.List;
import java.util.Map;

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
    public void ddlTest() {
        jdbcTemplate.execute(
                "CREATE TABLE `city` (\n" +
                "    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "    `city_name` varchar(100) DEFAULT NULL,\n" +
                "    PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB COMMENT '城市信息表'");
    }
    
    
    @Test
    public void queryForListTest() {
        // FIXME
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from user");
        for (int i = 0; i < maps.size(); i++) {
            System.out.printf("第%d行数据" + maps.get(i), i + 1);
        }
    }
    
    @Test
    public void queryForListWithColumnClassTypeTest() {
        List<String> strings = jdbcTemplate.queryForList("select username from user", String.class);
        for (int i = 0; i < strings.size(); i++) {
            System.out.printf("第%d行数据" + strings.get(i), i + 1);
            System.out.println();
        }
    }
    
    @Test
    public void queryForListWithColumnClassTypeAndArgTest() {
        List<String> strings = jdbcTemplate.queryForList("select username from user where id =?", String.class, 2);
        for (int i = 0; i < strings.size(); i++) {
            System.out.printf("第%d行数据" + strings.get(i), i + 1);
            System.out.println();
        }
    }
    
    @Test
    public void queryListWithArgTest() {
        // FIXME
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from user where id =?", 2);
        for (int i = 0; i < maps.size(); i++) {
            System.out.printf("第%d行数据" + maps.get(i), i + 1);
        }
    }
}

