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
						<input style=" margin-top: 2px;" id="cond_BRANCHNAME" type="text" placeholder="分店名称"/>
					</div>
				</li>
				<li class="li">
					<div class="wrapper">
						<div class="inputArea" style="margin-top:1.5%;">
							<select id="cond_BRANCHTYPE" style ="height: 28.5px;width: 140.5px;">
							    <option value="" style="font-size:15px;">分店类型</option>
							    <option value="1" style="font-size:15px;">酒店</option>
							    <option value="2" style="font-size:15px;">公寓</option>    
						    </select>
						</div>
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
					url: "querybranchIdAndType.do?branchName="+encodeURIComponent($("#cond_BRANCHNAME").val())+"&branchType="+$("#cond_BRANCHTYPE").val(),
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
		   				window.parent.$("#branchId_CUSTOM_VALUE").val(rowData["BRANCHID"]);
		   				window.parent.$("#branchId").val(rowData["BRANCHNAME"]);
		   				window.parent.JDialog.close();
					}
					
			});
			});
			
			function setGridCols() {
				return ['分店编号','分店类型','分店名称'];
			}
			
			function setGridModels() {
				return [
				{name: 'BRANCHID', index: 'BRANCHID', width:30, align:"center" },
				{name: 'BRANCHTYPE', index: 'BRANCHTYPE', width:30, align:"center" },
				{name: 'BRANCHNAME', index: 'BRANCHNAME', width:30, align:"center" }
				]
			}
			
			function closed(){
			window.parent.JDialog.close();
			}
			
			function queryCustomDialogData() {
				var query_url = "querybranchIdAndType.do?branchName="+encodeURIComponent($("#cond_BRANCHNAME").val())+"&branchType="+$("#cond_BRANCHTYPE").val();
				$("#customGrid").jqGrid("setGridParam", { url: query_url }).trigger("reloadGrid");
			}
			
			
		</script>
	</body>
</html>
