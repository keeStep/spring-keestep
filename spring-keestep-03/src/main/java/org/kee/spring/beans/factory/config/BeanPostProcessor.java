package org.kee.spring.beans.factory.config;


import org.kee.spring.beans.BeansException;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/25 23:40
 */
public interface BeanPostProcessor {

    /**
     * 在 Bean 初始化之前执行
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在 Bean 初始化之后执行
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
