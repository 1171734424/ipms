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
    <title>订单</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_check.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/ipms/room_statics/checkuser.css" />
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
	<script>var base_path = "<%=request.getContextPath()%>";</script>
  </head>
  <body>
		<div class="content_color" id="ifa">
		<input id="orderId" type="text" hidden="true" value="<%=request.getParameter("orderId")%>"/>
			<div class="ifa">
				<div class="check_one fl">
					<form action="" method="post">
						<ul class="clearfix">
							<li><label class="label_name">房间类型</label><input name="房间类型" type="text" id="roomtype"  class="check" disabled="disabled"></li>
							<li><label class="label_name">抵店日期</label> <input name="抵店日期" type="text" id="arrivaltime" class="check" disabled="disabled"></li>
							<li><label class="label_name">离店日期</label> <input name="离店日期" type="text" id="leavetime"  class="check" disabled="disabled"></li>
							<li><label class="label_name">房号</label> <input name="房号" id="roomid" type="text" value="<%=request.getParameter("roomid")%>" class="check" disabled="disabled"></li>
							<li><label class="label_name">房价</label> <input name="房价" type="text" id="roomprice" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">活动</label> <input name="活动" id="activity"  type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">担保</label> <input name="担保" id="guarantee" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">保留时效</label> <input name="保留时效" id="limited" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">状态</label> <input name="状态" id="status" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">预定人会员类型</label> <input name="预定人会员类型" id="memberrank" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">预订人</label><input name="预订人" id="orderuser"  type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">预定人手机</label> <input name="预定人手机" id="mphone" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">预定人会员卡号</label> <input name="预定人会员卡号"  id="memberid" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">销售员</label><input name="销售员" id="staff" type="text" value="" class="check" disabled="disabled"></li>
							<li><label class="label_name">接待备注</label> <textarea name="备注"  id="receptionremark" class="check txt_area" rols="3" cols="100" disabled="disabled"></textarea></li>
							<li><label class="label_name">客房备注</label> <textarea name="备注"  id="roomremark" class="check txt_area" rols="3" cols="100" disabled="disabled"></textarea></li>
							<li><label class="label_name">备注</label> <textarea name="备注"  id="remark" class="check txt_area" rols="3" cols="100" disabled="disabled"></textarea></li>						
						</ul>
					</form>
				</div>
				
				<section class="checkuser_wrapper clearfix">
				<!-- <div class="checkuser_bt clearfix">
					<div class="checkuser_button_left clearfix fl">
 						<label for="" class="label_name"> 
 							会员查询 
 						</label>
 						<input type="text" name="" id="searchmembercondotion"  class="check reset_input" />
 						<button id="searchmem" class="button_margin" onclick="searchmember()">
 							查找 
 						</button> 
					</div>
					<div class="checkuser_button_right clearfix fr"> 
						<span id="deletemyguest" class="button_margin" onclick="deleteCustomer()">减少客人</span>
						<span id="setbyhand" class="button_margin button_color" onclick="addCustomerHand()">手动增加客人</span>
						<span id="setmain" class="button_margin button_color" onclick="sethostCustomer()">设置主客人</span>
						<span style="margin-right: 53px;" class="button_margin button_color addhand" onclick="cnacelcustom()">取消</span>
						<span class="button_margin button_color addhand" onclick="checkusercheckin()">入住</span>
					</div> 
				</div>-->
					<!--客人信息展示ul-->
					<div class="checkuser_info_write">
					<form id="checkuserinfo">
					<ul class="check_ul clearfix" id="guest">
						<li class='check_name active'><label class="guestmemberid" style='display: none'></label><span><i class="fa fa-star"></i>新客人</span></li>
					</ul>
					<ul class="customer_info clearfix ">
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
							<label class="label_name">
								证件号码
							</label>
							<input id="idcard" name="idcard" type="text" value="" class="check  card_type"
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
						<li class="address">
							<label class="label_name">
								住址
							</label>
							<textarea id="address" name="address" disabled="disabled"></textarea>
						</li>
						<li class="record">
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
					<!-- <span class="button_margin getIN" role="button" onClick="enterIN()">入住</span> -->
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/checkuser.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/order_check/order_check.js"  charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/order/rightclick.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		var path = "<%=request.getContextPath() %>";
		var orderId = '<%=request.getParameter("orderId")%>';
		let checkid = orderId;
		var customerId = '<%=request.getParameter("customerid")%>';
		$(document).ready(function(){
			$.ajax({
		         url: path + "/loadOrderData.do",
				 type: "post",
				 dataType: "json",
				 data : {orderId : orderId},
				 success: function(json) {
				 	$("#roomtype").val(json[0]["ROOMTYPE"]);
				 	//$("#orderid").val(json["ROOMID"]);
				 	$("#arrivaltime").val(json[0]["ARRIVALTIME"]);
				 	$("#leavetime").val(json[0]["LEAVETIME"]);
				 	$("#orderuser").val(json[0]["ORDERUSER"]);
				 	$("#mphone").val(json[0]["MPHONE"]);
				 	$("#source").val(json[0]["SOURCE"]);
				 	$("#roomprice").val(json[0]["PRICE"]);
				 	$("#birthday").val(json[0]["BIRTHDAY"]); 
				 	$("#memberrank").val(json[0]["MEMBERRANK"]);
				 	$("#activity").val(json[0]["ACTIVITY"]);
				 	$("#guarantee").val(json[0]["GUARANTEE"]);
				 	$("#limited").val(json[0]["LIMITED"]);
				 	$("#orderid").val(json[0]["ORDERID"]);
				 	$("#advance").html("预付金额：" + json[0]["ADVANCE"]);
				 	$("#receptionremark").val(json[0]["RECEPTIONREMARK"]);
				 	$("#roomremark").val(json[0]["ROOMREMARK"]);
				 	$("#remark").val(json[0]["REMARK"]);
				 	$("#status").val(json[0]["STATUS"]);
				 	$(".mystatus").text(json[0]["STATUS"]);
				 	$("#staff").val(json[0]["RECORDUSER"]);
				 	$("#memberid").val(json[0]["MEMBERID"]);
				 	//loadCheckuser(json[0]["CHECKUSER"],json[0]["CHECKUSERNAME"]);
				 },
				 error: function(json) {}
				 
			});
			//loadCustomer(customerId);
			getallmemberrank();
			loadCheckuser();
		});
		
		
		

		function enterIN() {
			if(isCheckout(orderId)){
				return false;
			}
			if (!$($(".check_ul li").find("label:hidden")).text()) {
				showMsg("请至少填写一位入住人！")
				return false;
			} else if ($("#roomid").val() == null || $("#roomid").val() == "") {
				showMsg("请选择房号！")
				return false;
				//} else if (!isMobile($("#checkphone").val())) {
				//showMsg("请填写正确的手机号码！");
				//return false;
			} /**else if (!($("#idcard").val())) {
				showMsg("请填写正确的身份证！");
				return false;
			} **/else {
				var checkinuser = "";
				$.each($(".check_ul li").find("label:hidden"),function(i){
					if (i == 0) {
					 	checkinuser = $(this).text();
					} else {
						checkinuser = checkinuser + "," + $(this).text();
					}
				})
				
				$.ajax({
			         url: path + "/checkIn.do",
					 type: "post",
					 dataType: "json",
					 data : { checkinUser : checkinuser, 
					 			orderId: $("#orderId").val(), roomId : $("#roomid").val() },
					 success: function(json) {
					 	if (json.result == 0) {
					 		showMsg("入住成功!");
							window.setTimeout("window.parent.parent.document.getElementById('menu_903').click()",1800);
							window.setTimeout("window.parent.parent.JDialog.close()",1800);
					 	} else if (json.result == 1) {
					 		showMsg(json.message);
					 	}
					 },
					 error: function(json) {
					 	showMsg("入住失败!");
					 }
				});
			}
		}
	</script>	
    </body>
</html>
