package com.ideassoft.crm.service;

import java.io.File;
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.ideassoft.bean.TransferData;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.crm.controller.CommonController;

import com.ideassoft.util.HeartBeatClient;
import com.ideassoft.util.RemoteTransUtil;

public class TransferServcie {

	private static Logger log = Logger.getLogger(TransferServcie.class);

	private static TransferServcie transferService;

	private Queue<TransferData> orders;

	private Queue<TransferData> otherData;

	private Queue<TransferData> unreceivedData;

	private Queue<TransferData> fileData;
	
	private Queue<TransferData> unreceivedFileData;
	
	private OrderTransferThread orderService;

	private OtherDataTransferThread otherDataService;

	private UnreceivedDataTransferThread unreceivedDataService;
	
	private FileDataTransferThread fileDataService;
	
	private UnreceivedFileDataTransferThread unreceivedFileDataService;

	private static volatile boolean onService = false;

	private TransferServcie() {
		init();
	}

	public static TransferServcie getInstance() {
		if (transferService == null) {
			transferService = new TransferServcie();
		}
		return transferService;
	}

	public static void startService() {
		onService = true;
	}

	public static void stopService() {
		onService = false;
	}

	void init() {
		startService();
		if (orders == null) {
			// 优先队列,根据会员等级确认优先级别
			orders = new PriorityBlockingQueue<TransferData>(1, new Comparator<TransferData>() {
				public int compare(TransferData src, TransferData tar) {
					return tar.getPriority().compareTo(src.getPriority());
				}
			});
		}
		if (otherData == null) {
			// 优先队列,根据会员等级确认优先级别
			otherData = new PriorityBlockingQueue<TransferData>(1, new Comparator<TransferData>() {
				public int compare(TransferData src, TransferData tar) {
					return tar.getPriority().compareTo(src.getPriority());
				}
			});
		}
		if (unreceivedData == null) {
			// 优先队列,根据会员等级确认优先级别
			unreceivedData = new PriorityBlockingQueue<TransferData>(1, new Comparator<TransferData>() {
				public int compare(TransferData src, TransferData tar) {
					return tar.getPriority().compareTo(src.getPriority());
				}
			});
		}
		if (fileData == null) {
			// 优先队列,根据会员等级确认优先级别
			fileData = new PriorityBlockingQueue<TransferData>(1, new Comparator<TransferData>() {
				public int compare(TransferData src, TransferData tar) {
					return tar.getPriority().compareTo(src.getPriority());
				}
			});
		}
		if (unreceivedFileData == null) {
			// 优先队列,根据会员等级确认优先级别
			unreceivedFileData = new PriorityBlockingQueue<TransferData>(1, new Comparator<TransferData>() {
				public int compare(TransferData src, TransferData tar) {
					return tar.getPriority().compareTo(src.getPriority());
				}
			});
		}
		orderService = new OrderTransferThread();
		orderService.start();
		otherDataService = new OtherDataTransferThread();
		otherDataService.start();
		unreceivedDataService = new UnreceivedDataTransferThread();
		unreceivedDataService.start();
		fileDataService = new FileDataTransferThread();
		fileDataService.start();
		unreceivedFileDataService = new UnreceivedFileDataTransferThread();
		unreceivedFileDataService.start();

	}

	public void transferOrder(int priority, String remoteIp, String data) {
		if (orders.size() < SystemConstants.TransferConfig.MAX_QUEUE_SIZE) {
			TransferData transfer = new TransferData(SystemConstants.TransferDataType.ORDER, remoteIp, data, priority);
			orders.add(transfer);
		} else {

		}
	}

	class OrderTransferThread extends Thread {
		int a;
		int b;
		String IP;
		
		public OrderTransferThread() {
			a = 0;
			b = 0;
			IP = "";
		}
		
		public void run() {
			if (onService) {
				TransferData transfer;
				String result;
				while (!orders.isEmpty()) {
					synchronized (orders) {
						transfer = orders.poll();
						try {
							result = RemoteTransUtil.transData(transfer.getRemoteIp() + "/reciveData.do", 
									transfer.getData(), 
									SystemConstants.RemoteTransDataType.STRING,
									SystemConstants.RemoteTransReturnType.STRING);

							if (!result.equals(SystemConstants.PortalResultCode.DONE)) {
								// 若发送失败,将数据重新插入队列,等待再次发送
								a = transfer.getRemoteIp().indexOf("//") + 2;
								b = transfer.getRemoteIp().indexOf(":", a);
								IP = transfer.getRemoteIp().substring(a, b);
								Long lastReceiveTime = getLastReceiveTime(IP);
								if ((System.currentTimeMillis() - lastReceiveTime) > 10000) {
									unreceivedData.add(transfer);
								} else {
									log.error("transfer order occurs error! branch ip[" + transfer.getRemoteIp() + "], "
											+ "data[" + transfer.getData() + "] " );
								}

							}
						} catch (Exception e) {
							log.error("transfer order occurs error! branch ip[" + transfer.getRemoteIp() + "], "
									+ "data[" + transfer.getData() + "], " + e.getMessage());
							unreceivedData.add(transfer);
						}
					}
				}
			}
		}
	}

