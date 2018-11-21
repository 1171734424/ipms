<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>工作帐</title>
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/bill.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/orderfont.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
  </head>
  
  <body>
   	<div class="bill_width">
			<div class="bill_one">
				<section class="box_content_widget fl">
					<div class="content">
						<table id="myTable" class="myTable" border="0">
							<thead>
								<tr>
									<th class="header">选择</th>
									<th class="header" style="width:10%;display: none;">交易号</th>
									<th class="header">项目</th>
									<th class="header">项目名称</th>
									<th class="header">消费</th>
									<th class="header">结算</th>
									<th class="header">时间</th>
									<th class="header">操作者</th>
									<th class="header">凭证号</th>
									<th class="header" style="width:14%;">备注</th>
								</tr>
							</thead>
							<tbody id="workbill_data">
							</tbody>
						</table>
					</div>
				</section>
			</div>
			<div class="bill_three">
				<span class="">消费<span id="cost">0.00</span></span>
				<span class="">结算<span id="pay">0.00</span></span>
				<span class="">帐面余额<span id="accountfee">0.00</span></span>
			</div>
			<div class="bill_four">
				<span class="button_margin" onclick="loadallworkbill()">所有明细</span>
			</div>
			<div class="foot_submit">
				<span class="button_margin" onclick="showaddbill()">入账</span>
				<span class="button_margin" onclick="consumption()">冲减</span>
				<span class="button_margin">发票</span>
				<span class="button_margin">打印</span>
				<span class="button_margin" onclick="checkout()">结账</span>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
		var workbillid = '<%=request.getParameter("workbillid")%>';
			$(function(){
				$.ajax({
			         url: path + "/loadWorkBillData.do",
					 type: "post",
					 data : {
					 	workbillid : workbillid,
					 	status : "1"
					 },
					 success: function(json) {
						 loadworkbilldata(json);
					 },
					 error: function(json) {}
				});
				
				$.ajax({
			         url: path + "/loadpayandcostworkbill.do",
					 type: "post",
					 data : {workbillid : workbillid},
					 success: function(json) {
						 $("#cost").html(json["cost"]);
						 $("#pay").html(json["pay"]);
						 $("#accountfee").html((json["pay"] - json["cost"]).toFixed(2));
					 	
					 },
					 error: function(json) {}
				});
				
			});
			
			function showaddbill(){
				if(isWorkbillCheckout(workbillid)){
					return false;
				}
				
				window.parent.JDialog.open("入账", path + "/showgetAddWorkBill.do?workbillid=" + workbillid , 700, 400);
			}
			
			function loadallworkbill(){
				$.ajax({
			         url: path + "/loadWorkBillData.do",
					 type: "post",
					 data : {
					 	workbillid : workbillid,
					 	status : ''
					 },
					 success: function(json) {
					 	if(json.result == 0){
					 		showMsg(json.message);
					 	}
						 loadworkbilldata(json);
					 },
					 error: function(json) {}
				});
			}
			
			function loadworkbilldata(json){
				 var tabledata;
			 	$.each(json, function(index){
			 		tabledata = tabledata + "<tr class='odd' " + (json[index]["STATUS"] == 1?"":"style='color: #999'") + " onclick='aa(this)'>" +
			 		"<td><input class = 'checkbox' type='checkbox'></td>" +
			 		"<td style='display: none;'>" + json[index]["DETAILID"] + "</td>" +
			 		"<td>" + json[index]["PROJECTNAME"] + "</td>" +
			 		"<td>" + json[index]["DESCRIBE"] + "</td>" +
			 		"<td class='cost'>" + converttosomething(json[index]["COST"], null, '') + "</td>" +
			 		"<td class='pay'>" + converttosomething(json[index]["PAY"], null, '') +"</td>" +
			 		"<td>" + dealDate(json[index]["RECORDTIME"]) + "</td>" +
			 		"<td>" + json[index]["STAFFNAME"] + "</td>" +
			 		"<td>" + converttosomething(json[index]["VOUCHER"], null, '') + "</td>"+
			 		"<td onclick='ReWriteTd(this)'>" + converttosomething(json[index]["REMARK"], null, '')  + "</td>" +
			 		"</tr>";
			 	});
			 	if(json.length == 0){
			 		$("#workbill_data").html("");
			 	}else{
			 		$("#workbill_data").html(tabledata);			 	
			 	}
			 	$("input[type='checkbox']").click(function(e){  
				    e.stopPropagation();
				});
			}
			
			function consumption(){
				if(isWorkbillCheckout(workbillid)){
					return false;
				}
				$("input[type='checkbox']").is(':checked');
				var arr = $("input[type='checkbox']");
				var strdetailid = "";
				$.each(arr, function(index){
					if(arr[index]["checked"]){
						strdetailid = strdetailid + $(arr[index]["parentNode"]["parentNode"]["childNodes"][1]).html() + ",";
					}
				});
				strdetailid = strdetailid.substring(0,strdetailid.length-1);
				if(strdetailid == ''){
					showMsg("账单未勾选!");
					return false;
				}
				$.ajax({
			         url: path + "/checkAllRights.do",
					 type: "post",
					 data : {
			        	 strdetailid : strdetailid,
					 },
					 success: function(json) {
					 	if (json.result == 1) {
					 		JDialog.open("", path + "/page/ipmshotel/work_account/consumptionworkbill.jsp?strdetailid=" + strdetailid,680,360);
					 	} else {
						 	showMsg(json.message);
						}
					 },
					 error: function(json) {}
				});
			}
			
			function checkout(){
			 	var cost = +($("#cost").html());
			 	var pay = +($("#pay").html());
			 	if (cost != pay) {
			 		showMsg("无法结账，账面金额不平！");
			 		return false;
			 	}
				if(isWorkbillCheckout(workbillid)){
					return false;
				}
				$.ajax({
			         url: path + "/checkoutworkbill.do",
					 type: "post",
					 data : {workbillid : workbillid},
					 success: function(json) {
						 if(json.result == 1){
							 showMsg(json.message);
							 setTimeout("refreshPage()", 2000)
						 }
					 },
					 error: function(json) {}
				});
			}
			
			function aa(e) {
				if( $(e["style"]).length == 0) 
					checkedbytruns(e);
				//$(e).find(":checkbox").attr("checked", !$(e).find(":checkbox").is(":checked"));
			}
			
			function refreshPage(){
				//$(window.parent.parent.document.all[155].contentDocument.logFrame.document.forms[0]).submit();
				$(window.parent.parent.document.all.menu_908).click();
				window.parent.parent.JDialog.close();
			}
			
			function goods(){
				window.parent.JDialog.open("", path + "/goods.do",1280,738);
			}
			
			function ReWriteTd(e){
				if( $(e).siblings(".pay").text()<0 || $(e).siblings(".cost").text()<0){
					return;
				}else{
					event.stopPropagation();
					var billId = $(e).siblings().eq(1).text();
				$.ajax({//查找账单的备注信息
			         url: path + "/queryWorkbillRemark.do",
					 type: "post",
					 data : {billId : billId
					 },
					 success: function(json) {
					 	if(json.result == "2"){
							//获取当前td的内容
							text = json.message;
							window.parent.JDialog.open("修改备注",path+"/reWriteWKRemark.do?text="+text+"&billId="+billId,450,200);
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
			         url: path + "/addRemarkToBill.do",
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
			
			
			
		</script>
  </body>
</html>
