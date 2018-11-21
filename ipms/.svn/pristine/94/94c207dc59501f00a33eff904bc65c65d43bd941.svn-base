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
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" media="all" />
	<style type="text/css">
		.dealandcancle_button {
		    padding: 3px;
		    font-size: 0.75rem;
		    background: #FC8A15;
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
		.div_part_one table.myTable td{
			vertical-align: middle;
		    padding: 10px;
		    font-size: 0.7rem;
		    white-space: nowrap;
		    overflow: hidden;
		    text-align: center;
		    text-overflow: ellipsis;
		}
	</style>
  </head>
  <body>
   <div class="condition_div">
  	   <form id="pagerForm" name="pagerForm" action="queryApartmentCheckOut.do" target="_self" method="post">
	    <div class="div_part_one">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">合同编号</th>
						<th class="header">会员名称</th>
						<th class="header">门店名称</th>
						<th class="header">房间号</th>
						<th class="header">申请时间</th>
						<th class="header">退房来源</th>
						<th class="header">操作人</th>
						<th class="header">审核</th>
						<th class="header">状态</th>
						<th class="header">操作</th>
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list">
						<tr>
							<td>${list["CONTRACT_ID"]}</td>
							<td>${list["MEMBER_NAME"]}</td>
							<td>${list["BRANCH_NAME"]}</td>
							<td>${list["ROOM_ID"]}</td>
							<td>${list["APPLICATION_TIME"] }</td>
							<td>${list["SOURCE"] }</td>
							<td>${list["STAFF_NAME"] }</td>
							<td>${list["STATUS_NAME"] }</td>
							<td>${list["DISPOSE_NAME"] }</td>
							<c:if test="${list.dispose == '0'}">
								<td>
									<input class="dealandcancle_button" type="button" id="${list['CONTRACT_ID']},${list['CHECKOUT_ID']}" value="处理" onclick="check_submit(this.id)"/>
								</td>
							</c:if>
							<c:if test="${list.dispose == '1'}">
								<td>
									<input class="applybutton" type="button" id="${list['CONTRACT_ID']},${list['CHECKOUT_ID']}" value="已处理"/>
								</td>
							</c:if>
							<td>${list["REMARK"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<div>
			<input type="hidden" id="orderuser" name="orderuser" value="${orderuser }"/>
			<input type="hidden" id="ordertime" name="ordertime" value="${ordertime }"/>
			<input type="hidden" id="roomId" name="roomId" value="${roomId }"/>
			<input type="hidden" id="dispose" name="dispose" value="${dispose }"/>
		</div>
	</form>
  </div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
	<script type="text/javascript">
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		function check_submit(Id){
			$.ajax({
				url: "checkApartmentCheckOut.do",
				dataType: "json",
				type: "post",
				data:{
					'Id' : Id
				},
				success: function(json) {
				        showMsg("处理成功!");
				        window.setTimeout("window.parent.location.reload(true)",1000);
					   
				},
				error:function(json){
						showMsg("处理失败!");
					    window.setTimeout("window.parent.location.reload(true)",1000);
				}
			});
		};
	</script>
  </body>
</html>
