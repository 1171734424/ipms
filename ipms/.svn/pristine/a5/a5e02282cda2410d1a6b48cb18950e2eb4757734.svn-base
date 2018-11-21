<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ include file="../common/taglib.jsp"%>
<%
	Object mods = request.getAttribute("models");
	JSONArray models = JSONArray.fromObject(mods);
%>
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
   <body oncontextmenu="return true" ondragstart="return false;" onselectstart="return false;">
		<form method="post">
			<div class="title">
				<div class="sys_icon">&nbsp;管理系统</div>
			</div>
			<div class="main">
				<div class="bgstyle">
					<div class="contstyle" id="contstyle">
						<ul id="contul"></ul>
					</div>
				</div>
			</div>
		</form>
		<!-- <div class="footer"><%@include file="footer.jsp" %></div> -->
		<script type="text/javascript">
			var base_path = "<%=request.getContextPath()%>";
		</script>
		<jsp:include page="../common/script.jsp"></jsp:include>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/mainframe.js"></script>
		<script type="text/javascript">
			$(".main").css("height", $("body").outerHeight() - 80);
			
			var modelli, modelstyle, iconstyle, namestyle;
			<c:forEach items="${models}" var="model" varStatus="loopStatus">
				<c:if test="${ model.value.show }">
					modelstyle = $("<div/>");
					modelstyle.attr("id", "${model.value.modelId}");
					modelstyle.attr("class", "modelstyle");
					modelstyle.bind("click", function() {
						$("form").attr("action", base_path + "/loadAppFrame.do?modelId=${model.value.modelId}");
						$("form").submit();
					});
					
					iconstyle = $("<div/>");
					iconstyle.attr("class", "iconstyle");
					iconstyle.html("<img width='80' height='80' style='margin-top:10px;' src='./images/model/${model.value.icon}.png'/>");
					iconstyle.appendTo(modelstyle);
					
					namestyle = $("<div/>");
					namestyle.attr("class", "namestyle");
					namestyle.html("${model.value.modelName}");
					namestyle.appendTo(modelstyle);
			
					modelli = $("<li/>");
					modelstyle.appendTo(modelli);
					
					modelli.appendTo($("#contul"));
				</c:if>
			</c:forEach>
	
			$(".modelstyle").hover(function(){
				$(this).addClass("selectmodelstyle");
				$(this).css("border-radius", "20px");
			},function(){
				$(this).removeClass("selectmodelstyle");
			});
		</script>
	</body>
</html>

