<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>预定入住</title>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/repair.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css">
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/><!--
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
    --><script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	<style>
		ul.hi li{
			position: relative;
		    float: left;
		    width: 10.66%;
		    height:51px;
		    line-height:51px;
		    text-align: center;
		    background: #535474;
		    color: #fff;
	        margin-bottom: -1px;
	        border-right:1px solid #f0f0f0;
		}
		ul.hi li:last-child{
			border-right:none;
		}
		ul.hi li.active{
			background: #fff;
			color:#333;
		}
		.col_margin .walk_margin{
			padding:0;
			
		}
	</style>
  </head>
  <body >
		<div class="order_margin col_margin" style="height:780px;">
			<span class="close_span"><i class="imooc imooc_order" style="color: #3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i></span>
			<div class="div_tab">
				<ul class="hi clearfix">
					<li class="first_li active" onclick="orderNew(this)">
						<label class="order" >
							预定
						</label>
					</li>
					<li id = "tolive" class="secod_li" onclick="checkIn(this)">
						<label class="checkin" >
							入住
						</label>
					</li>
				</ul>
			</div>
			<div class="content_member_show" style="width: 100%; height: 92%;">
				<iframe id="orderIframe" name="orderIframe" class="" frameborder="0" width="100%" height="100%" src="orderNew.do"></iframe>
			</div>
		</div>
		<%@ include file="../../../common/script.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		/*laydate({
	    	elem: '#enddate'
		});
		laydate({
	    	elem: '#startdate'
		});*/
		var RoomType = '${roomType}';
		var RoomId = '${roomId}';
		$(function(){
			$("ul.hi li").on("click",function(){
				$(this).addClass("active");
				$(this).siblings().removeClass("active");
			})
			if(RoomType != null && RoomType != ''){
				checkIn();
			
			}
			
		});
	  	function orderNew(e){
	  		$("#orderIframe").attr("src","orderNew.do");
	  		
	  	}
	  	
	  	function checkIn(e){
	  		if(RoomType != null){
	  			$("#orderIframe").attr("src","checkInDirect.do?roomId=" + RoomId + "&roomType=" + RoomType);
	  			$(".secod_li").addClass("active");
				$(".secod_li").siblings().removeClass("active");
	  		}else{
	  			$("#orderIframe").attr("src","checkInDirect.do");
	  		}
	  	}
	  	$(".imooc_order").on("click",function(){
			$(".detail_five").css("display","none");
		})
		
	</script>
  </body>
</html>
