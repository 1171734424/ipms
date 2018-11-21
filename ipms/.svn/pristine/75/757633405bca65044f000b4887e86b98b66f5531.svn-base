<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE html>
<html style="width: 100%; height: inherit;">
	<head>
		<title></title>
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/css/common/common.css"/>
	<style>
	</style>
	</head>
	<body oncontextmenu="return true;">
		<div class="table_cond iot-condition">
			<ul class="ul">	
				<li class="li">
					<div class="dialogInput" style="margin-top:10px;">
						<input id="cond_GROUPID" type="text" placeholder="规则名称"/>
					</div>
				</li>
				<li class="li">
					<div id="queryInEdit" class="btn btn-default" onclick="queryCompDialogData();">
						<span>查 询</span>
					</div>
				</li>
			</ul>
		</div>
		<div align="center" style="margin-top: 5px; ">
			<table id="customGrid"></table>
			<div id="pager"></div>
		</div>
		<input type="hidden" name="colName" id="colName" value='${colName}'/>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.fmatter.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	 	<script src="<%=request.getContextPath()%>/script/common/ui.datetimepicker.js"></script>
	 	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script>
			var base_path = "<%=request.getContextPath()%>";
			$(document).ready(function(){
			  	$(".wdate").datetimepicker({ datetimeFormat: "yy/mm/dd HH:mm:ss" });
				$("#ui-datepicker-div").css('font-size','0.5em');
				});
		</script>
		
		<script>
			jQuery(document).ready(function() {
				jQuery("#customGrid").jqGrid({
					url: "ruledataIframe.do?groupId="+$("#cond_GROUPID").val(),
					datatype: "json",
				   	colNames: setGridCols(),
				   	colModel: setGridModels(),
				   	width: 490,
				   	height: 125,
				   	rowNum: 10,
				   	rowList: [10,20,50],
				   	pager: "#pager",
				   	forceFit: true,
				   	cellEdit: true,
				   	cellsubmit: "clientArray",
				   	sortname: "id",
				    viewrecords: true,
				    sortorder: "desc",
				    altRows: true,
					altclass: "ui-state-focus",
					ondblClickRow: function (rowid, iRow, iCol, e) {
		   				var rowData = $("#customGrid").jqGrid("getRowData", rowid);
		   				window.parent.$("#COMPANY_ID_ADD").val(rowData["COMPANY_NAME"]);
		   				window.parent.$("#COMPANY_ID_ADD_CUSTOM_VALUE").val(rowData["COMPANY_ID"]);
		   			  
		   				window.parent.JDialog.close();
		   				
					}
					
			});
			});
			
			function setGridCols() {
				return ['编号','名称','地址','联系方式'];
			}
			
			function setGridModels() {
				return [
				{name: 'COMPANY_ID', index: 'COMPANY_ID', width:40, align:"center",hidden:true},
				{name: 'COMPANY_NAME', index: 'COMPANY_NAME', width:40, align:"center" },
				{name: 'ADDRESS', index: 'ADDRESS', width:40, align:"center" },
				{name: 'PHONE', index: 'PHONE', width:40, align:"center",hidden:true }
				];
			}
			
			function closed(){
			window.parent.JDialog.close();
			}
			
			function queryCompDialogData() {
				var query_url = "ruledataIframe.do?groupId="+$("#cond_GROUPID").val();
				$("#customGrid").jqGrid("setGridParam", { url: query_url }).trigger("reloadGrid");
			}
			
			
		</script>
	</body>
</html>
