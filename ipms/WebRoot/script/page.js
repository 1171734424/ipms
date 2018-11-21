var page_id, model_id, page_conditions, grid_name, chart_type, query_url, query_flag, editFlag, updateFlag; 
var load_flag, last_row, last_cell, required_data, key_config, checked_row, data_changed, id_name, sequenceSource; 
var chartWidth, save_tag, update_tag, delete_tag, validate_tag, custom_dialog, condLeng, formId;
var gridWidth, gridHeight, firstEditCell, firstShownCell, focusTarget, fileUpload, colFrozen;

$(document).ready(function() {
	$(function($) {
		$(document).mousedown(function (e) {
			e = e || event;
			focusTarget = $(e.target)[0].tagName;
		});
		
		$(document).keydown(function(e) {
	   		hideMenus();
			var currKey = 0, e = e || event;
			currKey = e.keyCode || e.which || e.charCode;
			switch(currKey) {
				case 13: //ENTER
					e.preventDefault(); 
					break;
				case 65: //ALT+A
					if (e.altKey && editFlag) {
						e.preventDefault(); 
						multiSelect();
					}
					break;
				case 68: //ALT+D
				case 69: //ALT+E
					if (e.altKey && editFlag) {
						e.preventDefault(); 
						deleteRow();
					}
					break;
				case 70: //ALT+F
					if (e.altKey) {
						e.preventDefault(); 
						queryPageData();
					}
					break;
				case 78: //ALT+N
				case 81: //ALT+Q
					if (e.altKey && editFlag) {
						e.preventDefault(); 
						addNewRow();
					}
					break;
				case 82: //ALT+R
					if (e.altKey) {
						e.preventDefault(); 
						location.reload();
					}
					break;
				case 83: //ALT+S
					if (e.altKey && editFlag && page_config.editing == "line") {
						e.preventDefault(); 
						saveData(false, true);
					}
					break;
				default: break;
			}
		 });
	});
	
	key_config = "";
	sequenceSource = "";
	save_tag = "SAVE";
	update_tag = "UPDATE";
	delete_tag = "DELETE";
	validate_tag = "VALIDATE";
	firstEditCell = -1;
	firstShownCell = -1;
	
	grid_name = "#dataGrid";
	model_id = $("#modelId").val();
	page_id = $("#pageId").val();
	data_changed = new Array();
	required_data = new Object();
	checked_row = new Array();
	id_name = new Array();
	custom_dialog = new Array();
	
	editFlag = page_config.editing == 'other' || (page_config.functions 
			&& (page_config.functions.EDIT || page_config.functions.ADD
			|| page_config.functions.DELETE || page_config.functions.SELECT));
	updateFlag = page_config.editing == 'other' || (page_config.functions 
			&& (page_config.functions.EDIT || page_config.functions.UPDATE));
	
	loadDatePicker();
	adjustTemplateCss();
	
	if (page_config.loadOnRender) {
		loadConditions();
	}

	if (!page_config.showType || page_config.showType == "grid") {
		renderGrid();
	} else if(page_config.showType == "list") {
		renderList();
	}
});

onerror = handleError;
function handleError(msg, url, length) {
	showMsg(msg);
}

$.fn.serializeObject = function() {
   var obj = {}, objName;
   var arr = this.serializeArray();
   $.each(arr, function() {
	   objName = this.name.toUpperCase();
       if (obj[objName]) {
           if (!obj[objName].push) {
        	   obj[objName] = [obj[objName]];
           }
           obj[objName].push(this.value || '');
       } else {
    	   obj[objName] = this.value || '';
       }
   });
   return obj;
};

function adjustTemplateCss() {
	if (!page_config.conditions && !page_config.tab) {
		$(".reportform_data").css("height", "100%");
	}
	
	if (page_config.funcCondRate) {
		var funcWidth = page_config.funcCondRate.split(",")[0];
		var condWidth = page_config.funcCondRate.split(",")[1];

		$(".reportform_function").css("width", funcWidth + "%")
		$(".reportform_condition").css("width", condWidth + "%");
	}
}

function queryPageData() {

	loadConditions();
	if (query_flag) {
		if (page_config.queryFunc) {
			callCustomFunc(page_config.queryFunc);
		} else {
			if (!page_config.showType || page_config.showType == "grid") {
				$(grid_name).jqGrid("setGridParam", { url: query_url }).trigger("reloadGrid");
			} else if(page_config.showType == "list") {
				$(grid_name).zList("loadList", query_url);
			}
		}
	}
	
	if (page_config.afterQuery) {
		callCustomFunc(page_config.afterQuery);
	}
}

function loadDatePicker() {
	var conditions = page_config.conditions, condition, el;
	if (conditions) {
		var isFirstDay;
		var lastMonthTodays;
		$.each(conditions, function(index) {
			condition = conditions[index];
			if ($("#cond_" + condition).is("input") && (/(DATE|MONTH|YEAR|TIME)$/i.test(condition))) {
				el = /YEAR$/i.test(condition) ? "yy" : /MONTH$/i.test(condition) ? "yy/mm" : "yy/mm/dd"
				$("#cond_" + condition).datepicker({
					dateFormat: el, 
					changeMonth: true,///MONTH/i.test(condition), 
					changeYear: true///YEAR$/i.test(condition) 
				});
				isFirstDay = $("#cond_" + condition).attr("defaultValue") && $("#cond_" + condition).attr("defaultValue") == "First";
				if(condition.indexOf("LASTM") != -1){
					newdate = lastMonthToday();
				} else {
					newdate = isFirstDay ? new Date((new Date()).setUTCDate(1)) : new Date();
				}
				$("#cond_" + condition).datepicker("setDate", newdate);
			}
		});
		$("#ui-datepicker-div").css('font-size','1em');
	}
}

function loadConditions() {
	condLeng = 0;
	page_conditions = "";
	var conditions = page_config.conditions, condition;
	if (conditions) {
		$.each(conditions, function(index) {
			condition = conditions[index];
			var val, obj_id = "#cond_" + condition;
			if ($(obj_id).is("select")) {
				val = $(obj_id + " option:selected").val();
			} else {
				val = $(obj_id).val();
			}
			
			if ($("#cond_" + condition).is("input") && (/(DATE|MONTH|YEAR|TIME)$/i.test(condition)) && !val) {
				query_flag = false;
				showMsg("请选择日期!");
				return false;
			}
			
			page_conditions += condition + ":" + val  + ",";
			query_flag = true;
			condLeng += 1;
		});
	}
	
	if (initParams) {
		$.each(initParams, function(index) {
			page_conditions += index + ":" + initParams[index]  + ",";
		});
	}
	
	if (page_conditions) 
		page_conditions = encodeURIComponent(page_conditions);
	query_flag = conditions ? query_flag : true;
	query_url = !page_config.type ? "" : base_path + "/queryPageData.do?modelId=" + model_id 
		+ (page_id ? "&pageId=" + page_id : "") + (page_conditions ? "&conditions=" + page_conditions : "")
		+ "&queryFlag=" + query_flag;
}

