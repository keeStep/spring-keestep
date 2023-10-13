package org.kee.spring.core.convert.converter;

import cn.hutool.core.lang.Assert;

import java.util.Objects;
import java.util.Set;

/**
 * <p> 通用类型转换器
 *
 * @author Eric
 * @date 2023/10/9 11:41 下午
 */
public interface GenericConverter {

    /**
     * 获取转换类型对的集合
     * @return
     */
    Set<ConvertiblePair> getConvertibleTypes();

    /**
     * 类型转换
     */
    Object convert(Object source, Class sourceType, Class targetType);

    /**
     * 转换类型对
     */
    final class ConvertiblePair {
        private final Class<?> sourceType;
        private final Class<?> targetType;


        public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
            Assert.notNull(sourceType, "Source type must not be null");
            Assert.notNull(targetType, "Target type must not be null");

            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        public Class<?> getSourceType() {
            return sourceType;
        }

        public Class<?> getTargetType() {
            return targetType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ConvertiblePair that = (ConvertiblePair) o;
            return sourceType.equals(that.sourceType) && targetType.equals(that.targetType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sourceType, targetType);
        }
    }
}
