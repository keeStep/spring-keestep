package org.kee.spring.jdbc.core.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>数据提取器
 *
 * @author Eric
 * @date 2023/10/18 0:23
 */
public interface ResultSetExtractor<T> {

    T extractData(ResultSet rs) throws SQLException;
}
