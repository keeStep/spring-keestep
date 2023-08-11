package org.kee.spring.beans;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/11 23:47
 */
public class PropertyValue {

    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
