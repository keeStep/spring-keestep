package org.kee.spring.beans.factory.config;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.BeanFactory;

/**
 * Extension of the {@link BeanFactory}
 * interface to be implemented by bean factories that are capable of
 * autowiring, provided that they want to expose this functionality for
 *
 * @author Eric
 * @date 2023/8/22 22:51
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 执行实现接口 BeanPostProcessor 的方法 postProcessBeforeInitialization
     * @param existingBean
     * @param beanName
     * @return
     */
    Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 执行实现接口 BeanPostProcessor 的方法 postProcessAfterInitialization
     * @param existingBean
     * @param beanName
     * @return
     */
    Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
