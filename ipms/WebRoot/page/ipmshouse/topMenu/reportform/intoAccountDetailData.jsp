<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglib.jsp"%>
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
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<title>入账项目明细</title>
	</head>
	<body>
		<div class="guest_height">
			<form>
				<input type="hidden" name="checkId" id="checkId" class="shop_name" value="${checkId}" placeholder="账单编号" />
				<input type="hidden" name="branchName" id="branchName" class="shop_name" value="${branchName}" placeholder="民宿名称" />
				<input type="hidden" id="startTime" name="startTime" class="date shop_name" value="${times}" placeholder="日期范围" />
				<input type="hidden" name="recordUser" id="recordUser" class="shop_name" value="${recordUser}" placeholder="入账人" />
				<table id="myTable" class="myTable" border="0" width="100">
					<thead>
						<tr>
							<th class="header" style="width: 14%;"> 入账编号 </th>
							<th class="header" style="width: 14%;"> 原房单号 </th>
							<th class="header"> 民宿名称 </th>
							<th class="header"> 入账类型 </th>
							<th class="header"> 项目编号 </th>
							<th class="header"> 项目名称 </th>
							<th class="header"> 金额 </th>
							<th class="header"> 入账时间 </th>
							<th class="header"> 入账人 </th>
						</tr>
					</thead>
					<tbody id="tbodyInfo">
						<c:forEach items="${list}" var="list">
							<tr class="odd">
								<td> ${list["RECORDID"] } </td>
								<td> ${list["CHECKID"] } </td>
								<td> ${list["BRANCHNAME"] } </td>
								<td> ${list["RECORDTYPE"] } </td>
								<td> ${list["PROJECTID"] } </td>
								<td> ${list["PROJECTNAME"] } </td>
								<td> ${list["FEE"] } </td>
								<td> ${list["ACCOUNTTIME"] } </td>
								<td> ${list["ACCOUNTUSER"] } </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<div id="pager"></div>
	</body>
	<%@ include file="../../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/reportforms/changeRoomInfo.js" type="text/javascript" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
	 	</script>
</html>