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
   	<style>
			.pageRegion{
				margin-top:-62px;
			}
		
		</style>
  <body>
  	<form>
	    <div class="only_orderpadding"  style="height:824px;">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">预订人</th>
						<th class="header">会员等级</th>
						<th class="header">手机号</th>
						<th class="header">抵店时间</th>
						<th class="header">订单数</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${orderinfo}" var="ordinfo" varStatus="status">
						<tr id="${ordinfo['orderUserId'] }" ondblclick="loginInorderinfo(this)">
							<td>${ordinfo["orderuser"] }</td>
							<td>${ordinfo["memberRank"] }</td>
							<td>${ordinfo["phone"] }</td>
							<td>${ordinfo["arrivaltime"] }</td>
							<td>${ordinfo["orderNum"] }</td>
							</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<input type="hidden" id="orderId" name="orderId" value="${ multiQueryOrder.orderId }">
		<input type="hidden" id="roomType" name="roomType" value="${ multiQueryOrder.roomType }">
		<input type="hidden" id="source" name="source" value="${ multiQueryOrder.source }">
		<input type="hidden" id="guarantee" name="guarantee" value="${ multiQueryOrder.guarantee }">
		<input type="hidden" id="orderUser" name="orderUser" value="${ multiQueryOrder.orderUser }">
		<input type="hidden" id="mphone" name="mphone" value="${ multiQueryOrder.mphone }">
		<input type="hidden" id="status" name="status" value="${ multiQueryOrder.status }">
		<input type="hidden" id="orderTime" name="orderTime" value="${ multiQueryOrder.orderTime }">
		<input type="hidden" id="ordtimes" name="ordtimes" value="${ multiQueryOrder.ordtimes }">
		<input type="hidden" id="arrivalTime" name="arrivalTime" value="${ multiQueryOrder.arrivalTime }">
		<input type="hidden" id="arrtimes" name="arrtimes" value="${ multiQueryOrder.arrtimes }">
		<input type="hidden" id="movement" name="movement" value="${movement}">
		<input type="hidden" id="memberId" name="memberId" value="${multiQueryOrder.memberId}">
		
		<input type="hidden" id="orderIdPrev" name="orderIdPrev" value="${ multiQueryOrderNew.orderId }">
		<input type="hidden" id="roomTypePrev" name="roomTypePrev" value="${ multiQueryOrderNew.roomType }">
		<input type="hidden" id="sourcePrev" name="sourcePrev" value="${ multiQueryOrderNew.source }">
		<input type="hidden" id="guaranteePrev" name="guaranteePrev" value="${ multiQueryOrderNew.guarantee }">
		<input type="hidden" id="orderUserPrev" name="orderUserPrev" value="${ multiQueryOrderNew.orderUser }">
		<input type="hidden" id="mphonePrev" name="mphonePrev" value="${ multiQueryOrderNew.mphone }">
		<input type="hidden" id="statusPrev" name="statusPrev" value="${ multiQueryOrderNew.status }">
		<input type="hidden" id="orderTimePrev" name="orderTimePrev" value="${ multiQueryOrderNew.orderTime }">
		<input type="hidden" id="ordtimesPrev" name="ordtimesPrev" value="${ multiQueryOrderNew.ordtimes }">
		<input type="hidden" id="arrivalTimePrev" name="arrivalTimePrev" value="${ multiQueryOrderNew.arrivalTime }">
		<input type="hidden" id="arrtimesPrev" name="arrtimesPrev" value="${ multiQueryOrderNew.arrtimes }">
		<input type="hidden" id="movementPrev" name="movementPrev" value="${movement}">
		<input type="hidden" id="turnToOldPagePrev" name="turnToOldPagePrev" value="turnToOldPage">
		<input type="hidden" id="selectTimePrev" name="selectTimePrev" value="${selectTime}">
		<input type="hidden" id="memberIdPrev" name="memberIdPrev" value="${multiQueryOrderNew.memberId}">
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
		var M = {

		}
		
		// 屏蔽浏览器自带的右键
		$(document).bind("contextmenu",
		    function(){
		        return false;
		    }
		);
		
		/*弹出层*/
		$(document).delegate(".cancel_btn",'click',function(){
			if(M.dialog9){
				return M.dialog9.show();
			}
			M.dialog9 = jqueryAlert({
				'style'   : 'pc',
				'title'   : '提示',
				'content' :  '确定要取消当前客人吗？',
				'modal'   : true,
				'contentTextAlign' : 'left',
				'animateType': 'scale',
				'bodyScroll' : 'true',
				'buttons' : {
					'取消' : function(){
						M.dialog9.close();
					},
					'确定' : function(){
						//删除当前客人数据
					}
				}
			})
		})
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
			var selectTime = $(e).find("td:nth(3)").text();	
			window.parent.$("#orderFrame").attr("src","orderInfoAll.do?movement=5&turnToOldPage=turnToOldPage&selectTime="+selectTime+"&memberId="+e.id+"&arrivalTime="+$("#arrivalTime").val()+"&arrtimes="+$("#arrtimes").val()+"&mphone="+$("#mphone").val()+"&orderUser="+$("#orderUser").val());
			window.parent.$(".highsearch").html("返回上页");
            var html = "";
            html += "<div class='fr'id='newButtonss'><span class='highsearchNB' onclick='INtabSearch()'>高级查询</span></div><div class='high_headerNB fadeInDown'><h3>高级查询</h3>";
            html += "<i id='sss' class='imooc imooc_orderNB imoocnew' style='color:#fff;' onclick='closesss()'>&#xe900;</i><form action='' method='post' id='myFormNew' name='myFormNew'>";
            html += "<div class='margintopNB'>";
			html += "<ul class='clearfix'><li><label class='label_name' >订单号</label><input name='orderId' type='text' id='orderId' class='text_search'></li>";
			html += "<li><label class='label_name'>房型</label><select name='roomType' class='text_search' id='roomType'></select></li>";
			html += "<li><label class='label_name'>来源</label><select name='source' id='source' class='text_search'></select></li>";
			html += "<li><label class='label_name'>担保类型</label><select name='guarantee' id='guarantee' class='text_search'><option value=''>--请选择担保类型--</option>";
			html += "<option value='1'>无担保</option><option value='2'>有担保</option></select></li>";
			html += "<li><input name='orderUser' id='orderUser' type='hidden' value='' class='text_search'></li>";
			html += "<li><input name='mphone' id='mphone' type='hidden' value='' class='text_search'></li>";
			html += "<li style= 'margin-left: -1.5%;'><label class='label_name'>状态</label><select name='status' id='status' class='text_search'><option value=''>--请选择状态类型--</option>";
			html += "<option value='1'>新订</option><option value='2'>未到</option></select></li></ul>";
			html += "<ul class='clearfix'><li><label class='label_name'>预定日期</label><input id='orderTime' name='orderTime' type='text' value='' class='date text_search'></li>";  
			html += "<li><label class='label_name'>至</label><input name='ordtimes' id='ordtimes' type='text' value='' class='date text_search'></li></ul>";
			html += "<li><input name='selectTimeTemp' id='selectTimeTemp' type='hidden' value='' class='text_search'></li>";  
			html += "<li><input name='memberId' id='memberId' type='hidden' value='' class='text_search'></li>"; 
			html += "<ul class='clearfix'><li><input type='button' value='确认' class='button_margin confirm' onclick='querydataInTab()'></li></ul>";
			html += "</div></form></div>";
			//window.parent.$("#ultab").after(html);
			/*window.parent.$(".highsearchNB").on('click',function(){
				window.parent.$(".high_headerNB").css("display","block");
				loadsearchroomtype();
				loadordersource();
				$("#orderTime").on("click",function(){
		        	laydate({
		        	   	elem: '#orderTime'
		        	});
				});
		    	$("#ordtimes").on("click",function(){
		    		laydate({
		        	   	elem: '#ordtimes'
		        	});
				});
				//将前面页面的值拿到上面input框里
				var selectTimeTemp = $(window.parent.frames["orderFrame"].document).find("#selectTime").val();
				var memberIdTemp = $(window.parent.frames["orderFrame"].document).find("#memberId").val();
				
				window.parent.$("#selectTimeTemp").val(selectTimeTemp);
				window.parent.$("#memberId").val(memberIdTemp);
				})*/

			}


		function loadsearchroomtype(){
			$.ajax({
		         url: path + "/hotelloadsearchroomtype.do",
				 type: "post",
				 data : {},
				 success: function(json) {
				 	var data = "<option value=''>房间类型</option>";
				 	$.each(json,function(index){
				 		data = data + "<option value='" +json[index]["ROOMTYPE"] + "'>" +json[index]["ROOMNAME"] + "</option>"
				 	});
				 	window.parent.$("#roomType").html(data);
					/**$(".mySelect").select({
						width: "175px"
					});*/
				 },
				 error: function(json) {}
			});
		}

		function loadordersource() {
			$.ajax({
		         url: path + "/queryordersource.do",
				 type: "post",
				 data : {},
				 success: function(json) {
				 	var data = "<option value=''>请选择类型</option>";
				 	$.each(json,function(index){
				 		data = data + "<option value='" +json[index]["content"] + "'>" +json[index]["paramName"] + "</option>"
				 	});
				 	window.parent.$("#source").html(data);
					/**$(".mySelect").select({
						width: "175px"
					});*/
				 },
				 error: function(json) {}
			});
		}

		
	</script>
  </body>
</
<html>