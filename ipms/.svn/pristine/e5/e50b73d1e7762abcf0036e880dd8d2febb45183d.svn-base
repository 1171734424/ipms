package com.ideassoft.wechatrefund;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;



public class XMLUtil {
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String, String> doXMLParse(String strxml)
			throws JDOMException, IOException {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		Map<String, String> m = new HashMap<String, String>();
		InputStream in = HttpClientUtil.String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = XMLUtil.getChildrenText(children);
			}
			m.put(k, v);
		}
		// 关闭流
		in.close();
		return m;
	}
	
	public static Map<String, String> parseRefundXml(String refundXml) throws JDOMException, IOException{
        //jdomParseXml(refundXml);
        StringReader read = new StringReader(refundXml);
        // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        // 创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        // 通过输入源构造一个Document
        org.jdom.Document doc;
        doc = (org.jdom.Document) sb.build(source);
        org.jdom.Element root = doc.getRootElement();// 指向根节点
        List<org.jdom.Element> list = root.getChildren();
        Map<String, String> refundOrderMap = new HashMap<String, String>();
        if(list!=null&&list.size()>0){
            for (org.jdom.Element element : list) {
                refundOrderMap.put(element.getName(), element.getText());
            }
            return refundOrderMap;
            }
        return null;
    }
	
	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(XMLUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}

	/**
	 * 获取xml编码字符集
	 * 
	 * @param strxml
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static String getXMLEncoding(String strxml) throws JDOMException,
			IOException {
		InputStream in = HttpClientUtil.String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		in.close();
		return (String) doc.getProperty("encoding");
	}

    /**
     * map转换成xml
     * 
     * @param orginateMap
     * @return
     */
    public static String getXMLForMap(Map<String, String> orginateMap) {
        StringBuffer xmlString = new StringBuffer();
        xmlString.append("<xml>");

        Set<String> keySet = orginateMap.keySet();
        for (String key : keySet) {
            xmlString.append("<" + key + ">");

            xmlString.append(orginateMap.get(key));

            xmlString.append("</" + key + ">");
        }

        xmlString.append("</xml>");
        return xmlString.toString();
    }

}
