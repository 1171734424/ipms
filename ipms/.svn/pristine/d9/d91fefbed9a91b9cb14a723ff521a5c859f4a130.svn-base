<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
  <head><!-- 当前房态页面 -->
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
  </head>
  <body>
  	<form id="pagerForm" name="pagerForm" action="currentRoom.do" target="_self">
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="date">
					<tr>
						<th class="header">日期</th>
						<th class="header">总房数</th>
						<th class="header">已入住房数</th>
						<th class="header">预订房数</th>
						<th class="header">可售房数</th>
						<th class="header">维修房数</th>
						<th class="header">停售房数</th>
						<th class="header">出租率(%)</th>
						<th class="header">总房量入住率(%)</th>

					</tr>
				</thead>
				<tbody id="info">
 					<tr>
							<td>${madate}</td>
							<td>${result[0]["ZG"]}</td>
							<td>${result[0]["YRZF"]}</td>
							<td>${result[0]["YDF"]}</td>
							<td>${result[0]["KSF"]}</td>
							<td>${result[0]["WXF"]}</td>
							<td>${result[0]["TSF"]}</td>
							<td>${result[0]["CZRATE"]}</td>
							<td>${result[0]["RZRATE"]}</td>

						</tr>
 
   				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<input type="hidden" id="madate" name="madate" value="${madate}">
	</form>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
  </body>
</html>
