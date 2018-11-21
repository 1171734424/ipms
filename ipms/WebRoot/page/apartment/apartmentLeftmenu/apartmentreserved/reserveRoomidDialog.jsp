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
						<input id="cond_GROUPID" type="text" placeholder="房型"/>
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
		var time = '<%= request.getParameter("time")%>';
			jQuery(document).ready(function() {
			    var dataid = '<%= request.getParameter("dataid")%>';
				jQuery("#customGrid").jqGrid({
					url: base_path + "/queryresveredroomid.do?memberrank=2&time=" + time,
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
		   				$.ajax({
		   					url: base_path+"/resetresveredroomid.do",
		   					type: "post",
		   					dataType: "json",
		   					data: {
		   						dataId : dataid,
		   						roomid : rowData.ROOMID
		   					},
		   					
		   					success: function(json) {
		   						if(json.result == 1){
		   							window.parent.parent.pagerFormSubmit();
		   						}else if(json.result == 0 ){
		   						    showMsg(json.message);
		   						}
		   					},
		   					error: function(json) {
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
				var query_url = "queryresveredroomid.do?memberrank=2&time="+time + "&roomtype=" + $("#cond_GROUPID").val();
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
