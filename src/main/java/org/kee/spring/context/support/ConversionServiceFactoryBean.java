package org.kee.spring.context.support;

import cn.hutool.core.collection.CollectionUtil;
import com.sun.istack.internal.Nullable;
import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.FactoryBean;
import org.kee.spring.beans.factory.InitializingBean;
import org.kee.spring.core.convert.ConversionService;
import org.kee.spring.core.convert.converter.Converter;
import org.kee.spring.core.convert.converter.ConverterFactory;
import org.kee.spring.core.convert.converter.GenericConverter;
import org.kee.spring.core.convert.support.DefaultConversionService;
import org.kee.spring.core.convert.support.GenericConversionService;

import java.util.Set;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/12 11:36 下午
 */
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

    @Nullable
    private Set<?> converters;
    @Nullable
    private GenericConversionService conversionService;

    @Override
    public ConversionService getObject() throws Exception {
        return conversionService;
    }

    @Override
    public Class<?> getObjectType() {
        return conversionService.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws BeansException {
        this.conversionService = new DefaultConversionService();
        // 注册类型转换器 及类型转换工厂
        registerConverters();
    }

    private void registerConverters() {
        if (CollectionUtil.isNotEmpty(converters)) {
            for (Object converter : converters) {
                if (converter instanceof GenericConverter) {
                    conversionService.addConverter((GenericConverter) converter);
                } else if (converter instanceof Converter<?, ?>) {
                    conversionService.addConverter((Converter<?, ?>) converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    conversionService.addConverterFactory((ConverterFactory<?, ?>) converter);
                } else {
                    throw new IllegalArgumentException("Each converter object must implement one of the " +
                            "Converter, ConverterFactory, or GenericConverter interfaces");
                }
            }
        }
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }
}
