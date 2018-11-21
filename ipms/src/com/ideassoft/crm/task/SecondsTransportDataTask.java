package com.ideassoft.crm.task;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.crm.service.CommonService;
import com.ideassoft.util.DateUtil;
import com.ideassoft.util.RemoteTransUtil;
import com.ideassoft.util.RequestUtil;

public class SecondsTransportDataTask extends ScheduledTask {

	private final Logger logger = Logger.getLogger(SecondsTransportDataTask.class);

	private static String remotePath = null;

	private static List<?> beans = null;

	private static String tempPoint = null;

	private static String currPoint = null;

	private static SysParam checkPoint = null;

	private static CommonService commonService = null;

	public SecondsTransportDataTask(String name) {
		super(name);
	}

	public void initScheduledData() {
		commonService = (CommonService) ServiceFactory.getService("commonService");

		SysParam sp = (SysParam) commonService.findOneByProperties(SysParam.class, "paramType",
				CommonConstants.SecondsUpload.REMOTE_PATH, new Object[0]);
		remotePath = sp.getContent() + "/reciveData.do";
		logger.debug("schedule task [TRANSPORT DATA] remotePath..............." + remotePath);
	}

	public void run() {
		logger.debug("schedule task [TRANSPORT DATA] start...............");

		tempPoint = DateUtil.nextDate("yyyy/MM/dd 00:00");
		checkPoint = (SysParam) commonService.findOneByProperties(SysParam.class, "paramType",
				CommonConstants.SecondsUpload.CHECK_POINT, "status", "1");

		try {
			int random = (int) (Math.random() * 30) * 1000;
			Thread.sleep(random);

			SysParam sp;
			List<?> data;
			Map<String, List<?>> dts = new HashMap<String, List<?>>();
			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

			beans = commonService.findByProperties(SysParam.class, "paramType",
					CommonConstants.SecondsUpload.TRANSPORT_OBJECT, "status", "1");

			for (Object obj : beans) {
				sp = (SysParam) obj;
				data = commonService.findTransData(sp.getContent(), sdf.parse(checkPoint.getContent()), sdf.parse(tempPoint));

				if (data != null && !data.isEmpty()) {
					dts.put(sp.getContent(), data);
				}
				
//				if (sp.getContent().equals("Contrart")) {
//					List<?> list = commonService.findAll(Contrart.class);
//					if (list != null && !list.isEmpty()) {
//						Contrart contrart;
//						for (int i = 0; i < list.size(); i++) {
//							contrart = (Contrart) list.get(i);
//							String webPath = RequestUtil.getWebPath().replace("\\", "/");
//							String result = "";
//							if (!"null".equals(contrart.getContrart()) && !"".equals(contrart.getContrart()) && null != contrart.getContrart()) {
//								if((new File(webPath + "/upload/" + contrart.getContrart())).exists()){
//									result = RemoteTransUtil.transData(SystemConstants.Path.RECIVEFILE, "\\upload\\"
//											+ contrart.getContrart(), SystemConstants.RemoteTransDataType.MIXED,
//											SystemConstants.RemoteTransReturnType.STRING);
//								}
//							}                              
//							
//							if (SystemConstants.RemoteTransDataResult.FAILED.equals(result)) {
//								//logger.error("schedule task [TRANSPORT DATAS] transport data failed...............");
//							}
//						}
//					}
//				}

				logger.debug("[TRANSPORT DATA] query data [" + sp.getContent() + "]!" + " data size [" + data.size()
						+ "]!");
			}

			currPoint = DateUtil.currentDate("yyyy/MM/dd HH:mm");

			String result = null;
			if (!dts.isEmpty()) {
				result = RemoteTransUtil.transData(remotePath, dts, SystemConstants.RemoteTransDataType.OBJECT,
						SystemConstants.RemoteTransReturnType.STRING);
			}

			if (SystemConstants.RemoteTransDataResult.FAILED.equals(result)) {
				logger.error("schedule task [TRANSPORT DATA] transport data failed...............");
			} else {
				checkPoint.setContent(currPoint);
				commonService.update(checkPoint);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("schedule task [TRANSPORT DATA] finish...............");
	}

}
