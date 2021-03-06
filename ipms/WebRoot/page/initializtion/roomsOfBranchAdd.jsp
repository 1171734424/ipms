<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/css.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>房间录入</title>
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bwizard.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
		<link href="<%=request.getContextPath()%>/css/initialcss/initialStaff.css" rel="stylesheet" type="text/css" />
		<style>
		.edit-cell.ui-state-highlight select{
		margin:auto;
		width:90%;}
		</style>
	</head>
	<body>
		<div class="well" style="height:540px;">
			<input type="hidden" id="branchId" name="branchId" value="${branchId}"/>
			<input type="hidden" id="branchType" name="branchType" value="${branchType}"/>
			<div style="height: 50px;">
               	<div class="control-group addFloatStyle">
					<div class="controls">
						<input type="button" id="add" name="add" class="required btn btn-primary" value="新增" onclick="addNewRow();">
					</div>
				</div>
				<div class="control-group addFloatStyle">
					<div class="controls">
						<input type="button" id="save" name="delete" class="required btn btn-success" value="保存" onclick="saveData();">
					</div>
				</div>
				<div class="control-group addFloatStyle">
					<div class="controls">
						<input type="button" id="delete" name="delete" class="required btn btn-danger" value="删除" onclick="deleteRow();">
					</div>
				</div>
			</div>
			<form id = "myform" name = "myform">
               	<table id="dataGrid"></table> 
            </form>
		</div>
		<script src="<%=request.getContextPath()%>/script/initialData/jquery.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.fmatter.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
	    	var base_path = "<%=request.getContextPath()%>";
	    	var someNewData = '${someNewData}';
	    	var newDataFlag = '${newDataFlag}';
	    	var branchId = '${branchId}';
	    	var branchType = '${branchType}';
	    	var recordUser = '${recordUser}';
	    	var newObjString = "";
	    	var last_row, last_cell, firstEditCell = -1, checked_row = new Array(), data_changed = new Array(),data_newAdd = new Array(),required_data = new Object(),
	    		delete_tag = "DELETE",save_tag = "SAVE",validate_tag = "VALIDATE"
	    	$(document).ready(function() {
	    		//当数据展示时select选中
	    		//checkbox
	    		var newData = someNewData.split(",");
	    		if(newData != ""){
			    	for(var i=0;i<newData.length;i++){    
				           data_changed.push(newData[i]);
				      }
	    		}
	    		pageInit();
        	});
      
			function pageInit(){
			  var lastsel;
			  jQuery("#dataGrid").jqGrid({
			  	url: "turnToRoomsshow.do?branchType="+branchType+"&branchId="+branchId,
	   			mtype: "POST",
			    datatype : "json",
			    height : '80%',
			    width : '90%',
			    cellEdit:true,
	   			cellsubmit: "clientArray",
	   			shrinkToFit:true, 
			    viewrecords: true,
			    altRows: true,
				altclass: "ui-state-focus",
			    colNames : [ '序号','选择','归属门店','房间号','房间类型','所在楼层','场景','状态','录入人','录入时间'],
			    colModel : [ 
				{name: "id", index: "id", width:65, hidden:true,align: "center"},
			    { name: "CHECK", index: "CHECK", width: 93, align: "center", frozen: true, editable: false, edittype: "checkbox",
					formatter: function cboxFormatter(cellvalue, options, rowObject) {
						  return '<div class="checkbox"><input id="gcbox' + options.rowId + '" name="gcbox" type="checkbox" value="' + options.rowId + '" style="display:none" ' 
						  	+ ' onclick="onChecked(' + options.rowId + ')"/><label for="gcbox' + options.rowId + '" style="margin-left:-18px"></label></div>';
					}
				},
				 			 {name : 'roomKey.branchId',index : 'roomTypeKey.branchId',width : 330 ,align : "center" ,editable:true,edittype:'text',editrules:{required:true}, defvalue:branchId, hidden:true,}, 
			                 {name : 'roomKey.roomId',index : 'roomKey.roomId',width : 335 ,align : "center" ,editable:true,edittype:'text',editoptions: {size:4 , maxlength: 3},editrules:{required:true,custom:true, custom_func:UniqueInDBRoom}}, 
			                 {name : 'roomType',index : 'roomType',width : 335 ,align : "center" ,editable:true,edittype:'select', formatter:'select', editrules:{required:true}, editoptions : {value : gettypes()},editselected:'roomType' },
			                 {name : 'floor',index : 'floor',width : 330 ,align : "center" ,editable:true,editrules:{ required:true,number:true,custom:true,custom_func:trimValue },editoptions: {size:4 , maxlength: 2}}, 
			                 {name : 'theme',index : 'theme',width : 300 ,align : "center" ,hidden:true,editrules:{edithidden:true, required:true},defvalue:branchType}, 
			                 {name : 'status',index : 'status',width : 200 ,align : "center" ,hidden:true,editrules:{edithidden:true, required:true},defvalue:'0'},
			                 {name : 'recordUser',index : 'recordUser',width : 200 ,align : "center" ,hidden:true,editrules:{edithidden:true, required:true},defvalue:recordUser},
			                 {name : 'recordTime',index : 'recordTime',width : 200 ,align : "center" ,hidden:true, edittype:'date',
				                 formatter: function (v, c) {
												if (v && typeof v == "object") {
													var year = v.year + "";
													year = year.length > 3 ? year.substring(2, 4) : year.substring(1, 3);
													var date = v.date || v.day;
													date = date < 10 ? "0" + date : date;
													return "20" + year + "/" + (v.month + 1) + "/" + date + " 00:00:00";
												} else {
													return v;
												}
											},sorttype:"date",defvalue:"sysdate"}
			                 
			     			],
			    loadComplete: function(data) {
			    	
			    },
			    onSelectRow : function(id) {
			      if (id && id !== lastsel) {
			        jQuery('#dataGrid').jqGrid('restoreRow', lastsel);
			        jQuery('#dataGrid').jqGrid('editRow', id, true);
			        lastsel = id;
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
			   	gridComplete :!newDataFlag ? false : function() {
			   		data_newAdd = new Array();
			   		 var ids = jQuery("#dataGrid").jqGrid('getDataIDs');
			   		 for (var i = 0; i < ids.length; i++) {
                        var cl = ids[i];
			   		 	data_newAdd.push(cl);
			    	}
			   }
			  })
			   
			
			  //
			  id_name = new Array();  
			  id_name.push("roomKey.branchId");       
			  id_name.push("roomKey.roomId");     
			  id_name.push("roomType");   
			  id_name.push("floor");
			  id_name.push("theme");
			  id_name.push("status");
			  id_name.push("recordUser");
			  id_name.push("recordTime"); 
			}
			
			
			function selectCell(iRow, iCol) {
				last_row = iRow;
			   	last_cell = iCol;
			}
			
			function addNewRow(){
				if (last_row && last_cell) {
					$("#dataGrid").jqGrid("saveCell", last_row, last_cell);
				}
				var rows = $("#dataGrid")[0].rows;
	
				var lastrow_id = rows.length > 1 ? parseInt(rows[rows.length - 1].id) + 1 : 1;
		
				if (rows.length == 1) {
					$("#dataGrid").jqGrid("addRowData", lastrow_id, {});
				} else {
					$("#dataGrid").jqGrid("addRowData", la strow_id, {}, "after", lastrow_id - 1);
				}
				$("#dataGrid").jqGrid("setCell", lastrow_id, 0, lastrow_id, false, false, true);
				
				var cm = $("#dataGrid").jqGrid("getGridParam", "colModel");
				$.each(cm, function(index) {
					if (cm[index].edittype == "select") {
						$("#dataGrid").jqGrid("setCell", lastrow_id, index,
								cm[index].editoptions.value.split(";")[0].split(":")[0], false, false, true);
					} else if (cm[index].edittype == "checkbox") {
						$("#dataGrid").jqGrid("setCell", lastrow_id, index, "Yes", false, false, true);
					}else if (cm[index].name == "theme" ){
						$("#dataGrid").jqGrid("setCell", lastrow_id, index, branchType, false, false, true);
					}else if (cm[index].name == "status"){
						$("#dataGrid").jqGrid("setCell", lastrow_id, index, "1", false, false, true);
					}else if (cm[index].name == "roomKey.branchId"){
						$("#dataGrid").jqGrid("setCell", lastrow_id, index, branchId, false, false, true);
					}else if (cm[index].name == "recordUser"){
						$("#dataGrid").jqGrid("setCell", lastrow_id, index, recordUser, false, false, true);
					}else if (cm[index].name == "recordTime"){
								var timetemp ="";
								if(cm[index].defvalue) {
									if(cm[index].defvalue == "sysdate") {
										var now = new Date();
										timetemp = now.getFullYear() + "/" + now.getMonth() + "/" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
									} else {
										timetemp = cm[index].defvalue;
									}
								}
						$("#dataGrid").jqGrid("setCell", lastrow_id, index, timetemp, false, false, true);
					}
				});
				window.setTimeout(function () {
					$("#dataGrid").jqGrid("nextCell", lastrow_id + 2, firstEditCell);
				}, 0);
				
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
			
			function hideMenus() {
				if ($(".gridMenu") && $(".gridMenu").length > 0) {
					$(".gridMenu").remove();
				}
			}
			
			function deleteRow() {
				if (checked_row.length > 0) {
					showMsg("确认要删除这些数据?", deleteRowConfirm);
				}
			}
			
			function deleteRowConfirm() {
				delGridData();
				if (checked_row.length > 0) {
					var args = transData(checked_row);
					if (args && args.length > 0) {
						if(!newDataFlag){
							dealData(JSON.stringify(args), delete_tag, false, true);
						}else{
							//alert("进初始化数据的方法")
							delInitialRoomType();
							}
					}
				} else {
					$("#multiCBX").prop("checked", false);
					$(".b_select").css("backgroundImage", "url(./images/button/btn_unselect.png)");
				}
			}
			//初始化加载数据时，删除页面即将加载的list 
			function delInitialRoomType() {
				var id, arrForDel = new Array(), newString ="";
				$.each(checked_row, function(index) {
					id = checked_row[index];
					var rowData = $("#dataGrid").jqGrid("getRowData", id);
					if(rowData){
						newString = arrayTurnToString(rowData["roomKey.roomId"]);
					}
				})
				newString = newString.replace(/roomKey.roomId/g,"roomId");
				newObjString ="";
				//alert("最终要删除的初始化的list:"+newString)
				$.ajax({
						url: base_path + "/turnToRoomsshow.do",
						type: "post",
						dataType: "json",
						data: { branchType:branchType, delArray:newString ,branchId:branchId},
						success: function(json) {
								 $("#dataGrid").html("");
								 
								 if(json != null && json.rows.length > 0) {
								 	for ( var i = 0; i <= json.rows.length; i++){
								    	jQuery("#dataGrid").jqGrid('addRowData', i+1, json.rows[i]);
								    	$("#dataGrid").jqGrid("setCell", i+1, "CHECK",'',{width:30});
								 	}
								 } 
								checked_row = new Array();
								showMsg("操作成功!");
							
						},
						error: function(json) {
							showMsg("操作失败!");
						}
					});	
			}
			
			//房价初始化
			function rpInitialize(){
				$.ajax({
					url: base_path + "/rpInitialize.do",
					type: "post",
					dataType: "json",
					success: function(json) {
						if (json.result == 1) {
							if(json.message){
						     showMsg(json.message);
							}else{
								showMsg("初始化成功");
								window.setTimeout("location.reload(true)", 1800);
							}
							
						} else {
							 showMsg("初始化失败");
							 window.setTimeout("location.reload(true)", 1800);
						}
					},
					error: function(json) {
						showMsg("操作失败！");
						 window.setTimeout("location.reload(true)", 1800);
					}
				});
				
			}

			function delGridData() {
				$("#dataGrid").jqGrid("saveCell", last_row, last_cell);
				var id, arr = new Array();
				$.each(checked_row, function(index) {
					id = checked_row[index];
					var rowData = $("#dataGrid").jqGrid("getRowData", id);
					var _val = 1;
					if (rowData) {
						$.each(id_name, function(i) {
							_val = _val & (rowData[id_name[i]] ? 1 : 0);
						});
						if (_val == 0) {
							$('#dataGrid').jqGrid("delRowData", id);
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
			
			function adjustSelectorCSS(status) {
				$(".b_select").css("backgroundImage", "url(./images/button/" + (status ? "btn_select" : "btn_unselect") + ".png)");	
			}
			
			function dealData(args, deal_type, param, refresh) {
				var frameParams = "";
				frameParams = $("#myform").serialize();
				//将复合主键的两个name前面的复合主键标识去掉，便于调用框架方法
				args = delComplexKey(args);
				$.ajax({
					url: base_path + "/dealroomTypeData.do",
					type: "post",
					dataType: "json",
					data: {  args: args, keyConfig: "RoomKey(BRANCHID:ROOMID)", currTarget:"Room",
						dealType: deal_type, param: param ? encodeURIComponent(JSON.stringify(param)) : "",
						frameParams: encodeURIComponent(JSON.stringify(frameParams)) },
					success: function(json) {
						if (json.result == 1) {
							if (refresh) {
								$('#dataGrid').trigger("reloadGrid");
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
			
			function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
			
			function transData(arg, rule, state, col) {
				var breakFlag = false;
				var arr = new Array();
				var cm = $("#dataGrid").jqGrid("getGridParam", "colModel");
				$.each(arg, function(index) {
					var rowData = $("#dataGrid").jqGrid("getRowData", arg[index]);
					$.each(cm, function(i) {
						if (cm[i].editrules && cm[i].editrules["required"] && !rowData[cm[i].name] && rule == validate_tag) {
							//showMsg(cm[i].name + ":此字段必需");
							showMsg("请补齐房间信息");
							breakFlag = true;
							return;
						}if (cm[i].edittype == "checkbox") {
							rowData[cm[i].name] = rowData[cm[i].name] == "Yes" ? 1 : rowData[cm[i].name] == "No" ? 0 
									: cm[i].name == "CHECK" ? 0 : rowData[cm[i].name];
						}else if(cm[i].formatter == "date") {
							if(!rowData[cm[i].name].trim()) {
								if(cm[i].defValue) {
									if(cm[i].defValue == "sysdate") {
										var now = new Date();
										rowData[cm[i].name] = now.getFullYear() + "/" + now.getMonth() + "/" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
									} else {
										rowData[cm[i].name] = cm[i].defValue;
									}
								} else {
									rowData[cm[i].name] = "";
								}
							}
						} 
					});
					
					if (breakFlag) return;
					arr.push(rowData);
				});
				
				if (breakFlag) return;
				else return arr;
			}
			
			function saveData() {
				$("#dataGrid").jqGrid("saveCell", last_row, last_cell);
				if(!newDataFlag){
					var args = transData(data_changed, validate_tag), param = arguments[0], refresh = arguments[1];
				
					
					if(data_changed.length == 0) {
					    showMsg("数据未更改!");
						return false;
					}
					
					if(args.length == 0){
						return false;
					}
				
					
				}else{
					//alert(JSON.stringify(data_newAdd))
					//console.log(data_newAdd)
					var args = transData(data_newAdd, validate_tag), param = arguments[0], refresh = arguments[1];
				}
				
				if(args && args.length > 0) {
						showMsg("确认要保存更改?", dealData(JSON.stringify(args), save_tag, param, refresh));
					}
			}
			
			function arrayTurnToString(obj){
				newObjString += obj +",";
				if(newObjString.length >= 2){
					return newObjString.substring(0,newObjString.length-1);
				}else{
					newObjString ="";
					return newObjString;
				}
			}
			
			function delComplexKey(args){
				var c = args.replace(/roomKey.branchId/g,"branchId");
				var d = c.replace(/roomKey.roomId/g,"roomId");
				return d;
			}
			
			function gettypes(){
				var str="";
				$.ajax({
					type:"post",
					async:false,
					url:base_path + "/queryRoomTypeInCurrBranch.do",
					success:function(data){
					if (data != null) {
						var jsonobj=JSON.stringify(data.rows);
						var length=eval(jsonobj).length;
						for(var i=0;i<length;i++){
							if(i!=length-1){
								str+=eval(jsonobj)[i].ROOMTYPE+":"+eval(jsonobj)[i].ROOMNAME+";";
							}else{
								str+=eval(jsonobj)[i].ROOMTYPE+":"+eval(jsonobj)[i].ROOMNAME;// 这里是option里面的 value:label
							}
						}   
				
				     }
				}
				
				});
				 return str;
				}
				
				function UniqueInDBRoom(value, colname) {  
		        var flag;
		        //在前台判断
		         var obj = $("#dataGrid").jqGrid("getRowData");
				    var newRoomIdString ="";
				    jQuery(obj).each(function(){
				        var oldRoomId = this["roomKey.roomId"];
				        newRoomIdString = newRoomIdString + oldRoomId +",";     
				  });
		        //alert(newRoomIdString)
		        if(value == ""){
		        	flag = false;
		        	return [flag, flag ? "" : colname + "不能为空!"];
		        }else {
		        	if(newRoomIdString.indexOf(value) >= 0 ){
			        	flag = false;
			        } else {
				        //去后台去判断
						$.ajax({
							url: base_path + "/uniqueInDb.do",
							type: "post",
							dataType: "json",
							async: false,
							data: { target: "Room", colName: "roomKey.roomId", colValue: value, branchId: "roomKey.branchId" ,branchIdValue: branchId},
							success: function(json) { 
								flag = json.result;
							},
							error: function(json) {
							}
						});
			        }
					 return [flag, flag ? "" : colname + "违反唯一性,该数据已存在!"];       
		        } 
		     }
			 function trimValue(value,colname){
				 var reg =/\s/;
				 if(reg.test(value)){
	                 return [false, colname + "不能包含空格!"];  
				 }else{
	                 return [true,""];
	             }  
			 }
	</script>
	</body>
</html>

