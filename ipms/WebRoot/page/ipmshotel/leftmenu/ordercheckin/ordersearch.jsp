<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>订单查询</title>
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/order/order.css" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/style.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>		
		<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	<style>
	.formstyle{
		height:100%;
	}
	@media screen and (max-width:1400px){
		.formstyle{
			height:587px;
			overflow:scroll;
		}
	}
	</style>
	</head>
	<body>
		<div class="box_popup_member box_popup_member_margin leftorder_margin">
			<span class="close_span"><i class="imooc imooc_order"
				style="color: #3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i>
			</span>
			<h3>
				订单查询
			</h3>
			<form action="" method="post"  class='formstyle'>
				<div class="content_member">
					<div class="search_style fl">
						<ul class="search_member clearfix fl">
							<li>
								<label class="label_name">
									订单号
								</label>
								<input id="orderid" name="orderid" type="text"
									class="search_text" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="17">
							</li>
							<li>
								<label class="label_name">
									预订人
								</label>
								<input id="orderuser" name="orderuser" type="text"
									class="search_text" maxlength="10">
							</li>
							<li>
								<label class="label_name">
									房型
								</label>
								<input id="roomtype" name="roomtype" type="text"
									class="search_text" maxlength="8">
							</li>
							<li>
								<label class="label_name">
									会员手机
								</label>
								<input id="mobile" name="mobile" type="text" class="search_text" maxlength="11">
							</li>
							<li>
								<label class="label_name">
									会员卡号
								</label>
								<input id="memberid" name="memberid" type="text"
									class="search_text" maxlength="8">
							</li>
							<li>
								<input type="button" id="left_order_search" class="btn_style btn_search button_margin"
									onclick="osearchdata()" value="查询" />
							</li>
						</ul>
					</div>
					<div class="content_member_show orderearch_frame">
						<iframe name="" id="orderIframe" frameborder="0" width="100%"
							height="100%" src="hotelorderData.do"></iframe>
					</div>
				</div>
			</form>
		</div>
		<%@ include file="../../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
<!-- 		<script -->
<!-- 			src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script> -->
		<script
			src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/ordercheckin/ordersearch.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script>
		Pager.renderPager(<%=pagination%>);
	</script>
	</body>
</html>
