<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<input type="hidden" name="rpId" id="rpId" class="shop_name" value="${rpId}" placeholder="房价码" />
				<input type="hidden" name="branchName" id="branchName" class="shop_name" value="${branchName}" placeholder="民宿名称" />
				<input type="hidden" id="queryDate" name="queryDate" class="date shop_name" value="${queryDate}" placeholder="日期" />
				<table id="myTable" class="myTable" border="0" width="100">
					<thead>
						<tr>
							<th class="header"> 民宿ID </th>
							<th class="header"> 民宿名称 </th>
							<th class="header"> 平均价格 </th>
							<th class="header"> RevPAR(价格) </th>
							<th class="header"> 入住率 </th>
							<th class="header"> 赔偿 </th>
						</tr>
					</thead>
					<tbody id="tbodyInfo">
						<c:forEach items="${houseList }" var="list">
							<tr class="odd">
								<td> ${list["HOUSE_ID"] } </td>
								<td> ${list["HOUSENAME"] } </td>
								<td> ${avgPrice } </td>
								<td> ${list["REVPAR"] } </td>
								<td> ${list["OCCUPANCYRATE"] } </td>
								<td> ${list["SUMCOST"] } </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<div id="pager" style=" margin-top: 68px;"></div>
	</body>
	<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipmshotel/reportforms/changeRoomInfo.js" type="text/javascript" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
	 	</script>
</html>