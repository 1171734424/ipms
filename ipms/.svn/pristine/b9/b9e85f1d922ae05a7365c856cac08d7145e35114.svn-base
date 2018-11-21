package com.ideassoft.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtil {
	
	private static Logger logger = Logger.getLogger(XmlUtil.class);
	
	public final static Integer NODE = 0x01;
	
	public final static Integer ATTRIBUTE = 0x02;
	
	/**
	 * 读取XML文件
	 * @param xmlFilePath
	 * @return
	 */
	public static Document parseXml(String xmlFilePath) {
		SAXReader reader = new SAXReader();
		Document dom = null;
		File xmlFile = null;
		try {
			xmlFile = new File(xmlFilePath);
			dom = reader.read(xmlFile);
		} catch (Exception e) {
			logger.error("read file [" + xmlFilePath + "] occurs error, " + e);
			e.printStackTrace();
		}
		
		return dom;
	}
	
	/**
	 * 根据XPATH寻找节点
	 * @param dom
	 * @param xpath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> findElementByXPath(Document dom, String xpath) {
		List<Object> result = null;
		if (dom != null) {
			result = dom.selectNodes(xpath);
		}
		
		return result;
	}
	
	/**
	 * 替换XML节点属性
	 * @param dom DOM对象
	 * @param xpath 匹配节点属性规则 
	 * @param value 替换的值
	 * @param type 解析类型  1节点 2属性
	 */
	public static Document replaceXmlElement(Document dom, String xpath, String value, Integer type) {
		List<?> result = findElementByXPath(dom, xpath);
		if (result != null && !result.isEmpty()) {
			Iterator<?> it = result.iterator();
			
			while (it.hasNext()) {
				if (NODE == type) {
					Element element = (Element) it.next();
					element.setText(value);
				} else if (ATTRIBUTE == type) {
					Attribute attribute = (Attribute) it.next();
					attribute.setValue(value);
				} else {
					logger.error("replace xml file element occurs error! undefined type param [ " + type + " ]!");
				}
			}
		}
		
		return dom;
	}
	
	/**
	 * 保存XML文件
	 * @param dom
	 * @param xmlPath
	 * @return
	 */
	public static String writerXML(Document dom, String xmlPath) {
		String msg = "sucess";
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		XMLWriter writer = null;
		try {
			fos = new FileOutputStream(xmlPath);
			osw = new OutputStreamWriter(fos);
			
			writer = new XMLWriter(osw);
			writer.write(dom);
		} catch (IOException e) {
			msg = "write xml file occurs error! file path [ " + xmlPath + " ]!";
			logger.error(msg);
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					
				}
			}
			
			if (osw != null) {
				try {
					osw.flush();
					osw.close();
				} catch (IOException e) {
					
				}
			}
			
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					
				}
			}
		}
		
		return msg;
	}
	
	/**
	 * 保存XML文件
	 * @param xmlStr
	 * @param xmlPath
	 * @return
	 */
	public static String writerXML(String xmlStr, String xmlPath) {
		String msg = "sucess";
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(xmlPath), false);
			writer.write(xmlStr);
		} catch (IOException e) {
			msg = "write xml file occurs error! file path [ " + xmlPath + " ]!";
			logger.error(msg);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					
				}
			}
		}
		
		return msg;
	}
	
	/**
     * bean转换成XML
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        return convertToXml(obj, "UTF-8");
    }
  
    /**
     * bean转换成XML
     * @param obj
     * @param encoding
     * @return
     */
    public static String convertToXml(Object obj, String encoding) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding); 
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
  
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.replace(" standalone=\"yes\"", "");
    }
  
    /**
     * XML转换成bean
     * @param xml
     * @param cls
     * @return
     */
    @SuppressWarnings("unchecked")  
    public static <T> T convertToBean(String xmlString, Class<T> cls) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(cls);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            
            File xmlFile = new File(xmlString);
            if (xmlFile.isFile()) {
                t = (T) unmarshaller.unmarshal(xmlFile);
			} else {
                t = (T) unmarshaller.unmarshal(new StringReader(xmlString));
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return t;
    }
    
}

