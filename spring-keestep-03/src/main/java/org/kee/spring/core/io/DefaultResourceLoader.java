package org.kee.spring.core.io;

import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/15 23:06
 */
public class DefaultResourceLoader implements ResourceLoader{
    @Override
    public Resource getResource(String location) {
        Assert.notNull("Location must not be null");
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                return new FileSystemResource(location);
            }
        }
    }
}
