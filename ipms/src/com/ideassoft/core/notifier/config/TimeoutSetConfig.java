package com.ideassoft.core.notifier.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.Assert;

import com.ideassoft.core.server.Config;

public class TimeoutSetConfig {
	private static Logger logger = Logger.getLogger(TimeoutSetConfig.class);
	
	private static TimeoutSetConfig instance = null;

	public static int timeoutDuration = 60;// 超时时长 单位：分钟.默认时间1小时

	public static final boolean ENABLE = true;// 超时退出开关打开
	
	public static final boolean UNABLE = false;// 超时退出开关关闭
	
	public static boolean ReceiveFind = UNABLE;// 初始状态false

	public String[] filterUrlConfigs = null;

	public TimeoutSetConfig() {
		
	}

	public static TimeoutSetConfig getNewInstance() {
		if (instance == null) {
			instance = new TimeoutSetConfig();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public void init() {
		SAXReader reader = new SAXReader();
		try {
			InputStream stream = getConfigStream();
			if (stream == null) {
				logger.error("配置信息为空");
				return;
			}
			Document dom = reader.read(stream);
			
			List<Element> lstUrlNode = dom.selectNodes("//config/filterUrl");
			if (lstUrlNode != null) {
				filterUrlConfigs = new String[lstUrlNode.size()];
				for (int j = 0; j < lstUrlNode.size(); j++) {
					Element node = (Element) lstUrlNode.get(j);
					Assert.notNull(node);
					Properties props = new Properties();
					for (Iterator<Element> i = node.elementIterator(); i.hasNext();) {
						Element e = (Element) i.next();
						if (e.getText() != null) {
							props.put(e.getName(), e.getText());
						}

					}
					String filterUrl = props.getProperty("url");

					filterUrlConfigs[j] = filterUrl;
				}
			}
			
			List<Element> lstFilterTimeNode = dom.selectNodes("//config/filterTime");
			
			if (lstFilterTimeNode != null && lstFilterTimeNode.size() > 0) {
				Element node = (Element) lstFilterTimeNode.get(0);
				Assert.notNull(node);
				Properties props = new Properties();
				for (Iterator i = node.elementIterator(); i.hasNext();) {
					Element e = (Element) i.next();
					if (e.getText() != null) {
						props.put(e.getName(), e.getText());
					}
				}
				
				timeoutDuration = Integer.valueOf(props.getProperty("timeoutDuration"));
			}
		} catch (DocumentException e) {
			logger.error("读取文件timeoutSettingConfig.xml发生错误：" + e.toString());
		}
	}

	private InputStream getConfigStream() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream config = null;
		URL configFile = loader.getResource("timeoutSettingConfig.xml");

		if (configFile == null) {
			loader = Config.class.getClassLoader();
			if (loader != null) {
				configFile = loader.getResource("timeoutSettingConfig.xml");
			}
		}
		if (configFile == null) {
			return null;
		}
		
		try {
			File f = new File(configFile.toURI());
			config = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			logger.error("读取文件发生错误：" + e.toString());
		} catch (URISyntaxException e) {
			logger.error("读取文件发生错误：" + e.toString());
		} catch (Exception e) {
			logger.error("读取文件发生错误：" + e.toString());
		}
		
		return config;
	}

	public String[] getFilterUrlConfigs() {
		return filterUrlConfigs;
	}

	public void setFilterUrlConfigs(String[] filterUrlConfigs) {
		this.filterUrlConfigs = filterUrlConfigs;
	}
}