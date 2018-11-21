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
    
    <title>修改订单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/secpage.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	

  </head>
  
  <body>
  	<div class="update_order">
 		<input type="text" id="orderId" value="<%=request.getParameter("orderId")%>" hidden="true"/>
  		<p>房间类型与预定时不同，确认修改该订单吗？</p>
  		<div class="mybutton_roomtype">
			<span class="add_conroom query" role="button" onclick="editorder()">确定</span>
			<span class="add_conroom cancel" role="button" onclick="cancle()">取消</span>
		</div>
	</div>
  </body>
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  <script>
  		var path = "<%=request.getContextPath() %>";
  		function editorder() {
			if (window.parent.$("#roomType").val()=="") {
				window.parent.showMsg("请选择房型!");
				window.parent.JDialog.close();
			} else {
		  		$.ajax({
			         url: path + "/editorderinfo.do",
					 type: "post",
					 data : window.parent.$("#myForm").serialize(),
					 success: function(json) {
						 //showMsg(json.message);
						 window.setTimeout("window.parent.parent.location.reload(true)",1800);
					 },
					 error: function(json) {
					 	showMsg("操作失败！");
					 }
				});
			}
		}
		
		function cancle() {
			window.parent.JDialog.close();
		}
  </script>
</html>
