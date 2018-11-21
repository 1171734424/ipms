<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>房租计划</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/chummageplan.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
  </head>
  
  <body>
    	<div class="rent_width">
			<div class="rent_one">
				<section class="box_content_widget fl" style="min-height: 615px;padding: 10px 0;">
					<div class="content">
						<iframe name="logFrame" id="logFrame" frameborder="0" width="100%" height="100%"></iframe>
					<!-- 
						<table id="myTable" class="myTable" border="0">
							<thead>
								<tr>
									<th class="header">生效日期</th>
									<th class="header">房号</th>
									<th class="header">房价码</th>
									<th class="header">原房价（元）</th>
									<th class="header">浮动（元）</th>
									<th class="header">产生时间</th>
									<th class="header">操作者</th>
							    <th class="header">状态</th>
									<th class="header">备注</th>
								</tr>
							</thead>
							<tbody id="roomplan_date">
							</tbody>
						</table> -->
					</div>
				</section>
			</div>
			<!-- <div class="rent_footer"><span onclick="updateroomprice()">修改房价</span></div> -->
		</div>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath()%>";
			$(function(){
				getQueryData();
			});
			
			function getQueryData(){
				var roomid = $(window.parent.roomid).text();
				$("#logFrame").attr("src","<%=request.getContextPath()%>/showRoomPlanData.do?roomid=" + roomid);
			}
			
			
			
			
			
			
			/*function loadRoomPlan(){
				$.ajax({
			         url: path + "/loadRoomPlanData.do",
					 type: "post",
					 data : { roomid : roomid },
					 success: function(json) {
						 loadRoomPlanData(json);
					 },
					 error: function(json) {}
				});
			}
			
			function loadRoomPlanData(json){
				 var tabledata;
			 	$.each(json, function(index){
			 		tabledata = tabledata + "<tr class='odd' >" +
			 		"<td>" + dealDate(json[index]["VALIDDAY"]) + "</td>" +
			 		"<td>" + json[index]["ROOMID"] + "</td>" +
			 		"<td>" + json[index]["RPID"] + "</td>" +
			 		"<td>" + json[index]["ROOMPRICE"].toFixed(2) + "</td>" +
			 		"<td>" + json[index]["CASHBACK"].toFixed(2) + "</td>" +
			 		"<td>" + dealDate(json[index]["RECORDTIME"]) +"</td>" +
			 		"<td>" + json[index]["STAFFNAME"] + "</td>" +
			 		//"<td>" + json[index]["STATUS"] + "</td>" +
			 		"<td>" + json[index]["REMARK"] + "</td>" +
			 		"</tr>";
			 	});
			 	if(json.length == 0){
			 		$("#roomplan_date").html("");
			 	}else{
			 		$("#roomplan_date").html(tabledata);			 	
			 	}
			}*/
			/*function updateroomprice(){
				var checkid = '0';
				window.parent.JDialog.open("房价修改", path + "/showupdateRoomPrice.do?checkid=" + checkid , 700, 400);
			}*/
			
		</script>
		
  </body>
</html>
