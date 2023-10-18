package org.kee.spring.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/18 21:23
 */
public class ArgumentPreparedStatementSetter implements PreparedStatementSetter {

    private final Object[] args;

    public ArgumentPreparedStatementSetter(Object[] args) {
        this.args = args;
    }

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
    }
}
