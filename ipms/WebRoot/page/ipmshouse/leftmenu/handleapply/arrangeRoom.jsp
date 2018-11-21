<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/script.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>选房号</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/customer.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/orderfont.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	
  </head>
  <body>
 <section class="change_room" style="height: 345px;">
<!--		<div class="high_header" id="roomtype_data">-->
<!--			<span class="span_title">房型：<span class="span_name">自主大床房</span></span>-->
			<!--<i class="imooc" style="color: #3B3E40;">&#xe902;</i>-->
			<div class="margintop">
				<ul id= "roomidList" class="clearfix">
					<li onclick="changeRoom(this)"><p>305</p></li>	
				</ul>
			</div>	
<!--		</div>-->
		
	</section>
	<script src="<%=request.getContextPath()%>/script/common/newtipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  	<script type="text/javascript">
  		var path = '<%=request.getContextPath()%>';
  		//var checkid = '<%=request.getParameter("checkid")%>';
  		//var roomid = '<%=request.getParameter("roomid")%>';
  		var branchId = '<%=request.getParameter("branchId")%>';
  		$(function(){
  			getallstaff();
  		});
  		
  		function getallroomid(){
			$.ajax({
		         url: path + "/selectallroomid.do",
				 type: "post",
				 data : {},
				 success: function(json) {
				 	
				 	loadroomtype(json);
				
				 },
				 error: function(json) {}
			});
  		}
  		
  		function loadroomtype(json){
  		var tabledata = "";
  		$.each(json,function(index){
  		
  		
  		
  		});
  		
  			if(json.length == 0){
			 		$("#roomidList").html("");
			 	}else{
			 		$("#roomidList").html(tabledata);			 	
			 	}
  		
  		}
  		
  		
  	
  		function getroomid(e){
  			var roomid = $($(e).html()).html();
  		
			window.parent.$("#roomId").val(roomid);
			
			window.parent.JDialog.close();
  		}
  	</script>
  </body>
</html>
