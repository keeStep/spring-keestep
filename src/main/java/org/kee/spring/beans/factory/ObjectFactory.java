package org.kee.spring.beans.factory;

import org.kee.spring.beans.BeansException;

/**
 * <p> Defines a factory which can return an Object instance
 *  (possibly shared or independent) when invoked.
 *
 * @author Eric
 * @date 2023/10/6 23:22
 */
public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
