package org.kee.spring.beans.factory.config;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.PropertyValues;

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

    /**
     * 在 Bean 对象实例化完成之后，设置属性操作之前执行
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;

    /**
     * 在 Spring 中由 SmartInstantiationAwareBeanPostProcessor#getEarlyBeanReference 提供
     * @param bean
     * @param beanName
     * @return
     */
    default Object getEarlyBeanReference(Object bean, String beanName) {
        return  bean;
    }
}
