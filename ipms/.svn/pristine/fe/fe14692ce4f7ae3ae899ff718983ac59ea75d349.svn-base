<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/script.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>退房申请</title>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
    <script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body >
	<div class="box_popup_member leftorder_margin">
		<span class="close_span"><i class="imooc imooc_order" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i></span>
		<h3>退房申请</h3>
		<form id="pagerForm" name="pagerForm" action="queryApartmentCheckOut.do" method="post" target="reservedFrame">
			<div class="content_member">
				<div class="search_style fl">
					<ul class="search_member clearfix fl">
						<li><label class="label_name">申请人:</label><input id="orderuser" name="orderuser" type="text" class="search_text text_search"></li>
						<li><label class="label_name">房间号:</label><input id="roomId" name="roomId" type="text" class="search_text text_search"></li>
						<li>
				      		<label class="label_name">处理结果</label>
				      		<select name="dispose" id="dispose" class="text_search">
				      			<option value="">--请选择--</option>
							    <option value="0">未处理</option>
							    <option value="1">已处理</option>
						    </select>
				      	</li>
						<li><label class="label_name">申请时间:</label><input id="ordertime" name="ordertime" type="text" class="date_text text_search"></li>
						<li><button type="button" class="btn_style  button_margin" onclick="submit()">查询</button></li>
<!--						<li><button type="button" class="btn_style  button_margin button_add" onclick="addCheckOut()">新增</button></li>-->
					</ul>
				</div>
				<div class="content_member_show" style="width: 100%; height:97%;">
					<iframe name="reservedFrame" id="reservedFrame" frameborder="0" width="100%" height="100%" src="queryApartmentCheckOut.do"></iframe>
				</div>
			</div>
		</form>
	</div>
	<%@ include file="../../../common/script.jsp"%>
<!-- 	<script src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script> -->
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/apartment/js/laydate.dev.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
	<script type="text/javascript">
		laydate({
	    	elem: '#ordertime'
	    	
		});
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		function addCheckOut(){
			JDialog.open("新增退房申请",base_path +"/addApartCheckOut.do",650,350);
		}
	</script>
  </body>
</html>
