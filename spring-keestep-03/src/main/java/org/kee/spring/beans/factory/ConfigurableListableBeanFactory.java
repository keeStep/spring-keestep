package org.kee.spring.beans.factory;

/**
 * Configuration interface to be implemented by most listable bean factories.
 * In addition to {@link ConfigurableBeanFactory}, it provides facilities to
 * analyze and modify bean definitions, and to pre-instantiate singletons.
 *
 * @author Eric
 * @date 2023/8/22 22:54
 */
public interface ConfigurableListableBeanFactory extends ConfigurableBeanFactory,AutowireCapableBeanFactory,ListableBeanFactory {
}
