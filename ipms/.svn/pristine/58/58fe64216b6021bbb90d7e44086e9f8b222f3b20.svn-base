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
  	<form id="pagerForm" name="pagerForm" action="apartmentroomingHydropowerLog.do" target="_self">
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
						<th class="header">编号</th>
						<th class="header">单号</th>
						<th class="header">门店</th>
						<th class="header">房间</th>
						<th class="header">开始时间</th>
						<th class="header">结束时间</th>
						<th class="header">电费用量</th>
						<th class="header">电费金额</th>
						<th class="header">水费用量</th>
						<th class="header">水费金额</th>
						<th class="header">记录状态</th>
						<th class="header">记录时间</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list" varStatus="status">
						<tr>
							<td>${status.index+1 }</td>
							<%--<td>${list["LOGID"] }</td>
							--%>
							<td>${list["ORDERID"] }</td>
							<td>${list["BRANCHID"] }</td>
							<td>${list["ROOMID"] }</td>
							<td>${list["STARTTIME"] }</td>
							<td>${list["ENDTIME"] }</td>
							<td>${list["ELECUSED"] }</td>
							<td>${list["ELECCONSUME"] }</td>
							<td>${list["WATERUSED"] }</td>
							<td>${list["WATERCONSUME"] }</td>
							<td>${list["STATUS"] }</td>
							<td>${list["RECORDTIME"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
	</form>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/apartment/apartmentmainmenu/apartmenthydropower/hydropower.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
  </body>
</html>
