 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>公寓退款</title> 
  </head>
  <body>
  	<div>
  		<iframe name="reservedFrame" id="reservedFrame" frameborder="0" width="100%" height="100%" src="<%=request.getContextPath()%>/page/apartment/apartmentLeftmenu/apartmentrefund/apartment_bill.jsp?checkid=${checkid }" />
  	</div>                                          
  </body>
</html>
