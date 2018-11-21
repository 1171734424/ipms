<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
	<head>
	    <base href="<%=basePath%>">
	    
	    <title>账单</title>
	    
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/bill.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/orderfont.css"/>
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<style>
	.parentCls {margin:5px 60px 0;}
	.js-max-input {border: solid 1px #bfbcbc;background: white;padding: 0 10px 0 10px;left: -200px !import;top: 14px !import;font-size:20px;color: black}
	</style>
	</head>
  
	<body>
		<div class="bill_width">
	    	<input id="orderId" type="text" hidden="true" value="<%=request.getParameter("orderId")%>"/>
			<div class="bill_one">
				<section class="box_content_widget fl">
					<div class="content">	
						<table id="myTable" class="myTable" border="0">
							<thead>
								<tr>
									<th class="header"><input type="checkbox" onchange="updateallcheckbox(this)"/><span style="margin-left: 5px;">全选</span></th>
									<th class="header">账目类型</th>
									<th class="header">项目名称</th>
									<th class="header">消费</th>
									<th class="header">结算</th>
									<th class="header">描述</th>
									<th class="header">操作者</th>
									<th class="header" style="width:125px;">账目时间</th>
									<th class="header">凭证号</th>
									<th class="header">备注</th>
								</tr>
							</thead>
							<tbody id="roomlist_bill_date">
							</tbody>
						</table>
					</div>
				</section>
			</div>
			<!-- <div class="bill_two">
				<span>关联房</span>
				<ul class="clearfix fl">
					<li class="owner">
						<p id=""></p>
						<p>主客房</p>
						<p style="display: none" id="" ></p>
					</li>
				</ul>
			</div> -->
			<div class="bill_three">
				<span class="cost">消费<span class="money" id="costmoney"></span></span>
				<span class="pay">结算<span class="money" id="paymoney"></span></span>
				<span class="accountfee">帐面金额<span class="money" id="accountfeemoney"></span></span>
				<!-- <span class="">平账金额<span class="money"></span></span> -->
			</div>
			<div class="bill_four"><span class="button_margin" onclick="loadallbill()">所有明细</span></div>
			<div class="foot_submit">
				<span class="button_margin" onclick="showaddbill()">入账</span>
				<span class="button_margin" onclick="consumption()">冲减</span>
				<span class="button_margin">发票</span>
				<span class="button_margin">打印</span>
				<span class="button_margin" onclick="cancleorder()">取消订单</span>
			</div>
		</div>
	</body>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/Zoom.js"></script>
  	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/order/rightclick.js"></script> 
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  	<script>
  	var path = "<%=request.getContextPath() %>";
  	var orderId = $("#orderId").val();
  	$(document).ready(function(){
		loadorderbill();
  	});
  	
	function showaddbill(){
		if(isCheckout(orderId)){
			return false;
		}
		window.parent.JDialog.open("入账", path + "/showBillPage.do?orderId=" + orderId, 700, 320);
	}

	function loadorderbill() {
		$.ajax({
	         url: path + "/loadOrderBill.do",
			 type: "post",
			 data : {
			 	orderId : orderId,
			 	status : '1'
			 },
			 success: function(json) {
				 loadroombilldata(json.data);
				 addColor();
			 },
			 error: function(json) {}
		});
	}
	
	function loadroombilldata(json) {
		var tabledata;
	 	$.each(json, function(index) {
	 		tabledata = tabledata + "<tr class='odd' " + (json[index]["STATUS"] == 1?"":"style='color: #999'") + " onclick='aa(this)'>" +
	 		"<td><input type='checkbox'></td>" +
	 		"<td style='display: none;'>" + json[index]["BILLID"] + "</td>" +
	 		"<td>" + json[index]["BILLTYPE"] + "</td>" +
	 		"<td>" + json[index]["PROJECTNAME"] + "</td>" +
	 		"<td class='cost'>" + converttosomething(json[index]["COST"].toFixed(2), null, '') + "</td>" +
	 		"<td class='pay'>" + converttosomething(json[index]["PAY"].toFixed(2), null, '') +"</td>" +
	 		"<td class='desc'>" + json[index]["DESCRIBE"] + "</td>" +
	 		"<td>" + json[index]["STAFFNAME"] + "</td>" +
	 		"<td style='white-space:nowrap'>" + dealDate(json[index]["RECORDTIME"]) + "</td>" +
	 		"<td>" + json[index]["PAYMENT"] + "</td>"+
	 		"<td onclick='ReWriteTd(this)'  class='parentCls'>" + converttosomething(json[index]["REMARK"], null, '')  + "</td>" +
	 		"</tr>";
	 	});
	 	if (json.length == 0) {
	 		$("#roomlist_bill_date").html("");
	 	} else {
	 		$("#roomlist_bill_date").html(tabledata);
	 	}
	 	
	 	$(".odd :checkbox").click(function(e){
			 		var tr = $(this).parent().parent();
					if(tr.css("color") == "rgb(153, 153, 153)")	//没有设置style灰色属性的能选
						return false;
				   	e.stopPropagation();
				});
	 	loadpay();
	}

	function loadallbill() {
		$.ajax({
	         url: path + "/loadOrderBill.do",
			 type: "post",
			 data : {
			 	orderId : orderId,
			 	status : ''
			 },
			 success: function(json) {
			 	if(json.result == 0) {
			 		showMsg(json.message);
			 	}
				 loadroombilldata(json.data);
				 addColor(); 
			 },
			 error: function(json) {}
		});
	}
	
	function updateallcheckbox(e){
		var checkbox = document.getElementById("roomlist_bill_date");
		checkedbytruns(checkbox);
	}
			
	function aa(e) {
		if( $(e["style"]).length == 0 || $(e).css("background-color")) checkedbytruns(e);
		//$(e).find(":checkbox").attr("checked", !$(e).find(":checkbox").is(":checked"));
	}
	
	function consumption() {
		if (isCheckout(orderId)) {
			return false;
		}
		$("input[type='checkbox']").is(':checked');
		var arr = $("input[type='checkbox']");
		var strbillid = "";
		$.each(arr, function(index) {
			if (arr[index]["checked"]) {
				strbillid = strbillid + $(arr[index]["parentNode"]["parentNode"]["childNodes"][1]).html() + ",";
			}
		});
		strbillid = strbillid.substring(0,strbillid.length-1);
		if (strbillid == '') {
			showMsg("账单未勾选!");
			return false;
		}
		window.parent.JDialog.open("", path + "/page/ipmshotel/order/orderConsumption.jsp?strbillid=" + strbillid + "&orderId=" + orderId,680,360);
	}
	
	function cancleorder() {
		/**alert($("#accountfeemoney").text())
		if (!$("#accountfeemoney").text() == 0.00) {
			showMsg("账面不平,无法取消订单");
			return false;
		} else { **/
		if (isCheckout(orderId)) {
			return false;
		}
		window.parent.JDialog.open("取消订单", path + "/page/ipmshotel/order/secondquery.jsp?orderId=" + 
				orderId + "&bookvalue=" + $("#accountfeemoney").val() , 200, 100);
	}

	function loadpay(){
		$.ajax({
	         url: path + "/loadPay.do",
			 type: "post",
			 data : {orderId : orderId},
			 success: function(json) {
				 if (json.pay) {
					 $("#costmoney").html(json.cost.toFixed(2));
					 $("#paymoney").html(json.pay.toFixed(2));
					 $("#accountfeemoney").html(json.bookvalue.toFixed(2));
				 } else {
					 $("#costmoney").html("0.00");
					 $("#paymoney").html("0.00");
					 $("#accountfeemoney").html("0.00");
				 }
			 },
			 error: function(json) {}
		});
	}
	var text;
	function ReWriteTd(e){
		event.stopPropagation();
		
		if( $(e).siblings(".pay").text()<0 || $(e).siblings(".cost").text()<0){
			return;
		}else{
		var billId = $(e).siblings().eq(1).text();
		$.ajax({//查看改账单对应的订单的状态
	         url: path + "/queryOrderStatus.do",
			 type: "post",
			 data : {billId : billId
			 },
			 success: function(json) {
			 	if(json.result == "1"){
			 		showMsg(json.message);
			 		return;
			 	}
			 	if(json.result == "2"){
					//获取当前td的内容
					text = json.message;
					window.parent.JDialog.open("修改备注",path+"/orderreWriteRemark.do?text="+text+"&billId="+billId,450,200);
			 	}
			 },
			 error: function(json) {}
		});
		}	
	}
	
	function addRemark(e){
		var billId = $(e).parent().siblings().eq(1).text();
		var remark = $(e).val();
		$.ajax({
	         url: path + "/orderaddRemarkToBill.do",
			 type: "post",
			 data : {billId : billId,
			 		remark : remark
			 },
			 success: function(json) {
			 	if(json.result == "1"){
			 		$(e).parent().html($(e).val());
			 	}
			 	if(json.result == "2"){
				 	showMsg(json.message);
				 	$(e).parent().html(text);
			 	}
			 },
			 error: function(json) {}
		});
	}
	function addColor(){
		var tr = document.getElementsByClassName("odd");
		for ( var i = 0; i < tr.length; i++) {
				var td = $(tr[i]).find('.desc');
				if($(td).text()=='押金'){
					if(!$(td).parent().hasClass('style')){
						$(td).parent().css('background-color','#F7B633');
					
					}
				}
				if($(td).text()=='入账'){
					if(!$(td).parent().attr('style')){
					$(td).parent().css('background-color','#E85B48');
					
					}
			}
		}
	}
  	</script>
</html>
