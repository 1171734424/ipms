<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<%
	request.setAttribute("producttype", request.getAttribute("producttype"));
 %>
<!DOCTYPE html>
<html style="width: 100%; height: inherit;">
	<head>
		<title></title>
		<%@ include file="../common/css.jsp"%>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/manage/decideDetail.css"/>
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	</head>
	<body oncontextmenu="return true;">
		
		<div align="center" style="margin-top: 5px;">
			<table id="customGrid"></table>
			<div id="pager"></div>
		</div>
		<input type="hidden" name="colName" id="colName" value='${colName}'/>
		<%@ include file="../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.fmatter.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	 	<script src="<%=request.getContextPath()%>/script/common/ui.datetimepicker.js"></script>
	 	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<!--<script>
			var base_path = "<%=request.getContextPath()%>";
			var producttype="${producttype}";
			$(document).ready(function(){
			  	$(".wdate").datetimepicker({ datetimeFormat: "yy/mm/dd HH:mm:ss" });
				$("#ui-datepicker-div").css('font-size','0.5em');
				});
		</script>
		
		--><script>
			var checked_row,serialNo = "", hiddenValue = "";
			var cols = [ "value", "label" ];
			var status;
			jQuery(document).ready(function() {
				jQuery("#customGrid").jqGrid({
					url: "queryDecideDetail.do?producttype="+producttype,
					datatype: "json",
				   	colNames: setGridCols(),
				   	colModel: setGridModels(),
				   	width: 1500,
				   	height: 400,
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
					onCellSelect: function (rowid, iCol, cellcontent, ex) {
				   		if(cellcontent) {
			   				try {
			   					if($(cellcontent).attr("class") == "checkbox") {
			   						return;
			   					}
			   				} catch(ex) {
			   				}
			   			}
				   		var checkObj = $("#gcbox" + rowid),
				   			checked = checkObj.prop("checked");
				   		checkObj.prop("checked", !checked);
				   		onChecked(rowid);
				   	}
				});
			});
			
			function setGridCols() {
				return ['选择','设备序列号','状态','项目名称','客户名称','质保起始日期','质保截止日期','入库时间','出库时间','设备价格','供应商','供应商联系方式','商品编号'];
			}
			
			function setGridModels() {
				return [{name: "CHECK", index: "CHECK", width: 30, align: "center", frozen: true, editable: false, edittype: "radio",
						formatter: function cboxFormatter(cellvalue, options, rowObject) {
							  return '<div class="checkbox"><input id="gcbox' + options.rowId + '" name="gcbox" type="radio" value="' + options.rowId + '" ' 
							  	+ 'style="display:none;" onclick="onChecked(' + options.rowId + ')"/><label for="gcbox' + options.rowId + '"></label></div>';
						}
					},
				{name: 'SERIALNO', index: 'SERIALNO', width:75, align:"center" },
				{name: 'STATUS', index: 'STATUS', width:30, align:"center" },
				{name: 'PROJECTID', index: 'PROJECTID', width:48, align:"center" },
				{name: 'CUSTID', index: 'CUSTID', width:48, align:"center" },
				{name: 'VALIDSTART', index: 'VALIDSTART', width:60, align:"center" },
				{name: 'VALIDEND', index: 'VALIDEND', width:60, align:"center" },
				{name: 'RECORDTIME', index: 'RECORDTIME', width:65, align:"center"},
				{name: 'STOREOUTTIME', index: 'STOREOUTTIME', width:65, align:"center",editType:"date" },
				{name: 'GOODSPRICE', index: 'GOODSPRICE', width:60, align:"center" },
				{name: 'COMPANYNAME', index: 'companyname', width:60, align:"center" },
				{name: 'COMPANYNO', index: 'companyno', width:60, align:"center" },	
				{name: 'DATAID', index: 'DATAID', width:30, hidden:true,align:"center" }
				    ]
			}
			
			function closed(){
			window.parent.JDialog.close();
			}
			
			function queryCustomDialogData() {
				var query_url = "selectDecideInfoByCon.do?producttype="+producttype+"&querySerial=" + $('#cond_SERIALNO').val()+"&queryProjectId="+$('#cond_PROJECTID').val()+"&queryCustId="+$('#cond_CUSTID').val()+"&queryStoreOutTime="+$('#cond_STOREOUTTIME').val()+"&queryStatus="+$('#cond_STATUS').val()+"&queryValidEnd="+$('#cond_VALIDEND').val();
				$("#customGrid").jqGrid("setGridParam", { url: query_url }).trigger("reloadGrid");
			}
			
			function turnToQuestionFeedBack(rowId) {
				if (checked_row && checked_row.length > 0) {
							var rowData;
							$.each(checked_row, function(index) {
								rowData = $("#customGrid").jqGrid("getRowData", checked_row[index]);
								serialNo = rowData["SERIALNO"] ;
								status = rowData["STATUS"] ;						
							});
						
					if(status != "在库" ){
						JDialog.open("添加记录", base_path + "/turnToQuestionFeedBack.do?serialNo="+serialNo , 500, 400);
					}else{
						window.parent.showMsg("设备任在库状态，不可操作");
					}
				} else {
					window.parent.showMsg("还未选择，不可操作");
				}
			}
			
			function turnToApplyForStoreOut() {
				if (checked_row && checked_row.length > 0) {
							var rowData;
							$.each(checked_row, function(index) {
								rowData = $("#customGrid").jqGrid("getRowData", checked_row[index]);
								shownText = rowData["DATAID"] ;
								status = rowData["STATUS"] ;
							});
					if (status != "已出库"){	
						var defer = $.Deferred();
						defer.done(function() {
							window.parent.parent.listFunctions("M906");
						}).then(function() {
							window.parent.parent.doQuery("R90610", false, false, shownText);
						});
						defer.resolve();
					}else{
						window.parent.showMsg("设备是已出库的状态，不可操作");
					}
					
				} else {
					window.parent.showMsg("还未选择，不可操作");
				}
			}
			
			function onChecked() {
				var rowId = arguments[0];
				if(!checked_row) checked_row = new Array();
				var index = contains(checked_row, rowId);
				if(index == -1) {
					checked_row.push(rowId);
				} else {
					checked_row.splice(index, 1);
				}
			}
			
			function contains(arr, arg) {
				var position = -1;
				$.each(arr, function(index) {
					if (arr[index] == arg) {
						position = index;
						return false;
					}
				});
				return position;
			}
		</script>
	</body>
</html>
