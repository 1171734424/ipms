<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<%
	JSONObject json = JSONObject.fromObject(request.getAttribute("pageConfig"));
	json.remove("sql");
	json.remove("dataFormats");
%>
<html style="width:100%; height:100%;overflow:hidden;">
	<head>
		<title>report tabs</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<%@ include file="../common/css.jsp"%>
	</head>
	<body oncontextmenu="return false;" style="width:100%; height:100%;margin:0;padding:0;" >
		<div id="gridTabs" class="gridTabs">
			<ul id="gridul" class="gridul"></ul>
		</div>
		<!-- 隐藏条件 -->
		<input type="hidden" name="modelId" id="modelId" value='${modelId}'/>
		<input type="hidden" name="pageId" id="pageId" value='${pageConfig.pageId}'/>
		
		<%@ include file="../common/script.jsp"%>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			var page_config = JSON.parse('<%=json%>');
		</script>
		<script src="<%=request.getContextPath()%>/script/pageTabs.js"></script>
	</body>
</html>