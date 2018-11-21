<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/css.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>订单</title>
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
					<span>全部订单(今天)</span>
				</li>
				<li class="header_roomlist_li" index="3">
					<span>取消订单(今天)</span>
				</li><!--
				<li class="header_roomlist_li" index="2">
					<span>未到订单(今天)</span>
				</li>	
			-->
				<li class="header_roomlist_li" index="5">
					<span>入住订单(今天)</span>
				</li>
 				<li class="header_roomlist_li" index="4">
					<span>待确认(今天)</span>
				</li> 

<!-- 				<li class="header_roomlist_li" index="6"> -->
<!-- 					<span>新订订单(今天)</span> -->
<!-- 				</li> -->
				<li class="header_roomlist_li" index="7">
					<span>今日预抵(新订)</span>
				</li>
				<li class="header_roomlist_li" index="8">
					<span>待支付</span>
				</li>
			</ul>
			<div class="fr">
				<span class="highsearch">高级查询</span>
			</div>
			<div class="high_header fadeInDown">
				<h3>高级查询</h3>
				<i class="imooc imooc_log" style="color:#fff;">&#xe900;</i>
				<form name="condition" id="condition" action="HouseOrderAll.do" method="post" target="logFrame">
					<div class="margintop">
						<ul class="clearfix">
						  <li>
							<label class="label_name in_select">订单选择</label>
							<select id="selectAge" class="text_search" disabled='disabled'>
							    <option value="0">全部订单</option>
							    <option value="1">已住订单</option>
						        <option value="2">未到订单</option>
						        <option value="3">取消订单</option>
					         	<option value="4">待确认订单</option>
						        <option value="5">入住订单</option>
						        <option value="6">新订订单</option>
						        <option value="7">今日预抵</option>
						        <option value="8">待支付</option>
						    </select>
						 </li>
						 <li id="orderstatus">
							<label class="label_name">状态</label>
							<select class="text_search" id="selectstatus" name="selectstatus">
								<option value="">所有</option>
							    <option value="0">取消</option>
							    <option value="1">新订</option>
						        <option value="2">未到</option>
						        <option value="3">在住/转单</option>
					         	<option value="4">离店</option>
						        <option value="5">删除</option>
						        <option value="6">已退未结</option>
						    </select>
						 </li>
						</ul>
						<ul id="loginfo" class="clearfix">
							<li><label class="label_name">预定人</label><input name="memberName" id="memberName" type="text" value=""  class="text_search"></li>
							<li><label class="label_name">会员号</label><input name="memberId" id="memberId" type="text" value="" class="text_search"></li>
							<li><label class="label_name">手机号</label><input name="mobile" id="mobile" type="text" value=""  class="text_search"></li>
							<li><label class="label_name">入住人</label><input name="checkinPeople" id="checkinPeople" type="text" value=""  class="text_search"></li>
							<li><label class="label_name in_select">渠道</label>
							<select id="source" class="text_search" name="source">
	                        	<option value="0" selected="selected">全部</option>
						    </select></li>
							<li><label class="label_name">房型</label><input name="roomType" id="roomType" type="text" value=""  class="text_search"></li>
							<li><label class="label_name">操作员</label><input name="recordUser" id="recordUser" type="text" value=""  class="text_search"></li>
							<li><label class="label_name">预订时间</label><input name="bookingTime" id="bookingTime" type="text" value=""  class="text_search date"></li>
						</ul>
					</div>
					<div class="time_div">
						<ul class="clearfix">
							<li>
								<label class="label_name">入住时间</label>
								<input id="arrivaltime" name="arrivaltime" type="text" value="" class="text_search check date">
								<span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp--</span>
								<input id="arrivaltime2" name="arrivaltime2" type="text" value=""  style= "margin-left: 39px;" class="text_search check date">
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
					<iframe name="logFrame" id="logFrame" frameborder="0" width="100%" height="100%" src="HouseOrderAll.do"></iframe>
				</div>
			</section>
		</section>
	 </div>
	</div>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/ipmsinhouse/houseorder/houseorder.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		var base_path = "<%=request.getContextPath()%>";
	</script>
  </body>
</html>