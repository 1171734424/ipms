<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	  <title>管理系统</title>
      <link href="<%=request.getContextPath()%>/images/shortcut.png" rel="shortcut icon" />
	  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
	  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
	  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css"/>
	  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/mainframe.css"/>
   </head>
   <body oncontextmenu="return true;" ondragstart="return false;" onselectstart="return false;">
		<div class="main">
			<div class="bgstyle">
				<form id="reportform" class="reportform">
					<div class="main_left">
						<div class="main_title">
							<div class="main_left_title" style="width: 100%;">
								<div class="title_icon"><span class="title_text">功能列表</span></div>
							</div>
						</div>
						<div id="backZone" align="left" class="main_left_tree">
							<ul id="menuList" class="ul"></ul>
						</div>
					</div>
					<div class="main_info">
						<div id="tabs" class="main_right_title" style="display: none;">
							<jsp:include page="tabheader.jsp"></jsp:include>
						</div>
						<div class="main_right" >
							<div id="dataZone" align="left" class="main_right_grid">
								<iframe name="contentFrame_first" id="contentFrame_first" src="<%=request.getContextPath()%>/page/system/welcome.jsp"
								 frameborder="0" width="100%" height="100%" style="display:block;"></iframe>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- <div class="footer"><%@include file="footer.jsp" %></div> -->
		<input type="hidden" name="modelId" id="modelId" value='${modelConfig.modelId}'/>
		<input type="hidden" name="systemType" id="systemType" value='${sessionScope.LOGIN_USER.systemType}'/>
		<script type="text/javascript">
			var base_path = "<%=request.getContextPath()%>";
		</script>
		<jsp:include page="../common/script.jsp"></jsp:include>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/mainframe.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/mainTabs.js"></script>
		<script type="text/javascript">
			$(".main").css("height", $("body").outerHeight());
			$(".main_info").css("height", $("form").outerHeight());
			
			$(document).ready(function() {
				currentFrame = "#contentFrame_first";
				listFunctions('${modelConfig.modelId}', '${modelConfig.modelName}');
			});
		</script>
	</body>
</html>

