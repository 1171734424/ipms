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
		    height: 22px;
		    width: 35px;
	    }
	    .applybutton {
		    font-size: 12px;
		    font-family: "微软雅黑";
		    height: 30px !important;
		    line-height: 26px !important;
		    text-align: center;
		    width: 75px !important;
		    margin-left: 4px;
		    border: 1px solid #f1f1f1;
		    background: #898880 !important;
		    color: #fff;
		    cursor: pointer;
		}
	</style>
  </head>
  <body>
   <div class="condition_div">
  	   <form id="pagerForm" name="pagerForm" action="queryApartmentReserved.do" target="_self">
	    <div class="div_part_one">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">门店名称</th>
						<th class="header">房间类型</th>
						<th class="header">预约人</th>
						<th class="header">手机</th>
						<th class="header">看房时间</th>
						<th class="header">预约来源</th>
						<th class="header">操作</th>
						<th class="header">操作人</th>
						<th class="header">备注</th>
						<th class="header">状态</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list">
						<tr ondblclick="checkorderinfo('${list['RESERVED_ID']}')">
							<td>${list["BRANCH_NAME"]}</td>
							<td>${list["ROOM_NAME"]}</td>
							<td>${list["MEMBER_NAME"]}</td>
							<td>${list["MOBILE"]}</td>
							<td>${list["RESERVED_DATE"] }</td>
							<td>${list["SOURCE"] }</td>
							<c:if test="${list.dispose == null}">
								<td>
									<input type="button" id="${list['RESERVED_ID']}" class="dealandcancle_button" value="同意" onclick="checkReserved(this.id,'1')"/>
									<input type="button" id="${list['RESERVED_ID']}" class="dealandcancle_button" value="拒绝" onclick="checkReserved(this.id,'0')"/>
								</td>
							</c:if>
							<c:if test="${list.dispose != null}">
								<td>
									<input type="text" class="applybutton" value="${list['DISPOSE'] }" readonly/>
								</td>
							</c:if>
							<td>${list["STAFF_NAME"] }</td>
							<td>${list["REMARK"] }</td>
							<c:if test="${list.status == null}">
								<td>
									<c:if test="${list.dispose != null}">
										<c:if test="${list['DISPOSENAME'] == 1 }">
											<select id="${list['RESERVED_ID']}" class="text_search" onchange="change(this.id,this.value)">
										</c:if>
										<c:if test="${list['DISPOSENAME'] == 0 }">
											<select id="${list['RESERVED_ID']}" class="text_search" onchange="change(this.id,this.value)" disabled="disabled">
										</c:if>
									</c:if>
									<c:if test="${list.dispose == null}">
										<select id="${list['RESERVED_ID']}" class="text_search" onchange="change(this.id,this.value)" disabled="disabled">
									</c:if>
										<option value="">-选择-</option>
									    <option value="0">取消</option>
									    <option value="1">待定</option>
								        <option value="2">预定</option>
								        <option value="3">已租</option>
								    </select>
								</td>
							</c:if>
							<c:if test="${list.status != null}">
								<c:if test="${list.status == 1}">
									<td>
										<select id="${list['RESERVED_ID']}" class="text_search" onchange="change(this.id,this.value)">
										    <option value="1">待定</option>
										    <option value="0">取消</option>
									        <option value="2">预定</option>
									        <option value="3">已租</option>
									    </select>
								    </td>
								</c:if>
								<c:if test="${list.status != 1}">
									<td>
										<input type="text" class="applybutton" value="${list['STATUS_NAME'] }" readonly/>
									</td>
								</c:if>
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
			<input type="hidden" id="status" name="status" value="${status }"/>
			<input type="hidden" id="reservedTime" name="reservedTime" value="${reservedTime }"/>
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
		var path = "<%=request.getContextPath()%>";
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		function change(reservedId,status){
			if(status == ""){
				return false;
			}
			$.ajax({
				url: "checkApartmentReserved.do",
				dataType: "json",
				type: "post",
				data:{
					'reservedId' : reservedId,
					'status' : status
				},
				success: function(json) {
				        showMsg("处理成功!");
				        window.setTimeout("window.parent.location.reload(true)",800);
					   
				},
				error:function(json){
						showMsg("处理失败!");
					    window.setTimeout("window.parent.location.reload(true)",1000);
				}
			});
		};
		function checkReserved(reservedId,status){
			if(status == ""){
				return false;
			}
			$.ajax({
				url: "checkReservedApartment.do",
				dataType: "json",
				type: "post",
				data:{
					'reservedId' : reservedId,
					'status' : status
				},
				success: function(json) {
				        showMsg("处理成功!");
				        window.setTimeout("window.parent.location.reload(true)",800);
					   
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
