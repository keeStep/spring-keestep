package org.kee.spring.test.bean;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/13 23:21
 */
public class TestBeanReferenceParent {

    private String cityCode;

    private String cityName;

    private String location;

    private TestBeanReference testBeanReference;


    public String queryCityInfo() {
        return testBeanReference.getCityName(cityCode) + ", " + cityName + ", " + location;
    }

    public TestBeanReference getTestBeanReference() {
        return testBeanReference;
    }

    public void setTestBeanReference(TestBeanReference testBeanReference) {
        this.testBeanReference = testBeanReference;
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
