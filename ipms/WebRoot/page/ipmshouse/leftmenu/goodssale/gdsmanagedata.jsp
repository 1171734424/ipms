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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
  </head>
  <body id="aaa" class="gdcbody">
  	    <form id="pagerForm" name="pagerForm" action="" target="_self">
	    <div class="sale_div" style="height:547px;">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
					<th class="header gdm_hidden"><input name="chooseAll" id="chooseAll" type="checkbox"/>选择</th>
					    <th class="header">商品编号</th>
						<th class="header">商品名称</th>
						<th class="header">商品分类</th>
						<th class="header">商品售价</th>
						<th class="header">兑换积分</th>
						<th class="header">商品规格</th>
						<th class="header">商品单位</th>
						<th class="header">备注</th>
						<th class="header">操作</th>
					</tr>
				</thead>
				<tbody id="info" class="gdsmanagebutton">
					<c:forEach items="${goodsSale}" var="goodsSale">
						<tr>
						   <td class="gdm_hidden"><input name="gdscheckbox" type="checkbox" class="a" /></td>
							<td>${goodsSale["GOODSID"]}</td>
							<td>${goodsSale["GOODSNAME"]}</td>
							<td>${goodsSale["CATEGORYNAME"]}</td>
							<td>${goodsSale["PRICE"]}</td>
							<td>${goodsSale["INTEGRAL"]}</td>
							<td>${goodsSale["SPECIFICATIONS"] }</td>
							<td>${goodsSale["UNIT"] }</td>
							<td>${goodsSale["REMARK"] }</td>
							<td><input name="gdsdown" type="button" class="gdsbutton" value="下架" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="manysalecontent"></div>
		<div id="pager"></div>
		<div>
			<input type="hidden" id="source" name="source" value=""/>
			<input type="hidden" id="status" name="status" value=""/>
		</div>
	</form>
	<%@ include file="../../../common/script.jsp"%>
	<script type="text/javascript">var base_path = "<%=request.getContextPath()%>";</script>
<!-- 	<script src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script> -->
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipmsinhouse/leftmenu/goodsmanage.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
	</script>
  </body>
</html>
