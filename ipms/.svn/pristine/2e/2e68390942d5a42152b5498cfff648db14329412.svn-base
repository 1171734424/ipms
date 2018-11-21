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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
  </head>
   <style>
	 table.myTable td {
	    vertical-align: middle;
	    font-size: 0.7rem;
	    text-overflow: ellipsis;
	    text-align: center;
	    white-space: nowrap;
	    padding: 10px;
	    overflow: hidden;
	}
  </style>
  <body>
  	<form id="pagerForm" name="pagerForm" action="operateLog.do" target="_self">
  		<div>
			<input id="recorduser" name="recorduser" type="hidden" value="${recorduser}">
			<input id="starttime" name="starttime" type="hidden" value="${starttime}">
			<input id="endtime" name="endtime" type="hidden" value="${endtime}">
		</div>
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">编号</th>
						<th class="header">操作类型</th>
						<th class="header">操作模块</th>
						<th class="header">操作内容</th>
						<th class="header">操作人</th>
						<th class="header">操作时间</th>
						<th class="header">操作IP</th>
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list" varStatus="status">
						<tr>
							<td>${status.index+1 }</td>
							<td>${list["OPERTYPE"] }</td>
							<td>${list["OPERMODULE"] }</td>
							<td title="${list['CONTENT'] }">
							<script>
								document.write(beyondContent("${list["CONTENT"] }", 26));
							</script>
							</td>
							<td>${list["RECORDUSER"] }</td>
							<td>${list["RECORDTIME"] }</td>
							<td>${list["OPERIP"] }</td>
							<td title="${list['REMARK'] }">
							<script>
								document.write(beyondContent("${list['REMARK'] }", 20));
							</script>
							</td>
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
