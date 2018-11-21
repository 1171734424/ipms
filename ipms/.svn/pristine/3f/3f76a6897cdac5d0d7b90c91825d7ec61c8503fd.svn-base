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
    <title>公寓预约</title>
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
  <div class="check_wrap_margin check_color" style="height:825px">
		<h2>预约</h2>
		<div class="left_slidebar">
			<ul id="accordion" class="accordion" style="height:755px">
				<li>
					<div class="link">公寓预约<i class="fa fa-angle-down shopm"></i></div>
					<ul class="submenu">
						<li><i class="fa fa-hand-peace-o"></i><a onclick="apartmentReserved()" class="active">预约看房</a></li>
						<li><i class="fa fa-folder-open-o"></i><a onclick="goodsmanage()">无人看房</a></li>
				</li>
			</ul>
		</div>
		<div class="shop_content">
		    <iframe name="reserved" id="reserved" frameborder="0" width="100%" height="100%" src="apartmentReserved.do"></iframe>
		</div>
		</div>
		<%@ include file="../../../common/script.jsp"%>
	    <script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/apartment/js/index.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
			$(".submenu li").on("click",function(){
				$(this).find("a").addClass("active");
				$(this).siblings().find("a").removeClass("active");
			});
			function apartmentReserved(){
			  $("#reserved").attr("src","apartmentReserved.do");
			}
			function goodsmanage(){
			  $("#reserved").attr("src","apartmentSelfservice.do");
			}
			function goodsreport(){
			}
		</script>
  </body>
</html>
