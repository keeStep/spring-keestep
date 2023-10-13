package org.kee.spring.core.convert.support;

import org.kee.spring.core.convert.ConversionService;
import org.kee.spring.core.convert.converter.Converter;
import org.kee.spring.core.convert.converter.ConverterFactory;
import org.kee.spring.core.convert.converter.ConverterRegistry;
import org.kee.spring.core.convert.converter.GenericConverter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * <p> 通用类型转换服务
 * Base ConversionService implementation suitable for use in most environments.
 * Indirectly implements ConverterRegistry as registration API through the
 * ConfigurableConversionService interface.
 *
 *
 * @author Eric
 * @date 2023/10/12 11:38 下午
 */
public class GenericConversionService implements ConversionService, ConverterRegistry {

    private Map<GenericConverter.ConvertiblePair, GenericConverter> converterMap = new HashMap<>();

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return Objects.nonNull(getConverter(sourceType, targetType));
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        GenericConverter converter = getConverter(source.getClass(), targetType);
        return (T) converter.convert(source, source.getClass(), targetType);
    }

    @Override
    public void addConverter(Converter<?, ?> converter) {
        GenericConverter.ConvertiblePair typeInfo = getRequiredTypeInfo(converter);
        ConverterAdapter converterAdapter = new ConverterAdapter(typeInfo, converter);
        for (GenericConverter.ConvertiblePair convertibleType : converterAdapter.getConvertibleTypes()) {
            converterMap.put(convertibleType, converterAdapter);
        }
    }

    @Override
    public void addConverter(GenericConverter converter) {
        for (GenericConverter.ConvertiblePair convertibleType : converter.getConvertibleTypes()) {
            converterMap.put(convertibleType, converter);
        }
    }

    @Override
    public void addConverterFactory(ConverterFactory<?, ?> converterFactory) {
        GenericConverter.ConvertiblePair typeInfo = getRequiredTypeInfo(converterFactory);
        ConverterFactoryAdapter converterFactoryAdapter = new ConverterFactoryAdapter(typeInfo, converterFactory);
        for (GenericConverter.ConvertiblePair convertibleType : converterFactoryAdapter.getConvertibleTypes()) {
            converterMap.put(convertibleType, converterFactoryAdapter);
        }
    }

    /**
     * 获取需要的类型对信息
     * @param object
     * @return
     */
    private GenericConverter.ConvertiblePair getRequiredTypeInfo(Object object) {
        Type[] types = object.getClass().getGenericInterfaces();

        ParameterizedType type = (ParameterizedType) types[0];
        Type[] actualTypeArguments = type.getActualTypeArguments();

        Class sourceType = (Class) actualTypeArguments[0];
        Class targetType = (Class) actualTypeArguments[0];

        return new GenericConverter.ConvertiblePair(sourceType, targetType);
    }


    /**
     * 获取类型转换器
     * @param sourceType
     * @param targetType
     * @return
     */
    protected GenericConverter getConverter(Class<?> sourceType, Class<?> targetType) {
        List<Class<?>> sourceCandidates = getClassHierarchy(sourceType);
        List<Class<?>> targetCandidates = getClassHierarchy(targetType);

        // 遍历source与target类族，根据类型对获取类型转换器（k，v）
        for (Class<?> sourceCandidate : sourceCandidates) {
            for (Class<?> targetCandidate : targetCandidates) {
                GenericConverter.ConvertiblePair convertiblePair = new GenericConverter.ConvertiblePair(sourceCandidate, targetCandidate);
                GenericConverter converter = converterMap.get(convertiblePair);
                if (null != converter) {
                    return converter;
                }
            }
        }
        return null;
    }

    /**
     * 获取类的 类族
     * @param clazz
     * @return
     */
    private List<Class<?>> getClassHierarchy(Class<?> clazz) {
        List<Class<?>> hierarchy = new ArrayList<>();

        while (null != clazz) {
            hierarchy.add(clazz);
            clazz = clazz.getSuperclass();
        }

        return hierarchy;
    }

    /**
     * 转换器适配器
     */
    private final class ConverterAdapter implements GenericConverter {

        private final ConvertiblePair typeInfo;
        private final Converter<Object, Object> converter;

        public ConverterAdapter(ConvertiblePair typeInfo, Converter<?, ?> converter) {
            this.typeInfo = typeInfo;
            this.converter = (Converter<Object, Object>) converter;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(typeInfo);
        }

        @Override
        public Object convert(Object source, Class sourceType, Class targetType) {
            return converter.convert(source);
        }
    }

    /**
     * 转换工厂适配器
     */
    private final class ConverterFactoryAdapter implements GenericConverter {

        private final GenericConverter.ConvertiblePair typeInfo;
        private final ConverterFactory<Object, Object> converterFactory;

        public ConverterFactoryAdapter(GenericConverter.ConvertiblePair typeInfo, ConverterFactory<?, ?> converterFactory) {
            this.typeInfo = typeInfo;
            this.converterFactory = (ConverterFactory<Object, Object>) converterFactory;
        }


        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(typeInfo);
        }

        @Override
        public Object convert(Object source, Class sourceType, Class targetType) {
            return converterFactory.getConverter(targetType).convert(source);
        }
    }
}
