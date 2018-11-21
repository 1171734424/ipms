<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>报表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reportform/report_forms.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	</head>

	<body>
		<div class="check_wrap_margin check_color">
			<div class="left_slidebar">
				<ul id="accordion" class="accordion">
					<li>
						<div class="link">
							<i class="imooc">&#xe9d9;</i>渠道分析报表
						</div>
						<ul class="submenu">
							<li class="onColor">
								<a href="javascript:void(0)" onclick="queryChannelAnalyse();return false;">渠道分析报表</a>
							</li>
						</ul>
					</li>
										<li>
						<div class="link">
							<i  class="imooc">&#xe9d9;</i>经营报表
						</div>
						<ul class="submenu">
							<li>
								<a href="javascript:void(0)" onclick="queryOperationStatement();return false;">经营报表</a>
							</li>
						</ul>
					</li>
					<li>
						<div class="link">
							<i  class="imooc">&#xe9d9;</i>财务报表
						</div>
						<ul class="submenu">
							<li>
								<a href="javascript:void(0);" onclick="queryIntoAccountDetil();return false;">入账项目明细表</a>
							</li>
							<li>
								<a href="javascript:void(0)" onclick="queryWriteDownDetile();return false;">冲减明细表</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
			<div id="channelAnalyse" class="shop_content">
				<iframe name="frame" id="frame" class="myTable" frameborder="0" width="100%" height="100%" ></iframe>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/ipms/js/jquery-1.8.2.min.js"  charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/index.js"  charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipmsinhouse/reportforms/houseReportform.js"  charset="utf-8"></script>
	</body>
</html>

