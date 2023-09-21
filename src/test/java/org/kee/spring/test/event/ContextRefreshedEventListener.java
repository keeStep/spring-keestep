package org.kee.spring.test.event;

import org.kee.spring.context.event.ApplicationListener;
import org.kee.spring.context.event.ContextRefreshedEvent;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/9/7 23:49
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("监听到刷新事件：" + event.getSource());
        System.out.println("----------- ：" + this.getClass().getName());
    }
}
