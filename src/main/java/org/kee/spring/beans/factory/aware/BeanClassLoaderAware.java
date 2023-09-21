package org.kee.spring.beans.factory.aware;

/**
 * <p> BeanClassLoader 感知器
 *
 * @author Eric
 * @date 2023/9/3 18:13
 */
public interface BeanClassLoaderAware extends Aware {

    void setBeanClassLoader(ClassLoader classLoader);
}
