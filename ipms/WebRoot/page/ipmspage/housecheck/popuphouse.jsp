<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/newscript.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />	
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
			<span class="span_title">民宿<span class="span_name"></span></span>
				<div class="margintop">
					<ul class="clearfix">
					<c:forEach items="${listhouses}" var="house">
					<li onclick='popupdisclaimer(this)'><p id="houseid">${ house.HOUSE_ID }</p><p>${ house.HOUSENAME }</p></li>
					</c:forEach>
					</ul>
				</div>	
		</div>
	</section>
	<script src="<%=request.getContextPath()%>/script/common/newtipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  	<script type="text/javascript">
  		var path = '<%=request.getContextPath()%>';
  		var houseorderid = '${houseorderid}';
  		//var roomid = '<%=request.getParameter("roomid")%>';
  		$(function(){
  		});
  		
  		function popupdisclaimer(e){
  			var chosehouseid = $($(e).html()).html();
  			window.JDialog.open("民宿换房", path + "/page/ipmspage/housecheck/changhouse_disclaimer.jsp?orderId=" + houseorderid + "&chosehouseid=" + chosehouseid, 400,250);
  		}
  	</script>
  </body>
</html>
