package org.kee.spring.beans.factory.support;

import org.kee.spring.beans.factory.config.BeanDefinition;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/8 23:57
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition definition);

    boolean containsBeanDefinition(String beanName);
}