	public void transferData(int priority, String remoteIp, String data) {
		if (otherData.size() < SystemConstants.TransferConfig.MAX_QUEUE_SIZE) {
			TransferData transfer = new TransferData(SystemConstants.TransferDataType.DATA, remoteIp, data, priority);
			otherData.add(transfer);
			if (!otherDataService.isAlive()) {
				otherDataService = new OtherDataTransferThread();
				otherDataService.start();
			}
		} else {

		}
	}

	class OtherDataTransferThread extends Thread {
		int a;
		int b;
		String IP;
		public OtherDataTransferThread(){
			a = 0;
			b = 0;
			IP = "";
		}
		
		public void run() {
			if (onService) {
				TransferData transfer;
				String result;
				while (!otherData.isEmpty()) {
					synchronized (otherData) {
						transfer = otherData.poll();
						try {
							result = RemoteTransUtil.transData(transfer.getRemoteIp() + "/reciveData.do", 
									transfer.getData(), 
									SystemConstants.RemoteTransDataType.STRING,
									SystemConstants.RemoteTransReturnType.STRING);
							if (!result.equals(String.valueOf(SystemConstants.PortalResultCode.DONE))) {
								// 若发送失败,将数据重新插入队列,等待再次发送
								a = transfer.getRemoteIp().indexOf("//") + 2;
								b = transfer.getRemoteIp().indexOf(":", a);
								IP = transfer.getRemoteIp().substring(a, b);
								Long lastReceiveTime = getLastReceiveTime(IP);
								if (lastReceiveTime == null || (System.currentTimeMillis() - lastReceiveTime) > 10000) {
									unreceivedData.add(transfer);
								} else {
									log.error("transfer othrtData occurs error! branch ip[" + transfer.getRemoteIp() + "], "
											+ "data[" + transfer.getData() + "]");
								}

							}
						} catch (Exception e) {
							log.error("transfer othrtData occurs error! branch ip[" + transfer.getRemoteIp() + "], "
									+ "data[" + transfer.getData() + "], " + e.getMessage());
							unreceivedData.add(transfer);
						}
					}
				}
			}
		}
	}

