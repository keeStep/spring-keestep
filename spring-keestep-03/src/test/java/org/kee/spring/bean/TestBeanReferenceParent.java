package org.kee.spring.bean;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/13 23:21
 */
public class TestBeanReferenceParent {

    private String cityCode;

    private TestBeanReference testBeanReference;


    public String queryCityName() {
        return testBeanReference.getCityName(cityCode);
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
}
