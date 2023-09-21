package org.kee.spring.beans.factory;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/9/4 23:04
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();

    boolean isSingleton();
}
