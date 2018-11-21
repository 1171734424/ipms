<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
  <head>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/apartmentreserved.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<style type="text/css">
		.dealandcancle_button {
		    padding: 3px;
		    font-size: 0.75rem;
		    background: #FC8A15;
		    color: #fff;
		    height: 30px;
		    width: 55px;
	    }
	    .cancle_button {
		    padding: 3px;
		    font-size: 0.75rem;
		    background: darkgrey;
		    color: #fff;
		    height: 30px;
		    width: 55px;
	    }
	    .applybutton {
		    font-size: 12px;
		    font-family: "微软雅黑";
		    height: 30px !important;
		    line-height: 26px !important;
		    text-align: center;
		    width: 55px !important;
		    margin-left: 4px;
		    border: 1px solid #f1f1f1;
		    background: #898880 !important;
		    color: #fff;
		    cursor: pointer;
		}
		.div_part_one {
		    height: 545px !important;	
		}
	</style>
  </head>
  <body>
   <div class="condition_div">
  	   <form id="pagerForm" name="pagerForm" action="houseRefundAll.do" target="_self">
	    <div class="div_part_one">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">民宿编号</th>
						<th class="header">民宿名称</th>
						<th class="header">房型</th>
						<th class="header">房价(元)</th>
						<th class="header">入住人</th>
						<th class="header">手机号</th>
						<th class="header">离店日期</th>
						<th class="header">状态</th>
						<th class="header">自动退租</th>
						<th class="header">操作</th>
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="ordinfo" varStatus="status">
						<tr>
							<td>${ordinfo["BRANCH_ID"] }</td>
							<td>${ordinfo["ROOM_NAME"] }</td>
							<td>${ordinfo["ROOM_TYPE"] }</td>
							<td>${ordinfo["ROOM_PRICE"] }</td>
							<td>${ordinfo["MEMBER_NAME"] }</td>
							<td>${ordinfo["MOBILE"] }</td>
							<td>${ordinfo["CHECKOUT_TIME"] }</td>
							<td>${ordinfo["STATUSNAME"] }</td>
							<c:if test="${ordinfo['AUTOREFUND'] == '0'}">
							<td><input id="autorefund${status.index }" name="deal" type="button" value="是" onclick="changeAuto('${ordinfo['ORDER_ID'] }',1)"/></td>
							<td><input class="cancle_button" type="button" id="${ordinfo['ORDER_ID']}" value="退款" onclick=""/></td>
							</c:if>
							<c:if test="${ordinfo['AUTOREFUND'] == '1'}">
							<td><input id="autorefund${status.index }" name="deal" type="button" value="否" onclick="changeAuto('${ordinfo['ORDER_ID'] }',0)"/></td>
							<td><input class="dealandcancle_button" type="button" id="${ordinfo['ORDER_ID']}" value="退款" onclick="refundMoney(this.id)"/></td>
							</c:if>
							<c:if test="${fn:length(ordinfo['REMARK'])>10}">	
							<td title='${ordinfo["REMARK"]}'>${fn:substring(ordinfo["REMARK"],0,10)}...</td>
							</c:if>
							<c:if test="${fn:length(ordinfo['REMARK'])<=10}">	
							<td title='${ordinfo["REMARK"]}'>${ordinfo["REMARK"] }</td>
							</c:if>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<div>
			<input type="hidden" id="memberName" name="memberName" value="${memberName }"/>
			<input type="hidden" id="mobile" name="mobile" value="${mobile }"/>
			<input type="hidden" id="reservedTime" name="reservedTime" value="${reservedTime }"/>
		</div>
	</form>
  </div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
	<script type="text/javascript">
		var start = '14:45';
		var end = '15:15';
		var now = new Date();    
	    var year = now.getFullYear();       //年   
	    var month = now.getMonth() + 1;     //月   
	    var day = now.getDate();
	    var starttime = year+"-"+month+"-"+day+" "+start;
	    var endtime = year+"-"+month+"-"+day+" "+end;
	    var starttimes = Date.parse(new Date(starttime.replace(/-/g, "/")));
	    var endtimes = Date.parse(new Date(endtime.replace(/-/g, "/")));
		$(function(){
			$("#info").find("tr").each(function(i){
				if(starttimes < new Date().getTime() && new Date().getTime() < endtimes){
					$("#autorefund"+i).attr("onclick","");
					$("#autorefund"+i).addClass("cancle_button");
				}else{
					$("#autorefund"+i).addClass("dealandcancle_button");
				}
			});
		});
	</script>
	<script type="text/javascript">
		var path = "<%=request.getContextPath()%>";
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}

		function refundMoney(id){
			JDialog.open("民宿退款",path +"/RefundMoney.do?checkid="+id,1000,550);
		}

		function changeAuto(orderId,status){
			$.ajax({
				url : path + "/changeOrderAutorefund.do",
				dataType : "json",
				type : "post",
				data : {
					'orderId' : orderId,
					'status' :status
					
				},
				success :function (json){
					window.parent.showMsg(json.message);
					window.setTimeout("window.parent.location.reload(true)", 1800);
				},
				error : function (json){
					
				}
			});
		}
	</script>
  </body>
</html>
