<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
  <head>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/showdialog/alert.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" >

  </head>
  <body>
  	<form>
	    <div class="only_orderpadding"  style="height:824px;">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
					
					   
						
						<th class="header">预订人</th>
						<th class="header">预订人手机</th>
						<th class="header">总价(元)</th>
						<th class="header">预定数量</th>
						
						
					    <!-- <th class="header">订单号</th>
						<th class="header">房间名称</th>
						<th class="header">总价(元)</th>
						<th class="header">预订人</th>
						<th class="header">预订人手机</th>
						<th class="header">抵店日期</th>
						<th class="header">离店日期</th>
						<th class="header">预定数量</th>
						<th class="header">状态</th>
						<th class="header">渠道</th>
						<th class="header">操作员</th>
						<th class="header">备注</th> -->
						
						<!-- 
						<th class="header">预订人</th>
						<th class="header">会员等级</th>
						<th class="header">手机号</th>
						<th class="header">抵店时间</th>
						<th class="header">订单数</th> -->
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${orderinfo}" var="ordinfo" varStatus="status">
						<tr id="${ordinfo['orderId']}"  ondblclick="loginInorderinfo(this)">
							
							<td>${ordinfo["orderuser"]}</td>
							<td>${ordinfo["m_phone"]}</td>
							<td>${ordinfo["ROOM_PRICE"]}</td>
							<td>${ordinfo["orderNum"]}</td>
							
						
						
							<%-- <td>${ordinfo["ORDER_ID"] }</td>
							<td>${ordinfo["HOUSENAME"] }</td>
							<td>${ordinfo["ROOM_PRICE"] }</td>
							<td>${ordinfo["orderuser"] }</td>
							<td>${ordinfo["phone"] }</td>
							<td>${ordinfo["ARRIVALTIME"] }</td>
							<td>${ordinfo["LEAVETIME"] }</td>
							<td>${ordinfo["orderNum"] }</td>
							<td>${ordinfo["STATUS"] }</td>
							<td>${ordinfo["SOURCES"] }</td>
							<c:if test='${ordinfo["SOURCEID"] == "1" || ordinfo["SOURCEID"] == "2" || ordinfo["SOURCEID"] == "4" || ordinfo["SOURCEID"] == "7"}'>
								<td>顾客</td>
							</c:if>
							<c:if test='${ordinfo["SOURCEID"] != "1" && ordinfo["SOURCEID"] != "2" && ordinfo["SOURCEID"] != "4" && ordinfo["SOURCEID"] != "7"}'>
								<td>${ordinfo["RECORD_USER"] }</td>
							</c:if>
							
							<td>${ordinfo["REMARK"] }</td> --%>
							
							<%-- <td>${ordinfo["orderuser"] }</td> --%><!--预订人  -->
							<%-- <td>${ordinfo["memberRank"] }</td> --%><!--会员等级  -->
							<%-- <td>${ordinfo["phone"] }</td> --%><!-- 手机号 -->
							<%-- <td>${ordinfo["arrivaltime"] }</td> --%><!-- 地点时间 -->
							<%-- <td>${ordinfo["orderNum"] }</td> --%><!--订单数  -->
							</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<input type="hidden" id="arrivaltime" name="arrivaltime" value="${arrivaltime}">
		<input type="hidden" id="arrivaltime2" name="arrivaltime2" value="${arrivaltime2}">
		<input type="hidden" id="memberName" name="memberName" value="${memberName}">
		<input type="hidden" id="mobile" name="mobile" value="${mobile}">
		<input type="hidden" id="querymemberName" name="querymemberName" value="${querymemberName}">
		<input type="hidden" id="querymobile" name="querymobile" value="${querymobile}">
		<input type="hidden" id="type" name="type" value="${type}">
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>	
	<script src="<%=request.getContextPath()%>/script/ipms/js/showdialog/alert.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
	<script>
		var path = "<%=request.getContextPath()%>";

		
		// 屏蔽浏览器自带的右键
		$(document).bind("contextmenu",
		    function(){
		        return false;
		    }
		);
		

		function checkorderinfo(e) {
			var orderid = $(e.children[0]).html();
			window.parent.parent.JDialog.openWithNoTitle("", path + "/page/ipmspage/order/order_details.jsp?orderid=" + orderid,1179,733);
			//window.location.href=path + "/page/ipmspage/room_statistics/roomlist_check.jsp?checkid=" + checkid;
		}
		$("table.myTable tbody tr").mouseover(function(){
			$(this).find(".cancel_span").css({'background-image':'url('+ path +'/css/ipms/img/backgroundimg/cancel2.png)'});
			$(this).find(".undone").css({'background-image':'url('+ path +'/css/ipms/img/backgroundimg/undone2.png)'});
			$(".cancel_span").css("background-repeat","no-repeat");
			$(".cancel_span").css("background-position","center");
			$(".undone").css("background-repeat","no-repeat");
			$(".undone").css("background-position","center");
		})
		$("table.myTable tbody tr").mouseout(function(){
			$(this).find(".cancel_span").css({'background-image':'url('+ path +'/css/ipms/img/backgroundimg/cancel.png)'});
			$(this).find(".undone").css({'background-image':'url('+ path +'/css/ipms/img/backgroundimg/undone.png)'});
		})
		$(".cancel_span").on("click",function(){
			$(this).css({'background-image':'url('+ path +'/css/ipms/img/backgroundimg/cancel3.png)'});
			
		})
		
		$(".undone").on("click",function(){
			$(this).css({'background-image':'url('+ path +'/css/ipms/img/backgroundimg/undone3.png)'});
		})
		
		function changestatus(e) {
			var orderid = $($(e).parent().find("td:first")).text();
			$.ajax({
			   url: path + "/changeorderstatus.do",
				 type: "post",
				 dataType: "json",
				 data : {orderId : orderid},
				 success: function(json) {
					 if (json) {
						showMsg(json.message);
						window.setTimeout("window.location.reload(true)",1800);
					}
				 },
				 error: function(json) {
					 showMsg("操作失败！");
				 }
			});
		}
		
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}

		
		//列双击事件
		function loginInorderinfo(e){
			window.parent.$("#logFrame").attr("src","HwaitAgreCountInfo.do?orderId="+e.id+"&arrivaltime="+$("#arrivaltime").val()+"&arrivaltime2="+$("#arrivaltime2").val()+"&type="+$("#type").val()+"&membername="+$("#memberName").val()+"&mobile="+$("#mobile").val());
			window.parent.$(".highsearch").html("返回上页");
		}
		
		
	/* 	//列双击事件
		function loginInorderinfo(e){
			var selectTime = $(e).find("td:nth(5)").text();	
			var querymembername = $(e).find("td:nth(3)").text();	
			var querymobile = $(e).find("td:nth(4)").text();	
			//window.parent.$("#orderFrame").attr("src","orderInfoAll.do?movement=5&turnToOldPage=turnToOldPage&selectTime="+selectTime+"&memberId="+e.id+"&arrivalTime="+$("#arrivalTime").val()+"&arrtimes="+$("#arrtimes").val()+"&mphone="+$("#mphone").val()+"&orderUser="+$("#orderUser").val());
			window.parent.$("#logFrame").attr("src","orderCountInfo.do?selectTime="+selectTime+"&querymemberName="+querymembername+"&querymobile="+querymobile+
					       "&arrivaltime="+$("#arrivaltime").val()+"&arrivaltime2="+$("#arrivaltime2").val()+"&type="+$("#type").val()+"&membername="+$("#memberName").val()+"&mobile="+$("#mobile").val());
			window.parent.$(".highsearch").html("返回上页");
			} */
	</script>
  </body>
</
<html>