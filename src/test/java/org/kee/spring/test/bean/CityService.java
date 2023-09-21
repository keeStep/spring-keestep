package org.kee.spring.test.bean;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/13 23:21
 */
public class CityService {

    private String cityCode;
    private String cityName;
    private String location;
    private CityMapper cityMapper;

    public String queryCityInfo() {
        return cityMapper.getCityName(cityCode) + ", " + cityName + ", " + location;
    }

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

}
