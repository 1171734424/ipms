<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
  <style type="text/css">
  	.pageRegion {
    	height: 418px;
    }
     table.myTable th{
    	min-width: 210px;
    }
    table.mytable2{
    	table-layout:inherit;
    }   
    
  </style>
  </head>
  <body>

  	<form id="pagerForm" name="pagerForm">
	    <div style="overflow-x: scroll;">
	    	<table id="myTable" class="myTable mytable2" border="0" width="100">
				<thead id="date">
					<tr>
						<th class="header">民宿编号</th>
						<c:forEach items="${listdate}" var="listdate">
							<th class="header">${listdate}</th>
						</c:forEach> 
					</tr>
				</thead>	
				<tbody id="info">
					<c:forEach items="${newlist}" var="roomtype" ><!-- hOUSEID -->
						<tr>
							<td>${roomtype.houseId}</td>
						<c:forEach items="${roomtype.dates}" var="listdate"><!-- DATE -->
							<td <c:if test="${ listdate.status eq '停售' }">style="background: #EC9454;"</c:if> 
								<c:if test="${ listdate.status eq '维修' }">style="background: #78BBE6;"</c:if>
								<c:if test="${ listdate.status eq '在住' }">style="background: #A3DE83;"</c:if>
								<c:if test="${ listdate.status eq '预定' }">style="background: #FFF68F;"</c:if>
							>
							${listdate.status} </td>
						</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<input type="hidden" id="edate" name="edate" value="${ edate }">
		<input type="hidden" id="madate" name="madate" value="${madate}">
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/order/rightclick.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
		var path = "<%=request.getContextPath()%>";
		Pager.renderPager(<%=pagination%>);
	</script>
  </body>
</html>