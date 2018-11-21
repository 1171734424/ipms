<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/bill.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/orderfont.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<style>
	.parentCls {margin:5px 60px 0;}
	.js-max-input {border: solid 1px #bfbcbc;background: white;padding: 0 10px 0 10px;left: -200px !import;top: 14px !import;font-size:20px;color: black}
	</style>
  </head>
  
  <body>
    <div class="bill_width">
			<div class="bill_one apartment_bill">
				<section class="box_content_widget fl">
					<div class="content" style="padding:0;">
						<table id="myTable" class="myTable" border="0">
							<thead>
								<tr>
									<th class="header" id="all">
										<input type="checkbox" onchange="updateallcheckbox(this)"/><span style="margin-left: 5px;">全选</span>
									</th>
									<th class="header">账目类型</th>
									<th class="header">项目名称</th>
									<th class="header">消费</th>
									<th class="header">结算</th>
									<th class="header">描述</th>
									<th class="header">操作者</th>
									<th class="header" style="width:90px;">账目时间</th>
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
			<div class="bill_three">
				<%--<span class="">TTV<span class="money" id="TTV">0.00</span></span>
				--%><span class="">消费<span class="money" id="cost">0.00</span></span>
				<span class="">结算<span class="money" id="pay">0.00</span></span>
				<span class="">帐面余额<span class="money" id="accountfee">1.00</span></span>
			</div>
			<div class="foot_submit" id="foot_submit" style="margin-top: 20px;">
				<span class="button_margin" onclick="showaddbill()">入账</span>
				<span class="button_margin" onclick="consumption()">冲减</span>
				<span class="button_margin" onclick="checkout()">结账</span>
				<span class="button_margin" onclick="loadallbill()">所有明细</span>
			</div>
		 </div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/apartment/apartmentLeftmenu/apartmentrefund/util.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/apartment/apartmentLeftmenu/apartmentrefund/Zoom.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
		var checkid = '<%=request.getParameter("checkid")%>';
		var status = '<%=request.getParameter("status")%>';
			$(function(){
				loadbill();
				loadpayandcost();
				//loadroommapping();
				$(".bill_two ul:first li").addClass("active");
				if(status == '1'){
					$("#all").hide();
					$("#foot_submit").hide();
				}
			});
			
			function updateallcheckbox(e){
				//var checkbox = document.getElementById("roomlist_bill_date");
				var checked = $(e).prop('checked');
				var checkedtds = $("td input");
				if(checked){
					$.each(checkedtds, function(index){
						var tr = $(checkedtds[index]).parent().parent();
						if(tr.css("color") == "rgb(0, 0, 0)" )	//没有设置style灰色属性的能选
							$(checkedtds[index]).prop("checked", true);
					});
				}
				else{
					$.each(checkedtds, function(index){
						var tr = $(checkedtds[index]).parent().parent();
						if(tr.css("color") == "rgb(0, 0, 0)"  )	//没有设置style灰色属性的能选
							$(checkedtds[index]).prop("checked", false);
					});
				}
			}
			
			function refershbill(){
				loadbill();
				loadpayandcost();
			}
			
			function loadpayandcost(){
				$.ajax({
			         url: path + "/loadApartmentPayandCost.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
						 $("#cost").html(json.cost.toFixed(2));
						 $("#pay").html(json.pay.toFixed(2));
						 $("#accountfee").html(( json.cost - json.pay ).toFixed(2));
					 },
					 error: function(json) {}
				});
			}
			
			function loadroommapping(){
				$.ajax({
			         url: path + "/apartmentloadroommapping.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					     loadrooommapingdata(json);
						 $(".owner").on("click",function(){
							 $(this).addClass("active");
							 $(".bill_two .owner").not(this).removeClass("active");
							 checkid = $(this).find("p:hidden").html();
							 refershbill();
						});
					 },
					 error: function(json) {}
				});
			}
			
			function loadrooommapingdata(json){
				var data = "";
			 	$.each(json, function(index){
			 		data = data + "<li class='owner'><p>" + json[index]["ROOMID"] + "</p>" + 
			 				"<p>查看</p>" +
			 				"<p style='display: none;'>" + json[index]["CHECKID"] + "</p></li>"
			 	});
			 	if(json.length == 0){
			 		$("#roommapping_data").html("");
			 	}else{
			 		$("#roommapping_data").html(data);		 	
			 	}
			}
			
			function loadbill(){
				$.ajax({
			         url: path + "/apartmentloadRoomBillData.do",
					 type: "post",
					 data : {
					 	checkid : checkid,
					 	status : '1'
					 },
					 success: function(json) {
						 loadroombilldata(json.data);
						 addColor();
					 },
					 error: function(json) {}
				});
			}
			
			function loadallbill(){
				$.ajax({
			         url: path + "/apartmentloadRoomBillData.do",
					 type: "post",
					 data : {
					 	checkid : checkid,
					 	status : '1,2,3,4,0'
					 },
					 success: function(json) {
					 	if(json.result == 0){
					 		showMsg(json.message);
					 	}
						 loadroombilldata(json.data);
						 addColor();
					 },
					 error: function(json) {}
				});
			}
			
			function showaddbill(){
				if(isCheckout(checkid)){
					return false;
				}
				window.parent.JDialog.open("入账", path + "/showApartmentAddBill.do?checkid=" + checkid , 700, 350);
			}
			
			function transferbill(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				$.ajax({
			         url: path + "/checkApartmappingnull.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	if(json.result == 1){
							window.parent.JDialog.open("转账", path + "/showTransferBill.do?checkid=" + checkid , 700, 400);
					 	}else if(json.result == 0){
					 		showMsg(json.message);
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			function checkroommappingnull(){
				
			}
			
			function autotransferbill(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				$.ajax({
			         url: path + "/autoautotransferbill.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
						 showMsg(json.message);
					 },
					 error: function(json) {}
				});
			}
			
			function loadroombilldata(json){
				var tabledata;
			 	$.each(json, function(index){
			 		if(status == '1'){
			 			tabledata = tabledata + "<tr class='odd'> "+
				 		"<td style='display: none;'>" + json[index]["BILLID"] + "</td>" +
				 		"<td>" + json[index]["BILLTYPE"] + "</td>" +
				 		"<td>" + json[index]["PROJECTNAME"] + "</td>" +
				 		"<td class='cost'>" + converttosomething(json[index]["COST"].toFixed(2), null, '') + "</td>" +
				 		"<td class='pay'>" + converttosomething(json[index]["PAY"].toFixed(2), null, '') +"</td>" +
				 		"<td class='desc'>" + json[index]["DESCRIBE"] + "</td>" +
				 		"<td>" + json[index]["STAFFNAME"] + "</td>" +
				 		"<td style='white-space:nowrap'>" + dealDate(json[index]["RECORDTIME"]) + "</td>" +
				 		"<td>" + json[index]["PAYMENT"] + "</td>"+
				 		"<td class='refund' style='display:none'>" + json[index]["REFUNDSTATUS"] + "</td>"+
				 		"<td onclick='ReWriteTd(this)' class='parentCls'>" + converttosomething(json[index]["REMARK"], null, '')  + "</td>" +
				 		"</tr>";
			 		} else {
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
				 		"<td class='refund' style='display:none'>" + json[index]["REFUNDSTATUS"] + "</td>"+
				 		"<td onclick='ReWriteTd(this)' class='parentCls'>" + converttosomething(json[index]["REMARK"], null, '')  + "</td>" +
				 		"</tr>";
			 		}
			 	});
			 	if(json.length == 0){
			 		$("#roomlist_bill_date").html("");
			 	}else{
			 		$("#roomlist_bill_date").html(tabledata);			 	
			 	}
			 	$(".odd :checkbox").click(function(e){
			 		var tr = $(this).parent().parent();
					if(tr.css("color") == "rgb(153, 153, 153)")	//没有设置style灰色属性的能选
						return false;
				   	e.stopPropagation();
				});
			}
			function consumption(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				$("input[type='checkbox']").is(':checked');
				var arr = $("input[type='checkbox']");
				var strbillid = "";
				$.each(arr, function(index){
					if(arr[index]["checked"]){
						if($(arr[index]["parentNode"]["parentNode"]["childNodes"][1]).find("span").length != 1){
							strbillid = strbillid + $(arr[index]["parentNode"]["parentNode"]["childNodes"][1]).html() + ",";
						}
					}
				});
				strbillid = strbillid.substring(0,strbillid.length-1);
				if(strbillid == ''){
					showMsg("账单未勾选!");
					return false;
				}                         
				JDialog.open("", path + "/page/apartment/apartmentLeftmenu/apartmentrefund/consumption.jsp?strbillid=" + strbillid +"&checkid=" + checkid,680,360);
			}
			
			function checkout(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				$.ajax({
			         url: path + "/apartmentOrderoff.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	if(json.result == 1){
							JDialog.open("退房", path + "/page/apartment/apartmentLeftmenu/apartmentrefund/checkout.jsp?checkid=" + checkid, 700, 500);
						}else {
							showMsg(json.message);
						}
					 },
					 error: function(json) {}
				});
			}

			function aa(e) {
				var color = $(e).css("color");
				if(color != "rgb(153, 153, 153)" ) checkedbytruns(e);
				//$(e).find(":checkbox").attr("checked", !$(e).find(":checkbox").is(":checked"));
			}
			
			function goods(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				window.parent.parent.JDialog.open("", path + "/goods.do",1280,738);
			}
			var text;
			function ReWriteTd(e){
				event.stopPropagation();
				if( $(e).siblings(".pay").text()<0 || $(e).siblings(".cost").text()<0){
					return;
				}else{
				var billId = $(e).siblings().eq(0).text();
				$.ajax({//查看该账单对应的合同的状态
			         url: path + "/queryApartContrartStatus.do",
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
						window.JDialog.open("修改备注",path+"/reWriteApartRemarkAP.do?text="+text+"&billId="+billId,450,200);
					 	}
					 },
					 error: function(json) {}
				});
				}
			}
			function addRemark(e){
				var billId = $(e).parent().siblings().eq(0).text();
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
			function addColor(){
				var tr = document.getElementsByClassName("odd");
				for ( var i = 0; i < tr.length; i++) {
					var td = $(tr[i]).find('.desc');
					var refund = $(tr[i]).find('.refund');
					if($(refund).text()=='1'){
						$(refund).parent().css('background-color','#CC99CC))');
					}else{
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
			}
		</script>
  </body>
</html>
