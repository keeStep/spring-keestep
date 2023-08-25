package org.kee.spring.beans.factory.config;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.ConfigurableListableBeanFactory;

/**
 * <p> BeanFactory 创建之后，Bean实例化之前执行
 *
 * post processor 是后置处理器的意思
 * @author Eric
 * @date 2023/8/25 23:37
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有的 BeanDefinition 加载完成后，且将 Bean 对象实例化之前，提供修改 BeanDefinition 属性的机制
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
