package org.kee.spring.beans.factory.config;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/8 23:54
 */
public class BeanDefinition {
    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
