package org.kee.spring.core.io;

/**
 * <p> 资源加载器接口
 *
 * @author Eric
 * @date 2023/8/15 22:46
 */
public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);
}
