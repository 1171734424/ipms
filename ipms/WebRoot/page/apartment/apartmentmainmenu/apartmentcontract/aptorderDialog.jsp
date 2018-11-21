<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<!DOCTYPE html>
<html style="width: 100%; height: inherit;">
	<head>
		<title></title>
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css"/>
	<style>
       #pager_left{
          width: 65px;
        }
        .ui-jqgrid .ui-jqgrid-htable th div {
		    overflow: hidden;
		    position: relative;
		    height: 20px;
        }
	</style>
	</head>
	<body oncontextmenu="return true;">
		<div class="table_cond table_cond-five" style="margin: 0; height: 40px;">
			<ul class="ul">	
				<li class="li">
					<div class="dialogInput">
						<input id="cond_GROUPID" type="text" placeholder="手机号"/>
					</div>
				</li>
				<li class="li">
					<div id="queryInEdit" class="dialogButton" style="background-color:#ff8000;" onclick="queryCustomDialogData();">
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
		<input type="hidden" name="branchId" id="branchId" value='${branchId}'/>
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
					url: "querycontrartaptorder.do?groupId="+$("#cond_GROUPID").val()+"&branchId="+$("#branchId").val(),
					datatype: "json",
				   	colNames: setGridCols(),
				   	colModel: setGridModels(),
				   	width: 560,
				   	height: 234,
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
		   				window.parent.$("#member_id").val(rowData["MEMBERNAME"]);
		   				window.parent.$("#mobile").val(rowData["M_PHONE"]);
		   			    window.parent.$("#member_rank").val(rowData["MEMBERRANK"]);
		   			    window.parent.$("#member_id_CUSTOM_VALUE").val(rowData["ORDER_USER"]);
		   			    window.parent.$("#room_type").val(rowData["ROOM_TYPE"]);
		   				window.parent.$("#room_id").val(rowData["ROOM_ID"]);
		   			    window.parent.$("#unitprice").val(rowData["UNITPRICE"]);//单价UNITPRICE,需要添加的字段
		   			    window.parent.$("#cash").val(rowData["ADVANCE_CASH"]);//押金(就是定金)
		   			    window.parent.$("#totalprice").val(rowData["ROOM_PRICE"]);//应付金额，对应apt的roomprice
		   			    window.parent.$("#actualCash").val((Number(rowData["ROOM_PRICE"]) - Number(rowData["ADVANCE_CASH"])).toFixed(2));//实付金额
		   			    window.parent.$("#orderid").val(rowData["ORDER_ID"]);
		   			    window.parent.$("#advanceCash").val(rowData["ADVANCE_CASH"]);//定金
		   			   // window.parent.$("#typeofpayment").val(rowData["TYPEOFPAYMENT"]);//要改
		   			   window.parent.$("#typeofpayment").val("1");//要改
		   			   // window.parent.$("#time").val(rowData["TIME"]);//要改 租赁时间
		   			    window.parent.$("#time").val("");//要改 租赁时间
		   			   // window.parent.$("#totalprice").val("");
		   			    //window.parent.$("#contrart_end_time").val(rowData["CONTRARTENDTIME"]);
		   			    window.parent.$("#contrart_end_time").val(rowData["LEAVETIME"]);
		   			    window.parent.$("#endTimeTemp").val(rowData["LEAVETIME"]);//?
		   			    window.parent.$("#startTimeTemp").val(rowData["ARRIVALTIME"]);
		   			   // window.parent.$("#payment").val("");
		   			    //window.parent.$("#actualCash").val("");
		   			    window.parent.hasorder();
		   				window.parent.JDialog.close();
		   				
					}
					
			});
			});
			
			function setGridCols() {
				return ['订单编号','会员名','手机号','房间号','房型','房租总价','会员编号','会员等级','定金',
				        '单价','租赁方式','租赁时间','房租到期','开始时间','截止时间'];
			}
			
			function setGridModels() {
				return [
				{name: 'ORDER_ID', index: 'ORDER_ID', width:50, align:"center" },
				{name: 'MEMBERNAME', index: 'MEMBERNAME', width:30, align:"center" },
				{name: 'M_PHONE', index: 'M_PHONE', width:30, align:"center" },
				{name: 'ROOM_ID', index: 'ROOM_ID', width:15, align:"center" ,hidden:false},
				{name: 'ROOM_TYPE', index: 'ROOM_TYPE', width:15, align:"center" ,hidden:false},
				{name: 'ROOM_PRICE', index: 'ROOM_PRICE', width:30, align:"center" ,hidden:true},
				{name: 'ORDER_USER', index: 'ORDER_USER', width:30, align:"center" ,hidden:true},
				{name: 'MEMBERRANK', index: 'MEMBERRANK', width:30, align:"center" ,hidden:true},
				{name: 'ADVANCE_CASH', index: 'ADVANCE_CASH', width:30, align:"center" ,hidden:true},
				
				
				{name: 'UNITPRICE', index: 'UNITPRICE', width:30, align:"center" ,hidden:true},
				{name: 'TYPEOFPAYMENT', index: 'TYPEOFPAYMENT', width:30, align:"center" ,hidden:true},
				{name: 'TIME', index: 'ROOM_PRICE', width:30, align:"center" ,hidden:true},
				{name: 'CONTRARTENDTIME', index: 'ORDER_USER', width:30, align:"center" ,hidden:true},
				{name: 'LEAVETIME', index: 'MEMBERRANK', width:30, align:"center" ,hidden:true},
				{name: 'ARRIVALTIME', index: 'ADVANCE_CASH', width:30, align:"center" ,hidden:true}
				];
			}
			
			function closed(){
			window.parent.JDialog.close();
			}
			
			function queryCustomDialogData() {
				var query_url = "querycontrartaptorder.do?groupId="+$("#cond_GROUPID").val();
				$("#customGrid").jqGrid("setGridParam", { url: query_url }).trigger("reloadGrid");
			}
			
			
		</script>
	</body>
</html>
