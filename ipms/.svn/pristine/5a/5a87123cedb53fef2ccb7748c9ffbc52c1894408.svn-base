<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html style="width: 100%; height: 100%">
	<head>
		<title></title>
		<%@ include file="../../common/css.jsp"%>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/manage/decideDetail.css"/>
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/ipms/addSmsTemplates.css" rel="stylesheet" type="text/css" />
	</head>
	<body oncontextmenu="return true;">
		<div class="table_cond table_cond-five" style="margin: 0; height: 40px;">
			<ul class="ul">	
				<li class="li">
					<div class="dialogInput">
						<input id="cond_TIMESTART" type="text" class="wdate" placeholder="开始时间"/>
					</div>
				</li>
				<li class="li">
					<div class="dialogInput">
						<input id="cond_TIMEEND" type="text" class="wdate" placeholder="截止时间"/>
					</div>
				</li>
				<li class="li">
					<div class="wrapper">
						<div class="inputArea" style="margin-top:1.5%;">
							<select id="cond_STATUS">
							    <option value="" style="font-size:15px;">状态</option>
							    <option value="未修复" style="font-size:15px;">未修复</option>   
							    <option value="已修复" style="font-size:15px;">已修复</option>   
						    </select>
						</div>
					</div>
				</li>
				
				<li class="li">
					<div id="queryInEdit" class="dialogButton" style="background-color: #52B2E3" onclick="queryCustomDialogData();">
						<span>查 询</span>
					</div>
				</li>
				<li class="li">
					<div id="closeInEdit" class="dialogButton" onclick="addRepairLog();">
						<span>新增</span>
					</div>
				</li>
			</ul>
		</div>
		<div align="center" style="margin-top: 5px;">
			<table id="customGrid"></table>
			<div id="pager"></div>
		</div>
		<input type="hidden" name="colName" id="colName" value='${colName}'/>
		<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.fmatter.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	 	<script src="<%=request.getContextPath()%>/script/common/ui.datetimepicker.js"></script>
	 	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script>
			var base_path = "<%=request.getContextPath()%>";
			var serialNo="${serialNo}";
			$(document).ready(function(){
			  	$(".wdate").datetimepicker({ datetimeFormat: "yy/mm/dd HH:mm:ss" });
				$("#ui-datepicker-div").css('font-size','0.5em');
				});
		</script>
		<script>
			var checked_row, hiddenValue = "";
			var cols = [ "value", "label" ];
			var status;
			jQuery(document).ready(function() {
				jQuery("#customGrid").jqGrid({
					url: "queryERepairLog.do?serialNo="+serialNo,
					datatype: "json",
				   	colNames: setGridCols(),
				   	colModel: setGridModels(),
				   	width: 1000,
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
					ondblClickRow: function (rowid, iRow, iCol, e) {
		   				
					},
					onCellSelect: function (rowid, iCol, cellcontent, ex) {
				   
				   	}
				});
			});
			
			function setGridCols() {
				return ['申请编号','维修编号','问题标签','问题描述','关联房单','状态','申请时间','维修时间'];
			}
		
			function setGridModels() {
				return [
				{name: 'REPAIRAPPLYID', index: 'REPAIRAPPLYID', width:30, align:"center" },
				{name: 'LOGID', index: 'LOGID', width:30, align:"center" },
				{name: 'PROBLEMTAG', index: 'PROBLEMTAG', width:30, align:"center" },
				{name: 'DESCRIBE', index: 'DESCRIBE', width:30, align:"center" },
				{name: 'CONTRACTID', index: 'CONTRACTID', width:30, align:"center" },
				{name: 'STATUS', index: 'STATUS', width:30, align:"center" },
				{name: 'RECORDTIME', index: 'RECORDTIME', width:30, align:"center" },
				{name: 'ACREPAIRTIME', index: 'ACREPAIRTIME', width:30, align:"center"}
				]
			}
			
			function closed(){
			window.parent.JDialog.close();
			}
			
			function queryCustomDialogData() {
				var query_url = "queryERepairLog.do?serialNo=" +serialNo+ "&queryTimeStart="+$('#cond_TIMESTART').val()+"&queryTimeEnd="+$('#cond_TIMEEND').val()+"&queryStatus="+$('#cond_STATUS').val();
				$("#customGrid").jqGrid("setGridParam", { url: query_url }).trigger("reloadGrid");
			}
		</script>
	</body>
</html>
