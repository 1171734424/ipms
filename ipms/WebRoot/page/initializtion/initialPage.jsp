<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html style="height:100%;width:100%;">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>系统初始化设置</title>
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bwizard.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
	</head>
	<body style="padding-top: 125px;">
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<a class="brand" href="#">幕后致选</a>
				</div>
			</div>
		</div>
		<div class="container">
			<h2>初始化设置</h2>
			<div id="wizard" class="bwizard clearfix" style="height:600px;">
				<ol class="bwizard-steps clearfix" role="tablist">
					<li id="title1" role="tab" aria-selected="false" style="z-index: 6;">
						<span class="label">1</span><span class="hidden-phone">操作步骤</span>
					</li>
					<li id="title2" role="tab" aria-selected="false" style="z-index: 5;">
						<span class="label">2</span><span class="hidden-phone">门店管理</span>
					</li>
					<li id="title3" role="tab" aria-selected="false" style="z-index: 4;">
						<span class="label">3</span><span class="hidden-phone">参数管理</span>
					</li>
					<li id="title4" role="tab" aria-selected="false" style="z-index: 3;">
						<span class="label">4</span><span class="hidden-phone">房型管理</span>
					</li>
					<li id="title5" role="tab" aria-selected="false" style="z-index: 2;">
						<span class="label">5</span><span class="hidden-phone">房间管理</span>
					</li>
					<li id="title6" role="tab" aria-selected="false" style="z-index: 1;">
						<span class="label">6</span><span class="hidden-phone">导向完成</span>
					</li>
				</ol>
				<iframe name="frame" id="frame" frameborder="0" width="100%" height="100%">
				</iframe>
				<ul class="pager bwizard-buttons">
					<li class="previous" role="button" aria-disabled="false">
						<a href="#" onclick="toPrevious();">← 后退</a>
					</li>
					<li class="next" role="button" aria-disabled="false">
						<a href="#" onclick="toNext();">下一步 →</a>
					</li>
				</ul>
			</div>
		</div>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/initialData/jquery.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/initialData/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/initialData/initialPage.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
           <script type="text/javascript">
	       var base_path ="<%=request.getContextPath()%>";
   		   var steps ="${steps}";
 		   
 		   $(document).ready(function() {
				loadCheckStyle(1);
				instruction();	
			});
       </script>
	</body>
</html>

