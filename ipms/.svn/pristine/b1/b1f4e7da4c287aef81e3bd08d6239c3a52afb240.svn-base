<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ include file="../../../common/script.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/repair.css"/>
  </head>
  <body>
   <div class="condition_div" >
  	   <form id="pagerForm" name="pagerForm" action="" target="_self">
  	   <input type="hidden"  name ="applyId" value="${mlog[0]['APPLYID']}">
	    <div class="div_part_one">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">日志号</th>
						<th class="header">申请号</th>
						<c:if test="${flag =='1'}">
						<th class="header">民宿名称</th>
						</c:if>
						<c:if test="${flag =='2'}">
						<th class="header">房间号</th>
						</c:if>
						<th class="header">来源</th>
						<th class="header">维修人</th>
						<th class="header">申请日期</th>
						<th class="header">维修日期</th>
						<th class="header">维修时段</th>
						<th class="header">记录人</th>
						<th class="header">状态</th>
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					
					<c:forEach items="${mlog}" var="ml">
						<tr>
							<td class="td_log">${ml["LOGID"] }</td>
							<td class="td_log">${ml["APPLYID"] }</td>
							<c:if test="${flag =='1'}">
								<td >${ml["BRANCHID"] }</td>
							</c:if>
							<c:if test="${flag =='2'}">
								<td >${ml["ROOMID"] }</td>
							</c:if>
							<td >${ml["SOUR"] }</td>
							<td>${ml["REPERSON"] }</td>
							<td>
								<fmt:formatDate type="time" value="${ml['APPLICATIONDATE'] }" pattern="yyyy/MM/dd" />
							</td>
							<td>
								<fmt:formatDate type="time" value="${ml['REPAIRTIME'] }" pattern="yyyy/MM/dd" />
							</td>
							<td>${ml["AREA"] }</td>
							<td>${ml["RECORDER"] }</td>
							<td>${ml["STATUS"] }</td>
							
							<%-- <td>${ml["REMARK"] }</td> --%>
							<c:if test="${fn:length(ml['REMARK'])>6}">	
							 <td title='${ml["REMARK"]}'>${fn:substring(ml["REMARK"],0,6)}...</td>
							</c:if>
							<c:if test="${fn:length(ml['REMARK'])<=6}">	
							 <td title='${ml["REMARK"]}'>${ml["REMARK"]}</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
	</form>
  </div>
	
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/goodssale.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/repair.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	    var base_path = "<%=request.getContextPath()%>";
	    var path = "<%=request.getContextPath()%>";
	  	var log ='${mlog}';
	  	var applyId = '${mlog[0]["APPLYID"]}';
	</script>
  </body>
</html>
