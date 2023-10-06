package org.kee.spring.beans.factory.support;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.DisposableBean;
import org.kee.spring.beans.factory.ObjectFactory;
import org.kee.spring.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/8 23:58
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected final Object NULL_OBJECT = new Object();

    /**
     * 一级缓存：普通对象
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * 二级缓存：未完全实例化的对象
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();

    /**
     * 三级缓存：代理对象
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new ConcurrentHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        Object singletonObject = singletonObjects.get(beanName);
        // 一级没有去二级获取
        if (null == singletonObject) {
            singletonObject = earlySingletonObjects.get(beanName);
            // 二级 没有去三级获取
            if (null == singletonObject) {
                ObjectFactory<?> objectFactory = singletonFactories.get(beanName);
                if (null != objectFactory) {
                    singletonObject = objectFactory.getObject();
                    // 把真实对象放到二级缓存，然后删除三级缓存
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }

        return singletonObject;
    }

    public void registerSingleton(String beanName, Object singletonBean) {
        singletonObjects.put(beanName, singletonBean);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        // 添加代理对象时，如果一级缓存有对应的对象，就不用添加，否则：放入三级缓存，删除二级缓存
        if (!singletonObjects.containsKey(beanName)) {
            singletonFactories.put(beanName, singletonFactory);
            earlySingletonObjects.remove(beanName);
        }
    }

    public void registerDisposableBean(String beanName, DisposableBeanAdapter disposableBean) {
        disposableBeans.put(beanName, disposableBean);
    }

    public void destroySingletons() {
        Set<String> keySet = disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0 ; i--) {
            Object disposableBeanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(disposableBeanName);

            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy on bean with name '" + disposableBeanName + "' threw an exception", e);
            }
        }
    }
}
