package org.kee.spring.beans.factory.surpport;

import cn.hutool.core.bean.BeanUtil;
import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.PropertyValue;
import org.kee.spring.beans.PropertyValues;
import org.kee.spring.beans.factory.config.BeanDefinition;
import org.kee.spring.beans.factory.config.BeanReference;
import org.kee.spring.beans.factory.surpport.instantiation.CglibSubclassingInstantiationStrategy;
import org.kee.spring.beans.factory.surpport.instantiation.InstantiationStrategy;

import java.lang.reflect.Constructor;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/9 0:09
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        // 1.实例化bean
        Object bean = createBeanInstance(beanName, beanDefinition, args);

        // 2.为Bean填充属性
        applyBeanProperty(beanName, bean, beanDefinition);

        // 2.注册单例bean
        addSingleton(beanName, bean);

        // 3.返回bean
        return bean;
    }

    /**
     * 为Bean填充属性
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    private void applyBeanProperty(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                // 引用类型的属性值获取：getBean(beanName)
                if (value instanceof BeanReference) {
                    value = getBean(((BeanReference) value).getBeanName());
                }

                // 属性值填充：
                /*
                    方案1:反射
                    方案2:工具类（此处重点不在反射，可直接使用hutool的工具类）
                 */
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values: " + beanName);
        }

    }

    protected Object createBeanInstance(String beanName, BeanDefinition beanDefinition, Object[] args) {
        // 根据参数匹配获取Constructor
        Constructor constructor = null;
        Class beanClass = beanDefinition.getBeanClass();
        Constructor[] declaredConstructors = beanClass.getDeclaredConstructors();

        for (Constructor ctor : declaredConstructors) {
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructor = ctor;
                break;
            }
        }

        // 根据实例化策略实例化
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructor, args);

    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
