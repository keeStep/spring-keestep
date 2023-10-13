package org.kee.spring.test.converter;

import org.kee.spring.core.convert.converter.Converter;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/13 10:45 下午
 */
public class String2IntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        return Integer.valueOf(source);
    }
}
