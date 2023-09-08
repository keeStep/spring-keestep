package org.kee.spring.aop;

/**
 * <p>切点表达式接口
 *
 * @author Eric
 * @date 2023/9/8 22:25
 */
public interface Pointcut {

    /**
     * 获取类匹配实例
     * @return ClassFilter
     */
    ClassFilter getClassFilter();

    /**
     * 获取方法匹配实例
     * @return MethodMatcher
     */
    MethodMatcher getMethodMatcher();
}
