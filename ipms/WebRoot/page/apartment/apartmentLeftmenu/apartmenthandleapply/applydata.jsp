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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<style>
  
/*设置可预约数量按钮*/
 input.setbutton{
	font-size: 12px;
	font-family: "微软雅黑";
	height: 27px !important;
    line-height: 25px !important;
    text-align: center;
    width: 90px !important;
    margin-left: 4px;
    border: none;
    background: #FC8A15 !important;
    color: #fff;
    cursor: pointer;
}
input.applybutton {
    font-size: 12px;
    font-family: "微软雅黑";
    height: 26px !important;
    line-height: 25px !important;
    text-align: center;
    width: 82px !important;
    margin-left: -1px;
    border: 1px solid #f1f1f1;
    background: #898880 !important;
    color: #fff;
    cursor: pointer;
}
.dealandcancle_button{
    padding: 3px;
    font-size: 0.75rem;
    background: #FC8A15;
    color: #fff;
    height: 23px;
    width: 39px;
    border:none;
}
.pageRegion {
    height: 36px;
    }
   </style>
  </head>
  <body id="aaa">
  <form id="pagerForm" name="pagerForm" action="" target="_self">
  	<div class="one_sale" style="height: 550px;overflow-x: hidden;">
	    <div class=" handleapply_div">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
					    <!--<th class="header" style="width:14%;">申请编号</th>-->
<!--					    <th class="header" style="width:10%;">门店名称</th>-->
						<th class="header">申请日期</th>
						<th class="header">保洁日期</th>
						<th class="header">时段</th>
						<th class="header">保洁类型</th>
						<th class="header">保洁费</th>
						<th class="header">房间号</th>
						<th class="header">预约人</th>
						<th class="header" style="width:11%;">手机号</th>
						<th class="header" style="display: none">备注</th>
						<th class="header">状态</th>
						<th class="header">操作</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${applicationdata}" var="applicationdata">
						<tr ondblclick="showDetail(this)">
							<td id="cleanApplyId" style="display: none">${applicationdata["CLEANAPPLY_ID"]}</td>
							<td style="display: none">${applicationdata["BRANCHNAME"]}</td>
							<td>${applicationdata["APPLICATIONTIME"]}</td>
							<td>${applicationdata["CLEANTIME"]}</td>
							<td>${applicationdata["TIMEAREA"]}</td>
							<td>${applicationdata["CLEANSTYLE"]}</td>
							<td>${applicationdata["CLEANPRICE"]}</td>
							<td>${applicationdata["ROOM_ID"]}</td>
							<td>${applicationdata["RESERVED_PERSON"]}</td>
							<td>${applicationdata["MOBILE"]}</td>
							<td style="display: none">${applicationdata["REMARK"]}</td>
							<td>${applicationdata["STATUS"]}</td>
							<td id="memberid" style="display: none">${applicationdata["MEMBERID"]}</td>
							<c:if test="${applicationdata.lstatus == '1'}">
								<td><input name="deal" type="button" class="dealandcancle_button" value="处理" onclick="dealCleanApply(this,${applicationdata['CONTRACT_ID']},1)"/>
								<input name="deal" type="button" class="dealandcancle_button" value="撤销" onclick="dealCleanApply(this,${applicationdata['CONTRACT_ID']},2)"/></td>
							</c:if>
							<c:if test="${applicationdata.lstatus == '2'}">
								<td><input name="" type="button" class="applybutton" value=" 已处理"/></td>
							</c:if>
							<c:if test="${applicationdata.lstatus == '0'}">
								<td><input name="" type="button" class="applybutton" value=" 已撤销"/></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div>
			<input type="hidden" id="status" name="status" value=""/>
		</div>
	<input type="hidden" id="branchId" name="branchId" value="${branchId}"/>
	<div id="pager"></div>
	</div>
	</form>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script type="text/javascript">var base_path = "<%=request.getContextPath()%>";</script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/apartment/apartmentLeftmenu/apartmentclean/applydata.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		
		Pager.renderPager(<%=pagination%>);
		
	</script>
  </body>
</html>
