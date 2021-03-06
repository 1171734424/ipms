<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html style="width: 100%; height: inherit;">
	<head>
		<title></title>
		<%@ include file="../common/css.jsp"%>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
		<style type="text/css">
		select#selectType{
		height:36px!important;
		padding-left:4px
		}
		#cond_CUSTOMVALUE{
		height:36px;
		border-radius:4px;
		text-align:left;
		padding-left:60px;
		}
		.table_cond ul{
		overflow:hidden;
		}
		.table_cond ul li{
		    margin-left:0px;
		    border:none;
		}
		.dialogInput{
		height:36px;
		margin:0px;
		padding:0px;
		position:relative
		}
		li .dialogButton2{
		margin-right:0px;
	margin-top:0px;
		height:36px;
		line-height:36px;
		font-size: 14px;
    text-align: center;
    width: 72px;
    color:#fff;
  background: url(images/straR90912.png) no-repeat;
      background-position: center;
    background-size: 60%;
		}
		.table_cond ul{
		    float: inherit;
		    margin-left:25px;
		}
		body{
		background:transparent}
		.ui-jqgrid{
		padding-left:15px;
		padding-right:15px;
		}
		.ui-jqgrid .ui-jqgrid-hdiv{

		background:transparent;
		display:none;}
		.ui-widget-content{
		background:transparent;
		color:#fff}
		.ui-jqgrid-sortable{
		color:#fff}
		.ui-widget-content{
		border:none}
		.ui-jqgrid tr.ui-row-ltr td{
		border-right:none;
		height:28px;
		font-size:15px;
		}
	
		.ui-jqgrid-btable .ui-widget-content:nth-child(odd){
	background:transparent
}
.ui-jqgrid tr.jqgrow td{
          border-bottom-width: 0px;}
          input, select, textarea{
          background:transparent;
          color:#fff;
          }
         .dialogInput input::-webkit-input-placeholder{
           color: #fff;
           font-size:14px
           }
        #selectType{
        border:none;
        border-right:1px solid #ccc;
        }
        .ui-jqgrid-view{
        padding:0px 20px}
      
        .ui-jqgrid .ui-jqgrid-bdiv{
            height: 280px!important;
          width: 430px;
        }
        .typecolor{
        background-color:#bbb;
        }
        .ui-widget-content td:hover{
        background:#1e90ff;
       }
       .ui-jqgrid .ui-pg-input.fenye{
     height:18px;
     width:18px;
     font-size:11px;
     text-align:center;
     margin:0;
        }
        .ui-icon{
        width:18px;
        height:18px;
        }
         .ui-icon{
         background-image: url(css/common/images/next.png)!important;
        }
     
        .ui-icon-seek-next{
        background-position:0 0;
        }
        .ui-icon-seek-end{
        background-position:-54px 0px}
        .ui-icon-seek-prev{
        background-position:-18px 0px
        }
        .ui-icon-seek-first{
           background-position:-36px 0px;
        }
       .ui-state-disabled, .ui-widget-content .ui-state-disabled, .ui-widget-header .ui-state-disabled {
        opacity: 1;
        filter: Alpha(Opacity=35);
        background-image: none;
         }
         	.button_confirm{
		border: 1px solid #fff;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 13px;
    color: #fff;
		}
			.button_cancel{
		border: 1px solid #fff;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 13px;
    color: #fff;
		}
		.add_button{
		display: -webkit-inline-box;
    float: right;
       padding-right: 103px;
		}
     		</style>
	</head>
	<body oncontextmenu="return true;">
		<div class="table_cond" style="height:50px;line-height: 42px;">
			<ul class="ul">
			<!-- 	<li class="li" style="position:absoulte;left：0px;top:0px">
					<select id="selectType" onchange="chooseType()">
					<option value="1" selected>酒店</option>
					<option value="2">民宿</option>
					<option value="3">公寓</option>
					</select>
				</li> -->
				<li class="li chaxun">
					<div class="dialogInput" id="CDialog" style="width: 400px;" style="position:relative;">
					<select id="selectType" onchange="chooseType()" style="position:absolute;left：0px;top:0px">
					<option class="typecolor" value="1" selected>酒店</option>
				<!-- 	<option value="2">民宿</option> -->
					<option class="typecolor" value="3">公寓</option>
					</select>
					<div id="queryInEdit" class="dialogButton2" style="background-color:#transparent;position:absolute;right:0px;top:0px;width:36px;height:36px;" onclick="queryCustomDialogData();">

					</div>
						<input id="cond_CUSTOMVALUE" type="text" placeholder="请输入门店名称"/>
					</div>
				</li>	
				<!-- <li class="li" style="">
					<div id="queryInEdit" class="dialogButton2" style="background-color:#transparent;" onclick="queryCustomDialogData();">
						<span>查 询</span>
					</div>
				</li> -->
				<!-- <li class="li">
					<div id="confirmInEdit" class="dialogButton" onclick="doConfirm();">
						<span>确定</span>
					</div>
				</li> -->
				<!--<li class="li">
					<div id="closeInEdit" class="dialogButton" onclick="window.parent.JDialog.close();">
						<span>关闭</span>
					</div>
				</li>
			--></ul>
		</div>
		<div align="center">
			<table id="customGrid"></table>
			<div id="pager"  style="position: absolute;bottom: -60px;"></div>
    		<div class="add_button">
			<div class="button_confirm" onclick="confirm()">确认</div>
			<div class="button_cancel" onclick="cancel()">取消</div>
			
		</div>
		</div>
	
		    
		<input type="hidden" name="dialogTarget" id="dialogTarget" value="${dialogTarget}"/>
		<input type="hidden" name="dialogColumns" id="dialogColumns" value="${dialogColumns}"/>
		<input type="hidden" name="dialogConditions" id="dialogConditions" value="${dialogConditions}"/>
		<input type="hidden" name="selectType" id="selectType" value="${selectType}"/>
		<input type="hidden" name="colName" id="colName" value='${colName}'/>
		<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery-ui.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.md5.js"></script>
		<script src="<%=request.getContextPath()%>/script/loginDialog.js"></script>
		<script src="<%=request.getContextPath()%>/script/login-jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.fmatter.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		
		<script>
			var selectType = "${selectType}";
			var columns = $("#dialogColumns").val();
			var cols = [ "value", "label" ];
			var checked_row,editTypeTemp, shownText = "", hiddenValue = "";
			var rowData;
			jQuery(document).ready(function() {
				if (selectType == "dialog" || selectType == "dialog-radio") {
					renderDialogGrid();
				} else if (selectType == "dialog-multi") {
					showCheckList();
					
				}
				
				if($("#dialogQuickAdd").val()){
				  $("#CDialog").css("width","180px");
				}
				$('.ui-pg-selbox').css("display","none");
				
				$(".ui-pg-input").addClass("fenye");
				
				$("#pager").css("width","400px");
				$("#pager").css("color","#fff");
				$(".ui-state-default").css("background-image","url(images/next.png)");
				
				$("#pager_left").css("display","none");
				$("#pager_right").css("display","none");
			});
			
			function renderDialogGrid() {
				jQuery("#customGrid").jqGrid({
					url: loadUrl(),
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
		   				rowData = $("#customGrid").jqGrid("getRowData", rowid);
		   				window.parent.$("#branchValue").text(rowData[cols[1]]);
		   				window.parent.$("#branchId").val(rowData[cols[0]]);
		   				window.parent.$("#home-banner").show();
		   				window.parent.JDialog.close();
					},
					onLeftClickRow: function (rowid, iRow, iCol, e) {
		   				rowData = $("#customGrid").jqGrid("getRowData", rowid);
		   				$("#"+rowid).siblings().css("background","");
		   				$("#"+rowid).css("background","#1e90ff");
		   				
					},
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
			}
			
			function showCheckList() {
				$.ajax({
					url: loadUrl(),
					type: "post",
					dataType: "json",
					success: function(json) {
						$("#customGrid").empty();
						if(json.rows) {
							var el = "<tr>";
							$.each(json.rows, function(index) {
								if (index != 0 && index % 3 == 0) {
									el += "</tr><tr>"
								}
								el += "<td><div class='dialogTd' id='dt" + json.rows[index].value + "'>"
									+ "<input value='" + json.rows[index].value + "' type='hidden'/>" 
									+ json.rows[index].label + "</div></td>";
							});
							el += "</tr>";
							$(el).appendTo($("#customGrid"));
							
							$(".dialogTd").bind("click", function() {
								if ($(this).find(".selectTd").length == 0) {
									var ck = "<div class='selectTd'></div>";
									$(ck).appendTo($(this));
								} else {
									$(this).find(".selectTd").remove();
								}
								onChecked($(this).find("input").val());
			  				});
						}
					},
					error: function(json) {
					}
				});
			}
				
			function onChecked() {
				if(selectType == "dialog-radio"){
					var rowId = arguments[0];
					checked_row = new Array();
					checked_row.push(rowId);
					var idValue = "#gcbox" + rowId;
					$("input[id^='gcbox']").not(idValue).prop("checked", false);
				}else{
					var rowId = arguments[0];
					if(!checked_row) checked_row = new Array();
					var index = contains(checked_row, rowId);
					if(index == -1) {
						checked_row.push(rowId);
					} else {
						checked_row.splice(index, 1);
					}
				
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
			
			function loadUrl() {
				return "loadBranchDialogData.do?dialogTarget=" + $("#dialogTarget").val()
					+ "&dialogColumns=" + columns + "&dialogConditions=" + encodeURIComponent($("#dialogConditions").val())
					+ "&queryData=" + encodeURIComponent($("#cond_CUSTOMVALUE").val());
			}
			
			function queryCustomDialogData() {
				if ((selectType == "dialog")||(selectType == "dialog-radio")) {
					$("#customGrid").jqGrid("setGridParam", { url: loadUrl() }).trigger("reloadGrid");
				} else if (selectType == "dialog-multi") {
					showCheckList();
				}
			}
			
			function setGridCols() {
				return ['选择','编号','名称'];
			}
			
			function setGridModels() {
				if (selectType == "dialog-radio") {
					editTypeTemp = "radio";
				}else if(selectType == "dialog") {
					editTypeTemp = "checkbox";
				}
				return [
					{name: "CHECK", index: "CHECK", width: 30,hidden:true, align: "center", frozen: true, editable: false, edittype: editTypeTemp,
						formatter: function cboxFormatter(cellvalue, options, rowObject) {
							  return '<div class="checkbox"><input id="gcbox' + options.rowId + '" name="gcbox" type="'+editTypeTemp+'" value="' + options.rowId + '" ' 
							  	+ 'style="display:none;" onclick="onChecked(' + options.rowId + ')"/><label for="gcbox' + options.rowId + '"></label></div>';
						}
					},{name: cols[0], index: cols[0], width:110, align:"center",hidden:true },
				    {name: cols[1], index: cols[1], width:110, align:"left" }
				];
			}
			
			function doConfirm() {
				if (selectType == "dialog"||selectType == "dialog-radio") {
					if (checked_row && checked_row.length > 0) {
						var rowData;
						$.each(checked_row, function(index) {
							rowData = $("#customGrid").jqGrid("getRowData", checked_row[index]);
							shownText += rowData[cols[1]] + ",";
							hiddenValue += rowData[cols[0]] + ",";
						});
					}
				} else if (selectType == "dialog-multi") {
					if (checked_row && checked_row.length > 0) {
						var selDiv;
						$.each(checked_row, function(index) {
							selDiv = $("#dt" + checked_row[index]);
							shownText += selDiv.text() + ",";
							hiddenValue += selDiv.find("input").val() + ",";
						});
					}
				}
				
				if (shownText && hiddenValue) {
	   				window.parent.$("#" + $("#colName").val()).val(shownText.substring(0, shownText.length - 1));
	   				window.parent.$("#" + $("#colName").val() + "_CUSTOM_VALUE").val(hiddenValue.substring(0, hiddenValue.length - 1));
	   				window.parent.JDialog.close();
				}
			}
		function chooseType(){
			var type = $("#selectType").val();
			if(type == '1'){
				var sql = "status = '1' and branch_type = '1' ";
				$("#dialogConditions").val(sql);
			}else if(type == '3'){
				var sql = "( status = '1' and branch_type = '2' ) or ( status = '1' and branch_type = '1' and rank = '0' )";
				$("#dialogConditions").val(sql);
			}
			$("#customGrid").jqGrid("setGridParam", { url: loadUrl() }).trigger("reloadGrid");
		}	
		function confirm(){
			   if(rowData == null){
				   return;
			   }else{
				   window.parent.$("#branchValue").text(rowData[cols[1]]);
					window.parent.$("#branchId").val(rowData[cols[0]]);
					window.parent.$("#home-banner").show();
					window.parent.JDialog.close();
			   }
				
		}
		function cancel(){
			window.parent.$("#home-banner").show();
			window.parent.JDialog.close();
		}
		</script>
	</body>
</html>
