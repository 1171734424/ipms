<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String sysdate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
	String roomId = request.getParameter("roomId");
	String roomtype = request.getParameter("roomType");
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>维修申请</title>
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/repair.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/common/tipInfo.css">
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/ipms/need/laydate.css" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css" />
		<style type="text/css">
			#add_repair ul li {
			   line-height: 40px;
			}
			#li_label_remark {
			    margin-top: 10px;
			}
			#li_label_pepole {
			    margin-top: 5px;
			}
			#confirmBut {
			    position: absolute;
				left: -83px;
				top: 76px;
			}
			#repair_ul {
				margin-left: 10px;
			}
		</style>
	</head>
	<body>
		<div class="high_header add_repair  fadeIndown">
			<form id="workbill_search" action="" method="post">
				<div class="margintop add_repair">
					<input type="hidden" id="roomtype" value="" />
					<input type="hidden" id="houseId" value="" />
					<input type="hidden" id="housename" value="" />
					<input type="hidden" id="houseType" value="" />
					<input type="hidden" id="houseNo" value="" />
					<input type="hidden" id="orderId" value="" />
					<input type="hidden" id="memberId" value="" />
					<ul class="clearfix" id="repair_ul">
						<li>
							<label class="label_name">
								门店名称：
							</label>
							<input id="roomid" name="roomid" type="text"
								class="search_text_li" onclick="selectRoomIdDetailH()" autocomplete="off">
						</li>
						<li>
							<label class="label_name label_repairtime">
								预修日期：
							</label>
							<input id="repairTime" name="repairTime" type="text"
								class="date_text search_text" value="<%=sysdate %>" />
						</li>
						<li>
							<label class="label_name">
								紧急维修：
							</label>
							<select id="emegent_select"
								class="text_add search_text select_addrepair">
								<option value="1">
									是
								</option>
								<option value="2" selected="selected">
									否
								</option>
							</select>
						</li>
						<li>
							<label class="label_name">
								维修类别：
							</label>
							<select id="equipment_select"
								class="text_add search_text select_addrepair">
								<c:forEach items="${result}" var="rt">
									<option value="${rt['CONTENTID']}">
										${rt['PARAMNAME']}
									</option>
								</c:forEach>
							</select>
						</li>
						<li id="li_label_pepole">
							<label class="label_name">
								申请人：
							</label>
							<input id="applyname" name="applyname" type="text"
								class="search_text_li" value="" disabled>
						</li>
						<li id="li_label_pepole">
							<label class="label_name">
								手机号：
							</label>
							<input id="applymobile" name="mobile" type="text"
								class="search_text_li" disabled>
						</li>
						<li id="li_label_remark">
							<label class="label_name">
								申请备注：
							</label>
							<textarea id="repairRemark" name="remark"
								class="date_text search_text textarea_addrepair"></textarea>
						</li>
						<li>
							<input name="confirm" type="button" id="confirmBut" value="确定"
								class="button_margin confirm confirm_addrepair"
								onclick="addRepairRecordH()">
						</li>
					</ul>
				</div>
			</form>
		</div>
		<div class="roomtype_class" id="roomtype_class" style="display: none;">
			<div class="class_main" style="overflow: auto">
				<ul class="clearfix roomtypeul">
				</ul>
				<!--<div class="class_detail">
				<input type="button" id="roomid" class="button_margin confirm confirm_chooseroom" value="确定" onclick="chooseHouse()"/>
			</div>
		-->
			</div>
		</div>
		<%@ include file="../../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipmsinhouse/leftmenu/repair.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipmsinhouse/jquery-1.8.2.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipmsinhouse/classie.js" type="text/javascript"></script>
		<script src="<%=request.getContextPath()%>/script/ipmsinhouse/laydate.dev.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
		var path="<%=request.getContextPath()%>";
		var houseId = "${param.houseId}";
		laydate({
	        	elem: '#repairTime'
    	});
		  $(document).ready(function(){
				loadEventfirst();
				loadHouseDate(houseId);
			    })
			    
			 function loadEventfirst(){
			//页面加载自动获取维修类别
	    	$.ajax({//自动获取
  		 			url: path + "/loadEquipmentDataInHouse.do",
  		 			type: "post",
  		 			data : {},
  		 			success: function(json) {
  		 				var op = "";
      		 			$.each(json,function(index){
      		 				if(json[index]['CONTENTID'] == '10'){
      		 					return;
      		 				}
      		 				op += "<option value='"+json[index]['CONTENTID']+"'>"+json[index]['PARAMNAME']+"</option>"
      		 			})
      		 			$("#equipment_select").html("");
      		 			$("#equipment_select").html(op);
  		 			},
  		 			error: function() {}
  		 		});
		}
		  function loadHouseDate(houseId){
			  if(houseId == null || houseId == undefined || houseId == ""){
				  return;
			  }
			  $.ajax({//自动获取
					url: path + "/getApplicationDateHouse.do",
					type: "post",
					data : {"houseId" : houseId},
					success: function(json) {
						$("#roomtype_class").css("display","none");
						$("#roomid").val(json.houseName);
						$("#applyname").val(json.memberName);
						$("#applymobile").val(json.memberPhone);
						$("#houseId").val(json.houseId);
						$("#houseNo").val(json.houseNo);
						$("#houseType").val(json.houseType);
						$("#orderId").val(json.orderId);
						$("#memberId").val(json.memberId);
						$("#housename").val(json.houseName);
					},
					error: function() {}
				});
		  }
	</script>
	</body>
</html>
