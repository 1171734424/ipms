<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>商品售卖</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/reset.css" />
	<link rel="stylesheet" type="text/css" href="css/shopsell.css" />
	<link href="css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<script src="js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </head>
  
  <body>
    <div class="check_wrap_margin check_color">
			<h2>商品售卖</h2>
			<div class="left_slidebar">
				<ul id="accordion" class="accordion">
					<li>
						<div class="link"><i class="fa fa-paint-brush"></i>小商品管理<i class="fa fa-chevron-down"></i></div>
						<ul class="submenu">
							<li><a href="javascript:;">小商品售卖</a></li>
							<li><a href="javascript:;">小商品管理</a></li>
							<li><a href="javascript:;">报表</a></li>
						</ul>
					</li>
					<li>
						<div class="link"><i class="fa fa-code"></i>旅游商品管理<i class="fa fa-chevron-down"></i></div>
						<ul class="submenu">
							<li><a href="javascript:;">旅游商品售卖</a></li>
							<li><a href="javascript:;">旅游商品管理</a></li>
							<li><a href="javascript:;">报表</a></li>
						</ul>
					</li>
				</ul>
			</div>
			<div class="shop_content">
				<h2>小商品售卖</h2>
				<span>点击列表【售卖】按钮，把商品添加到售卖列表</span>
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
									<table id="myTable" class="myTable" border="0" width="100">
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
											<tr class="odd" onclick="aa()" ondblclick="bb()">
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
		<script src="js/index.js" type="text/javascript" charset="utf-8"></script>
  </body>
</html>
