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
  	<form id="pagerForm" name="pagerForm" action="${action }" target="_self" method="post">
		<div >
			<input id="memberName" name="memberName" type="hidden" value="${memberName}">
			<input id="mobile" name="mobile" type="hidden" value="${mobile}">
			<%--<input id="arrivaltime" name="arrivaltime" type="hidden" value="${arrivaltime}">
		--%></div>
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">房单号</th>
						<th class="header">民宿名称</th>
<!-- 						<th class="header">房型</th> -->
						<th class="header">单价(元)</th>
						<th class="header">会员号</th>
						<th class="header">预订人</th>
<!-- 						<th class="header">入住人</th> -->
						<th class="header">手机号</th>
						<th class="header">渠道</th>
						<th class="header">入住时间</th>
						<th class="header">预离日期</th>
						<th class="header">状态</th>
						<th class="header">操作人</th>
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="ordinfo" varStatus="status">
						<tr ondblclick="houseOrderInfo(this)">
							<td>${ordinfo["CHECK_ID"] }</td>
							<td>${ordinfo["HOUSENAME"] }</td>
<!-- 							<td>${ordinfo["ROOM_TYPE"] }</td> -->
							<td>${ordinfo["ROOM_PRICE"] }</td>
							<td>${ordinfo["ORDER_USER"] }</td>
							<td>${ordinfo["ORDER_NAME"] }</td>
<!-- 							<td>${ordinfo["MEMBER_NAME"] }</td> -->
							<td>${ordinfo["MOBILE"] }</td>
							<td>${ordinfo["SOURCENAME"] }</td>
							<td>${ordinfo["CHECKIN_TIME"] }</td>
							<td>${ordinfo["CHECKOUT_TIME"] }</td>
							<td>${ordinfo["STATUSNAME"] }</td>
							<td>${ordinfo["RECORDUSER"] }</td>
							<td>${ordinfo["REMARK"] }</td>
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
		var path = "<%=request.getContextPath()%>";
		function houseOrderInfo(e) {
			var houseorderid = $(e.children[0]).html();
			window.parent.parent.JDialog.openWithNoTitle("", path + "/page/ipmshouse/housecheck/housecheck_details.jsp?houseorderid=" + houseorderid,1179,620);
		}
	</script>
  </body>
</html>
