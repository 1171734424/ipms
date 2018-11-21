<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
%>

<!DOCTYPE HTML>
<html>
	<head>
  		<base href="<%=basePath%>">
   		<title>房态统计</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/jquery-easyui/themes/icon.css" />
		<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/room_statistics.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/><%--
		<link rel="stylesheet" id="style" type="text/css" 
			href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
	--%></head>
	<body>
		<div class="room_margin">
	 	<div class="header_search">
	 		<form action="" method="">
	  		<%--<span>当前房态</span>
	  		--%><label>营业日期</label>
	  		<input id="mdate" type="text" name="mdate" class="date_txt" />
	 			<span class="forward">远期房态</span>
	 		</form>
	 	</div>
		<section class="box-content-section fl" style="width:100%;margin-top: -9px;">
			<section class="box_content_widget fl">
				<div id="integral" class="content" style="height:600px;padding:10px;">
					<iframe name="currentFrame" id="currentFrame" frameborder="0" width="100%" height="100%" ></iframe>
				</div>
			</section>
		</section>
		
		<section class="box-content-section fl" style="width:100%;margin-top: 46px;">
			<section class="box_content_widget fl">
				<div id="integral" class="content" style="height:600px;padding:10px;">
					<iframe name="activityFrame" id="activityFrame" frameborder="0" width="100%" height="100%" ></iframe>
				</div>
			</section>
		</section>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script><%--
		<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>--%>
		<script type="text/javascript">
			var base_path = "<%=request.getContextPath()%>";
			Pager.renderPager(<%=pagination%>);
			$(".forward").on("click",function() {
				JDialog.openWithNoTitle("",base_path+"/page/ipmshotel/room_statistics/room_forward.jsp",1179,738);
			});
			$(document).ready(function() {
				laydate({
				   	elem: '#mdate'
				   	,format: 'YYYY/MM/DD'
				   	,min: laydate.now()
				    ,choose: function(){
						$("#currentFrame").attr("src","currentRoom.do?madate="+$("#mdate").val());
			        }  
				});
				var now = new Date();    
			    var year = now.getFullYear();       //年   
			    var month = now.getMonth() + 1;     //月   
			    var day = now.getDate();
			    var time = year+"/"+month+"/"+day;
			    $("#mdate").val(laydate.now(0, 'YYYY/MM/DD'));
				$("#currentFrame").attr("src","currentRoom.do?madate="+time);
				$("#activityFrame").attr("src","queryMyCampaign.do");
			})
		</script>
	</body>
</html>
