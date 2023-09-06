package org.kee.spring.beans.factory.config;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/8 23:55
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObject);
}
