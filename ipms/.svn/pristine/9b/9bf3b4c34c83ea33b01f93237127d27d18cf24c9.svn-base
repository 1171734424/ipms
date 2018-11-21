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
  	<form id="pagerForm" name="pagerForm" action="switchRoom.do" target="_self">
  		<div>
			<input id="roomId" name="roomId" type="hidden" value="${roomId}">
			<input id="starttime" name="starttime" type="hidden" value="${starttime}">
			<input id="endtime" name="endtime" type="hidden" value="${endtime}">
		</div>
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">门店</th>
						<th class="header">房间(旧)</th>
						<th class="header">房间(新)</th>
						<th class="header">房间类型(旧)</th>
						<th class="header">房间类型(新)</th>
						<th class="header">价格(旧)</th>
						<th class="header">价格(新)</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list" varStatus="status">
						<tr>
							<td>${list["BRANCH_ID"] }</td>
							<td>${list["LAST_ROOMID"] }</td>
							<td>${list["CURR_ROOMID"] }</td>
							<td>${list["LAST_ROOMTYPE"] }</td>
							<td>${list["CURR_ROOMTYPE"] }</td>
							<td>${list["LAST_ROOMPRICE"] }</td>
							<td>${list["CURR_ROOMPRICE"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
	</form>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
  </body>
</html>
