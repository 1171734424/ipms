<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>删除员工</title>
    
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
  		<p>确定删除总超管吗？</p>
  		<div class="mybutton buttonclass">
			<span class="add_conroom query" role="button" onclick="submitdel()">确定</span>
			<span class="add_conroom cancel fl myclass" role="button" onclick="cancle()">取消</span>
		</div>
  	</div>
  </body>
  <script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/jquery-ui.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/jquery.md5.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  <script>
  		var path = "<%=request.getContextPath() %>";
  		var staffId = "<%=request.getParameter("staffId") %>";
  		
		function submitdel() {
			$.ajax({
				url: path + "/deleteStaff.do",
				type: "post",
				dataType: "json",
				data: { staffId: staffId },
				success: function(json) {
					if (json.result == 1) {
						window.parent.showMsg("操作成功");
						window.setTimeout("window.parent.refreshGrid()",1000);
						window.setTimeout("window.parent.JDialog.close()",1000);
					}
				},
				error: function(json) {
					window.parent.parent.showMsg("操作失败！");
				}
			});
		}

		function cancle() {
			window.parent.JDialog.close();
		}

		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
  </script>
</html>
