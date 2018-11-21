<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%
	JSONObject pagination = JSONObject.fromObject(request
			.getAttribute("pagination"));
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
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
				<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<title>冲减明细</title>
	</head>
	<body>
		<div class="guest_height">
			<form>
				<input type="hidden" id="startTime" name="startTime"
					class="date shop_name" value="${startTime}" placeholder="开始时间" />
				<input type="hidden" id="endTime" name="endTime"
					class="date shop_name" value="${endTime}" placeholder="结束时间" />
				<input type="hidden" name="recordUser" id="recordUser"
					class="shop_name" value="${recordUser}" placeholder="操作人" />

				<table id="myTable" class="myTable" border="0">
					<thead>
						<tr>
							<!--<th class="header">
									原房单号
								</th>
								-->
							<th class="header">
								明细编号
							</th>
							<!--<th class="header">
									归属部门
								</th>
								-->
							<th class="header">
								房间号
							</th>
							<th class="header">
								项目编号
							</th>
							<th class="header">
								项目名称
							</th>
							<th class="header">
								描述
							</th>
							<th class="header">
								消费
							</th>
							<th class="header">
								结算
							</th>
							<th class="header">
								支付方式
							</th>
							<th class="header">
								冲减原因
							</th>
							<th class="header">
								冲减时间
							</th>
							<th class="header">
								操作人
							</th>
							<th class="header">
								冲减原单编号
							</th>
						</tr>
					</thead>
					<tbody id="tbodyInfo">
						<c:forEach items="${cancelOutDetailList}" var="list">
							<tr class="odd">
								<!--<td>
										${list["roomOrderId"] }
									</td>
									-->
								<td>
									${list["recordId"] }
								</td>
								<!--<td>
										${list["branchId"] }
									</td>
									-->
								<td>
									${list["roomId"] }
								</td>
								<td>
									${list["projectId"] }
								</td>
								<td>
									${list["projectName"] }
								</td>
								<td>
									${list["describe"] }
								</td>
								<td>
									${list["oldCost"] }
								</td>
								<td>
									${list["oldPay"] }
								</td>
								<td>
									${list["payMent"] }
								</td>
								<td>
									${list["oldoffSet"] }
								</td>
								<td>
									${list["recordTime"] }
								</td>
								<td>
									${list["recordUser"] }
								</td>
								<td>
									${list["remark"] }
								</td>
							</tr>
						</c:forEach>
						<c:if test = "${lastPage == 'lastPage'}">
							<c:forEach items="${cancelOutSum}" var="list">
								<tr class="odd">
									<td>
										总计
									</td>
									<td>
	
									</td>
									<td>
	
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
										${list["oldCost"] == null ? "0" : list["oldCost"]}
									</td>
									<td>
										${list["oldPay"] == null ? "0" : list["oldPay"]}
									</td>
									<td>
	
									</td>
									<td>
	
									</td>
									<td>
	
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
		<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script
			src="<%=request.getContextPath()%>/script/common/ui.datetimepicker.js"></script>
		<script
			src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script
			src="<%=request.getContextPath()%>/script/ipms/js/reportforms/cancelOutDetail.js"
			type="text/javascript" charset="utf-8"></script>
			<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
		
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
	 	</script>
	</body>
</html>