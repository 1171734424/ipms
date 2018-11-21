<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html style="width: 100%; height: inherit;">
	<head>
		<title></title>
		<%@ include file="../../common/css.jsp"%>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
	</head>
	<body oncontextmenu="return true;">
		<div class="table_cond" style="height:50px;line-height: 42px;">
			<ul class="ul">
				<li class="li">
					<div class="dialogInput">
						<input id="cond_CUSTOMVALUE" type="text" placeholder="编号/名称"/>
					</div>
				</li>
				<li class="li">
					<div id="queryInEdit" class="dialogButton" style="background-color:#ff8000;" onclick="queryCustomDialogData();">
						<span>查 询</span>
					</div>
				</li>
				<li class="li">
					<div id="confirmInEdit" class="dialogButton"  onclick="doConfirm();">
						<span>确定</span>
					</div>
				</li>
			</ul>
		</div>
		<div align="center" style="margin-top: 5px;">
			<table id="customGrid"></table>
			<div id="pager"></div>
		</div>
		<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.fmatter.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
			var memberRank = ${memberRank}
			jQuery(document).ready(function() {
				renderDialogGrid();
			});
			
			function renderDialogGrid() {
				jQuery("#customGrid").jqGrid({
					url: "queryCampaign.do?cond_CUSTOMVALUE="+$("#cond_CUSTOMVALUE").val()+"&memberRank="+memberRank,
					datatype: "json",
				   	colNames: setGridCols(),
				   	colModel: setGridModels(),
				   	width: 430,
				   	height: 225,
				   	rowNum: 10,
				   	rowList: [10,20,50],
				   	pager: "#pager",
				   	forceFit: true,
				   	cellEdit: true,
				   	cellsubmit: "clientArray",
				    viewrecords: true,
				    altRows: true,
					altclass: "ui-state-focus",
					ondblClickRow: function (rowid, iRow, iCol, e) {
		   				var rowData = $("#customGrid").jqGrid("getRowData", rowid);
		   				window.parent.$("#total_recharge").val(rowData["CAMPAIGN_NAME"]);
		   				window.parent.$("#total_recharge_CUSTOM_VALUE").val(rowData["DATA_ID"]);
		   				window.parent.$("#total_recharge_DESC").html(rowData["CAMPAIGN_DESC"]);
		   				window.parent.JDialog.close();
					}
				});
			}
			
			function queryCustomDialogData() {
				var query_url = "queryCampaign.do?cond_CUSTOMVALUE="+$("#cond_CUSTOMVALUE").val();
				$("#customGrid").jqGrid("setGridParam", { url: query_url }).trigger("reloadGrid");
			}
			
			function setGridCols() {
				return ['编号','名称','描述'];
			}
			
			function setGridModels() {
				return [
						{name: 'DATA_ID', index: 'DATAID', width:30, align:"center" },
						{name: 'CAMPAIGN_NAME', index: 'CAMPAIGN_NAME', width:30, align:"center" },
						{name: 'CAMPAIGN_DESC', index: 'CAMPAIGN_DESC', width:30, align:"center",hidden : true }
						];
			}
		</script>
	</body>
</html>
