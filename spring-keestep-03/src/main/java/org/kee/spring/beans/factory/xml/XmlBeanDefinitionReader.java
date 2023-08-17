package org.kee.spring.beans.factory.xml;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.kee.spring.beans.BeansException;
import org.kee.spring.beans.PropertyValue;
import org.kee.spring.beans.factory.config.BeanDefinition;
import org.kee.spring.beans.factory.config.BeanDefinitionRegistry;
import org.kee.spring.beans.factory.config.BeanReference;
import org.kee.spring.beans.factory.surpport.AbstractBeanDefinitionReader;
import org.kee.spring.core.io.Resource;
import org.kee.spring.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

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
    public void loadBeanDefinitions(String location) throws BeanException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeanException {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinition(inputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new BeanException("IOException parsing XML document from " + resource, e);
        }

    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeanException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }


    /**
     * 加载XML配置文件的Bean信息到BeanDefinition
     * @param inputStream
     */
    protected void doLoadBeanDefinition(InputStream inputStream) throws ClassNotFoundException {
        Document document = XmlUtil.readXML(inputStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            // 1.过滤非Bean信息
            // 1.1.过滤元素（Element）
            if (!(childNodes.item(i) instanceof Element)) {
                continue;
            }
            // 1.2.过滤节点（bean）
            if (!"bean".equals(childNodes.item(i).getNodeName())) {
                continue;
            }

            // 2.解析 Bean 标签
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            Class<?> clazz = Class.forName(className);
            // beanName 优先取ID
            String beanName = StrUtil.isNotBlank(id) ? id : name;
            // ------id 和 name 都为空时，beanName取clazz的name
            if (StrUtil.isBlank(beanName)) {
                beanName =StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 3.Bean 定义
            BeanDefinition beanDefinition = new BeanDefinition(clazz);

            // 4.属性填充
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                // 4.1.过滤元素（Element）
                if (!(childNodes.item(i) instanceof Element)) {
                    continue;
                }

                // 4.2.过滤节点（property）
                if (!"property".equals(childNodes.item(i).getNodeName())) {
                    continue;
                }

                // 4.3.解析 property 标签
                Element property = (Element) childNodes.item(i);
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");

                // 4.4.引用类型的属性
                Object value = StrUtil.isBlank(attrRef) ? new BeanReference(attrRef) : attrValue;

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
}
