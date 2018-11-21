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
					<div class="dialogInput" style="width:290px;">
						<input id="cond_GROUPID" type="text" style="height:32px;" placeholder="组合面额"/>
					</div>
				</li>
				<li class="li">
					<div id="queryInEdit" class="dialogButton" style="background-color: #ff8000" onclick="queryCustomDialogData();">
						<span>查 询</span>
					</div>
				</li>
				<!--<li class="li">
					<div id="closeInEdit" class="dialogButton" onclick="window.parent.JDialog.close();">
						<span>关闭</span>
					</div>
				</li>
			--></ul>
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
					url: "queryCouponGroupDetail.do?groupId="+$("#cond_GROUPID").val(),
					datatype: "json",
				   	colNames: setGridCols(),
				   	colModel: setGridModels(),
				   	width: 450,
				   	height: 232,
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
		   				window.parent.$("#couponGroupId").val(rowData["DATAID"]);
		   				window.parent.$("#couponName").val(rowData["COUPONNAME"]);
		   				window.parent.$("#couponPrice").val(rowData["TOTALPRICE"]);
		   				var showInfo = "";
		   				if(rowData["TENCOUPON"] != null && rowData["TENCOUPON"] != ""){
		   					showInfo = showInfo +"10元"+rowData["TENCOUPON"]+"张，";
		   				}
		   				if(rowData["TWENTYCOUPON"] != null && rowData["TWENTYCOUPON"] != ""){
		   					showInfo = showInfo +"20元"+rowData["TWENTYCOUPON"]+"张，";
		   				}
		   				if(rowData["THIRTYCOUPON"] != null && rowData["THIRTYCOUPON"] != ""){
		   					showInfo = showInfo +"30元"+rowData["THIRTYCOUPON"]+"张，";
		   				}
		   				if(rowData["FIFTYCOUPON"] != null && rowData["FIFTYCOUPON"] != ""){
		   					showInfo = showInfo +"50元"+rowData["FIFTYCOUPON"]+"张，";
		   				}
		   				if(rowData["HUNDREDCOUPON"] != null && rowData["HUNDREDCOUPON"] != ""){
		   					showInfo = showInfo +"100元"+rowData["HUNDREDCOUPON"]+"张，";
		   				}
		   				showInfo = showInfo.substring(0, showInfo.length - 1);
		   				window.parent.$("#showRule").val(showInfo);
		   				window.parent.JDialog.close();
					}
					
			});
			});
			
			function setGridCols() {
				return ['数据编号','组合名称','面额','10元','20元','30元','50元','100元'];
			}
			
			function setGridModels() {
				return [
				{name: 'DATAID', index: 'DATAID', width:100, align:"center" ,hidden: true},
				{name: 'COUPONNAME', index: 'COUPONNAME', width:30, align:"center" },
				{name: 'TOTALPRICE', index: 'TOTALPRICE', width:30, align:"center" },
				{name: 'TENCOUPON', index: 'TENCOUPON', width:30, align:"center" },
				{name: 'TWENTYCOUPON', index: 'TWENTYCOUPON', width:30, align:"center" },
				{name: 'THIRTYCOUPON', index: 'THIRTYCOUPON', width:30, align:"center" },
				{name: 'FIFTYCOUPON', index: 'FIFTYCOUPON', width:30, align:"center" },
				{name: 'HUNDREDCOUPON', index: 'HUNDREDCOUPON', width:30, align:"center" }
				]
			}
			
			function closed(){
			window.parent.JDialog.close();
			}
			
			function queryCustomDialogData() {
				var query_url = "queryCouponGroupDetail.do?groupId="+$("#cond_GROUPID").val();
				$("#customGrid").jqGrid("setGridParam", { url: query_url }).trigger("reloadGrid");
			}
			
			
		</script>
	</body>
</html>
