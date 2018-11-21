<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("basePath", request.getContextPath());
	request.setAttribute("roomtype", request.getAttribute("roomtype"));
	request.setAttribute("startdate", request.getAttribute("startdate"));
	request.setAttribute("enddate", request.getAttribute("enddate"));
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单详情</title>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
<script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
<body>
	<table id="orderRoomDetailShow"></table>
   	<div id="pager"></div>
   	<div class="dialogButton" style="margin:8px auto" onclick="closed()">关 闭</div>
	<script>
		var base_Path = "${basePath}";
		var roomtype = "${roomtype}";
		var startdate = "${startdate}";
		var enddate = "${enddate}";
		jQuery(document).ready(function() {
			jQuery("#orderRoomDetailShow").jqGrid({
				url: "roomorder.do?roomtype="+roomtype+"&startdate="+startdate+"&enddate="+enddate,
				datatype: "json",
			   	colNames: setGridCols(),
			   	colModel: setGridModels(),   
			   	width: 800,
			   	height: 270,
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
		return ['订单号','房间号','房间状态','下单日期','更新人'];
	}
	
	function setGridModels() {
		return [{name: 'ORDERID', index: 'ORDERID', width:60, align:"center" },
		        {name: 'ROOMID', index: 'ROOMID', width:60, align:"center" },
		        {name: 'STATUS', index:'STATUS' ,width:60, align:"center" },
		        {name: 'ORDERTIME', index:'ORDERTIME' ,width:60, align:"center" },
		        {name: 'RECORDUSER', index:'RECORDUSER' ,width:60, align:"center" }]
		    
	}
	
	function closed(){
		window.parent.JDialog.close();
	}
	</script>
</body>
</html>
