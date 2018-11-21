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
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/reportform/report_forms.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
				<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<title>商品售卖明细报表</title>
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
				<table id="myTable" class="myTable" border="0" width="100">
					<thead>
						<tr>
							<!--<th class="header">
									明细编号
								</th>
								-->
							<th class="header">
								商品名称
							</th>
							<th class="header" style="width: 10%;">
								商品分类
							</th>
							<!--<th class="header">
									规格
								</th>
								-->
							<th class="header">
								单位
							</th>
							<th class="header">
								挂账类型
							</th>
							<th class="header">
								原房单号
							</th>
							<th class="header">
								房间号
							</th>
							<!--<th class="header">
									商品编号
								</th>
								-->
							<th class="header">
								售卖类型
							</th>
							<th class="header">
								单价
							</th>
							<th class="header">
								数量
							</th>
							<th class="header">
								总额
							</th>
							<th class="header">
								支付方式
							</th>
							<!--<th class="header">
									操作时间
								</th>
								<th class="header">
									操作人
								</th>
								-->
							<th class="header">
								备注
							</th>
						</tr>
					</thead>
					<tbody id="tbodyInfo">
						<c:forEach items="${goodsSaleDetailList}" var="list">
							<tr class="odd">
								<!--<td>
										${list["logId"] }
									</td>
									-->
								<td>
									${list["goodsName"] }
								</td>
								<td>
									${list["categoryId"] }
								</td>
								<!--<td>
										${list["specifications"] }
									</td>
									-->
								<td>
									${list["unit"] }
								</td>
								<td>
									${list["debts"] }
								</td>
								<td>
									${list["checkId"] }
								</td>
								<td>
									${list["roomId"] }
								</td>
								<!--<td>
										${list["goodId"] }
									</td>
									-->
								<td>
									${list["saleType"] }
								</td>
								<td>
									${list["price"] }
								</td>
								<td>
									${list["amount"] }
								</td>
								<td>
									${list["totalPrice"] }
								</td>
								<td>
									${list["payType"] }
								</td>
								<!--<td>
										${list["recordTime"] }
									</td>
									<td>
										${list["recordUser"] }
									</td>
									-->
								<td>
									${list["remark"] }
								</td>
							</tr>
						</c:forEach>
						<c:if test = "${lastPage == 'lastPage'}">
							<c:forEach items="${goodsSaleSum}" var="list">
								<tr class="odd">
									<!--<td>
											
										</td>
										-->
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
									<!--<td>
											
										</td>
										-->
									<td>
	
									</td>
									<td>
	
									</td>
									<td>
										${list["sumAmount"] }
									</td>
									<td>
										${list["sumTotalPrice"] }
									</td>
									<td>
	
									</td>
									<!--<td>
											
										</td>
										<td>
											
										</td>
										-->
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
			src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script
			src="<%=request.getContextPath()%>/script/common/ui.datetimepicker.js"></script>
		<script
			src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script
			src="<%=request.getContextPath()%>/script/ipms/js/reportforms/goodsSaleDetail.js"
			type="text/javascript" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
	 	</script>
	</body>
</html>