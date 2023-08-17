package org.kee.spring.test.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/13 23:20
 */
public class TestBeanReference {

    private static Map<String,String> hashMap = new HashMap<>();

    static {
        hashMap.put("XIY", "西安市");
        hashMap.put("SZX", "深圳市");
        hashMap.put("PKG", "北京市");
    }

    public String getCityName(String code) {
        return hashMap.get(code);
    }
}
