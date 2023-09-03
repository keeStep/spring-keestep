package org.kee.spring.beans.factory.aware;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.BeanFactory;

/**
 * <p> BeanFactory 感知器
 *
 * @author Eric
 * @date 2023/9/3 18:14
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
