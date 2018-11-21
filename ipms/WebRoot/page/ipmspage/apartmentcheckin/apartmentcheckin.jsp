<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/integral/integral.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
  </head>
  <style>
  
.addnewcontrart{
    display: inline-block;
    margin: 10px 20px;
    padding: 1px 10px;
    height: 38px;
    line-height: 35px;
    border:none;
    text-align: center;
    cursor: pointer;
    border-radius: 4px;
    background: #535474;
    color: #fff;
    font-size: 14px;
}
    </style>
  <body>
  	 <div class="integral_margin">
    	<div class="header_roomlist">
			<ul>
				<li class="header_roomlist_li active" index="0">
					<span>全部合同</span>
				</li>
				<li class="header_roomlist_li" index="1">
					<span>在住合同</span>
				</li>
				<li class="header_roomlist_li" index="2">
					<span>待住合同</span>
				</li>
				<li class="header_roomlist_li" index="3">
					<span>离店合同</span>
				</li>
			</ul>
			
			<div class="fr">
				<span class="highsearch" >高级查询</span>
			</div>
			<div class="fr">
				<span class="addnewcontrart" onclick="addnewcontrart()">合同新增</span>
			</div>
			<div class="high_header fadeInDown">
				<h3>高级查询</h3>
				<i class="imooc imooc_log" style="color:#fff;">&#xe900;</i>
				<form name="condition" id="condition" action="queryApartment.do" method="post" target="logFrame">
					<div class="margintop">
						<ul class="clearfix">
						  <li>
							<label class="label_name in_select">合同选择</label>
							<select id="selectAge" class="text_search text_select" disabled='disabled'>
							    <option value="0">全部合同</option>
							    <option value="1">在住合同</option>
						        <option value="2">待住合同</option>
						        <option value="3">离店合同</option>
						    </select>
						 </li>
						</ul>
						<ul id="loginfo" class="clearfix">
							<li><label class="label_name">预定人</label><input name="memberName" id="memberName" type="text" value="" id="id" class="text_search"></li>
							<li><label class="label_name">手机号</label><input name="mobile" id="mobile" type="text" value="" id="id" class="text_search"></li>
						</ul>
					</div>
					<!-- <div class="time_div">
						<ul class="clearfix">
							<li>
								<label class="label_name">开始时间</label>
								<input id="starttime" name="starttime" type="text" value="" class="text_search check date">
							</li>
							<li>
								<label class="label_name">结束时间</label>
								<input id="endtime" name="endtime" type="text" value="" class="text_search check date">
							</li>
						</ul>
					</div> -->
					<div>
						<button type="button" class="btn_style button_margin reset" onclick="resetform()">重置</button>	
						<button type="button" class="btn_style button_margin button_query" onclick="submitFrom()">查询</button>
					</div>
				</form>
			</div>
		<section class="box-content-section fl" style="width:100%;">
			<section class="box_content_widget fl">
				<div id="integral" class="content" style="height:889px;">
					<iframe name="logFrame" id="logFrame" frameborder="0" width="100%" height="100%" src="queryApartment.do"></iframe>
				</div>
			</section>
		</section>
	 </div>
	</div>
	<script src="<%=request.getContextPath()%>/script/ipms/js/apartmentcheckin/apartmentcheckin.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
	var base_path ="<%=request.getContextPath()%>";
	</script>
  </body>
</html>