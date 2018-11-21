<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.ideassoft.core.bean.LoginUser"%>
<%@page import="com.ideassoft.util.RequestUtil"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ include file="../common/taglib.jsp"%>
<%
	LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
	if(loginUser != null) {
		request.setAttribute("user", JSONArray.fromObject(loginUser.getStaff()));
		request.setAttribute("basePath", request.getContextPath());
	}
 %>
<!DOCTYPE HTML>
<html>
	<head>
	  <title>打印</title>
	  <meta http-equiv="pragma" content="no-cache">
	  <meta http-equiv="cache-control" content="no-cache">
	  <meta http-equiv="expires" content="0">
	  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css">
	  <%@ include file="../common/script.jsp"%>
	  <script>
	    var basePath = "${ basePath }";
	  </script>
	  <script src="<%=request.getContextPath()%>/script/common/LodopFuncs.js"></script>
	  <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
		<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="install_lodop32.exe"></embed>
	  </object> 
	  
	  <script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.js"></script>
	  <script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
	  <script src="<%=request.getContextPath()%>/script/print.js"></script>
   </head>
   <body oncontextmenu="return true;">
		<div id="grid_content" class="grid_content" align="center">
   			
			<table id="print_info"></table>
			
   			<p><a href="javascript:prn1_preview()">打印预览</a>  <a href="javascript:printRecords()">打印</a></p>
		</div>
	</body>
</html>
