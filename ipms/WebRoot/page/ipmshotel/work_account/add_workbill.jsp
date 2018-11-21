<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>入账</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
  </head>
  
  <body>
   <!--入账开始 -->
		<section class="detail_six">
			<div class="high_header">
				<!--  <i class="imooc detail_imooc" style="color:#3B3E40;"></i>	-->
					<div class="margintop">
						<form id="bill_date">
							<ul class="clearfix">
						      <li><label class="label_name">工作账单号</label><input name="workbillid" type="text" value="${ workbillid }" id="id" class="text_edit" readonly></li>
						      <li style="position:relative;">
							      <label class="label_name">项目</label>
							      <input id="project" name="project" type="text" value="" class="text_edit margin_text" onclick="onbill()">
							      <div id="ontime" class="ontime fadeIn"></div>
						      </li>
						      <li><label class="label_name">金额</label><input id="amount" name="amount" type="number" min=0 value="" class="text_number" ></li>
						      <li><label class="label_name">备注</label> <textarea name="remark" col="100" rols="10"></textarea></li>
						      <li onclick="submitbill()"><span role="button" class="button confirm" >确定</span></li>
			       		   </ul>
			       		   <input type="hidden" id="projectid" name="projectid">
		       		   </form>
	    		 </div>
			</div>
		</section>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/tipInfo-lj.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
			function submitbill(){
				if(isNull($("#amount"))){
					return false;
				}
				showMsg("确认入账?", submitbilldata, closeparent)
			}
			
			function closeparent(){
			 	window.parent.JDialog.close();
			}
			
			function submitbilldata(){
				$.ajax({
			         url: path + "/addworkbill.do",
					 type: "post",
					 data : $("#bill_date").serialize(),
					 success: function(json) {
						 showMsg(json.message);
						 setTimeout("refreshpage()", 2000);
					 },
					 error: function(json) {}
				});
			}
			
			$(".margin_text").on("click",function(){
				$(".ontime").show();
				$(document).on("click", function () {//对document绑定一个影藏Div方法
					$("#ontime").hide();
				});
				event.stopPropagation();
			});
			
			function refreshpage(){
				 $(window.parent.document).find(".tab_two").click();
				 //$(window.parent.parent.parent.document.all[147].contentDocument.logFrame.document.forms[0]).submit();
				 window.parent.JDialog.close();
			}
			
			function onbill(){
				document.getElementById("ontime").innerHTML='<iframe src="' + path +'/page/ipmshotel/common_addbill/commbill.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
		</script>
  </body>
</html>
