package org.kee.spring.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.PropertyValue;
import org.kee.spring.beans.factory.config.BeanDefinition;
import org.kee.spring.beans.factory.config.BeanReference;
import org.kee.spring.beans.factory.support.AbstractBeanDefinitionReader;
import org.kee.spring.beans.factory.support.BeanDefinitionRegistry;
import org.kee.spring.context.annotation.ClassPathBeanDefinitionScanner;
import org.kee.spring.core.io.Resource;
import org.kee.spring.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * <p> 读取XMl的Bean对象定义到BeanDefinition
 *
 * @author Eric
 * @date 2023/8/15 23:19
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String[] locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinition(inputStream);
        } catch (IOException | ClassNotFoundException | DocumentException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }

    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }


    /**
     * 加载XML配置文件的Bean信息到BeanDefinition
     * @param inputStream
     */
    protected void doLoadBeanDefinition(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        // 之前使用的解析器是 cn.hutool.core.util.XmlUtil
        // Document document = XmlUtil.readXML(inputStream);
//        Element root = document.getDocumentElement();
//        NodeList childNodes = root.getChildNodes();


        SAXReader reader = new SAXReader();
        org.dom4j.Document document = reader.read(inputStream);
        org.dom4j.Element root = document.getRootElement();

        // 解析Bean扫描配置
        org.dom4j.Element componentScan = root.element("component-scan");
        if (Objects.nonNull(componentScan)) {
            String scanPath = componentScan.attributeValue("base-package");
            if (StrUtil.isBlank(scanPath)) {
                throw new BeansException("The value of base-package attribute can not be null or empty");
            }

            // 扫描注册Bean
            scanPackage(scanPath);
        }

        List<org.dom4j.Element> elements = root.elements("bean");
        for (org.dom4j.Element bean : elements) {

            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            String initMethod = bean.attributeValue("init-method");
            String destroyMethodName = bean.attributeValue("destroy-method");
            String beanScope = bean.attributeValue("scope");

            Class<?> clazz = Class.forName(className);
            // beanName 优先取ID
            String beanName = StrUtil.isNotBlank(id) ? id : name;
            // ------id 和 name 都为空时，beanName取clazz的name
            if (StrUtil.isBlank(beanName)) {
                beanName =StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 3.Bean 定义
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            if (StrUtil.isNotBlank(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            // 4.属性填充
            List<org.dom4j.Element> properties = root.elements("property");
            for (org.dom4j.Element property : properties) {

                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String attrRef = property.attributeValue("ref");

                // 4.4.引用类型的属性
                Object value = StrUtil.isNotBlank(attrRef) ? new BeanReference(attrRef) : attrValue;

                // 4.5.构建属性对象
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }

            // 5.重复注册校验
            if (getBeanRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName [" + beanName + "] is not allowed");
            }

            // 6.Bean 注册
            getBeanRegistry().registerBeanDefinition(beanName, beanDefinition);
        }

    }

    /**
     * 扫描注册Bean
     * @param scanPath
     */
    private void scanPackage(String scanPath) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getBeanRegistry());

        scanner.doScan(StrUtil.splitToArray(scanPath, ','));
    }
}
