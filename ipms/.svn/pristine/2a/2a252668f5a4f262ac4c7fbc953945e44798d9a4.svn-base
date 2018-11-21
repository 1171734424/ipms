package com.ideassoft.pms.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;

import com.ideassoft.bean.Branch;
import com.ideassoft.bean.Campaign;
import com.ideassoft.bean.Check;
import com.ideassoft.bean.ExceptionMessage;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.Order;
import com.ideassoft.bean.RoomStatus;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants.ProjectName;
import com.ideassoft.core.factory.ServiceFactory;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.core.task.ScheduledTask;
import com.ideassoft.pms.service.DailyService;
import com.ideassoft.pms.service.RoomService;

public class TimingTask extends ScheduledTask implements ProjectName {

	private final Logger logger = Logger.getLogger(TimingTask.class);

	private static DailyService dailyService = null;
	
	private static RoomService roomService = null;

	public TimingTask(String name) {
		super(name);
	}
	
	public void initScheduledData() {
		dailyService = (DailyService) ServiceFactory.getService("dailyService");
		
		roomService = (RoomService) ServiceFactory.getService("roomService");
		
		logger.debug("schedule task [test1111] init...............");
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			List<Branch> branchs = dailyService.findByProperties(Branch.class, "branchType", "1", "status", "1");
			for(Branch branch : branchs){
				if(StringUtil.isEmpty(branch.getBranchIp())){
					String branchId = branch.getBranchId();
					
					Date date = new Date();
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					SimpleDateFormat sdff = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					
					Pagination pagination = new Pagination();
					pagination.setPageNum(1);
					pagination.setRows(100);
					List<?> roomStatus = roomService.getRoomStatus(sdf.format(date), sdff.format(date), branchId, pagination);
					for(int i = 0; i < roomStatus.size(); i++){
						RoomStatus rs = (RoomStatus) roomService.findOneByProperties(RoomStatus.class, "roomType", ((Map<?, ?>) roomStatus.get(i)).get("ROOMTYPE").toString(), "branchId", branchId);
						rs.setCount(Integer.valueOf(((Map<?, ?>) roomStatus.get(i)).get("ROOMID").toString()));
						rs.setSellnum(Integer.valueOf(((Map<?, ?>) roomStatus.get(i)).get("SELL").toString()));
						rs.setStopnum(Integer.valueOf(((Map<?, ?>) roomStatus.get(i)).get("STOP").toString()));
						rs.setNightnum(Integer.valueOf(((Map<?, ?>) roomStatus.get(i)).get("NIGHT").toString()));
						rs.setValidnum(Integer.valueOf(((Map<?, ?>) roomStatus.get(i)).get("VAILD").toString()));
						rs.setInvalidnum(Integer.valueOf(((Map<?, ?>) roomStatus.get(i)).get("INVAILD").toString()));
						roomService.update(rs);
					}
					
					// 刷新房间数量
					List<Object> list = dailyService.findBySQL("sumroom", new String[] { branchId }, true);
					List<Object> start_campaigns_room = dailyService.findBySQL("startCampaignsRoom", new String[] { branchId }, true);// 特惠房开始
					List<Object> end_campaigns_room = dailyService.findBySQL("endCampaignsRoom", new String[] { branchId }, true);// 特惠房结束
//					List<Object> haltplanRoom = dailyService.findBySQL("haltplanRoom", new String[] { branchId }, true);// 停售房
//					List<Object> validOrder = dailyService.findBySQL("validOrder", new String[] { branchId }, true);// 有效订单
//					List<Object> invalidOrder = dailyService.findBySQL("invalidOrder", new String[] { branchId }, true);// 全部订单
					Map<String, String> start_campaigns_rooms = new HashMap<String, String>();
					Map<String, String> end_campaigns_rooms = new HashMap<String, String>();
//					Map<String, String> haltplanRooms = new HashMap<String, String>();
//					Map<String, String> validOrders = new HashMap<String, String>();
//					Map<String, String> invalidOrders = new HashMap<String, String>();
					// 特惠房活动开始
					if (start_campaigns_room.size() > 0) {
						for (Object campaigns : start_campaigns_room) {
							start_campaigns_rooms.put(((Map<?, ?>) campaigns).get("ROOM_TYPE").toString(),
									((Map<?, ?>) campaigns).get("EXPERIENCE_COUNT").toString());
						}
					}
					// 特惠房活动结束
					if (end_campaigns_room.size() > 0) {
						for (Object campaigns : end_campaigns_room) {
							end_campaigns_rooms.put(((Map<?, ?>) campaigns).get("ROOM_TYPE").toString(),
									((Map<?, ?>) campaigns).get("EXPERIENCE_COUNT").toString());
						}
					}
					// 停售房
//					if (haltplanRoom.size() > 0) {
//						for (Object campaigns : haltplanRoom) {
//							haltplanRooms.put(((Map<?, ?>) campaigns).get("ROOM_TYPE").toString(), ((Map<?, ?>) campaigns).get(
//									"SUM").toString());
//						}
//					}
//					// 有效订单
//					if (validOrder.size() > 0) {
//						for (Object campaigns : validOrder) {
//							validOrders.put(((Map<?, ?>) campaigns).get("ROOM_TYPE").toString(), ((Map<?, ?>) campaigns).get(
//									"SUM").toString());
//						}
//					}
//					// 无效订单
//					if (invalidOrder.size() > 0) {
//						for (Object campaigns : invalidOrder) {
//							invalidOrders.put(((Map<?, ?>) campaigns).get("ROOM_TYPE").toString(), ((Map<?, ?>) campaigns).get(
//									"SUM").toString());
//						}
//					}
					if (list.size() > 0) {
						Map<String, String> types = new HashMap<String, String>();
						for (Object type : list) {
							types.put(((Map<?, ?>) type).get("ROOM_TYPE").toString(), ((Map<?, ?>) type).get("SUM").toString());
						}
						if (!start_campaigns_rooms.isEmpty()) {
							for (int i = 0; i < list.size(); i++) {
								int num = dailyService.findByProperties(Check.class, "roomType",
										((Map<?, ?>) list.get(i)).get("ROOM_TYPE"), "status", "1", "branchId" , branchId).size();
								RoomStatus roomstatus = (RoomStatus) dailyService.findOneByProperties(RoomStatus.class,
										"roomType", ((Map<?, ?>) list.get(i)).get("ROOM_TYPE"), "branchId" , branchId);
								roomstatus.setCount(Integer.parseInt(types.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")))
										- num);
								roomstatus.setSellnum(Integer.parseInt(types.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")))
										- num);
								if (start_campaigns_rooms.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")) != null) {
									roomstatus.setCampaigns(Integer.parseInt(start_campaigns_rooms
											.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE"))));
								}
//								if (validOrders.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")) != null) {
//									roomstatus.setValidnum(Integer.parseInt(validOrders.get(((Map<?, ?>) list.get(i))
//											.get("ROOM_TYPE"))));
//									roomstatus.setSellnum(roomstatus.getSellnum()
//											- Integer.parseInt(validOrders.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE"))));
//								}
//								if (invalidOrders.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")) != null) {
//									roomstatus.setValidnum(Integer.parseInt(invalidOrders.get(((Map<?, ?>) list.get(i))
//											.get("ROOM_TYPE"))));
//								}
//								if (haltplanRooms.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")) != null) {
//									roomstatus.setValidnum(Integer.parseInt(haltplanRooms.get(((Map<?, ?>) list.get(i))
//											.get("ROOM_TYPE"))));
//									roomstatus.setSellnum(roomstatus.getSellnum()
//											- Integer.parseInt(haltplanRooms.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE"))));
//								}
								if (!end_campaigns_rooms.isEmpty()) {
									if (end_campaigns_rooms.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")) != null) {
										roomstatus.setCampaigns(0);
									}
								}
								dailyService.update(roomstatus);
							}
						} else if (!end_campaigns_rooms.isEmpty()) {
							for (int i = 0; i < list.size(); i++) {
								int num = dailyService.findByProperties(Check.class, "roomType",
										((Map<?, ?>) list.get(i)).get("ROOM_TYPE"), "status", "1", "branchId" , branchId).size();
								RoomStatus roomstatus = (RoomStatus) dailyService.findOneByProperties(RoomStatus.class,
										"roomType", ((Map<?, ?>) list.get(i)).get("ROOM_TYPE"), "branchId" , branchId);
								roomstatus.setCount(Integer.parseInt(types.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")))
										- num);
								roomstatus.setSellnum(Integer.parseInt(types.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")))
										- num);
								if (!end_campaigns_room.isEmpty()) {
									if (end_campaigns_rooms.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")) != null) {
										roomstatus.setCampaigns(0);
									}
								}
//								if (validOrders.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")) != null) {
//									roomstatus.setValidnum(Integer.parseInt(validOrders.get(((Map<?, ?>) list.get(i))
//											.get("ROOM_TYPE"))));
//									roomstatus.setSellnum(roomstatus.getSellnum()
//											- Integer.parseInt(validOrders.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE"))));
//								}
//								if (invalidOrders.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")) != null) {
//									roomstatus.setValidnum(Integer.parseInt(invalidOrders.get(((Map<?, ?>) list.get(i))
//											.get("ROOM_TYPE"))));
//								}
//								if (haltplanRooms.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")) != null) {
//									roomstatus.setValidnum(Integer.parseInt(haltplanRooms.get(((Map<?, ?>) list.get(i))
//											.get("ROOM_TYPE"))));
//									roomstatus.setSellnum(roomstatus.getSellnum()
//											- Integer.parseInt(haltplanRooms.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE"))));
//								}
								dailyService.update(roomstatus);
							}
						} else {
							for (int i = 0; i < list.size(); i++) {
								int num = dailyService.findByProperties(Check.class, "roomType",
										((Map<?, ?>) list.get(i)).get("ROOM_TYPE"), "status", "1", "branchId" , branchId).size();
								RoomStatus roomstatus = (RoomStatus) dailyService.findOneByProperties(RoomStatus.class,
										"roomType", ((Map<?, ?>) list.get(i)).get("ROOM_TYPE"), "branchId" , branchId);
								roomstatus.setCount(Integer.parseInt(types.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")))
										- num);
								roomstatus.setSellnum(Integer.parseInt(types.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")))
										- num);
//								if (validOrders.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")) != null) {
//									roomstatus.setValidnum(Integer.parseInt(validOrders.get(((Map<?, ?>) list.get(i))
//											.get("ROOM_TYPE"))));
//									roomstatus.setSellnum(roomstatus.getSellnum()
//											- Integer.parseInt(validOrders.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE"))));
//								}
//								if (invalidOrders.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")) != null) {
//									roomstatus.setValidnum(Integer.parseInt(invalidOrders.get(((Map<?, ?>) list.get(i))
//											.get("ROOM_TYPE"))));
//								}
//								if (haltplanRooms.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE")) != null) {
//									roomstatus.setValidnum(Integer.parseInt(haltplanRooms.get(((Map<?, ?>) list.get(i))
//											.get("ROOM_TYPE"))));
//									roomstatus.setSellnum(roomstatus.getSellnum()
//											- Integer.parseInt(haltplanRooms.get(((Map<?, ?>) list.get(i)).get("ROOM_TYPE"))));
//								}
								dailyService.update(roomstatus);
							}
						}
					}
					if (end_campaigns_room.size() > 0) {
						for (Object campaigns : end_campaigns_room) {
							Campaign campaign = (Campaign) dailyService.findById(Campaign.class, ((Map<?, ?>) campaigns).get(
									"DATA_ID").toString());
							campaign.setStatus("2");
							dailyService.update(campaign);
						}
					}
					// NoShow异常
					List<Order> orders = dailyService.findBySQL("orderStatus", new String[] { branchId }, true);
					if (orders.size() > 0) {
						for (int i = 0; i < orders.size(); i++) {
							if (((Map<?, ?>) orders.get(i)).get("GUARANTEE").toString().equals(CommonConstants.Guarantee.SECURED)) {
								Order order = (Order) dailyService.findOneByProperties(Order.class, "orderId",
										((Map<?, ?>) orders.get(i)).get("ORDER_ID"));
								order.setStatus("0");
								MemberAccount memberaccount = (MemberAccount) dailyService.findOneByProperties(
										MemberAccount.class, "memberId", order.getOrderUser());
								memberaccount.setCurrNoshow((short) (memberaccount.getCurrNoshow() + 1));
								memberaccount.setTotalNoshow((short) (memberaccount.getTotalNoshow() + 1));
								ExceptionMessage exceptionMessage = new ExceptionMessage();
								exceptionMessage.setExceptionId(dailyService.getSequence("SEQ_EXCEPTION_ID"));
								exceptionMessage.setExceptionType("1");
								exceptionMessage.setDailyTime(date);
								exceptionMessage.setRecordTime(date);
								exceptionMessage.setStatus("0");
								exceptionMessage.setRemark("订单没有入住.没有预付款,取消");
								exceptionMessage.setBranchId(order.getBranchId());
								dailyService.update(order);
								dailyService.update(memberaccount);
								dailyService.save(exceptionMessage);
							} 
						}
					}
					
//					List<?> checkList = dailyService.findBySQL("checkRoomStatus", new String[] { branchId }, true);
//					if(checkList.size() > 0){
//						for(int i = 0; i < checkList.size(); i++){
//							String BranchId = ((Map<?, ?>) checkList.get(i)).get("BRANCH_ID").toString();
//							String roomType = ((Map<?, ?>) checkList.get(i)).get("ROOM_TYPE").toString();
//							String nightNum = ((Map<?, ?>) checkList.get(i)).get("NIGHTNUM").toString();
//							RoomStatus roomStatus = (RoomStatus) dailyService.findOneByProperties(RoomStatus.class, "branchId", BranchId, "roomType", roomType);
//							roomStatus.setNightnum(Integer.parseInt(nightNum));
//							dailyService.update(roomStatus);
//						}
//					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}