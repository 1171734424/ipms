<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
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
		<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/pagination.css" /> --%>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<title>在住客人明细</title>
		<style type="text/css">
			#pager {
			    position: absolute;
			    padding-top: 1%;
			    padding-left: 68%;
			}
		</style>
	</head>
	<body>
		<div class="member_margin">
			<form action="" method="" id = "myForm">
			  <input id="" name="month" value="${month }" type="hidden">
			  <section class="box-content-section padding  fl">
				<section class="box_content_widget fl">
				  <div class="content">
					<table id="myTable" class="myTable" border="0" width="100">
						<thead>
							<tr>
								<th class="header"> 订单日 </th>
								<th class="header"  style="width: 14%;"> 订单编号 </th>
								<th class="header"> 民宿名称 </th>
								<th class="header"> 预订人 </th>
								<th class="header"> 预订人手机 </th>
								<th class="header"> 订单来源 </th>
								<th class="header"> 抵店时间 </th>
								<th class="header"> 离店时间 </th>
								<th class="header"> 状态 </th>
							</tr>
						</thead>
						<tbody id="tbodyInfo">
							<c:forEach items="${list}" var="list">
								<tr class="odd">
									<td class="header"> ${list["ORDERTIME"] } </td>
									<td class="header"  style="width: 14%;"> ${list["ORDER_ID"] } </td>
									<td class="header"> ${list["BRANCHNAME"] } </td>
									<td class="header"> ${list["ORDER_USER"] } </td>
									<td class="header"> ${list["M_PHONE"] } </td>
									<td class="header"> ${list["SOURCE"] } </td>
									<td class="header"> ${list["ARRIVAL_TIME"] } </td>
									<td class="header"> ${list["LEAVE_TIME"] } </td>
									<td class="header"> ${list["STATUS"] } </td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				 </div>
			<div id="pager"></div>
			 </section>
			</section>	
		   </form>
		</div>
		
		<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/ui.datetimepicker.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipmshotel/reportforms/cancelOutDetail.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
	 	</script>
	</body>
</html>