package org.kee.spring.jdbc.support;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import org.kee.spring.jdbc.UnCategorizedSQLException;
import org.kee.spring.jdbc.core.*;
import org.kee.spring.jdbc.core.extractor.ResultSetExtractor;
import org.kee.spring.jdbc.core.extractor.RowMapperResultSetExtractor;
import org.kee.spring.jdbc.core.rowmapper.ColumnMapRowMapper;
import org.kee.spring.jdbc.core.rowmapper.RowMapper;
import org.kee.spring.jdbc.core.rowmapper.SingleColumnRowMapper;
import org.kee.spring.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * <p>JDBC操作模板
 *
 * 这是JDBC核心包中的中心类。
 * 它简化了JDBC的使用，并有助于避免常见错误。
 * 它执行核心JDBC工作流，让应用程序代码提供SQL
 * 并提取结果。这个类执行SQL查询或更新，初始化
 * 对resultset进行迭代，捕捉JDBC异常并进行转换
 * 它们变成了通用的
 *
 * @author Eric
 * @date 2023/10/18 22:24
 */
public class JdbcTemplate extends JdbcAccessor implements JdbcOperations {

    private int fetchSize = -1;
    private int maxRows = -1;
    private int queryTimeout = -1;

    public JdbcTemplate() {
    }

    public JdbcTemplate(DataSource dataSource) {
        setDataSource(dataSource);
        afterPropertiesSet();
    }

    public int getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public int getQueryTimeout() {
        return queryTimeout;
    }

    public void setQueryTimeout(int queryTimeout) {
        this.queryTimeout = queryTimeout;
    }

    // -------------------------------------------
    // JDBC操作核心方法：实现 JdbcOperations 定义的数据库操作接口都是调用这里
    // -------------------------------------------
    private <T> T execute(StatementCallback<T> action, boolean closeResources) {
        Connection connection = DataSourceUtils.getConnection(obtainDataSource());

        Statement statement = null;

        try {
            // 获取语句
            statement = connection.createStatement();
            // 应用设置
            applyStatementSettings(statement);
            // 执行语句
            return action.doInStatement(statement);
        } catch (SQLException e) {
            String sql = getSql(action);
            JdbcUtils.closeStatement(statement);
            statement = null;
            throw translateException("ConnectionCallback", sql, e);
        } finally {
            if (closeResources) {
                JdbcUtils.closeStatement(statement);
            }
        }
    }

