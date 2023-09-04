package org.kee.spring.beans.factory.surpport;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> 处理 FactoryBean 类的注册操作
 *
 * @author Eric
 * @date 2023/9/4 23:06
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    /**
     * 从缓存中获取 Bean 的 Object
     * 【必须是FactoryBean相关的bean，才可以调用】
     * @param beanName
     * @return
     */
    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return object != NULL_OBJECT ? object : null;
    }

    /**
     * 从用户自定义的 factory 中获取 Bean的 Object
     * @param factory
     * @param beanName
     * @return
     */
    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
        if (factory.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
                factoryBeanObjectCache.put(beanName, object != null ? object : NULL_OBJECT);
            }
            return object == NULL_OBJECT ? object : null;
        } else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    private Object doGetObjectFromFactoryBean(FactoryBean factory, String beanName) {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object [" + beanName + "] creation", e);
        }
    }
}
