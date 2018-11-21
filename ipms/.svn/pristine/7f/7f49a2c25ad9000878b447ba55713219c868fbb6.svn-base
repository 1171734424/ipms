<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>房态</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" id="style" type="text/css"  href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" id="style" type="text/css"  href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomstate.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />	
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/iconfont/iconfont.css"/>
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/ipmsicon/iconfont.css"/>
		<style type="text/css">
		.main_content ul li{
		 width:110px;
		 height:110px;
		 border:none;
		 box-shadow: 0 0 5px rgba(0,0,0,0.2);
		 border-radius:6px;
		}
		.main_content ul li span{
		width:110px;
		height:110px;
		display: table-cell;
		vertical-align: middle;
		margin-left:0px;
		padding:0 10px;
		text-align:center;
		white-space:normal!important;
		line-height:1.5;
		}
		</style>
	</head> 

	<body>
		<div class="house" style="display:none;">
			<div class="main_wrapper_house">
				<div class="main_content">
					<ul class="room_list">
					</ul>
				</div>
			</div>
			<div class="footer_content">
				<form action="" method="">
					<div class="countroom fl">
						<span>总房数<span class="housetotal"></span>
						</span>
						<span>在住房数<span class="houselive"></span>
						</span>
					</div>
					<ul class="house_roomstatus">
						<li class="footer_li active" onclick="queryhouselist();">
							<span>所有</span>
						</li>
						<li class="footer_li" onclick="queryarrival(1);" style="background-color: #FFF68F;">
							<span><img src="./css/ipms/img/backgroundimg/arriving.png"/>预抵</span>
						</li>
						<li class="footer_li" onclick="queryarrival(2);" style="background-color:#FFC77F;">
							<span><img src="./css/ipms/img/backgroundimg/leaving.png"/>预离</span>
						</li>
						<li class="footer_li" onclick="queryarrival(3);" style="background-color:#A3DE83;">
							<span><img src="./css/ipms/img/backgroundimg/holding.png"/>在住</span>
						</li>
						<li class="footer_li emptyroom" onclick="queryhouseroom('empty');">
							<span>空置房</span>
						</li>

						<li class="footer_li dirhouse" onclick="queryhouseroom('dirty');">
							<span>脏房</span>
						</li>
						<li class="footer_li stopsellhouse" onclick="queryhouseroom('stop');" style="background-color:#EC9454;">
							<span>停售房</span>
						</li> 
						<li class="footer_li" onclick="queryhouseroom('maintence')" style="background-color:#78BBE6;">
							<span>维修房</span>
						</li>
						<!-- <li class="dropse_li">
							<select id="dropse_roomtype"  class="mySelect">
							</select>
						</li>
						<li class="dropse_li">
							<select id="dropse_floor">
							</select>
						</li> -->
						<li class="dropse_li" style="background-color: white;">
							<input type="text" name="houseName" id="houseName" value="" placeholder="民宿名称" />
						</li>
						<%--<li class="" >
							<span>打印机</span>
						</li>
					--%></ul>
				</form>
			</div>
			<div class="setmargin housecleanroom" style="display:none">
				<ul>
					<li onclick="setroom('T','3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置停售房<span/></li>
					<li onclick="setroom('W', '3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置维修房<span/></li>
					<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
					<input type="text" id="housezroom" class="roomIds" style="display:none;"/>
				</ul>
			</div>
			<div class="setmargin houserepairroom" style="display:none">
				<ul>
					<li onclick="setroom('W','3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置维修房<span/></li>
					<!-- <li onclick="setroom('W');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置维修房<span/></li>-->
					<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
					<input type="text" id="housezroom" class="roomIds" style="display:none;"/>
				</ul>
			</div>
			<div class="setmargin housemaintroom" style="display:none">
				<ul>
					<li onclick="setroom('3','3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置在住<span/></li>
					<%--<li onclick="setroom('T','3');" class="setclean"><i class="iconfont icon-z	angfang"></i><span class="spans">设置停售房<span/></li>
					--%><li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
					<input type="text" id="housecroom" class="roomIds" style="display:none;"/>
				</ul>
			</div>
			<div class="setmargin housestoproom" style="display:none">
				<ul>
					<!--<li onclick="setroom('1','3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置清洁房<span/></li>
					--><li onclick="setroom('T','3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置停售房<span/></li>
					<li onclick="setroom('W', '3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置维修房<span/></li>
					<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
					<input type="text" id="housecroom" class="roomIds" style="display:none;"/>
				</ul>
			</div>
			<div class="setmargin onlyhousestoproom" style="display:none">
				<ul>
					<!--<li onclick="setroom('1','3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置清洁房<span/></li>
					--><li onclick="setroom('T','3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置停售房<span/></li>
					<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
					<input type="text" id="housecroom" class="roomIds" style="display:none;"/>
				</ul>
			</div>
			<!-- <div class="setmargin houseliveroom" style="display:none">
				<ul>
					<li onclick="setroom('1','3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置维修房<span/></li>
					<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
					<input type="text" id="housecroom" class="roomIds" style="display:none;"/>
				</ul>
			</div> -->
			<div class="setmargin housecleanedroom" style="display:none">
				<ul>
					<li onclick="setroom('1','3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置清洁房<span/></li>
					<li onclick="setroom('W', '3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置维修房<span/></li>
					<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
					<input type="text" id="housecroom" class="roomIds" style="display:none;"/>
				</ul>
			</div>
		</div>
	<script src="<%=request.getContextPath()%>/script/ipmsinhouse/roominhouse.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>	
	<script src="<%=request.getContextPath()%>/script/ipms/js/customer/customer.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		var base_path = "<%=request.getContextPath()%>";
		
		$(function(){
			
			$(".setcleanlive").hide();
			$(".footer_content ul .footer_li").on("click",function(){
				$(this).addClass("active");
				$(this).siblings().removeClass("active");
			});
			
		});
		
		// 屏蔽浏览器自带的右键
		$(document).bind("contextmenu",
		    function(){
		        return false;
		    }
		);
	</script>
	</body>
</html>