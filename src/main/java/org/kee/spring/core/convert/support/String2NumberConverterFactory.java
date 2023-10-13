package org.kee.spring.core.convert.support;

import cn.hutool.core.util.StrUtil;
import org.kee.spring.core.convert.converter.Converter;
import org.kee.spring.core.convert.converter.ConverterFactory;
import org.kee.spring.util.NumberUtils;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/12 11:47 下午
 */
public class String2NumberConverterFactory implements ConverterFactory<String, Number> {
    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<>(targetType);
    }

    private static final class StringToNumber<T extends Number> implements Converter<String, T> {
        private final Class<T> targetType;

        public StringToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            if (StrUtil.isBlank(source)) {
                return null;
            }
            return NumberUtils.parseNumber(source, this.targetType);
        }
    }
}
