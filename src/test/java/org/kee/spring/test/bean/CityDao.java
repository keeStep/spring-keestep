package org.kee.spring.test.bean;

import cn.hutool.core.collection.CollectionUtil;
import org.kee.spring.aop.Pointcut;
import org.kee.spring.context.annotation.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/13 23:20
 */
@Component
public class CityDao {

    private static Map<String,String> hashMap = new HashMap<>();

    static {
        hashMap.put("XIY", "西安市");
        hashMap.put("SZX", "深圳市");
        hashMap.put("PKG", "北京市");
    }

    /*public void initDataMethod() {
        System.out.println("^^^^^^^^ 执行：CityDao.initDataMethod()");
        hashMap.put("XIY", "西安市");
        hashMap.put("SZX", "深圳市");
        hashMap.put("PKG", "北京市");
    }

    public void destroyDataMethod() {
        System.out.println("^^^^^^^^ 执行：CityDao.destroyDataMethod()");
        hashMap.clear();
    }*/

    public String getCityName(String code) {
        return hashMap.get(code);
    }
}
