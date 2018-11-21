<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("basePath", request.getContextPath());
	request.setAttribute("checkId", request.getAttribute("checkId"));
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
		
		var checkId = "${checkId}";
		jQuery(document).ready(function() {
	
			jQuery("#querysourceinfo").jqGrid({
				url: "queryHosueAbnormalInfo.do?checkId="+checkId,
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
	
		return ['民宿名称','项目名称','结算','消费','记录时间'];
		
	}
	
	function setGridModels() {
	
		return [{name: 'HOUSENAME', index: 'HOUSENAME', width:150, align:"center" },
		        {name: 'PROJECTNAME', index:'PROJECTNAME' ,width:120, align:"center" },
		        {name: 'PAY', index:'PAY' ,width:80, align:"center" },
		        {name: 'COST', index:'COST' ,width:150, align:"center" },
		        {name: 'RECRODTIME', index:'RECRODTIME' ,width:150, align:"center" }]
		  
	}
	
	function closed(){
	
		window.parent.JDialog.close();
	}
	</script>
</body>
</html>