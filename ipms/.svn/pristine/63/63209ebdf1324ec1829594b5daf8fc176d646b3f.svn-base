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
		span.typetitle{
			text-align: right;
		    display: inline-block;
		    width: 26%;	
		}
		span.typename{
			text-align: left;
	    	display: inline-block;
		}
		.roomdata_one{
		}
		.roomdata_one textarea{
			background-color: transparent;
		    color: #fff;
		    font-size: 16px;
		    border: none;
		    resize: none;
		    padding-left: 0;
		    vertical-align: top;
		    height: 109px;
		    margin-left: -5px;
			user-select:none;
		}
		.pageRegion{
				margin-top:-62px;
			}
	</style>
  </head>
 
  <body>
   	<div class="check_wrap_margin check_color">
			<div class="top_check">
				<div class="left_check fl">
					
					<span class="cn">房号 <span class="rno" id="roomid"></span></span><span class="cn" ><!--<span class="on">2063993</span>--></span><span class="cm" id="status">【状态】</span>
					<input type="hidden" id="checkid"><!-- checkid账单判断是否是本账单 -->
				</div>
				<div class="right_check fr">
					<ul>
						<li class="tab_one" onclick="changeone()"><i class="fa fa-users"></i><span>客单</span></li>
						<li class="tab_two" onclick="changetwo()"><i class="fa fa-th-list"></i><span>账单</span></li>
						<li class="tab_three" onclick="changethree()"><i class="fa fa-folder-open-o"></i><span>房租计划</span></li>
						<li class="tab_four" onclick="changefour()"><i class="fa fa-bell"></i><span class="hot">9</span><span>提示</span></li>
						<li class="tab_five" onclick="changefive()"><i class="fa fa-columns"></i><span>日志</span></li>
				<!--	<li class="tab_six" onclick="changesix()"><i class="fa fa-tasks"></i><span>财务计划</span></li> -->
					</ul>
					<span class="top_icon fr" onclick="window.parent.JDialog.close();"><i class="imooc im_top">&#xea0f;</i></span>
				</div>
			</div>
			<div class="content_color">
				<div class="roomdata">
					<ul class="clearfix">  
					<!-- 入住人房间类型*/ -->            
						<li class="roomdata_li">
							<p class="roomdata_one">
								<span class="typetitle">房间类型：</span><span id="roomtype" class="typename">自主大床房</span>
							</p>
							<p class="roomdata_one">
								<span class="typetitle">房价：</span><span id="roomprice" class="typename">177</span>
							</p>
							<p class="roomdata_one">
								<span class="typetitle">入住时间：</span><span id="checkintime" class="typename">2017-06-01 08:23</span>
							</p>
							<p class="roomdata_one">
								<span class="typetitle">离店时间：</span><span id="checkouttime" class="typename">2017-06-02 08:23</span>
							</p>
						</li>
						<!--  /*入住人信息*/-->
						<li class="roomdata_two">
							<p class="roomdata_one">
								<span class="typetitle">预订人：</span><span id="orderusername">老铁</span>
							</p>
							<p class="roomdata_one">
								<span class="typetitle">预定手机：</span><span id="mphone">17790909090</span>
							</p>
							<p class="roomdata_one">
								<span class="typetitle">来源：</span><span id="source">哪里</span>
							</p>
							<!-- <p class="roomdata_one">
								<span>备注<span id="checkremark">...</span></span>
							</p> -->
							<p class="roomdata_one">
								<span class="typetitle">客人名：</span><span id="checkusername">one、two、three</span>
							</p>
							<p class="roomdata_one" id="remark_block">
								<span class="typetitle">备注：</span>
								<textarea id="remark" col="60" row="4" disabled=disabled readonly></textarea>
								<!-- <span id="remark" style="width:100%;height:82px;word-break:normal;white-space:pre-wrap;overflow:auto;">...</span> -->
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
								<span class="typetitle">消费：</span><span id="cost">0</span>
							</p>
							<p class="roomdata_one">
								<span class="typetitle">结算：</span><span id="pay">0</span>
							</p>
							<p class="roomdata_one">
								<span class="typetitle">账面金额：</span><span id="accountfee">0</span>
							</p>
							<p class="roomdata_one">
								<span class="typetitle">平账金额：</span><span id="totalfee"></span>
							</p>
						</li>
						<li class="roomdata_five"  role="button" >
							<div role="button" id="checkout"></div>
						</li>
						<li role="button" class="li_recheckIn">
							<span class="recheckIn button_margin" id="recheckin">重新入住</span>
						</li>
					</ul>
				</div>
				<div id="customer" style="height:643px;display:none;">
				</div>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipmshotel/customer/customer.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
			var checkid = "<%=request.getParameter("checkid") %>";
			var type = "<%=request.getParameter("type") %>";
			var path = "<%=request.getContextPath()%>";
			var pagea = "<%=request.getParameter("pagea")%>"; //pagea 用来判断roomlist是来自与房单还是房态图，如果为-1则是来自于房单
			var checkstatus = "";
			$(function(){
				if (type == 'check') {
					$(".tab_one").click();
				} else if (type == 'bill') {
					$(".tab_two").click();
				} else if (type == 'plan') {
					$(".tab_three").click();
				} else if (type == 'hint') {
					$(".tab_four").click();
				}
				loadCheckData();
				loadallcheckuser();
				loadpayandcost();
				countPrompt();
				loadroommapping();
				checkrollback();
				iscustomerValid();
			});
			

			
			function iscustomerValid(){
				$.ajax({
			         url: path + "/getcheckStatus.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	if(json.data){
					 		checkstatus = json.data.status;
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			function recheckin(){
				JDialog.open("重新入住", path + "/page/ipmshotel/room_statistics/recheckin.jsp?checkid=" + checkid, 580, 400);
			}
			
			function checkrollback(){
				$.ajax({
			         url: path + "/checkrollback.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	if(json.result == 1){
							if(json.data == 1){
								$("#checkout").html("结算");
								$("#recheckin").css("color", "rgb(154, 149, 149)");
								$("#checkout").click(function (){
									checkout();
									//rollback();
								});
							}else if(json.data == 2){
								$("#checkout").html("取消结账");;
								//$("#recheckin").css("color", "#d8d5d5");
								$("#checkout").click(function (){
									rollback();
								});
								$("#recheckin").click(function(){
									recheckin();
								});
							} else if(json.data == 3){
								$("#checkout").html("结算(房间已退)");
								$("#checkout").click(function (){
									checkout();
								});
								$("#recheckin").click(function (){
									recheckin();
								});
							}else if(json.data == 4){
								$("#checkout").html("取消结账");
								$("#checkout").css("color", "rgb(154, 149, 149)");
								$("#recheckin").css("color", "rgb(154, 149, 149)");
								$("#recheckin").hidde();
							}
						}else {
							showMsg(json.message);
						}
					 	
					 },
					 error: function(json) {}
				});
			}
			
			function rollback(){
				$.ajax({
			         url: path + "/rollbackcheckout.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	if(json.result == 1){
					 		window.parent.showMsg(json.message);
					 		setTimeout("refresh()", 2000);
						}else {
							window.parent.showMsg(json.message);
							setTimeout("refresh()", 2000);
						}
					 },
					 error: function(json) {}
				});
			}
			
			function checkout(freshtype){
				if(isCheckout(checkid)){
					return false;
				}
				JDialog.open("退房", path + "/page/ipmshotel/room_statistics/checkout.jsp?checkid=" + checkid + "&freshtype=1&page=" + pagea, 700, 520);
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
					 	}else {
					 		$(".hot").hide();
					 	}
					 },
					 error: function(json) {}
				});
			}
			
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
					 		window.parent.JDialog.openWithNoTitle("", path + "/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=" + json.data.checkId,1179,733);
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
						 $("#cost").html(json["uncheckoutCost"].toFixed(2));
						 $("#pay").html(json["uncheckoutPay"].toFixed(2));
						 $("#accountfee").html((json["uncheckoutCost"] - json["uncheckoutPay"]).toFixed(2));
						 $("#totalfee").html((json["shouldPay"]).toFixed(2));
					 },
					 error: function(json) {}
				});
			}
			
			function loadallcheckuser(){
				$.ajax({
			         url: path + "/loadallcheckuser.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
					 	let checkusername = "";
					 	$.each(json.data, function(index){
					 		if(json.data[index].status == 1){
						 		checkusername = checkusername + json.data[index].checkuserName + "、";					 		
					 		}
					 	});
					 	checkusername = checkusername.substring(0, checkusername.length - 1);
					 	$("#checkusername").text(beyondContent(checkusername, 15));
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
					 	$("#checkid").val(json[0]["CHECKID"]);
					 	$("#roomid").html(json[0]["ROOMID"]);
					 	$("#status").html("【" + json[0]["STATUSNAME"] + "】");
					 	$("#roomtype").html(json[0]["ROOMNAME"]);
					 	$("#roomprice").html(json[0]["ROOMPRICE"] + " 元");
					 	$("#checkintime").html(dealDate(json[0]["CHECKINTIME"]));
					 	$("#checkouttime").html(dealDate(json[0]["CHECKOUTTIME"]));
					 	$("#source").html(json[0]["DECODESOURCE"]);
					 	$("#orderusername").html(json[0]["ORDERUSERNAME"]);
					 	$("#mphone").html(json[0]["MPHONE"]);
					 	//$("#checkusername").html(json[0]["CHECKUSERNAME"]);
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
			 		data = data + "<li onclick='jumptocustom(this)'><a>" + json[index]["ROOMID"] + "</a>";
			 	});
			 	if(json.length == 0){
			 		$("#roommapping_data").html("<li><a>无</a>");
			 	}else{
			 		$("#roommapping_data").html(data);		 	
			 	}
			}
			
			function refresh(){
				 if ($(window.parent.parent.parent.document).find("iframe")[1]){
				 	$($(window.parent.parent.parent.document).find("iframe")[1].contentDocument.logFrame.document.forms[0]).submit();
					window.parent.parent.parent.JDialog.close();
				 }
			}
			
		</script>
  </body>
</html>
