package org.kee.spring.context.support;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.ConfigurableListableBeanFactory;
import org.kee.spring.beans.factory.surpport.DefaultListableBeanFactory;

/**
 * <p> 抽象实现 AbstractApplicationContext 的 refreshBeanFactory
 *
 * @author Eric
 * @date 2023/8/27 9:44 下午
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        // 创建 BeanFactory
        DefaultListableBeanFactory beanFactory = createBeanFactory();

        // 加载资源到 BeanDefinition
        // -- 子类 AbstractXmlApplicationContext 实现
        loadBeanDefinitions(beanFactory);

        // 赋能本抽象类属性 BeanFactory（对外部只提供get功能）
        this.beanFactory = beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
