package org.kee.spring.beans.factory.support;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.PropertyValue;
import org.kee.spring.beans.PropertyValues;
import org.kee.spring.beans.factory.ConfigurableListableBeanFactory;
import org.kee.spring.beans.factory.config.BeanDefinition;
import org.kee.spring.beans.factory.config.BeanFactoryPostProcessor;
import org.kee.spring.core.io.DefaultResourceLoader;
import org.kee.spring.util.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * <p>占位符配置处理器
 *
 * @author Eric
 * @date 2023/9/20 22:55
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;

    /**
     * 在所有的 BeanDefinition 加载完成后，且将 Bean 对象实例化之前，提供修改 BeanDefinition 属性的机制
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            // 加载属性文件获取属性映射
            Properties properties = new Properties();
            properties.load(new DefaultResourceLoader().getResource(location).getInputStream());

            // 占位符替换属性值
            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String)) {
                        continue;
                    }

                    value = resolvePlaceholder((String) value, properties);
                    propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));
                }
            }

            // TODO 向容器中添加字符串解析器，解析 @Value 使用

        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String resolvePlaceholder(String value, Properties properties) {
        StringBuilder stringBuilder = new StringBuilder(value);
        int startIdx = value.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int endIdx = value.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);

        if (startIdx != -1 && endIdx != -1 && startIdx < endIdx) {
            String propKey = value.substring(startIdx + 2, endIdx);
            String propVal = properties.getProperty(propKey);

            stringBuilder.replace(startIdx, endIdx + 1, propVal);
        }

        return stringBuilder.toString();
    }


    /**
     * 字符串解析器
     */
    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        private PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties);
        }
    }


}
