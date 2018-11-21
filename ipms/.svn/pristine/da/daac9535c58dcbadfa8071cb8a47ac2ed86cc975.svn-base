<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信内容</title>
</head>
	<body>
		<div>
			<form action="" method="post">
				<div class="content">
				<c:forEach items="${templateInfo}" var="tt">
					<textarea id="templateContent" name="templateContent" style = "position: relative;height: 75%;width: 90%;
					margin: 4.5%;font-size: 12pt;font-family: Microsoft YaHei;">${tt.smsContent}</textarea>
				</c:forEach>	
				</div>
			</form><!--
			<div class="button div_button close" onclick="window.parent.JDialog.close();">
						<span>关闭</span>
					</div>
		--></div>
		<%@ include file="../../common/script.jsp"%>
		<script>
			var base_path = '<%=request.getContextPath()%>';
	 	</script>
	 	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	</body>
</html>