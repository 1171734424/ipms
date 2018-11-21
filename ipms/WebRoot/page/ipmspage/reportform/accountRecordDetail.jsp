<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/reportform/report_forms.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<title>入账项目明细报表</title>
	</head>
	<body style="overflow:hidden;">
		<div class="shop_search">
			<form action="accountRecordDetail.do" method="post" id="myForm">
			    <div class="form_margin">
					<input type="text" id="startTime" name="startTime" class="date shop_name" value="${startTime}" placeholder="开始时间" readonly/>
					<input type="text" id="endTime" name="endTime" class="date shop_name" value="${endTime}" placeholder="结束时间"/>
					<!--<input type="text" id="branchId" name="branchId" class="shop_name" value="${branchId}" placeholder="归属门店"/>
					--><input type="text" name="checkId" id="checkId" class="shop_name" value="${checkId}" placeholder="房单编号" />
					<!--<input type="text" id="recordType" name="recordType" class="shop_name" value="${recordType}" placeholder="入账类型"/>
					<input type="text" id="accountStatus" name="accountStatus" class="shop_name" value="${accountStatus}" placeholder="账单状态"/>
					<input type="text" id="payMent" name="payMent" class="shop_name" value="${payMent}" placeholder="支付方式"/>
					-->
					<select name="recordType" id="recordType" class="search_select">
						<option value="">入账类型</option>
						<option value="1">账单入账</option>
						<option value="2">工作账入账</option>
					</select>
					<select name="projectType" id="projectType" class="search_select">
						<option value="">项目类型</option>
						<option value="1">消费</option>
						<option value="2">结算</option>
					</select>
					<!--<select name="accountStatus" id="accountStatus" class="search_select">
						<option value="">账单状态</option>
						<c:forEach items="${billStatusList}" var="rl">
							<option value="${rl.paramName}">
								${rl.paramName}
							</option>
						</c:forEach>
					</select>
					<select name="payMent" id="payMent" class="search_select">
						<option value="">支付方式</option>
						<c:forEach items="${payMentList}" var="rl">
							<option value="${rl.paramName}">
								${rl.paramName}
							</option>
						</c:forEach>
					</select>
					--><input type="text" name="recordUser" id="recordUser" class="shop_name" value="${recordUser}" placeholder="入账人" />
					<button type="button" class="btn_style btn_search button_margin" onclick="search();">
						查询
					</button>
				</div>
				<section class="box-content-section fl">
					<section class="box_content_widget fl">
						<div class="content">
							<iframe name="frame" id="frame" class="myTable" frameborder="0" width="100%" height="100%" ></iframe>
						</div>	
					</section>
				</section>
			</form>
		</div>
		<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/reportforms/accountRecordDetail.js" type="text/javascript" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
			var recordType ="${recordType}";
			var accountStatus ="${accountStatus}";
			var payMent ="${payMent}";
			var projectType ="${projectType}";
			$(document).ready(function(){
				$("#recordType").val(recordType);
				$("#accountStatus").val(accountStatus);
				$("#payMent").val(payMent);
				$("#projectType").val(projectType);
				$("#frame").attr('src',"accountRecordDetailData.do?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&checkId="+$("#checkId").val()+"&recordType="+$("#recordType").val()+"&recordUser="+$("#recordUser").val()+"&projectType="+$("#projectType").val());
		    })
	 	</script>
	</body>
</html>