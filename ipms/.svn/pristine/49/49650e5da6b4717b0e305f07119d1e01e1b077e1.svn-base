<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../../common/script.jsp"%>
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
  	   <form id="pagerForm" name="pagerForm" action="queryApartmentRoomStatus.do" target="_self">
	    <div class="div_part_one">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header ornumber">房间号</th>
						<th class="header">一月</th>
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
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${rooms}" var="rooms">
						<tr>
							<td>${rooms["ROOM_ID"] }</td>
							<c:forEach items="${roomMap}" var="roomMap">
								<c:if test="${roomMap.key == rooms['ROOM_ID'] }">
									<c:forEach items="${roomMap.value}" var="roomMapValue">
										<c:if test="${roomMapValue.value != '0'}">
											<td style="background-color: #E85B48;">${roomMapValue.value }</td>
										</c:if>
										<c:if test="${roomMapValue.value == '0'}">
											<td></td>
										</c:if>
									</c:forEach>
								</c:if>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<div>
			<input type="hidden" id="pageNum" name="pageNum" value="${pageNum }">
			<input type="hidden" id="year" name="year" value="${year }"/>
		</div>
	</form>
  </div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
		window.parent.document.getElementById('pageNum').value=$("#pageNum").val();
	</script>
	<script type="text/javascript">
	</script>
  </body>
</html>
