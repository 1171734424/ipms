<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html style="width: 100%; height: inherit;">
	<head>
		<title></title>
		<%@ include file="../../common/css.jsp"%>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/tipinfo-common.css"/>
		<script>
		</script>
	</head>
	<body oncontextmenu="return true;">
		<div class="table_cond" style="height:44px;line-height:38px;">
			<ul class="ul">
				<li class="li">
					<div class="dialogInput">
						<input id="cond_CUSTOMVALUE" type="text" placeholder="编号/名称"/>
					</div>
				</li>
				<li class="li li_one">
					<div class="dialogButton" onclick="queryDefaultauditDialogData();">
						<span>查 询</span>
					</div>
				</li>
				<li class="li li_two">
					<div class="dialogButton" onclick="closeCustom()">
						<span>关闭</span>
					</div>
				</li>
			</ul>
		</div>
		<div align="center" style="margin-top: 5px;">
			<table id="defaultauditGrid"></table>
			<div id="pager"></div>
		</div>
		<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.fmatter.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
			jQuery(document).ready(function() {			
				jQuery("#defaultauditGrid").jqGrid({
					url: "loaddefaultauditGrid.do?queryData=" + $("#cond_CUSTOMVALUE").val(),
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
				   	sortname: "id",
				    viewrecords: true,
				    sortorder: "desc",
				    altRows: true,
					altclass: "ui-priority-secondary",
					ondblClickRow: function (rowid, iRow, iCol, e) {
		   				var rowData = $("#defaultauditGrid").jqGrid("getRowData", rowid);
		   				window.parent.$("#coauditone").val(rowData["POST_ID"]);
		   				window.parent.$("#coauditonename").val(rowData["POST_NAME"]);
		   				closeCustom();
					}
				});
			});
			
			
			function queryDefaultauditDialogData() {
				var query_url = "loaddefaultauditGrid.do?queryData=" + $('#cond_CUSTOMVALUE').val();
				$("#defaultauditGrid").jqGrid("setGridParam", { url: query_url }).trigger("reloadGrid");
			}
			
			function setGridCols() {
				return ['职位编号','职位名称'];
			}
			
			function setGridModels() {
				return [{name: 'POST_ID', index: 'POST_ID', width:110,align:"center" },
				        {name: 'POST_NAME', index: 'POST_NAME', width:110, align:"center" }
				   ]
			}
			
			function closeCustom(){
			window.parent.JDialog.close();
			}
		</script>
	</body>
</html>