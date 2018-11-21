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
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
  </head>
  <body style="overflow: hidden;">
    <div class="check_wrap_margin check_color">
		<div class="shop_content  margin_gdspage">
			<h2>小商品售卖</h2>
			<div class="shop_search" style="height:100%;">
				<form action="" method="post" style="height:100%;">
					<input type="text" name="text" id="goodsid" class="shop_name" value="" placeholder="编码" maxlength="8"/>
					<input type="text" name="text" id="goodsname" class="shop_name" value="" placeholder="名称" maxlength="15"/>
					<select id="categoryid" name="" class="search_select">
					        <option  value="">所有分类</option> 
						 <c:forEach var="th" items="${categoryCondition}">  
                               <option  value="${th.categoryid}"> ${th.categoryname} </option>  
                               </c:forEach>  
					</select>
					<input type="button" name="" id="" class="gdsbutton" value="查询" onclick="gdsearch()"/>
					<input type="button" name="" id="" class="gdsmulbutton" value="批量售卖" onclick="gdsmanyearch_se()"/>
					<!-- 批量售卖隐藏域 -->
					<input type="hidden" id="gdscontent" name="gdscontent"/>
					<iframe name="goodsIframe" id="goodsIframe" frameborder="0" width="100%" height="100%" src="gdsaleApartCondition.do" class="gpageiframe"></iframe>
					</div>
				</form>
			</div>
		</div>
	<div id="pager"></div>
	</div>
	
		<%@ include file="../../../common/script.jsp"%>
	    <script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/apartment/js/index.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">var base_path = "<%=request.getContextPath()%>";</script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/apartment/apartmentLeftmenu/apartmentgoods/goodssale.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
		function gdsearch(){
		 var gdscontent = $("#gdscontent").val(); 
		 var goodsid = $("#goodsid").val();
		 var goodsname = $("#goodsname").val();
		 var categoryid = $("#categoryid").val();
		 $("#goodsIframe").attr("src","gdsaleApartCondition.do?goodsid="+goodsid+"&goodsname="+goodsname+"&categoryid="+categoryid+"&gdscontent="+gdscontent);
		}
		
		</script>
  </body>
</html>
