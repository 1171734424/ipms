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
    
    <title>取消订单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/secpage.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	
  </head>
  
  <body>
  	 <div class="update_myorder">	
 		<input type="text" id="orderId" value="<%=request.getParameter("orderId")%>" hidden="true"/>
 		<input type="text" id="bookvalue" value="<%=request.getParameter("bookvalue")%>" hidden="true"/>
 		
  		<p>确定取消该订单吗？</p>
  		<div class="mybutton buttonclass">
			<span class="add_conroom query" role="button" onclick="cancleorder()">确定</span>
			<span class="add_conroom cancel fl myclass" role="button" onclick="cancle()">取消</span>
		</div>
  	</div>
  </body>
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  <script>
  		var path = "<%=request.getContextPath() %>";
		function cancleorder() {
			$.ajax({
				url: path + "/cancleorder.do",
				type: "post",
				datatype: "json",
				data: { orderId: $("#orderId").val() },
				success: function (json) {
					if (json.result == 0) {
						//showMsg(json.message);
						//window.setTimeout("window.parent.location.reload(true)",1800);
						//$(window.parent.parent.parent.document.getElementById('menu_903')).click();
						//window.parent.parent.JDialog.close();
						//window.parent.JDialog.close();
						var leftorderserach = $(window.parent.parent.document).find("#left_order_search");
						if( leftorderserach.length == 1){
							leftorderserach.click();
							window.parent.parent.JDialog.close();
						}
						else{
							$(window.parent.parent.parent.document.getElementById('menu_903')).click();
							window.parent.parent.JDialog.close();
						}
					} else if (json.result == 1) {
						window.parent.parent.showMsg(json.message);
						window.setTimeout("window.parent.JDialog.close()", 1800);
						return false;
					} else if (json.result == 2) {
						window.parent.parent.showMsg(json.message);
						window.setTimeout("window.parent.JDialog.close()", 1800);
						return false;
					}
				},
				error: function(json) {
					showMsg("操作失败！");
				}
			})
		}

		function cancle() {
			window.parent.JDialog.close();
		}
  </script>
</html>
