<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/script.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>合同查看</title>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<style>
		.div_h{
			padding-left: 10px;
		    width: 100%;
		    line-height:34px;
		    line-height: 34px;
		    background: #4A5064;
	        font-size: 16px;
		    color: #fff;
		    border-top-left-radius: 4px;
		    border-top-right-radius: 4px;
		}
		.imooc_log{
		    left: 96%;
		    top:2px;
		    font-size: 30px;
		    transition: 0.3s all ease-in-out;
		}
		.imooc_log:hover{
		    transform: rotate(90dge);
		    -ms-transform: rotate(90deg);
		    -webkit-transform: rotate(90deg);
		}
	</style>	
  </head>
  
  <body>
  	<div>
		<h3 class="div_h">合同查看</h3>
		<i class="imooc imooc_log" style="color:#fff;" onclick="window.parent.JDialog.close();">&#xe900;</i>
		<c:if test="${ifNull == 'true' }">
			<c:if test="${contract != null && contract != '' }">
	  			<iframe style="height: 93%;width: 100%" src="<%=request.getContextPath()%>/upload/1.html"></iframe>
	  		</c:if>
	  		<c:if test="${contractUrl != null && contractUrl != '' }">
	  			<iframe style="height: 95%;width: 100%" src="${contractUrl }"></iframe>
	  		</c:if>
		</c:if>
		<c:if test="${ifNull == 'false' }">
			<c:if test="${contract == null || contract == '' }">
	  			<div style="font-size:25px;text-align: center;padding-top: 20%;height:100%;font-family:'微软雅黑';color:#aaaaaa;background-color: antiquewhite;">
	  				暂无合同附件上传!
	  			</div>
	  		</c:if>
		</c:if>
  	</div>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
</html>
