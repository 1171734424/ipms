<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%String a = (String)request.getAttribute("workBillId");%>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>账单备注</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
  </head>
  
  <body>
		<section class="detail_six add_pro">
			<div class="high_header">
					<div class="margintop">
						<form id="bill_date">
							<ul class="clearfix">
							  <li><label class="label_name">备注：</label><textarea id="billReamrk" name="content" spellcheck="false"></textarea></li>
						      <li onclick="submitPromptRecord()"><span role="button" style="margin-right: 6px;"class="button confirm" >保存</span></li>
			       		   	</ul>
			       		   	<input type="hidden" name="workBillId" id="workBillId" value="<%=a %>">
			       		</form>
	    		 </div>
			</div>
		</section>
			<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
			<script type="text/javascript">
			var path = "<%=request.getContextPath() %>";
			var text = "<%=request.getAttribute("text") %>";
			var workBillId = "<%=request.getAttribute("workBillId") %>";
				$(document).ready(function(){
					$("#billReamrk").focus().val(text);
				})
				/*确认刷新父页面并将数据保存至后台*/
				function submitPromptRecord(){
				var remark = $("#billReamrk").val();
				$.ajax({
			         url: path + "/addRemarkToworkBillDetail.do",
					 type: "post",
					 data : {billId : workBillId,
					 		remark : remark
					 },
					 success: function(json) {
					 	if(json.result == "1"){//修改成功
							 refresh();
					 	}
					 	if(json.result == "2"){//异常
						 	showMsg(json.message);
					 	}
					 },
					 error: function(json) {}
				});
				}
			function refresh(){
			    $(window.parent.document).find(".tab_two").click();
				window.parent.JDialog.close();
			}
			
			</script>
  </body>
</html>
