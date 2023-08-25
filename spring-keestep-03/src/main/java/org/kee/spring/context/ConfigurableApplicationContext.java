package org.kee.spring.context;

import org.kee.spring.beans.BeansException;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/25 23:45
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     * @throws BeansException
     */
    void refresh() throws BeansException;
}
