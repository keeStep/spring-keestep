package org.kee.spring.test.bean;

import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.factory.DisposableBean;
import org.kee.spring.beans.factory.InitializingBean;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/13 23:21
 */
public class CityService implements InitializingBean, DisposableBean {

    private String cityCode;

    private String cityName;

    private String location;

    private CityDao cityDao;


    public String queryCityInfo() {
        return cityDao.getCityName(cityCode) + ", " + cityName + ", " + location;
    }

    public CityDao getCityDao() {
        return cityDao;
    }

    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
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
        System.out.println("执行：CityService.destroy()");
    }

    @Override
    public void afterPropertiesSet() throws BeansException {
        System.out.println("执行：CityService.afterPropertiesSet()");
    }
}
