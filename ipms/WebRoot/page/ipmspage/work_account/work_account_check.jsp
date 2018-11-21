<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>工作帐</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/work_account/work_account_check.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
  </head>
  
  <body>
   	   <div class="check_wrap_margin check_color">
			<div class="top_check">
				<div class="left_check fl">
				<span class="top_icon"  onclick="window.parent.JDialog.close();"><i class="imooc im_top">&#xea0f;</i></span>
				<span class="cn">订单号<span class="on order-number">2063993</span></span>
				</div>
				<div class="right_check fl">
					<ul>
						<li class="tab_five" onclick="changefive()"><i class="fa fa-hourglass-end"></i><span>日志</span></li>
						<li class="tab_two" onclick="changetwo()"><i class="fa fa-file-text"></i><span>工作账单</span></li>
						<li class="tab_one tab_select"><i class="fa fa-wpforms"></i><span>工作帐</span></li>
					</ul>
				</div>
			</div>
			<div class="content_color">
				<div class="ifa" id="f">
					<div class="check_one fl">
						<form id="work_bill_creat" action="" method="post">
							<ul class="clearfix">
								<li><label class="label_name">单号</label><input id="workbillId" name="workbillId" type="text" value="" class="check" readonly="readonly"></li>
								<li><label class="label_name">名称</label> <input id="name" name="name" type="text" value="" class="check" ></li>
								<li><label class="label_name">说明</label> <input id="describe" name="describe" type="text" value="" class="check"></li>
								<li><label class="label_name">创建日期</label> <input id="recordTime"  type="text" value="" class="check" readonly="readonly"></li>
								<li><label class="label_name">创建者</label> <input id="recordUser" name="recordUser" type="text" value="" class="check" readonly="readonly"></li>
								<li><label class="label_name">结账日期	</label><input id="finalTime"  type="text" value="" class="check" readonly="readonly"></li>
								<li><label class="label_name">结账者</label> <input id="finalUser" name="finalUser" type="text" value="" class="check" readonly="readonly"></li>
								<li><span><input type="button" id="submitbill" value="修改" class="button_margin update" onclick="updatework()"/></span></li>
							</ul>
							<input id="recordTimetemp" name="recordTime" type="hidden" >
						</form>
					</div>
				</div>
				<div id="a" style="display:none;height:626px;"></div>
				<div id="d" style="display: none;height:626px;"></div>

			</div>
		</div>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath()%>";
			var workbillid = "<%=request.getParameter("workbillid")%>";
			$(function(){
				getworkbillinfo();
			});
			
		
			function getworkbillinfo(){
				$.ajax({
			         url: path + "/getworkbillinfo.do",
					 type: "post",
					 data : {workbillid : workbillid},
					 success: function(json) {
					 	$(".on").html(json["workbill"]["workbillId"]);
					 	$("#workbillId").val(json["workbill"]["workbillId"]);
					 	$("#recordTime").val(json["workbill"]["recordTime"]);
					 	$("#recordUser").val(json["recordUserName"]);
					 	$("#name").val(json["workbill"]["name"]);
					 	$("#describe").val(json["workbill"]["describe"]);
					 	$("#finalUser").val(json["finalUserName"]);
					 	$("#finalTime").val(json["workbill"]["finalTime"]);
					 },
					 error: function(json) {}
				});
			}
			
			function updatework(){
				//console.log($(window.parent.parent.document.all[160].contentDocument.logFrame.document.forms[0]).submit());
				var name = $("#name").val();
				var describe = $("#describe").val();
				$.ajax({
			         url: path + "/updateworkbill.do",
					 type: "post",
					 data : {
					 	describe : describe,
					 	name : name,
					 	workbillid : workbillid
					 },
					 success: function(json) {
					 	if(json.result == 1){
					 		showMsg(json.message);
					 		$(window.parent.parent.document.all[160].contentDocument.logFrame.document.forms[0]).submit();
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			/*账单*/
			function changetwo() {
				document.getElementById("a").innerHTML = '<iframe src="<%=request.getContextPath()%>/page/ipmspage/work_account/work_bill.jsp?workbillid=' + workbillid + '" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
			/*日志*/
			function changefive() {
				document.getElementById("d").innerHTML = '<iframe src="' + path +'/page/ipmspage/room_statistics/log.jsp?type=workbill&checkid='+ workbillid + '" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
				//alert('<iframe src="' + path +'/log.jsp?type=workbill&checkid='+ workbillid + '" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>')
			}
			$(".right_check ul li").on("click", function() {
				$(this).addClass("tab_select");
				$(this).siblings().removeClass("tab_select");
			});
			$(".tab_one").on("click", function() {
				$(".ifa").css("display", "block");
				$("#a").css("display", "none");
				$("#d").css("display", "none");
			});
			$(".tab_two").on("click", function() {
				$("#a").css("display", "block");
				$(".ifa").css("display", "none");
				$("#d").css("display", "none");
			});
			$(".tab_five").on("click", function() {
				$("#d").css("display", "block");
				$(".ifa").css("display", "none");
				$("#a").css("display", "none");
			});
			
			
			
		</script>	
  </body>
</html>
