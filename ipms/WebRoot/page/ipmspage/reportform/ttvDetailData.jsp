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
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
				<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<title>ttv明细报表</title>
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
				<input type="hidden" name="status" id="" status"" class="shop_name"
					value="${status}" placeholder="状态" />
				<table id="myTable" class="myTable" border="0" width="100">
					<thead>
						<tr>
							<th class="header">
								房单号
							</th>
							<th class="header">
								门店号
							</th>
							<th class="header">
								房间价格
							</th>
							<th class="header">
								房间类型
							</th>
							<th class="header">
								入住人
							</th>
							<th class="header">
								入住时间
							</th>
							<th class="header">
								离店时间
							</th>
							<th class="header">
								消费
							</th>
							<th class="header">
								TTV
							</th>
							<th class="header">
								状态
							</th>
							<th class="header">
								操作时间
							</th>
							<th class="header">
								操作人
							</th>
							<th class="header">
								备注
							</th>
						</tr>
					</thead>
					<tbody id="tbodyInfo">
						<c:forEach items="${ttvDetailList}" var="list">
							<tr class="odd">
								<td>
									${list["CHECKID"] }
								</td>
								<td>
									${list["branchId"] }
								</td>
								<td>
									${list["roomPrice"] }
								</td>
								<td>
									${list["roomType"] }
								</td>
								<td>
									${list["checkUser"] }
								</td>
								<td>
									${list["checkInTime"] }
								</td>
								<td>
									${list["checkOutTime"] }
								</td>
								<td>
									${list["oldCost"] == null ? "0": list["oldCost"] }
								</td>
								<td>
									${list["ttv"] == null ? "0": list["ttv"]}
								</td>
								<td>
									${list["status"] }
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
							<c:forEach items="${ttvDetailSum}" var="list">
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
									<td>
	
									</td>
									<td>
	
									</td>
									<td>
	
									</td>
									<td>
										${list["oldCost"]== null ? "0": list["oldCost"]}
									</td>
									<td>
										${list["ttv"] == null ? "0": list["ttv"]}
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
		<div id="pager"></div>
		<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script
			src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script
			src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script
			src="<%=request.getContextPath()%>/script/ipms/js/reportforms/ttvDetail.js"
			type="text/javascript" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
	 	</script>
	</body>
</html>