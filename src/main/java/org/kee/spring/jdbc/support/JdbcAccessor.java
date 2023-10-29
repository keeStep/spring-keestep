package org.kee.spring.jdbc.support;

import cn.hutool.core.lang.Assert;
import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.InitializingBean;

import javax.sql.DataSource;

/**
 * <p> JDBC操作的基类，定义JDBC常见属性：数据源，异常转换器等
 *
 * Base class for {@link org.kee.spring.jdbc.support.JdbcTemplate}
 * and other JDBC-accessing DAO helpers, defining common properties
 * such as dataSource and exception translator.
 *
 * @author Eric
 * @date 2023/10/18 22:22
 */
public abstract class JdbcAccessor implements InitializingBean {

    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取实际使用的数据源
     * Obtain the DataSource for actual use.
     * @return
     */
    protected DataSource obtainDataSource() {
        DataSource dataSource = getDataSource();
        Assert.state(dataSource != null, "No DataSource set");
        return dataSource;
    }

    /**
     * 在bean对象属性填充完成后调用
     *
     * @throws BeansException
     */
    @Override
    public void afterPropertiesSet() {
        if (null == getDataSource()) {
            throw new IllegalArgumentException("Property 'dataSource' is required");
        }
    }
}
