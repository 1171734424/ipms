<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查找订单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/loginfile/box_popup.css" />
	<link rel="stylesheet" type="text/css" href="css/reset.css" />
	<link rel="stylesheet" type="text/css" href="css/commom_table.css"/>
	<link rel="stylesheet" type="text/css" href="css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" type="text/css" href="css/pagenation.css"/>

  </head>
  
  <body>
    <div class="box_popup_member box_popup_member_margin">
		<h2>查找订单</h2>
		<form action="" method="post">
			<div class="content_member">
				<i class="imooc" style="color:#3B3E40;">&#xe902;</i>
				<div class="search_style fl">
					<ul class="search_member clearfix fl">
						<li><label class="label_name">订单号:</label><input name="" type="text" class="search_text"></li>
						<li><label class="label_name">预订人:</label><input name="" type="text" class="search_text"></li>
						<li><label class="label_name">入住人:</label><input name="" type="text" class="search_text" ></li>
						<li><label class="label_name">会员手机:</label><input name="" type="text" class="search_text" ></li>
						<li><label class="label_name">会员卡号:</label><input name="" type="text" class="search_text" ></li>
						<li><button type="button" class="btn_style btn_search">查询</button></li>
					</ul>
				</div>
				<div class="content_member_show">
					<section class="box_content_section fl">
						<section class="box_content_widget fl">
							<div class="content">
								<table id="myTable" class="myTable" border="0" width="100">
									<thead>
										<tr>
											<th class="header">订单号</th>
											<th class="header">预定时间</th>
											<th class="header">房型</th>
											<th class="header">房价</th>
											<th class="header">担保类型</th>
											<th class="header">保留时间</th>
											<th class="header">预订人</th>
											<th class="header">入住日期</th>
											<th class="header">退房日期</th>
											<th class="header">入住人</th>
											<th class="header">预定渠道</th>
											<th class="header">备注</th>
										</tr>
									</thead>
									<tbody>
										<tr class="odd">
											<td><input type="checkbox"> Home</td>
											<td>01/3/2013</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
										</tr>
										<tr>
											<td><input type="checkbox"> Services</td>
											<td>01/3/2013</td>
											<td>4</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
										</tr>
										<tr class="odd">
											<td><input type="checkbox"> Home</td>
											<td>01/3/2013</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
										</tr>
										<tr>
											<td><input type="checkbox"> Services</td>
											<td>01/3/2013</td>
											<td>4</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
											<td>John Doe</td>
											<td>0</td>
											<td>0</td>
										</tr>
									</tbody>
								</table>
							</div>
						</section>
					</section>
				</div>
				<div class="box_pagenation">
						<ul>
							<li><a><i class="arrow_left">&#xe904;</i>上一页</a></li>
							<li class="active"><a>1</a></li>
							<li><a>2</a></li>
							<li><a>3</a></li>
							<li><a>4</a></li>
							<li><a>...</a></li>
							<li><a>5</a></li>
							<li><a><i class="arrow_right">&#xe901;</i>下一页</a></li>
						</ul>
					</div>
			</div>
			</form>
		</div>
		<script src="js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
		  var page_li=$(".box_pagenation ul li");
		  page_li.on('click',function(){
			  	$(this).addClass('active');
			  	$(this).siblings().removeClass('active');
		  })
		  $(document).ready(function(){
		   	var imooc=$(".imooc");
			  $(".imooc").on('click',function(){
			   $(".box_popup_member").fadeOut(300);
			  });
		 });
		function addmember(){
			window.location.href="add_member.jsp";
		}
		</script>
  </body>
</html>
