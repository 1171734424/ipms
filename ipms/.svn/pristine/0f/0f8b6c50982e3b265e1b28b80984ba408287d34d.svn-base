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
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagination.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<title>在住客人明细</title>
	</head>
	<body>
		<div class="member_margin">
			<form action="#" method="post" id = "myForm">
			  <section class="box-content-section padding  fl">
				<section class="box_content_widget fl">
				  <div class="content">
					<table id="myTable" class="myTable" border="0" width="100">
						<thead>
							<tr>
								<th class="header">
									客人名称
								</th>
								<th class="header">
									客人类型
								</th>
								<th class="header" style="width: 14%;">
									身份证
								</th>
								<th class="header">
									性别
								</th>
								<th class="header">
									手机号
								</th>
								<th class="header">
									状态
								</th>
								<th class="header"  style="width: 11%;">
									生日
								</th>
								<th class="header">
									入住时间
								</th>
								<th class="header">
									会员等级
								</th>
								<th class="header">
									有效截止
								</th>
							</tr>
						</thead>
						<tbody id="tbodyInfo">
							<c:forEach items="${roomGuestDetailLists}" var="list">
								<tr class="odd" id="${list['checkuser_id']}">
									<td class="header">
										${list["checkuser_name"] }
									</td>
									<td class="header">
										${list["checkusertype"] }
									</td>
									<td class="header" style="width: 14%;">
										${list["idcard"] }
									</td>
									<td class="header">
										${list["gendor"] }
									</td>
									<td class="header">
										${list["mobile"] }
									</td>
									<td class="header">
										${list["livestatus"] }
									</td>
									<td class="header"  style="width: 11%;">
										${list["birthday"] }
									</td>
									<td class="header">
										${fn:substring(list["record_time"],0,19) }
									</td>
									<td class="header">
										${list["memberrank"] }
									</td>
									<td class="header">
										${fn:substring(list["invalid_time"],0,19) }
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				 </div>
			   <div id="pager"></div>
			 </section>
			</section>	
		   </form>
		</div>
		<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/reportforms/roomingGuests.js" type="text/javascript" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
	 	</script>
	</body>
</html>