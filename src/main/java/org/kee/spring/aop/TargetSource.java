package org.kee.spring.aop;

import org.kee.spring.util.ClassUtils;

/**
 * <p>被代理的目标对象
 *
 * @author Eric
 * @date 2023/9/8 22:53
 */
public class TargetSource {

    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取代理对象实现的所有接口的对象
     * @return
     */
    public Class<?>[] getTargetClass() {
        Class<?> clazz = this.target.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        return clazz.getInterfaces();
    }

    /**
     * 获取代理对象
     * @return
     */
    public Object getTarget() {
        return this.target;
    }
}
