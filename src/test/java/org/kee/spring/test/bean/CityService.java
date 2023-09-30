package org.kee.spring.test.bean;

import org.kee.spring.beans.factory.annotation.Autowired;
import org.kee.spring.beans.factory.annotation.Value;
import org.kee.spring.context.annotation.Component;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/13 23:21
 */
@Component
public class CityService {

    @Value("${cityCode}")
    private String cityCode;

    @Autowired
    private CityDao cityDao;

    public String queryCityInfo() {
        return cityDao.getCityName(cityCode);
    }


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }


}
