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
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<title>入账项目明细</title>
	</head>
	<body>
		<div class="guest_height">
			<form>
				<input type="hidden" id="startTime" name="startTime"
					class="date shop_name" value="${startTime}" placeholder="开始时间" />
				<input type="hidden" id="endTime" name="endTime"
					class="date shop_name" value="${endTime}" placeholder="结束时间" />
				<!--<input type="hidden" id="branchId" name="branchId" class="shop_name" value="${branchId}" placeholder="归属门店"/>-->
				<input type="hidden" name="checkId" id="checkId" class="shop_name"
					value="${checkId}" placeholder="账单编号" />
				<input type="hidden" id="recordType" name="recordType"
					class="shop_name" value="${recordType}" placeholder="入账类型" />
				<input type="hidden" id="projectType" name="projectType"
					class="shop_name" value="${projectType}" placeholder="项目类型" />
				<input type="hidden" id="accountStatus" name="accountStatus"
					class="shop_name" value="${accountStatus}" placeholder="账单状态" />
				<input type="hidden" id="payMent" name="payMent" class="shop_name"
					value="${payMent}" placeholder="支付方式" />
				<input type="hidden" name="recordUser" id="recordUser"
					class="shop_name" value="${recordUser}" placeholder="入账人" />

				<table id="myTable" class="myTable" border="0" width="100">
					<thead>
						<tr>
							<th class="header" style="width: 10%;">
								入账编号
							</th>
							<!--<th class="header">
									归属部门
								</th>
								-->
							<th class="header" style="width: 10%;">
								原房单号
							</th>
							<th class="header">
								房间号
							</th>
							<th class="header" style="width: 5%;">
								入账类型
							</th>
							<th class="header">
								项目编号
							</th>
							<th class="header">
								项目名称
							</th>
							<th class="header">
								金额
							</th>
							<th class="header">
								入账时间
							</th>
							<th class="header">
								入账人
							</th>
						</tr>
					</thead>
					<tbody id="tbodyInfo">
						<c:forEach items="${accountRecordList}" var="list">
							<tr class="odd">
								<td>
									${list["recordId"] }
								</td>
								<!--<td>
										${list["branchId"] }
									</td>
									-->
								<td>
									${list["checkId"] }
								</td>
								<td>
									${list["roomId"] }
								</td>
								<td>
									${list["recordType"] }
								</td>
								<td>
									${list["projectId"] }
								</td>
								<td>
									${list["projectName"] }
								</td>
								<td>
									${list["fee"] }
								</td>
								<td>
									${list["accountTime"] }
								</td>
								<td>
									${list["accountUser"] }
								</td>
							</tr>
						</c:forEach>
						<c:if test = "${lastPage == 'lastPage'}">
							<c:forEach items="${accountRecordSum}" var="list">
								<tr class="odd">
									<td>
										总计
									</td>
									<td>
	
									</td>
									<!--<td>
	
										</td>
										
										-->
									<td>
	
									</td>
									<td>
	
									</td>
									<td>
	
									</td>
									<td>
	
									</td>
									<td>
										${list["fee"] == null ?"0" :list["fee"]}
									</td>
									<td>
	
									</td>
									<td>
	
									</td>
								</tr>
							</c:forEach>
						</c:if>	
					</tbody>
				</table>

			</form>
		</div>
		<div id="pager" style=" margin-top: 68px;"></div>
	</body>
	<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/reportforms/changeRoomInfo.js" type="text/javascript" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
	 	</script>
</html>