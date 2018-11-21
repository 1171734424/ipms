<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>新增停售房</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/stopsell/stophouse_info.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link href="<%=request.getContextPath()%>/css/initialcss/daterangepikerbootstrap.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/daterangepicker.css"/>
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<%
	String roomId = request.getParameter("roomId");
	if(roomId == null){
		roomId = "";
	}
	String flag = request.getParameter("flag");
	if(flag == null){
		flag = "2";
	}
	%>
	<style type="text/css">
		#rangeTime{
			width: 725px;
		}
		.workaccount_info textarea.remark{
			height: 120px;
			margin-left:10px;
		}
		.house_info ul li {
		    margin: 8px 2px !important;
		}
		#sub_button {
			position: absolute;
		    left: 86%;
		    top: 83%;
		}
	</style>
  </head>
  <body>
   	   <div class="house_info workaccount_info">
			<form action="" method="post" id="haltplan_submit">
				<div class="">
					<ul class="clearfix">
						<li><label class="label_name">民宿编号</label><input spellcheck="false" name="roomId" type="text" id="roomId" onclick="roomIdFunction()" class="adroom" value='<%=roomId%>'></li>
						<li><input id="roomId_CUSTOM_VALUE" name="roomName" type="hidden"></li>
						<li><label class="label_name">停售类型</label>
							<select name="haltType"class="adroom">
								<option value="1">停售</option>
								<!--<option value="2">维修</option>-->
							</select>
						</li>
						<li><label class="label_name">停售原因</label>
							<select name="haltReason" class="adroom">
								<option value="1">马桶损坏</option>
								<option value="2">天花漏水</option>
								<option value="3">地板变形</option>
								<option value="4">设施损坏</option>
								<option value="5">其他原因</option>
							</select>
						</li>
						<li><label class="label_name">日期范围</label><input name="rangeTime" type="text" value="" id="rangeTime" class="adroom">
						<i class="fa fa-calendar-check-o"></i></li>
						<!--  <li><label class="label_name">开始日期：</label><input name="startTime" type="text" value="" id="startTime" class="adroom">
						<i class="fa fa-calendar-check-o"></i></li>
						<li><label class="label_name">预计结束日期：</label><input name="endTime" type="text" value="" id="endTime" class="adroom">
						<i class="fa fa-calendar-check-o"></i></li>-->
						<li>
							<label class="label_name">备注</label>
							<textarea name="remark" class="remark" id="remark" rows="10" cols="30"></textarea>
						</li>
						<li class="last_li_one" id="sub_button">
							<input id="submithaltpan" type="button" value="确定" class="button_comfirm button_margin" onclick="submithaltplan()">
						</li>
					</ul>
					
				</div>
			</form>
		</div>
		<script src="<%=request.getContextPath()%>/script/initialData/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/moment.js" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/daterangepicker.js" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath()%>";
			var today = dealLocalDate(new Date());
			$(function(){
				$('#rangeTime').daterangepicker({
					"applyClass": "button_comfirm",
					"startDate": moment(),
    				"endDate": moment().add('day', 1),
				    "locale": {
				        "format": "YYYY/MM/DD",
				        "separator": " - ",
				        "customRangeLabel": "Custom",
				        "firstDay": 1
				    },
					"minDate": moment().add('hours', 1)
				}, function(start, end, label) {
				});
			});
			
			/* $("#roomId").click(function(){
				JDialog.open("民宿选择", path + "/selectHouseOnAddComment.do?types=民宿", 450,350);
			}); */
			
			function roomIdFunction(){
				JDialog.open("民宿选择", path + "/queryStopHouseId.do", 450,350);
			}
			
			function submithaltplan(){
				if(isNull($("#roomId"), "房号：")){
					return false;
				}
				$.ajax({
			         url: path + "/submithaltpanOnHouse.do",
					 type: "post",
					 data : $("#haltplan_submit").serialize(),
					 success: function(json) {
					 	if(json.result == 0){
						 	showMsg(json.message);
					 	}else if(json.result == 1){
					 		showMsg(json.message);
					 		setTimeout("refresh()", 2000);
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			function refresh(){
				//$(window.parent.parent.document.all[161].contentDocument.logFrame.document.forms[0]).submit();
				if (<%=flag%> == 1) {
					$(window.parent.parent.document.all.contentFrame_first).click();
				} else {
					$(window.parent.parent.document.all.menu_905).click();
				}
				window.parent.JDialog.close();
			}
		</script>
  </body>
</html>
