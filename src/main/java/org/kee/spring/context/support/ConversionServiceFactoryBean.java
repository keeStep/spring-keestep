package org.kee.spring.context.support;

import com.sun.istack.internal.Nullable;
import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.FactoryBean;
import org.kee.spring.beans.factory.InitializingBean;
import org.kee.spring.core.convert.ConversionService;
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
        //TODO 注册类型转换器
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }
}
