package org.kee.spring.context.event;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.BeanFactory;
import org.kee.spring.beans.factory.aware.BeanFactoryAware;
import org.kee.spring.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Abstract implementation of the {@link ApplicationEventMulticaster} interface,
 * providing the basic listener registration facility.
 *
 * @author Eric
 * @date 2023/9/6 22:52
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    /**
     * 添加监听器
     *
     * @param listener
     */
    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    /**
     * 移除监听器
     *
     * @param listener
     */
    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 获取对指定事件的所有(感兴趣的)监听器
     * @param event
     * @return
     */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
        LinkedList<ApplicationListener> listeners = new LinkedList<>();

        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportsEvent(listener, event)) {
                listeners.add(listener);
            }
        }

        return listeners;
    }

    /**
     * 监听器是否对该事件感兴趣
     * @param applicationListener
     * @param event
     * @return
     */
    private boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

        // 获取目标class
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;

        // 获取类的接口实现信息（包含泛型信息）--暂时取第一个实现的接口
        Type genericInterface = targetClass.getGenericInterfaces()[0];

        // 获取方法的参数：事件类 --暂时取第一个参数
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String typeName = actualTypeArgument.getTypeName();

        // 获取参数名称和参数的类名称（事件类）
        Class<?> eventClass = null;
        try {
            eventClass = Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + typeName);
        }

        // 判断参数中的事件类是否是传参事件的子类或相同
        return eventClass.isAssignableFrom(event.getClass());
    }
}
