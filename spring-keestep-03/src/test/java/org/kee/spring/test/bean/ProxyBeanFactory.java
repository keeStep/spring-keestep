package org.kee.spring.test.bean;

import org.kee.spring.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/9/5 21:30
 */
public class ProxyBeanFactory implements FactoryBean<CityMapper> {

    @Override
    public CityMapper getObject() throws Exception {
        InvocationHandler handler = (proxy, method, args) -> {
            // 添加排除方法
            if ("toString".equals(method.getName())) {
                return this.toString();
            }

            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("XIY", "西安市");
            hashMap.put("SZX", "深圳市");
            hashMap.put("PKG", "北京市");

            return "惊喜吧！@你 被代理了 " + method.getName() + ": " + hashMap.get(args[0].toString());
        };

        return (CityMapper) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{CityMapper.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return CityMapper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
