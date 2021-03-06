<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String sysdate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
	String roomId = request.getParameter("roomId");
	String roomtype = request.getParameter("roomType");
%>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>维修申请</title>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/repair.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css">
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	</head>
  <body>
	<div class="high_header add_repair  fadeIndown">
		<form id="workbill_search" action="" method="post">
			<div class="margintop add_repair">
				<input type="hidden" id="roomtype" value=""/>
				<ul class="clearfix">
					<li><label class="label_name">房号</label><input id="roomid" name="roomid" type="text" class="search_text_li" onclick="selectRoomIdDetail()"></li>
					<li><label class="label_name label_repairtime">预修日期</label><input id="repairTime" name="repairTime" type="text" class="date_text search_text" value="<%=sysdate %>"/></li>		
					<li><label class="label_name">紧急维修</label> 
						<select id="emegent_select" class="text_add search_text select_addrepair"><option value="1">是</option><option value="2" selected="selected">否</option></select>
					</li>
					<li><label class="label_name">维修类别</label>
						<select id="equipment_select" class="text_add search_text select_addrepair">
							<c:forEach items="${result}" var="rt">
							<option value="${rt['CONTENTID']}">${rt['PARAMNAME']}</option>
							</c:forEach>
						</select>
					</li>
					<li><label class="label_name">合同号</label><input id="contrartid" name="contrartid" type="text" class="search_text_li" value=""  disabled/></li>
					<li><label class="label_name">申请人</label><input id="applyname" name="applyname" type="text" class="search_text_li" value="" disabled></li>
					<li><label class="label_name">手机号</label><input id="applymobile" name="mobile" type="text" class="search_text_li" disabled></li>
					<li ><label class="label_name">申请备注</label><textarea id="repairRemark" name="remark" class="date_text search_text textarea_addrepair"></textarea></li>
					<li>
						<input name="confirm" type="button" value="确定" class="button_margin confirm confirm_addrepair" onclick="addRepairRecord()">
					</li>
				</ul>
			</div>
		</form>
	</div>
	<div class="roomtype_class" id="roomtype_class" style="display:none;">
		<div class="class_main">
			<ul class="clearfix roomtypeul">
			</ul>
			<div class="class_detail">
				<table>
					<tr id="room_date">
					</tr>
				</table> 
				<!-- <input type="button" id="roomid" class="button_margin confirm confirm_chooseroom" value="确定" onclick="chooseRoom()"/> -->
			</div>
		</div>
	</div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/apartment/apartmentLeftmenu/apartmentRepair/repair.js"></script>
	<script  src="<%=request.getContextPath()%>/script/apartment/js/jquery-1.8.2.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/apartment/js/classie.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/css/ipms/jquery-easyui/jquery.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/css/ipms/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/apartment/js/laydate.dev.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
	  var path="<%=request.getContextPath()%>";
	  laydate({
        	  elem: '#repairTime'
   	  });
	  $(document).ready(function(){
			loadEventfirst();
			loadEventsecond();
		    })
		function loadEventfirst(){
			//页面加载自动获取维修类别
	    	$.ajax({//自动获取
  		 			url: path + "/loadApartEquipmentData.do",
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
		function loadEventsecond(){
			//$("#roomid").focus();//默认光标选中
   			var roomId ="<%=request.getParameter("roomId")%>";//判断房态图跳转
   			var roomType = "<%=request.getParameter("roomType")%>";
   			if(roomId != "null"){
   				$.ajax({//自动获取
   		 			url: path + "/getApartApplicationDate.do",
   		 			type: "post",
   		 			data : {"roomid" : roomId,"roomtype" : roomType },
   		 			success: function(json) {
       		 			$("#roomid").val(roomId);
   		 				$("#contrartid").val(json.contrartid);
   		 				$("#applyname").val(json.membername);
   		 				$("#applymobile").val(json.mobile);
   		 			},
   		 			error: function() {}
   		 		});
       			}
		}    
	</script>
  </body>
</html>
