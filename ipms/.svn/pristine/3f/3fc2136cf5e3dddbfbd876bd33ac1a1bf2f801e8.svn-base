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
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
  </head>
  <body>
   <div class="condition_div rent_deadline">
  	   <form id="pagerForm" name="pagerForm" action="queryApartmentRent.do" target="_self">
	    <div class="div_part_one">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header ornumber">房间号</th>
						<th class="header">租客姓名</th>
						<th class="header" style="width:13%;">租赁期限</th>
						<th class="header">租赁方式</th>
						<th class="header">单价</th>
						<th class="header">交租时间</th>
						<%--<th class="header">一月</th>
						<th class="header">二月</th>
						<th class="header">三月</th>
						<th class="header">四月</th>
						<th class="header">五月</th>
						<th class="header">六月</th>
						<th class="header">七月</th>
						<th class="header">八月</th>
						<th class="header">九月</th>
						<th class="header">十月</th>
						<th class="header">十一月</th>
						<th class="header">十二月</th>
					--%></tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list">
						<tr>
							<td>${list["ROOMID"]}</td>
							<td>${list["MEMBERID"]}</td>
							<td>${list["RENTTIME"]}</td>
							<td>${list["TYPEOFPAYMENT_NAME"]}</td>
							<td>${list["UNITPRICE"]}元</td>
							<td>${list["CONTRARTENDTIME"]}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<div>
			<input type="hidden" id="memberName" name="memberName" value="${memberName }"/>
			<input type="hidden" id="roomId" name="roomId" value="${roomId }"/>
			<input type="hidden" id="typeofpayment" name="typeofpayment" value="${typeofpayment }"/>
			<input type="hidden" id="startTime" name="startTime" value="${startTime }"/>
			<input type="hidden" id="endTime" name="endTime" value="${endTime }"/>
		</div>
	</form>
  </div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
	<script type="text/javascript">
	</script>
  </body>
</html>
