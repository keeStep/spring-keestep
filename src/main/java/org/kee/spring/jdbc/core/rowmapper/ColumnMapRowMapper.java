package org.kee.spring.jdbc.core.rowmapper;

import org.kee.spring.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>对某行数据，把字段值挨个放到map
 *
 * @author Eric
 * 
 * @date 2023/10/18 0:08
 */
public class ColumnMapRowMapper implements RowMapper<Map<String, Object>> {
    @Override
    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        Map<String, Object> mapOfColumnValues = new LinkedHashMap<>(columnCount);
        // 遍历获取每行列值
        // -- 这里得从1开始遍历，因为：
        // com.mysql.cj.jdbc.result.ResultSetMetaData.getField中
        // 1.取的是 this.fields[columnIndex - 1]
        // 2.代码判断了index小于1直接去抛异常了
        for (int i = 1; i <= columnCount; i++) {
            // 1.列名（字段名）
            String columnName = JdbcUtils.lookupColumnName(metaData, i);
            // 2.列值（字段值）
            Object columnValue = JdbcUtils.getResultSetValue(rs, i);

            // 3.收集字段键值
            mapOfColumnValues.putIfAbsent(columnName, columnValue);
        }
        return mapOfColumnValues;
    }
}
