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
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reportform/report_forms.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<title>在住客人明细报表</title>
	</head>
	<body>
		<div class="guest_height">
			<form>
				<input type="hidden" name="startTime" id="startTime"
					class="date shop_name" value="${startTime}" placeholder="开始时间" />
				<input type="hidden" name="endTime" id="endTime"
					class="date shop_name" value="${endTime}" placeholder="结束时间" />
				<input type="hidden" name="checkId" id="checkId" class="shop_name"
					value="${checkId}" placeholder="房单编号" />
				<input type="hidden" name="status" id="status" class="shop_name"
					value="${status}" placeholder="状态" />
				<input type="hidden" name="checkUser" id="checkUser"
					class="shop_name" value="${checkUser}" placeholder="客人名称" />
				<input type="hidden" name="recordUser" id="recordUser"
					class="shop_name" value="${recordUser}" placeholder="操作员" />
				<table id="myTable" class="myTable" border="0" width="100">
					<thead>
						<tr>
							<!--<th class="header">
						日志编号
					</th>
					<th class="header">
						归属门店
					</th>
					-->
							<th class="header">
								房单编号
							</th>
							<th class="header">
								入住人
							</th>
							<th class="header">
								房单状态
							</th>
							<th class="header">
								房价码(前)
							</th>
							<th class="header">
								房间类型(前)
							</th>
							<th class="header">
								房间编号(前)
							</th>
							<th class="header">
								房间价格(前)
							</th>
							<th class="header">
								房价码(后)
							</th>
							<th class="header">
								房间类型(后)
							</th>
							<th class="header">
								房间编号(后)
							</th>
							<th class="header">
								房间价格(后)
							</th>
							<th class="header">
								记录时间
							</th>
							<th class="header">
								录入人
							</th>
						</tr>
					</thead>
					<tbody id="tbodyInfo">
						<c:forEach items="${changeRoomList}" var="list">
							<tr class="odd">
								<!--<td>
							${list["logId"] }
						</td>
						<td>
							${list["branchId"] }
						</td>
						-->
								<td>
									${list["checkId"] }
								</td>
								<td>
									${list["checkUser"] }
								</td>
								<td>
									${list["status"] }
								</td>
								<td>
									${list["rpId"] }
								</td>
								<td>
									${list["lastRoomType"] }
								</td>
								<td>
									${list["lastRoomId"] }
								</td>
								<td>
									${list["lastRoomPrice"] }
								</td>
								<td>
									${list["currRpId"] }
								</td>
								<td>
									${list["currRoomType"] }
								</td>
								<td>
									${list["currRoomId"] }
								</td>
								<td>
									${list["currRoomPrice"] }
								</td>
								<td>
									${list["recordTime"] }
								</td>
								<td>
									${list["recordUser"] }
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<div id="pager"></div>
	</body>
	<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipmshotel/reportforms/changeRoomInfo.js" type="text/javascript" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
			$(document).ready(function(){
			  
		    })
	 	</script>
</html>