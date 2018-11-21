<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	JSONObject pagination = JSONObject.fromObject(request
			.getAttribute("pagination"));
%>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>

<style type="text/css">
input.payHydropower {
	font-size: 12px;
	font-family: "微软雅黑";
	height: 30px !important;
	line-height: 26px !important;
	text-align: center;
	width: 70px !important;
	margin-left: 4px;
	border: none;
	background: #FC8A15 !important;
	color: #fff;
	cursor: pointer;
	margin-left: 10px;
}

input.payHydropower1 {
	font-size: 12px;
	font-family: "微软雅黑";
	height: 30px !important;
	line-height: 26px !important;
	text-align: center;
	width: 70px !important;
	margin-left: 4px;
	border: none;
	background: #ccc !important;
	color: #fff;
	cursor: pointer;
	margin-left: 10px;
}
</style>
</head>
<body>
	<form id="pagerForm" name="pagerForm" action="roomingHydropowerLog.do"
		target="_self">
		<div>
			<input id="type" name="type" type="hidden" value="${type}"> <input
				id="contractId" name="contractId" type="hidden"
				value="${contractId}"> <input id="roomId" name="roomId"
				type="hidden" value="${roomId}"> <input id="moblie"
				name="moblie" type="hidden" value="${moblie}"> <input
				id="leavetime" name="leavetime" type="hidden" value="${leavetime}">
				<input id="myorderId" name="myorderId" type="hidden"/>
				<input id="mymonth" name="mymonth" type="hidden"/>
		</div>
		<div>
			<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">选择</th>
						<th class="header">编号</th>
						<th class="header">合同编号</th>
						<th class="header">门店</th>
						<th class="header">房间</th>
						<th class="header">月份</th>
						<th class="header">电费用量</th>
						<th class="header">电费金额</th>
						<th class="header">水费用量</th>
						<th class="header">水费金额</th>
						<th class="header">费用合计</th>
						<th class="header">记录状态</th>
						<th class="header">操作</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list" varStatus="status">
						<tr id="hydcheck1" onclick="hydcheck(this)">
							<c:if test="${list['SATAUS'] eq '未支付'}">
								<td>
									<input name="hydcheckbox" class="hydcheckbox" type="checkbox" id="${status.index+1 }">
								</td>
							</c:if>
							<c:if test="${list['SATAUS'] ne '未支付'}">
								<td>
									<input name="hydcheckbox" class="hydcheckbox" type="checkbox" id="${status.index+1 }" onclick="return false;">
								</td>
							</c:if>
							<td style="width:2.6%;">${status.index+1 }</td>
							<td class="orderId">${list["ORDER_ID"] }</td>
							<td>${list["BRANCH_ID"] }</td>
							<td>${list["ROOM_ID"] }</td>
							<td class="month">${list["MONTH"] }</td>
							<td class="electric">${list["ELECTRIC"] }</td>
							<td class="ammeterCost">${list["AMMETERCOST"] }</td>
							<td class="water">${list["WATER"] }</td>
							<td class="waterMemterCost">${list["WATERMEMTERCOST"] }</td>
							<td class="ammount">${list["AMOUNT"] }</td>
							<td class="status">${list["SATAUS"] }</td>
							<td>
								<c:if test="${list['SATAUS'] eq '未支付'}">
									<input id="payHydropower" name="payHydropower"
									type="button" onclick="singerPay(this)" class="payHydropower"
									value="支付">
								</c:if>
								<c:if test="${list['SATAUS'] eq '已支付'}">
									<input id="payHydropower" name="payHydropower"
									type="button"  class="payHydropower1"
									value="已支付">
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/hydropower/hydropower.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
		var base_path = "<%=request.getContextPath()%>";
		
		
		/*showMsg*/
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		
		
		
		$("#info input[type='checkbox']").click(function(e) {
			if ($(this).parent().parent().children("td").eq(11).html() == "未支付") {
				// 顺序获取编号值、订单号值、月份值
				let trNo = $(this).parent().parent().children("td").eq(1).html();
				let trid = $(this).parent().parent().children("td").eq(2).html();
				let trmonth = $(this).parent().parent().children("td").eq(5).html();
	
				// 获取隐藏域中的值
				let myorderId = $("#myorderId").val();
				let mymonth = $("#mymonth").val();
				
				// 判断复选框是否被选中，如果被选中
				if (document.getElementById(trNo).checked == true) {
					
					// 所有被中：隐藏域中的值 = 隐藏域中原有值 + 上述获取对应的值
					myorderId = myorderId + trid + ",";
					mymonth = mymonth + trmonth + ",";
	
				// 如果取消选中
				} else {
					
					// 将取消选中的值赋值给“待替换的值”
					let deletegds = trid + ",";
					let deletegdMon = trmonth + ",";
					
					// 取消选中：隐藏域中的值 = 隐藏域中原有值 - 待替换的值
					myorderId = myorderId.replace(deletegds, "");
					mymonth = mymonth.replace(deletegdMon, "");
				}
				
				// 将重新获取的隐藏域中的值赋值到隐藏域中
				$("#myorderId").val(myorderId);
				$("#mymonth").val(mymonth);
				
				// 取消冒泡事件
				e.stopPropagation();
			}
		}); 

		// 复选框选中事件：未选中点击后为选中，已选中点击后为未选中状态
		
		function hydcheck(e) {
			// 获取点击行的订单号与月份
			let orderIdBefore = $(e).children("td").eq(2).html();
			let monthBefore = $(e).children("td").eq(5).html();
			
			// 获取现在隐藏域中有哪些数据
			let orderIdAfter = $("#myorderId").val();
			let monthAfter = $("#mymonth").val();
			
			// 判断状态是否已支付，如果未支付可以点击，如果已支付不可点击
			if ($(e).find(".status").text() == "未支付") {
				
				// 判断是否选中,未选中
				if (!$(e).find(".hydcheckbox").is(":checked")) {
					// 如果是未选中：隐藏域中的值 = 隐藏域中的值 + 这一行中的订单编号和月份（并加上逗号）
					orderIdAfter = orderIdAfter + orderIdBefore + ",";
					monthAfter = monthAfter + monthBefore + ",";
					// 改为已选中状态
					$(e).find(".hydcheckbox").prop("checked", true);
				// 判断您是否选中，已选中
				} else if ($(e).find(".hydcheckbox").is(":checked")) {
					// 改为取消选中状态
					$(e).find(".hydcheckbox").prop("checked", false);
					
					orderIdBefore = monthBefore + ",";
					monthBefore = monthBefore + ",";
					// 如果是已选中：隐藏域中的值 = 隐藏域中的值 - 点击行中的订单编号和月份
					orderIdAfter = orderIdAfter.replace(orderIdBefore, "");
					monthAfter = monthAfter.replace(monthBefore, "");

				}
				// 将最后获取的值存入到隐藏域中
				$("#myorderId").val(orderIdAfter);
				$("#mymonth").val(monthAfter);
			}

		}

		// 单个支付事件
		function singerPay(e) {
			// 将需要传送的数值存入到变量中
			// $(e).parent().siblings().children("input[type='checkbox']").attr("checked",true).parent().siblings().children("input[type='checkbox']").attr("checked",false);
			var orderId = $(e).parent().siblings(".orderId").text();
			var month = $(e).parent().siblings(".month").text();
			var electric = $(e).parent().siblings(".electric").text();
			var ammeterCost = $(e).parent().siblings(".ammeterCost").text();
			var water = $(e).parent().siblings(".water").text();
			var waterMemterCost = $(e).parent().siblings(".waterMemterCost")
					.text();
			var ammount = $(e).parent().siblings(".ammount").text();
			// 使用JDialog跳转至controller逻辑层
			window.parent.JDialog.open("水电支付", base_path
					+ "/singerPayMoney.do?orderId=" + orderId + "&month="
					+ month + "&electric=" + electric + "&ammeterCost="
					+ ammeterCost + "&water=" + water + "&waterMemterCost="
					+ waterMemterCost + "&ammount=" + ammount, 750, 430);
		}
	</script>
</body>
</html>
