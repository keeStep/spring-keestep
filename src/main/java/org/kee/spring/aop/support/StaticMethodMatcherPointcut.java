package org.kee.spring.aop.support;


import org.kee.spring.aop.ClassFilter;
import org.kee.spring.aop.MethodMatcher;
import org.kee.spring.aop.Pointcut;

/**
 * @author zhangdd on 2022/2/27
 */
public abstract class StaticMethodMatcherPointcut extends StaticMethodMatcher implements Pointcut {

    private ClassFilter classFilter = ClassFilter.TRUE;

    public void setClassFilter(ClassFilter classFilter) {
        this.classFilter = classFilter;
    }

    public ClassFilter getClassFilter() {
        return classFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
