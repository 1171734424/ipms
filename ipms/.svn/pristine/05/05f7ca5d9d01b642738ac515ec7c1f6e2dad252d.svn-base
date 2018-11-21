<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
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
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<title>渠道占比数据</title>
	</head>
	<body style="">
		<div class="guest_height">
			<form>
				<input type="hidden" name="startTime" id="startTime"  value="${startTime }" />
				<input type="hidden" name="adminUser" id="adminUser"  value="${adminUser }" />
				<table id="myTable" class="myTable" border="0" width="100">
					<thead>
						<tr>
							<th class="header"> 月份 </th>
							<th class="header"> APP </th>
							<th class="header"> 网站 </th>
							<th class="header"> 门店 </th>
							<th class="header"> WAP </th>
							<th class="header"> 微信 </th>
							<th class="header"> 直接入住</th>
							<th class="header"> 合作渠道 </th>
							<th class="header"> 其他 </th>
							<th class="header"> 合计数量 </th>
							<th class="header"> 环比 </th>
						</tr>
					</thead>
					<tbody id="tbodyInfo">
						<c:forEach items="${list}" var="list">
							<tr class="odd"> 
								<td> ${list["ORDERTIME"] } </td>
								<td> ${list["APP"] } </td>
								<td> ${list["WEB"] } </td>
								<td> ${list["BRANCH"] } </td>
								<td> ${list["WAP"] } </td>
								<td> ${list["WECHAT"] } </td>
								<td> ${list["DIRECTRY"] } </td>
								<td> ${list["COOPERATION"] } </td>
								<td> ${list["OTHER"] } </td>
								<td> ${list["TOTAL"] } </td>
								<td> ${list["BT_TOTAL"] } </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<div id="pager"></div>
	</body>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
			
			// 双击跳出当月所有订单信息
			$("tr[class='odd']").dblclick(function(){
				var month = $(this).children(":first")[0].innerText;
				window.parent.JDialog.open("当月订单信息", base_path +"/hotelallMonthOrderDetails.do?month="+month,1200,430);
			});
	</script>
</html>