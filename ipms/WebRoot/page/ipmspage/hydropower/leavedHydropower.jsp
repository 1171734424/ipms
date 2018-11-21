<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
  <head>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
  </head>
  <body>
  	<form id="pagerForm" name="pagerForm" action="leavedHydropowerLog.do" target="_self">
  		<div>
			<input id="type" name="type" type="hidden" value="${type}">
			<input id="contractId" name="contractId" type="hidden" value="${contractId}">
			<input id="roomId" name="roomId" type="hidden" value="${roomId}">
			<input id="moblie" name="moblie" type="hidden" value="${moblie}">
			<input id="leavetime" name="leavetime" type="hidden" value="${leavetime}">
		</div>
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header" style="width:2.6%;">编号</th>
						<th class="header">合同号</th>
						<th class="header">门店</th>
						<th class="header">房型</th>
						<th class="header" style="width:4%;">房间</th>
						<th class="header">用电量</th>
						<th class="header">用电金额</th>
						<th class="header">用水量</th>
						<th class="header">用水金额</th>
						<th class="header" style="width:8%;">抵店时间</th>
						<th class="header" style="width:8%;">离店时间</th>
						<th class="header">合同人</th>
						<th class="header">手机</th>
						<th class="header">实际金额</th>
						<th class="header">合同状态</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list" varStatus="status">
						<tr class='odd' id="${list['CONTRARTID']}">
							<td style="width:2.6%;">${status.index+1 }</td>
							<td>${list["CONTRARTID"] }</td>
							<td>${list["BRANCHID"] }</td>
							<td>${list["ROOMTYPE"] }</td>
							<td style="width:4%;">${list["ROOMID"] }</td>
							<td>${list["AMMETER"] }</td>
							<td>${list["AMMETERCOST"] }</td>
							<td>${list["WATERMEMTER"] }</td>
							<td>${list["WATERMEMTERCOST"] }</td>
							<td style="width:8%;">${list["ARRIVALTIME"] }</td>
							<td style="width:8%;">${list["LEAVETIME"] }</td>
							<td>${list["MEMBERID"] }</td>
							<td>${list["MOBILE"] }</td>
							<td>${list["PAYMONEY"] }</td>
							<td>${list["STATUS"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/hydropower/hydropower.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
		var base_path = "<%=request.getContextPath()%>";
	</script>
  </body>
</html>
