package org.kee.spring.aop.framework;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * <p>Invokes the target object using reflection. Subclasses can override the
 *  #invokeJoinpoint() method to change this behavior, so this is also
 *  a useful base class for more specialized MethodInvocation implementations.
 *
 * @author Eric
 * @date 2023/9/9 23:57
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    /**
     * 目标对象
     */
    protected final Object target;
    /**
     * 方法
     */
    protected final Method method;
    /**
     * 入参
     */
    protected final Object[] arguments;

    public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }


    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public Object proceed() throws Throwable {
        return target;
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
