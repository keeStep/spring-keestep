package org.kee.spring.core.convert.converter;

/**
 * <p>类型转换注册接口
 *
 * @author Eric
 * @date 2023/10/9 11:37 下午
 */
public interface ConverterRegistry {

    void addConverter(Converter<?, ?> converter);
    void addConverter(GenericConverter converter);
    void addConverterFactory(ConverterFactory<?, ?> converterFactory);
}
