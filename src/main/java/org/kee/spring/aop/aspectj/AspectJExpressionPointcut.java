package org.kee.spring.aop.aspectj;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.kee.spring.aop.ClassFilter;
import org.kee.spring.aop.MethodMatcher;
import org.kee.spring.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/9/8 22:33
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVE = new HashSet<>();

    static {
        SUPPORTED_PRIMITIVE.add(PointcutPrimitive.EXECUTION);
    }

    private PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(String expression) {
        // aspect这方法名真TM长
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
                SUPPORTED_PRIMITIVE, this.getClass().getClassLoader());

        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    /**
     * 给定的类或接口能否匹配上切点表达式
     *
     * @param clazz
     * @return
     */
    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    /**
     * 给定的方法能否匹配上切点表达式
     *
     * @param method
     * @param targetClass
     * @return
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    /**
     * 获取类匹配实例
     *
     * @return ClassFilter
     */
    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    /**
     * 获取方法匹配实例
     *
     * @return MethodMatcher
     */
    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
