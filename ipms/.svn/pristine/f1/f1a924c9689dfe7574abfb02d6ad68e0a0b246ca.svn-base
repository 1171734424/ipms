<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ideassoft.core.bean.pageConfig.PageConfig"%>
<%@page import="com.ideassoft.util.ExportUtil"%>
<%@page import="java.io.OutputStream"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
<%
	String fileName = request.getAttribute("fileName").toString();
	response.reset();
	response.setCharacterEncoding("utf-8");
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment; filename=" + new String(fileName.getBytes("gb2312"), "iso-8859-1"));
	OutputStream os = response.getOutputStream();
	
	PageConfig pagetConfig = (PageConfig) request.getAttribute("pageConfig");
	List result = (List) request.getAttribute("result");
	ExportUtil.exportExcel(pagetConfig, result, false, os);
	
	out.clear();
	out = pageContext.pushBody();
%>
</body>
</html>
