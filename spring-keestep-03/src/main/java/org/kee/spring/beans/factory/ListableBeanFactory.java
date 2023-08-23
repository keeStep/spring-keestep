package org.kee.spring.beans.factory;

import org.kee.spring.beans.BeansException;

import java.util.Map;

/**
 * Extension of the {@link BeanFactory} interface to be implemented by bean factories
 * that can enumerate all their bean instances, rather than attempting bean lookup
 * by name one by one as requested by clients. BeanFactory implementations that
 * preload all their bean definitions (such as XML-based factories) may implement
 * this interface.
 *
 * @author Eric
 * @date 2023/8/22 22:46
 */
public interface ListableBeanFactory extends BeanFactory {


    /**
     * 按照类型返回bean集合
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;


    /**
     * 返回Bean注册表中所有的bean名称
     * @return
     */
    String[] getBeanDefinitionNames();
}
