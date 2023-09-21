package org.kee.spring.beans.factory.config;

import org.kee.spring.beans.BeansException;

/**
 * <p> Subinterface of {@link BeanPostProcessor} that adds a before-instantiation callback,
 * and a callback after instantiation but before explicit properties are set or autowiring occurs.
 * @author Eric
 * @date 2023/9/14 23:47
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {


    /**
     * 在 Bean 初始化之前执行
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Class<?> beanClass, String beanName) throws BeansException;
}
