package org.kee.spring.beans.factory.surpport;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.config.BeanDefinition;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/9 0:09
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition) {
        Object bean;
        try {
            // 1.实例化bean
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 2.注册单例bean
        addSingleton(name, bean);

        // 3.返回bean
        return bean;
    }
}
