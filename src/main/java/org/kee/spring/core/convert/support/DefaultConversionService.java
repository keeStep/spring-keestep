package org.kee.spring.core.convert.support;

import org.kee.spring.core.convert.converter.ConverterRegistry;

/**
 * <p> 默认类型转换服务
 * A specialization of {@link GenericConversionService} configured by default
 * with converters appropriate for most environments.
 *
 * @author Eric
 * @date 2023/10/12 11:43 下午
 */
public class DefaultConversionService extends GenericConversionService {

    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    private void addDefaultConverters(ConverterRegistry converterRegistry) {
        // 添加各类型转换器工厂
        // ---这里先添加一个 string -》number
        converterRegistry.addConverterFactory(new String2NumberConversionFactory());
    }
}
