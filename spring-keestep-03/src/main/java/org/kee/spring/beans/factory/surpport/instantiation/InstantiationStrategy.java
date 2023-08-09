package org.kee.spring.beans.factory.surpport.instantiation;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/9 23:27
 */
public interface InstantiationStrategy {

    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;

}
