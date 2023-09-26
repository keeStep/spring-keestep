package org.kee.spring.beans.factory;

import org.kee.spring.beans.BeansException;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/8 23:51
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    <T> T getBean(Class<T> requiredType) throws BeansException;
}
