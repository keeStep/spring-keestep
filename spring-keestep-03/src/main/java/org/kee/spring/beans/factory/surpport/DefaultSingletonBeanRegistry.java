package org.kee.spring.beans.factory.surpport;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.DisposableBean;
import org.kee.spring.beans.factory.config.SingletonBeanRegistry;

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

    private final Map<String, Object> singletonBeans = new ConcurrentHashMap<>();
    private final Map<String, DisposableBean> disposableBeans = new ConcurrentHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonBeans.get(beanName);
    }

    protected void registerSingleton(String beanName, Object singletonBean) {
        singletonBeans.put(beanName, singletonBean);
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
