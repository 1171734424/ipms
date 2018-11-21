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
			href="<%=request.getContextPath()%>/css/ipms/css/reportform/report_forms.css"/>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
				<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<title>入账项目汇总表</title>
	</head>
	<body>
		<div class="guest_height">
			<form>
				<input type="hidden" id="startTime" name="startTime" class="date shop_name" value="${startTime}" placeholder="开始时间"/>
				<input type="hidden" id="endTime" name="endTime" class="date shop_name" value="${endTime}" placeholder="结束时间"/>
					<table id="myTable" class="myTable" border="0" width="100">
						<thead>
							<tr>
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
									数量
								</th>
							</tr>
						</thead>
						<tbody id="tbodyInfo">
							<c:forEach items="${accountRecordList}" var="list">
								<tr class="odd">
									<td>
										${list["CONTENT"] }
									</td>
									<td>
										${list["PARAMNAME"]}
									</td>
									<td>
										${list["FEE"]==null ?"0" :list["FEE"] }
									</td>
									<td>
										${list["COUNTNUM"] }
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
										<td>
											${list["FEE"] ==null ?"0" :list["FEE"] }
										</td>
										
										<td>
											${list["countNum"] }
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
		<script src="<%=request.getContextPath()%>/script/common/ui.datetimepicker.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/reportforms/accountRecordTotal.js" type="text/javascript" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
	 	</script>
	</body>
</html>