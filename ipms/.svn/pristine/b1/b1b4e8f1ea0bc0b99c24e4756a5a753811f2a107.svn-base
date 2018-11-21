<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<link rel="stylesheet" type="text/css" href="css/reset.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reportform/report_forms.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/font-awesome.min.css"/>
  </head>
  
  <body>
    <div class="check_wrap_margin check_color">
			<div class="left_slidebar">
				<ul id="accordion" class="accordion">
					<li>
						<div class="link"><i class="fa fa-paint-brush"></i>统计<i class="fa fa-chevron-down"></i></div>
						<ul class="submenu">
							<li><a href="javascript:;">销售汇总</a></li>
							<li><a href="javascript:;">销售明细</a></li>
							<li><a href="javascript:;">销售日报表</a></li>
							<li><a href="javascript:;">销售月报表</a></li>
							<li><a href="javascript:;">畅销排行（房型、商品）</a></li>
							<li><a href="javascript:;">客户来源分析</a></li>
							<li><a href="javascript:;">交接班</a></li>
							<li><a href="javascript:;">会员统计</a></li>
							<li><a href="javascript:;">服务需求分析</a></li>
							<li><a href="javascript:;">综合报表</a></li>
						</ul>
					</li>
				</ul>
			</div>
			<div class="shop_content">
				<div class="shop_search">
					<form action="" method="post">
						<input type="text" name="text" id="text" class="shop_name" value="" placeholder="编码、名称" />
						<select name="" class="search_select">
							<option value="0">所有分类</option>
							<option value="1">王老吉</option>
						</select>
						<section class="box-content-section fl">
							<section class="box_content_widget fl">
								<div class="content">
									<table id="myTable" border="0" width="100">
										<thead>
											<tr>
												<th class="header">编码</th>
												<th class="header">商品名称</th>
												<th class="header">自定义名称</th>
												<th class="header">分类</th>
												<th class="header">单价(元)</th>
												<th class="header">单位</th>
												<th class="header">操作</th>
											</tr>
										</thead>
										<tbody>
											<tr class="odd">
												<td>2013</td>
												<td>0</td>
												<td>0</td>
												<td>John Doe</td>
												<td>John Doe</td>
												<td>01/3/2013</td>
												<td>售卖</td>
											</tr>
										</tbody>
									</table>
								</div>
							</section>
						</section>
					</form>
				</div>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/ipms/js/jquery-1.8.2.min.js" 
		type="text/javascript" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>	
		<script src="<%=request.getContextPath()%>/script/ipms/js/index.js" type="text/javascript" charset="utf-8"></script>
  </body>
</html>
