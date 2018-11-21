<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>房单详细页面</title>
    
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/houseorder/houseorder_details.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />	
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/ipms/room_statics/checkuser.css" />
	<script>var base_path = "<%=request.getContextPath()%>";</script>	
	<style type="text/css">
	</style>
  </head>
  
  <body>
   	<%--  <div class="detail_wrap_margin detail_color">
			<div class="detail_title">
				<span class="top_icon" onclick="window.parent.JDialog.close();"><i class="imooc im_top" >&#xea0f;</i></span>
				<span>房单<span class="order_span"><%=request.getParameter("houseorderid")%> ${ houseorderid }</span></span>
				<span class="mystatus"></span>
			</div> --%>
			<div class="order_margin house_margin">
				<section class="detail_one">
					<label class="label_title">房单信息</label>
					<ul class="clearfix">
						<li><label class="label_name">订单号</label><input name="订单号" type="text" id="houseorderid" value="<%=request.getParameter("houseorderid")%>" class="check room_select" disabled></li>
						<li style="display:none;"><label class="label_name">房号</label><input name="房号" type="text" id="roomid" class="check room_select" disabled></li>
						<li><label class="label_name">房间类型</label><input id="roomtype" name="房间类型" type="text" class="check"  id="roomtype" disabled/></li>
						<li><label class="label_name">入住日期</label> <input id="arrivaltime" name="入住日期" type="text" class="check" disabled></li>
						<li><label class="label_name">离店日期</label> <input id="leavetime" name="离店日期" type="text" class="check" disabled></li>
						<li><label class="label_name">入住人</label><input id="orderuser" name="入住人" type="text"  class="check" disabled></li>
						<li><label class="label_name">手机号</label> <input id="mphone" name="手机号" type="text" class="check" disabled></li>
						<li><label class="label_name">会员类型</label> <input id="memberrank" name="会员类型" type="text" class="check" disabled></li>
						<li><label class="label_name">状态</label> <input id="status" name="状态" type="text" class="check" disabled></li>
						<li><label class="label_name">房价(元)</label> <input id="room_price" name="房价" type="text" class="check" disabled></li>
						<li><label class="label_name">备注</label>
						<textarea  id="remark" name="remark" class="" rows="2" cols="124.5" readonly="readonly"></textarea>
						</li>
					</ul>
				</section>
				<section class="checkuser_wrapper clearfix">
				<div class="checkuser_bt clearfix">
					<div class="checkuser_button_left clearfix fl">
 						<label for="" class="label_name order_search"> 
 							会员查询 
 						</label>
 						<input type="text" name="" id="searchmembercondotion"  class="check reset_input" />
 						<!-- <button class="button_margin button_color" onclick="searchmember()">
 							查找 
 						</button>
 						<button class="register_one" onclick="registermember()"> 
 							注册会员 
 						</button> 
						-->
					</div>
					<div class="fl" style="margin: 4px 0 0 50px;"><span id="checkinMessage" style="color: red;font-size: 17px;"></span> </div>
					<div class="checkuser_button_right clearfix fr">
						<%--<span class="button_margin button_color" onclick="showupdateMember()">修改客人</span>
						--%><span class="button_margin button_color" onclick="deleteCustomer()">删减客人</span>
						<span class="button_margin button_color" onclick="addCustomerHand()">手动增加客人</span>
						<span class="button_margin button_color" onclick="sethostCustomer()">设置主客人</span>
						<span style="margin-right: 53px;" class="button_margin button_color addhand" onclick="cnacelcustom()">取消</span>
						<span class="button_margin button_color addhand" onclick="checkusercheckin()">入住</span>
					</div>
				</div>
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
						<li class="address cols_li">
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
			<%-- <section class="detail_four" >
				<div class="fr" role="button">
					<ul class="clearfix">
						<li id="checkout" onclick="checkOut();"><span id="checkout_span">退房</span></li>
						<li id="savePerson" onclick="savePerson();"><span id="savePerson_span">保存</span></li>
						<li id="cancleorder" onclick="queryBill(1);"><span class="button_margin">查看账单</span></li>
						<li id="showeditorder" onclick="showeditorder();"><span class="button_margin update_order">修改房单</span></li>
						<li id="lookHouseOrder" onclick="lookHouseOrder();"><span class="button_margin button_color">查看订单</span></li>
						<li id="checkout" onclick="popuphouse()"><span id="checkout_span" class="button_margin">换房</span></li>
						<li id="checkout" onclick="overstay()"><span id="checkout_span" class="button_margin">续住</span></li>
					</ul>
				</div>
			</section> --%>
			</div> 
		<!-- </div> -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/check.js"></script>	
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/checkuser.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
			var path = "<%=request.getContextPath() %>";
			var houseorderid = '<%=request.getParameter("houseorderid")%>';
			var checkid = houseorderid;
			$(function(){
				checkinType = "2";
				$("#checkid").val(checkid);
				quertHouseOrderInfo();
				loadCheckuser(-1);
				getallmemberrank();
			});
			
			document.onkeyup = function(event){
				const e = event || window.event || arguments.callee.caller.arguments[0];
				
				if (e && e.keyCode == 27) { //ESC
					
				}
				if (e && e.keyCode == 113) { //F2
					
				} 
				if (e && e.keyCode == 13) { //ENTER
					interfacecheckuser();
				}
			}
			
			function popuphouse(){
				window.JDialog.open("换房", "popuphouse.do?houseorderid=" + houseorderid, 700, 500);
			}
			
			function overstay(){
				window.JDialog.open("续住", path + "/page/ipmspage/housecheck/overstay.jsp?leavetime=" + $("#leavetime").val() + "&houseorderid=" + houseorderid, 700, 350);
			}
			
			function quertHouseOrderInfo(){
				$.ajax({
			         url: path + "/quertHouseCheckInfo.do",
					 type: "post",
					 dataType: "json",
					 data : {houseCheckId : houseorderid},
					 success: function(json) {
					 	$("#roomid").val(json[0]["ROOM_ID"]);
				 		$("#roomtype").val(json[0]["ROOM_NAME"]);
					 	$("#arrivaltime").val(json[0]["CHECKIN_TIME"]);
					 	$("#leavetime").val(json[0]["CHECKOUT_TIME"]);
					 	$("#orderuser").val(json[0]["MEMBERNAME"]);
					 	$("#mphone").val(json[0]["MOBILE"]);
					 	$("#status").val(json[0]["STATUSNAME"]);
					 	$("#room_price").val(json[0]["ROOM_PRICE"]);
					 	$("#memberrank").val(json[0]["RANK_NAME"]);
					 	$("#remark").val(json[0]["REMARK"]);
					 	if(json[0]["STATUS"] != '1'){
							$("#savePerson").attr("onclick","");
							$("#checkout").attr("onclick","");
							$("#checkout_span").addClass("check_span");
							$("#savePerson_span").addClass("check_span");
							$("#read").attr("onclick","");
							$("#sethostCustomer").attr("onclick","");
							$("#addCustomer").attr("onclick","");
							$("#deleteCustomer").attr("onclick","");
							$("#read").addClass("check_span");
							$("#sethostCustomer").addClass("check_span");
							$("#addCustomer").addClass("check_span");
							$("#deleteCustomer").addClass("check_span");
							initcheckusercss("2", "0");
					 	}else{
					 		$("#checkout_span").addClass("button_margin");
							$("#savePerson_span").addClass("button_margin");
							$("#read").addClass("button_margin");
							$("#sethostCustomer").addClass("button_margin");
							$("#addCustomer").addClass("button_margin");
							$("#deleteCustomer").addClass("button_margin");
							initcheckusercss("2", "1");
						}
					 },
					 error: function(json) {}
				});
			}

			function savePerson() {
				var checkinuser = "";
				$.each($(".check_ul li"),function(i){
					if (i == 0) {
					 	checkinuser = $($(".check_ul li")[i]).find("label:hidden").html() ;
					} else {
						checkinuser = checkinuser + "," + $($(".check_ul li")[i]).find("label:hidden").html();
					}
				});
				$.ajax({
			         url: path + "/saveHousePerson.do",
					 type: "post",
					 dataType: "json",
					 data : { mobile : checkinuser, 
			        	 housecheckId: houseorderid },
					 success: function(json) {
					 	if (json.result == 0) { 
					 		showMsg("保存成功!");
					 		window.setTimeout("window.parent.JDialog.close()",1800);
					 	} else if (json.result == 1) {
					 		showMsg(json.message);
					 	}	
					 },
					 error: function(json) {
					 	showMsg("保存失败!");
					 }
				});
			}

			function checkOut() {
				window.JDialog.open("民宿退房", path + "/page/ipmspage/housecheck/secondquery.jsp?orderId=" + houseorderid , 400,250);
				/* $.ajax({
			         url: path + "/OperationHouseCheckOut.do",
					 type: "post",
					 dataType: "json",
					 data : { housecheckId: houseorderid },
					 success: function(json) {
					 	if (json.result == 0) { 
					 		showMsg("退房成功!");
					 		window.parent.document.getElementById('menu_904').click();
							window.parent.JDialog.close();
					 	} else if (json.result == 1) {
					 		showMsg(json.message);
					 	}	
					 },
					 error: function(json) {
					 	showMsg("退房失败!");
					 }
				}); */
			}
			
			$(".imooc").on("click",function(){
				$(".detail_five").css("display","none");
			});
			$(".detail_imooc").on("click",function(){
				$(".detail_six").css("display","none");
			});
			$(".update_order").on("click",function(){
				$(".detail_six").css("display","block");
			})
			$(".info_write ul li").on("click", function() {
				$(this).addClass("active");
				$(this).siblings().removeClass("active");
			});
			$(".detail_four .look_order").on("click", function() {
				window.location.href = base_path + "order_check.jsp";
			});
			
			function checkin() {
				if(isCheckout(houseorderid)){
					return false;
				}
				if ($("#memberid").val() == null || $("#memberid").val() == "") {
					showMsg("请至少填写一位入住人！");
					return false;
				} else if ($("#roomid").val() == null || $("#roomid").val() == "") {
					showMsg("请选择房号！");
					return false;
				} else if (!isMobile($("#checkphone").val())) {
					showMsg("请填写正确的手机号码！");
					return false;
				} else if (!($("#idcard").val())) {
					showMsg("请填写正确的身份证！");
					return false;
				}else {
					var checkinuser = "";
					$.each($(".check_ul li"),function(i){
						if (i == 0) {
						 	checkinuser = $($(".check_ul li")[i]).find("label:hidden").html() ;
						} else {
							checkinuser = checkinuser + "," + $($(".check_ul li")[i]).find("label:hidden").html();
						}
					})
					$.ajax({
				         url: path + "/HouseCheckIn.do",
						 type: "post",
						 dataType: "json",
						 data : { checkinUser : checkinuser, 
				        	 orderId: houseorderid, roomId: $("#roomid").val() },
						 success: function(json) {
						 	if (json.result == 0) { 
						 		showMsg("入住成功!");
								window.parent.document.getElementById('menu_903').click();
								window.parent.JDialog.close();
						 		
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
			
			function registermember(){
				if(isCheckout(houseorderid)){
					return false;
				}
				JDialog.open("会员注册", path + "/page/ipmspage/leftmenu/leftorder/ordermemberadd.jsp" , 800, 410);
			}
			//房单查看
		function checkOrder(){
			var customerid;
			$(".check_name").each(function(i){
				if (i == 0) {
					customerid = $(this).find("label:hidden").text(); 
				} else {
					var custid = $(this).find("label:hidden").text(); 
					customerid = customerid + "," + custid;
				}
			})
			window.parent.JDialog.openWithNoTitle("", path + "/page/ipmspage/order/order_check.jsp?houseorderid=" + $("#houseorderid").val() + 
					"&roomId=" + $("#roomid").val() +"&customerid="+customerid, 1179, 733);
		}
		
		function showeditorder() {
			if(isCheckout(houseorderid)){
					return false;
				}
			window.JDialog.open("修改房单", path + "/page/ipmspage/order/editorder.jsp?houseorderid=" + $("#houseorderid").val() , 700, 400);
		}	

		function cancleorder() {
			if (isCheckout(houseorderid)) {
				return false;
			}
			window.JDialog.open("取消房单", path + "/page/ipmspage/order/secondquery.jsp?houseorderid=" + $("#houseorderid").val() , 200, 100);
		}

		function queryBill(num){
			window.JDialog.open("账单", path + "/page/ipmspage/leftmenu/houserefund/house_bill.jsp?checkid=" + houseorderid+"&status="+num , 1000, 500);
		}
		
		// 查看房单信息
		function lookHouseOrder(){                            
			window.parent.JDialog.openWithNoTitle("", path + "/page/ipmspage/houseorder/houseOrder_check.jsp?houseorderid=" + houseorderid , 1179, 733);
		}
	</script>
  </body>
</html>
