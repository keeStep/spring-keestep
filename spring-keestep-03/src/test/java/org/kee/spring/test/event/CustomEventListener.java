package org.kee.spring.test.event;

import org.kee.spring.context.event.ApplicationListener;

import java.util.Date;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/9/7 23:37
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("\n监听到事件 CustomEvent 的消息：时间 " + new Date()
                + "，消息 "+ event.getId() + " => " + event.getMessage());
    }
}
