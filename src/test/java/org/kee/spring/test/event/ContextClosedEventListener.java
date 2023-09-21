package org.kee.spring.test.event;

import org.kee.spring.context.event.ApplicationListener;
import org.kee.spring.context.event.ContextClosedEvent;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/9/7 23:47
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("监听到关闭事件：" + event.getSource());
        System.out.println("----------- ：" + this.getClass().getName());
    }
}
