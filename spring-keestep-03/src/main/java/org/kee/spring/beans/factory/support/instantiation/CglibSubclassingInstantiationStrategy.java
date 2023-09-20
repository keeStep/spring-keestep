package org.kee.spring.beans.factory.support.instantiation;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.util.Objects;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/9 23:37
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });

        if (Objects.isNull(ctor)) {
            return enhancer.create();
        }

        return enhancer.create(ctor.getParameterTypes(), args);
    }
}
