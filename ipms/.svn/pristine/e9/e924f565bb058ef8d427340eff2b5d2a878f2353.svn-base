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
	</head> 

	<body>
		<div class="hotel" style="display:none;">
			<div class="main_wrapper">
				<div class="main_content">
					<ul class="room_list">
					</ul>
				</div>
				<div class="footer_content">
					<form action="" method="">
						<div class="countroom fl">
							<span>总房数<span class="totalcount"></span>
							</span>
							<span>在住房数<span class="livecount"></span>
							</span>
						</div>
						<ul class="hotel_roomstatus">
							<li class="footer_li active" onclick="queryalllist();">
								<span>所有</span>
							</li><%--
							<li class="footer_li" onclick="queryarrival();">
								<span><img src="./css/ipms/img/backgroundimg/arriving.png"/>预抵</span>
							</li>
							--%><li class="footer_li" onclick="queryleavelive(1);" style="background-color: #FFC77F;">
								<span><img src="./css/ipms/img/backgroundimg/leaving.png"/>预离</span>
							</li>
							<li class="footer_li" onclick="queryleavelive(2);" style="background-color: #A3DE83;">
								<span><img src="./css/ipms/img/backgroundimg/holding.png"/>在住</span>
							</li>
							<li class="footer_li" onclick="queryroom('empty');">
								<span>空置房</span>
							</li>
							<li class="footer_li" onclick="queryroom('dirty');">
								<span>脏房</span>
							</li>
							<li class="footer_li" onclick="queryroom('stop');" style="backgrond-color:#EC9454;">
								<span>停售房</span>
							</li>
							<!-- <li class="footer_li" onclick="queryroom('maintence')" style="background-color: #78BBE6;">
								<span>维修房</span>
							</li> -->
							<li class="dropse_li">
								<select id="dropse_roomtype"  class="mySelect">
								</select>
							</li>
							<li class="dropse_li">
								<select id="dropse_floor">
								</select>
							</li>
							<li class="dropse_li">
								<input type="text" name="roomId" id="roomId" value="" placeholder="房号" />
							</li>
							<!-- <li class="" >
								<span>打印机</span>
							</li> -->
						</ul>
					</form>
				</div>
				<div class="setmargin cleanroom" style="display:none">
					 <ul>
						<li onclick="setroom('Z', '1');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置脏房<span/></li>
						<li onclick="setroom('T', '1');" class="setclean"><i class="iconfont icon-tingzhi"></i><span class="spans">设置停售房<span/></li>
						<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
						<input type="text" id="cleanroomid" class="roomIds" style="display:none;"/>
					</ul> 
				</div>
				<div class="setmargin dirtyroom" style="display:none">
					<ul>
						<li onclick="setroom('Z', '1');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置脏房<span/></li>
						<li onclick="turnto('check',this);" class="setclean"><i class="iconfont icon-tuanfangdingdan"></i><span class="spans">转至客单<span/></li>
						<li onclick="turnto('bill', this);" class="setclean"><i class="iconfont icon-zhangdan"></i><span class="spans">转至账单<span/></li>
						<li onclick="turnto('plan', this);" class="setclean"><i class="iconfont icon-jihua"></i><span class="spans">转至房租计划<span/></li>
						<li onclick="turnto('hint', this);" class="setclean"><i class="iconfont icon-tishi"></i><span class="spans">转至提示<span/></li>
						<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
						<input type="text" id="dirtyroomid" class="roomIds" style="display:none;"/>
						<input type="text" id="thischeckid" class="mycheckid" style="display:none;"/>
					</ul>
				</div>
				<!-- <div class="setmargin stoproom" style="display:none">
					<ul>
						<li onclick="setroom('1','1');" class="setclean"><i class="iconfont icon-qingjie"></i><span class="spans">设置清洁房<span/></li>
						<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
						<input type="text" id="stoproomid" style="display:none;"/>
					</ul>
				</div> -->
				<div class="setmargin vzroom" style="display:none">
					<ul>
						<li style="display: none;" onclick="setroom('3', '1');" class="setcleanlive"><i class="iconfont icon-zaizhu"></i><span class="spans">设置在住<span/></li>
						<li style="display: none;" onclick="setroom('1', '1');" class="setcleanroom"><i class="iconfont icon-qingjie"></i><span class="spans">设置清洁房<span/></li>
						<li style="display: none;" onclick="setroom('T', '1');" class="setcleanstop"><i class="iconfont icon-tingzhi"></i><span class="spans">设置停售房<span/></li>
						<li style="display: none;" onclick="turnto('check',this);" class="setcleanlist"><i class="iconfont icon-tuanfangdingdan"></i><span class="spans">转至客单<span/></li>
						<li style="display: none;" onclick="turnto('bill', this);" class="setcleanbill"><i class="iconfont icon-zhangdan"></i><span class="spans">转至账单<span/></li>
						<li style="display: none;" onclick="turnto('plan', this);" class="setcleanplan"><i class="iconfont icon-jihua"></i><span class="spans">转至房租计划<span/></li>
						<li style="display: none;" onclick="turnto('hint', this);" class="setcleanhint"><i class="iconfont icon-tishi"></i><span class="spans">转至提示<span/></li>
						<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
						<input type="text" id="vzroom" class="roomIds" style="display:none;"/>
						<input type="text" id="thecheckid" class="mycheckid" style="display:none;"/>
					</ul>
				</div>
			</div>
		</div>
	
		<div class="apartment" style="display:none;">
			<div class="main_wrapper_apartment">
				<div class="main_content">
					<ul class="room_list">
					</ul>
				</div>
			</div>
			<div class="room_count">
				<table class="table_count"> 
					<thead><tr><th>房源状态</th><td>已入住</td><td>预定</td><td>空置房</td><td>停售房</td><td>维修</td><td>样板房</td><td>员工宿舍</td><td>其他</td></tr></thead>
					<tbody>
					<tr><th>颜色条</th><td><div id="checkin"></div></td><td ><div id="reserve"></div></td> <td><div id="null"></div></td><td ><div id="stopsale"></div></td><td ><div id="repair"></div></td><td ><div id="demoRoom"></div></td> <td><div id="staffRoom"></div></td><td ><div id="other"></div></td>
					</tr>
					<tr><th>房间数</th><td id="checkin_count"></td><td id="reserve_count"></td> <td id="null_count"></td><td id="stopsale_count"></td><td id="repair_count"></td><td id="demoRoom_count"></td> <td id="staffRoom_count"></td><td id="other_count"></td></tr>
					<tr><hr class ="hrStyle"></tr>
					<tr><th>总房间数</th><td id="total_count" ></td><th>成交小计</th><td id="deal_count" ></td><th>出租率</th><td colspan='2' id="checkin_rate"></td></tr>
					</tbody>
				</table>
			</div>
			
			<div class="setmargin apartmaintroom" style="display:none">
				<ul><%--
					<li onclick="setroom('1', '2');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置清洁房<span/></li>
					--%><li onclick="setroom('T', '2');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置停售房<span/></li>
					<!-- <li onclick="setroom('W', '2');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置维修房<span/></li> -->
					<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
					<input type="text" id="housezroom" class="roomIds" style="display:none;"/>
					<input type="text" id="myRoomType" name="myRoomType" style="display:none;"/>
				</ul>
			</div>
			<div class="setmargin apartcleanroom" style="display:none">
				<ul>
					<li onclick="setroom('T', '2');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置停售房<span/></li>
					<!--  <li onclick="setroom('W', '2');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置维修房<span/></li>-->
					<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
					<input type="text" id="housecroom" class="roomIds" style="display:none;"/>
				</ul>
			</div>
			<div class="setmargin apartstoproom" style="display:none">
				<ul>
					<!-- <li onclick="setroom('3', '2');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置在住<span/></li> -->
					<!--  <li onclick="setroom('W', '2');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置维修房<span/></li>-->
					<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
					<input type="text" id="housecroom" class="roomIds" style="display:none;"/>
				</ul>
			</div>
			<div class="setmargin apartliveroom" style="display:none">
				<ul>
