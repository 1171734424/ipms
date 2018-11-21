<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%
	JSONObject pagination = JSONObject.fromObject(request
			.getAttribute("pagination"));
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reportform/report_forms.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<title>冲减明细</title>
	</head>
	<body>
		<div class="guest_height">
			<form>
				<input type="hidden" id="branchName" name="branchName" class="date shop_name" value="${branchName }" placeholder="民宿名称"/>
				<input type="hidden" id="endTime" name="endTime" class="date shop_name" value="${endTime}" placeholder="日期范围"/>
				<input type="hidden" id="startTime" name="startTime" class="date shop_name" value="${startTime}" placeholder="日期范围"/>
				<input type="hidden" id="times" name="times" class="date shop_name" value="${times}" placeholder="日期范围"/>
				<input type="hidden" name="recordUser" id="recordUser" class="shop_name" value="${recordUser}" placeholder="入账人员" />
				<table id="myTable" class="myTable" border="0">
					<thead>
						<tr>
							<th class="header"> 明细编号 </th>
							<th class="header"> 名宿编号 </th>
							<th class="header"> 民宿名称 </th>
							<th class="header"> 项目编号</th>
							<th class="header"> 项目名称</th>
							<th class="header"> 描述 </th>
							<th class="header"> 消费 </th>
							<th class="header"> 结算 </th>
							<th class="header"> 支付方式 </th>
							<th class="header"> 冲减原因 </th>
							<th class="header"> 冲减时间 </th>
							<th class="header"> 操作人 </th>
							<!-- <th class="header"> 冲减原单编号 </th> -->
						</tr>
					</thead>
					<tbody id="tbodyInfo">
						<c:forEach items="${list}" var="list">
							<%-- <c:if test="${list['OLDCOST'] >= 0 && list['OLDPAY'] >=0}"> --%>
								<tr class="odd">
									<td> ${list["RECORDID"] } </td>
									<td> ${list["OLDBRANCHID"] } </td>
									<td> ${list["BRANCHNAME"] } </td>
									<td> ${list["PROJECTID"] } </td>
									<td> ${list["PROJECTNAME"] } </td>
									<td> ${list["DESCRIBE"] } </td>
									<td> ${list["OLDCOST"] } </td>
									<td> ${list["OLDPAY"] } </td>
									<td> ${list["PAYMENT"] } </td>
									<td> ${list["OLDOFFSET"] } </td>
									<td> ${list["RECORDTIME"] } </td>
									<td> ${list["RECORDUSER"] } </td>
									<%-- <td> ${list["REMARK"] } </td> --%>
								</tr>
							<%-- </c:if> --%>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<div id="pager"></div>
		<%@ include file="../../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/ui.datetimepicker.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
		
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
			
			
			function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
	 	</script>
	</body>
</html>