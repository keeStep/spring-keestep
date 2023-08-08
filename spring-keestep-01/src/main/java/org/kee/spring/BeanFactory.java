package org.kee.spring;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/8 23:25
 */
public class BeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 获取 Bean
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        return beanDefinitionMap.get(beanName).getBean();
    }

    /**
     * 注册 Bean
     * @param name
     * @param beanDefinition
     */
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }
}
