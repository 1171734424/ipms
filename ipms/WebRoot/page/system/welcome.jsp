<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html style="height:100%;">
	<head>
		<meta http-equiv="pragma" content="no-cache">    
	    <meta http-equiv="cache-control" content="no-cache">    
	    <meta http-equiv="expires" content="0">    
		<%@ include file="../common/css.jsp"%>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css" />
		
		<link  rel="stylesheet" href="<%=request.getContextPath()%>/hahaha/css.css"/>
		<style>
			.box01 img{
				width:100%;
			}
		</style>
	</head>
	<body style="width:100%; height:100%;background-color: #EAEDF1;overflow:hidden;
	background-repeat:no-repeat;background-size: cover;">
		<div class="divWrapper">
			<div class="combox">
				<!--<div class="combox_left" id="combox_left" align="center">
					 <span id="weatherimg">暂无数据</span> 
				</div>-->
				<div class="combox_right">
					<div class="calender_title">
						<div class="calender_data">
							<span id="calender_title" style="font-size: 15px;"></span>
							<span id="calender_week" style="font-size: 14px;"></span>
							<span id="calender_nl" style="font-size: 14px;"></span>
						</div>
						<div class="calender_time" id="calender_time">
						</div>
					</div>
				</div>
			</div>
		</div>
	<div class="userInfo">
	</div>
	<!--消息提示-->
		<!--<div class="divBox_top clearfix">
			<ul>
				<li class="second">
					<div class="second-number">
						<div class="work-item blue">
							<img src="../../images/me-two.png" class="li-img">
							<span class="number-top">14&nbsp;<span class="second-one">个</span></span>
							<label class="word-tip">待办未处理</label>
						</div>
					</div>
				</li>
				<li class="third">
					<div class="second-number">
						<div class="work-item orange">
							<img src="../../images/me-four.png" class="li-img">
							<span class="number-top">6&nbsp;<span class="second-one">条</span> </span>
							<label class="word-tip">预警信息未读</label>
						</div>
					</div>
				</li>
				<li class="last">
					<div class="second-number">
						<div class="work-item grey">
							<img src="../../images/me-five.png" class="li-img">
							<span class="number-top">10&nbsp;<span class="second-one">个</span> </span>
							<label class="word-tip">供应商开发</label>
						</div>
					</div>
				</li>
			</ul>
		</div>
		--><!-- 登录人信息展示 -->
		<%@ include file="../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/hahaha/hahaha.js"></script>
		<script>
			var base_path = "<%=request.getContextPath()%>";
			var message_num = "${ messageNum }";
		</script>
		<script src="<%=request.getContextPath()%>/script/fusioncharts/fusioncharts.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/cndate.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/calender.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/homePage.js"></script>
	</body>
</html>

