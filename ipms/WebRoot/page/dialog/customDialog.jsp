<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html style="width: 100%; height: inherit;">
	<head>
		<title></title>
		<%@ include file="../common/css.jsp"%>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
	</head>
	<body oncontextmenu="return true;">
		<div class="table_cond" style="height:50px;line-height: 42px;">
			<ul class="ul">
				<li class="li">
					<div class="dialogInput" id="CDialog" style="width: 258px;">
						<input id="cond_CUSTOMVALUE" type="text" placeholder="编号/名称"/>
					</div>
				</li>	
				<c:if test="${dialogQuickAdd != null}">
					<li>
						<div id="addInEdit" class="dialogButton" onclick="quickAdd('${dialogQuickAdd}');">
							<span>新增</span>
						</div>
					</li>
				</c:if>
				<li class="li">
					<div id="queryInEdit" class="dialogButton" style="background-color:#ff8000;" onclick="queryCustomDialogData();">
						<span>查 询</span>
					</div>
				</li>
				<li class="li">
					<div id="confirmInEdit" class="dialogButton" onclick="doConfirm();">
						<span>确定</span>
					</div>
				</li>
				<!--<li class="li">
					<div id="closeInEdit" class="dialogButton" onclick="window.parent.JDialog.close();">
						<span>关闭</span>
					</div>
				</li>
			--></ul>
		</div>
		<div align="center" >
			<table id="customGrid"></table>
			<div id="pager"></div>
		</div>
		<input type="hidden" name="dialogTarget" id="dialogTarget" value="${dialogTarget}"/>
		<input type="hidden" name="dialogColumns" id="dialogColumns" value="${dialogColumns}"/>
		<input type="hidden" name="dialogConditions" id="dialogConditions" value="${dialogConditions}"/>
		<input type="hidden" name="selectType" id="selectType" value="${selectType}"/>
		<input type="hidden" name="colName" id="colName" value='${colName}'/>
		<input type="hidden" name="dialogQuickAdd" id="dialogQuickAdd" value='${dialogQuickAdd}'/>
		<%@ include file="../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.fmatter.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script><!--
			var selectType = "${selectType}";
			var columns = $("#dialogColumns").val();
			var cols = [ "value", "label" ];
			var checked_row,editTypeTemp, shownText = "", hiddenValue = "";
			
			jQuery(document).ready(function() {
				if (selectType == "dialog" || selectType == "dialog-radio") {
					renderDialogGrid();
				} else if (selectType == "dialog-multi") {
					showCheckList();
					
				}
				
				if($("#dialogQuickAdd").val()){
				  $("#CDialog").css("width","180px");
				}
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
				   /* 	rowList: [10,20,50],
				   	pager: "#pager", */
				   	scroll:true,
				   	scrollrow:true,
				   	forceFit: true,
				   	cellEdit: true,
				   	cellsubmit: "clientArray",
				    viewrecords: true,
				    altRows: true,
					altclass: "ui-state-focus",
					ondblClickRow: function (rowid, iRow, iCol, e) {
		   				var rowData = $("#customGrid").jqGrid("getRowData", rowid);
		   				window.parent.$("#" + $("#colName").val()).val(rowData[cols[1]]);
		   				window.parent.$("#" + $("#colName").val() + "_CUSTOM_VALUE").val(rowData[cols[0]]);
		   				window.parent.JDialog.close();
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
				return "loadCustomDialogData.do?dialogTarget=" + $("#dialogTarget").val()
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
				return ['选择', '编号','名称'];
			}
			
			function setGridModels() {
				if (selectType == "dialog-radio") {
					editTypeTemp = "radio";
				}else if(selectType == "dialog") {
					editTypeTemp = "checkbox";
				}
				return [
					{name: "CHECK", index: "CHECK", width: 30, align: "center", frozen: true, editable: false, edittype: editTypeTemp,
						formatter: function cboxFormatter(cellvalue, options, rowObject) {
							  return '<div class="checkbox"><input id="gcbox' + options.rowId + '" name="gcbox" type="'+editTypeTemp+'" value="' + options.rowId + '" ' 
							  	+ 'style="display:none;" onclick="onChecked(\'' + options.rowId + '\')"/><label for="gcbox' + options.rowId + '"></label></div>';
						}
					},{name: cols[0], index: cols[0], width:110, align:"center" },
				    {name: cols[1], index: cols[1], width:110, align:"center" }
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
			
			function quickAdd(reportId) {
				window.parent.openSubWin('新增', '', reportId, { QUICKADD: $("#colName").val() }, 508, 435, "dialog", "formEditing");
			}
		</script>
	</body>
</html>
