package org.kee.spring.beans.factory.surpport;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.config.BeanDefinition;
import org.kee.spring.beans.factory.config.BeanDefinitionRegistry;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/9 0:13
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    protected BeanDefinition getBeanDefinition(String name) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (Objects.isNull(beanDefinition)) {
            throw new BeansException("No bean named '" + name + "' is defined");
        }
        return beanDefinition;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition definition) {
        beanDefinitionMap.put(beanName, definition);
    }
}
