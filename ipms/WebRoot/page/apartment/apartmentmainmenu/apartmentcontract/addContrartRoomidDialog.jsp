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
	<input type="hidden" id="memberrank" name="memberrank" value="${memberrank}">
		<div class="table_cond table_cond-five" style="margin: 0; height: 40px;">
			<ul class="ul">	
				<li class="li">
					<div class="dialogInput">
						<input id="cond_GROUPID" type="text" placeholder="房间号"/>
					</div>
				</li>
				<li class="li">
					<div id="queryInEdit" class="dialogButton" style="background-color: #ff8000" onclick="queryCustomDialogData();">
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
		<input type="hidden" name="startTime" id="startTime" value='${startTime}'/>
		
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
					url: "querycontrartroomidandroomtype.do?groupId="+$("#cond_GROUPID").val()+"&memberrank="+$("#memberrank").val(),
					datatype: "json",
				   	colNames: setGridCols(),
				   	colModel: setGridModels(),
				   	width: 450,
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
		   				window.parent.$("#room_type").val(rowData["ROOMTYPE"]);
		   				window.parent.$("#room_id").val(rowData["ROOMID"]);
		   				var startTime = $("#startTime").val();
		   				var memberRank = $("#memberrank").val();
		   				$.ajax({
		   					url: base_path+"/loadRoomPrice4Contrart.do",
		   					type: "post",
		   					dataType: "json",
		   					data: {
		   						roomType:rowData["ROOMTYPE"],
		   						startTime : startTime,
		   						memberRank : memberRank
		   					},
		   					
		   					success: function(json) {
		   					    window.parent.$("#unitprice").val(json.message);
		   					    window.parent.$("#oldprice").val(json.message);
				   			    window.parent.$("#cash").val(json.message);
				   			    window.parent.$("#orderid").val("");
				   			  //window.parent.$("#typeofpayment").val("");
				   			    window.parent.$("#totalprice").val("");
				   			    window.parent.$("#contrart_end_time").val("");		   			    
				   			    window.parent.$("#orderid").val("");
				   			    window.parent.$("#advanceCash").val("");
				   			    window.parent.$("#actualCash").val("");
				   			    window.parent.Money();
				   				window.parent.JDialog.close();
		   					},
		   					error: function(json) {
		   						showMsg("房价获取失败！");
		   					}
		   				});
		   				
		   			    //window.parent.$("#unitprice").val(rowData["ROOMPRICE2"]);
		   			    //window.parent.$("#cash").val(rowData["ROOMPRICE2"]);
		   			   
					}
					
			});
			});
			
			function setGridCols() {
				return ['房间类型','房型名称','房间号','房价'];
			}
			
			function setGridModels() {
				return [
				{name: 'ROOMTYPE', index: 'ROOMTYPE', width:30, align:"center",hidden:true},
				{name: 'ROOMNAME2', index: 'ROOMNAME', width:30, align:"center"  },
				{name: 'ROOMID', index: 'ROOMID', width:30, align:"center" },
				{name: 'ROOMPRICE2', index: 'ROOMPRICE', width:30, align:"center" ,hidden:true}
				];
			}
			
			function closed(){
			window.parent.JDialog.close();
			}
			
			function queryCustomDialogData() {
				var query_url = "querycontrartroomidandroomtype.do?groupId="+$("#cond_GROUPID").val()+"&memberrank="+$("#memberrank").val();
				$("#customGrid").jqGrid("setGridParam", { url: query_url }).trigger("reloadGrid");
			}
			
		    function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
		</script>
	</body>
</html>