	public void transferUnreceivedData(int priority, String remoteIp, String data) {
		if (unreceivedData.size() < SystemConstants.TransferConfig.MAX_QUEUE_SIZE) {
			TransferData transfer = new TransferData(SystemConstants.TransferDataType.DATA, remoteIp, data, priority);
			unreceivedData.add(transfer);
			if (!unreceivedDataService.isAlive()) {
				unreceivedDataService = new UnreceivedDataTransferThread();
				unreceivedDataService.start();
			}
		} else {

		}
	}

	
	class UnreceivedDataTransferThread extends Thread {
		public void run() {
			if (onService) {
				TransferData transfer;
				String result;
				while (true) {
					if (!unreceivedData.isEmpty()) {
						synchronized (unreceivedData) {
							transfer = unreceivedData.poll();
							try {
								result = RemoteTransUtil.transData(transfer.getRemoteIp() + "/reciveData.do", 
										transfer.getData(), 
										SystemConstants.RemoteTransDataType.STRING,
										SystemConstants.RemoteTransReturnType.STRING);
								if (!result.equals(String.valueOf(SystemConstants.PortalResultCode.DONE))) {
									// 若发送失败,将数据重新插入队列,等待再次发送
									try {
										Thread.sleep(3000);
									} catch (InterruptedException e1) {
										log.error("Thread error! [" + e1.getMessage());
									}
									unreceivedData.add(transfer);
								}
							} catch (Exception e) {
								log.error("transfer unreceivedData occurs error! branch ip[" + transfer.getRemoteIp()
										+ "], " + "data[" + transfer.getData() + "], " + e.getMessage());
								unreceivedData.add(transfer);
							}
						}
					}else{
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e2) {
							log.error("Thread error! [" + e2.getMessage());
						}
					}
				}
			}
		}
	}
	
	public void transferFile(int priority, String remoteIp, String data) {
		if (fileData.size() < SystemConstants.TransferConfig.MAX_QUEUE_SIZE) {
			TransferData transfer = new TransferData(SystemConstants.TransferDataType.DATA, remoteIp, data, priority);
			fileData.add(transfer);
			if (!fileDataService.isAlive()) {
				fileDataService = new FileDataTransferThread();
				fileDataService.start();
			}
		} else {

		}
	}
	
	class FileDataTransferThread extends Thread {
		int a;
		int b;
		String IP;
		public FileDataTransferThread(){
			a = 0;
			b = 0;
			IP = "";
		}
		public void run() {
			if (onService) {
				synchronized (fileData) {
					TransferData transfer;
					String result;
					while (!fileData.isEmpty()) {
						transfer = fileData.poll();
						try {
							String fileNameTostring=new JSONObject(transfer.getData()).get("FileName").toString();
							String fileName=fileNameTostring.substring(2, fileNameTostring.length()-2);
							
							 result = RemoteTransUtil.transData(transfer.getRemoteIp() + "/reciveFile.do", 
									 File.separator+"upload"+File.separator+ fileName, SystemConstants.RemoteTransDataType.MIXED,
									SystemConstants.RemoteTransReturnType.STRING);
							
							if (!result.equals(SystemConstants.PortalResultCode.DONE)) {
								// 若发送失败,将数据重新插入队列,等待再次发送
								a = transfer.getRemoteIp().indexOf("//") + 2;
								b = transfer.getRemoteIp().indexOf(":", a);
								IP = transfer.getRemoteIp().substring(a, b);
								Long lastReceiveTime = getLastReceiveTime(IP);
								if ((System.currentTimeMillis() - lastReceiveTime) > 10000) {
									unreceivedFileData.add(transfer);
								} else {
									log.error("transfer file occurs error! branch ip[" + transfer.getRemoteIp() + "], "
											+ "data[" + transfer.getData() + "]");
								}

							}
						} catch (Exception e) {
							log.error("transfer order occurs error! branch ip[" + transfer.getRemoteIp() + "], "
									+ "data[" + transfer.getData() + "], " + e.getMessage());
							unreceivedFileData.add(transfer);
						}
					}
				}
			}
		}
	}
	
	public void transferUnreceivedFile(int priority, String remoteIp, String data) {
		if (unreceivedFileData.size() < SystemConstants.TransferConfig.MAX_QUEUE_SIZE) {
			TransferData transfer = new TransferData(SystemConstants.TransferDataType.DATA, remoteIp, data, priority);
			unreceivedFileData.add(transfer);
			if (!unreceivedFileDataService.isAlive()) {
				unreceivedFileDataService = new UnreceivedFileDataTransferThread();
				unreceivedFileDataService.start();
			}
		} else {

		}
	}
	
	class UnreceivedFileDataTransferThread extends Thread {
		public void run() {
			if (onService) {
				synchronized (unreceivedFileData) {
					TransferData transfer;
					String result;
					while(true){
						if(!unreceivedFileData.isEmpty()) {
							transfer = unreceivedFileData.poll();
							try {
								String fileNameTostring=new JSONObject(transfer.getData()).get("FileName").toString();
								String fileName=fileNameTostring.substring(2, fileNameTostring.length()-2);
								
								 result = RemoteTransUtil.transData(transfer.getRemoteIp() + "/reciveFile.do", 
										 File.separator+"upload"+File.separator+ fileName, SystemConstants.RemoteTransDataType.MIXED,
										SystemConstants.RemoteTransReturnType.STRING);
								if (!result.equals(SystemConstants.PortalResultCode.DONE)) {
									// 若发送失败,将数据重新插入队列,等待再次发送
									try {
										Thread.sleep(3000);
									} catch (InterruptedException e1) {
										log.error("Thread error! [" + e1.getMessage());
									}
									unreceivedFileData.add(transfer);	
								}
							} catch (Exception e) {
								log.error("transfer order occurs error! branch ip[" + transfer.getRemoteIp() + "], "
										+ "data[" + transfer.getData() + "], " + e.getMessage());
								unreceivedFileData.add(transfer);
							}
						}else{
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e2) {
								log.error("Thread error! [" + e2.getMessage());
							}
						}
					}
					
				}
			}
		}
	}

	
	

	public static Long getLastReceiveTime(String remoteIp) {
		return CommonController.heartbeats.get(remoteIp);
	}

	
}
