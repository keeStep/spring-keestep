package org.kee.spring.util;

/**
 * <p> Simple strategy interface for resolving a String value.
 * Used by {@link org.kee.spring.beans.factory.config.ConfigurableBeanFactory}.
 * @author Eric
 * @date 2023/9/26 23:48
 */
public interface StringValueResolver {

    String resolveStringValue(String strVal);

}
