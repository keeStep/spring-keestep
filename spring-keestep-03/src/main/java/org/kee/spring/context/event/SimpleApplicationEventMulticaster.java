package org.kee.spring.context.event;

import org.kee.spring.beans.factory.BeanFactory;

/**
 * <p>Simple implementation of the {@link ApplicationEventMulticaster} interface.
 *
 * @author Eric
 * @date 2023/9/6 23:42
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    /**
     * 广播事件
     *
     * @param event
     */
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (final ApplicationListener applicationListener : getApplicationListeners(event)) {
            applicationListener.onApplicationEvent(event);
        }
    }
}
