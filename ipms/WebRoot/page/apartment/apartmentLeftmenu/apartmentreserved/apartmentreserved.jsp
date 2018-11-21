<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../../common/script.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>公寓预约</title>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
    <script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	<style type="text/css">
		.text_search{
		    width: 129px;
		    margin-left: 10px;
		    padding: 8px 5px;
		}
	</style>
  </head>
  <body >
	<div class="leftorder_margin" style="background: #EFF3F5;box-shadow: 1px 1px 8px rgba(0, 0, 0, .3);border-radius: 4px;overflow: hidden;z-index: 9999;">
		<!-- <span class="close_span"><i class="imooc imooc_order" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i></span> -->
		<h3>公寓预约</h3>
		<form id="pagerForm" name="pagerForm" action="queryApartmentReserved.do" method="post" target="reservedFrame">
			<div class="content_member">
				<div class="search_style fl">
					<ul class="search_member clearfix fl">
						<li><label class="label_name">预约人:</label><input id="memberName" name="memberName" type="text" class="text_search"></li>
						<li><label class="label_name">手机号:</label><input id="mobile" name="mobile" type="text" class="text_search"></li>
						<li>
							<label class="label_name">状态:</label>
							<select id="status" name="status" class="text_search">
								<option value="">选择</option>
							    <option value="0">取消</option>
							    <option value="1">待定</option>
						        <option value="2">预定</option>
						        <option value="3">已租</option>
						    </select>
						</li>
						<li><label class="label_name">预约时间:</label><input id="reservedTime" name="reservedTime" type="text" class="date_text text_search"></li>
						<li><button type="button" class="btn_style  button_margin" onclick="pagerFormSubmit()">查询</button></li>
						<li><button type="button" class="btn_style  button_margin button_add" onclick="addReserved()">新增</button></li>
					</ul>
				</div>
				<div class="content_member_show" style="width: 100%; height:97%;">
					<iframe name="reservedFrame" id="reservedFrame" frameborder="0" width="100%" height="100%" src="queryApartmentReserved.do"></iframe>
				</div>
			</div>
		</form>
	</div>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
<!-- 	<script src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script> -->
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/apartment/js/laydate.dev.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
	<script type="text/javascript">
		laydate({
	    	elem: '#reservedTime'
	    	
		});
		function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
		
		function osearchdata(){
			var memberName = $("#memberName").val();
			var mobile = $("#mobile").val();
			var reservedTime = $("#reservedTime").val();
		 	$("#reservedFrame").attr("src","queryApartmentReserved.do?memberName="+memberName+"&mobile="+mobile+"&reservedTime="+reservedTime);
		}
		
		function addReserved(){
			JDialog.open("新增公寓预约",base_path +"/addApartmentReserved.do",650,330);
		}

		function pagerFormSubmit(){
			var mobile = $("#mobile").val();
			/* if("" != mobile){
				if(!(/^1[34578]\d{9}$/.test(mobile))){
					showMsg("请填写正确的手机号!");
					return false;
				}
			} */
			$("#pagerForm").submit();
		}
		</script>
  </body>
</html>
