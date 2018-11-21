<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.ideassoft.util.RequestUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jump to login...</title>
<%
  if(null != request.getParameter("message")){
  	request.getSession(true).removeAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
  }

 %>
</head>
<body>
<script type="text/javascript">
	window.top.location.href = "<%=request.getContextPath()%>/page/ipmspage/login/login.jsp";
</script>
</body>
</html>