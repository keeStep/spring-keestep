package org.kee.spring.beans.factory;

import org.kee.spring.beans.BeansException;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/30 22:27
 */
public interface DisposableBean {

    void destroy() throws BeansException;
}
