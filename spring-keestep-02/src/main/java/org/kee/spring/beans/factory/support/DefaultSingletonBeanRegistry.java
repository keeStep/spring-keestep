package org.kee.spring.beans.factory.support;

import org.kee.spring.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/8 23:58
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonBeans = new ConcurrentHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonBeans.get(beanName);
    }

    protected void addSingleton(String beanName, Object singletonBean) {
        singletonBeans.put(beanName, singletonBean);
    }
}
