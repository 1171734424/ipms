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
	</head>
	<body oncontextmenu="return true;">
		<div class="table_cond table_cond-five" style="margin: 0; height: 40px;">
			<ul class="ul">	
				<li class="li">
					<div class="dialogInput">
						<input style=" margin-top: 2px;" id="cond_BRANCHNAME" type="text" placeholder="民宿名称"/>
					</div>
				</li>
				<li class="li">
					<div id="queryInEdit" class="dialogButton" style="background-color: #ff8000" onclick="queryCustomDialogData();">
						<span>查 询</span>
					</div>
				</li>
				<%--<li class="li">
					<div id="closeInEdit" class="dialogButton" onclick="window.parent.JDialog.close();">
						<span>关闭</span>
					</div>
				</li>
			--%></ul>
		</div>
		<div align="center" style="margin-top: 5px;">
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
					url: "queryHouseAndroomIdInHouse.do?houseName="+encodeURIComponent($("#cond_BRANCHNAME").val()),
					datatype: "json",
				   	colNames: setGridCols(),
				   	colModel: setGridModels(),
				   	width: 450,
				   	height: 220,
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
		   				window.parent.$("#branchId_CUSTOM_VALUE").val(rowData["HOUSEID"]);
		   				window.parent.$("#branchId").val(rowData["HOUSENAME"]);
		   				window.parent.$("#roomId").val(rowData["HOUSENO"]);
		   				window.parent.$("#roomId").attr('disabled','disabled');
		   				window.parent.$("#roomId").css("background-color","#EAEDF1");
		   				window.parent.$("#roomType").val(rowData["HOUSENO"]);
		   				window.parent.$("#roomType").attr('disabled','disabled');
		   				window.parent.$("#roomType").css("background-color","#EAEDF1");
		   				window.parent.$("#labelhousechange").text("特惠房号");
		   				window.parent.JDialog.close();
					}
					
			});
			});
			
			function setGridCols() {
				return ['民宿编号','民宿名称','房间号'];
			}
			
			function setGridModels() {
				return [
				{name: 'HOUSEID', index: 'HOUSEID', width:30, align:"center" },
				{name: 'HOUSENAME', index: 'HOUSENAME', width:30, align:"center" },
				{name: 'HOUSENO', index: 'HOUSENO', width:30, align:"center" }
				]
			}
			
			function closed(){
			window.parent.JDialog.close();
			}
			
			function queryCustomDialogData() {
				var query_url = "queryHouseAndroomIdInHouse.do?branchName="+encodeURIComponent($("#cond_BRANCHNAME").val())+"&branchType="+$("#cond_BRANCHTYPE").val();
				$("#customGrid").jqGrid("setGridParam", { url: query_url }).trigger("reloadGrid");
			}
			
			
		</script>
	</body>
</html>
