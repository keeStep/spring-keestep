package org.kee.spring.context.aware;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.aware.Aware;
import org.kee.spring.context.ApplicationContext;

/**
 * <p> ApplicationContext 感知器
 *
 * @author Eric
 * @date 2023/9/3 18:16
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
