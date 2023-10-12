package org.kee.spring.core.convert.converter;

/**
 * <p>类型转换处理接口
 *
 * @author Eric
 * @date 2023/10/9 11:33 下午
 */
public interface Converter<S, T> {
    T convert(S source);
}
