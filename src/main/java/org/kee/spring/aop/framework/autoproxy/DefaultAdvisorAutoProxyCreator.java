package org.kee.spring.aop.framework.autoproxy;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.kee.spring.aop.AdvisedSupport;
import org.kee.spring.aop.Advisor;
import org.kee.spring.aop.Pointcut;
import org.kee.spring.aop.TargetSource;
import org.kee.spring.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.kee.spring.aop.framework.ProxyFactory;
import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.PropertyValues;
import org.kee.spring.beans.factory.BeanFactory;
import org.kee.spring.beans.factory.aware.BeanFactoryAware;
import org.kee.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.kee.spring.beans.factory.support.DefaultListableBeanFactory;
import org.kee.spring.context.annotation.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p> 代理对象创建者
 *
 * @author Eric
 * @date 2023/9/14 23:46
 */
@Component
public class DefaultAdvisorAutoProxyCreator implements BeanFactoryAware, InstantiationAwareBeanPostProcessor {

    private DefaultListableBeanFactory beanFactory;

    private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    /**
     * 在 Bean 初始化之前执行
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    /**
     * 在 Bean 对象实例化完成之后，设置属性操作之前执行
     *
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    /**
     * 是否基础设施类（AOP的基础设施bean不参与aop的应用）
     * @param beanClass
     * @return
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!earlyProxyReferences.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }

        return bean;
    }

    private Object wrapIfNecessary(Object bean, String beanName) {
        // AOP的基础设施bean不参与aop的应用
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }

        // 1.获取所有通知标记的访问者
        Collection<AspectJExpressionPointcutAdvisor> values = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        // 2.遍历匹配当前beanClass，生成bean的代理对象
        for (AspectJExpressionPointcutAdvisor advisor : values) {
            // 未匹配到当前beanClass，跳过
            if (!advisor.getPointcut().getClassFilter().matches(bean.getClass())) {
                continue;
            }

            // 匹配到当前beanClass，封装AOP参数，并生成代理
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            // TODO TELL ME WHY 从JDK代理改为Cglib代理??
            advisedSupport.setProxyTargetClass(true);

            return new ProxyFactory(advisedSupport).getProxy();
        }

        return bean;
    }


    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) {
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }
}
