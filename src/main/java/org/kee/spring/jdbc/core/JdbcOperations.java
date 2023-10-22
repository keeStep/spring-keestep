package org.kee.spring.jdbc.core;

import org.kee.spring.jdbc.core.extractor.ResultSetExtractor;
import org.kee.spring.jdbc.core.rowmapper.RowMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>数据库执行接口
 *
 * @author Eric
 * @date 2023/10/18 21:46
 */
public interface JdbcOperations {

    // -----------------------------------------------------------
    // 执行DDL【execute】
    // -----------------------------------------------------------
    void execute(String sql);
    <T> T execute(StatementCallback<T> action);

    // -----------------------------------------------------------
    // 查询【query】 -- 单个/多个/无参/含参/setter
    // -----------------------------------------------------------
    <T> T query(String sql, ResultSetExtractor<T> res);
    <T> T query(String sql, ResultSetExtractor<T> res, Object[] args);
    <T> T query(String sql, ResultSetExtractor<T> res, PreparedStatementSetter pss);
    <T> List<T> query(String sql, RowMapper<T> rowMapper);
    <T> List<T> query(String sql, RowMapper<T> rowMapper, Object[] args);



    // -----------------------------------------------------------
    // 批量查询【queryForList】
    // -----------------------------------------------------------
    List<Map<String, Object>> queryForList(String sql);
    List<Map<String, Object>> queryForList(String sql, Object... args);
    // 查询某字段
    <T> List<T> queryForList(String sql, Class<T> elementType);
    <T> List<T> queryForList(String sql, Class<T> elementType, Object... args);


    // -----------------------------------------------------------
    // 查询对象【queryForObject】-- 一行数据
    // -----------------------------------------------------------
    <T> T queryForObject(String sql, RowMapper<T> rowMapper);
    <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object[] args);

    // 查询某条记录的某个字段
    <T> T queryForObject(String sql, Class<T> requiredType);


    // -----------------------------------------------------------
    // 查询映射【queryForMap】 -- 一行数据；无参/含参
    // -----------------------------------------------------------
    Map<String, Object> queryForMap(String sql);
    Map<String, Object> queryForMap(String sql, Object... args);
}
