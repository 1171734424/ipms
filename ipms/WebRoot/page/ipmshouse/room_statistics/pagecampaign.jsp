<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
	//JSONObject  mylist = JSONObject.fromObject(request.getAttribute("list"));
	//JSONArray json=JSONArray.fromObject(request.getAttribute("list"));
%>
<!DOCTYPE HTML>
<html>
  <head>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
  </head>
   <style>
   	table.myTable tbody tr:hover td{
   		color:#333 !important;
   	}
   	
	.dotline { 
		BORDER-BOTTOM-STYLE: dotted; BORDER-LEFT-STYLE: dotted; BORDER-RIGHT-STYLE: dotted; BORDER-TOP-STYLE: dotted 
	} 
   </style>
  <body>
  
 	<!--  市场活动配额-->
 	<hr class=dotline color=#000000 size=1>
	<section class="box-content-section youfl fl" style="width:100%;height:30px;display:none;margin-top:0px;">
		<section class="box_content_widget fl" style="width:100%;height:30px;">
			<div class="label_name" style="width:auto;">市场活动配额:</div>	
		</section>
	</section>
	<section class="box-content-section myfl fl" style="width:100%;height:30px;display:none;margin-top:0px;">
		<section class="box_content_widget myfl" style="width:100%;height:30px;">
			<div class="label_names" style="width:auto;text-align:center;font-size:19px;padding-top:22px;">暂无市场活动配额</div>	
		</section>
	</section>		
  	<%--<form id="pagerForm" name="pagerForm" action="currentRoom.do" target="_self">
	    --%><div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="date">
					<tr>
						<c:forEach items="${list}" var="name">
							<th class="header">${name["CAMNAME"]}</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<tr>
						<c:forEach items="${list}" var="count">
							<td>${count["ROOMNAME"]}：${count["COUNTNUM"]}/${count["CAMP"]}</td>
						</c:forEach>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
	<%--</form>
	--%><%@ include file="../../common/script.jsp"%>
<!-- 	<script src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script> -->
<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
  <script>
  	var mylist = "${list}";
  	mylist = mylist.split("[");
  	mylist = mylist[1].split("]");
  	if (mylist[0].length > 0) {
		$(".youfl").show();
    } else {
    	$(".myfl").show();
    }
  </script>
</html>