<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" pageEncoding="UTF-8"%>
<%response.setStatus(HttpServletResponse.SC_OK);%>
<%
	String errorCode = request.getParameter("errorCode").toString();
%>
<body style="background: url(./images/error<%=errorCode %>.png) no-repeat; background-position: center;">
</body>