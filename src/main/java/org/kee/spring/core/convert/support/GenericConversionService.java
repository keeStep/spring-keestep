package org.kee.spring.core.convert.support;

import org.kee.spring.core.convert.ConversionService;
import org.kee.spring.core.convert.converter.Converter;
import org.kee.spring.core.convert.converter.ConverterFactory;
import org.kee.spring.core.convert.converter.ConverterRegistry;
import org.kee.spring.core.convert.converter.GenericConverter;

/**
 * <p> TODO 通用类型转换服务
 * Base ConversionService implementation suitable for use in most environments.
 * Indirectly implements ConverterRegistry as registration API through the
 * ConfigurableConversionService interface.
 *
 *
 * @author Eric
 * @date 2023/10/12 11:38 下午
 */
public class GenericConversionService implements ConversionService, ConverterRegistry {

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return false;
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        return null;
    }

    @Override
    public void addConverter(Converter<?, ?> converter) {

    }

    @Override
    public void addConverter(GenericConverter converter) {

    }

    @Override
    public void addConverterFactory(ConverterFactory<?, ?> converterFactory) {

    }
}
