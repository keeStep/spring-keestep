package org.kee.spring;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/8 23:23
 */
public class BeanDefinition {

    private Object bean;

    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }
}
