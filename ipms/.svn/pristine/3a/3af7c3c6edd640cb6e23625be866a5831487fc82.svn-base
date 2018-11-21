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
  	<form id="pagerForm" name="pagerForm" action="saleLog.do" target="_self">
  		<div>
			<input id="debts" name="debts" type="hidden" value="${debts}">
			<input id="goodsname" name="goodsname" type="hidden" value="${goodsname}">
			<input id="paytype" name="paytype" type="hidden" value="${paytype}">
			<input id="starttime" name="starttime" type="hidden" value="${starttime}">
			<input id="endtime" name="endtime" type="hidden" value="${endtime}">
		</div>
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">编号</th>
						<!-- <th class="header">门店名称</th> -->
						<th class="header">挂账类型</th>
						<!-- <th class="header">房单号</th> -->
						<th class="header">房间号</th>
						<th class="header">商品大类</th>
						<th class="header">商品名称</th>
						<th class="header">数量</th>
						<th class="header">单价</th>
						<th class="header">付款方式</th>
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
							<td>${list["DEBTS"] }</td>
							<%-- <td>${list["CHECK_ID"] }</td> --%>
							<td>${list["ROOM_ID"] }</td>
							<td>${list["CATEGORY_ID"] }</td>
							<td>${list["GOODS_NAME"] }</td>
							<td>${list["AMOUNT"] }</td>
							<td>${list["PRICE"] }</td>
							<td>${list["PAY_TYPE"] }</td>
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
