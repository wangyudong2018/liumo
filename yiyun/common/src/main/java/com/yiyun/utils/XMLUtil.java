package com.yiyun.utils;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XMLUtil {
    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws
     * @throws IOException
     */
    public static Map<String, String> doXMLParse(String strxml) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if (StringUtils.isBlank(strxml)) {
            return null;
        }

        Map<String, String> m = new HashMap<>();

        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List<Element> list = root.getChildren();
        for (Element e : list) {
            String k = e.getName();
            String v;
            List<Element> children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = XMLUtil.getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List<Element> children) {
        StringBuilder sb = new StringBuilder();
        if (!children.isEmpty()) {
            for (Element e : children) {
                String name = e.getName();
                String value = e.getTextNormalize();
                List<Element> list = e.getChildren();
                sb.append("<").append(name).append(">");
                if (!list.isEmpty()) {
                    sb.append(XMLUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</").append(name).append(">");
            }
        }
        return sb.toString();
    }

}
