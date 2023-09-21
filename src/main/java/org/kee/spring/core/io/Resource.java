package org.kee.spring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/15 22:45
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

}
