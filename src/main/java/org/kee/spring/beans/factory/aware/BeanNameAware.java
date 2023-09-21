package org.kee.spring.beans.factory.aware;

/**
 * <p> BeanName 感知器
 *
 * @author Eric
 * @date 2023/9/3 18:12
 */
public interface BeanNameAware extends Aware {
    void setBeanName(String beanName);
}
