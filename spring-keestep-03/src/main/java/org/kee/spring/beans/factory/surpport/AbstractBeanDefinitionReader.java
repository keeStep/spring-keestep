package org.kee.spring.beans.factory.surpport;

import org.kee.spring.beans.factory.config.BeanDefinitionRegistry;
import org.kee.spring.core.io.DefaultResourceLoader;
import org.kee.spring.core.io.ResourceLoader;

/**
 * <p>读取Bean对象定义到BeanDefinition（抽象类公共处理）
 *
 * @author Eric
 * @date 2023/8/15 23:14
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getBeanRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
