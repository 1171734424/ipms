<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'publictable.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/user-defined-css/publictable.css"/>

  </head>
  
  <body>
    <div class="big_box">
   		<table id="myTable" border="0">
			<thead>
				<tr>
					<th class="header">one</th>
					<th class="header">two</th>
				</tr>
			</thead>
			<tbody>
		    	<tr>
		    		<td>
		    			<input id="score" class="score"  type="text"/>
		    		</td>
		    		<td>
		    			<input id="money" class="money" type="text"/>
		    		</td>
		    	</tr>
	    	</tbody>
    	</table>
    </div>
  </body>
</html>
