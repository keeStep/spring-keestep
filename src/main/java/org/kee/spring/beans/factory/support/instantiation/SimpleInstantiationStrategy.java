package org.kee.spring.beans.factory.support.instantiation;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/9 23:29
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
        Class beanClazz = beanDefinition.getBeanClass();

        try {
            if (Objects.isNull(ctor)) {
                return beanClazz.getDeclaredConstructor().newInstance();
            } else {
                return beanClazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new BeansException("Failed to instantiate [" + beanName + "]", e);
        }
    }
}