    private <T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> action, boolean closeResources) {

        Assert.notNull(psc, "PreparedStatementCreator must not be null");
        Assert.notNull(action, "Callback object must not be null");


        Connection con = DataSourceUtils.getConnection(obtainDataSource());
        PreparedStatement ps = null;
        try {
            ps = psc.createPreparedStatement(con);
            applyStatementSettings(ps);
            T result = action.doInPreparedStatement(ps);
            return result;
        } catch (SQLException ex) {

            String sql = getSql(psc);
            psc = null;
            JdbcUtils.closeStatement(ps);
            ps = null;
            DataSourceUtils.releaseConnection(con, getDataSource());
            con = null;
            throw translateException("PreparedStatementCallback", sql, ex);
        } finally {
            if (closeResources) {

                JdbcUtils.closeStatement(ps);
                DataSourceUtils.releaseConnection(con, getDataSource());
            }
        }
    }

    public  <T> T query(PreparedStatementCreator psc, PreparedStatementSetter pss, ResultSetExtractor<T> res) {
        Assert.notNull(res, "ResultSetExtractor must not be null");

        return execute(psc, new PreparedStatementCallback<T>() {
            @Override
            public T doInPreparedStatement(PreparedStatement ps) throws SQLException {
                ResultSet rs = null;
                try {
                    if (null != pss) {
                        pss.setValues(ps);
                    }

                    rs = ps.executeQuery();
                    return res.extractData(rs);
                } finally {
                    JdbcUtils.closeResultSet(rs);
                }
            }
        }, true);
    }


    // -------------------
    // 辅助方法
    // -------------------
    private static String getSql(Object sqlProvider) {
        if (sqlProvider instanceof SqlProvider) {
            return ((SqlProvider) sqlProvider).getSql();
        }

        return null;
    }

    private static <T> T result(T result) {
        Assert.state(null != result, "No result");
        return result;
    }

    protected void applyStatementSettings(Statement statement) throws SQLException {
        int fetchSize = getFetchSize();
        if (-1 != fetchSize) {
            statement.setFetchSize(fetchSize);
        }

        int maxRows = getMaxRows();
        if (-1 != maxRows) {
            statement.setMaxRows(maxRows);
        }
    }

    protected UnCategorizedSQLException translateException(String task, String sql, SQLException e) {
        return new UnCategorizedSQLException(task, sql, e);
    }



    // -------------------------------------------
    // 实现 JdbcOperations 定义的数据库操作接口
    // -------------------------------------------

    @Override
    public void execute(String sql) {
        class ExecuteStatementCallback implements StatementCallback<Object>, SqlProvider {

            @Override
            public String getSql() {
                return sql;
            }

            @Override
            public Object doInStatement(Statement statement) throws SQLException {
                statement.execute(sql);
                return null;
            }
        }

        execute(new ExecuteStatementCallback(), true);
    }

    @Override
    public <T> T execute(StatementCallback<T> action) {
        return execute(action, true);
    }

    @Override
    public <T> T query(String sql, ResultSetExtractor<T> res) {
        Assert.notNull(sql, "SQL must not be null");
        Assert.notNull(res, "ResultSetExtractor must be null");

        class QueryStatementCallback implements StatementCallback<T>, SqlProvider {

            @Override
            public String getSql() {
                return sql;
            }

            @Override
            public T doInStatement(Statement statement) throws SQLException {
                ResultSet resultSet = statement.executeQuery(sql);
                return res.extractData(resultSet);
            }
        }

        return execute(new QueryStatementCallback(), true);
    }

    // ↑↑↑↑↑↑↑↑ --- 以上三个是 JdbcOperations 的核心方法 --- ↑↑↑↑↑↑↑↑


    @Override
    public <T> T query(String sql, ResultSetExtractor<T> res, Object[] args) {
        return query(sql, res, new ArgumentPreparedStatementSetter(args));
    }

    @Override
    public <T> T query(String sql, ResultSetExtractor<T> res, PreparedStatementSetter pss) {
        return query(new SimplePreparedStatementCreator(sql), pss, res);
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper) {
        return result(query(sql, new RowMapperResultSetExtractor<>(rowMapper)));
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object[] args) {
        return result(query(sql, new RowMapperResultSetExtractor<>(rowMapper), args));
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql) {
        return query(sql, new ColumnMapRowMapper());
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql, Object... args) {
        return query(sql, new ColumnMapRowMapper(), args);
    }

    @Override
    public <T> List<T> queryForList(String sql, Class<T> elementType) {
        return query(sql, new SingleColumnRowMapper<>(elementType));
    }

    @Override
    public <T> List<T> queryForList(String sql, Class<T> elementType, Object... args) {
        return query(sql, new SingleColumnRowMapper<>(elementType), args);
    }

    @Override
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper) {
        List<T> result = query(sql, rowMapper);

        if (CollUtil.isEmpty(result)) {
            throw new RuntimeException("Incorrect result size: expected 1, actual 0");
        }

        if (result.size() > 1) {
            throw new RuntimeException("Incorrect result size: expected 1, actual " + result.size());
        }
        return result.iterator().next();
    }

    @Override
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object[] args) {
        List<T> result = query(sql, rowMapper, args);

        if (CollUtil.isEmpty(result)) {
            throw new RuntimeException("Incorrect result size: expected 1, actual 0");
        }

        if (result.size() > 1) {
            throw new RuntimeException("Incorrect result size: expected 1, actual " + result.size());
        }
        return result.iterator().next();
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> requiredType) {
        return queryForObject(sql, new SingleColumnRowMapper<>(requiredType));
    }

    @Override
    public Map<String, Object> queryForMap(String sql) {
        return result(queryForObject(sql, new ColumnMapRowMapper()));
    }

    @Override
    public Map<String, Object> queryForMap(String sql, Object... args) {
        return result(queryForObject(sql, new ColumnMapRowMapper(), args));
    }
}
