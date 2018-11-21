<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../common/script.jsp"%>
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
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<style type="text/css">
	.pageRegion {
    height: 36px;
    line-height: 36px;
    width: 100%;
    margin-top: 11px;
}
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
		.div_part_one1{
		height: 601px;
		padding:0 10px;
		}
	</style>
  </head>
  <body>
   <div class="condition_div">
  	   <form id="pagerForm" name="pagerForm" action="dealHouseCleanInfo.do" target="_self">
	    <div class="div_part_one1">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">民宿名称</th>
						<th class="header">保洁时间</th>
						<th class="header">房间号</th>
						<th class="header">预订人</th>
						<th class="header">手机号</th>
						<th class="header">申请时间</th>
						<th class="header">状态</th>
						<th class="header">操作</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${houseCleanApplyList}" var="houseCleanApplyList">
						<tr>
							<td>${houseCleanApplyList["HOUSENAME"] }</td>
							<td>${houseCleanApplyList["CLEANTIME"] }</td>
							<td>${houseCleanApplyList["ROOMID"] }</td>
							<td>${houseCleanApplyList["RESERVEPERSON"] }</td>
							<td>${houseCleanApplyList["MOBILE"] }</td>
							<td>${houseCleanApplyList["APPLICATIONTIME"] }</td>
							<td>${houseCleanApplyList["STATUS"] }</td>
							<c:if test="${houseCleanApplyList['STATUSCODE'] == '1'}">
								<td><input name="deal" type="button" class="dealandcancle_button" value="处理" onclick="dealCleanApply('${houseCleanApplyList['CLEANAPPLYID']}',1)"/>
								<input name="deal" type="button" class="dealandcancle_button" value="撤销" onclick="dealCleanApply('${houseCleanApplyList['CLEANAPPLYID']}',2)"/></td>
							</c:if>
							<c:if test="${houseCleanApplyList['STATUSCODE'] == '2' or houseCleanApplyList['STATUSCODE'] =='0'}">
								<td><input name="" type="button" class="applybutton" value=" 已处理"/></td>
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
	
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
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
			if(starttimes < new Date().getTime() && new Date().getTime() < endtimes){
				$("#autorefund").attr("onclick","");
				$("#autorefund").addClass("cancle_button");
			}else{
				$("#autorefund").addClass("dealandcancle_button");
			}
		})
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
		
		// 处理保洁申请
		function dealCleanApply(cleanApplyId,status) {
				$.ajax({
					url : path + "/dealHouseCleanApply.do",
					dataType : "json",
					type : "post",
					data : {
						cleanApplyId : cleanApplyId,
						status : status
					},
					success :function (json){
						if(json == 1){
							showMsg("处理成功！");
						}else if(json == -1){
							showMsg("撤销成功！");
						}
							window.setTimeout("window.parent.location.reload(true)", 1000);
							window.setTimeout("window.parent.JDialog.close();",1000);
					},
					error : function (json){
						
					}
				});	
		}
	</script>
  </body>
</html>