function loadChart(chart_type) {
	if (!page_config.charts) return;
	if ($(".chart_outter").is(":hidden")) {
		$(".chart_outter").show();
	}
	
	$("body").mousedown(function () {
		var e = e || event;
		if (/^(div|table|td)/i.test($(e.target)[0].tagName) && !$(".chart_outter").is(":hidden")) {
			$(".chart_outter").hide();
		}
	});
	/*chart_type = $("input[name=chartType]:checked").val();
	if (!chart_type) {
		$("input[name=chartType]").eq(0).prop("checked", true);
		chart_type = $("input[name=chartType]").eq(0).val();
	}*/
	
	$.ajax({
		url: base_path + "/queryPageChart.do",
		type: "post",
		dataType: "json",
		data: { modelId: model_id, pageId : page_id, conditions: page_conditions, queryFlag: query_flag },
		success: function(json) {
			if (json) {
				var data = new Array(), 
					row, 
					dt;
				$.each(json, function(index) {
					row = json[index];
					dt = { label: row.label, value: row.value };
					if (page_config.chartDataEvent) {
						dt.link = "JavaScript:callCustomFunc('" + page_config.chartDataEvent + "', '" + row.value + "');";
					}
					data.push(dt);
				});
				
				var latitudes = page_config.charts.latitude.split(":");
				
				FusionCharts.ready(function() {
					var fChart = new FusionCharts({
						"type": chart_type,
						"renderAt": "chart_inner",
						"width": screen.availWidth * 0.4,
						"height":  screen.availHeight * 0.5,
						"dataFormat": "json",
						//"clickURL": "JavaScript:callCustomFunc('" + page_config.chartDataEvent + "');",
						"dataSource": {
							"chart": {
								"caption": page_config.charts.caption || "统计图表",
								"subCaption": page_config.charts.subCaption || "",
						        "xAxisName": getColumnConfig(latitudes[0]).name,
						        "yAxisName": getColumnConfig(latitudes[1]).name,
								//"rotateYAxisName": "0", //Y轴标题横向展示
						        "labelDisplay": "wrap",
								"baseFont": "Microsoft YaHei",
								"useRoundEdges": 0,
								"wmode": "Opaque",
								"bgcolor": '#d7ebf9',
								//"bgAlpha": 0.5,
								//"showLegend": 1,
								//"renderAs": "Area",
								//"legendPosition": "BOTTOM",
								"theme": "fint",
								"palette": 2
							},
							/*"colorrange": {
				                "minvalue": "0",
				                "startlabel": "Low",
				                "endlabel": "High",
				                "code": "#e44a00",
				                "gradient": "1",
				                "color": [
				                    {
				                        "maxvalue": "56580",
				                        "displayvalue": "Average",
				                        "code": "#f8bd19"
				                    },
				                    {
				                        "maxvalue": "100000",
				                        "code": "#6baa01"
				                    }
				                ]
				            },*/
							"data": data
						}
					});

				    fChart.render();
				});

				try{
					$(".chart_outter").focus();
				} catch (ex){
				}
			}
		},
		error: function(json) {
		}
	});
}

function calcGridBorder() {
	if (page_config.gridSquare) {
		gridWidth = page_config.gridSquare.split(",")[0];
		gridHeight = page_config.gridSquare.split(",")[1];
	} else {
		gridWidth = $("form").outerWidth() - 2;

		var outterHeigth = window.top.$("#dataZone").height();
		var tabId = window.top.currentTabId;
		var tabHeight = 0;
		if (page_config.tab) {
			tabHeight = $(window.top.frames["contentFrame_" + tabId].document).find("#gridul").height();
		}
		
		var minusHeight = page_config.functions ? 40 : 0;
		minusHeight += page_config.tab ? tabHeight : -1;
		minusHeight += page_config.sumCols ? 22 : 0;
		//minusHeight += page_config.paramUrl ? 0 : 0;
		
		gridHeight = outterHeigth - minusHeight - 75;
	}
}

function renderList() {
	calcGridBorder();
	
	$(grid_name).zList({
		url: query_url,
	   	attrModel: setGridModel(),
	   	width: gridWidth,
	   	height: gridHeight,
		rowNum: 20,
		pager: "#pager"
	});
	
	if (page_config.loadOnRender) {
		$(grid_name).zList("loadList", query_url);
	}
}

