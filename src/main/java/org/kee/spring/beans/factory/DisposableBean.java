package org.kee.spring.beans.factory;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/30 22:27
 */
public interface DisposableBean {

    void destroy() throws Exception ;
}
