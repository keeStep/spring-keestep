package org.kee.spring.test.bean;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.BeanFactory;
import org.kee.spring.beans.factory.DisposableBean;
import org.kee.spring.beans.factory.InitializingBean;
import org.kee.spring.beans.factory.aware.BeanClassLoaderAware;
import org.kee.spring.beans.factory.aware.BeanFactoryAware;
import org.kee.spring.beans.factory.aware.BeanNameAware;
import org.kee.spring.context.ApplicationContext;
import org.kee.spring.context.aware.ApplicationContextAware;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/13 23:21
 */
public class CityService implements InitializingBean, DisposableBean, BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, ApplicationContextAware {

    // 利用Aware感知的属性
    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;

    // bean业务属性
    private String cityCode;
    private String cityName;
    private String location;
//    private CityDao cityDao;
    private CityMapper cityMapper;


    public String queryCityInfo() {
        return cityMapper.getCityName(cityCode) + ", " + cityName + ", " + location;
    }
//
//    public CityMapper getCityDao() {
//        return cityDao;
//    }
//
//    public void setCityDao(CityMapper cityDao) {
//        this.cityDao = cityDao;
//    }


    public CityMapper getCityMapper() {
        return cityMapper;
    }

    public void setCityMapper(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("^^^^^^^^ 执行：CityService.destroy()");
    }

    @Override
    public void afterPropertiesSet() throws BeansException {
        System.out.println("^^^^^^^^ 执行：CityService.afterPropertiesSet()");
    }

    // --- 利用Aware感知的属性
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("### ClassLoader: "+ classLoader);
    }

    @Override
    public void setBeanName(String beanName) {
        System.out.println("### Bean name: "+ beanName);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // --- 利用Aware感知的属性
}
