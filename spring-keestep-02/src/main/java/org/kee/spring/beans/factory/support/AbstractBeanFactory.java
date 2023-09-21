package org.kee.spring.beans.factory.support;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.BeanFactory;
import org.kee.spring.beans.factory.config.BeanDefinition;

import java.util.Objects;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/9 0:01
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) throws BeansException {
        // 1.先从缓存获取
        Object bean = getSingleton(name);
        if (Objects.nonNull(bean)) {
            return bean;
        }

        // 2.缓存没有则注册并获取
        // 2.1.获取bean定义
        BeanDefinition beanDefinition = getBeanDefinition(name);
        // 2.2.创建bean
        return createBean(name, beanDefinition);
    }

    /**
     * 获取bean定义，由 beanFactory核心类：DefaultListableBeanFactory 实现
     * @param name
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String name);

    /**
     * 创建bean, 由 AbstractAutowireCapableBeanFactory 实现
     * @param name
     * @param beanDefinition
     * @return
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition);
}
