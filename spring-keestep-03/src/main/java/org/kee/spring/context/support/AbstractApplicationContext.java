package org.kee.spring.context.support;

import org.kee.spring.beans.BeansException;
import org.kee.spring.context.ConfigurableApplicationContext;
import org.kee.spring.core.io.DefaultResourceLoader;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/25 23:46
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public void refresh() throws BeansException {
        // TODO 刷新容器 5 大步骤
    }
}
