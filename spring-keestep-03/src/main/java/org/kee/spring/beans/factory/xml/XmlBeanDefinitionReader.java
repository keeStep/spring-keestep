package org.kee.spring.beans.factory.xml;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.util.XmlUtil;
import org.kee.spring.beans.factory.config.BeanDefinitionRegistry;
import org.kee.spring.beans.factory.surpport.AbstractBeanDefinitionReader;
import org.kee.spring.core.io.Resource;
import org.kee.spring.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>读取XMl的Bean对象定义到BeanDefinition
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

    @Override
    public void loadBeanDefinitions(String location) throws BeanException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
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
            // 过滤元素（Element）
            if (!(childNodes.item(i) instanceof Element)) {
                continue;
            }

            // 过滤节点（bean）
            if (!"bean".equals(childNodes.item(i).getNodeName())) {
                continue;
            }

            // 解析Bean标签
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            Class<?> clazz = Class.forName(className);



        }

    }
}
