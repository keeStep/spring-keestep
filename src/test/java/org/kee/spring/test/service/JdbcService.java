package org.kee.spring.test.service;

import org.kee.spring.jdbc.support.JdbcTemplate;
import org.kee.spring.tx.transaction.annotation.Transactional;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/29 17:21
 */
public class JdbcService {

    @Transactional(rollbackFor = Exception.class)
    public void saveData(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("insert into user (id, username) values (4, '刘大木')");
        jdbcTemplate.execute("insert into user (id, username) values (4, '李大招')");
    }

    public void saveDataWithoutTransaction(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("insert into user (id, username) values (4, '刘大木')");
        jdbcTemplate.execute("insert into user (id, username) values (4, '李大招')");
    }
}