function renderGrid() {
	calcGridBorder();
	
	$(grid_name).jqGrid({
		url: page_config.loadOnRender ? query_url : "",
		datatype: "json",
	   	mtype: "POST",
	   	colNames: setGridCol(),
	   	colModel: setGridModel(),
	   	width: gridWidth,
	   	height: gridHeight,
	   	userDataOnFooter: false,
		footerrow : page_config.sumCols,
	   	rowNum: page_config.rowNum ? page_config.rowNum : 20,
	   	rowList: [20, 50, 100],
	   	pager: "#pager",
	   	forceFit: true,
	   	shrinkToFit: !page_config.noShrink,
	   	autoScroll: page_config.noShrink,
	   	cellEdit: page_config.editing && page_config.editing != "form",
	   	cellsubmit: "clientArray",
	    viewrecords: true,
	    altRows: true,
		altclass: "ui-state-focus",
	   	//sortname: "id",
	    //sortorder: "desc",
	   	//autowidth: true,
	   	//multiselect: editFlag,
		//caption: page_config.name,
	    loadComplete: function(data) {
			if (page_config.sumCols) {
				var rnum = $(grid_name).jqGrid("getGridParam", "records");
				if (rnum > 0) {
					var scols = page_config.sumCols.split(",");
					var sums = new Object();
					sums[page_config.columns[firstShownCell].columnId] = "合计";
					
					$.each(scols, function(index) {
						sums[scols[index]] = Math.round($(grid_name).getCol(scols[index], false, "sum") * 100) / 100;
					});
					$(".ui-jqgrid-sdiv").show();
					$(grid_name).footerData("set", sums);
				}
			}
			
			if (editFlag && $(grid_name)[0].rows.length == 1 && page_config.editing == "line") {
				addNewRowInLineEditing();
			}
			
			if (page_config.afterLoad) {
				callCustomFunc(page_config.afterLoad);
			}
		},
		afterSaveCell: function (rowid, celname, value, iRow, iCol) {
	   		if(data_changed.indexOf(iRow) == -1 && iRow !== 0) {
	   			data_changed.push(iRow);
	   		}
	   	},
	   	beforeEditCell: function (rowid, cellname, value, iRow, iCol) {
	   		selectCell(iRow, iCol);
	   	},
	   	onRightClickRow: function(rowid, iRow, iCol, ex) {
	   		if (page_config.showMenu && editFlag && page_config.editing != "line" && iCol != -1) {
		   		hideMenus();
		   		selectCell(iRow, iCol);
	   			
		   		var e = e || event;
				var X = e.clientX, Y = e.clientY;
				
		   		var el = "<ul class='gridMenu' onRightClick='return false;'>"
		   			   + "<li onclick='hideMenus();editData(\"" + rowid + "\")'>"
		   			   + "<div><span class='ui-icon ui-icon-pencil'></span></div>编辑</li>"
		   			   + "<li onclick='hideMenus();deleteSingleRow(\"" + rowid + "\")'>"
		   			   + "<div><span class='ui-icon ui-icon-close'></span></div>删除</li></ul>";
		   		
		   		var target = $("tr[id=" + rowid + "]");
		   		$(el).css({ "left": X, "top": Y - 85 }).menu().appendTo(target);
		   	}
	   	},
	   	ondblClickRow: function (rowid, iRow, iCol, ex) {
	   		if (updateFlag) {
		   		editData(rowid);
			}
	   	},
	   	onCellSelect: function (rowid, iCol, cellcontent, ex) {
	   		if (editFlag) {
		   		hideMenus();
	   			selectCell(rowid, iCol);
	   			
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
	   	}
	});
	
	if (!editFlag && colFrozen) {
		$(grid_name).jqGrid("setFrozenColumns");
	}
	
	$(grid_name).navGrid("#pager", {
		refresh: false,
		refreshtext: "刷新",
		edit: false,
		add: false,
		del: false,
		search: false,
		position: "left"
	});
}

function setGridCol() {
	var title_arr = new Array();
	var page_columns = page_config.columns;
	if (editFlag) {
		title_arr.push("ROW_NO");
		title_arr.push("<input id='multiCBX' type='checkbox' onclick='multiSelect(this)' style='display:none;'/>");
	}
	$.each(page_columns, function(index) {
		title_arr.push(page_columns[index].name);
		var column = page_columns[index];
		if (column.primaryKey || column.complexKey) {
			id_name.push(column.columnId);
			if (column.complexKey) {
				if (key_config.indexOf(column.complexKey) != -1) {
					keys = key_config.split(",");
					latest_keyStr = keys[keys.length - 1];
					replace_keyStr = latest_keyStr.substring(0, latest_keyStr.length - 1) + ":" + column.columnId;
					key_config = key_config.replace(latest_keyStr, replace_keyStr);
				} else {
					key_config += column.complexKey + "("+ column.columnId;
				}
			} else {
				key_config += (key_config ? "," : "") + column.columnId;
			}
			
			if (column.sequence) {
				key_config += "|" + column.sequence;
				if (column.seqPrefix) {
					key_config += "|" + column.seqPrefix;
				}
			}
			
			if (column.complexKey) {
				key_config += ")"
			}
			
			if (column.sequenceSource) {
				sequenceSource = column.sequenceSource;
			}
		}
	});
	return title_arr;
}

function setGridModel() {
	var col_arr = new Array();
	var page_columns = page_config.columns;
	if (editFlag) {
		col_arr.push({ name: "id", index: "id", hidden: true, frozen: true });
		col_arr.push({ name: "CHECK", index: "CHECK", width: 30, align: "center", frozen: true, editable: false, edittype: "checkbox",
			formatter: function cboxFormatter(cellvalue, options, rowObject) {
				  return '<div class="checkbox"><input id="gcbox' + options.rowId + '" name="gcbox" type="checkbox" value="' + options.rowId + '" ' 
				  	+ 'style="display:none;" onclick="onChecked(' + options.rowId + ')"/><label for="gcbox' + options.rowId + '"></label></div>';
			}
		});
	}
	
	$.each(page_columns, function(index) {
		var column = page_columns[index];
		if (column.editable && !column.hidden && firstEditCell < 0) {
			firstEditCell = index + 1;
		}
		if (!column.hidden && firstShownCell < 0) {
			firstShownCell = index;
		}
		
		if (!colFrozen && column.frozen) {
			colFrozen = true;
		}
		var col = { name: column.columnId, index: column.columnId, text: column.name, width: column.width || 120, frozen: column.frozen, initValue: column.initValue,
				align: column.align || "center", editable: column.editable, hidden: column.hidden, sortable: column.sortable, readonly: column.readonly,
				edithidden: column.edithidden, threshold: column.threshold, thresholdParam: column.thresholdParam, cellattr: addCellAttr ,cellcallFunc: column.cellButtonFunc};
		if (column.editType && column.editType != "none") {
			if (!/^(button|dialog|funcbutton)$/i.test(column.editType)) {
				col.formatter = column.editType;
				col.formatoptions = { disabled: !column.editable };
			}
			var value = !column.editValue ? page_config.requiredData ? page_config.requiredData[column.value ? column.value : column.columnId] 
					: "" : column.editValue;
			if (column.editType == "select") {
				col.edittype = column.editType;
				if(column.initialSelectData){
					value = page_config.requiredData[column.initialSelectData]; 
				}
				col.editoptions = { value: value || { "" : "" }, dataInit: function(element) {
					if (page_config.editing && page_config.editing != "form") {
				        $(element).width(column.width);
					}
			    }};

				if (column.dataEvent) {
					var event = column.dataEvent.split(":")[0], changeTarget = column.dataEvent.split(":")[1];
					col.editoptions["dataEvents"] = [{
						type: "change",
						fn: function(e) {
							var val = this.value;
							$.ajax({
								url: base_path + "/queryMultiData.do",
								type: "post",
								dataType: "json",
								data: { modelId: model_id, pageId: page_id, param: column.eventRule, value: val },
								success: function(json) {
									if (json) {
										if (event == "AUTOFILL") {
											var data = json.data;
											var targets = changeTarget.split(",");
											$.each(targets, function (index) {
												var v = data[targets[index]];
												if (page_config.editing && page_config.editing != "form") {
													$(grid_name).jqGrid("setCell", last_row, targets[index], v, false, false, true);
												} else {
													$("#" + targets[index]).val(v);
												}
											});
										} else if (event == "MULTI") {
											try {
												var data = json.data.split(";");
												var options = new Object(), opt = "";
												$.each(data, function (index) {
													var k = data[index].split(":")[1], v = data[index].split(":")[0];
													opt += "<option value='" + v + "'>" + k + "</option>;";
													options[k] = v;
												});
												if (page_config.editing && page_config.editing != "form") {
													$(grid_name).jqGrid("getCell", last_row, changeTarget).append(opt.substring(0, opt.length - 1));
												} else {
													$("#" + changeTarget).empty();
													$("#" + changeTarget).append(opt.substring(0, opt.length - 1));
												}
												required_data[changeTarget] = options;
											} catch(ex) {
												if (page_config.editing && page_config.editing != "form") {
													$(grid_name).jqGrid("getCell", last_row, changeTarget).append("");
												} else {
													$("#" + changeTarget).empty();
												}
											}
										}
									}
								}
							});
						}
                   }];
                }
				if (value) {
					var vals = value.split(";");
					var options = new Object();
					$.each(vals, function(index) {
						options[vals[index].split(":")[0]] = vals[index].split(":")[1];
					});
					col.formatter = function (v, opt, rec) {
						return options[v] || "";
					};
					col.rqval = column.value || column.columnId;
					required_data[col.rqval] = options;
				}
			} else if (column.editType == "checkbox") {
				col.edittype = column.editType;
				if (value) {
					col.editoptions = { value: value };
				}
			} else if (column.editType == "date") {
				col.formatter = function (v, c) {
					if (v && typeof v == "object") {
						var year = v.year + "";
						year = year.length > 3 ? year.substring(2, 4) : year.substring(1, 3);
						var date = v.date || v.day;
						date = date < 10 ? "0" + date : date;
						return "20" + year + "/" + (v.month + 1) + "/" + date;
					} else {
						return v;
					}
				};
				
				col.formatoptions = { 
					srcformat: "Y/m/d",
					newformat: "Y/m/d" 
				};
				col.editoptions = { 
					dataInit: function(element) {
						$(element).datepicker({
							dateFormat: "yy/mm/dd",
							changeMonth: true, 
							changeYear: true
						});
						//$(element).datepicker("setDate", new Date());
						$("#ui-datepicker-div").css('font-size','0.8em');
					}
				};
				
				if(value) {
					col.defValue = value;
				}
			} else if (column.editType == "datetime") {
				col.formatter = function (v, c) {
					if (v && typeof v == "object") {
						var year = v.year + "";
						year = year.length > 3 ? year.substring(2, 4) : year.substring(1, 3);
						var date = v.date || v.day;
						var minutes, seconds;
						date = date < 10 ? "0" + date : date;
						minutes = v.minutes < 10 ? "0" + v.minutes : v.minutes;
						seconds = v.seconds < 10 ? "0" + v.seconds : v.seconds;
						return "20" + year + "/" + (v.month + 1) + "/" + date 
							+ " " + v.hours + ":" + minutes + ":" + seconds;
					} else {
						return v;
					}
				};
				
				col.formatoptions = { 
					srcformat: "Y-m-d H:i:s",
					newformat: "Y-m-d H:i:s" 
				};
				col.editoptions = { 
					dataInit: function(element) {
						$(element).datepicker({
							dateFormat: "yy/mm/dd",
							changeMonth: true, 
							changeYear: true
						});
						//$(element).datepicker("setDate", new Date());
						$("#ui-datepicker-div").css('font-size','0.8em');
					}
				};
			} else if (column.editType == "textarea") {
				col.edittype = column.editType;
				col.editoptions = { 
					value: value || { "" : "" }, 
					dataInit: function(element) {
						if (page_config.editing && page_config.editing != "form") {
					        $(element).width(column.width);
						}
					}
				};
			} else if (column.editType == "password") {
				col.edittype = column.editType;
			} else if (column.editType == "button") {
				col.editable = false;
				var funcName = column.funcName, callFunc = column.callFunc, buttonCls = column.buttonCls;
				col.formatter = function (v, c) {
					c = c ? c.rowId : "";
					return "<a class='b_button'><span class='" + buttonCls + "' onclick='" + callFunc+ "(" + c + ")'>" + funcName + "</span></a>";
				};
			}  else if (column.editType == "funcbutton") {
				col.editable = false;
				col.formatter = function (v, c) {
					return callCustomFunc(column.callFunc, v);
				};
			} else if (column.editType == "link") {
				col.editable = false;
				var funcName = column.funcName, callFunc = column.callFunc;
				col.formatter = function (v, c) {
					c = c ? c.rowId : "";
					return "<a class='b_link'><span onclick='" + callFunc+ "(" + c + ")'>" + funcName + "</span></a>";
				};
			} else if (/^dialog/i.test(column.editType)) {
				if (page_config.editing && page_config.editing != "form") {
					if (column.dialogCustom) { 
						col.editoptions = { 
							custom: true, 
							click: function () {
								callCustomFunc(column.dialogCustom, column.columnId);
							}
						};
					}
				}
				
				var options = new Object();
				if (value) {
					var vals = value.split(";");
					$.each(vals, function(index) {
						options[vals[index].split(":")[0]] = vals[index].split(":")[1];
					});
				}
				col.formatter = function (v, opt, rec) {
					return options[v] || "";
				};
				col.rqval = column.columnId;
				required_data[col.rqval] = options;
				col.customtype = column.editType;
				
				if (column.dataEvent) {
					col.customFunc = column.dialogCustom ? column.dialogCustom : "callLinkageDialog";
					col.dataEvent = column.dataEvent;
				} else {
					col.customFunc = column.dialogCustom ? column.dialogCustom : "callCustomDialog";
				}
				custom_dialog.push(col);
			} else if (column.editType == "image") {
				col.editable = false;
				col.formatter = function (v) {
					if (!v) {
						v = "./images/shortcut.png";
					} else {
						v = "./upload/" + (page_config.uploadFileDir ? page_config.uploadFileDir + "/" + v : v);
					}
					return "<img src='" + v + "' style='cursor:pointer;' height='35' width='35' " +
							"onclick='showImageScale(\"" + v + "\")'/>";
				};
			} else if (column.editType == "file") {
				if (!fileUpload) {
					fileUpload = new Object();
				}
				fileUpload[col.name] = "";
				
				col.edittype = column.editType;
				col.editoptions = { enctype: "multipart/form-data" };
				col.useDefaultFileName = column.useDefaultFileName;
				if (column.showImage) {
					col.formatter = function (v) {
						if (!v) {
							v = "./images/shortcut.png";
						} else {
							
							v = "./upload/" + (page_config.uploadFileDir ? page_config.uploadFileDir + "/" + v : v);
							
						}
						return "<img src='" + v + "' style='cursor:pointer;' height='35' width='35' " +
								"onclick='showImageScale(\"" + v + "\")'/>";
					};
				}
			} else if (column.editType == "cityPicture") {
				if (!fileUpload) {
					fileUpload = new Object();
				}
				fileUpload[col.name] = "";
				
				col.edittype = column.editType;
				col.editoptions = { enctype: "multipart/form-data" };
				col.useDefaultFileName = column.useDefaultFileName;
				if (column.showImage) {
					col.formatter = function (v) {
						if (!v) {
							v = "./images/shortcut.png";
						} else {
							v = (page_config.uploadFileDir ? page_config.uploadFileDir + "/" + v : v);
						}
						return "<img src='" + v + "' style='cursor:pointer;' height='35' width='35' " +
								"onclick='showImageScale(\"" + v + "\")'/>";
					};
				}
			} else if (column.editType == "number") {
				col.formatter = function (v) {
					if (v) {
						if (typeof v == "number") {
							v = String(v);
						}
						
						if (v.indexOf(".") == -1) {
							v = v + ".00"
						} else {
							if(v.split(".")[1].length == 1) {
								v = v + "0";
							}
						}
					} else {
						v = "0.00";
					}
					
					return v;
				};
			}
		}
		
		if (column.customCallback) {
			col.customCallback = column.customCallback;
		}
		
		if(column.editable && column.validateRule) {
			var validateRule = column.validateRule.split(",");
			var editrules = new Array();
			$.each(validateRule, function(index) {
				if (/^NOTNULL$/i.test(validateRule[index]))
					editrules["required"] = true;
				else if (/^NUMBER$/i.test(validateRule[index]))
					editrules["number"] = true;
				else if (/^EMAIL$/i.test(validateRule[index]))
					editrules["email"] = true;
				else if (/^DATE$/i.test(validateRule[index]))
					editrules["date"] = true;
				else if (/^TIME$/i.test(validateRule[index]))
					editrules["time"] = true;
				else if (/^MAX|MIN/i.test(validateRule[index]))
					editrules[validateRule[index].split(":")[0].toLowerCase()] = validateRule[index].split(":")[1];
				else {
					editrules["custom"] = true;
					if (/^IP$/i.test(validateRule[index])) {
						editrules["custom_func"] = function (value, colname) {
							var flag = /\b(([01]?\d?\d|2[0-4]\d|25[0-5])\.){3}([01]?\d?\d|2[0-4]\d|25[0-5])\b/i.test(value);
							return [flag, flag ? "" : colname + "地址无效!"];
						};
					} else if (/^LENGTH/i.test(validateRule[index])) {
						editrules["custom_func"] = function (value, colname) {
							var flag = validateRule[index].split(":")[1] >= value.length;
							return [flag, flag ? "" : colname + "超出" + validateRule[index].split(":")[1] + "位限定长度!"];
						};
					} else if (/^MOBILE$/i.test(validateRule[index])) {
						editrules["custom_func"] = function (value, colname) {
							var flag = /^((13|15|17|18)+\d{9})$/i.test(value);
							return [flag, flag ? "" : colname + "无效手机号码!"];
						};
					} else if (/^IDCARD$/i.test(validateRule[index])) {
						editrules["custom_func"] = function (value, colname) {
							var flag = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[x])$)$/i.test(value);
							return [flag, flag ? "" : colname + "无效身份证号码!"];
						};
					} else if (/^INTEGER$/i.test(validateRule[index])) {
						editrules["custom_func"] = function (value, colname) {
							var flag = /^[1-9]\d*$/i.test(value) && 3 >= value.length && value <=100;
							return [flag, flag ? "" : colname + "填写小于100的正整数!"];
						};
					} else if (/^UNIQUE$/i.test(validateRule[index])) {
						var ctFunc = editrules["custom_func"];
						editrules["custom_func"] = function (value, colname) {
							if (ctFunc) {
								var rs = ctFunc(value, colname);
								if (!rs[0]) {
									return rs;
								}
							}
							
							var idInfo = "";
							if (formId && formId != "new") {
								var rowData = $(grid_name).jqGrid("getRowData", last_row);

								$.each(id_name, function(index) {
									idInfo += id_name[index] + ":" + rowData[id_name[index]] + "|";
								});
							}
							
							if (column.editType == "dialog" ||column.editType == "dialog-radio" ) {
								value = $("#" + column.columnId + "_CUSTOM_VALUE").val();
							}
							
							var flag;
							$.ajax({
								url: base_path + "/isUnique.do",
								type: "post",
								dataType: "json",
								async: false,
								data: { target: page_config.target, colName: column.columnId, colValue: value, idInfo: idInfo },
								success: function(json) {
									flag = json.result;
								},
								error: function(json) {
								}
							});
							return [flag, flag ? "" : colname + "违反唯一性,该数据已存在!"];
						}
					} else if (/^GOODSTYPE$/i.test(validateRule[index])) {
						var ctFunc = editrules["custom_func"];
						editrules["custom_func"] = function (value, colname) {
							if (ctFunc) {
								var rs = ctFunc(value, colname);
								if (!rs[0]) {
									return rs;
								}
							}
							
							var idInfo = "";
							if (formId && formId != "new") {
								var rowData = $(grid_name).jqGrid("getRowData", last_row);

								$.each(id_name, function(index) {
									idInfo += id_name[index] + ":" + rowData[id_name[index]] + "|";
								});
							}
							
							if (column.editType == "dialog" ||column.editType == "dialog-radio" ) {
								value = $("#" + column.columnId + "_CUSTOM_VALUE").val();
							}
							
							var flag;
							$.ajax({
								url: base_path + "/isUniqueGoodsType.do",
								type: "post",
								dataType: "json",
								async: false,
								data: {colValue: value, idInfo: idInfo},
								success: function(json) {
									flag = json.result;
								},
								error: function(json) {
								}
							});
							return [flag, flag ? "" : colname + "违反唯一性,该数据已存在!"];
						}
					}
				}
			});
			col.editrules = editrules;
		}
		
		col_arr.push(col);
	});
	return col_arr;
}

