package org.kee.spring.context.support;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.config.BeanPostProcessor;
import org.kee.spring.context.ApplicationContext;
import org.kee.spring.context.aware.ApplicationContextAware;

/**
 * <p> ApplicationContextAware 处理器
 *
 * 为实现 ApplicationContextAware 的 bean 设置感知 ApplicationContext
 *
 * @author Eric
 * @date 2023/9/3 18:20
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
