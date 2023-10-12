package org.kee.spring.core.convert;

import com.sun.istack.internal.Nullable;

/**
 * <p>类型转换抽象接口
 *
 * @author Eric
 * @date 2023/10/12 11:10 下午
 */
public interface ConversionService {

    /** Return {@code true} if objects of {@code sourceType} can be converted to the {@code targetType}. */
    boolean canConvert(@Nullable Class<?> sourceType, Class<?> targetType);

    /** Convert the given {@code source} to the specified {@code targetType}. */
    <T> T convert(Object source, Class<T> targetType);
}
