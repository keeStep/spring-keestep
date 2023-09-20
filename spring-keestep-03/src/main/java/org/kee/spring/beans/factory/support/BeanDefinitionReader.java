package org.kee.spring.beans.factory.support;

import org.kee.spring.beans.BeansException;
import org.kee.spring.core.io.Resource;
import org.kee.spring.core.io.ResourceLoader;

/**
 * <p> 读取Bean对象定义到BeanDefinition
 *
 * @author Eric
 * @date 2023/8/15 23:11
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getBeanRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(String location) throws BeansException;
    void loadBeanDefinitions(String[] locations) throws BeansException;
    void loadBeanDefinitions(Resource resource) throws BeansException;
    void loadBeanDefinitions(Resource... resources) throws BeansException;
}
