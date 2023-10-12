package org.kee.spring.beans.factory.config;

import com.sun.istack.internal.Nullable;
import org.kee.spring.beans.factory.BeanFactory;
import org.kee.spring.beans.factory.HierarchicalBeanFactory;
import org.kee.spring.beans.factory.config.BeanPostProcessor;
import org.kee.spring.beans.factory.config.SingletonBeanRegistry;
import org.kee.spring.core.convert.ConversionService;
import org.kee.spring.util.StringValueResolver;

/**
 * Configuration interface to be implemented by most bean factories. Provides
 * facilities to configure a bean factory, in addition to the bean factory
 * client methods in the {@link BeanFactory}
 * interface.
 *
 * @author Eric
 * @date 2023/8/22 22:52
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加 BeanPostProcessor
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例对象
     */
    void destroySingletons();

    /**
     * Add a String resolver for embedded values such as annotation attributes.
     * @param stringValueResolver
     */
    void addEmbeddedValueResolver(StringValueResolver stringValueResolver);

    /**
     * Resolve the given embedded value, e.g. an annotation attribute.
     * @param value
     * @return
     */
    String resolveEmbeddedValue(String value);

    /**
     * Specify a Spring 3.0 ConversionService to use for converting
     * property values, as an alternative to JavaBeans PropertyEditors.
     * @since 3.0
     */
    void setConversionService(ConversionService conversionService);

    /**
     * Return the associated ConversionService, if any.
     * @since 3.0
     */
    @Nullable
    ConversionService getConversionService();
}
