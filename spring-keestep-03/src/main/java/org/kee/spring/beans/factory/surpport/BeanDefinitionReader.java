package org.kee.spring.beans.factory.surpport;

import cn.hutool.core.bean.BeanException;
import org.kee.spring.beans.factory.config.BeanDefinitionRegistry;
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

    void loadBeanDefinitions(Resource resource) throws BeanException;
    void loadBeanDefinitions(Resource... resources) throws BeanException;
    void loadBeanDefinitions(String location) throws BeanException;
}
