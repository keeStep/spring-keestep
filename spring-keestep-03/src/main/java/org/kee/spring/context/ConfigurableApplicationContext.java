package org.kee.spring.context;

import org.kee.spring.beans.BeansException;

/**
 * <p> 具有配置能力的Context
 * 主要定义刷新容器功能（refresh()）
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
