package org.kee.spring.beans.factory.surpport;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.config.BeanDefinition;
import org.kee.spring.beans.factory.surpport.instantiation.CglibSubclassingInstantiationStrategy;
import org.kee.spring.beans.factory.surpport.instantiation.InstantiationStrategy;

import java.lang.reflect.Constructor;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/9 0:09
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition, Object[] args) {
        Object bean = null;
        try {
            // 1.实例化bean
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 2.注册单例bean
        addSingleton(name, bean);

        // 3.返回bean
        return bean;
    }

    protected Object createBeanInstance(String name, BeanDefinition beanDefinition, Object[] args) {
        // 根据参数匹配获取Constructor
        Constructor constructor = null;
        Class beanClass = beanDefinition.getBeanClass();
        Constructor[] declaredConstructors = beanClass.getDeclaredConstructors();

        for (Constructor ctor : declaredConstructors) {
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructor = ctor;
                break;
            }
        }

        // 根据实例化策略实例化
        return getInstantiationStrategy().instantiate(beanDefinition, name, constructor, args);

    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }
}
