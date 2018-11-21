<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/repair.css"/>
  </head>
  <body>
   <div class="condition_div">
  	   <form id="pagerForm" name="pagerForm" action="" target="_self">
  	   <input type="hidden"  name ="startdate" value="${startdate}">
  	   <input type="hidden"  name ="enddate" value="${enddate}">
  	   <input type="hidden"  name ="roomid" value="${roomid}">
  	   <input type="hidden"  name ="equipment" value="${equipment}">
  	   <input type="hidden"  name ="checkstatus" value="${checkstatus}">
  	   <input type="hidden"  name ="mobile" value="${mobile}">
	    <div class="div_part_one">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">查看日志</th>
						<th class="header" style="display:none">申请编号</th>
						<th class="header" style="display:none">合同编号</th>
						<th class="header" style="display:none">日志编号</th>
						<c:if test="${flag =='1'}">
						<th class="header">民宿名称</th>
						</c:if>
						<th class="header">房号</th>
						<th class="header">申请人</th>
						<th class="header">手机号码</th>
						<th class="header">申请日期</th>
						<th class="header">状态</th>
						<th class="header" style="display:none">审核进度</th>
						<th class="header" style="display:none">审核备注</th>
						<th class="header">维修类型</th>
						<th class="header">备注</th>
						<th class="header">操作</th>
						<th class="header" style="display:none">设备编号</th>
					</tr>
				</thead>
				<tbody id="info">
					
					<c:forEach items="${result}" var="re">
						<tr ondblclick="showDetail(this)">
							<td >
								<input id="detailbtn" type="button" value="详情" name="detailbtn" onclick="showLog(this)"/>
							</td>
							<td style="display:none">${re["repairApplyId"]}</td>
							<td style="display:none">${re["contractId"]}</td>
							<c:if test="${flag =='1'}">
								<td>${re["names"]}</td>
							</c:if>
							<td >${re["roomId"]}</td>
							<c:if test="${re['reservedPerson'] == null}">
							<td>admin</td>
							</c:if>
							<c:if test="${re['reservedPerson'] != null}">
							<td>${re["reservedPerson"]}</td>
							</c:if>
							<td>${re["mobile"]}</td>
							<td><fmt:formatDate type="time" value="${re['applicationDate']}" pattern="yyyy/MM/dd" />
							</td>
							<td class="status">${re["status"]}</td>
							<td style="display:none" class="post">${re["post"]}</td><!--标记审核进度,多级审核  -->
							<td style="display:none" class="auditremark">${re["auditRemark"]}</td><!--记录多级审核备注 -->
							<td>${re["equipment"]}</td>
							<c:if test="${fn:length(re['remark'])>10}">	
							 	<td title='${re["remark"]}'>${fn:substring(re["remark"],0,10)}...</td>
							</c:if>
							<c:if test="${fn:length(re['remark'])<=10}">	
							 	<td title='${re["remark"]}'>${re["remark"]}</td>
							</c:if>							<td>
								<input id="cancel" type="button" class="addClass addone" value="撤销" onclick="cancelApplication(this)"/>
								<input id="Done" type="button" class="addClass addtwo" value="修复" onclick="doneApplication(this)"/>
							</td>
							<td style="display:none" class="serialNo">${re["serialNo"]}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
	</form>
  </div>
	
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipmsinhouse/leftmenu/goodssale.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipmsinhouse/leftmenu/repair.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipmsinhouse/leftmenu/onloadrepair.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
		var base_path = "<%=request.getContextPath()%>";
	    var path = "<%=request.getContextPath()%>";
	</script>
  </body>
</html>
