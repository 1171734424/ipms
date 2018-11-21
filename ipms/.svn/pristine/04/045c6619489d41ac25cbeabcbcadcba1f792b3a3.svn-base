package com.ideassoft.core.page;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;

import com.ideassoft.core.bean.pageConfig.ColumnConfig;
import com.ideassoft.core.bean.pageConfig.ModelConfig;
import com.ideassoft.core.bean.pageConfig.PageConfig;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;


public class ConfigLoader {
	private static Logger logger = Logger.getLogger(ConfigLoader.class);
	
	private final static String pageConfigPrefix = "pageConfig";

	private ConfigLoader() {
		
	}
	
	public final static Map<String, ModelConfig> loadConfig() {
		Map<String, ModelConfig> configs = new HashMap<String, ModelConfig>();
		
		File configFilePath = new File(getXmlFilePath());
		if (configFilePath != null) {
			File[] configFiles = configFilePath.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.startsWith(pageConfigPrefix);
				}
			});
			
			if (configFiles != null && configFiles.length > 0) {
				Map<String, ModelConfig> modelConfigs;
				for (File configFile : configFiles) {
					modelConfigs = loadConfigByModel(configFile);
					configs.putAll(modelConfigs);
				}
			}
		}
		
		return configs;
	}
	
	public final static Map<String, ModelConfig> loadConfigByModel(File coinfigFile) {
		Map<String, ModelConfig> configs = new HashMap<String, ModelConfig>();
		ModelConfig config = null;
		
		SAXReader reader = new SAXReader();
		Document dom = null;
		try {
			dom = reader.read(coinfigFile);
			
			Element root = dom.getRootElement();
			String rootName = "config";
			Iterator<?> itconfigs = root.elementIterator(rootName);
			
			if (itconfigs == null || !itconfigs.hasNext()) {
				throw new ConfigException("config file error, can not find node <" + rootName + "> !");
			}
			
			while (itconfigs.hasNext()) {
				Element roots = (Element) itconfigs.next();
				Element els = roots.element("models");
				
				if (els != null) {
					Iterator<?> models = els.elementIterator("model");
					
					while (models.hasNext()) {
						Element model = (Element) models.next();
						config = new ModelConfig();
						config.setModelId(model.attributeValue("modelId"));
						config.setModelName(model.attributeValue("name"));
						config.setIcon(model.attributeValue("icon"));
						config.setSubSystem(model.attributeValue("subSystem") == null ? "crm" : model.attributeValue("subSystem"));
						config.setShow(model.attributeValue("show") == null || "true".equals(model.attributeValue("show")));
						
						Iterator<?> it = model.elementIterator("page");
						Map<String, PageConfig> pageConfigs = new TreeMap<String, PageConfig>();
						if (it != null) {
							PageConfig pageConfig;
							while (it.hasNext()) {
								pageConfig = new PageConfig();
								Element page = (Element) it.next();
								
								Class<?> classT = pageConfig.getClass();
								Field[] fields = classT.getDeclaredFields();
								
								String varName, varType;
								String queryType = page.element("query") != null ? page.element("query").attributeValue("type") : null;
								
								for (int k = 0; k < fields.length; k++) {
									varName = fields[k].getName();
									varType = fields[k].getType().getSimpleName();
									
									if (fields[k].getAnnotation(Transient.class) != null) {
										continue;
									}
									
									Method method = classT.getMethod("set" + varName.substring(0, 1).toUpperCase() 
											+ varName.substring(1, varName.length()), fields[k].getType());
									
									if("String".equals(varType)) {
										if ("type".equals(varName)) {
											method.invoke(pageConfig, queryType);
										} else if ("sql".equals(varName)) {
											if ("sql".equals(queryType) || "hql".equals(queryType)) {
												method.invoke(pageConfig, page.element("query").elementTextTrim(varName));
											} else if ("procedure".equals(queryType)) {
												method.invoke(pageConfig, page.element("query").elementText("procName"));
											}
										} else {
											if (page.attributeValue(varName) != null) {
												method.invoke(pageConfig, page.attributeValue(varName));
											} else if (page.elementText(varName) != null) {
												method.invoke(pageConfig, page.elementText(varName));
											}
										}
									} else if ("procedure".equals(queryType) && "TreeMap".equals(varType)) {
										Iterator<?> params = page.element("query").element("params").elementIterator("param");
										if(params != null) {
											Map<String, String> map = new TreeMap<String, String>();
											while (params.hasNext()) {
												Element param = (Element) params.next();
												map.put(param.attributeValue("id"), param.getText());
											}
											method.invoke(pageConfig, map);
										}
									} else if ("List".equals(varType)) {
										if (page.element("columns") != null) {
											Iterator<?> columns = page.element("columns").elementIterator("column");
											if (columns != null) {
												/*if (page.element("columns").attributeValue("rebuildResult") != null) {
													pageConfig.setRebuildResult(true);
												}*/
												List<ColumnConfig> columnConfigList = new ArrayList<ColumnConfig>();
												ColumnConfig columnConfig;
												while (columns.hasNext()) {
													columnConfig = new ColumnConfig();
													Element column = (Element) columns.next();
													
													Class<?> cl = columnConfig.getClass();
													Field[] fls = cl.getDeclaredFields();
													
													String vName, vType;
													for (int j = 0; j < fls.length; j++) {
														vName = fls[j].getName();
														vType = fls[j].getType().getSimpleName();
														
														if (fls[j].getAnnotation(Transient.class) != null) {
															continue;
														}
														
														Method mtd = cl.getMethod("set" + vName.substring(0, 1).toUpperCase() 
																+ vName.substring(1, vName.length()), fls[j].getType());
														
														if ("boolean".equals(vType)) {
															if ("downloadable".equals(vName)) {
																mtd.invoke(columnConfig, column.attributeValue(vName) == null
																		|| "true".equals(column.attributeValue(vName)));
															} else {
																mtd.invoke(columnConfig, "true".equals(column.attributeValue(vName)));
															}
														} else {
															mtd.invoke(columnConfig, column.attributeValue(vName));
														}
													}
													columnConfigList.add(columnConfig);
												}
												method.invoke(pageConfig, columnConfigList);
											}
										}
									} else if ("Map".equals(varType)) {
										if (page.elementText(varName) != null && !"".equals(page.elementText(varName))) {
											Map<String, Object> map = new LinkedHashMap<String, Object>();
											if ("dataFormats".equals(varName)) {
												for (Iterator<?> sqls = page.element("dataFormats").elementIterator("sql"); sqls.hasNext();) {
													Element sql = (Element) sqls.next();
													map.put(sql.attributeValue("name"), sql.getTextTrim());
												}
											} else {
												for (String str : page.elementText(varName).split(",")) {
													map.put(str, str);
												}
												if (page.element(varName).attributeCount() > 0) {
													List<?> list = page.element(varName).attributes();
													for (int i = 0; i < list.size(); i++) {
														DefaultAttribute da = (DefaultAttribute) list.get(i);
														map.put(da.getQName().getName(), da.getValue());
													}
												}
											}
											method.invoke(pageConfig, map);
										} else {
											if ("lazy".equals(varName) && page.element("dataFormats") != null) {
												Map<String, Object> map = new LinkedHashMap<String, Object>();
												for (Iterator<?> sqls = page.element("dataFormats").elementIterator("sql"); sqls.hasNext();) {
													Element sql = (Element) sqls.next();
													map.put(sql.attributeValue("name"), sql.attributeValue("lazy") != null && "true".equals(sql.attributeValue("lazy")));
												}
												method.invoke(pageConfig, map);
											}
										}
									} else if ("boolean".equals(varType)) {
										if (page.attributeValue(varName) != null) {
											method.invoke(pageConfig, "true".equals(page.attributeValue(varName)));
										} else if (page.elementText(varName) != null) {
											method.invoke(pageConfig, "true".equals(page.elementText(varName)));
										} else if ("show".equals(varName)) {
											method.invoke(pageConfig, page.attributeValue(varName) == null  
													|| "true".equals(page.attributeValue(varName)));
										}
									}
								}
								
								if (pageConfig.isTab()) {
									PageConfig content = pageConfigs.get(pageConfig.getContainerId());
									Map<String, String> map = content.getTabs();
									
									if (map == null) {
										map = new LinkedHashMap<String, String>();
									}
									
									map.put(pageConfig.getPageId(), pageConfig.getName());
									content.setTabs(map);

									if (pageConfig.getUrl() != null) {
										Map<String, String> urls = content.getTabUrls();
										
										if (urls == null) {
											urls = new HashMap<String, String>();
										}
										
										urls.put(pageConfig.getPageId(), pageConfig.getUrl());
										content.setTabUrls(urls);
									}
									pageConfigs.put(pageConfig.getContainerId(), content);
								}
								pageConfigs.put(pageConfig.getPageId(), pageConfig);
							}
						}
						config.setPageConfigs(pageConfigs);
						configs.put(config.getModelId(), config);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("read " + coinfigFile.getName() + " file occurs error, " + e);
		}
		
		return configs;
	}
	
	public static final String getXmlFilePath() {
		return RequestUtil.getWebPath() + "/configs";
	}

	public static final String getWorkDir() {
		String dir = System.getProperty("app.BaseDir") == null ? System.getProperty("user.dir") : System.getProperty("app.BaseDir");
		if (!dir.endsWith(File.separator)) {
			dir += File.separator;
		}
		return dir;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(JSONUtil.buildReportJSON(loadConfig()));
	}
}
