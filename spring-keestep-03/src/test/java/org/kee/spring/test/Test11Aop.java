package org.kee.spring.test;

import org.junit.Test;
import org.kee.spring.aop.aspectj.AspectJExpressionPointcut;
import org.kee.spring.test.bean.CityDao;
import org.kee.spring.test.bean.CityService;

import java.lang.reflect.Method;

/**
 * Unit test for simple App.
 */
public class Test11Aop {


    @Test
    public void TestAop() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(
                "execution(* org.kee.spring.test.bean.CityService.*(..))");

        Class<?> clazz = CityService.class;
        Class<?> wrongClass = CityDao.class;
        Method method = clazz.getDeclaredMethod("queryCityInfo");
        Method wrongMethod = wrongClass.getDeclaredMethod("initDataMethod");

        System.out.println("匹配测试：");
        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));

        System.out.println("不匹配测试：");
        System.out.println(pointcut.matches(wrongClass));
        System.out.println(pointcut.matches(wrongMethod, wrongClass));
    }
}
