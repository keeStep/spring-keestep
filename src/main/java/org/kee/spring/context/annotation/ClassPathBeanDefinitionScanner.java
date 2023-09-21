package org.kee.spring.context.annotation;

import cn.hutool.core.util.StrUtil;
import org.kee.spring.beans.factory.config.BeanDefinition;
import org.kee.spring.beans.factory.support.BeanDefinitionRegistry;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * <p>BeanDefinition 扫描器 （注册扫描到的Bean）
 *
 * @author Eric
 * @date 2023/9/20 23:22
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> components = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : components) {
                String beanScope = resolveBeanScope(beanDefinition);

                if (StrUtil.isNotBlank(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }

                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
    }

    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> clazz = beanDefinition.getBeanClass();
        Component component = clazz.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isBlank(value)) {
            value = StrUtil.lowerFirst(clazz.getSimpleName());
        }
        return value;
    }

    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> clazz = beanDefinition.getBeanClass();
        Scope scope = clazz.getAnnotation(Scope.class);

        return null != scope ? scope.value() : StrUtil.EMPTY;
    }
}
