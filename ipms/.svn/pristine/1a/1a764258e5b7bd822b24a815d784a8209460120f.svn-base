package com.ideassoft.core.server;

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

public class Config {
	
	private final static Logger logger = Logger.getLogger("Config");
	
	private static Config instance = null;

	private Document dom = null;

	public SubTaskConfig[] subTaskConfigs = null;

	private Config() {
		init();
	}

	public static Config getNewInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public void init()  {
		SAXReader reader = new SAXReader();

		try {
			InputStream stream = getConfigStream();
			if (stream == null) {
				logger.info("taskConfig配置信息为空");
				return;
			}

			dom = reader.read(stream);

			List<Element> lstNode = dom.selectNodes("//config/subTask");
			subTaskConfigs = new SubTaskConfig[lstNode.size()];
			
			for (int j = 0; j < lstNode.size(); j++) {
				Element node = lstNode.get(j);
				Assert.notNull(node);
				Properties props = new Properties();
				for (Iterator<Element> i = node.elementIterator(); i.hasNext();) {
					Element e = i.next();
					if (e.getText() != null) {
						props.put(e.getName(), e.getText());
					}
				}

				SubTaskConfig subTaskConfig = new SubTaskConfig();

				subTaskConfig.taskId = props.getProperty("taskId");
				subTaskConfig.nameZH = props.getProperty("nameZH");
				subTaskConfig.nameEN = props.getProperty("nameEN");
				subTaskConfig.taskClass = props.getProperty("taskClass");
				subTaskConfig.scheduleExpression = props.getProperty("scheduleExpression");
				subTaskConfig.taskType = props.getProperty("taskType");
				subTaskConfig.taskMode = props.getProperty("taskMode");
				subTaskConfig.isDaemon = "true".equals(props.getProperty("isDaemon"));
				subTaskConfig.autoStart = "true".equals(props.getProperty("autoStart"));
				subTaskConfig.isValid = "true".equals(props.getProperty("isValid"));

				subTaskConfigs[j] = subTaskConfig;
			}
		} catch (DocumentException e) {
			logger.error("读取文件发生错误：" + e.toString());
		}
	}

	private InputStream getConfigStream() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream config = null;
		URL configFile = loader.getResource("taskConfig.xml");

		if (configFile == null) {
			loader = Config.class.getClassLoader();
			if (loader != null) {
				configFile = loader.getResource("taskConfig.xml");
			}
		}
		
		if (configFile == null) {
			return null;
		}
		
		logger.debug("配置文件路径：" + configFile.getPath());
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

	public String getValue(String key) {
		return null;
	}

	public static class SubTaskConfig {
		private String taskId;
		
		private String nameZH;

		private String nameEN;

		private String taskClass;

		private boolean isDaemon;

		private String scheduleExpression;

		private boolean autoStart;

		private String taskType;

		private String taskMode;
		
		private boolean isValid;

		public boolean isDaemon() {
			return isDaemon;
		}

		public void setDaemon(boolean isDaemon) {
			this.isDaemon = isDaemon;
		}

		public String getNameZH() {
			return nameZH;
		}

		public void setNameZH(String nameZH) {
			this.nameZH = nameZH;
		}

		public String getNameEN() {
			return nameEN;
		}

		public void setNameEN(String nameEN) {
			this.nameEN = nameEN;
		}

		public String getTaskClass() {
			return taskClass;
		}

		public void setTaskClass(String taskClass) {
			this.taskClass = taskClass;
		}

		public void setAutoStart(boolean autoStart) {
			this.autoStart = autoStart;
		}

		public boolean getAutoStart() {
			return autoStart;
		}

		public void setTaskType(String taskType) {
			this.taskType = taskType;
		}

		public String getTaskType() {
			return taskType;
		}

		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}

		public String getTaskId() {
			return taskId;
		}

		public void setScheduleExpression(String scheduleExpression) {
			this.scheduleExpression = scheduleExpression;
		}

		public String getScheduleExpression() {
			return scheduleExpression;
		}

		public void setTaskMode(String taskMode) {
			this.taskMode = taskMode;
		}

		public String getTaskMode() {
			return taskMode;
		}

		public void setValid(boolean isValid) {
			this.isValid = isValid;
		}

		public boolean isValid() {
			return isValid;
		}

	}

}
