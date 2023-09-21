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
import org.kee.spring.beans.factory.BeanFactory;
import org.kee.spring.beans.factory.aware.BeanFactoryAware;
import org.kee.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.kee.spring.beans.factory.support.DefaultListableBeanFactory;

import java.util.Collection;

/**
 * <p> 代理对象创建者
 *
 * @author Eric
 * @date 2023/9/14 23:46
 */
public class DefaultAdvisorAutoProxyCreator implements BeanFactoryAware, InstantiationAwareBeanPostProcessor {

    private DefaultListableBeanFactory beanFactory;

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
        // AOP的基础设施bean不参与aop的应用
        if (isInfrastructureClass(beanClass)) {
            return null;
        }

        // 1.获取所有通知标记的访问者
        Collection<AspectJExpressionPointcutAdvisor> values = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        // 2.遍历匹配当前beanClass，生成bean的代理对象
        for (AspectJExpressionPointcutAdvisor advisor : values) {
            // 未匹配到当前beanClass，跳过
            if (!advisor.getPointcut().getClassFilter().matches(beanClass)) {
                continue;
            }

            // 匹配到当前beanClass，封装AOP参数，并生成代理
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = null;
            try {
                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }

            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setProxyTargetClass(false);

            return new ProxyFactory(advisedSupport).getProxy();
        }

        return null;
    }

    /**
     * 是否寄基础设施类（AOP的基础设施bean不参与aop的应用）
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
        return bean;
    }
}
