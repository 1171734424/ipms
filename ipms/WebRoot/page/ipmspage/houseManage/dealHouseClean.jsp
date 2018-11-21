<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>民宿保洁处理</title>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
    <script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body >
  
	<div class="box_popup_member leftorder_margin">
		<span class="close_span"><i class="imooc imooc_order" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i></span>
		<h3>民宿保洁处理</h3>
		<form id="pagerForm" name="pagerForm" action="dealHouseCleanInfo.do" method="post" target="reservedFrame">
			<div class="content_member">
				<div class="search_style fl">
					<ul class="search_member clearfix fl">
						<li><label class="label_name">民宿名称</label><input id="houseId" name="houseId" type="text" class="text_search"></li>
						<li><label class="label_name">保洁时间</label><input type="text" id="cleanTime" name="cleanTime" class="date_text text_search" value="" onblur="" /></li>
						<li><label class="label_name">申请时间</label><input  id="applicationTime" name="applicationTime" type="text" class="text_search"></li>
						<li><label class="label_name">处理情况</label><select id="dealType" name="dealType" type="text" class="date_text text_search">
																	<option  value="">请选择状态</option>
																	<option value = "0">已撤销</option>
																	<option value = "1">未处理</option>
																	<option value = "2">已处理</option>
																	</select></li>
						<li><button type="button" class="btn_style  button_margin" onclick="pagerFormSubmit()">查询</button></li>
					</ul>
				</div>
				<div class="content_member_show" style="width: 100%; height:97%;">
					<iframe name="reservedFrame" id="reservedFrame" frameborder="0" width="100%" height="100%" src="dealHouseCleanInfo.do"></iframe>
				</div>
			</div>
		</form>
	</div>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		//Pager.renderPager(<%=pagination%>);
	</script>
	<script type="text/javascript">
		laydate({
	    	elem: '#cleanTime'
		});
		laydate({
			elem: '#applicationTime'
		});
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		
		function pagerFormSubmit(){
			$("#pagerForm").submit();
		}
		
		</script>
  </body>
</html>
