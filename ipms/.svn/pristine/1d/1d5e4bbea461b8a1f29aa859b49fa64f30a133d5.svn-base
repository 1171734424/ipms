<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
%>
<!DOCTYPE HTML>
<html>
  <head>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css"/>
	
  </head>
  <body>
  	<form>
	    <div style="height: 543px;padding-right: 5px;">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">生效日期</th>
						<th class="header">房号</th>
						<th class="header">房价码</th>
						<th class="header">原房价（元）</th>
						<th class="header">浮动（元）</th>
						<th class="header">现房价（元）</th>
						<th class="header">产生时间</th>
						<th class="header">操作者</th>
				    <!--<th class="header">状态</th>-->
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${list}" var="list">
						<tr>
							<td><fmt:formatDate value='${list["VALIDDAY"] }'  type="both" pattern="yyyy/MM/dd HH:mm:ss" /></td>
							<td>${list["ROOMID"] }</td>
							<td>${list["RPID"] }</td>
							<td>${list["ROOMPRICE"] }</td>
							<td>${list["CASHBACK"] }</td>
							<td>
							<script>
								document.write(${list["ROOMPRICE"] } + ${list["CASHBACK"] });
							</script>
							</td>
							<td>${list["RECORDTIME"] }</td>
							<td>${list["STAFFNAME"] }</td>
							<td>${list["REMARK"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager" style="margin-top:16px"></div>
		<input type="hidden" id="roomid" name="roomid" value = '<%=request.getParameter("roomid")%>'>
	</form>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script>
		var path = "<%=request.getContextPath()%>";
		Pager.renderPager(<%=pagination%>);
		function bb(e) {
			var checkid = $(e.children[0]).html();
			window.parent.parent.JDialog.openWithNoTitle("", path + "/page/ipmspage/room_statistics/roomlist_check.jsp?checkid=" + checkid,1179,733);
			//window.location.href=path + "/page/ipmspage/room_statistics/roomlist_check.jsp?checkid=" + checkid;
		}
		
	</script>
  </body>
</html>
