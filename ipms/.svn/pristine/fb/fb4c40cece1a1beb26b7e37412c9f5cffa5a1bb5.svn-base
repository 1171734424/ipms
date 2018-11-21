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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css"/>
	<style>
		.pageRegion{
				margin-top:-62px;
			}
	
	</style>
  </head>
  <body>
  	<form>
	    <div style="height: 824px;padding-right: 5px;">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">房单号</th>
						<th class="header">房型</th>
						<th class="header">房号</th>
						<th class="header">入住时间</th>
						<th class="header">退房时间</th>
						<th class="header">预订人</th>
						<th class="header">预订人手机</th>
						<th class="header">姓名</th>
						<th class="header">入住人手机</th>
						<th class="header">来源</th>
						<th class="header">会员卡号</th>
						<th class="header">房价</th>
						<th class="header">状态</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list">
						<tr ondblclick='bb(this)'>
							<td>${list["CHECKID"] }</td>
							<td>${list["ROOMNAME"] }</td>
							<td>${list["ROOMID"] }</td>
							<td>${list["CHECKINTIME"] }</td>
							<td>${list["CHECKOUTTIME"] }</td>
							<td>${list["ORDERUSERNAME"] }</td>
							<td>${list["MPHONE"] }</td>
							<td>${list["FIRSTCHECKUSERNAME"] }</td>
							<td>${list["FIRSTCHECKUSERPHONE"] }</td>
							<td>${list["DECODESOURCE"] }</td>
							<td>${list["CHECKUSER"] }</td>
							<td>${list["ROOMPRICE"] }</td>
							<td>${list["STATUSNAME"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<input type="hidden" id="checkid" name="checkid" value="${ multiQuerycheck.checkid }">
		<input type="hidden" id="roomtype" name="roomtype" value="${ multiQuerycheck.roomtype }">
		<input type="hidden" id="roomid" name="roomid" value="${ multiQuerycheck.roomid }">
		<input type="hidden" id="checkintime" name="checkintime" value="${ multiQuerycheck.checkintime }">
		<input type="hidden" id="checkouttime" name="checkouttime" value="${ multiQuerycheck.checkouttime }">
		<input type="hidden" id="checkuser" name="checkuser" value="${ multiQuerycheck.checkuser }">
		<input type="hidden" id="guarantee" name="guarantee" value="${ multiQuerycheck.guarantee }">
		<input type="hidden" id="orderuser" name="orderuser" value="${ multiQuerycheck.orderuser }">
		<input type="hidden" id="mphone" name="mphone" value="${ multiQuerycheck.mphone }">
		<input type="hidden" id="status" name="status" value="${ multiQuerycheck.status }">
		<input type="hidden" id="ordertimebegin" name="ordertimebegin" value="${ multiQuerycheck.ordertimebegin }">
		<input type="hidden" id="ordertimeend" name="ordertimeend" value="${ multiQuerycheck.ordertimeend }">
		<input type="hidden" id="arrivaltimebegin" name="arrivaltimebegin" value="${ multiQuerycheck.arrivaltimebegin }">
		<input type="hidden" id="arrivaltimeend" name="arrivaltimeend" value="${ multiQuerycheck.arrivaltimeend }">
		<input type="hidden" id="leavetimebegin" name="leavetimebegin" value="${ multiQuerycheck.leavetimebegin }">
		<input type="hidden" id="leavetimeend" name="leavetimeend" value="${ multiQuerycheck.leavetimeend }">
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		var path = "<%=request.getContextPath()%>";
		Pager.renderPager(<%=pagination%>);
		function bb(e) {
			var checkid = $(e.children[0]).html();
			window.parent.parent.JDialog.openWithNoTitle("", path + "/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=" + checkid + "&pagea=" + "-1",1179,733);
			//window.location.href=path + "/page/ipmspage/room_statistics/roomlist_check.jsp?checkid=" + checkid;
		}
		
	</script>
  </body>
</html>
