<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
	request.setAttribute("gdscontent", request.getAttribute("gdscontent"));
%>
<!DOCTYPE HTML>
<html>
  <head>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
  </head>
  <body class="gdcbody">
       <!-- 第一版批量提交 按钮-->
       <!--<input type="button" name="" id="" class="gdsbutton manysale" value="批量售卖" onclick="gdsmanyearch()"/> -->
  	  <form id="pagerForm" name="pagerForm" action="" target="_self">
	    <div class="gdcsale_div">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
					    <th class="header">选择</th>
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
				<tbody id="info">
					<c:forEach items="${gdsaleCondition}" var="gdsaleCondition">
						<tr onclick="gdscheck(this)">
						    <td><input name="gdscheckbox" type="checkbox"  id="${gdsaleCondition['GOODSID']}" /></td>
							<td>${gdsaleCondition["GOODSID"]}</td>
							<td>${gdsaleCondition["GOODSNAME"]}</td>
							<td>${gdsaleCondition["CATEGORYNAME"]}</td>
							<td>${gdsaleCondition["PRICE"]}</td>
							<td>${gdsaleCondition["INTEGRAL"]}</td>
							<td>${gdsaleCondition["SPECIFICATIONS"] }</td>
							<td>${gdsaleCondition["UNIT"] }</td>
							<td class="cols_remark">${gdsaleCondition["REMARK"] }</td>
							<td id="gggr"><input name="gdsale" type="button" class="gdsbuttonse" value="单品售卖" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		 </div>	
		<div id="pager"></div>
		<div>
			<input type="hidden" id="source" name="source" value=""/>
			<input type="hidden" id="status" name="status" value="${status }"/>
			<input type="hidden" id="goodsid" name="goodsid" value="${goodsid }"/>
			<input type="hidden" id="goodsname" name="goodsname" value="${goodsname }"/>
			<input type="hidden" id="categoryid" name="categoryid" value="${categoryid }"/>
			 <!-- 批量售卖隐藏域-->
			<input type="hidden" id="gdscontent" name="gdscontent" value="${gdscontent}"/>
		</div>
	</form>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script type="text/javascript">var base_path = "<%=request.getContextPath()%>";</script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipmsinhouse/leftmenu/goodssale.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
		/*已勾选的商品再次查询默认选中CM*/
		$(document).ready(function() {
		  var gdscontent =  $("#gdscontent").val();
		  var gdsidarray = document.getElementsByName("gdscheckbox");
			for(var i=0; i<gdsidarray.length; i++ ){
			 var id = gdsidarray[i].id;
			 if(gdscontent.indexOf(id)>-1){
			    var chk = document.getElementById(id);
			    chk.checked = true;
			 }
            }
		})
	</script>
  </body>
</html>
