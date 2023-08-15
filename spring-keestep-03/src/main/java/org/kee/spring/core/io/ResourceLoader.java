package org.kee.spring.core.io;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/15 22:46
 */
public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);
}
