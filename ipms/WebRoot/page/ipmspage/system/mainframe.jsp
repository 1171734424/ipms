<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("branchname", request.getAttribute("branchname"));
request.setAttribute("staffid", request.getAttribute("staffid"));
request.setAttribute("staffname", request.getAttribute("staffname"));
request.setAttribute("webstatus", request.getAttribute("webstatus"));
request.setAttribute("branchrank", request.getAttribute("branchrank"));
request.setAttribute("shiftname", request.getAttribute("shiftname"));
request.setAttribute("branchtype", request.getAttribute("branchtype"));
request.setAttribute("houseAdmin", request.getAttribute("houseAdmin"));
request.setAttribute("systemStatus", request.getAttribute("systemStatus"));
request.setAttribute("subManager", request.getAttribute("subManager"));
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <c:choose>
	    <c:when test="${subManager != 'subManager' && houseAdmin == 'houseAdmin'}">
	    	<title>PMS民宿</title>
	    </c:when>
	    <c:when test="${branch.branchType == '2'}">
	    	<title>PMS公寓</title>
	    </c:when>
	    <c:when test="${branch.branchType == '1'}">
	    	<title>PMS酒店</title>
	    </c:when>
	    <c:otherwise>  
	    	<title>主界面</title>
	    </c:otherwise>
  	</c:choose>
    <meta http-equiv="pragma" content="no-cache">
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">	
  <%@ include file="../../common/css.jsp"%>
	<link rel="Shortcut Icon" href="<%=request.getContextPath()%>/css/ipms/img/backgroundimg/deer.png" type="image/x-icon">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/system/mainframe.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/loginfile/box_popup.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/animate.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon/iconfont.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<script type="text/javascript">
	var base_path = "<%=request.getContextPath()%>";
	var img_url="<%=request.getContextPath()%>/css/ipms/img/png-logo/";
	</script>	
 </head>
  <body class="push">
    	<div id="box_mainframe" class="box_mainframe">
    	<!-- 左侧导航 -->
    		<nav class="box_left_sliderbar box_left_vertical fl" id="left_menu">
    			<div class="header_title">
    				<span class="logo_icon"><img src="<%=request.getContextPath()%>/css/ipms/img/backgroundimg/logo.png" alt="logo"/></span>
    				<span class="title_word">
    					<c:choose>
						    <c:when test="${subManager != 'subManager' && houseAdmin == 'houseAdmin'}">
						    	PMS民宿
						    </c:when>
						    <c:when test="${branch.branchType == '2'}">
						    	PMS公寓
						    </c:when>
						    <c:when test="${branch.branchType == '1'}">
						    	PMS酒店
						    </c:when>
						    <c:otherwise>  
						    	主界面
						    </c:otherwise>
					  	</c:choose>
    				</span>
    				<p class="branchname">${branchname}</p>
    				<p id="hotelshift" class="clearfix"><span class="one fl">班次：</span><span class="two fr">${shiftname}</span></p>
    				<p class="clearfix"><span class="one fl">当班：</span><span class="two fr">${staffname}</span></p>
    				<c:if test="${ systemStatus == '2'}">
    					<p id="staffwebstatus" class="clearfix"><span class="one fl">状态：</span><span  id="statusId" class="two online fr">${webstatus}</span></p>
    				</c:if>
    			</div>
    			<!--  <div class="sidebar_fold">
    				<span class="fa fa-bars"></span>
    			</div>-->
				<ul class="box_left_sliderbar_ul" id="accordion">
					<c:if test="${subManager != 'subManager' && house == '3'}">
						<li class="section">
						<div class="link"><i class="fa fa-tasks"></i>民宿管理<i class="fa fa-chevron-down"></i></div>
							<ul class="light_cash submenu">				
								<li class="register"><span class=" f5"></span><a onclick="memberManage()">会员查询</a></li>
								<li class="last"><span class=" f5"></span><a onclick = "goodsInHouse()">商品售卖</a></li>
								<li class="register"><span class=" f5"></span><a onclick="houseRefund()">民宿结账提醒</a></li>
								<li class="register"><span class=" f5"></span><a onclick="repairInHouse()">民宿维修处理</a></li>
								<li class="register"><span class=" f5"></span><a onclick="dealHouseClean()">民宿保洁处理</a></li> 
								<li class="register"><span class=" f5"></span><a onclick="housestaff()">管理员信息</a></li>
							</ul>
							<div class="link"><i class="fa fa-tasks"></i>常用<i class="fa fa-chevron-down"></i></div>
							<ul class="light_cash submenu">
								<li class="register"><span class=" f5"></span><a onclick="houseOrder()">预定</a></li>
								<li class="register"><span class=" f5"></span><a onclick="clockIn()">打卡</a></li> 
							</ul>
						</li>
				    </c:if>
					<c:if test="${ branch.branchType == '1' || loginBranch.branchId == '100001'}">
						<li class="section"> 
							<div class="link"><i class="fa fa-desktop"></i>常用<i class="fa fa-chevron-down"></i></div>
							<ul class="submenu">
								<li><span class="f1"></span><a onclick="orderSerach()">订单查询</a></li>
								<li><span class="f1 f2"></span><a onclick="orderNew()">预定入住</a></li>
								<%-- <li><span class="f1 f1"></span><a onclick="checkInDirect()">直接入住</a></li>--%>
								<li class="last"><span class="f1 f3"></span><a onclick = "hotelgoods()">商品售卖</a></li>
							</ul>	
						</li>
						<li class="section">
							<div class="link"><i class="fa fa-users"></i>会员<i class="fa fa-chevron-down"></i></div>
							<ul class="submenu">
								<li class="last"><span class="f1 f4"></span><a onclick="memberSearch()">查询会员</a></li>
								<li class="last"><span class="f1 f4"></span><a onclick="memberAddLevel()">购卡升级</a></li>
							</ul>
						</li>
						<li class="section">
							<div class="link"><i class="fa fa-bell"></i>日结异常<i class="fa fa-chevron-down"></i></div>
							<ul class="submenu">
								<li class="last"><span class="f1 f4"></span><a onclick="exceptionSearch()">日结异常</a></li>
							</ul>
						</li>
						<c:if test="${subManager != 'subManager'}">
							<li class="section">
							<div class="link"><i class="fa fa-tasks"></i>其他<i class="fa fa-chevron-down"></i></div>
								<ul class="light_cash submenu">
									<li class="register"><span class="f1 f5"></span><a onclick="pettyCash()">备用金交接</a></li>
									<%--<li class="register"><span class=" f5"></span><a onclick="updatebyhand()">手动上传</a></li>--%>
								</ul>
							</li>
						</c:if>
								
						
					</c:if>	
					<c:if test="${ branch.branchType == '2' || loginBranch.branchId == '100001'}">
						<li class="section">
						<div class="link"><i class="fa fa-tasks"></i>公寓管理<i class="fa fa-chevron-down"></i></div>
							<ul class="light_cash submenu">
								<li class="register"><span class=" f5"></span><a onclick="apartmentReFund()">公寓退款</a></li>
								<li class="last"><span class=" f5"></span><a onclick = "apartmentGoods()">商品售卖</a></li>
								<li class="register"><span class=" f5"></span><a onclick="apartmentRent()">房租到期提醒</a></li>
								<li class="register"><span class=" f5"></span><a onclick="apartmentRoomStatus()">公寓远期房态</a></li>
								<li class="register"><span class=" f5"></span><a onclick="apartmentReserved()">处理公寓预约</a></li>
								<li class="register"><span class=" f5"></span><a onclick="apartmentCheckOut()">处理退房申请</a></li>
								<li class="register"><span class=" f5"></span><a onclick="apartmentHandleApply()">处理保洁申请</a></li>
								<li class="register"><span class=" f5"></span><a onclick="apartmentRepair()">公寓维修申请</a></li>
