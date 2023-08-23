package org.kee.spring.beans.factory;

/**
 * Extension of the {@link BeanFactory}
 * interface to be implemented by bean factories that are capable of
 * autowiring, provided that they want to expose this functionality for
 *
 * @author Eric
 * @date 2023/8/22 22:51
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
}
