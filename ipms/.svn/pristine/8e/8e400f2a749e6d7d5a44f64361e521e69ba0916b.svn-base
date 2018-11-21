<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>房单</title>
    
	<meta http-equiv="pragma" content="no-cache">
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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
	<script src="<%=request.getContextPath()%>/script/common/LodopFuncs.js"></script>
	<style type="text/css">
	.button_margin{
		float: right;
	    display: block;
	    height: 34px;
	    line-height: 34px;
	    text-align: center;
	    width: 80px;
	    margin-left: 10px;
	    background-color: #b8b8bd00;
	    border-radius: 4px;
	    font-size: 0.85rem;
	    color: #FFF;
	    border: none;
	}
	.button_color{
		background-color: #5A5D9D;
	}
	.cancel_button_color{
		background-color: #a5a3a3;
	}
	.bill_width{
		position: relative;
	}
	.parentCls {margin:5px 60px 0;}
	.js-max-input {
		border: solid 1px #bfbcbc;
		background: white;
		padding: 0 10px 0 10px;
		font-size:20px;color: #333;
	}
	.pageRegion{
				margin-top:-62px;
			}
	</style>
  </head>
  
  <body>
    <div class="bill_width">
			<div class="bill_one">
				<section class="box_content_widget fl">
					<div class="content" style="overflow: auto;">
						<table id="myTable" class="myTable" border="0">
							<thead>
								<tr>
									<th class="header"><input type="checkbox" onchange="updateallcheckbox(this)"/><span style="margin-left: 5px;">全选</span></th>
									<!-- <th class="header">交易号</th> -->
									<th class="header">账目类型</th>
									<th class="header">项目名称</th>
									<th class="header">消费</th>
									<th class="header">结算</th>
									<th class="header">描述</th>
									<th class="header">操作者</th>
									<th class="header">账目时间</th>
									<th class="header">支付方式</th>
									<th class="header">状态</th>
									<th class="header">备注</th>
								</tr>
							</thead>
							<tbody id="roomlist_bill_date" style="overflow: auto; height: 100%;">
							</tbody>
						</table>						
					</div>
				</section>
			</div>
			<div class="targetRem"></div>
			<div class="bill_two">
				<span>关联房</span>
				<!-- 
				<ul class="clearfix fl">
					<li class="owner">
						<p id="roomid"></p>
						<p>主客房</p>
						<p style="display: none" id="checkid" ></p>
					</li>
				</ul> -->
				<ul class="clearfix fl" id="roommapping_data">
				</ul>
			</div>
			<div class="bill_three">
				<!-- <span class="">所选消费<span class="">0.00</span></span>
				<span class="">订单号<span class="">2063993</span></span> -->
				<!-- <span class="">TTV<span class="money" id="TTV">0.00</span></span> -->
				<span class="">消费<span class="money" id="cost">0.00</span></span>
				<span class="">结算<span class="money" id="pay">0.00</span></span>
				<span class="">帐面金额<span class="money" id="accountfee">1.00</span></span>
				<span class="">平账金额<span class="money" id="totalfee">1.00</span></span>
			</div>
			<!--功能按钮区-->
			<div class="bill_four">
				<!-- <span class="button_margin" onclick="goods()">商品售卖</span>
				<span class="button_margin">会员升级</span> -->
				<span class="button_margin" onclick="resetprice()">房费调整</span>
				<span class="button_margin button_color" onclick="loadallbill()">所有明细</span>
			</div>
			<div class="foot_submit">
				<span class="button_margin" onclick="showaddbill()">入账</span>
				<span class="button_margin" onclick="consumption()">冲减</span>
				<span class="button_margin" onclick="transferbill()">转账</span>
				<span class="button_margin" onclick="autotransferbill()">自动转账</span>
				<span class="button_margin" onclick="printinvoice()" style="display: none;">发票</span>
				<span class="button_margin" onclick="printbill()" style="display: none;">打印</span>
				<span class="button_margin" onclick="checkout(2)">结账</span>
				<span class="button_margin" onclick="splitaccount()">拆分账</span>
			</div>
		 </div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/Zoom.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">		
		var path = "<%=request.getContextPath() %>";
		//var checkid = '<%=request.getParameter("checkid")%>';
		var checkid = $(window.parent.document.all.checkid).val();
		var pagea = '<%=request.getParameter("pagea")%>'; //区分来自于房单还是房态图
		var billCheck = true;
			$(function(){
				loadbill();
				loadpayandcost();
				loadCheckData();
				loadroommapping();
				checkallfunction();
				$(".bill_two ul:first li").addClass("active");
				//$(".bill_two ul[id==''] li").addClass("active");
			});
			//检查父页面中的checkstatus的状态，来判断该房单的状态，要加载哪些颜色
			function checkallfunction(){
				if(window.parent.checkstatus == 1 || window.parent.checkstatus == 3){
					//billCheck = true;
					billCheck = false;
					$(".foot_submit .button_margin").addClass("button_color");
					$(".bill_four .button_margin:first").addClass("button_color");
				}
				else{
					$(".foot_submit .button_margin").addClass("cancel_button_color");
					$(".bill_four .button_margin:first").addClass("cancel_button_color");
				}
			}
			
			function passcheckroomlistbill(){
				if($(window.parent.document.all.checkid).val() != checkid){
					var msg = "非本账单!";
					showMsg(msg);
					return true;
				}
			}
			
			function resetprice(){
				if(billCheck)
					return false;
				if(passcheckroomlistbill())
					return false;
				window.parent.JDialog.open("房费调整", path + "/showresetprice.do?checkid=" + checkid , 420,180);
			}
			
			function updateallcheckbox(e){
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
				//var checkbox = document.getElementById("roomlist_bill_date");
				//checkedbytruns(checkbox);
			}
			
			function printbill() {
				if(billCheck)
					return false;
				var strbillid = getstrbillids();
				var msg = "";
				var LODOP = getLodop();
				LODOP.PRINT_INIT("打印功能演示！");
				if (strbillid != '') {
					var arr = $("input[type='checkbox']");
					$.each(arr, function(index) {
						if (!arr[index]["checked"]) {
							if ($($(this).parent().parent()).hasClass("odd")) {
								$($(this).parent().parent()).css("display","none");
							}
						}
					});
				}
				$($("tr").find("input:checkbox")).parent().css("display","none");
				LODOP.SET_PRINT_MODE("FULL_WIDTH_FOR_OVERFLOW", true);
				 LODOP.SET_PRINT_STYLE("FontSize",10); 
				//LODOP.SET_PRINT_MODE("FULL_HEIGHT_FOR_OVERFLOW", true);
				LODOP.ADD_PRINT_HTM(10, 8, "100%", "100%", "<html><head>" + $("head").html() + "</head><body><div class='bill_width'><div>" + $(".bill_one").html() + "</div><div class='bill_two'>" + $(".bill_two").html()  + "</div><div class='bill_three'>" + $(".bill_three").html() + "</div></div></body></html>");
				//LODOP.ADD_PRINT_HTM(10, 8, "100%", "100%", $("html").html());
				//LODOP.SET_PRINT_PAGESIZE(3,"100%", "100%","");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽138.5mm；45表示页底空白4.5mm
				//$("#roomlist_bill_date").css("overflow","scroll");
				LODOP.PRINT_DESIGN();
				
				//LODOP.PREVIEW();
				window.parent.$(".tab_two").click();
			}
			
			function refershbill(){
				loadbill();
				loadpayandcost();
			}
			
			function loadCheckData(){
				$.ajax({
			         url: path + "/loadCheckData.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	$("#roomid").html(json[0]["ROOMID"]);
					 	$("#checkid").html(checkid);
					 },
					 error: function(json) {}
				});
			}
			
			function loadpayandcost(){
				$.ajax({
			         url: path + "/loadpayandcost.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
						 //$("#TTV").html(json["TTV"].toFixed(2));
						 $("#cost").html(json["cost"].toFixed(2));
						 $("#pay").html(json["pay"].toFixed(2));
						 $("#accountfee").html((json["uncheckoutCost"] - json["uncheckoutPay"]).toFixed(2));
						 $("#totalfee").html((json["shouldPay"]).toFixed(2));
					 },
					 error: function(json) {}
				});
			}
			
			function loadroommapping(){
				$.ajax({
			         url: path + "/loadroommapping.do",
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
			
			function isActive(checkid, old){
				return checkid==old?' active':'';
			}
			
			function loadrooommapingdata(json){
				var data = "";
			 	$.each(json, function(index){
			 		data = data + "<li class='owner" + isActive(json[index]["CHECKID"], checkid) + "'><p>" + json[index]["ROOMID"] + "</p>" + 
			 				"<p>查看</p>" +
			 				"<p style='display: none;'>" + json[index]["CHECKID"] + "</p></li>"
			 	});
			 	if(json.length == 0){
			 		$("#roommapping_data").html("");
			 	}else{
			 		$("#roommapping_data").html(data);		 	
			 	}
			}
			
			//加载当前默认账单数据
			function loadbill(){
				$.ajax({
			         url: path + "/loadRoomBillData.do",
					 type: "post",
					 data : {
					 	checkid : checkid,
					 	status : '1,3,4'
					 },
					 success: function(json) {
						 loadroombilldata(json.data);
						 addroomplan();
						 addColor();
					 },
					 error: function(json) {}
				});
			}
			
			//加载所有账单列表数据
			function loadallbill(){
				$.ajax({
			         url: path + "/loadRoomBillData.do",
					 type: "post",
					 data : {
					 	checkid : checkid,
					 	status : '1,2,3,4,5'
					 },
					 success: function(json) {
					 	if(json.result == 0){
					 		showMsg(json.message);
					 	}
						 loadroombilldata(json.data);
						 addroomplan();
						 addColor();
					 },
					 error: function(json) {}
				});
			}
			
			function showaddbill(){
				if(billCheck)
					return false;
				if(passcheckroomlistbill())
					return false;
				
				window.parent.JDialog.open("入账", path + "/showgetAddBill.do?checkid=" + checkid , 700,380);
			}
			
			function transferbill(){
				if(billCheck)
					return false;
				if(passcheckroomlistbill())
					return false;
				
				$.ajax({
			         url: path + "/checkroommappingnull.do",
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
				if(billCheck)
					return false;
				if(passcheckroomlistbill())
					return false;
				
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
			
			//加载房单列表数据
			function loadroombilldata(json){
				var tabledata;
			 	$.each(json, function(index){
			 		tabledata = tabledata + "<tr class='odd' " + ((checkbillstatus(json[index]["STATUS"]))?"":"style='color: #999'") + " onclick='aa(this)' >" +
			 		"<td><input type='checkbox'></td>" +
			 		"<td style='display: none;'>" + json[index]["BILLID"] + "</td>" +
			 		"<td class='billtype'>" + json[index]["BILLTYPE"] + "</td>" +
			 		"<td>" + json[index]["PROJECTNAME"] + "</td>" +
			 		"<td class='cost'>" + converttosomething(json[index]["COST"].toFixed(2), null, '') + "</td>" +
			 		"<td class='pay'>" + converttosomething(json[index]["PAY"].toFixed(2), null, '') +"</td>" +
			 		"<td class='desc'>" + json[index]["DESCRIBE"] + "</td>" +
			 		"<td>" + json[index]["STAFFNAME"] + "</td>" +
			 		"<td style='white-space:nowrap'>" + dealDate(json[index]["RECORDTIME"]) + "</td>" +
			 		"<td>" + decode(json[index]["PAYMENT"], "1", "现金",  "2", "银行卡", "4", "支付宝", "5", "微信", "其他") + "</td>"+
			 		"<td class='billstatus' >" + decode(json[index]["STATUS"], '1', '正常', '2', '冲减', '3', '转账', '4', '已结', '5', '拆分') + "</td>"+
			 		"<td onclick='ReWriteTd(this)' class='parentCls' title='"+ converttosomething(json[index]["REMARK"], null, '') +"'>" + beyondContent(json[index]["REMARK"], 6)  + "</td>" +
			 		"</tr>";
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
				$( document ).tooltip();
				
			}
			
			function checkbillstatus(e){
				if( e == 1 || e == 3){
					return true;
				}else{
					return false;
				}
			}
			
			
			function consumption(){
				if(billCheck)
					return false;
				if(passcheckroomlistbill())
					return false;
				var strbillid = getstrbillids();
				var msg = "";
				if(strbillid == ''){
					msg = "账单未勾选!";
					showMsg(msg);
					return false;
				}
				if(strbillid == 0){
					msg = "选中了错误金额!";
					showMsg(msg);
					return false;
				}
				
				JDialog.open("账目冲减", path + "/showconsumption.do?checkid=" + checkid + "&strbillid=" + strbillid,680,360);
			}
			
			function getstrbillids(){
				$("input[type='checkbox']").is(':checked');
				var arr = $("input[type='checkbox']");
				var strbillid = "";
				$.each(arr, function(index){
					if(arr[index]["checked"]){
						var billid = $(arr[index]["parentNode"]["parentNode"]["childNodes"][1]).html();
						var pay, cost;
						cost = $(arr[index]["parentNode"]["parentNode"]["childNodes"][4]).html();
						pay = $(arr[index]["parentNode"]["parentNode"]["childNodes"][5]).html();
						if(isNumber(billid)){
							strbillid = strbillid + billid + ",";
						}
						if (cost == 0 && pay == 0){
							strbillid = "0,";
							return false;
						}
					}
				});
				strbillid = strbillid.substring(0,strbillid.length-1);
				return strbillid;
			}
			
			function checkout(freshtype){
				if(billCheck)
					return false;
				if(passcheckroomlistbill())
					return false;
				var strbillid = getstrbillids();
				if( strbillid && strbillid == 0){
				    showMsg("选中错误了金额!");
					return false;
				}
				JDialog.open("退房", path + "/page/ipmshotel/room_statistics/checkout.jsp?checkid=" + checkid + "&strbillids=" + strbillid + "&freshtype=" + freshtype + "&page=" + pagea, 700, 520);
			}

			function aa(e) {
				var billstatus = $(e).find(".billstatus").text();
				if( billstatus == "正常") checkedbytruns(e);
				//if( $(e["style"]).length == 0 || $(e).css("background-color")) checkedbytruns(e);
				//$(e).find(":checkbox").attr("checked", !$(e).find(":checkbox").is(":checked"));
			}
			
			
			function checkstrbillids(fn){
				$("input[type='checkbox']").is(':checked');
				var arr = $("input[type='checkbox']");
				var flag = true;
				$.each(arr, function(index){
					if(arr[index]["checked"]){
						var tr = $(arr[index]["parentNode"]["parentNode"]);
						flag = fn(tr);
					}
				});
				return flag;
			}
			
			function goods(){
				window.parent.parent.JDialog.open("", path + "/goods.do",1280,738);
			}
			
			//向账单列表中添加房租计划
			function addroomplan(){
				$.ajax({//查看改账单对应的订单的状态
			         url: path + "/addroomplan.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	if(json.length > 0){
						 	var billtable = $("#roomlist_bill_date");
						 	var strroomplan = "";
						 	$.each(json, function(index){
						 		 strroomplan = strroomplan + "<tr class='odd'>" +
						 		"<td><input type='checkbox' onclick= 'this.checked=!this.checked'></td>" +
						 		"<td style='display: none;'>" + json[index]["data"]["logId"] + "</td>" +
						 		"<td>房租计划</td>" +
						 		"<td>房租计划</td>" +
						 		"<td class='cost'>0.00</td>" +
						 		"<td class='pay'>0.00</td>" +
						 		"<td class='desc'>计划</td>" +
						 		"<td>" + json[index]["staffName"] + "</td>" +
						 		"<td style='white-space:nowrap'>" + dealDateOne(json[index]["recordTime"]) + "</td>" +
						 		"<td>无</td>"+
						 		"<td>正常</td>"+
						 		"<td title='"+ converttosomething("原房价:" + json[index]["data"]["roomPrice"].toFixed(2) + 
						 		"元,现房价: " + (Number(json[index]["data"]["roomPrice"]) + Number(json[index]["data"]["cashback"])).toFixed(2) + 
						 		"元,浮动价:" + json[index]["data"]["cashback"].toFixed(2) + "元", null, '') +"'>浮动价:" + json[index]["data"]["cashback"].toFixed(2) + "元</td>" +
						 		"</tr>";
						 	});
						 	billtable.append(strroomplan);
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
				$.ajax({//查看改账单对应的房单的状态
			         url: path + "/queryCheckStatus.do",
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
							window.parent.JDialog.open("修改备注",path+"/reWriteRemark.do?text="+text+"&billId="+billId,450,200);
							} 
						 	
//					 	}
					 },
					 error: function(json) {}
				});
				
				}
			}
			function addColor(){
				var tr = document.getElementsByClassName("odd");
				for ( var i = 0; i < tr.length; i++) {
						var td = $(tr[i]).find('.desc');
						if($(td).text()=='押金'){
							if(!$(td).parent().hasClass('style')){
								$(td).parent().css('background','#E85B48');
							
							}
						}
						if($(td).text()=='入账'){
							if(!$(td).parent().attr('style')){
							$(td).parent().css('background','#E85B48');
							
							}
					}
				}
			}

			function splitaccount() {
				if (billCheck)
					return false;
				if(passcheckroomlistbill())
					return false;
				var strbillid = getstrbillids();
				var msg = "";
				if (strbillid == '') {
					msg = "账单未勾选!";
					showMsg(msg);
					return false;
				}
				if (strbillid.indexOf(",")>0) {
					msg = "请选择一条账单!";
					showMsg(msg);
					return false;
				}
				if(strbillid == 0){
					msg = "选中了错误金额!";
					showMsg(msg);
					return false;
				}
				$("input[type='checkbox']").is(':checked');
				var arr = $("input[type='checkbox']");
				var billid = "";
				$.each(arr, function(index){
					if(arr[index]["checked"]){
						billid = $(arr[index]["parentNode"]["parentNode"]["childNodes"][2]).html();	
					}
				});
				if(billid == "结算"){
					showMsg("不可拆分结算!");
					return false;
				}
				
				JDialog.open("拆分账", path + "/spitAccountPage.do?checkId=" + checkid + "&strbillId=" + strbillid,620,360);
			}


			function printinvoice() {
				showMsg("请提供接口!");
			}
		</script>
  </body>
</html>
