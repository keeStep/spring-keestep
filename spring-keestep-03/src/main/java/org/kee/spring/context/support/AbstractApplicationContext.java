package org.kee.spring.context.support;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.ConfigurableListableBeanFactory;
import org.kee.spring.beans.factory.config.BeanFactoryPostProcessor;
import org.kee.spring.beans.factory.config.BeanPostProcessor;
import org.kee.spring.context.ConfigurableApplicationContext;
import org.kee.spring.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * <p> 抽象实现 {@link org.kee.spring.context.ApplicationContext}
 * 简单实现公共上下文功能
 *
 * 使用模版方法模式，需要具体的子类来实现抽象方法
 * 继承 DefaultResourceLoader 具备资源加载能力
 * @author Eric
 * @date 2023/8/25 23:46
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public void refresh() throws BeansException {
        // 刷新容器 5 大步骤  [暂时是5大 哈哈哈]
        // 1.创建 BeanFactory 并加载 BeanDefinition
        // ---- 子类 AbstractRefreshableApplicationContext 实现
        refreshBeanFactory();

        // 2.获取 BeanFactory
        // ---- 子类 AbstractRefreshableApplicationContext 实现
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3.执行 BeanFactoryPostProcessor (Bean实例化前)
        // ---- 本抽象类实现
        invokeBeanFactoryPostProcessors(beanFactory);

        // 4.注册 BeanPostProcessor (Bean实例化前注册，便于初始化的时候执行)
        // ---- 本抽象类实现
        registerBeanPostProcessors(beanFactory);

        // 5.提前？实例化Bean对象
        // ---- BeanFactory提供, DefaultListableBeanFactory 实现
        beanFactory.preInstantiateSingletons();

    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
}
