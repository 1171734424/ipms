<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>远期房态</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/jquery-easyui/themes/default/easyui.css" />
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/loginfile/box_popup.css" />
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
  </head>
  
  <body>
    <div class="box_popup_member box_popup_member_margin">
		<h2>远期房态</h2>
		<span class="top_icon"><i class="imooc im_top" onclick="window.parent.JDialog.close();">&#xea0f;</i></span>
		<form action="" method="post">
			<div class="content_member">
				<div class="search_style fl">
					<ul class="search_member clearfix fl">
						<li><label class="label_name">开始日期:</label><!-- <input name="" type="text" class="search_text">-->
						<input id="mdate" type="text" name="mdate" class="date_txt" style="width:150px;height:30px;margin-left:10px;"/>
						<li><label class="label_name">显示天数:</label>
						<select class="days search_text">
							<option value="7" selected>7</option>
							<option value="10">10</option>
							<option value="15">15</option>
						</select>
						</li>
						<li><button type="button" class="btn_style btn_search" onclick="queryforward();">查询</button></li>
					</ul>
				</div>
				<div class="content_member_show">
				<section class="box-content-section fl" style="width:100%;">
					<section class="box_content_widget fl">
						<div id="integral" class="content" style="height:631px;padding:10px;">
							<iframe name="forwardFrame" id="forwardFrame" frameborder="0" width="100%" height="100%"></iframe>
						</div>
					</section>
				</section>
			</div>
			</form>
		</div>
		<script src="<%=request.getContextPath()%>/css/ipms/jquery-easyui/jquery.min.js"></script>	
		<script src="<%=request.getContextPath()%>/script/ipms/js/jquery-1.8.2.min.js" 
			type="text/javascript" charset="utf-8"></script>	
		<script src="<%=request.getContextPath()%>/css/ipms/jquery-easyui/jquery.easyui.min.js"></script>	
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
		<script type="text/javascript">
			var page_li=$(".box_pagenation ul li");
			laydate ({
			   	elem: '#mdate'
			   	,format: 'YYYY/MM/DD'   	
			   	,min: laydate.now(+1)
			    ,choose: function() {
					$("#forwardFrame").attr("src","forwardRoom.do?madate="+$("#mdate").val()+"&days="+$(".days").val());
		        }  
			});
			  page_li.on('click',function() {
				  	$(this).addClass('active');
				  	$(this).siblings().removeClass('active');
			  })
			  $(document).ready(function() {
			  	  var imooc=$(".imooc");
				  $(".imooc").on('click',function(){
				  $(".box_popup_member").fadeOut(300);   
				  });
				var now = new Date();    
			    var year = now.getFullYear();       //年   
			    var month = now.getMonth() + 1;     //月   
			    var day = now.getDate() + 1;
			    var time = year+"/"+month+"/"+day;
			    $("#mdate").val(laydate.now(+1));
				$("#forwardFrame").attr("src","forwardRoom.do?madate="+ $("#mdate").val()+"&days="+$(".days").val());
			 });

			function queryforward() {
				$("#forwardFrame").attr("src","forwardRoom.do?madate="+$("#mdate").val()+"&days="+$(".days").val());
			}
		</script>
  </body>
</html>
