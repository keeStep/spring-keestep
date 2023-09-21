package org.kee.spring.context.annotation;

import cn.hutool.core.util.ClassUtil;
import org.kee.spring.beans.factory.config.BeanDefinition;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p> 扫描识别 @Component 的Bean类到 BeanDefinition
 *
 * @author Eric
 * @date 2023/9/20 23:18
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);

        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }

        return candidates;
    }
}
