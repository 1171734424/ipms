<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>民宿远期房态</title>
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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css">
	
  </head>
  <body>
    <div class="box_popup_member box_popup_member_margin">
		<form action="" method="post">
			<div class="content_member">
				<div class="search_style fl">
					<ul class="search_member clearfix fl">
						<li><label class="label_name">开始日期:</label><!-- <input name="" type="text" class="search_text">-->
						<input id="mdate" type="text" name="mdate" class="date_txt" style="width:150px;height:30px;margin-left:10px;"/>
						<li><label class="label_name">结束日期:</label>
						<input id="edate" type="text" name="edate" class="date_txt" style="width:150px;height:30px;margin-left:20px;"/>
						<%--<li><label class="label_name">显示天数:</label>
						<select class="days search_text">
							<option value="7" selected>7</option>
							<option value="10">10</option>
							<option value="15">15</option>
						</select>
						</li>
						--%><li><button type="button" class="btn_style btn_search" onclick="queryforward();">查询</button></li>
					</ul>
				</div>
				<div class="content_member_show">
				<section class="box-content-section fl" style="width:100%;">
					<section class="box_content_widget fl">
						<div id="integral" class="content" style="height:1076px;padding:10px;">
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript">
			var page_li=$(".box_pagenation ul li");
			laydate ({
			   	elem: '#mdate'
			   	,format: 'YYYY/MM/DD'   	
			   	,min: laydate.now(+1)
			    ,choose: function() {
			    	if(checkTime()){	
					$("#forwardFrame").attr("src","houseForwardRoom.do?madate="+$("#mdate").val()+"&edate="+$("#edate").val());
			    	};
		        }  
			});

			laydate ({
			   	elem: '#edate'
			   	,format: 'YYYY/MM/DD'   	
			   	,min: laydate.now(+7)
			    ,choose: function() {
			    	if(checkTime()){	
					$("#forwardFrame").attr("src","houseForwardRoom.do?madate="+$("#mdate").val()+"&edate="+$("#edate").val());
			    	};
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
			    $("#edate").val(laydate.now(+7));
				$("#forwardFrame").attr("src","houseForwardRoom.do?madate="+ $("#mdate").val()+"&edate="+$("#edate").val());
			 });

			function queryforward() {
				if(!checkTime()){
					showMsg("请选择正确的日期!");
					return false;
				}
				$("#forwardFrame").attr("src","houseForwardRoom.do?madate="+$("#mdate").val()+"&edate="+$("#edate").val());
				
			}
			
			function checkTime(){
				var startTime = $("#mdate").val();
				var endTime = $("#edate").val();
				if(startTime == "" || endTime == ""){
					return false;
				}
				if(startTime != "" && endTime != ""){
					if(new Date(startTime).getTime() >= new Date(endTime).getTime()){
						showMsg("请选择正确的查询日期!");
						return false;
					}	
				}
				return true;
			};
			function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
		</script>
  </body>
</html>
