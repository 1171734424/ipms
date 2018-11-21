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
  	<form id="pagerForm" name="pagerForm" action="shiftLog.do" target="_self">
  		<div>
			<input id="lastshift" name="lastshift" type="hidden" value="${lastshift}">
			<input id="currshift" name="currshift" type="hidden" value="${currshift}">
			<input id="lastuser" name="lastuser" type="hidden" value="${lastuser}">
			<input id="curruser" name="curruser" type="hidden" value="${curruser}">
			<input id="starttime" name="starttime" type="hidden" value="${starttime}">
			<input id="endtime" name="endtime" type="hidden" value="${endtime}">
		</div>
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">编号</th>
						<!-- <th class="header">门店编号</th> -->
						<th class="header">上个班次</th>
						<th class="header">当前班次</th>
						<th class="header">交接人</th>
						<th class="header">当班人</th>
						<th class="header">操作时间</th>
						<th class="header">操作人</th>
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list" varStatus="status">
						<tr>
							<td>${status.index+1 }</td>
							<%-- <td>${list["BRANCH_ID"] }</td> --%>
							<td>${list["LAST_SHIFT"] }</td>
							<td>${list["CURR_SHIFT"] }</td>
							<td>${list["LAST_USER"] }</td>
							<td>${list["CURR_USER"] }</td>
							<td>${list["RECORD_TIME"] }</td>
							<td>${list["RECORD_USER"] }</td>
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
