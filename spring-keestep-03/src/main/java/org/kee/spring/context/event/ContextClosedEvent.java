package org.kee.spring.context.event;

/**
 * <p>Event raised when an <code>ApplicationContext</code> gets closed.
 *
 * @author Eric
 * @date 2023/9/6 22:44
 */
public class ContextClosedEvent extends ApplicationContextEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
