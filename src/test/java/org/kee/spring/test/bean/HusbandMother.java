package org.kee.spring.test.bean;

import org.kee.spring.beans.factory.FactoryBean;
import org.kee.spring.context.annotation.Component;

import java.lang.reflect.Proxy;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/7 22:56
 */
@Component
public class HusbandMother implements FactoryBean<IMother> {
    @Override
    public IMother getObject() throws Exception {
        return (IMother) Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class[]{IMother.class},
                    (proxy, method, args) -> "婚后媳妇妈妈的职责被婆婆代理了呀" + method.getName()
                );
    }

    @Override
    public Class<?> getObjectType() {
        return IMother.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
