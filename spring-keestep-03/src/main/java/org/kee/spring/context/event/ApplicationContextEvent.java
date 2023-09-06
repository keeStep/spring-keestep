package org.kee.spring.context.event;

import org.kee.spring.context.ApplicationContext;

/**
 * <p>Base class for events raised for an <code>ApplicationContext</code>.
 *
 * @author Eric
 * @date 2023/9/6 22:41
 */
public class ApplicationContextEvent extends ApplicationEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
