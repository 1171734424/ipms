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
    <link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	
  </head>
  <body>
  	<form id="pagerForm" name="pagerForm" action="LoginLog.do" target="_self" method="post">
		<div >
			<input id="logname" name="logname" type="hidden" value="${logname}">
			<input id="source" name="source" type="hidden" value="${source}">
			<input id="status" name="status" type="hidden" value="${status}">
			<input id="starttime" name="starttime" type="hidden" value="${starttime}">
			<input id="endtime" name="endtime" type="hidden" value="${endtime}">
		</div>
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">编号</th>
						<th class="header">登录名</th>
						<th class="header">登录端</th>
						<!-- <th class="header">登录人</th> -->
						<th class="header">登录IP</th>
						<th class="header">登录状态</th>
						<th class="header">登录浏览器</th>
						<th class="header">登录分辨率</th>
						<th class="header">登录时间</th>
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list" varStatus="status">
						<tr>
							<td>${status.index+1 }</td>
							<td>${list["LOGIN_ID"] }</td>
							<td>${list["SOURCE"] }</td>
							<!--<td>${list["USER_ID"] }</td>-->
							<td>${list["LOGIN_IP"] }</td>
							<td>${list["STATUS"] }</td>
							<td>${list["BROWSER"] }</td>
							<td>${list["RESOLUTION"] }</td>
							<td>${list["RECORD_TIME"] }</td>
							<td>${list["REMARK"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
  </body>
</html>
