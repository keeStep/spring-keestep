package org.kee.spring.context;

import org.kee.spring.beans.factory.HierarchicalBeanFactory;
import org.kee.spring.beans.factory.ListableBeanFactory;
import org.kee.spring.context.event.ApplicationEventPublisher;
import org.kee.spring.core.io.ResourceLoader;

/**
 * <p> 应用上下文核心接口
 *
 * 后续继承其他功能接口来扩展context的功能
 *
 * @author Eric
 * @date 2023/8/25 23:43
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
