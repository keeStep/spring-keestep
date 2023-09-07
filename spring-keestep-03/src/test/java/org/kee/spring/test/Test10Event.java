package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.context.support.ClassPathXmlApplicationContext;
import org.kee.spring.test.bean.CityService;
import org.kee.spring.test.event.CustomEvent;

/**
 * Unit test for simple App.
 */
public class Test10Event {


    @Test
    public void Test() {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        // 发布事件 -》 广播事件 -》 获取监听该事件的所有监听器 -》 执行每个监听器的监听动作onApplicationEvent
        applicationContext.publishEvent(new CustomEvent(applicationContext, 12154136410L, "就这样成功了"));

        applicationContext.registerShutdownHook();
    }
}
