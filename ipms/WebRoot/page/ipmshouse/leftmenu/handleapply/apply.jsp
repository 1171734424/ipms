<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
	request.setAttribute("categoryCondition", request.getAttribute("categoryCondition")); 		
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>保洁申请</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<style>
	.gsbotton{
		font-size: 14px;
		font-family: "微软雅黑";
		height: 34px;
	    line-height: 31px;
	    text-align: center;
	    width: 80px;
	    margin-left: 4px;
	    border: 1px solid #f0f0f0;
	    background: #FC8A15;
	    color: #fff;
	    cursor: pointer;
	}
	</style>
  </head>
  <body style="background:transparent;">
  <span class="close_span">
 	 <i class="imooc imooc_order" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i>
  </span>
   <div class="check_wrap_margin check_color">
			<h2>保洁</h2>
			<div class="left_slidebar">
				<ul id="accordion" class="accordion">
					<li>
						<div class="link">保洁申请<i class="fa fa-angle-down shopm"></i></div>
						<ul class="submenu">
							<li><i class="fa fa-hand-peace-o"></i><a onclick="loadapply()" class="active">申请处理</a></li>
							<li><i class="fa fa-folder-open-o"></i><a onclick="cleanrecord()">保洁记录</a></li>
						</ul>
					</li>
			    </ul>
			</div>
			<div class="shop_content" style="height:712px;">
			    <iframe name="goodsIframe" id="goodsIframe" frameborder="0" width="100%" height="100%" src="loadapply.do"></iframe>
			</div>
	</div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/index.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	   <script>
			$(".submenu li").on("click",function(){
				$(this).find("a").addClass("active");
				$(this).siblings().find("a").removeClass("active");
			});
			function loadapply(){
			  $("#goodsIframe").attr("src","loadapply.do");
			}
			function cleanrecord(){
			  $("#goodsIframe").attr("src","loadcleanrecord.do");
			}
	  </script>
  </body>
</html>
