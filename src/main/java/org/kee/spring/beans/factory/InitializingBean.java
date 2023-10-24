package org.kee.spring.beans.factory;

import org.kee.spring.beans.BeansException;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/30 22:25
 */
public interface InitializingBean {

    /**
     * 在bean对象属性填充完成后调用
     * @throws BeansException
     */
    void afterPropertiesSet() throws Exception;
}
