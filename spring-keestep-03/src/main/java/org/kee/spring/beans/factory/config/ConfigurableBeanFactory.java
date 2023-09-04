package org.kee.spring.beans.factory.config;

import org.kee.spring.beans.factory.BeanFactory;
import org.kee.spring.beans.factory.HierarchicalBeanFactory;
import org.kee.spring.beans.factory.config.BeanPostProcessor;
import org.kee.spring.beans.factory.config.SingletonBeanRegistry;

/**
 * Configuration interface to be implemented by most bean factories. Provides
 * facilities to configure a bean factory, in addition to the bean factory
 * client methods in the {@link BeanFactory}
 * interface.
 *
 * @author Eric
 * @date 2023/8/22 22:52
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加 BeanPostProcessor
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例对象
     */
    void destroySingletons();
}
