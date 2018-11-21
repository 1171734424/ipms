<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reportform/report_forms.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<title>在住客人明细报表</title>
	</head>
	<body style="overflow:hidden;">
		<div class="shop_search">
			<form action="changeRoomTable.do" method="post" id = "myForm">
			  <div class="form_margin">
				<input type="text" name="startTime" id="startTime" class="date shop_name" value="${startTime}" placeholder="开始时间" />
				<input type="text" name="endTime" id="endTime" class="date shop_name" value="${endTime}" placeholder="结束时间" />
				<!--<input type="text" name="status" id="status" class="shop_name" value="${status}" placeholder="房单状态" />
				--><input type="text" name="checkId" id="checkId" class="shop_name" value="${checkId}" placeholder="房单编号" />
				<select name="status" id="status" class="search_select">
					<option value="">房单状态</option>
					<option value="1">在住</option>
					<option value="2">离店</option>
					<option value="3">已结未退</option>
				</select>
				<input type="text" name="checkUser" id="checkUser" class="shop_name" value="${checkUser}" placeholder="客人名称" />
				<input type="text" name="recordUser" id="recordUser" class="shop_name" value="${recordUser}" placeholder="操作员" />
					<button type="button" class="btn_style btn_search button_margin" onclick="search();">
						查询
					</button>
				</div>
				<section class="box-content-section fl">
					<section class="box_content_widget fl">
						<div class="content">
							<iframe name="frame" id="frame" class="myTable" frameborder="0" width="100%" height="100%" ></iframe>
						</div>
					</section>
				</section>
			</form>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/reportforms/changeRoomInfo.js" type="text/javascript" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			var status = "${status}";
			
		$(document).ready(function(){
			//alert("&status="+$("#status").val())
			$("#status").val(status);
			$("#frame").attr('src',"changeRoomTableData.do?checkId="+$("#checkId").val()+"&status="+$("#status").val()+"&checkUser="+$("#checkUser").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&recordUser="+$("#recordUser").val());
			$(".date").datepicker({ dateFormat: "yy/mm/dd " });
		    $("#startTime").css('font-size','0.85em');
		    $("#endTime").css('font-size','0.85em');
		 })
	 	</script>
	</body>
</html>