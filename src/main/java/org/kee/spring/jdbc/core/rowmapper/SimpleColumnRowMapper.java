package org.kee.spring.jdbc.core.rowmapper;

import org.kee.spring.jdbc.IncorrectResultSetColumnCountException;
import org.kee.spring.util.JdbcUtils;
import org.kee.spring.util.NumberUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/17 23:44
 */
public class SimpleColumnRowMapper<T> implements RowMapper<T> {

    private Class<?> requireType;

    public SimpleColumnRowMapper() {
    }

    public SimpleColumnRowMapper(Class<?> requireType) {
        this.requireType = requireType;
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        if (columnCount != 1) {
            throw new IncorrectResultSetColumnCountException(1, columnCount);
        }

        // * 1.获取字段的值
        Object result = getColumnValue(rs, 1, this.requireType);

        // * 2.当字段值的类型和目标类型不一致时，进行转换
        if (null != result && null != this.requireType && !this.requireType.isInstance(result)) {

            try {
                return (T) convertValueToRequireType(result, this.requireType);
            } catch (IllegalArgumentException e) {

            }
        }

        return (T) result;
    }

    /**
     * 获取字段的值
     * @param rs
     * @param index
     * @param requireType
     * @return
     * @throws SQLException
     */
    protected Object getColumnValue(ResultSet rs, int index, Class<?> requireType) throws SQLException {
        if (null != requireType) {
            return JdbcUtils.getResultSetValue(rs, index, requireType);
        }
        return JdbcUtils.getResultSetValue(rs, index);
    }

    /**
     * 当字段值的类型和目标类型不一致时，进行转换
     * @param value
     * @param requireType
     * @return
     */
    private Object convertValueToRequireType(Object value, Class<?> requireType) {
        if (String.class == requireType) {
            return value.toString();
        } else if (Number.class.isAssignableFrom(requireType)) {
            // isAssignableFrom : 我是你爸爸吗？
            if (value instanceof Number) {
                return NumberUtils.convertNumberToTargetClass((Number) value, (Class<Number>) requireType);
            } else {
                return NumberUtils.parseNumber(value.toString(), (Class<Number>) requireType);
            }
        }
        return null;
    }
}