function callCustomFunc() {

	var func = eval(arguments[0]);
	if (typeof func == "function") {
		return func(arguments[1]);
	}
}

function getColumnConfig(info) {
	var page_columns = page_config.columns, column, column_config;

	$.each(page_columns, function(index) {
		column = page_columns[index];

		if (column.columnId == info || column.name == info) {
			column_config = column;
			return false;
		}
		
	});
	return column_config;
}

function isShrink() {
	return page_config.charts ? page_config.columns.length < 6 : page_config.columns.length < 10;
}

function addCellAttr(rowId, val, rawObject, cm, rdata) {
	if (cm.threshold) {
		var args = cm.threshold.split(":"), comp, threshold;
		comp = args.length > 1 ? args[0] : "LT";
		threshold = args.length > 1 ? args[1] : args[0];
		
		var thresholdVal;
		if (parseInt(threshold)) {
			thresholdVal = threshold;
		} else {
			if (page_config.requiredData) {
				if (page_config.lazy[threshold]) {
					$.ajax({
						url: base_path + "/queryMultiData.do",
						type: "post",
						dataType: "json",
						async: false,
						data: { modelId: model_id, pageId: page_id, param: threshold, value: rdata[cm.thresholdParam] },
						success: function(json) {
							if (json) {
								thresholdVal = json.data["VALUE"];
							}
						}
					});
				} else {
					thresholdVal = page_config.requiredData[threshold];
				}
			}
		}
		
		return getReturnAttr(comp, parseInt(thresholdVal), parseInt(val));
	}
}

