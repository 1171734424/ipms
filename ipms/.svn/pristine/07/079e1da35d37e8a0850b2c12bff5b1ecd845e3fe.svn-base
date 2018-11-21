<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/script.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>水电查询</title>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/integral/integral.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
  	<style type="text/css">
	  	input.batchpayHydropower{
		    display: inline-block;
		    margin: 10px 0px;
		    padding: 1px 7px;
		    height: 38px;
		    line-height: 35px;
		    border: none;
		    text-align: center;
		    cursor: pointer;
		    border-radius: 4px;
		    background: #FC8A15;
		    color: #fff;
		    font-size: 13px;
		    margin-right: 20px;
		}
		div.batchPay {
			display: none;	
		}
  	</style>
  </head>
  
  <body>
  	 <div class="integral_margin">
    	<div class="header_roomlist">
			<ul>
                <li class="header_roomlist_li active" index="0">
					<span>完结合同</span>
				</li>
				<li class="header_roomlist_li" index="1">
					<span>在住合同</span>
				</li>
				<li class="header_roomlist_li" index="2">
					<span>水电记录</span>
				</li>
				<li class="header_roomlist_li" index="3">
					<span>水电缴费</span>
				</li>
			</ul>
			<div class="fr batchPay">
				<span><input type="button" value="批量支付" class="batchpayHydropower" onclick="payHydropower()"></span>
			</div>
			<div class="fr">
				<span class="highsearch">高级查询</span>
			</div>
			<div class="high_header inte-padding fadeInDown">
				<h3>高级查询</h3>
				<i class="imooc imooc_log" style="color:#fff;">&#xe900;</i>
				<form name="condition" id="condition" action="aprtmentleavedHydropowerLog.do" method="post" target="logFrame">
				<input id="type" name="type" type="hidden" value='<%=request.getParameter("type")%>'>
					<div class="margintop">
						<ul class="clearfix">
						  <li>
							<label class="label_name in_select">合同选择</label>
							<select id="selectAge" class="text_search" disabled='disabled'>
							    <option value="0">完结合同</option>
							    <option value="1">在住合同</option>
							    <option value="2">水电记录</option>
							    <option value="3">水电缴费</option>
						    </select>
						 </li>
						</ul>
						<ul id="loginfo" class="clearfix">
							<li><label class="label_name">合同号</label><input name="contractId" id="contractId" type="text" value="" class="text_search"></li>
							<li><label class="label_name">房间号</label><input name="roomId" id="roomId" type="text" value="" class="text_search"></li>
							<li><label class="label_name">手机号</label><input name="mobile" id="mobile" type="text" value="" class="text_search"></li>
							<li><label class='label_name'>合同状态</label>
								<select name='ctStatus' id='ctStatus' id='id' class='text_search'>
									<option value=''>--请选择--</option>
									<option value='1'>有效</option>
									<option value='2'>退租</option>
									<option value='3'>已退未结</option>
									<option value='4'>在住</option>
									<option value='0'>取消</option>
							    </select>
							</li>
						</ul>
					</div>
					<div class="time_div">
						<ul class="clearfix" id="timeShow">
							<li>
								<label class="label_name">离店时间</label>
								<input id="leavetime" name="leavetime" type="text" value="" class="text_search check date">
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
					<iframe name="logFrame" id="logFrame" frameborder="0" width="100%" height="100%" src="apartmentleavedHydropowerLog.do?type=<%=request.getParameter("type")%>"></iframe>
				</div>
			</section>
		</section>
	 </div>
	</div>
	<script src="<%=request.getContextPath()%>/script/apartment/apartmentmainmenu/apartmenthydropower/hydropower.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script>
		var base_path = "<%=request.getContextPath()%>";
		var type = "<%=request.getParameter("type")%>";
	</script>
  </body>
</html>
