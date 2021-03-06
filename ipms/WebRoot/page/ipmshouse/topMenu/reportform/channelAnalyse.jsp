<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reportform/report_forms.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/select/jquery.mCustomScrollbar.min.css" />
	 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/select/select.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/theme/default/laydate.css" />
		<title>渠道分析报表</title>
	</head>
	<body style="overflow:hidden;">
		<div class="shop_search">
			<form action="" method="post" id = "myForm">
				<div class="form_margin">
					<input type="text" name="startTime" id="startTime" class="shop_name rnumber" placeholder="月份范围"/>
					<input type="text" name="adminUser" id="adminUser" class="shop_name rnumber" placeholder="管理人员"/>
					<button type="button" class="btn_style btn_search button_margin" onclick="search();">查询</button>
				</div>
				<section class="box-content-section fl">
				<section class="box_content_widget fl">
				<div class="content">
					<iframe name="frame" id="frame" class="myTable" frameborder="0" width="100%" height="100%" ></iframe>
				</div>
				<div id="pager"></div>
				</section>
				</section>
			</form>
		</div>
		<%@ include file="../../../common/script.jsp"%>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.select.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.mCustomScrollbar.concat.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/laydate.js" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
			
			// 初始化加载数据
			$(document).ready(function(){
				// 初始化月
				laydate.render({
					  elem: '#startTime'
					  ,type: 'month'
					  ,range: '~'
					  ,format: 'yyyy-MM'
				});
				// 初始化加载数据详情页
				search();
		    }); 
			
			// showMsg提示框方法
			function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}

			// 输入时间范围查询渠道分析报表
			function search(){
				//判断初始化时间是否为空，如果不为空分割时间，如果为空直接返回两个空的时间段
				var startTime = $("#startTime").val();
				var adminUser = $("#adminUser").val();
				if (startTime == "") {
					$("#frame").attr('src',"queryChannelAnalyseData.do?startTime="+startTime+"&endTime="+startTime+"&adminUser="+adminUser);
				} else if (startTime != "") {
					var times = startTime.split(" ~ ");
					$("#frame").attr('src',"queryChannelAnalyseData.do?startTime="+times[0]+"&endTime="+times[1]+"&adminUser="+adminUser);
				}
			}
	 	</script>
	</body>
</html>