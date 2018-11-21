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
	<style>
		#pager{
			position:fixed;
			bottom:0px;
			right:0px;
		}
		
		@media screen and (max-width:1102px){
			#pager{
				bottom:80px;
			}
		}
	#pager .pageRegion{
		position:static;
	}
	</style>
  </head>
  <body>
  	<form id="pagerForm" name="pagerForm" action="currentRoom.do" target="_self">
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="date">
					<tr>
						<th class="header">房间类型</th>
						<th class="header">总房数</th>
						<th class="header">可售房数</th>
						<th class="header">停售房数</th>
						<th class="header">过夜房数</th>
						<th class="header">有效预定</th>
						<th class="header">所有预定</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list">
						<tr>
							<td>${list["ROOMNAME"] }</td>
							<td>${list["ROOMID"] }</td>
							<td>${list["SELL"] }</td>
							<td>${list["STOP"] }</td>
							<td>${list["NIGHT"] }</td>
							<td>${list["VAILD"] }</td>
							<td>${list["INVAILD"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<input type="hidden" id="madate" name="madate" value="${madate}">
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
  </body>
</html>
