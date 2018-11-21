<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>积分调整</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" media="all" />
	<link href="<%=request.getContextPath()%>/css/ipms/exchangeScore.css" rel="stylesheet"media="all" />
  </head>
  
  <body>
  	<div class="adjust_box">
	   <table>
	    	<tr>
	    		<td>输入操作数值:</td>
	    		<td><input id="integration" type="text" class="text_edit"/></td>
	    	</tr>
	   </table>
	   <span>修改积分格式,数字在积分范围内随意填写</span></br>
	   <span>例如:</span></br>
	   <span>添加积分:  10 </span></br>
	   <span>减去积分: -10 </span>
    </div>
    <input id="accountid" type="hidden" value="${accountid }"/>
    <div class="div_input parents-content">
    	<input class="crm-confirmbt" type="button" value="确定" onclick="updateScore()"/>  
   		<input class="crm-cancelbt" type="button" value="取消" onclick="window.parent.JDialog.close()"/>
    </div>
  </body>	
  <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  <script src="<%=request.getContextPath()%>/script/crm/integral/scoreAdjustment.js"></script>
</html>
