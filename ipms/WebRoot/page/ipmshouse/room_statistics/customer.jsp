<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/newscript.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>客单</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/customer.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/ipms/room_statics/checkuser.css" />
	</head>

	<body>
		<div class="ifa" id="f">
		<div class="customer_div">
			<div class="check_one">
				<label class="label_title">房单信息</label>
					<ul class="clearfix">
						<li>
							<label class="label_name">
								房型
							</label>
							<input id="roomtype" type="text" value="" class="check specolor"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								房号
							</label>
							<input id="roomid" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								抵点时间
							</label>
							<input id="checkintime" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								离店时间
							</label>
							<input id="checkouttime" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								房价
							</label>
							<input id="roomprice" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								来源
							</label>
							<input id="source" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								活动
							</label>
							<input id="" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								担保
							</label>
							<input id="guarantee" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								保留时效
							</label>
							<input id="limited" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								状态
							</label>
							<input id="statusname" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								预订人
							</label>
							<input id="orderusername" type="text" value="" class="check specolor"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								预定手机
							</label>
							<input id="mphone" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								预定人会员ID
							</label>
							<input id="orderuser" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								会员类型
							</label>
							<input id="rankname" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								备注
							</label> 
							<textarea id="checkremark"   onchange="autosaveRemark()" class="check remark_text"></textarea>
						</li>
					</ul>
			</div>
			<section class="checkuser_wrapper cols_margin clearfix">
				<div class="checkuser_bt clearfix">
					<div class="checkuser_button_left clearfix fl">
 						<label for="" class="label_name order_search"> 
 							会员查询 
 						</label>
 						<input type="text" name="" id="searchmembercondotion"  class="check reset_input" />
 						<!--<button class="button_margin" onclick="searchmember()">
 							查找 
 						</button> 
 						<button class="register_one" onclick="registermember()"> 
 							注册会员 
 						</button> 
						-->
					</div>
					<div class="fl" style="margin: 4px 0 0 50px;"><span id="checkinMessage" style="color: red;font-size: 17px;"></span> </div>
					<div class="checkuser_button_right clearfix fr">
						<!-- <span class="button_margin button_color" onclick="showupdateMember()">修改客人</span> -->
						<span class="button_margin" onclick="deleteCustomer()">删减客人</span>
						<span class="button_margin" onclick="addCustomerHand()">手动增加客人</span>
						<span class="button_margin" onclick="sethostCustomer()">设置主客人</span>
						<span style="margin-right: 53px;" class="button_margin addhand" onclick="cnacelcustom()">取消</span>
						<span class="button_margin addhand" onclick="checkusercheckin()">入住</span>
					</div>
				</div>
					<!--客人信息展示ul-->
					<div class="checkuser_info_write">
					<form id="checkuserinfo">
					<ul class="check_ul clearfix" id="guest">
						<li class='check_name active'><label class="guestmemberid" style='display: none'></label><span><i class="fa fa-star"></i>新客人</span></li>
					</ul>
					<ul class="customer_info cols_ul clearfix ">
						<li>
							<label class="label_name">
								姓名
							</label>
							<input id="username" name="username" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								性别
							</label>
							<select class="check" id="gender" name="gender" disabled="disabled">
								<option value="0">女</option>
								<option value="1">男</option>
							</select>
						</li>
						<!--
						 <li>
							<label class="label_name">
								抵店日期
							</label>
							<input name="离店日期" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<li>
							<label class="label_name">
								离店日期
							</label>
							<input name="离店日期" type="text" value="" class="check"
								disabled="disabled">
						</li>
						-->
						<li>
							<label class="label_name">
								手机号码
							</label>
							<input id="checkphone" name="checkphone" type="text" value="" class="check"
								disabled="disabled">
						</li>
						<!--  
						<li>
							<label class="label_name">
								证件类型
							</label>
							<input name="预订人" type="text" value="" class="check"
								disabled="disabled">
						</li>
						-->
						<!-- <li>
							<label class="label_name">
								会员编码
							</label>
							<input id="memberid" type="text" value="" class="check"
								disabled="disabled">
						</li> -->
						<li>
							<label class="label_name" style="width: 94px">
								证件号码
							</label>
							<input id="idcard" name="idcard" type="text" value="" class="check  card_type" style="width: 159px"
								disabled="disabled">
						</li>
						<!--  
						<li>
							<label class="label_name">
								客单编码
							</label>
							<input name="预定人会员类型" type="text" value="" class="check"
								disabled="disabled">
						</li>
						-->
						<li>
							<label class="label_name">
								会员类型
							</label>
							<select class="check" id="memberrankname" name="memberrankname" disabled="disabled">
							</select>
							<!-- <span role="button" class="button_margin reset_m" onclick="resetvip()">重置会员</span> -->
						</li>
						<li class="address cols_address">
							<label class="label_name">
								住址
							</label>
							<textarea id="address" name="address" disabled="disabled"></textarea>
						</li>
						<li class="record cols_record">
							<label class="label_name">
								备注
							</label>
							<textarea  id="customerreamrk" name="customerreamrk" disabled="disabled" class="te_height"></textarea>
						</li>
					</ul>
					<input type="hidden" id="checkid" name="checkid">
					</form>
					</div>
			</section>
			<div class="check_three">
				<span class="conncroom fl">关联房</span>
				<span class="add_conroom fl" role="button" onclick="showroommap()">
					增加
				</span>
				<span class="add_conroom cancel fl" role="button" onclick="cancelroommap()">
					取消
				</span>
				<!-- 
				<ul  class="fl">
					<li><i class="fa fa-star"></i><p id="hostroomid"></p><p>主关联房</p></li>
				</ul> 
				-->
				<div class="captain">
					<ul class="main_ul clearfix fl" id="roommapping_data">
					</ul>
				</div>
			</div>
			<div class="check_four">
				<span class="button_margin" onclick="roomout()">退房</span>
				<!-- <span class="button_margin button_color" onclick="updateCustomer()">确定</span> -->
				<span class="button_margin" onclick="showcheck()">打印</span>
				<span class="button_margin" onclick="getAvailableRoom()">换房</span>
				<span class="button_margin" onclick="showgetContinue()">续住</span>
				<span class="button_margin" onclick="switchOrder()">转单</span>
			</div>
			</div>
		</div>
		<script type="text/javascript">var path = "<%=request.getContextPath() %>";
			var checkid = $(window.parent.document.all.checkid).val();
		</script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/checkuser.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="checkuserdata.json"></script>
		<script >
			//var checkid = '<%=request.getParameter("checkid")%>';
			
			//window.parent.checkstatus 用来判断此房单是何种状态，取自于check表中的status
			var customerCheck = true;
			$(function(){
				$("#checkid").val(checkid);
				laodCheckData();
				loadroommapping();
				$("#searchmembercondotion").focus();
				checkallfunction();
			});
			document.onkeydown = function(event){
				const e = event || window.event || arguments.callee.caller.arguments[0];
				
				if (e && e.keyCode == 27) { //ESC
					
				}
				if (e && e.keyCode == 113) { //F2
					
				} 
				if (e && e.keyCode == 13) { //ENTER
					interfacecheckuser();
				}
			}
			
			function checkallfunction(){
				if(window.parent.checkstatus == 1){
					//customerCheck = true;
					initcheckusercss("2", "1");
					customerCheck = false;
					$(".check_four .button_margin").addClass("button_color");
				}
				else{
					$(".check_four .button_margin").addClass("cancel_button_color");
					initcheckusercss("2", "0");
				}
			}
			
			function laodCheckData(){
				$.ajax({
			         url: path + "/loadCheckData.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	$("#roomtype").val(json[0]["ROOMNAME"]);
					 	$("#roomid,#hostroomid").val(json[0]["ROOMID"]);
					 	$("#hostroomid").html(json[0]["ROOMID"]);
					 	$("#checkintime").val(dealDate(json[0]["CHECKINTIME"]));
					 	$("#checkouttime").val(dealDate(json[0]["CHECKOUTTIME"]));
					 	$("#roomprice").val(json[0]["ROOMPRICE"]);
					 	$("#source").val(json[0]["DECODESOURCE"]);
					 	$("#orderuser").val(json[0]["ORDERUSER"]);
					 	$("#orderusername").val(json[0]["ORDERUSERNAME"]);
					 	$("#mphone").val(json[0]["MPHONE"]);
					 	$("#statusname").val(json[0]["STATUSNAME"]);
					 	$("#rankname").val(json[0]["RANKNAME"]);
					 	$("#guarantee").val(json[0]["DECODEGUARANTEE"]);
					 	$("#limited").val(json[0]["LIMITED"]);
					 	$("#orderid").val(json[0]["CHECKID"]);
					 	$("#checkremark").val(json[0]["REMARK"]);
					 },
					 error: function(json) {}
				});
			}
			
			function roomout() {
				if(customerCheck)
					return false;
				$.ajax({
			         url: path + "/roomout.do",
					 type: "post",
					 data : {
					 	checkid : checkid
					 },
					 success: function(json) {
					 	if(json.result == 1){
					 		if(window.parent.pagea == -1){
								$(window.parent.parent.document).find("#menu_904").click();
								window.parent.parent.JDialog.close();
							}else {
								$(window.parent.parent.parent.document).find("#contentFrame_first").click();
								window.parent.parent.JDialog.close();
							}
					 	}
					 },
					 error: function(json) {}
				});
				
			}
				
			function autosaveRemark(){
				var remark = $("#checkremark").val();
				if(remark){
					$.ajax({
				         url: path + "/autosaveRemark.do",
						 type: "post",
						 data : {
						 	checkid : checkid,
						 	remark : remark
						 },
						 success: function(json) {
						 	if(json.result == 0){
						 		showMsg(json.message);
						 	}
						 },
						 error: function(json) {}
					});
				}
			}
			
			function resetvip(){
				var orderuser = $("#orderuser").val();
				$.ajax({
			         url: path + "/resetvip.do",
					 type: "post",
					 data : {
					 	orderuser : orderuser,
					 	checkid : checkid
					 },
					 success: function(json) {
					 	if(json.result == 1){
					 		showMsg(json.message);
					 		laodCheckData();
					 	}else if(json.result == 0){
					 		showMsg(json.message);
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			function switchOrder(branchid, orderid){
				//if($("#checkouttime"))
				if(customerCheck)
					return false;
				var arr1 = $("#checkouttime").val().split(" ");
				var arr2 = dealLocalDate(new Date()).split(" ");
				if(arr1[0] == arr2[0]){
					showtransferorder();
				}else {
					showMsg("无法转单!");
				}
			}
			
			function loadroommapping(){
				$.ajax({
			         url: path + "/loadroommapping.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	loadrooommapingdata(json);
					 },
					 error: function(json) {}
				});
			}
			
			function cancelroommap(){
				if(customerCheck)
					return false;
				var delroomid = $($("#roommapping_data .active").find("p").get(0)).html();
				if(!delroomid)
					return false;
				if($("#roomid").val() == delroomid)
					return false;
				$.ajax({
			         url: path + "/cancelroommapping.do",
					 type: "post",
					 data : {
					 	checkid : checkid,
					 	delroomid : delroomid
					 },
					 success: function(json) {
					 	loadrooommapingdata(json.data);
					 	if(json.result == 0){
					 		showMsg(json.message);
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			/*function updateCustomer(){
			
				if(isCheckout(checkid)){
					return false;
				}
				if(isCustomout(checkid)){
					return false;
				}
				var strguest = "";
		 		$.each($(".check_ul li"),function(index){
		 			if($($(".check_ul li")[index]["childNodes"][0]).html() != ''){
		 				strguest = strguest + $($(".check_ul li")[index]["childNodes"][0]).html() + ",";
		 			}
		 		});
		 		strguest = strguest.substring(0,strguest.length-1);
				$.ajax({
			         url: path + "/updateCustomer.do",
					 type: "post",
					 data : {
					 	checkid : checkid,
					 	strguest : strguest
					 },
					 success: function(json) {
					 	if(json.result == 1){
					 		showMsg(json.message);
					 	}else if(json.result == 0){
					 		showMsg(json.message);
					 	}
					 },
					 error: function(json) {}
				});
			}*/
			
			function jumptocustom(e){
				var relaroomid = $(e.children[0]).text();
				$.ajax({
			         url: path + "/jumptocustom.do",
					 type: "post",
					 data : {
					 	checkid : checkid,
					 	relaroomid : relaroomid
					 },
					 success: function(json) {
					 	if(json.result == 0){
					 		showMsg(json.message);
					 	}else if(json.result == 1){
					 		//window.parent.parent.JDialog.openWithNoTitle("", path + "/page/ipmspage/room_statistics/roomlist_check.jsp?checkid=" + json.data.checkId,1179,733);
							$(window.parent.document).find("#roomid").html(json.data.roomId);
							$(window.parent.document).find("#status").html(decode(json.data.status, '1', '【在住】', '2', '【离店】', '3', '【已退未结】', '4', '【离店】'));
							$(window.parent.document).find("#checkid").val(json.data.checkId);
					 		window.parent.document.getElementById("customer").innerHTML='<iframe src="' + path +'/page/ipmspage/room_statistics/customer.jsp?checkid=' + json.data.checkId + '" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>';
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			function loadrooommapingdata(json){
				var data = '';
			 	$.each(json, function(index){
			 		data = data + "<li ondblclick='jumptocustom(this)' " + wantHide(json[index]["CHECKID"]) + ">" + addxingxing(json[index]["CHECKID"])+"<p class='nameid'>" + json[index]["ROOMID"] + "</p>"+
			 				"<p>" + json[index]["MEMBERNAME"] + "</p></li>";
			 	});
			 	if(json.length == 0){
			 		$("#roommapping_data").html("");
			 	}else{
			 		$("#roommapping_data").html(data);
			 	}
				$("#roommapping_data li").on("click",function() {
					$(this).addClass("active");
					$(this).siblings().removeClass("active");
				});
			}
			
			function addxingxing (mapcheckid) {
				if(mapcheckid == checkid){
					return "<i class='fa fa-star'></i>";
				}else{
					return '';
				}
			}
			
			function wantHide(mapcheckid) {
				if(mapcheckid == checkid){
					return "style='display: none;'";
				}else{
					return '';
				}
			}
			
			function showupdateMember(){
				if(customerCheck)
					return false;
				var guestid = $(".check_ul").find(".active").find("label:hidden").html();
				if(guestid)
					window.parent.JDialog.open("会员修改", path + "/showupdateMember.do?memberId=" + guestid , 600,420);
			}
			/*会员注册*/
			function registermember(){
				if(customerCheck)
					return false;
				window.parent.JDialog.open("会员注册", path + "/page/ipmspage/room_statistics/mebregister.jsp" , 500,220);
			}
			function getAvailableRoom(){
				if(customerCheck)
					return false;
				window.parent.JDialog.open("换房", path + "/showgetAvailableRoom.do?checkid=" + checkid , 1000, 650);
			}
			
			function showgetContinue(){
				if(customerCheck)
					return false;
				window.parent.JDialog.open("续住", path + "/showgetcontinue.do?checkid=" + checkid , 700, 400);
			}
			
			function showtransferorder(){
				window.parent.JDialog.open("转单", path + "/showtransferorder.do?checkid=" + checkid , 1000, 650);
			}
			
			function showroommap(){
				if(customerCheck)
					return false;
				window.parent.JDialog.open("关联房", path + "/showroommap.do?checkid=" + checkid , 800, 514);
			}
		</script>
	</body>
</html>