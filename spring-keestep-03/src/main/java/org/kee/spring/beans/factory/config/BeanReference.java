package org.kee.spring.beans.factory.config;

/**
 * <p> Bean 属性中引用的对象定义
 *
 * @author Eric
 * @date 2023/8/13 23:09
 */
public class BeanReference {

    private String beanName;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
