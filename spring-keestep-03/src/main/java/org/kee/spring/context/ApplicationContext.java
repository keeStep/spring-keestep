package org.kee.spring.context;

import org.kee.spring.beans.factory.ListableBeanFactory;

/**
 * <p> 应用上下文核心接口
 *
 * 后续继承其他功能接口来扩展context的功能
 *
 * @author Eric
 * @date 2023/8/25 23:43
 */
public interface ApplicationContext extends ListableBeanFactory {
}
