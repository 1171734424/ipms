<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<%
	request.setAttribute("pagetype", request.getAttribute("pagetype"));
%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>新增工作账</title>
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
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </head>
  
  <body>
   	   <div class="house_info createworkbill">
			<form action="" method="post" id="work_bill_creat">
				<div class="">
					<ul class="clearfix">
					 <input type="hidden" id="pagetype" name="pagetype" value="<%=request.getParameter("pagetype")%>"/>
						<!-- <li><label class="label_name">单号</label><input style="background-color: #f0f0f0" id="workbillId" name="workbillId" type="text" readonly class="adroom"></li> -->
						<li><label class="label_name">名称</label> <input id="name" name="name" type="text" value="" class="adroom text_add" ></li>
						<li><label class="label_name">说明</label> <input id="describe" name="describe" type="text" value="" class="adroom text_add"></li>
						<li><label class="label_name">创建日期</label> <input style="background-color: #f0f0f0" id="recordTime"  type="text" readonly class="adroom text_add"></li>
						<li><label class="label_name">创建者</label> <input style="background-color: #f0f0f0" id="recordUser" name="recordUser" type="text" readonly class="adroom text_add"></li>
						<li><label class="label_name">结账日期	</label><input id="finalTime"  type="text"  class="adroom text_add" disabled></li>
						<li><label class="label_name">结账者</label> <input id="finalUser" name="finalUser" type="text" value="" class="adroom text_add" disabled></li>
						<li class="last_li"><span><input  type="button" id="submitbill" value="确定" class="button_margin button_confirm" onclick="submitbill1()"/></span></li>
					</ul>
				</div>
			</form>
		</div>
		<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath()%>";
			var workbillid = "<%=request.getParameter("workbillid")%>";
			$(function (){
				getcreatworkbillinfo();
				$("#name").focus();
			});
			
			function getcreatworkbillinfo(){
				$.ajax({
			         url: path + "/getcreatworkbillinfo.do",
					 type: "post",
					 data : {},
					 success: function(json) {
					 	console.log(json)
					 	$(".rno").html(json.workbill.workbillId);
					 	//$("#workbillId").val(json.workbill.workbillId);
					 	$("#recordTime").val(json.recordTime);
					 	$("#recordUser").val(json.staffName);
					 	$("#recordTimetemp").val(new Date());
					 },
					 error: function(json) {}
				});
			}
			
			function submitbill1() {
				if (isNull($("#name"), "名称：")) {
					return false;
				}
				$.ajax({
			         url: path + "/creatworkbill.do",
					 type: "post",
					 data : $("#work_bill_creat").serialize(),
					 success: function(json) {
					 	if (json.result == 1) {
					 		$(window.parent.document).find("#menu_908").click();
					 		if($("#pagetype").val()=="gdssale"){
					 			$(window.parent.document.all.goodsIframe.contentDocument.all.jd_iframe.contentWindow.location.reload(true));
					 		}
					 		window.parent.JDialog.close();
					 	} else if (json.result == 0){
					 		showMsg(json.message);
					 	}
					 },
					 error: function(json) {}
				});
			}
		</script>	
  </body>
</html>
