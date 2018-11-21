<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>房单查看</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
	<script>var base_path = "<%=request.getContextPath()%>";</script>
	<style type="text/css">
	.hot {
		display: none;
	}
	</style>
  </head>
 
  <body>
   	<div class="check_wrap_margin check_color">
			<div class="top_check">
				<div class="left_check fl">
					<span class="top_icon"><i class="imooc im_top" onclick="window.parent.JDialog.close();">&#xea0f;</i></span>
					<span class="cn">房号 <span class="rno" id="roomid"></span></span><span class="cn" ><!--<span class="on">2063993</span>--></span><span class="cm" id="status">【状态】</span>
				</div>
				<div class="right_check fr">
					<ul>
						<li class="tab_one" onclick="changeone()"><i class="fa fa-users"></i><span>客单</span></li>
						<li class="tab_two" onclick="changetwo()"><i class="fa fa-th-list"></i><span>账单</span></li>
						<!--<li class="tab_three" onclick="changethree()"><i class="fa fa-folder-open-o"></i><span>房租计划</span></li>
						--><li class="tab_four" onclick="changefour()"><i class="fa fa-bell"></i><span class="hot">9</span><span>提示</span></li>
						<li class="tab_five" onclick="changefive()"><i class="fa fa-columns"></i><span>日志</span></li>
						<!--<li class="tab_six" onclick="changesix()"><i class="fa fa-tasks"></i><span>财务计划</span></li>
					--></ul>
				</div>
			</div>
			<div class="content_color">
				<div class="roomdata">
					<ul class="clearfix">  
					<!-- 入住人房间类型*/ -->            
						<li class="roomdata_li">
							<p class="roomdata_one">
								<span>房间类型：<span id="roomtype">自主大床房</span></span>
							</p>
							<p class="roomdata_one">
								<span>房价：<span id="roomprice">177</span></span>
							</p>
							<p class="roomdata_one">
								<span>入住时间：<span id="checkintime">2017-06-01 08:23</span></span>
							</p>
							<p class="roomdata_one">
								<span>离店时间：<span id="checkouttime">2017-06-02 08:23</span></span>
							</p>
						</li>
						<!--  /*入住人信息*/-->
						<li class="roomdata_two">
							<p class="roomdata_one">
								<span>预订人：<span id="orderusername">老铁</span></span>
							</p>
							<p class="roomdata_one">
								<span>预定手机：<span id="mphone">17790909090</span></span>
							</p>
							<p class="roomdata_one">
								<span>来源：<span id="source">哪里</span></span>
							</p>
							<!-- <p class="roomdata_one">
								<span>备注<span id="checkremark">...</span></span>
							</p> -->
							<p class="roomdata_one">
								<span>客人名：<span id="checkusername">one、two、three</span></span>
							</p>
							<p class="roomdata_one" id="remark_block">
								<span>备注：<span id="remark">...</span></span>
							</p>
							
						</li>
						<!-- 关联房 -->
						<li class="roomdata_three">
							<h4>关联房</h4>
							<ul class="clearfix connec_room" id="roommapping_data">
								<li><a>401</a></li>
								<li><a>402</a></li>
							</ul>
						</li>
						<!--消费 -->
						<li class="roomdata_four">
							<p class="roomdata_one">
								<span>消费：<span id="cost">0</span></span>
							</p>
							<p class="roomdata_one">
								<span>结算：<span id="pay">0</span></span>
							</p>
							<p class="roomdata_one">
								<span>账面金额：<span id="accountfee">0</span></span>
							</p>
							<p class="roomdata_one">
								<span>平账余额：<span id="totalfee"></span></span>
							</p>
						</li>
						<li class="roomdata_five"  role="button">
							<div role="button" onclick="checkout()">结账</div>
						</li>
					</ul>
				</div>
				<div id="customer" style="height:626px;">
				</div>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/customer/customer.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
			var checkid = "<%=request.getParameter("checkid") %>";
			var type = "<%=request.getParameter("type") %>";
			$(document).ready(function(){
				if (type == 'check') {
					$(".tab_one").click();
				} else if (type == 'bill') {
					$(".tab_two").click()
				} else if (type == 'plan') {
					$(".tab_three").click()
				} else if (type == 'hint') {
					$(".tab_four").click()
				}
			})
		
			var path = "<%=request.getContextPath() %>";
			$(function(){
				loadCheckData();
				loadpayandcost();
				countPrompt();
				loadroommapping();

			});
			
			function checkout(){
			
				if(isCheckout(checkid)){
					return false;
				}
				
				$.ajax({
			         url: path + "/checkroompayoff.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	if(json.result == 1){
							JDialog.open("退房", path + "/page/ipmshotel/room_statistics/checkout.jsp?checkid=" + checkid, 700, 620);
						}else {
							showMsg(json.message);
						}
					 	
					 },
					 error: function(json) {}
				});
			}
			
			function countPrompt(){
				$.ajax({
			         url: path + "/countPrompt.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	if(json[0]["NUM"] != 0){
					 		$(".hot").show();
							$(".hot").html(json[0]["NUM"]);
					 	}
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
						 $("#cost").html(json["cost"]);
						 $("#pay").html(json["pay"]);
						 $("#accountfee").html((json["pay"] - json["cost"]).toFixed(2));
						 $("#totalfee").html((json["pay"] - json["cost"] - json["subprice"]).toFixed(2));
					 	
					 },
					 error: function(json) {}
				});
			}
			
			function loadCheckData(){
				$.ajax({
			         url: path + "/loadCheckData.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
						 console.log(json)
					 	$("#roomid").html(json[0]["ROOMID"]);
					 	$("#status").html("【" + json[0]["STATUSNAME"] + "】");
					 	$("#roomtype").html(json[0]["ROOMNAME"]);
					 	$("#roomprice").html(json[0]["ROOMPRICE"] + " 元");
					 	$("#checkintime").html(dealDate(json[0]["CHECKINTIME"]));
					 	$("#checkouttime").html(dealDate(json[0]["CHECKOUTTIME"]));
					 	$("#source").html(json[0]["DECODESOURCE"]);
					 	$("#orderusername").html(json[0]["ORDERUSERNAME"]);
					 	$("#mphone").html(json[0]["MPHONE"]);
					 	$("#checkusername").html(json[0]["CHECKUSERNAME"]);
					 	if(json[0]["REMARK"]){
						 	$("#remark").html(json[0]["REMARK"]);					 		
					 	}else {
					 		$("#remark_block").css("display", "none");
					 	}
					 	
					 },
					 error: function(json) {}
				});
			}
			
			function loadroommapping(){
				console.log(checkid)
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
			
			function loadrooommapingdata(json){
				var data = '';
			 	$.each(json, function(index){
			 		data = data + "<li><a>" + json[index]["ROOMID"] + "</a>";
			 				/*"<p>" + json[index]["MEMBERNAME"] + "</p></li>";*/
			 	});
			 	if(json.length == 0){
			 		$("#roommapping_data").html("<li><a>无</a>");
			 	}else{
			 		$("#roommapping_data").html(data);			 	
			 	}
			}
			
		</script>
  </body>
</html>




