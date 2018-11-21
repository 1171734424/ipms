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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<style type="text/css">
table td{
height :40px;
}
.div_part_one_1 {
	height: 601px;
    
}
	</style>
  </head>
  <body>
   <div class="condition_div">
  	   <form id="pagerForm" name="pagerForm" action="" target="_self">
	    <div class="div_part_one_1">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">管家编号</th>
						<th class="header">管家姓名</th>
						<th class="header">登录名</th>
						<th class="header">身份证</th>
						<th class="header">性别</th>
						<th class="header">生日</th>
						<th class="header">手机号</th>
						<th class="header">邮箱</th>
<!--						<th class="header">家庭住址</th>-->
						<th class="header">入职时间</th>
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${managerList}" var="managerList">
						<tr ondblclick="showManagerDetail(this)">
							<td>${managerList["STAFF_ID"] }</td>
							<td>${managerList["STAFF_NAME"] }</td>
							<td>${managerList["LOGIN_NAME"] }</td>
							<td>${managerList["IDCARD"] }</td>
							<td>${managerList["TGENDOR"] }</td>
							<td>${managerList["TBIRTHDAY"] }</td>
							<td>${managerList["MOBILE"] }</td>
							<td>${managerList["TEMAIL"] }</td>
<!--							<td>${managerList["TADDRESS"] }</td>-->
							<td>${managerList["TENTRYTIME"] }</td>
							<td>${managerList["TREMARK"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<div>
			<input type="hidden" id="memberName" name="memberName" value="${memberName }"/>
			<input type="hidden" id="mobile" name="mobile" value="${mobile }"/>
		</div>
	</form>
  </div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
	var base_path = "<%=request.getContextPath()%>";
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

		function showManagerDetail(e){
		    var staffid =$(e).children("td").eq(0).html();
			//window.parent.JDialog.open("",base_path+"/editHouseManager.do?staffid="+staffid,1000,250);
            window.parent.JDialog.open("管家信息管理", base_path + "/showApartmentManager.do?staffid="+staffid,570,450);
		}


	</script>
  </body>
</html>
