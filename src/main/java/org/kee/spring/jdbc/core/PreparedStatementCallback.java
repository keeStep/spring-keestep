package org.kee.spring.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/18 21:19
 */
public interface PreparedStatementCallback<T> {

    T doInStatement(PreparedStatement ps) throws SQLException;
}
