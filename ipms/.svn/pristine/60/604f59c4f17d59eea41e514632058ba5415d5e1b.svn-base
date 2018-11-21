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
   <style>
	 table.myTable td {
	    vertical-align: middle;
	    font-size: 0.7rem;
	    text-overflow: ellipsis;
	    text-align: center;
	    white-space: nowrap;
	    padding: 10px;
	    overflow: hidden;
	}
  </style>
  <body>
  	<form id="pagerForm" name="pagerForm" action="operateLog.do" target="_self">
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">订单号</th>
						<th class="header">房型</th>
						<th class="header">房价</th>
						<th class="header">担保</th>
						<th class="header">时效</th>
						<th class="header">预订人</th>
						<th class="header">预订人手机</th>
						<th class="header">预订渠道</th>
						<th class="header">状态</th>
						<th class="header">备注</th>
						<th class="header">操作</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${orderinfo}" var="ordinfo" varStatus="status">
						<tr ondblclick="checkorderinfo(this)">
							<td>${ordinfo["ORDERID"] }</td>
							<td>${ordinfo["ROOMNAME"] }</td>
							<td>${ordinfo["ROOMPRICE"] }</td>
							<td>${ordinfo["GUARANTEE"] }</td>
							<td>${ordinfo["LIMITED"] }</td>
							<td>${ordinfo["ORDERUSER"] }</td>
							<td>${ordinfo["PHONE"] }</td>
							<td>${ordinfo["SOURCE"] }</td>
							<td>${ordinfo["STATUS"] }</td>
							<td>${ordinfo["REMARK"] }</td>
							<td><span class="cancel_span"></span><span class="undone"></span></td>
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
		var path = "<%=request.getContextPath()%>";
		Pager.renderPager(<%=pagination%>);
	</script>
  </body>
</html>
