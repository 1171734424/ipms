<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/common/jquery-ui.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<title>在住客人数据</title>
	</head>
	<body style="">
		<div class="guest_height">
			<form>
				<input type="hidden" name="roomType" id="roomType" class="shop_name"
					value="${roomType}" placeholder="房间类型" />
				<input type="hidden" name="roomType_CUSTOM_VALUE"
					id="roomType_CUSTOM_VALUE" class="shop_name"
					value="${roomType_CUSTOM_VALUE}" />
				<input type="hidden" name="roomId" id="roomId" class="shop_name"
					value="${roomId}" placeholder="房间号" />
				<input type="hidden" name="checkUser" id="checkUser"
					class="shop_name" value="${checkUser}" placeholder="客人名称" />
				<table id="myTable" class="myTable" border="0" width="100">
					<thead>
						<tr>
							<th class="header" style="width:8%;">
								房单编号
							</th>
							<!--<th class="header">
									归属门店
								</th>
								-->
							<th class="header">
								房间号
							</th>
							<th class="header">
								房间类型
							</th>
							<th class="header">
								房价码
							</th>
							<th class="header">
								房间价格
							</th>
							<th class="header" style="width:9%;">
								入住人
							</th>
							<th class="header" style="width: 10%;">
								入住时间
							</th>
							<th class="header" style="width: 10%;">
								离店时间
							</th>
							<th class="header">
								押金
							</th>
							<!--<th class="header">
								ttv
							</th>
							--><th class="header">
								消费
							</th>
							<th class="header">
									结算
							</th>
							<th class="header">
								账面金额
							</th>
							<!--<th class="header">
								平面金额
							</th>
							--><!--<th class="header">
									结账方式
								</th>
								<th class="header">
									结账信息
								</th>
								<th class="header">
									支付主体
								</th>
								-->
							<th class="header">
								转单号
							</th>
							<!--<th class="header">
									记录时间
								</th>
								<th class="header">
									记录人
								</th>
								-->
							<th class="header" style="width: 11%;">
								备注
							</th>
						</tr>
					</thead>
					<tbody id="tbodyInfo">
						<c:forEach items="${roomingGuestsList}" var="list">
							<tr class="odd" id="${list['checkId']}">
								<td>
									${list["checkId"] }
								</td>
								<!--<td>
										${list["branchId"] }
									</td>
									-->
								<td>
									${list["roomId"] }
								</td>
								<td>
									${list["roomType"] }
								</td>
								<td>
									${list["rpId"] }
								</td>
								<td>
									${list["roomPrice"] }
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
									${list["deposit"] }
								</td>
								<!--<td>
									${list["ttv"] }
								</td>
								--><td>
									${list["currentcost"] }
								</td>
								<td>
									${list["pay"] }
								</td>
								<td>
									${list["accountFee"] }
								</td>
								<!--<td>
									${list["totalFee"] }
								</td>
								<td>
									${list["payType"] }
								</td>
								<td>
									${list["payInfo"] }
								</td>
								<td>
									${list["payer"] }
								</td>
								-->
								<td>
									${list["switchId"] }
								</td>
								<!--<td>
										${list["recordTime"] }
									</td>
									<td>
										${list["recordUser"] }
									</td>
									-->
								<td style="white-space: normal;text-overflow: ellipsis;overflow: hidden;text-align: left;">
									${list["remark"] }
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
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script
		src="<%=request.getContextPath()%>/script/ipmshotel/reportforms/roomingGuests.js"
		charset="utf-8"></script>
	<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
	</script>
</html>