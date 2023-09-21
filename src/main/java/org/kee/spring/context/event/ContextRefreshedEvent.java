package org.kee.spring.context.event;

/**
 * <p>Event raised when an <code>ApplicationContext</code> gets initialized or refreshed.
 *
 * @author Eric
 * @date 2023/9/6 22:44
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