function getReturnAttr(comp, thresholdVal, val) {
	if (comp == "LT" ? val <= thresholdVal : val >= thresholdVal) {
		return "style='color:red;font-weight:bold;'";
	}
}

function hideMenus() {
	if ($(".gridMenu") && $(".gridMenu").length > 0) {
		$(".gridMenu").remove();
	}
}

function adjustSelectorCSS(status) {
	$(".b_select").css("backgroundImage", "url(./images/button/" + (status ? "btn_select" : "btn_unselect") + ".png)");	
}

function multiSelect() {
	if (showType == "list") {
		if ($(".list_select").length < $(".list_innderDiv").length) {
			var ck = "<div class='list_select'></div>";
			$(ck).appendTo($(".list_innderDiv").not($(".list_innderDiv").has(".list_select")));
			adjustSelectorCSS(true);
		} else {
			$(".list_select").remove();
			adjustSelectorCSS(false);
		}
		
	} else {
		var ckboxes = $("input[name=gcbox]"), checked = $("#multiCBX").is(":checked");
		
		if (arguments.length == 0) {
			$("#multiCBX").prop("checked", !checked);
			checked = !checked;
		}
		adjustSelectorCSS(checked);	
		
		if (checked) {
			if(!checked_row) checked_row = new Array();
			
			var ckid;
			$.each(ckboxes, function(index) {
				$(ckboxes[index]).prop("checked", checked);
				ckid = $(ckboxes[index]).val();
				if (contains(checked_row, ckid) == -1) {
					checked_row.push(ckid);
				}
			});
		} else {
			checked_row = new Array();
			$.each(ckboxes, function(index) {
				$(ckboxes[index]).prop("checked", checked);
			});
		}
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
	
	var multiChecked = checked_row.length == $("input[name=gcbox]").length;
	if ($("#multiCBX").length > 0) {
		$("#multiCBX").prop("checked", multiChecked);
	}
	adjustSelectorCSS(multiChecked);
}

function showImageScale(src) {
	JDialog.open("查看图片", base_path + "/showImageScale.do?src=" + src, 450, 350, true);
}

function exportFile() {
	if (!query_flag) {
		loadConditions();
		return;
	}
	var option = "height=0,width=0,left=0,top=0,location=no,status=no";
	window.open(base_path + "/exportFile.do?modelId=" + model_id + "&pageId=" + page_id + "&conditions=" + page_conditions, "_self", option);
}

function importFile() {
	JDialog.open("导入", base_path + "/importFile.do?modelId=" + model_id + "&pageId=" + page_id, 400, 150);
}

function importDoorFile(){
	JDialog.open("门锁导入", base_path + "/importDoorFile.do?modelId=" + model_id + "&pageId=" + page_id, 400, 150);
}

function printTemplate() {
	var height = screen.availHeight - 300, width = screen.availWidth - 300;
	var option = "height=" + height + ",width=" + width + ",scroll=0,status=0,location=0";
	window.open(base_path + "/" + page_config.printTemplate + ".do" 
			+ (page_conditions ? "?conditions=" + page_conditions : ""), "_blank", option);
}

function addNewRow() {
	if (!page_config.editing || page_config.editing == "other" || page_config.editing == "form") {
		addNewRowInFormEditing();
	} else {
		addNewRowInLineEditing();
	}
}

function addNewRowInLineEditing() {
	if (last_row && last_cell) {
		$(grid_name).jqGrid("saveCell", last_row, last_cell);
	}
	
	var rows = $(grid_name)[0].rows;
	var lastrow_id = rows.length > 1 ? parseInt(rows[rows.length - 1].id) + 1 : 1;
	
	if (rows.length == 1) {
		$(grid_name).jqGrid("addRowData", lastrow_id, {});
	} else {
		$(grid_name).jqGrid("addRowData", lastrow_id, {}, "after", lastrow_id - 1);
	}
	$(grid_name).jqGrid("setCell", lastrow_id, 0, lastrow_id, false, false, true);
	
	var cm = $(grid_name).jqGrid("getGridParam", "colModel");
	$.each(cm, function(index) {
		if (cm[index].edittype == "select") {
			$(grid_name).jqGrid("setCell", lastrow_id, index,
					cm[index].editoptions.value.split(";")[0].split(":")[0], false, false, true);
		} else if (cm[index].edittype == "checkbox") {
			$(grid_name).jqGrid("setCell", lastrow_id, index, "Yes", false, false, true);
		}
	});
	window.setTimeout(function () {
		$(grid_name).jqGrid("nextCell", lastrow_id, firstEditCell);
	}, 0);
	$(grid_name).scrollTop = $(grid_name).scrollTop + 30;
}

function editData(rowid) {
	if (page_config.readOnly) return;
	if (page_config.editFunc) {
		callCustomFunc(page_config.editFunc, rowid);
	} else {
   		if ((page_config.functions && page_config.functions.EDIT && (!page_config.editing || page_config.editing == "form"))|| page_config.functions.UPDATE) {
   			var editData = new Object();
			var rowData = $(grid_name).jqGrid("getRowData", rowid);
				
   			if (id_name) {
				var cm = $(grid_name).jqGrid("getGridParam", "colModel"), data = "";
				$.each(id_name, function(index) {
					data = rowData[id_name[index]] == "undefined" ? "" : rowData[id_name[index]];
					$.each(cm, function(i) {
						if (cm[i].name == id_name[index]) {
							if (cm[i].edittype && cm[i].edittype == "select") {
								var key = cm[i].rqval || cm[i].name, firstKey;
								$.each(required_data[key], function(k) {
									if(rowData[id_name[index]] == required_data[key][k]) {
										data = k;
										return false;
									}
									if (!firstKey) {
										firstKey = k;
									}
								});
								if (!data) {
									data = firstKey;
								}
							}
							return false;
						}
					});
					editData[id_name[index]] = data;
				});
			}
   			
   			formEditing(rowid, editData);
   		} else {
   			formView(rowid);
   		}
	}
}

function formView(rowid) {
	$(grid_name).jqGrid("viewGridRow", rowid, {
		resize: false,
		top : $("body").outerHeight() / 2 - 220,
		left: $("body").outerWidth() / 2 - 250,
   		width: 500,
   		height: $("body").outerHeight() - 50 > 450 ? 420 : $("body").outerHeight() - 50,
   		datawidth: 500,
   		dataheight: $("body").outerHeight() - 50 > 450 ? 340 : $("body").outerHeight() - 130
	});
}

function formEditing(rowid, editData) {
	formId = rowid || "new";
	var cm = $(grid_name).jqGrid("getGridParam", "colModel");
		rowData = rowid ? $(grid_name).jqGrid("getRowData", rowid) : "";
		
	$.each(cm, function(i) {
		if(cm[i].formatter == "date") {
			if(cm[i].defValue) {
				editData = editData || new Object();
				if(!rowData || !rowData[cm[i].name].trim()) {
					if(cm[i].defValue == "sysdate") {
						var now = new Date();
						editData[cm[i].name] = now.getFullYear() + "/" + now.getMonth() + "/" + now.getDate();
					} else {
						editData[cm[i].name] = cm[i].defValue;
					}
				} else if (rowData && rowData[cm[i].name].trim()) {
					editData[cm[i].name] = rowData[cm[i].name];
				}
			}
		}
	});
	
	var url = page_config.formSubmitUrl || "dealDataInFormEditing";
	$(grid_name).jqGrid("editGridRow", formId, {
   		url: base_path + "/" + url + ".do?modelId=" + model_id + "&pageId=" + page_id + "&dealType=" 
   			+ (rowid ? update_tag : save_tag) + "&keyConfig=" + key_config + "&returnValue=" + quickAddCol + "&sequenceSource=" + sequenceSource ,
   		resize: false,
   		clearAfterAdd: true, //rowid ? false : true,
   		closeAfterAdd: quickAddCol ? true : false,
   		closeAfterEdit: true,
		reloadAfterSubmit : true,
   		checkOnSubmit: false,
   		checkOnUpdate: false,
   		closeOnEscape: true,
   		editData: editData || {},
		savekey: [true, 13],
   		onInitializeForm: function(form, oper) {
   			if (fileUpload) {
   				$(form).attr('enctype','multipart/form-data');
			}
   			
   			if (custom_dialog.length > 0) {
   				var col;
   				$.each(custom_dialog, function(index) {
   					col = custom_dialog[index];
   					var customFunc = col["customFunc"], colName = col["name"], colFunc = col["cellcallFunc"], vl;
   					var dataEvent = col["dataEvent"];
   					$("#" + colName).attr("readonly", true);
   					if (customFunc == "callLinkageDialog") {
	   					$($("#" + colName)).bind("click", function() {
	   						callLinkageDialog(colName, dataEvent);
	   					});
   					} else {
	   					$($("#" + colName)).bind("click", function() {
	   						callCustomFunc(customFunc, colName);
	   					});
   					}
   					
   					$.each(required_data[colName], function(k) {
   						if(rowData[colName] == required_data[colName][k]) {
   							vl = k;
   							return;
   						}
   					});
   					concatEl(col["name"], vl ? vl : "",colFunc);
   					colFunc ="";
   				});
			}
   			
   			var cm = $(grid_name).jqGrid("getGridParam", "colModel");
   			$.each(cm, function(index){
   				if(cm[index].initValue) {
   					//$("#" + cm[index].name).val(setInitValue(cm[index].initValue));
   					setInitValue($("#" + cm[index].name), cm[index].initValue);
   				}
   			});
   		},
   		beforeSubmit: function(rowData, form, oper) {
			var cm = $(grid_name).jqGrid("getGridParam", "colModel"), fileName, checkMsg = "";
			$.each(cm, function(index) {
				if (cm[index].editable) {
					if(/.*[`~!#$%^&*()+=|{}'',<>?~！#￥%……&*（）——+|{}【】‘”“’？].*/i.test(rowData[cm[index].name])) {
						checkMsg = "内容不能包含特殊字符!";
						return false;
					}
					
					if (cm[index].edittype == "password") {
						rowData[cm[index].name] = $.md5(rowData[cm[index].name]);
					} else if (cm[index].edittype == "file") {
						fileName = $("#" + cm[index].name).val();
						fileName = fileName.substring(fileName.lastIndexOf("\\"));
						if (fileName) {
							if (!cm[index].useDefaultFileName) {
								fileName = new Date().getTime() + fileName.substring(fileName.lastIndexOf("."));
							}
							rowData[cm[index].name] = fileName;
							fileUpload[cm[index].name] = fileName;
						}
					}
				}
			});

			return [checkMsg.length == 0, checkMsg, ""];
   		},
   		afterSubmit: !fileUpload ? closeGridDialog : function(response, postData, oper) {
   			$.each(fileUpload, function(index) {
   				if ($("#" + index).val()) {
   					uploadFile(index);
				}
   			});
   			doAfterQuickAdd(response);
			return [true, "", ""];
   		},
   		top : $("body").outerHeight() / 2 - (quickAddCol ? 250 : 230),
		left: $("body").outerWidth() / 2 - (quickAddCol ? 255 : 250),
   		width: quickAddCol ? 515: 500,
   		height: $("body").outerHeight() > 500 ? 420 : $("body").outerHeight() - 50,
   		datawidth: 500,
   		dataheight: $("body").outerHeight() > 500 ? 330 : $("body").outerHeight() - 130
   	});
	if(quickAddCol){
		$("#FrmGrid_dataGrid").css("height", 375);
		$("#FrmGrid_dataGrid").css("width", 503);
	}
	$("#FrmGrid_dataGrid").css("overflow-x", "hidden");
//	$("#TblGrid_dataGrid").effect("slide", { direction: "left" }, 500);
}

function setInitValue(e, tag) {
	//var result = "";
	switch(tag) {
		case "BRANCHNAME":
			getBranch(e, tag);
			break;
		case "BRANCHTYPE":
			getBranch(e, tag);
			break;
	}
	//return result ? result : tag;
}

function closeGridDialog(response, postData, oper) {
	doAfterQuickAdd(response);
	return [true, "", ""];
}

function doAfterQuickAdd(response) {
	if (quickAddCol) {
		var hiddenValue = response.responseText.split(":")[0];
		var shownText = response.responseText.split(":")[1];
		window.parent.$("#" + quickAddCol + "_CUSTOM_VALUE").val(hiddenValue);
		window.parent.$("#" + quickAddCol).val(shownText);
		closeDialog();
	}
}

function clearText(colName){
	var column = colName.id;
	$("#"+ column).val("");
	$("#"+ column + "_CUSTOM_VALUE").val("");
}

function concatEl(colName, value, callFunc) {
	var el = "<tr class='FormData' style='display:none;'><td colspan='2'>" +
		"<input type='text' class='FormElement ui-widget-content ui-corner-all' role='textbox'" +
		" id='" + colName + "_CUSTOM_VALUE' name='" + colName + "_CUSTOM_VALUE'" +
		" value='" + value + "'/></td></tr>";
	$("tbody").append(el);
	if(callFunc != null && callFunc !=""){
		var newel = "<tr><td><a class='b_button' style='float: left; margin-top: -13px;margin-left: 5px;'><span class='button' onclick='" + callFunc+ "(" + colName + ")'>清空 </span></a></td></tr>";
		$("tbody #" + colName).css('width','320px');
		$("tbody #" + colName).css('float','left');
		$("tbody #" + colName).css('margin-left','5px');
		$("tbody #" + colName).closest('.DataTD').append(newel);
	} 	
}

function addNewRowInFormEditing() {
	var func = page_config.addFunc || page_config.editFunc;
	if (func) {
		callCustomFunc(func);
	} else {
		formEditing();
	}
}

function callCustomDialog(colName) {
	var url = base_path + "/customDialog.do?modelId=" + model_id + "&pageId=" + page_id + "&colName=" + colName;
	JDialog.open("查询", url, 450, 350);
}

function callLinkageDialog(colName, dataEvent) {
	var dataevents = dataEvent.split(";")[0];
	var events = dataevents.split(":")[1];
	var event = $(("#") + events + "_CUSTOM_VALUE").val()
	if (!event) {
		showMsg("请先选择"+$($("#"+events).parent().prev()).text());
		return false;
	}
	var url = base_path + "/linkageDialog.do?modelId=" + model_id + "&pageId=" + page_id + "&colName=" 
		+ colName + "&dataEvent=" + dataEvent + "&event=" + event;
	JDialog.open("查询", url, 450, 350);
} 

function deleteSingleRow(rowid) {
	checked_row = new Array();
	checked_row.push(rowid);
	deleteRow();
}

function deleteRow() {
	if (checked_row.length > 0) {
		showMsg("确认要删除这些数据?", deleteRowConfirm);
	}
}

function deleteRowConfirm() {
	if (page_config.delFunc) {
		callCustomFunc(page_config.delFunc);
	} else {
		delGridData();
		
		if (checked_row.length > 0) {
			var args = transData(checked_row);
			if (args && args.length > 0) {
				dealData(JSON.stringify(args), delete_tag, false, true);
			}
		} else {
			$("#multiCBX").prop("checked", false);
			$(".b_select").css("backgroundImage", "url(./images/button/btn_unselect.png)");
		}
	}
}

function showCharts() {
	if (page_config.charts) {
		if (!chartWidth) {
			chartWidth = parseInt($(".reportform_data").css("width")) * 0.27;
		}
		if ($(".right_chart").is(":hidden")) {
			$(".reportform_left").animate({ width: chartWidth });
//			$(".right_chart").effect("slide", { direction: "left" }, 500);
			$(grid_name).setGridWidth(gridWidth);
		} else {
			$(".right_chart").hide();
			$(".reportform_left").css("width", 8);
			$(grid_name).setGridWidth($("form").outerWidth() - 15);
		}
	}
}

function transData(arg, rule, state, col) {
	var breakFlag = false;
	var arr = new Array();
	var cm = $(grid_name).jqGrid("getGridParam", "colModel");
	$.each(arg, function(index) {
		var rowData = $(grid_name).jqGrid("getRowData", arg[index]);
		
		$.each(cm, function(i) {
			if (cm[i].editrules && cm[i].editrules["required"] && !rowData[cm[i].name] && rule == validate_tag) {
				showMsg(cm[i].text + ":此字段必需");
				breakFlag = true;
				return;
			}
			if (cm[i].edittype == "checkbox") {
				rowData[cm[i].name] = rowData[cm[i].name] == "Yes" ? 1 : rowData[cm[i].name] == "No" ? 0 
						: cm[i].name == "CHECK" ? 0 : rowData[cm[i].name];
			} else if (cm[i].edittype == "select" || /^dialog/i.test(cm[i].customtype)) {
				var key = cm[i].rqval || cm[i].name;
				$.each(required_data[key], function(k) {
					if(rowData[cm[i].name] == required_data[key][k]) {
						rowData[cm[i].name] = k;
						return;
					}
				});
			} else if(cm[i].formatter == "date") {
				if(!rowData[cm[i].name].trim()) {
					if(cm[i].defValue) {
						if(cm[i].defValue == "sysdate") {
							var now = new Date();
							rowData[cm[i].name] = now.getFullYear() + "/" + now.getMonth() + "/" + now.getDate();
						} else {
							rowData[cm[i].name] = cm[i].defValue;
						}
					} else {
						rowData[cm[i].name] = "";
					}
				}
			} else if (cm[i].edittype == "password") {
				rowData[cm[i].name] = $.md5(rowData[cm[i].name]);
			}
		});
		
		if (breakFlag) return;
		arr.push(rowData);
	});
	
	if (breakFlag) return;
	else return arr;
}

function callSaveFunc() {
	if (page_config.saveFunc) {
		callCustomFunc(page_config.saveFunc);
	} else {
		saveData(null, true);
	}
}

function uploadFile(elementId) {
    $.ajaxFileUpload({
        url: base_path + "/uploadFile.do",
        secureuri: false,
        fileElementId: elementId,
        dataType: 'json',
        data: { key: elementId, fileName: fileUpload[elementId], dir: page_config.uploadFileDir },
        success: function (data, status) {
        },
        error: function (data, status, ex) {
        	showMsg("上传文件失败！");
        }
    });          
}  

function saveData() {
	$(grid_name).jqGrid("saveCell", last_row, last_cell);
	
	if(data_changed.length == 0) {
	    showMsg("数据未更改!");
		return false;
	}

	var args = transData(data_changed, validate_tag), param = arguments[0], refresh = arguments[1];
	if(args && args.length > 0) {
		showMsg("确认要保存更改?", dealData(JSON.stringify(args), save_tag, param, refresh));
	}
}

function dealData(args, deal_type, param, refresh) {
	var frameParams = "";
	if (page_config.paramUrl) {
		frameParams = $(window.frames["pageParam"].document).find("form").serializeObject();
	}
	$.ajax({
		url: base_path + "/dealData.do",
		type: "post",
		dataType: "json",
		data: { modelId: model_id, pageId: page_id, args: args, keyConfig: key_config, 
			dealType: deal_type, param: param ? encodeURIComponent(JSON.stringify(param)) : "",
			frameParams: encodeURIComponent(JSON.stringify(frameParams)), sequenceSource : sequenceSource},
		success: function(json) {
			if (json.result == 1) {
				if (refresh) {
					$(grid_name).trigger("reloadGrid");
					checked_row = new Array();
				}
				
				if(delete_tag == deal_type) {
					checked_row = new Array();
				} else if (save_tag == deal_type) {
					data_changed = new Array();
				}
				showMsg("操作成功!");
			}
		},
		error: function(json) {
			showMsg("操作失败!");
		}
	});
}

function getRowDataById() {
	return $(grid_name).jqGrid("getRowData", arguments[0]);
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

function refreshGrid() {
	$(grid_name).trigger("reloadGrid");
	checked_row = new Array();
}

function clearGrid() {
	$(grid_name).jqGrid("clearGridData");
	checked_row = new Array();
}

function getRowsCount(delLine) {
	var rows = $(grid_name)[0].rows, id = 1;
	if (rows.length > 1) {
		id = parseInt(rows[rows.length - 1].id);
		if (delLine) {
			var rowData = $(grid_name).jqGrid("getRowData", id);
			if (rowData) {
				$(grid_name).jqGrid("delRowData", id);
			}
		} else {
			id += 1;
		}
	}
	return id;
}

function columnFocus(iRow, iCol) {
	$(grid_name).jqGrid("nextCell", iRow, iCol);
}

function insertLines(datas) {
	var cm = $(grid_name).jqGrid("getGridParam", "colModel");
	var pos = getRowsCount();
	$.each(datas, function(index) {
		if (!datas[index]) {
			showMsg("无相关匹配记录!");
			return false;
		}
		insertLine(datas[index], pos, cm, true);
		pos += 1;
	});
}

function insertLine(data, pos, cm, addLine) {
	if (!data) {
		showMsg("无相关匹配记录!");
		return false;
	}
	if (!cm) {
		cm = $(grid_name).jqGrid("getGridParam", "colModel");
	}
	if (!pos) {
		pos = getRowsCount();
	}
	if (addLine) {
		$(grid_name).jqGrid("addRowData", pos, {});
	}
	
	$.each(cm, function(i) {
		if (cm[i].name != "id" && data[cm[i].name.toUpperCase()]) {
			$(grid_name).jqGrid("setCell", pos, i, data[cm[i].name.toUpperCase()], false, false, true);
		} else if (cm[i].name == "id") {
			$(grid_name).jqGrid("setCell", pos, i, pos, false, false, true);
		}
	});
	if (contains(data_changed, pos) == -1) {
		data_changed.push(pos);
	}
}

function delGridData() {
	$(grid_name).jqGrid("saveCell", last_row, last_cell);
	
	var id, arr = new Array();
	$.each(checked_row, function(index) {
		id = checked_row[index];
		var rowData = $(grid_name).jqGrid("getRowData", id);
		var _val = 1;
		if (rowData) {
			$.each(id_name, function(i) {
				_val = _val & (rowData[id_name[i]] ? 1 : 0);
			});
			if (_val == 0) {
				$(grid_name).jqGrid("delRowData", id);
				arr.push(id);
			}
		}
		var pos = data_changed.length > 0 ? contains(data_changed, id) : -1;
		if (pos != -1) {
			data_changed.splice(pos, 1);
		}
	});
	
	if (arr.length > 0) {
		$.each(arr, function(index) {
			checked_row.splice(checked_row.indexOf(arr[index]), 1);
		});
	}
}

function getParamFrameElementById(elemId) {
	return $(window.frames["pageParam"].document).find("#" + elemId);
}

function setParamFrameElement(data) {
	var eleId, val;
	var elements = $(window.frames["pageParam"].document).find("input, select, textarea");
	
	if (elements.length > 0) {
		elements.each(function(index) {
			eleId = $(elements[index]).attr("id");
			if (eleId) {
				val = data[eleId] || data[eleId.toUpperCase()];
				if (val) {
					$(elements[index]).val(val);
				}
			}
		});
	}
}

function openSubWin(title, modleId, pageId, params, width, height, style, initFunc) {
	var quickAddCol = params ? params.QUICKADD : '';
	if (quickAddCol) {
		delete params.QUICKADD;
	}
	
	var initParams = params ? encodeURIComponent(JSON.stringify(params)) : "";
	var url = base_path + "/queryPageConfig.do?modelId=" + modleId + "&pageId=" + pageId 
		+ "&initParams=" + initParams + "&initFunc=" + initFunc 
		+ "&quickAddCol=" + quickAddCol;
	width = width || screen.availWidth - 240;
	height = height || screen.availHeight - 240;
	
	if (!style || style == "dialog") {
		JDialog.open(title, url, width, height);
	} else if (style == "win") {
		var option = "height=" + height + ",width=" + width + ",scroll=0,status=0,location=0";
		window.open(url, "_blank", option);
	}
}

function concatColumns(columnId) {
	var cols = "", rowData;

	$.each(checked_row, function(index) {
		rowData = $(grid_name).jqGrid("getRowData", checked_row[index]);
		cols += rowData[columnId] + ","
	});
	return cols;
}

function selectCell(iRow, iCol) {
	last_row = iRow;
   	last_cell = iCol;
}

function packageCell(iRow, iCol, value) {
	var cell = new Object();
	cell.rid = iRow;
	cell.cid = iCol;
	cell.content = value;
	return cell;
}

function showErrorMsg(code) {
	var msg;
	switch(code) {
		case 1:
			msg = "error test!";
			break;
			
		default:
			msg = "";
			break;
	}
	showMsg(msg);
}

function testFunc() {
	openSubWin('测试', 'R99914', { "STARTDATE": "" }, false, false, "dialog");
//	showMsg("set text and hidden value 1234");
//	$("#" + arguments[0], document).val("set text value");
//	$("#" + arguments[0] + "_CUSTOM_VALUE", document).val(1234);
}

function lastMonthToday(){
	  var now=new Date();
      var year = now.getFullYear();//getYear()+1900=getFullYear()
      var month = now.getMonth() +1;//0-11表示1-12月
      var day = now.getDate();
      if(parseInt(month)<10){
          month="0"+month;
      }
      if(parseInt(day)<10){
          day="0"+day;
      }

      now =year + '/'+ month + '/' + day;

      if (parseInt(month) ==1) {//如果是1月份，则取上一年的12月份
          return (parseInt(year) - 1) + '/12/' + day;
      }

      var  preSize= new Date(year, parseInt(month)-1, 0).getDate();//上月总天数
      if (preSize < parseInt(day)) {//上月总天数<本月日期，比如3月的30日，在2月中没有30
          return year + '/' + month + '/01';
      }

      if(parseInt(month) <=10){
          return year + '/0' + (parseInt(month)-1) + '/' + day;
      }else{
          return year + '/' + (parseInt(month)-1) + '/' + day;
      }
}
