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
  <body class="gdcbody">
  	   <form id="pagerForm" name="pagerForm" action="" target="_self">
	    <div class="sale_div" style="height:547px;">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
					<th class="header gdm_hidden">选择</th>
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
					<c:forEach items="${gdsmanageCondition}" var="gdsmanageCondition">
						<tr>
						    <td class="gdm_hidden"><input name="gdscheckbox" type="checkbox"/></td>
						    <td>${gdsmanageCondition["GOODSID"]}</td>
							<td>${gdsmanageCondition["GOODSNAME"]}</td>
							<td>${gdsmanageCondition["CATEGORYNAME"]}</td>
							<td>${gdsmanageCondition["PRICE"]}</td>
							<td>${gdsmanageCondition["INTEGRAL"]}</td>
							<td>${gdsmanageCondition["SPECIFICATIONS"] }</td>
							<td>${gdsmanageCondition["UNIT"] }</td>
							<td>${gdsmanageCondition["REMARK"] }</td>
							<td name="gmstatus" style="display:none;">${gdsmanageCondition["STATUS"] }</td>
							<td id="d${gdsmanageCondition['GOODSID']}" name="gmsup"><input name="gdsdown" type="button" class="gdsbutton" onclick="goodsdown()" value="下架"/></td>
							<td id="u${gdsmanageCondition['GOODSID']}" name="gmsdown" class="gdm_hidden"><input name="gdsup" type="button" class="gdsbutton" onclick="goodsup()" value="上架"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
		<div>
			<input type="hidden" id="goodsid" name="goodsid" value="${goodsid }"/>
			<input type="hidden" id="goodsname" name="goodsname" value="${goodsname }"/>
			<input type="hidden" id="categoryid" name="categoryid" value="${categoryid }"/>
			<input type="hidden" id="status" name="status" value="${status }"/>
		</div>
	</form>
	<%@ include file="../../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
		$(document).ready(function(){
		var tableInfo = "";
	    var tableObj = document.getElementById("myTable");
	    for ( var i = 1; i < tableObj.rows.length; i++) {
	      var status = tableObj.rows[i].cells[9].innerText;
	      var did = "#"+tableObj.rows[i].cells[10].id;
	      var uid= "#"+tableObj.rows[i].cells[11].id;
	        if(status=="1"){
	        $(did).removeClass("gdm_hidden");
	        $(uid).addClass("gdm_hidden");
		   }else{
		      $(did).addClass("gdm_hidden");
	         $(uid).removeClass("gdm_hidden");
	     }
	     }
		
		})
		
	</script>
<!-- 	<script src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script> -->

	<script type="text/javascript">var base_path = "<%=request.getContextPath()%>";</script>
	<script src="<%=request.getContextPath()%>/script/ipmsinhouse/leftmenu/goodsmanage.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	
  </body>
</html>