<!--								<li class="register"><span class=" f5"></span><a onclick="editHouseManager()">管家信息管理</a></li>-->
								<li class="register"><span class=" f5"></span><a onclick="apartmentclicktaff()">管家信息管理</a></li>
								
								<%--<li class="register"><span class=" f5"></span><a onclick="updatebyhand()">手动上传</a></li>--%>
							</ul>
						</li>
				    </c:if>
					<li class="section">
					<div class="link"><i class="fa fa-tasks"></i>登录信息<i class="fa fa-chevron-down"></i></div>
						<ul class="light_cash submenu">
							<li class="register"><span class="f1 f5" style="width: 18%;height: 65%;text-indent: 3px;line-height: 28px;">F10</span><a href="<%=request.getContextPath()%>/loadMainFrame.do">退出</a></li>
						</ul>	
					</li>
				</ul>
			</nav>
			<div class="picBox" onclick="switchSysBar()" id="switchPoint">
				<span class="fa fa-chevron-left"></span>
			</div> 
			<!--头部导航 -->
			<div class="wrap_right">
				<div class="header-toggle">
					<ul class="header_toggle_ul">
							<c:choose>
								<c:when test="${ branch.branchType == '2' || loginBranch.branchId == '100001'}">
									<li class="header_toggle_line active" index="0" id="contentFrame_first"  onclick="apartmentchangeUrl(this)">
										<a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/room_status.png" alt="房态图"><span>房态图</span></a>
									</li>
								</c:when>
								<c:when test="${ branch.branchType == '1' || loginBranch.branchId == '100001'}">
									<li class="header_toggle_line active" index="0" id="contentFrame_first"  onclick="hotelchangeUrl(this)">
										<a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/room_status.png" alt="房态图"><span>房态图</span></a>
									</li>
								</c:when>
								<c:otherwise>  
						    		<li class="header_toggle_line active" index="0" id="contentFrame_first"  onclick="houseChangeUrl(this)">
										<a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/room_status.png" alt="房态图"><span>房态图</span></a>
									</li>
						    	</c:otherwise>
							</c:choose>
						<c:if test="${ branch.branchType == '2' || loginBranch.branchId == '100001'}">
							<li class="header_toggle_line " index="10" id="menu_910" onclick="apartmentCheckIn(this)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/apartinfo.png" alt="公寓合同"/><span>公寓合同</span></a></li>
							<li class="header_toggle_line" index="3" id="menu_903" onclick="apartmentchangeUrlthree(this,2)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/order.png" alt="订单"/><span>订单</span></a></li>
					    </c:if>
						<c:if test="${ branch.branchType == '1' || loginBranch.branchId == '100001'}">
							<li class="header_toggle_line " index="1" id="menu_901" onclick="changeUrlone(this,1)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/count.png" alt="房态统计"/><span>房态统计</span></a></li>
							<li class="header_toggle_line" index="2" id="menu_902" onclick="changeUrltwo(this)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/city.png"  alt="同城房态"/><span>同城房态</span></a></li>
							<li class="header_toggle_line" index="5" id="menu_908" onclick="changeUrlfive(this,1)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/workaccout.png" alt="工作帐"/><span>工作帐</span></a></li>
						</c:if>
						<c:if test="${ branch.branchType == '1'}">
							<li class="header_toggle_line" index="8" id="menu_906" onclick="reportForms(this)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/report.png" alt="报表"/><span>报表</span></a></li>
						</c:if>
						<c:if test="${ branch.branchType == '2'}">
						<li class="header_toggle_line" index="7" id="menu_907" onclick="apartmentchangeIntegral(this,2)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/log.png" alt="日志"/><span>日志</span></a></li>
						<li class="header_toggle_line" index="5" id="menu_908" onclick="changeUrlfive(this,2)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/workaccout.png" alt="工作帐"/><span>工作帐</span></a></li>
						<li class="header_toggle_line" index="6" id="menu_905" onclick="changeUrlsix(this,2)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/stop_sell.png" alt="停售房"/><span>停售房</span></a></li>
						<li class="header_toggle_line" index="6" id="menu_911" onclick="changeUrlhydropower(this,2)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/water_electric.png" alt="水电查询"/><span>水电查询</span></a></li>
						</c:if>
						<c:if test="${ branch.branchType == '1' || loginBranch.branchId == '100001' }">
							<li class="header_toggle_line" index="3" id="menu_903" onclick="changeUrlthree(this,null)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/order.png" alt="订单"/><span>订单</span></a></li>
							<li class="header_toggle_line" index="4" id="menu_904" onclick="changeUrlfour(this,null)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/icon_room.png" alt="房单" /><span>房单</span></a></li>
							<li class="header_toggle_line" index="7" id="menu_907" onclick="changeIntegral(this,1)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/log.png" alt="日志"/><span>日志</span></a></li>
							<li class="header_toggle_line" index="6" id="menu_905" onclick="changeUrlsix(this,1)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/stop_sell.png" alt="停售房"/><span>停售房</span></a></li>
							<%--<li class="header_toggle_line " index="3" id="menu_903" onclick="quertHouseOrder(this)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/order.png" alt="订单查询"/><span>订单查询</span></a></li>
							<li class="header_toggle_line " index="4" id="menu_904" onclick="quertHouseCheck(this)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/icon_room.png" alt="房单查询"/><span>房单查询</span></a></li>
							<li class="header_toggle_line " index="7" id="menu_910" onclic	k="changeIntegral(this,3)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/log.png" alt="操作日志"/><span>操作日志</span></a></li>
					    --%></c:if>
					    <c:if test="${ subManager != 'subManager' && house == '3'}">
							<li class="header_toggle_line active" index="3" id="contentFrame_houseone" onclick="changeUrlthree(this,3)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/order.png" alt="订单"/><span>订单</span></a></li>
							<li class="header_toggle_line" index="4" id="menu_904" onclick="changeUrlfour(this,3)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/icon_room.png" alt="房单" /><span>房单</span></a></li>
							<li class="header_toggle_line" index="7" id="menu_907" onclick="changeIntegral_house(this,3)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/log.png" alt="日志"/><span>日志</span></a></li>
							<li class="header_toggle_line" index="6" id="menu_905" onclick="changeUrlsix(this,3)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/stop_sell.png" alt="停售房"/><span>停售房</span></a></li>
							<li class="header_toggle_line " index="1" id="menu_901" onclick="changeUrlone(this,3)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/count.png" alt="房态统计"/><span>远期房态</span></a></li>
							<%--<li class="header_toggle_line" index="6" id="menu_911" onclick="changeUrlhydropower(this,3)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/stop_sell.png" alt="水电查询"/><span>水电查询</span></a></li>	
						--%>
							<li class="header_toggle_line " index="8" id="menu_008" onclick="changeUrleight(this)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/count.png" alt="房态统计"/><span>房态统计</span></a></li>							
							<%-- <c:if test="${ housemanager == 'housemanager'}">
								<li class="header_toggle_line" index="8" id="menu_906" onclick="houseReportForms(this)"><a class="header-toggle-div"><img src="<%=request.getContextPath()%>/css/ipms/img/png-logo/report.png" alt="报表"/><span>报表</span></a></li>
							</c:if> --%>
						</c:if>
					</ul>	
				
				</div>
				<div id="st-scroll">
				</div>
				<div class="mark"></div>
			</div>
		</div>
		<%-- <div class="footer"><%@include file="../system/footer.jsp" %></div> --%>
		<div id="next" style="display:none;">
		</div>
		<div class="wholeloading" style="display:none;">
			<div class="loading_div">
				<span class="iconfont icon-orderloading icon-loa"></span>
			</div>
		</div>
		<input type="hidden" id="branchrank" value="${branchrank}" />
		<input type="hidden" id="branchtype" value="${branchtype}" />
		<input type="hidden" id="housemanager" value="${housemanager}" />
		<!--遮罩层-->
		  <%@ include file="../../common/script.jsp"%>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/classie.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/index.js"></script>
		<script  src="<%=request.getContextPath()%>/script/ipms/js/mainframe/mainframe.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
		$(document).ready(function(){
		/* var branchTypeaa = '${branch.branchType}';
		var subManager = '${subManager}'; */
		//console.log(branchTypeaa)
		//console.log(subManager)
		});
		</script>
  </body>
</html>
