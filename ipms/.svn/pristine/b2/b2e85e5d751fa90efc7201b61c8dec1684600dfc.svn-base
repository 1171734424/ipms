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
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<style>
	
	.checkroom{
	    font-size: 0.75rem;
	    background: #FC8A15;
	    color: #fff;
	    height: 25px;
	    width: 45px;
	}
	   
	</style>
  </head>
  <body>
  	<form id="pagerForm" name="pagerForm" action="${action }" target="_self" method="post">
		<div >
			<input id="memberName" name="memberName" type="hidden" value="${memberName}">
			<input id="mobile" name="mobile" type="hidden" value="${mobile}">
			<input id="arrivaltime" name="arrivaltime" type="hidden" value="${arrivaltime}">
		</div>
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">房间号</th>
						<th class="header">房型</th>
						<th class="header">房价(元)</th>
						<th class="header">预订人</th>
						<th class="header">预订人手机</th>
						<th class="header">开始日期</th>
						<th class="header">结束日期</th>
						<th class="header">预定日期</th>
						<th class="header">定金(元)</th>
						<th class="header">状态</th>
						<th class="header">备注</th>
						<th class="header">操作</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="ordinfo" varStatus="status">
						<tr>
							<td>${ordinfo["ROOM_ID"] }</td>
							<td>${ordinfo["ROOMNAME"] }</td>
							<td>${ordinfo["ROOM_PRICE"] }</td>
							<td>${ordinfo["MEMBER_NAME"] }</td>
							<td>${ordinfo["M_PHONE"] }</td>
							<td>${ordinfo["ARRIVAL_TIME"] }</td>
							<td>${ordinfo["LEAVE_TIME"] }</td>
							<td>${ordinfo["ORDER_TIME"] }</td>
							<td>${ordinfo["ADVANCE_CASH"] }</td>
							<td>${ordinfo["STATUS_NAME"] }</td>
							<td>${ordinfo["REMARK"] }</td>
							<td><input type="button" class="checkroom" value="退款" onclick="aptOrderReFund('${ordinfo['ORDER_ID'] }','${ordinfo['STATUS'] }')"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		Pager.renderPager(<%=pagination%>);
		var path = "<%=request.getContextPath()%>";
		function aptOrderReFund(aptOrderId,status){
			if(status == 1){
				$.ajax({
			         url: path + "/aptOrderReFund.do",
					 type: "post",
					 data : {aptOrderId : aptOrderId},
					 success: function(json) {
						 showMsg(json.message);
						 window.setTimeout("window.parent.location.reload(true)",1000);
					 },
					 error: function(json) {}
				});
			} else {
				showMsg("不可退款!");
			}
		}
	</script>
  </body>
</html>