<!-- 					<li onclick="setroom('W', '2');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置维修房<span/></li>
 -->					<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
					<input type="text" id="housecroom" class="roomIds" style="display:none;"/>
				</ul>
			</div>
		</div>
		
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
						<li class="" >
							<span>打印机</span>
						</li>
					</ul>
				</form>
			</div>
			<div class="setmargin housecleanroom" style="display:none">
				<ul>
					<li onclick="setroom('T','3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置停售房<span/></li>
<!-- 					<li onclick="setroom('W', '3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置维修房<span/></li>
 -->					<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
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
<!-- 					<li onclick="setroom('W', '3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置维修房<span/></li>
 -->					<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
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
<!-- 					<li onclick="setroom('W', '3');" class="setclean"><i class="iconfont icon-zangfang"></i><span class="spans">设置维修房<span/></li>
 -->					<li onclick="refresh();"><i class="iconfont icon-shuaxin"></i><span class="spans">刷新<span/></li>
					<input type="text" id="housecroom" class="roomIds" style="display:none;"/>
				</ul>
			</div>
		</div>
	<script src="<%=request.getContextPath()%>/script/ipmshotel/roomstatus/room.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>	
	<script src="<%=request.getContextPath()%>/script/ipmshotel/customer/customer.js"></script>
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