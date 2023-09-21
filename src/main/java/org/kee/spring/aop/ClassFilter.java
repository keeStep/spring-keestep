package org.kee.spring.aop;

/**
 * <p>类匹配定义
 *
 * @author Eric
 * @date 2023/9/8 22:26
 */
public interface ClassFilter {

    /**
     * 给定的类或接口能否匹配上切点表达式
     * @param clazz
     * @return
     */
    boolean matches(Class<?> clazz);
}
