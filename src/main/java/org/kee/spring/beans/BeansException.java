package org.kee.spring.beans;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/8 23:44
 */
public class BeansException extends RuntimeException {

    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
