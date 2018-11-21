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
						<input id="cond_SENDTIMESTART" type="text" class="wdate" placeholder="开始时间"/>
					</div>
				</li>
				<li class="li">
					<div class="dialogInput">
						<input id="cond_SENDTIMEEND" type="text" class="wdate" placeholder="截止时间"/>
					</div>
				</li>
				<li class="li">
					<div class="dialogInput">
						<input id="cond_SMSRECIPENTNO" type="text" placeholder="收信号码"/>
					</div>
				</li>
				<li class="li">
					<div class="wrapper">
						<div class="inputArea" style="margin-top:1.5%;">
							<select id="cond_BRANCHID">
							    <option value="" style="font-size:15px;">归属门店</option>
							    <c:forEach items="${branchList}" var="bl">
									<option value="${bl.branchId}">
										${bl.branchName}
									</option>
								</c:forEach>
						    </select>
						</div>
					</div>
				</li>
				<li class="li">
					<div class="wrapper">
						<div class="inputArea" style="margin-top:1.5%;">
							<select id="cond_STATUS">
							    <option value="" style="font-size:15px;">状态</option>
							    <option value="1" style="font-size:15px;">待发</option>
							    <option value="2" style="font-size:15px;">已发</option>   
							    <option value="3" style="font-size:15px;">失败</option>   
						    </select>
						</div>
					</div>
				</li>
				
				<li class="li">
					<div id="queryInEdit" class="dialogButton" style="background-color: #ff8000;" onclick="queryCustomDialogData();">
						<span>查 询</span>
					</div>
				</li>
				<li class="li">
					<div id="closeInEdit" class="dialogButton" onclick="window.parent.JDialog.close();">
						<span>关闭</span>
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
			var templateId="${templateId}";
			$(document).ready(function(){
			  	$(".wdate").datetimepicker({ datetimeFormat: "yy/mm/dd HH:mm:ss" });
				$("#ui-datepicker-div").css('font-size','0.5em');
				});
		</script>
		
		<script>
			var checked_row,serialNo = "", hiddenValue = "";
			var cols = [ "value", "label" ];
			var status;
			jQuery(document).ready(function() {
				jQuery("#customGrid").jqGrid({
					url: "querySmsInDetail.do?templateId="+templateId,
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
		   				var rowData = $("#customGrid").jqGrid("getRowData", rowid);
		   				JDialog.open("短信内容", base_path + "/selectSmsContent.do?dataId="+rowData["DATAID"],450,281);
		   				
					},
					onCellSelect: function (rowid, iCol, cellcontent, ex) {
				   
				   	}
				});
			});
			
			function setGridCols() {
				return ['数据编号','归属门店','短信内容','收信号码','创建时间','状态','发送时间','返回码'];
			}
			
			function setGridModels() {
				return [
				{name: 'DATAID', index: 'DATAID', width:30, align:"center" },
				{name: 'BRANCHID', index: 'BRANCHID', width:30, align:"center" },
				{name: 'SMSCONTENT', index: 'SMSCONTENT', width:30, align:"center" },
				{name: 'SMSRECIPENTNO', index: 'SMSRECIPENTNO', width:30, align:"center" },
				{name: 'RECORDTIME', index: 'RECORDTIME', width:30, align:"center" },
				{name: 'STATUS', index: 'STATUS', width:30, align:"center" },
				{name: 'SENDTIME', index: 'SENDTIME', width:30, align:"center" },
				{name: 'ERRORCODES', index: 'ERRORCODES', width:30, align:"center"}
				]
			}
			
			function closed(){
			window.parent.JDialog.close();
			}
			
			function queryCustomDialogData() {
				var query_url = "querySmsInDetail.do?templateId="+templateId+"&queryRecipentNo=" + $('#cond_SMSRECIPENTNO').val()+"&querySendTimeStart="+$('#cond_SENDTIMESTART').val()+"&querySendTimeEnd="+$('#cond_SENDTIMEEND').val()+"&queryBranchId="+$('#cond_BRANCHID').val()+"&queryStatus="+$('#cond_STATUS').val();
				$("#customGrid").jqGrid("setGridParam", { url: query_url }).trigger("reloadGrid");
			}
		</script>
	</body>
</html>
