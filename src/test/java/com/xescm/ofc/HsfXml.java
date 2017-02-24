package com.xescm.ofc;

import com.taobao.hsf.lightapi.ServiceFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/12/29.
 */
public class HsfXml {
    private static ServiceFactory factory = ServiceFactory.getInstanceWithPath("H:\\ofcTomcat\\taobao-tomcat-7.0.59\\deploy");

    static public void loadConsumer() {
        final String path = "edas\\consumer\\ofc-hsf-consumer.xml";
        List<Map<String, String>> list = getProperties(path);
        for (Map<String, String> map : list) {
            factory.consumer(map.get("id")).service(map.get("interface")).version(map.get("version")).group(map.get("group")).subscribe();
        }
        new ClassPathXmlApplicationContext("edas\\consumer\\ofc-hsf-consumer.xml");
    }

    private static List<Map<String, String>> getProperties(String path) {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            InputStream is = HsfXml.class.getClassLoader().getResourceAsStream(path);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(is);
            Element rootElement = document.getDocumentElement();
            NodeList nodes = rootElement.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Map<String, String> map = new HashMap<>();
                    Element child = (Element) node;
                    map.put("id", child.getAttribute("id"));
                    map.put("interface", child.getAttribute("interface"));
                    map.put("group", child.getAttribute("group"));
                    map.put("version", child.getAttribute("version"));
                    map.put("clientTimeout", child.getAttribute("clientTimeout"));
                    list.add(map);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
