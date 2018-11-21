<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("basePath", request.getContextPath());
	request.setAttribute("memberID", request.getAttribute("memberID"));
	request.setAttribute("mobile", request.getAttribute("mobile"));
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>消费详情</title>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<link rel="stylesheet" type="text/css" href="${basePath}/css/common/ui.jqgrid.css"/>
<script src="${basePath}/script/common/jquery.jqGrid.src.js"></script>
<script src="${basePath}/script/common/grid.locale-cn.js"></script>
<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
<body>
	<table id="queryconsumeinfo"></table>
   	<div id="pager"></div>
   	<div class="dialogButton" style="margin:8px auto" onclick="closed()">关 闭</div>
	<script>
		var memberID = "${memberID}";
		
		
		jQuery(document).ready(function() {

			jQuery("#queryconsumeinfo").jqGrid({
				url: "consumeinfo.do?memberID="+memberID,
				datatype: "json",
			   	colNames: setGridCols(),
			   	colModel: setGridModels(),
			   	width: 1500,
			   	height: 295,
			   	rowNum: 20,
			   	rowList: [20,50,100],
			   	pager: "#pager",
			   	forceFit: true,
			   	cellsubmit: "clientArray",
			   	sortname: "id",
			    viewrecords: true,
			    sortorder: "desc",
			    altRows: true,
				altclass: "ui-state-focus"
			});
		});
		
	function setGridCols() {
		
		return ['房单号','实际入住人','入住日期','预离日期','房价','押金','账面金额','平账金额','项目名称','消费'
		        ,'支付方式','房单状态','账单状态'];
		
	}
	
	function setGridModels() {
		
		return [
		        {name: 'CHECK_ID', index: 'CHECK_ID', width:140, align:"center" },
		        {name: 'CHECK_USER', index: 'CHECK_USER', width:130, align:"center" },
		        {name: 'CHECKIN_TIME', index: 'CHECKIN_TIME', width:120, align:"center" },
		        {name: 'CHECKOUT_TIME', index:'CHECKOUT_TIME' ,width:120, align:"center" },
		        {name: 'ROOM_PRICE', index:'ROOM_PRICE' ,width:80, align:"center" },
		        {name: 'DEPOSIT', index:'DEPOSIT' ,width:80, align:"center" },
		        {name: 'ACCOUNT_FEE', index:'ACCOUNT_FEE' ,width:80, align:"center" },
		        {name: 'TOTAL_FEE', index:'TOTAL_FEE' ,width:80, align:"center" },
		        {name: 'PROJECT_NAME', index:'PROJECT_NAME' ,width:180, align:"center" },
		        {name: 'COST', index:'COST' ,width:80, align:"center" },
		        {name: 'PAYMENT', index:'PAYMENT' ,width:80, align:"center" },
		        {name: 'ROOM_STATUS', index:'ROOM_STATUS' ,width:80, align:"center" },
		        {name: 'ACCOUNT_STATUS', index:'ACCOUNT_STATUS' ,width:80, align:"center" }]
		  
	}
	
	function closed(){

		window.parent.JDialog.close();
	}
	</script>
</body>
</html>