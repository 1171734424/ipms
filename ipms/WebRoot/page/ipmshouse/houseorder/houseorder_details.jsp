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
    
    <title>订单详细页面</title>
    
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/houseorder/houseorder_details.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />	
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/ipms/room_statics/checkuser.css" />
	<style type="text/css">
		.detail_one textarea {
		    width: 424px;
		}
	</style>
	<script>var base_path = "<%=request.getContextPath()%>";</script>
  </head>
  <body>
   	<div class="detail_wrap_margin detail_color">
			<div class="detail_title">
				<input type="hidden" id="jspStatus" value="<%=request.getParameter("status")%>"/>
				<span class="top_icon" onclick="window.parent.JDialog.close();"><i class="imooc im_top" >&#xea0f;</i></span>
				<span>订单<span class="order_span"><%=request.getParameter("houseorderid")%> ${ houseorderid }</span></span>
				<span class="mystatus"></span>
			</div>
			<div class="order_margin">
				<section class="detail_one">
					<label class="label_title">订单信息</label>
					<ul class="clearfix">
						<li><label class="label_name">民宿编号</label><input name="BRANCH_ID" type="text" id="BRANCH_ID" class="check room_select" disabled></li>
						<li style="display:none;"><label class="label_name">房号</label><input name="房号" type="text" id="roomid" class="check room_select" disabled></li>
						<li><label class="label_name">房间类型</label><input id="roomtype" name="房间类型" type="text" class="check"  id="roomtype" disabled/></li>
						<li><label class="label_name">抵店日期</label> <input id="arrivaltime" name="抵点日期" type="text" class="check" disabled></li>
						<li><label class="label_name">离店日期</label> <input id="leavetime" name="离店日期" type="text" class="check" disabled></li>
						<li><label class="label_name">预订人</label><input id="orderuser" name="预订人" type="text"  class="check" disabled></li>
						<li><label class="label_name">预定人手机</label> <input id="mphone" name="预定人手机" type="text" class="check" disabled></li>
						<li><label class="label_name">会员类型</label> <input id="memberrank" name="预定人会员类型" type="text" class="check" disabled></li>
						<li><label class="label_name">来源</label> <input id="source" name="来源" type="text" class="check" disabled></li>
						<!-- <li><label class="label_name">备注</label>
						<textarea  id="remark" name="remark" class="" rows="2" cols="124.5" readonly="readonly" onclick="editCustomerReamrk(this,1)"></textarea>
						</li> -->
						<li>
							<label class="label_name">接待备注</label>
							<textarea  id="reception" name="reception"  readonly="readonly" onclick="editCustomerReamrk(this,3)" ></textarea>
						</li>
						<li>
							<label class="label_name">客房备注</label>
							<textarea  id="roomRemark" name="roomRemark" readonly="readonly" onclick="editCustomerReamrk(this,4)" ></textarea>
						</li>
					</ul>
				</section>
				<section class="checkuser_wrapper clearfix">
				<div class="checkuser_bt clearfix">
					<div class="checkuser_button_left clearfix fl">
 						<label for="" class="label_name order_search"> 
 							会员查询 
 						</label>
 						<input type="text" name="" id="searchmembercondotion"  class="check reset_input" placeholder="请输入手机号或身份证"/>
 						<!--<button class="button_margin button_color" onclick="searchmember()">
 							查找 
 						</button> 
 						<button class="register_one" onclick="registermember()"> 
 							注册会员 
 						</button> 
						-->
					</div>
					<div class="fl" style="margin: 4px 0 0 50px;"><span id="checkinMessage" style="color: red;font-size: 17px;"></span> </div>
					<div class="checkuser_button_right  house_button clearfix fr"  style="margin-right: -7px;">
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
					<ul class="customer_info cols_houseorder clearfix ">
						<li>
							<label class="label_name">
								姓名
							</label>
							<input id="username" name="username" type="text" value="" class="check" disabled="disabled">
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
						<li class="address cols_li address_textare">
							<label class="label_name">
								住址
							</label>
							<textarea id="address" name="address" disabled="disabled" style="width:698px;"></textarea>
						</li>
						<li class="record">
							<label class="label_name">
								备注
							</label>
							<div onclick="editCustomerReamrk(this,2);">
								<textarea  id="customerreamrk" name="customerreamrk"  disabled="disabled" class="te_height" style="width:87.5%;" ></textarea>
								
							</div>
							
						</li>
					</ul>
					<input type="hidden" id="checkid" name="checkid">
					<input type="hidden" id="guarantee" />
					</form>
					</div>
			</section>
				<section class="detail_four" >
					<div class="fr" role="button">
						<ul class="clearfix">
							<li id="checkin" onclick="checkin();"><span class="button_margin button_color">入住</span></li>
							<li id="cancleorder" onclick="cancleorder();"><span class="button_margin button_color">取消订单</span></li>
							<li id="lookBill" onclick="queryBill(1);"><span class="button_margin button_color">查看账单</span></li>
							<!-- <li id="lookHouseOrder" onclick="lookHouseOrder();"><span class="button_margin button_color">查看订单</span></li> -->
							<%--<li id="showeditorder" onclick="showeditorder();"><span class="button_margin update_order">修改订单</span></li>
						--%></ul>
					</div>
				</section>
			</div> 
		</div>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipmsinhouse/workbillroomInHouse/util.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipmsinhouse/workbillroomInHouse/checkuser.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipmsinhouse/check.js"></script>	
	    <script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
			var path = "<%=request.getContextPath() %>";
			var houseorderid = '<%=request.getParameter("houseorderid")%>';
			var reception = '<%=request.getParameter("reception")%>';
			var roomRemark = '<%=request.getParameter("roomRemark")%>';
			
			var checkid = houseorderid;
			var jspStatus = $("#jspStatus").val();
			$(function(){
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
			
			function quertHouseOrderInfo(){
				$.ajax({
			         url: path + "/quertHouseOrderInfo.do",
					 type: "post",
					 dataType: "json",
					 data : {houseOrderId : houseorderid},
					 success: function(json) {
						 var roomRemark = json[0]["ROOMREMARK"] == null?"":json[0]["ROOMREMARK"]
						 var reception = json[0]["RECEPTIONREMARK"]	== null?"":json[0]["RECEPTIONREMARK"]
						 	$("#BRANCH_ID").val(json[0]["BRANCH_ID"]);
						 	$("#roomid").val(json[0]["ROOMID"]);
						 	$("#roomtype").val(json[0]["RMTYPE"]);
						 	$("#arrivaltime").val(json[0]["ARRIVALTIME"]);
						 	$("#leavetime").val(json[0]["LEAVETIME"]);
						 	$("#orderuser").val(json[0]["ORDERUSER"]);
						 	$("#mphone").val(json[0]["MPHONE"]);
						 	$("#source").val(json[0]["SOURCE"]);
						 	$("#memberrank").val(json[0]["MEMBERRANK"]);
						 	$("#remark").val(json[0]["REMARK"]);
						 	$("#guarantee").val(json[0]["GUARANTEENAME"]);
						 	$("#reception").text(reception);
						 	$("#roomRemark").text(roomRemark);
							var start = json[0]["ARRIVALTIME"];
						 	var now = new Date();    
						    var year = now.getFullYear();       //年   
						    var month = now.getMonth() + 1;     //月   
						    var day = now.getDate();
						    var time = year+"/"+month+"/"+day;
							var starttime = new Date(start);
							var endtime = new Date(time);
							if(json[0]["STATUSNAME"] == '1' && Date.parse(starttime) > Date.parse(endtime)){
								$("#checkin").hide();
							} else if(json[0]["STATUSNAME"] != '1' || json[0]["GUARANTEENAME"] == '1'){
						 		$("#checkin").hide();
						 		if(json[0]["STATUSNAME"] != '1'){
						 			$("#cancleorder").hide();
						 		};
							};
					 },
					 error: function(json) {}
				});
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
				var oUl =$(".check_ul");
				var lis = oUl.find("li");
				if(isCheckout(houseorderid)){
					return false;
				}
				if (oUl.length == 1 && !oUl.find("label:hidden").text()) {
					showMsg("请至少填写一位入住人！");
					return false;
				} else if ($("#roomid").val() == null || $("#roomid").val() == "") {
					showMsg("请选择房号！");
					return false;
				} else if (false) { //!isMobile($("#checkphone").val())
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
						 		if(jspStatus == 'null'){
						 			window.parent.document.getElementById('contentFrame_houseone').click();
						 		}else{
						 			window.parent.location.reload();
						 		}
								window.parent.JDialog.close();
						 	} else if (json.result == 1) {
						 		showMsg(json.message);
						 	};
						 },
						 error: function(json) {
						 	showMsg("入住失败!");
						 }
					}); 
				};
			}

			//订单查看
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
				window.parent.JDialog.openWithNoTitle("", path + "/page/ipmshouse/houseorder/order_check.jsp?houseorderid=" + $("#houseorderid").val() + 
						"&roomId=" + $("#roomid").val() +"&customerid="+customerid, 1179, 733);
			}
			
			function showeditorder() {
				if(isCheckout(houseorderid)){
						return false;
					}
				window.JDialog.open("修改订单", path + "/page/ipmshouse/order/editorder.jsp?houseorderid=" + $("#houseorderid").val() , 700, 400);
			}	
	
			function cancleorder() {
				if (isCheckout(houseorderid)) {
					return false;
				}
				var objarr = [];
				if($("#guarantee").val() == 1){
					showMsg("是否确定取消订单?",function(){
						$.ajax({
							url: path + "/cancleorderInHouse.do",
							type: "post",
							datatype: "json",
							data: { 
								orderId: houseorderid,
								status: 0,
								remark:"",
								money:"",
								objarr:objarr
							},
							traditional: true,
							success: function (json) {
								if (json.result == 0) {
									var leftorderserach = $(window.parent.parent.document).find("#left_order_search");
									if( leftorderserach.length == 1){
										leftorderserach.click();
										window.parent.parent.JDialog.close();
									}
									else{
										//$(window.parent.parent.parent.document.getElementById('menu_903')).click();
										$(window.parent.parent.document).find("#contentFrame_houseone").click();
										window.parent.parent.JDialog.close();
									}
								} else if (json.result == 1) {
									window.parent.parent.showMsg(json.message);
									window.setTimeout("window.parent.JDialog.close()", 1800);
									return false;
								} else if (json.result == 2) {
									window.parent.parent.showMsg(json.message);
									window.setTimeout("window.parent.JDialog.close()", 1800);
									return false;
								};
							},
							error: function(json) {
								showMsg("操作失败！");
							}
						});
			 		});
				}else{
					window.JDialog.open("取消订单", path + "/page/ipmshouse/houseorder/secondquery.jsp?orderId=" + houseorderid + "&jspStatus=" + $("#jspStatus").val(), 300,370);
				}
			}
			function queryBill(num){
				window.JDialog.open("账单", path + "/page/ipmshouse/leftmenu/houserefund/house_bill.jsp?checkid=" + houseorderid+"&status="+num+"&guarantee="+$("#guarantee").val(), 1000, 500);
			}
		/* // 查看订单
		function lookHouseOrder(){                            
			window.parent.JDialog.openWithNoTitle("", path + "/page/ipmspage/houseorder/houseOrder_check.jsp?houseorderid=" + houseorderid , 1179, 733);
		} */
		function editCustomerReamrk(e,data){
			var remark; 
			var id;
			if(data == "1"){
				remark =$(e).val();
				id= houseorderid;
			}else if(data == "3"){
				remark =$(e).text();
				id= houseorderid;
			}else if(data == "4"){
				remark =$(e).text();
				id= houseorderid;
			}else{
				remark =  $(e).find("textarea").val();
				id = $("#guest").find("li.active").find("label").html();
			}
			window.JDialog.open("备注", path + "/page/ipmshouse/houseorder/editRemark.jsp?id=" + id + "&remark="+remark+ "&data="+data, 450,200);
		}
	</script>
  </body>
</html>
