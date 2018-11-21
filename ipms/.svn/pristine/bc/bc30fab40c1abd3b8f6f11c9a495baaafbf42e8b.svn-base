<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/newscript.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>换房</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/customer.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/orderfont.css"/>
  </head>
  <style>
	.no_data{
	    width: 100%;
	    height: 100%;
	    background-color: antiquewhite;
	    margin: 0 auto;
	    position: relative;
   }
   .no_data #no_data{
	    text-align: center;
	    position: absolute;
	    top: 47%;
	    width: 100%;
	    font-size: 30px;
	    color: #9f9d9d;
	    }
  </style>
  <body>
   <section class="change_room">
		<div class="high_header" id="roomtype_data">
			<span class="span_title">房型：<span class="span_name">自主大床房</span></span>
			<!--<i class="imooc" style="color: #3B3E40;">&#xe902;</i>-->
			<div class="margintop">
				<ul class="clearfix">
				</ul>
			</div>	
		</div>
	</section>
	<script src="<%=request.getContextPath()%>/script/common/newtipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  	<script type="text/javascript">
  		var path = '<%=request.getContextPath()%>';
  		var checkid = '<%=request.getParameter("checkid")%>';
  		//var roomid = '<%=request.getParameter("roomid")%>';
  		$(function(){
  			getallroomtype();
  		});
  		function getallroomtype(){
  			var theme = "1";//场景	1-酒店,2-公寓,3-民宿
  			var status = "1";//房态	0-已删除,1-空房,2-预定,3-已入住,T-停售,Z-脏房,W-维修,O-其他,F-无效,E-员工宿舍,M-样板房
			$.ajax({
		         url: path + "/showallroomtype.do",
				 type: "post",
				 data : {
				 	theme : theme,
				 	status : status
				 	},
				 success: function(json) {
				 	loadroomtype(json);
				 },
				 error: function(json) {}
			});
  		}
  		
  		function loadroomtype(json){
				 var tabledata = "";
			 	$.each(json, function(indextype){
			 		tabledata = tabledata + "<span class='span_title'>房型：<span class='span_name'>" + indextype + 
			 								"</span></span><div class='margintop'><ul class='clearfix'>";
			 		$.each(json[indextype], function(index){
			 			tabledata = tabledata + "<li onclick='changeRoom(this)'><p>" + json[indextype][index] + "</p></li>";
			 		});
			 		tabledata = tabledata + "</ul></div>";
			 	});
			 	if(!json){
			 		$("#roomtype_data").html("<div class='no_data'><p id='no_data'>暂无房间可选！</p></div>");
			 	}else{
			 		$("#roomtype_data").html(tabledata);
			 	}
  		
  		}
  		
  		function changeRoom(e){
  			var roomid = $($(e).html()).html();
			$.ajax({
		         url: path + "/changeRoom.do",
				 type: "post",
				 data : {
				 	roomid : roomid,
				 	checkid : checkid
				 	},
				 success: function(json) {
				 	$(window.parent.document).find(".tab_one").click();
				 	//$(window.parent.parent.document.all[155].contentDocument.logFrame.document.forms[0]).submit();
				 	if(window.parent.pagea == "-1"){
		  				$($(window.parent.parent.document).find("iframe")[1].contentDocument.logFrame.document.forms[0]).submit();
		  				//window.parent.JDialog.close();
				 	}else{
				 		$(window.parent.parent.parent.document).find("#contentFrame_first").click();
				 	}
				 	window.parent.loadCheckData();
				 	window.parent.JDialog.close();
				 },
				 error: function(json) {}
			});
  		}
  	</script>
  </body>
</html>
