package com.ideassoft.wxPay;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLUtil {
	/*public static Map<String, Object> XMLToMap(String requestXml) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 将字符串转为XML
		Document doc;
		try {
			doc = DocumentHelper.parseText(requestXml);
			// 获取根节点
			Element rootElm = doc.getRootElement();// 从root根节点获取请求报文
			XMLUtil xmlIntoMap = new XMLUtil();
			map = xmlIntoMap.parseXML(rootElm, new HashMap<String, Object>());
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return map;
	}

	*//**
	 * 将xml解析成map键值对 <功能详细描述>
	 * 
	 * @param ele
	 *            需要解析的xml对象
	 * @param map
	 *            入参为空，用于内部迭代循环使用
	 * @return
	 * @see [类、类#方法、类#成员]
	 *//*
	public Map<String, Object> parseXML(Element ele, Map<String, Object> map) {

		for (Iterator<?> i = ele.elementIterator(); i.hasNext();) {
			Element node = (Element) i.next();
			// System.out.println("parseXML node name:" + node.getName());
			if (node.attributes() != null && node.attributes().size() > 0) {
				for (Iterator<?> j = node.attributeIterator(); j.hasNext();) {
					Attribute item = (Attribute) j.next();

					map.put(item.getName(), item.getValue());
				}
			}
			if (node.getText().length() > 0) {
				map.put(node.getName(), node.getText());
			}
			if (node.elementIterator().hasNext()) {
				parseXML(node, map);
			}
		}
		return map;
	}*/
	 /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }
    
    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }

    }

}
