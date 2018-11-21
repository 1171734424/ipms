<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/script.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>日志</title>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/houseorder/houseorder.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
  </head>
  
  <body>
  	 <div class="integral_margin">
    	<div class="header_roomlist">
			<ul>
                <li class="header_roomlist_li active" index="0">
					<span>全部订单</span>
				</li>
				<li class="header_roomlist_li" index="1">
					<span>有效订单</span>
				</li>
				<li class="header_roomlist_li" index="2">
					<span>取消订单</span>
				</li>
				<!-- <li class="header_roomlist_li" index="3">
					<span>无人看房</span>
				</li> -->		
			</ul>
			<div class="fr">
				<span class="highsearch">高级查询</span>
			</div>
			<div class="high_header fadeInDown">
				<h3>高级查询</h3>
				<i class="imooc imooc_log" style="color:#fff;">&#xe900;</i>
				<form name="condition" id="condition" action="ApartmentOrderAll.do" method="post" target="logFrame">
					<div class="margintop">
						<ul class="clearfix">
						  <li>
							<label class="label_name in_select">订单选择</label>
							<select id="selectAge" class="text_search" disabled='disabled'>
							    <option value="0">全部订单</option>
							    <option value="1">有效订单</option>
						        <option value="2">取消订单</option>
						         <option value="3">无人看房</option>
						    </select>
						 </li>
						</ul>
						<ul id="loginfo" class="clearfix">
							<li><label class="label_name">预定人</label><input name="memberName" id="memberName" type="text" value="" id="id" class="text_search"></li>
							<li><label class="label_name">手机号</label><input name="mobile" id="mobile" type="text" value="" id="id" class="text_search"></li>
						</ul>
					</div>
					<div class="time_div">
						<ul class="clearfix">
							<li>
								<label class="label_name">预定时间</label>
								<input id="arrivaltime" name="arrivaltime" type="text" value="" class="text_search check date">
							</li>
						</ul>
					</div>
					<div>
						<button type="button" class="btn_style button_margin reset" onclick="resetform()">重置</button>	
						<button type="button" class="btn_style button_margin" onclick="submitFrom()">查询</button>
					</div>
				</form>
			</div>
		<section class="box-content-section fl" style="width:100%;">
			<section class="box_content_widget fl">
				<div id="integral" class="content" style="height:889px;">
					<iframe name="logFrame" id="logFrame" frameborder="0" width="100%" height="100%" src="ApartmentOrderAll.do"></iframe>
				</div>
			</section>
		</section>
	 </div>
	</div>
	<script src="<%=request.getContextPath()%>/script/apartment/apartmentmainmenu/apartmentorder/apartmentorder.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		var base_path = "<%=request.getContextPath()%>";
	</script>
  </body>
</html>
