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
  	<title>退换押金(民宿)</title>
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
		.text_search {
		    padding: 8px 5px;
		    width: 45px;
		    margin-left: 5px;
		}
		.content_member .search_member .label_name {
		    float: left;
		    width: 64px;
		    text-align: right;
		    font-weight: normal;
		}
		#fund_div {
			height: 120px;
    		width: 100%;
		}
		#new_button{
			position: absolute;
		    left: 90%;
		    top: 31px;
		}
		#new_label_name{
			width: 50px;
		}
		.text_search {
	   	 	width: 156px !important; 
    	}
	</style>
  </head>
  <body >
	<div class="box_popup_member leftorder_margin">
		<span class="close_span"><i class="imooc imooc_order" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i></span>
		<h3>退换押金(民宿)</h3>
		<form id="pagerForm" name="pagerForm" action="houseRefundAll.do" method="post" target="reservedFrame">
			<div class="content_member">
				<div class="search_style fl" id="fund_div">
					<ul class="search_member clearfix fl">
						<li><label class="label_name">预订人</label><input id="orderUser" name="orderUser" type="text" class="text_search"></li>
						<li><label class="label_name" id="new_label_name">会员号</label><input id="memberId" name="memberId" type="text" class="text_search"></li>
						<li><label class="label_name" id="new_label_name">入住人</label><input id="memberName" name="memberName" type="text" class="text_search"></li>
						<li><label class="label_name">预定时间</label><input id="arrivalTime" name="arrivalTime" type="text" class="date_text text_search"></li>
						<li><label class="label_name">离店时间</label><input id="leaveTime" name="leaveTime" type="text" class="date_text text_search"></li>
					<!-- <li><label class="label_name">渠道</label><input id="source" name="source" type="text" class="text_search"></li> -->
						<li><label class="label_name">渠道</label>
							<select id="source" name="source" type="text" class="text_search">
								<option value="">请选择</option>
								<option value="1">APP</option>
								<option value="2">网站</option>
								<option value="3">分店</option>
								<option value="4">WAP</option>
								<option value="5">合作渠道</option>
								<option value="6">其他</option>
								<option value="7">微信</option>
								<option value="8">直接入住</option>
							</select>
						</li>
						<li><label class="label_name" id="new_label_name">房型</label><input id="roomType" name="roomType" type="text" class="text_search"></li>
						<li><label class="label_name" id="new_label_name">手机号</label><input  id="mobile" name="mobile" type="text" class="text_search"></li>
						<li><label class="label_name">退款时间</label><input id="reservedTime" name="reservedTime" type="text" class="date_text text_search"></li>
						<li><label class="label_name">记录人</label><input id="recordUser" name="recordUser" type="text" class="text_search"></li>
						<li><button type="button" class="btn_style  button_margin" id="new_button" onclick="pagerFormSubmit()">查询</button></li>
					</ul>
				</div>
				<div class="content_member_show" style="width: 100%; height:90%;">
					<iframe name="reservedFrame" id="reservedFrame" frameborder="0" width="100%" height="100%" src="houseRefundAll.do"></iframe>
				</div>
			</div>
		</form>
	</div>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
	</script>
	<script type="text/javascript">
		laydate({
	    	elem: '#reservedTime'
		});
		laydate({
	    	elem: '#arrivalTime'
		});
		laydate({
	    	elem: '#leaveTime'
		});
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		
		function pagerFormSubmit(){
			var mobile = $("#mobile").val();
			if("" != mobile){
				if(!(/^1[34578]\d{9}$/.test(mobile))){
					showMsg("请填写正确的手机号!");
					return false;
				}
			}
			
			$("#pagerForm").submit();
		}
		
		</script>
  </body>
</html>
