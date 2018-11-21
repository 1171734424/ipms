<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("basePath", request.getContextPath());
	request.setAttribute("ORDERDATE", request.getAttribute("time"));
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>查询详细</title>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<link rel="stylesheet" type="text/css" href="${basePath}/css/common/ui.jqgrid.css"/>
<script src="${basePath}/script/common/jquery.jqGrid.src.js"></script>
<script src="${basePath}/script/common/grid.locale-cn.js"></script>
<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
<body>
	<table id="querysourceinfo"></table>
   	<div id="pager"></div>
   	<div class="dialogButton" style="margin:8px auto" onclick="closed()">关 闭</div>
	<script>
		
		var orderDate = "${ORDERDATE}";
		jQuery(document).ready(function() {
	
			jQuery("#querysourceinfo").jqGrid({
				url: "sourceinfo.do?orderDate="+orderDate,
				datatype: "json",
			   	colNames: setGridCols(),
			   	colModel: setGridModels(),
			   	width: 980,
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
	
		return ['订单日期','订单号','预订人','订单来源','抵店时间','离店时间','状态'];
		
	}
	
	function setGridModels() {
	
		return [{name: 'ORDERTIME', index: 'ORDERTIME', width:120, align:"center" },
		        {name: 'ORDERID', index: 'ORDERID', width:150, align:"center" },
		        {name: 'ORDERUSER', index:'ORDERUSER' ,width:120, align:"center" },
		        {name: 'SOURCE', index:'SOURCE' ,width:80, align:"center" },
		        {name: 'ARRIVALTIME', index:'ARRIVALTIME' ,width:150, align:"center" },
		        {name: 'LEAVETIME', index:'LEAVETIME' ,width:150, align:"center" },
		        {name: 'STATUS', index:'STATUS' ,width:80, align:"center" }]
		  
	}
	
	function closed(){
	
		window.parent.JDialog.close();
	}
	</script>
</body>
</html>