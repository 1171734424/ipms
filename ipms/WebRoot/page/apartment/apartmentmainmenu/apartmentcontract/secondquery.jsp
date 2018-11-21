<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公寓退房</title>
    
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/secpage.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/pageloading.css"/>
	<!-- <style>
	.c_loading{
	    background-color: #FFFFFF;
 
    display: none;
    height: 100%;
    
  /*   position: fixed !important; */
  border:none;
    position: absolute;
    left: 25%;
    top: 40%;
    width: 100%;
    text-align: center;
    z-index: 50;
    line-height: 180px;}
	</style> -->
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
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/apartment/apartmentmainmenu/apartmentcontract/util.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/pageloading.js"></script>
  <script>
  		var path = "<%=request.getContextPath() %>";
  		function checkOut(status) {
  			PageLoading.beginPageLoading("正在退房中...");
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
						 		PageLoading.beginPageLoading("退房成功!");
								window.setTimeout("PageLoading.endPageLoading()", 1000);
						 		window.setTimeout("window.parent.parent.JDialog.close()",1000);
						 		window.setTimeout("window.parent.parent.parent.document.getElementById('menu_910').click()",950);
						 	} else if (json.result == 1) {
						 		showMsg(json.message);
						 	}	
						 },
						 error: function(json) {
							PageLoading.beginPageLoading("退房失败!");
							window.setTimeout("PageLoading.endPageLoading()", 1000);
						 }
					});
				 } else {
					 window.parent.parent.JDialog.open("水电费", path + "/queryCost.do?contractId=" + $("#orderId").val() +"&status=" + status, 750, 430);
				 }
			 },
			 error: function(json) {
				PageLoading.beginPageLoading("退房失败!");
				window.setTimeout("PageLoading.endPageLoading()", 1000);
			 }
		});
  			
			
		}
  </script>
</html>
