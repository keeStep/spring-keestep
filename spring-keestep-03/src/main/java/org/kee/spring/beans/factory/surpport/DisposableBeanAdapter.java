package org.kee.spring.beans.factory.surpport;

import cn.hutool.core.util.StrUtil;
import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.DisposableBean;
import org.kee.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * <p> 销毁方法的适配器
 *
 * @author Eric
 * @date 2023/8/31 8:35
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private final String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }


    @Override
    public void destroy() throws Exception {
        // 两种来源的初始化方法都需要执行：
        // -- 1.实现 DisposableBean 的 destroy 方法
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        // -- 2.配置文件的 destroy-method 属性配置的初始化方法
        if (StrUtil.isNotBlank(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
            Method method = bean.getClass().getMethod(destroyMethodName);
            if (null == method) {
                throw new BeansException("Could not find an destroy method named '" + beanName + "' on bean with name '"
                        + beanName + "'");
            }

            method.invoke(bean);
        }
    }
}
