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
    <title>商品售卖</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
  </head>
  <body style="background:transparent;">
  <span class="close_span">
 	 <i class="imooc imooc_order" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i>
  </span>
  <div class="check_wrap_margin check_color">
		<h2>商品售卖</h2>
		<div class="left_slidebar">
			<ul id="accordion" class="accordion">
				<li>
					<div class="link">小商品管理<i class="fa fa-angle-down shopm"></i></div>
					<ul class="submenu">
						<li><i class="fa fa-hand-peace-o"></i><a onclick="goodssale()" class="active">商品售卖</a></li>
						<li><i class="fa fa-folder-open-o"></i><a onclick="goodsmanage()">小商品管理</a></li>
						<!--<li><i class="fa fa-file-excel-o"></i><a onclick="goodsreport()">报表</a></li>
					--></ul>
				</li>
				<!--<li>
					<div class="link"><i class="fa fa-code"></i>旅游商品管理<i class="fa fa-chevron-down"></i></div>
					<ul class="submenu">
						<li><a href="javascript:;">旅游商品售卖</a></li>
						<li><a href="javascript:;">旅游商品管理</a></li>
						<li><a href="javascript:;">报表</a></li>
					</ul>
				</li>
			--></ul>
		</div>
		<div class="shop_content">
		    <iframe name="goodsIframe" id="goodsIframe" frameborder="0" width="100%" height="100%" src="hotelgoodsPage.do"></iframe>
		</div>
		</div>
		<%@ include file="../../../common/script.jsp"%>
	    <script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/index.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
			$(".submenu li").on("click",function(){
				$(this).find("a").addClass("active");
				$(this).siblings().find("a").removeClass("active");
			})
			function goodssale(){
			  $("#goodsIframe").attr("src","hotelgoodsPage.do");
			}
			function goodsmanage(){
			  $("#goodsIframe").attr("src","gdmanageData.do");
			}
			function goodsreport(){
			}
		</script>
  </body>
</html>
