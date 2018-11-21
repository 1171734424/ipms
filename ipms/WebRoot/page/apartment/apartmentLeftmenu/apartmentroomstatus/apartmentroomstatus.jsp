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
  	<title>公寓远期房态</title>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
    <script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	<style>
		#jd_iframe{
			border-radius:4px;
		}
		.button_margin_no{
		    height: 34px;
		    line-height: 30px;
		    width: 85px;
		    border: none;
		    font-size: 14px;
		    margin-right: 8px;
		    margin-left: 7px;
		    margin-top: 6px;
		    float: right;
		    text-align: center;
		    cursor: pointer;
		    border-radius: 4px;
		    background: #9e9e9e;
		    color: #fff;
		}
	</style>
  </head>
  <body >
	<div class="box_popup_member box_popup_member_margin leftorder_margin">
		<span class="close_span"><i class="imooc imooc_order" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i></span>
		<h3>公寓远期房态</h3>
		<form id="pagerForm" name="pagerForm" action="queryApartmentRoomStatus.do" method="post" target="reservedFrame">
			<div class="content_member">
				<div class="search_style fl">
					<ul class="search_member clearfix fl">
						<li><label class="label_name">年份:</label><input id="year" name="year" type="text" class="search_text" readonly="true"></li>
						<li><button id="submitPrevious" type="button" class="btn_style btn_search" onclick="Previous()">上一年</button></li>
						<li><button id="submitNext" type="button" class="btn_style btn_search" onclick="Next()">下一年</button></li>
					</ul>
				</div>
				<div class="content_member_show" style="width: 100%; height:97%;">
					<iframe name="reservedFrame" id="reservedFrame" frameborder="0" width="100%" height="100%"></iframe>
				</div>
			</div>
			<input type="hidden" id="pageNum" name="pageNum" value="1">
		</form>
	</div>
	<%@ include file="../../../common/script.jsp"%>
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
		var date=new Date;
	 	var year=date.getFullYear();
	 	var previousYear = year - 1 ;
	 	var nextYear = year + 3 ;
	 	$(function(){
			$("#year").val(year);
			$("#submitPrevious").addClass("button_margin");
			$("#submitNext").addClass("button_margin");
			$("#pagerForm").submit();
	 	})
	 	function Previous(){
		 	//if($("#year").val() > previousYear ){
		 		$("#year").val(parseInt($("#year").val()) - 1);
		 		/*	if($("#year").val() == previousYear){
					$("#submitPrevious").addClass("button_margin_no");
			 		$("#submitPrevious").attr("onclick","");
				}
				if($("#year").val() < nextYear){
					$("#submitNext").removeClass("button_margin_no");
			 		$("#submitNext").attr("onclick","Next()");
				}*/
			//}
			$("#pagerForm").submit();
	 	}
	 	function Next(){
	 		//if($("#year").val() < nextYear ){
		 		$("#year").val(parseInt($("#year").val()) + 1);
		 	/*	if($("#year").val() == nextYear){
		 			$("#submitNext").addClass("button_margin_no");
			 		$("#submitNext").attr("onclick","");
				}
				if($("#year").val() > previousYear){
					$("#submitPrevious").removeClass("button_margin_no");
			 		$("#submitPrevious").attr("onclick","Previous()");
				}*/
			//}
	 		$("#pagerForm").submit();
	 	}
	</script>
  </body>
</html>
