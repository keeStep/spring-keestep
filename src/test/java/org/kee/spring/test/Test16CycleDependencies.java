package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.context.support.ClassPathXmlApplicationContext;
import org.kee.spring.test.bean11.IUserService;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Unit test for simple App.
 */
public class Test16CycleDependencies {

    private final static Map<String, Object> singletonObjects = new ConcurrentHashMap<>(100);


    /**
     * 使用一级缓存对循环依赖的简单解决方案测试
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println(getBean(A.class).getB());
        System.out.println(getBean(B.class).getA());
    }

    /**
     * 使用一级缓存对循环依赖的简单解决方案测试
     * @param beanClass
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getBean(Class<T> beanClass) throws Exception {

        String beanName = beanClass.getSimpleName().toLowerCase();

        if (singletonObjects.containsKey(beanName)) {
            return (T) singletonObjects.get(beanName);
        }

        // 实例化bean
        T t = beanClass.newInstance();
        // 未设置属性前将Bean放到缓存--》便于被相互依赖的另一个对象在设置属性的时候获取到，否则两者在设置属性时无限获取下去死锁了
        singletonObjects.put(beanName, t);

        // 设置属性（A获取B对象作为属性，B为空会去getBean，然后B获取A对象在这里设置属性）
        Field[] declaredFields = t.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);

            Class<?> fieldClass = field.getType();
            String fieldBeanName = fieldClass.getSimpleName().toLowerCase();
            field.set(t, singletonObjects.containsKey(fieldBeanName) ? singletonObjects.get(fieldBeanName) : getBean(fieldClass));
        }

        return t;
    }

    @Test
    public void TestCycle() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        IUserService userService = applicationContext.getBean("userService", IUserService.class);

        System.out.println("测试结果：" + userService.queryUserInfo());
    }
}


class A {
    private B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}

class B {
    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }
}
