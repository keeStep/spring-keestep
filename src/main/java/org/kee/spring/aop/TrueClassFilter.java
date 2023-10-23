package org.kee.spring.aop;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/23 23:53
 */
public class TrueClassFilter implements ClassFilter {
    
    private static final TrueClassFilter INSTANCE = new TrueClassFilter();

    public TrueClassFilter() {
    }

    
    /**
     * 给定的类或接口能否匹配上切点表达式
     *
     * @param clazz
     * @return
     */
    @Override
    public boolean matches(Class<?> clazz) {
        return true;
    }
    
    private Object readResolve() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "ClassFilter.TRUE";
    }
}
