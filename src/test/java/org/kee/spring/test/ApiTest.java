package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.context.support.ClassPathXmlApplicationContext;
import org.kee.spring.core.convert.converter.Converter;
import org.kee.spring.core.convert.support.String2NumberConverterFactory;
import org.kee.spring.test.bean.Husband;
import org.kee.spring.test.converter.String2IntegerConverter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Unit test for simple App.
 */
public class ApiTest {

    private final static Map<String, Object> singletonObjects = new ConcurrentHashMap<>(100);

    @Test
    public void testConverter() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        System.out.println("testConverter: " + husband);
    }

    @Test
    public void testString2Integer() {
        String2IntegerConverter converter = new String2IntegerConverter();
        Integer integer = converter.convert("135");
        System.out.println("String2IntegerConverter: " + integer);
    }

    @Test
    public void testStringToNumberConverterFactory() {
        String2NumberConverterFactory converterFactory = new String2NumberConverterFactory();

        Converter<String, Integer> integerConverter = converterFactory.getConverter(Integer.class);
        Converter<String, Long> longConverter = converterFactory.getConverter(Long.class);

        System.out.println("String2NumberConverterFactory#integerConverter: " + integerConverter.convert("3197"));
        System.out.println("String2NumberConverterFactory#longConverter: " + longConverter.convert("1290"));
    }
}

