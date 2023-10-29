package org.kee.spring.test;

import org.junit.Before;
import org.junit.Test;
import org.kee.spring.aop.AdvisedSupport;
import org.kee.spring.aop.TargetSource;
import org.kee.spring.aop.aspectj.AspectJExpressionPointcut;
import org.kee.spring.aop.framework.Cglib2AopProxy;
import org.kee.spring.context.support.ClassPathXmlApplicationContext;
import org.kee.spring.jdbc.datasource.DataSourceTransactionManager;
import org.kee.spring.jdbc.support.JdbcTemplate;
import org.kee.spring.test.service.JdbcService;
import org.kee.spring.tx.transaction.annotation.AnnotationTransactionAttributeSource;
import org.kee.spring.tx.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;

/**
 * Unit test for simple App.
 */
public class SpringTxTest {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private JdbcService jdbcService;

    @Before
    public void init() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
        dataSource = applicationContext.getBean("dataSource", DataSource.class);
        jdbcService = applicationContext.getBean("jdbcService", JdbcService.class);
    }


    @Test
    public void testTx() {
        // 1.构建事务拦截器
        AnnotationTransactionAttributeSource transactionAttributeSource = new AnnotationTransactionAttributeSource();
        transactionAttributeSource.findTransactionAttribute(jdbcService.getClass());

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(transactionManager, transactionAttributeSource);

        // 2.构建jdbc代理
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(jdbcService));
        advisedSupport.setMethodInterceptor(transactionInterceptor);
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution (* org.kee.spring.test.service.JdbcService.* (..))"));

        JdbcService proxy = (JdbcService) new Cglib2AopProxy(advisedSupport).getProxy();

        // 3.执行调用测试
        // 3.1.事务（主键冲突两个脚本）-期望结果：全部被回滚
//        proxy.saveData(jdbcTemplate);
        // 3.2.无事务（主键冲突两个脚本）-期望结果：第一个成功执行，第二个执行失败
        proxy.saveDataWithoutTransaction(jdbcTemplate);
    }

}

