package org.kee.spring.test;

import cn.hutool.core.io.IoUtil;
import org.junit.Before;
import org.junit.Test;
import org.kee.spring.beans.factory.support.DefaultListableBeanFactory;
import org.kee.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.kee.spring.core.io.DefaultResourceLoader;
import org.kee.spring.core.io.Resource;
import org.kee.spring.test.bean.TestBeanReferenceParent;

import java.io.IOException;
import java.io.InputStream;

/**
 * Unit test for simple App.
 */
public class Test05AutoWireBean {

    private DefaultResourceLoader resourceLoader;


    @Before
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void Test05ResourceLoaderClasspath() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = resource.getInputStream();
        String s = IoUtil.readUtf8(inputStream);
        System.out.println(s);
    }

    @Test
    public void Test05ResourceLoaderFile() throws IOException {
        Resource resource = resourceLoader.getResource("src/test/resources/important.properties");
        InputStream inputStream = resource.getInputStream();
        String s = IoUtil.readUtf8(inputStream);
        System.out.println(s);
    }

    @Test
    public void Test05ResourceLoaderUrl() throws IOException {
        Resource resource = resourceLoader.getResource("https://gitee.com/xiaoym/knife4j/blob/dev/knife4j-doc/package.json");
        InputStream inputStream = resource.getInputStream();
        String s = IoUtil.readUtf8(inputStream);
        System.out.println(s);
    }


    @Test
    public void Test05AutoRegisterBean() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3. 获取Bean对象调用方法
        TestBeanReferenceParent testBean = (TestBeanReferenceParent) beanFactory.getBean("testBeanReferenceParent");
        System.out.println("获取的城市名称：" + testBean.queryCityInfo());;
    }
}
