 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>民宿退款</title> 
	<script>
      $(function(){
		var a = '${checkid}';
      })
	</script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </head>
  <body>
  	<div>
  		<iframe name="reservedFrame" id="reservedFrame" frameborder="0" width="100%" height="100%" 
  		src="<%=request.getContextPath()%>/page/ipmshouse/leftmenu/houserefund/house_bill.jsp?checkid=${checkid }" />
  	</div>
  </body>
</html>
