package org.kee.spring.aop;

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

    public Class<?>[] getTargetClass() {
        return this.target.getClass().getInterfaces();
    }

    public Object getTarget() {
        return this.target;
    }
}
