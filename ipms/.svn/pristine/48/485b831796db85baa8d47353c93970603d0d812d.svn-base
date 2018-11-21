<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%> 
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>会员积分金额修改</title>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
    <style type="text/css">
    	.content_member .search_member .label_name {
		    width: 70px;
    	}   
    	
    	.search_style {
    	 	padding-left: 15px;
    	}
    	.text_search {
			width: 143px;
		}
		.label_name1 {
			
		}
    </style>
    <script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body >
	<div class="box_popup_member leftorder_margin">
		<span class="close_span"><i class="imooc imooc_order" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i></span>
		<h3>会员管理</h3>
		<form id="pagerForm" name="pagerForm" action="memberManage.do" method="post" target="reservedFrame">
			<div class="content_member">
				<div class="search_style fl">
					<ul class="search_member clearfix fl">
						<li><label class="label_name">会员账号</label><input id="memberId" name="memberId" type="text" class="search_text text_search"></li>
						<li><label class="label_name1 label_name">手机号</label><input id="mobile" name="mobile" type="text" class="search_text text_search"></li>
						<li><label class="label_name">会员名称</label><input id="memberName" name="memberName" type="text" class="search_text text_search"></li>
						<li><label class="label_name">公司编号</label><input id="companyName" name="companyName" type="text" class="search_text text_search"></li>
						<li><label class="label_name">会员等级</label>
						<select id="memberRank" name="memberRank" type="text" class="search_text text_search">
							<option value="">请选择会员</option>
							<option value="0">公司会员</option>
							<option value="1">非会员</option>
							<option value="2">注册会员</option>
							<option value="3">银卡会员</option>
							<option value="4">金卡会员</option>
							<option value="5">铂金会员</option>
							<option value="6">黑钻会员</option>
						</select>
						</li>
						<li><button type="button" class="btn_style  button_margin" onclick="submit()">查询</button></li>
					</ul>
				</div>
				 <div class="content_member_show" style="width: 100%; height:97%;">
					<iframe name="reservedFrame" id="reservedFrame" frameborder="0" width="100%" height="100%" src="memberManage.do"></iframe>
				</div> 
			</div>
		</form>
	</div>
<!-- 	<script src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script> -->
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
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
		function addCheckOut(){
			JDialog.open("新增退房申请",base_path +"/addCheckOut.do",650,350);
		}

	</script>
  </body>
</html>
