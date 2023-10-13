package org.kee.spring.test.converter;

import org.kee.spring.beans.factory.FactoryBean;
import org.kee.spring.context.annotation.Component;
import org.kee.spring.core.convert.support.String2NumberConverterFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/13 10:46 下午
 */
@Component("converters")
public class ConvertersFactoryBean implements FactoryBean<Set<?>> {
    @Override
    public Set<?> getObject() throws Exception {
        HashSet<Object> converters = new HashSet<>();
        converters.add(new String2IntegerConverter());
        converters.add(new String2LocalDateConverter("yyyy-MM-dd"));
        converters.add(new String2NumberConverterFactory());
        return converters;
    }

    @Override
    public Class<?> getObjectType() {
        return HashSet.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
