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
    <link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<style>
	.dealandcancle_button{
    padding: 3px;
    font-size: 0.75rem;
    background: #FC8A15;
    color: #fff;
    height: 23px;
    width: 46px;
    border:none;
}
	
	/* .rich{
	width:85px;

	} */
	.rich2{
	width:160px;

	}
	.rich3{
	width:60px;

	}
	.other{
	overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
	}
    </style>
  </head>
  <body>
  	<form id="pagerForm" name="pagerForm" action="${action }" target="_self" method="post">
		<div >
			<%-- <input id="memberName" name="memberName" type="hidden" value="${memberName}">
			<input id="mobile" name="mobile" type="hidden" value="${mobile}">
			<input id="arrivaltime" name="arrivaltime" type="hidden" value="${arrivaltime}"> --%>
		</div>
	    <div>
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header other rich2">订单号</th>
						<th class="header other">民宿名称</th>
						<th class="header other">总价(元)</th>
						<th class="header rich other">预订人</th>
						<th class="header other">预订人手机</th>
						<th class="header other">抵店日期</th>
						<th class="header other">离店日期</th>
						<th class="header rich3">预定数量</th>
						<th class="header rich other">状态</th>
						<th class="header rich other">渠道</th>
						<th class="header other">接待备注</th>
						<th class="header other">客房备注</th>
						<th class="header rich other">操作员</th>
						<c:if test="${judge == 'waitpage'}">
						<th class="header rich other">操作</th>
						</c:if>
						<c:if test="${judge != 'waitpage'}">
						<th class="header other">备注</th>
						</c:if>
						
						
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="ordinfo" varStatus="status">
						<tr ondblclick="houseOrderInfo(this)">
							<td class="other rich2">${ordinfo["ORDER_ID"]}</td>
							<td class="other">${ordinfo["HOUSENAME"]}</td>
							<td class="other">${ordinfo["ROOM_PRICE"]}</td>
							<td class="rich other">${ordinfo["MEMBER_NAME"]}</td>
							<td class="other">${ordinfo["M_PHONE"]}</td>
							<td class="other">${ordinfo["ARRIVALTIME"]}</td>
							<td class="other">${ordinfo["LEAVETIME"]}</td>
							<td class="rich3">1</td>
							<td class="rich other">${ordinfo["STATUS"]}</td>
							<td class="rich other">${ordinfo["SOURCE"]}</td>
							<td class="other">${ordinfo["RECEPTION"]}</td>
							<td class="other">${ordinfo["ROOMREMARK"] }</td>
							<c:if test='${ordinfo["SOURCEID"] == "1" || ordinfo["SOURCEID"] == "4" || ordinfo["SOURCEID"] == "7"}'>
								<td class="rich other">顾客</td>
							</c:if>
							<c:if test='${ordinfo["SOURCEID"] != "1" &&  ordinfo["SOURCEID"] != "4" && ordinfo["SOURCEID"] != "7"}'>
								<td class="rich other">${ordinfo["RECORD_USER"]}</td>
							</c:if>
							<c:if test="${judge == 'waitpage'}">
							<td class="rich other">
							<input name="deal" type="button" class="dealandcancle_button" value="同意" onclick="dealCleanApply(this,1)"/>
							<input name="deal" type="button" class="dealandcancle_button" value="不同意" onclick="dealCleanApply(this,2)"/></td>
							</c:if>
							<c:if test="${judge != 'waitpage'}">
							<td class="other">${ordinfo["REMARK"]}</td>
							</c:if>
							
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
				<input type="hidden" id="type" name="type" value="${type}">
				<input type="hidden" id="orderId" name="orderId" value="${orderId}">
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
		var path = "<%=request.getContextPath()%>";
		function houseOrderInfo(e) {
			var houseorderid = $(e.children[0]).html();
			/* var reception = $(e.children[10]).html();
			var roomRemark = $(e.children[11]).html(); */
			/* window.parent.parent.JDialog.openWithNoTitle("", path + "/page/ipmshouse/houseorder/houseorder_details.jsp?houseorderid=" + houseorderid + "&reception=" + reception + "&roomRemark=" + roomRemark,1179,580); */
			window.parent.parent.JDialog.openWithNoTitle("", path + "/page/ipmshouse/houseorder/houseorder_details.jsp?houseorderid=" + houseorderid ,1179,580);
		}
		
		function dealCleanApply(btn,status){
			var waitorderid = $(btn).parent().parent().children("td").eq(0).html();
			//if(status == 2){
				//window.parent.JDialog.open("取消订单", path + "/page/ipmshouse/houseorder/secondquery.jsp?orderId=" + waitorderid + "&status=" + status, 350, 200);
			//} else {
				$.ajax({
					url : path + "/agreeOrnot.do",
					dataType : "json",
					type : "post",
					data : {
						'waitorderid' : waitorderid,
						'status':status
					},
					success : function(json) {
					

						var order_id_str = $("#orderId").val();
						 order_id_str = order_id_str.replace(waitorderid,"");
						var fdStart = order_id_str.indexOf(",");
						var lastdouhao = order_id_str.lastIndexOf(",");
						var length_str = order_id_str.length;
						
						if(fdStart == 0){
							order_id_str =order_id_str.substring(1,length_str );
							
						}
						if(lastdouhao == length_str){
							order_id_str =order_id_str.substring(0,length_str -1);
						}
						
						order_id_str = order_id_str.replace(",,",",");
					
					window.parent.$("#logFrame").attr("src","HwaitAgreCountInfo.do?orderId="+order_id_str+"&arrivaltime="+$("#arrivaltime").val()+"&arrivaltime2="+$("#arrivaltime2").val()+"&type="+$("#type").val()+"&membername="+$("#memberName").val()+"&mobile="+$("#mobile").val());


					},
					error : function(json) {
						showMsg("操作失败!");
						window.setTimeout("window.location.reload(true);",1800);
					}
				});
			//}
		}
		
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
