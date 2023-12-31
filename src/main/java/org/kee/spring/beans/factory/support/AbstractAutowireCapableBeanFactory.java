package org.kee.spring.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.PropertyValue;
import org.kee.spring.beans.PropertyValues;
import org.kee.spring.beans.factory.DisposableBean;
import org.kee.spring.beans.factory.InitializingBean;
import org.kee.spring.beans.factory.aware.Aware;
import org.kee.spring.beans.factory.aware.BeanClassLoaderAware;
import org.kee.spring.beans.factory.aware.BeanFactoryAware;
import org.kee.spring.beans.factory.aware.BeanNameAware;
import org.kee.spring.beans.factory.config.*;
import org.kee.spring.beans.factory.support.instantiation.CglibSubclassingInstantiationStrategy;
import org.kee.spring.beans.factory.support.instantiation.InstantiationStrategy;
import org.kee.spring.beans.factory.support.instantiation.SimpleInstantiationStrategy;
import org.kee.spring.core.convert.ConversionService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/9 0:09
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            // 0.代理对象（返回代理对象就不用Bean原始对象了）--> 迁移到初始化之后
            /*bean = getBeanProxyIfNecessary(beanName, beanDefinition);
            if (Objects.nonNull(bean)) {
                return bean;
            }*/

            // 1.实例化bean
            bean = createBeanInstance(beanName, beanDefinition, args);

            // * 处理循环依赖
            if (beanDefinition.isSingleton()) {
                Object finalBean = bean;
                addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, beanDefinition, finalBean));
            }

            // 2.在设置Bean属性之前，允许 BeanPostProcessor 修改属性值
            applyBeanPostProcessorsBeforeApplyBeanProperty(beanName, bean, beanDefinition);

            // 2.为Bean填充属性
            applyBeanProperty(beanName, bean, beanDefinition);

            // 3.执行 Bean 的初始化方法及(其前后的 BeanPostProcessor, aware容器感知)
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 4.注册实现了 DisposableBean 接口的 Bean对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 5.注册单例bean
        Object exposedBean = bean;
        if (beanDefinition.isSingleton()) {
            exposedBean = getSingleton(beanName);
            registerSingleton(beanName, bean);
        }

        // 6.返回bean
        return exposedBean;
    }


    protected Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object bean) {
        Object exposedObject = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                exposedObject = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).getEarlyBeanReference(exposedObject, beanName);
                if (null == exposedObject) return exposedObject;
            }
        }

        return exposedObject;
    }


    protected void applyBeanPostProcessorsBeforeApplyBeanProperty(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                PropertyValues propertyValues = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (null != propertyValues) {
                    for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }


    protected Object getBeanProxyIfNecessary(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInitialization(beanDefinition.getBeanClass(), beanName);
        if (Objects.nonNull(bean)) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    protected Object applyBeanPostProcessorsBeforeInitialization(Class<?> beanClass, String beanName) throws BeansException {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                // 代理创建器 DefaultAdvisorAutoProxyCreator 实现了 InstantiationAwareBeanPostProcessor -> 生成代理对象
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInitialization(beanClass, beanName);
                if (null != result) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 注册实现了 DisposableBean 接口的 Bean对象
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 非 Singleton 类型的 Bean 对象不必执行销毁  为什么？-- 因为原型对象不会存储不用销毁
        if (!beanDefinition.isSingleton()) {
            return;
        }

        if (bean instanceof DisposableBean || StrUtil.isNotBlank(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    /**
     * 执行 Bean 的初始化方法及其前后的 BeanPostProcessor
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @return
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 1.invokeAwareMethods
        invokeAwareMethods(beanName, bean);

        // 2.BeanPostProcessor Before
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 3.invokeInitMethods
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        // 4.BeanPostProcessor After
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    private void invokeAwareMethods(String beanName, Object bean) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
        }
    }

    /**
     * invokeInitMethods
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @throws BeansException
     */
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // 两种来源的初始化方法都需要执行：
        // -- 1.实现 InitializingBean 的 afterPropertiesSet 方法
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // -- 2.配置文件的 init-method 属性配置的初始化方法
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotBlank(initMethodName)) {
            Method method = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == method) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '"
                + beanName + "'");
            }

            method.invoke(bean);
        }

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
                } else {
                    // 17-设置属性值时进行类型转换
                    Class<?> sourceType = value.getClass();
                    Class<?> targetType = (Class<?>) TypeUtil.getFieldType(bean.getClass(), beanName);
                    ConversionService conversionService = this.getConversionService();
                    if (Objects.nonNull(conversionService) && conversionService.canConvert(sourceType, targetType)) {
                        conversionService.convert(sourceType, targetType);
                    }
                }

                // 属性值填充：
                /*
                    方案1:反射
                    方案2:工具类（也是反射实现的哈哈哈）（此处重点不在反射，可直接使用hutool的工具类）
                 */
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values: " + beanName + " message：" + e);
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

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (null == current) {
                return result;
            }

            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (null == current) {
                return result;
            }

            result = current;
        }
        return result;
    }
}
