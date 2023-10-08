package org.kee.spring.beans.factory.support;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.FactoryBean;
import org.kee.spring.beans.factory.config.BeanDefinition;
import org.kee.spring.beans.factory.config.BeanPostProcessor;
import org.kee.spring.beans.factory.config.ConfigurableBeanFactory;
import org.kee.spring.util.ClassUtils;
import org.kee.spring.util.StringValueResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/9 0:01
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    protected <T> T doGetBean(final String name, final Object[] args) {
        // 1.先从缓存获取
         Object bean = getSingleton(name);
        if (Objects.nonNull(bean)) {
            // 如果是 FactoryBean ，则还需要调用 FactoryBean#getObject
            // 【即：通过工厂容器（FactoryBean）获取name对应的对象bean】
            // FIXME 问题：为什么一路下来 FactoryBean 的 beanName 和 需要获取的 Object 的 beanName 一样？
            return (T) getObjectForBeanInstance(bean, name);
        }

        // 2.缓存没有则注册并获取
        // 2.1.获取bean定义
        BeanDefinition beanDefinition = getBeanDefinition(name);
        // 2.2.创建bean
        Object bean1 = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean1, name);
    }

    private Object getObjectForBeanInstance(Object bean, String name) {
        if (!(bean instanceof FactoryBean)) {
            return bean;
        }

        Object object = getCachedObjectForFactoryBean(name);

        if (Objects.isNull(object)) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) bean;
            object = getObjectFromFactoryBean(factoryBean, name);
        }
        return object;
    }

    /**
     * 获取bean定义，由 beanFactory核心类：DefaultListableBeanFactory 实现
     *
     * @param name
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;

    /**
     * 创建bean, 由 AbstractAutowireCapableBeanFactory 实现
     *
     * @param name
     * @param beanDefinition
     * @return
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition, Object[] args) throws BeansException;


    // BeanPostProcessor
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }


    // BeanClassLoader
    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }

    // embeddedValueResolvers
    /**
     * Add a String resolver for embedded values such as annotation attributes.
     *
     * @param stringValueResolver
     */
    @Override
    public void addEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        embeddedValueResolvers.add(stringValueResolver);
    }

    /**
     * Resolve the given embedded value, e.g. an annotation attribute.
     *
     * @param value
     * @return
     */
    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver valueResolver : this.embeddedValueResolvers) {
            result = valueResolver.resolveStringValue(result);
        }
        return result;
    }
}
