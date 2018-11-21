<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>停售房信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/reset.css" />
	<link rel="stylesheet" type="text/css" href="css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="css/roomlist.css" />

  </head>
  
  <body>
    <div class="header_roomlist">
			<ul>
				<li class="header_roomlist_li">
					<span>所有停售房</span>
				</li>
				<li class="header_roomlist_li">
					<span>计划停售房</span>
				</li>
				<li class="header_roomlist_li">
					<span>活动停售房</span>
				</li>
				<li class="header_roomlist_li">
					<span>已取消停售房</span>
				</li>
				<li class="header_roomlist_li">
					<span>已完成停售房</span>
				</li>
			</ul>
			<div class="fr">
				<span class="highsearch">高级查询</span>
				<span class="createhouse">增加</span>
			</div>
			<!--增加-->
			<div class="high_header">
				<h2>增加</h2>
				<i class="imooc" style="color:#3B3E40;">&#xe902;</i>
				<form action="" method="post">
					<div class="margintop">
						<ul class="clearfix">
							<input name="会员编号" type="hidden" class="" />
							<li><label class="label_name">房间号码</label><input name="会员编号" type="text" value="" id="id" class="text_search"></li>
							<li><label class="label_name">停售原因</label><input name="会员卡号" type="text" value="" class="text_search"></li>
							<li><label class="label_name">开始日期</label><input name="会员编号" type="text" value="" id="id" class="text_search"></li>
							<li><label class="label_name">结束日期</label><input name="会员卡号" type="text" value="" class="text_search"></li>
							<li>
								<input name="确定" type="submit" value="确定" class="">
								<input name="取消" type="submit" value="取消" class="">
								
							</li>
						</ul>
					</div>
				</form>
			</div>
		</div>
		<section class="box-content-section fl">
			<section class="box_content_widget fl">
				<div class="content">
					<table id="myTable" class="myTable" border="0" width="100">
						<thead>
							<tr>
								<th class="header">房单号</th>
								<th class="header">房号</th>
								<th class="header">类型</th>
								<th class="header">状态</th>
								<th class="header">开始日期</th>
								<th class="header">结束日期</th>
								<th class="header">创建者</th>
								<th class="header">原因</th>
								<th class="header">说明</th>
							</tr>
						</thead>
						<tbody>
							<tr class="odd" onclick="aa()" ondblclick="bb()">
								<td><input type="checkbox"> Home</td>
								<td>01/3/2013</td>
								<td>0</td>
								<td>John Doe</td>
								<td>John Doe</td>
								<td>01/3/2013</td>
								<td>0</td>
								<td>0</td>
								<td>John Doe</td>

							</tr>
							<tr>
								<td><input type="checkbox"> Services</td>
								<td>01/3/2013</td>
								<td>4</td>
								<td>0</td>
								<td>John Doe</td>
								<td>01/3/2013</td>
								<td>0</td>
								<td>0</td>
								<td>0</td>

							</tr>
						</tbody>
					</table>
				</div>
			</section>
		</section>
		
		<script src="js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
			$(".highsearch").on("click", function() {
				$(".high_header").css("display", "block");
			})
			$(".imooc").on("click", function() {
				$(".high_header").fadeOut(300);
			})
			var timer = null;
			function aa() {
				clearTimeout(timer);
				if (event.detail == 2)
					return;
				timer = setTimeout(function() {
					console.log('单击');
				}, 300);
			}
			function bb() {
				clearTimeout(timer);
				window.location.href="stophouse_info.jsp";
			}
			$(".createhouse").on("click",function(){
				window.location.href="stophouse_add.jsp";
			});
		</script>
  </body>
</html>
