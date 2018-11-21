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
    
    <title>公寓退房</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/secpage.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<style>
		
	</style>
  </head>
  
  <body>
  	 <div class="update_myorder">	
 		<input type="text" id="orderId" value="<%=request.getParameter("orderId")%>" hidden="true"/>
  		<div style="width: 100%; text-align: center; font-size: 14px; padding:12% 0;">
  			是否正常退房?
  		</div>
  		<div>
			<span class="add_conroom query" role="button" onclick="checkOut(0)">是</span>
			<span class="add_conroom cancel fl" role="button" onclick="checkOut(1)" style="margin-left: 20px;">否</span>
		</div>
  	</div>
  </body>
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  <script>
  		var path = "<%=request.getContextPath() %>";
  		function checkOut(status) {
  			$.ajax({
	         url: path + "/queryApartmentCost.do",
			 type: "post",
			 dataType: "json",
			 data : { contractId: $("#orderId").val() },
			 success: function(json) {
				 if(json["amount"] == 0){
					 $.ajax({
				         url: path + "/apCheckOut.do",
						 type: "post",
						 dataType: "json",
						 data : { 
							 contractId : $("#orderId").val(),
							 status : status
							  },
						 success: function(json) {
						 	if (json.result == 0) { 
						 		showMsg("退房成功!");
						 		window.setTimeout("window.parent.parent.JDialog.close()",1000);
						 		window.setTimeout("window.parent.parent.parent.document.getElementById('menu_910').click()",950);
						 	} else if (json.result == 1) {
						 		showMsg(json.message);
						 	}	
						 },
						 error: function(json) {
						 	showMsg("退房失败!");
						 }
					});
				 } else {
					 window.parent.parent.JDialog.open("水电费", path + "/queryCost.do?contractId=" + $("#orderId").val() +"&status=" + status, 750, 430);
				 }
			 },
			 error: function(json) {
			 	showMsg("退房失败!");
			 }
		});
  			
			
		}
  </script>
</html>
