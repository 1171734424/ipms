<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>编辑停售房</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/stopsell/stophouse_info.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<link href="<%=request.getContextPath()%>/css/initialcss/daterangepikerbootstrap.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/daterangepicker.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<style>
		#logId,#roomId{
			background-color: #f0f0f0;
		}
		#rangeTime {
			width: 717px;
		}
		#remark{
			height: 268px;
		}
		.house_info .adroom {
    		/* padding: 10px 5px; */
   	 		width: 317px;
    		margin-left: 4px;
    		font-size: 14px;
    	}
    	.workaccount_edit ul li label {
    		width: 70px;
		}	
		.house_info {
    		padding:  0;
		}
		.workaccount_info ul {
		    margin: 10px 0 10px 15px;
		}
		 .pageRegion{
				margin-top:-62px;
			}
	</style>
  </head>
  
  <body>
   	   <div class="house_info workaccount_edit">
			<form action="" method="post" id="haltplan_submit">
				<div class="house_info workaccount_info">
					<ul class="clearfix">
						<li><label class="label_name">编号</label><input name="logId" type="text" id="logId" class="adroom text_search" readonly></li>
						<li><label class="label_name">房间号码</label><input name="roomId" type="text" id="roomId" class="adroom text_search" readonly></li>
						<li><label class="label_name">停售类型</label>
							<select name="haltType" id="haltType" class="adroom text_search">
								<option value="1">停售</option>
								<option value="2">维修</option>
							</select>
						</li>
						<li><label class="label_name">停售原因</label>
							<select name="haltReason" id="haltReason" class="adroom text_search">
								<option value="1">马桶损坏</option>
								<option value="2">天花漏水</option>
								<option value="3">地板变形</option>
								<option value="4">设施损坏</option>
								<option value="5">其他原因</option>
							</select>
						</li>
						<li><label class="label_name">日期范围</label><input name="rangeTime" type="text" value="" id="rangeTime" class="adroom text_search">
						<i class="fa fa-calendar-check-o"></i></li>
						<!--<li><label class="label_name">开始日期：</label><input name="strstartTime" type="text" value="" id="strstartTime" class="adroom text_search">
						<i class="fa fa-calendar-check-o"></i></li>
						<li><label class="label_name">预计结束日期：</label><input name="strendTime" type="text" value="" id="strendTime" class="adroom text_search">
						<i class="fa fa-calendar-check-o"></i></li>
						--><li>
							<label class="label_name">备注</label>
							<textarea name="remark" class="text_area text_remark" id="remark" rows="10" cols="70" style="width: 717px" onchange="autosaveRemark(this)"></textarea>
						</li>
						<li class="last_li_edit">
							<input id="submithaltpan" type="button" value="修改" class="button_comfirm button_margin" onclick="updatehaltplan()">
						</li>
					</ul>
				</div>
				<input type="hidden" id="branchId" name="branchId">
				<input type="hidden" id="recordUser" name="recordUser">
				<input type="hidden" id="strrecordTime" name="strrecordTime">
				<input type="hidden" id="status" name="status">
			</form>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/initialData/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/moment.js" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/daterangepicker.js" charset="utf-8"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath()%>";
			var logid = "<%=request.getParameter("logid")%>";
			var startTime = "<%=request.getParameter("startTime")%>";
			var endTime = "<%=request.getParameter("endTime")%>";
			$(function(){
				loadsinglehaltplan();
			});
			function loadsinglehaltplan(){
				$.ajax({
			         url: path + "/hotelLoadsinglehaltplan.do",
					 type: "post",
					 data : { logid : logid},
					 success: function(json) {
					 	$("#logId").val(json.data.logId);
					 	$("#branchId").val(json.data.branchId);
					 	$("#roomId").val(json.data.roomId);
					 	$("#haltType").val(json.data.haltType);
					 	$("#haltReason").val(json.data.haltReason);
					 	//$("#strstartTime").val(json.data.startTime.split(" ")[0].replace(/-/g, "/"));
					 	//$("#strendTime").val(json.data.endTime.split(" ")[0].replace(/-/g, "/"));
					 	$("#remark").val(json.data.remark);
					 	$("#recordUser").val(json.data.recordUser);
					 	$("#strrecordTime").val(json.data.recordTime);
					 	$("#status").val(json.data.status);
					 	if(json.data.status == 1){
					 		//$("#submithaltpan").css("background-color", "#a6a6b3");
					 		//$("#submithaltpan").attr("disabled", true);
					 	}
					 	else if(json.data.status == 3){
					 		disableput();
					 		$("#submithaltpan").val("完成停售房");
					 	}
					 	else if(json.data.status == 2){
					 		disableput();
					 		$("#submithaltpan").css("background-color", "#a6a6b3");
					 		$("#submithaltpan").attr("disabled", true);
					 		$("#submithaltpan").val("已完成");
					 	}
					 	else if(json.data.status == 0){
					 		disableput();
					 		$("#submithaltpan").css("background-color", "#a6a6b3");
					 		$("#submithaltpan").attr("disabled", true);
					 		$("#submithaltpan").val("已取消");
					 	}
						$('#rangeTime').daterangepicker({
							"applyClass": "button_comfirm",
						    "locale": {
						        "format": "YYYY/MM/DD",
						        "separator": " - ",
						        "customRangeLabel": "Custom",
						        "firstDay": 1
						    },
						    //"minDate": moment(),
							"startDate": json.data.startTime,
							"endDate": json.data.endTime
						}, function(start, end, label) {
						});
					 },
					 error: function(json) {}
				});
			}
			function updatehaltplan(){
				var status = $("#status").val();
				var dourl = "";
				if(status == 1){
					dourl = "/hotelUpdatehaltpan.do";
				}
				else if(status == 2){
					showMsg("已完成");
					return false;
				}
				else if(status == 3){
					dourl = "/hotelFinishhaltplan.do";
				}
				$.ajax({
			         url: path + dourl,
					 type: "post",
					 data : $("#haltplan_submit").serialize(),
					 success: function(json) {
					 	if(json.result == 0){
						 	showMsg(json.message);
					 	}else if(json.result == 1){
					 		showMsg(json.message);
					 		setTimeout("refresh()", 1000);
					 	}
					 },
					 error: function(json) {}
				});
			}
			function disableput(){
				$("#haltType").attr("disabled", "disabled");
				$("#haltReason").attr("disabled", "disabled");
				$("#rangeTime").attr("disabled", "disabled");
			}
			function rekk(){
				console.log("keyup")
			}
			function autosaveRemark(e){
				var remark = $(e).val();
				var logid = $("#logId").val();
				return false;
				if(remark){
					$.ajax({
				         url: path + "/hotelAutoSaveStopHouseRemark.do",
						 type: "post",
						 data : {
						 	logid : logid,
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
			function refresh(){
				//$(window.parent.parent.document.forms[0]).submit();
				//$(window.parent.parent.parent.document.all.menu_905)
				//console.log($(window.parent.parent.document.all.menu_905))
				if( window.parent.pagea == 1){
					$(window.parent.parent.parent.document.all.menu_905).click();
					window.parent.JDialog.close();
				}
				else{
					$(window.parent.parent.parent.document.all.contentFrame_first).click();
					window.parent.JDialog.close();
				}
			}
		</script>
  </body>
</html>
