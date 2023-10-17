package org.kee.spring.jdbc.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>SQL行转换器接口
 *
 * @author Eric
 * @date 2023/10/17 23:41
 */
public interface RowMapper<T> {

    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
