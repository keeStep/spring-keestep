package org.kee.spring.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.PropertyValues;
import org.kee.spring.beans.factory.BeanFactory;
import org.kee.spring.beans.factory.ConfigurableListableBeanFactory;
import org.kee.spring.beans.factory.aware.BeanFactoryAware;
import org.kee.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.kee.spring.util.ClassUtils;

import java.io.File;
import java.lang.reflect.Field;

/**
 * <p> 处理 @Value、@Autowired，注解的 BeanPostProcessor
 *
 * @author Eric
 * @date 2023/9/27 0:23
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    /**
     * 在 Bean 对象实例化完成之后，设置属性操作之前执行
     *
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        // 获取类属性
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        Field[] declaredFields = clazz.getDeclaredFields();

        // 1.处理注解 @Value
        for (Field field : declaredFields) {
            Value valueAnno = field.getAnnotation(Value.class);
            if (null != valueAnno) {
                // 获取 @Value中的占位符
                String value = valueAnno.value();
                // 处理占位符替换为属性值
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

        // 2.处理注解 @Autowired
        for (Field field : declaredFields) {
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (null != autowired) {
                // 获取Bean Class
                Class<?> fieldBeanClass = field.getType();

                // 获取Bean Name
                String fieldBeanName = null;
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                if (null != qualifier) {
                    fieldBeanName = qualifier.value();
                }

                // 获取Bean
                Object fieldBean = StrUtil.isBlank(fieldBeanName) ? beanFactory.getBean(fieldBeanClass) : beanFactory.getBean(fieldBeanName, bean);

                // 设置属性值
                BeanUtil.setFieldValue(bean, field.getName(), fieldBean);
            }
        }

        return pvs;
    }

    /**
     * 在 Bean 初始化之前执行
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    /**
     * 在 Bean 初始化之前执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    /**
     * 在 Bean 初始化之后执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
