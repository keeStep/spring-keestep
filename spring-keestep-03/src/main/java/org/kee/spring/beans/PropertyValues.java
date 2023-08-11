package org.kee.spring.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/11 23:48
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public PropertyValue[] getPropertyValues() {
        return propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : propertyValueList) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }

        return null;
    }

    public void addPropertyValue(PropertyValue pv) {
        propertyValueList.add(pv);
    }
}
