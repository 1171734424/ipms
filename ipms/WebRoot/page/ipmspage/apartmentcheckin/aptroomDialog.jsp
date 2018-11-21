<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html style="width: 100%; height: inherit;">
	<head>
		<title></title>
		<%@ include file="../../common/css.jsp"%>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
	</head>
	<body oncontextmenu="return true;">
		<div class="table_cond" style="height:50px;line-height: 42px;">
			<ul class="ul">
				<li class="li">
					<div class="dialogInput">
						<input id="cond_CUSTOMVALUE" type="text" placeholder="编号/名称"/>
					</div>
				</li>
				<li class="li">
					<div id="queryInEdit" class="dialogButton" style="background-color:#ff8000;"  onclick="queryCustomDialogData();">
						<span>查 询</span>
					</div>
				</li>
				<li class="li">
					<div id="confirmInEdit" class="dialogButton"  onclick="doConfirm();">
						<span>确定</span>
					</div>
				</li>
			</ul>
		</div>
		<div align="center" style="margin-top: 5px;">
			<table id="customGrid"></table>
			<div id="pager"></div>
		</div>
		<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.fmatter.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
			var path = "<%=request.getContextPath() %>";
			var memberId = '${memberId}';
			var roomPrice = '${roomPrice}';
			var endTime = '${contrartEndTime}';
			var aptRenewTime = '${aptRenewTime}';
			var endPrice = '${endPrice}';
			var status = '${status}';
			var num = '${num}';
			jQuery(document).ready(function() {
				renderDialogGrid();
			});
			
			function renderDialogGrid() {
				jQuery("#customGrid").jqGrid({
					url: "queryAptRoomPrice.do?roomId="+$("#cond_CUSTOMVALUE").val()+"&memberId="+memberId,
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
				    viewrecords: true,
				    altRows: true,
					altclass: "ui-state-focus",
					ondblClickRow: function (rowid, iRow, iCol, e) {
		   				var rowData = $("#customGrid").jqGrid("getRowData", rowid);
		   				window.parent.$("#newRoomId").val(rowData["ROOMID2"]);
		   				window.parent.$("#newRoomPrice").val(rowData["ROOMPRICE2"]);
		   				//console.log(window.parent.document.getElementById('newRoomPrice').click());
		   				changeRoomPrice(roomPrice,rowData["ROOMPRICE2"],endTime,aptRenewTime,endPrice,status);
		   				window.parent.JDialog.close();
					}
				});
			}
			
			function queryCustomDialogData() {
				var query_url = "queryAptRoomPrice.do?roomId="+$("#cond_CUSTOMVALUE").val()+"&memberId="+memberId;
				$("#customGrid").jqGrid("setGridParam", { url: query_url }).trigger("reloadGrid");
			}
			
			function setGridCols() {
				return ['编号','名称','价格'];
			}
			
			function setGridModels() {
				return [
						{name: 'ROOMID2', index: 'ROOMID2', width:30, align:"center" },
						{name: 'ROOMNAME2', index: 'ROOMNAME2', width:30, align:"center" },
						{name: 'ROOMPRICE2', index: 'ROOMPRICE2', width:30, align:"center",hidden : true }
						];
			}
			function changeRoomPrice(roomPrice,newroomPrice,endTime,aptRenewTime,endPrice,status){
				var day2;
				if(aptRenewTime != ""){
					day2 = aptRenewTime.split('/')[2];
				} else {
					day2 = endTime.split('/')[2];
				}
				var now = new Date();
			    var year = now.getFullYear();       //年   
			    var month = now.getMonth() + 1;     //月   
			    var day = now.getDate();
			    var time = year+"/"+month+"/"+day;
			    var time1;
			    if(day2 <= day){
			    	time1 = year+"/"+month+"/"+day2;
			    } else {
			    	month = month - 1 ;
			    	time1 = year+"/"+month+"/"+day2;
			    }
			    var d2 = new Date(time.replace(/\-/g, "\/"));
			    var end = new Date(time1);
			    var date3=d2.getTime() - end.getTime();
			    var days = Math.floor(date3/(24*3600*1000));
			    
				var date1 = endTime.split('/');
				date1 = parseInt(date1[0]) * 12 + parseInt(date1[1]);
				var date2 = time.split('/');
				date2 = parseInt(date2[0]) * 12 + parseInt(date2[1]);
				var m = Math.abs(date1 - date2);
				var daymoney = doubleToPrice(roomPrice / 30) * days;
				
				var newdaymoney = doubleToPrice(newroomPrice / 30) * days;
			    //window.parent.$("#contrastPrice").val(Math.floor((newroomPrice - roomPrice) * 100) / 100);
			    window.parent.$("#contrastPrice").val(doubleToPrice(newroomPrice - roomPrice));
			    window.parent.$("#daymoney").val(daymoney);
			    
			    var newroomPrice11 = newroomPrice * m;

			    if(status == '1'){
			    	//window.parent.$("#repayMoney").val(Math.floor((((parseFloat(newroomPrice) - parseFloat(roomPrice)) * num) + (parseFloat(newroomPrice) - parseFloat(roomPrice))) * 100) / 100);
			    	//window.parent.$("#repayMoney").val(((((parseFloat(newroomPrice) - parseFloat(roomPrice)) * num) + (parseFloat(newroomPrice) - parseFloat(roomPrice)) * 100) / 100).toFixed(2));
			    	window.parent.$("#repayMoney").val(doubleToPrice(((parseFloat(newroomPrice) - parseFloat(roomPrice)) * num) + (parseFloat(newroomPrice) - parseFloat(roomPrice))));
			    }else{
			    	//window.parent.$("#repayMoney").val(Math.floor((parseFloat(newroomPrice11) + parseFloat(newroomPrice) - parseFloat(endPrice) - parseFloat(roomPrice) - parseFloat(newdaymoney)) * 100) / 100);
			    	//window.parent.$("#repayMoney").val((((parseFloat(newroomPrice11) + parseFloat(newroomPrice) - parseFloat(endPrice) - parseFloat(roomPrice) - parseFloat(newdaymoney)) * 100) / 100).toFixed(2));
			    	window.parent.$("#repayMoney").val(doubleToPrice(parseFloat(newroomPrice11) + parseFloat(newroomPrice) - parseFloat(endPrice) - parseFloat(roomPrice) - parseFloat(newdaymoney)));
			    }
			}
			
			function doubleToPrice(price){
				var thousand =  Math.floor(price*1000)/1000;
				
				var hundred =Math.floor(price*100)/100;
				
				if(thousand > hundred){
					var result =Math.ceil(price*100)/100;
					return result;
				}else{
					return hundred;
				}
			}
		</script>
	</body>
</html>
