<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
%>

<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>订单</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/orderfont.css" />
		<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/order/order.css" />
	
		<link rel="stylesheet" type="text/css" 
			href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />		
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	</head>
 
	<body>
  		<div class="order_margin">
	  	    <div class="header_order">
				<ul id="ultab">
					<li id="orderNews" class="header_order_li active" index="5"><p>今日预抵</p><p>(新订)</p></li>
					<li class="header_order_li" index="1"><p>今日预抵</p><p>(担保)</p></li>
					<li class="header_order_li" index="2"><p>今日预抵</p><p>(未过保留期)</p></li>
					<li class="header_order_li" index="3" style="display: none;"><p>今日预抵</p><p>(已过保留期)</p></li>
					<li class="header_order_li" index="4"><p>今日预抵</p><p>	异常订单</p></li>
					<li class="header_order_li" index="0"><p>今日预抵</p><p>(所有)</p></li>
				</ul>
				<div class="fr">
					<span class="highsearch">高级查询</span>
				</div>
				<div class="high_header fadeInDown">
					<h3>高级查询</h3>
					<i class="imooc imooc_order imoocOld" style="color:#fff;">&#xe900;</i>
					<form action="" method="post" id="myForm" name="myForm">
							<div class="margintop">
								<ul class="clearfix" style="margin-left: 15%;">
									<li>
										<label class="label_name">
											预订人
										</label>
										<input name="orderUser" id="orderUser" type="text" value=""
											id="id" class="text_search">
									</li>
									<li>
										<label class="label_name">
											手机号
										</label>
										<input name="mphone" id="mphone" type="text" value="" id="id"
											class="text_search">
									</li>
								</ul>
								<ul class="clearfix" style="margin-left: 15%;">
									<li>
										<label class="label_name">
											抵店日期
										</label>
										<input id="arrivalTime" name="arrivalTime" type="text" value=""
											class="check date text_search">
									</li>
									<li>
										<label class="label_name">
											至
										</label>
										<input name="arrtimes" id="arrtimes" type="text" value=""
											class="check date text_search">
									</li>
								</ul>
								<ul class="clearfix">
									<li>
										<input type="button" value="确认" class="button_margin confirm"
											onclick="queryDataNew()">
									</li>
								</ul>
							
			    		</div>
					</form>
				</div>
			</div>
			<section class="box-content-section fl" style="width:100%;">
				<section class="box_content_widget fl">
					<div id="integral" class="content" style="height:926px;padding:10px;">
						<iframe name="orderFrame" id="orderFrame" frameborder="0" width="100%" height="100%"></iframe>
					</div>
				</section>
			</section>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>     
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>	
		<script src="<%=request.getContextPath()%>/script/ipmshotel/order/orderinfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipmshotel/order/rightclick.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath()%>";
			laydate({
			   	elem: '#arrivalTime'
			});
			laydate({
			   	elem: '#arrtimes'
			})
		</script>
	</body>
</>
