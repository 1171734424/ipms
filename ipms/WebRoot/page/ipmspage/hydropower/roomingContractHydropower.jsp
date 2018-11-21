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
  	<form id="pagerForm" name="pagerForm" action="roomContractHydropower.do" target="_self">
  		<div>
			<input id="type" name="type" type="hidden" value="${type}">
			<input id="memberName" name="memberName" type="hidden" value="${memberName }">
			<input id="mobile" name="mobile" type="hidden" value="${mobile}">
		</div>
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">编号</th>
						<th class="header">合同编号</th>
						<th class="header">预订人</th>
						<th class="header">手机号</th>
						<th class="header">房间号</th>
						<th class="header">房型</th>
						<th class="header">租赁方式</th>
						<th class="header">单价</th>
						<th class="header">开始时间</th>
						<th class="header">结束时间</th>
						<th class="header">交租时间</th>
						<th class="header">状态</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list" varStatus="status">
						<tr class='odd' id="${list['CONTRARTID']}">
							<td style="width:2.6%;">${status.index+1 }</td>
							<td>${list["CONTRARTID"] }</td>
							<td>${list["MEMBERID"] }</td>
							<td>${list["MOBILE"] }</td>
							<td>${list["ROOMID"] }</td>
							<td>${list["ROOMTYPENAME"] }</td>
							<td>${list["TYPEOFPAYMENT"] }</td>
							<td>${list["UNITPRICE"] }</td>
							<td>${list["STARTTIME"] }</td>
							<td>${list["ENDTIME"] }</td>
							<td>${list["CONTRARTENDTIME"] }</td>
							<td>${list["STATUSNAME"] }</td>
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
