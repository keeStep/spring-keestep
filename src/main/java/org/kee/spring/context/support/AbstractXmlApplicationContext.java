package org.kee.spring.context.support;

import org.kee.spring.beans.factory.support.DefaultListableBeanFactory;
import org.kee.spring.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * <p> 抽象实现 AbstractRefreshableApplicationContext 的 loa
 *
 * @author Eric
 * @date 2023/8/27 9:49 下午
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        // 1.实例化 XmlBeanDefinitionReader
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);

        // 2.获取资源配置
        // -- 子类实现
        String[] configLocations = getConfigLocations();

        // 3.遍历加载资源
        if (null != configLocations) {
            xmlBeanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
