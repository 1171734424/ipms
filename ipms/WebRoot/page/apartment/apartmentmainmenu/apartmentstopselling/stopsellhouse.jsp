<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>停售房</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </head>
  
  <body>
  
  <div class="stopsell_margin">
   	<div class="header_roomlist">
			<ul>
				<li class="header_roomlist_li active" onclick="getQuerydata(1)">
					<span>有效计划停售房</span>
				</li>
				<li class="header_roomlist_li" onclick="getQuerydata(3)">
					<span>正在执行停售房</span>
				</li>
				<li class="header_roomlist_li" onclick="getQuerydata(0)">
					<span>已取消停售房</span>
				</li>
				<li class="header_roomlist_li" onclick="getQuerydata(2)">
					<span>已完成停售房</span>
				</li>
				<li class="header_roomlist_li" onclick="getQuerydata('')">
					<span>所有停售房</span>
				</li>
			</ul>
			<div class="fr">
				<span class="highsearch">高级查询</span>
				<span class="createhouse">增加</span>
			</div>
			<!--高级查询-->
			<div class="high_header stop fadeIndown">
				<h3>高级查询</h3>
				<i class="imooc imooc_stop" style="color:#fff;">&#xe900;</i>
				<form id="halt_search" method="post">
					<div class="margintop">
						<ul class="clearfix">
							<li><label class="label_name">房间号码</label><input name="roomid" type="text" value="" id="id" class="text_search"></li>
							<li><label class="label_name">停售原因</label>
								<select name="haltreason" id="haltreason" class="text_search">
									<option value="">--请选择停售原因--</option>
									<option value="1">马桶损坏</option>
									<option value="2">天花漏水</option>
									<option value="3">地板变形</option>
									<option value="4">设施损坏</option>
									<option value="5">其他原因</option>
								</select>
							</li>
						</ul>
						<ul class="clearfix">
							<li><label class="label_name">开始日期</label><input name="starttime" type="text" value="" id="startime" class="text_search one"><i class="fa fa-calendar-check-o"></i></li>
							<li><label class="label_name">结束日期</label><input name="endtime" type="text" value="" id="endtime" class="text_search"><i class="fa fa-calendar-check-o"></i></li>
						</ul>
						<ul class="clearfix">
							<li><input  type="button" value="重置" class="button_margin cancel" onclick="resetsearch()"></li>
							<li><input  type="button" value="确定" class="button_margin confirm" onclick="getQuerydata()"></li>
						</ul>
					</div>
					<input type="hidden" id="status" name="status"> 
					<input type="hidden" id="type" name="type" value="${param.type}">
				</form>
			</div>
		</div>
		<section class="box-content-section fl"  style="width:100%;">
			<section class="box_content_widget fl">
				<div class="content" style="height:929px;padding:10px;">
				<iframe name="logFrame" id="logFrame" frameborder="0" width="100%" height="100%" src="apartmentshowstophouse.do?status=1&type=${param.type}"></iframe>
				</div> 
			</section>
		</section>
		</div>
		<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath()%>";
			$(function(){
				$("#status").val("1");
			});
			laydate({
		    	elem: '#startime'
			});
			laydate({
		    	elem: '#endtime'
			});
			$(".highsearch").on("click", function() {
				$(".high_header").css("display", "block");
			})
			$(".imooc").on("click", function() {
				resetsearch();
				$(".high_header").fadeOut(300);
			})
			
			function getQuerydata(status){
				var type = $("#type").val();
				if(typeof(status)!= "undefined"){
					$("#status").val(status);
					$("#logFrame").attr('src',path + "/apartmentshowstophouse.do?status=" + status + "&type=" + type);				
				}
				else{
					$("#logFrame").attr('src',path + "/apartmentshowstophouse.do?" + $("#halt_search").serialize());	
				}
			}
			
			function resetsearch(){
				$(":text").val("");
			}
			
			function bb() {
				clearTimeout(timer);
				window.location.href="stophouse_info.jsp";
			}
			$(".header_roomlist_li").on("click",function(){
				$(this).addClass("active");
				$(this).siblings().removeClass("active");
			});
			
			$(".imooc").on("click", function() {
				$(".high_header").addClass("fadeOutUp").fadeOut(100);
			})
			$(".confirm").on("click", function() {
				$(".high_header").addClass("fadeOutUp").fadeOut(100);
			})
			
			/*增加停售房*/
			$(".createhouse").on("click",function(){
				window.parent.JDialog.open("增加停售房", path + "/page/apartment/apartmentmainmenu/apartmentstopselling/stophouse_add.jsp",832,462);
				//window.location.href="stophouse_add.jsp";
			});
		</script>
  </body>
</html>
