package org.kee.spring.core.convert.converter;


/**
 * <p>类型转换工厂🏭
 *
 * @author Eric
 * @date 2023/10/9 11:34 下午
 */
public interface ConverterFactory<S, R> {

    /**
     * 获取从 S 转为 T 的类型转换器 （T 是 R 的实例）
     * @param targetType
     * @param <T>
     * @return
     */
    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
