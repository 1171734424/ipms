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
	<table id="checkDetailShow"></table>
   	<div id="pager"></div>
   	<div class="dialogButton" style="margin:8px auto" onclick="closed()">关 闭</div>
	<script>
		var base_Path = "${basePath}";
		var roomtype = "${roomtype}";
		var startdate = "${startdate}";
		var enddate = "${enddate}";
		console.log(roomtype)
		console.log(startdate)
		jQuery(document).ready(function() {
			jQuery("#checkDetailShow").jqGrid({
				url: "roomsummarycheck.do?roomtype="+roomtype+"&startdate="+startdate+"&enddate="+enddate,
				datatype: "json",
			   	colNames: setGridCols(),
			   	colModel: setGridModels(),   
			   	width: 1000,
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
		return ['房单号','房间类型','入住天数'];
	}
	
	function setGridModels() {
		return [{name: 'CHECKID', index: 'CHECKID', width:50, align:"center" },
		    {name: 'ROOMTYPE', index: 'ROOMTYPE', width:50, align:"center" },
		    {name: 'A', index:'A' ,width:50, align:"center" }]
		    
	}
	
	function closed(){
		window.parent.JDialog.close();
	}
	</script>
</body>
</html>
