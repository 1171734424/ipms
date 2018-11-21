var mobReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //手机校验
var idcardReg = /^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\d{4}(([1][9]\d{2})|([2]\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\d{3}[0-9xX]$/; //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
var numberReg = /^[0-9]*[1-9][0-9]*$/; //校验正整数
var moneyReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/; //校验是金额
var IpReg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/; //校验是Ip
var LongitudeReg = /^[\-\+]?(0?\d{1,2}\.\d{1,10}|1[0-7]?\d{1}\.\d{1,10}|180\.0{1,10})$/; //校验经度
var LatitudeReg = /^[\-\+]?([0-8]?\d{1}\.\d{1,10}|90\.0{1,10})$/; //校验纬度
var last_row, last_cell;

 function loadCheckStyle(e){
	 //初始化页面的选中样式
	 var currentId = "#title"+e;
	 var liList = $("#wizard .bwizard-steps li[id != 'title"+e+"']");
	 $(currentId).addClass("active").attr("aria-selected",true).find(".label").addClass("badge-inverse");
	 liList.each(function(index) {
		 $(this).removeClass("active").attr("aria-selected", false).find(".label").removeClass("badge-inverse");	 
	 })
	 //上一步和下一步按钮的样式，是否可点击
	 if(e==1){
		 $(".pager .previous").addClass("disabled").attr("aria-disabled",true);
		 $(".previous a").removeAttr("onclick");
		 $(".pager .next").removeClass("disabled").attr("aria-disabled",false);
		 $(".next a").attr("onclick","toNext()"); 
	 }else if($("#wizard .bwizard-steps li").size()==e){
		 $(".pager .next").addClass("disabled").attr("aria-disabled",true);
		 $(".next a").removeAttr("onclick");
		 $(".pager .previous").removeClass("disabled").attr("aria-disabled",false);
		 $(".previous a").attr("onclick","toPrevious()"); 
	 }else{
		 $(".pager .previous").removeClass("disabled").attr("aria-disabled",false);
		 $(".pager .next").removeClass("disabled").attr("aria-disabled",false);
		 $(".previous a").attr("onclick","toPrevious()"); 
		 $(".next a").attr("onclick","toNext()"); 
	 }
 }

 //初始化首页
 function instruction(){
	 $("#frame").attr('src',"instruction.do");
 }
 
 function toNext(){
	 var currentId = $("#wizard .bwizard-steps li[class='active']").attr("id");
	 var nextIdValue = parseInt(currentId.substr(currentId.length-1,1))+1;
	 var liListSize = $("#wizard .bwizard-steps li").size();
	 //目前只有一个方法，要动态加载，
	 if(nextIdValue <= liListSize){
		 loadNextSteps(nextIdValue);
	 }
	 
 }
 
 function loadNextSteps(e){
	 var currentUrl ="";
	 if(e==1){
		 loadCheckStyle(e);
		 currentUrl ="instruction.do";
	 }else if(e==2){
		 loadCheckStyle(e);
		 currentUrl ="turnToBranchPage.do";
	 }else if(e==3){
		 if(!checkBranchInput())	 
			 return false;
    	 loadCheckStyle(e);
    	 currentUrl ="turnToParameterAdd.do?"+$($(window.frames["frame"].document).find("#myForm")).serialize()+"&rank="+encodeURIComponent($($(window.frames["frame"].document).find("#rank")).val());
	 }else if (e==4){
		 var branchSubType = encodeURIComponent($($(window.frames["frame"].document).find("#branchSubType")).val()) ;
		 if(branchSubType == "1"){
			 if(!cashBoxCheck())
    			 return false;
			 loadCheckStyle(e);
			 currentUrl ="saveRightAndTurnToRoomTypeAddView.do?"+$($(window.frames["frame"].document).find("#myForm")).serialize();
    	 }else if(branchSubType == "2"){
    		 if(!cleanCountCheck())
    			 return false;
    		 if(!cleanCash())
    			 return false;
    		 loadCheckStyle(e);
    		 currentUrl ="saveRightAndTurnToRoomTypeAddView.do?"+$($(window.frames["frame"].document).find("#myForm")).serialize();
    	 }
	 }else if (e==5){
		 window.frames[0].$("#dataGrid").jqGrid("saveCell", window.frames[0].last_row, window.frames[0].last_cell);
		 if(roomTypeInDatabaseCheck()=="false")
			 return false;
		 if(roomPriceInDatabaseCheck()=="false")
			 return false;
		 loadCheckStyle(e);
		 currentUrl ="turnToRoomsAdd.do?branchId="+encodeURIComponent($($(window.frames["frame"].document).find("#branchId")).val()) ;
     }else if (e==6){
    	 window.frames[0].$("#dataGrid").jqGrid("saveCell", window.frames[0].last_row, window.frames[0].last_cell);
    	 if(roomInDatabaseCheck()=="false")	
			 return false;
    	 loadCheckStyle(e);
    	 var currentUrl ="toEnd.do?branchId="+encodeURIComponent($($(window.frames["frame"].document).find("#branchId")).val()) ;
     }
	 $("#frame").attr('src',currentUrl);
 }
 
 function checkBranchInput(){
	 var jqueryObj = $(window.frames["frame"].document);
	 if (isNull($(jqueryObj.find("#branchName")),"门店名称") || isNull($(jqueryObj.find("#rank")),"门店等级") || isNull($(jqueryObj.find("#branchType")),"门店类型")
			|| isNull($(jqueryObj.find("#mobile")),"手机号") || isNull($(jqueryObj.find("#address")),"门店地址") ||  isNull($(jqueryObj.find("#phone")),"座机")
			|| isNull($(jqueryObj.find("#postcode")),"邮编") || isNull($(jqueryObj.find("#contacts")),"联系人") || isNull($(jqueryObj.find("#city")),"城市")
			|| isNull($(jqueryObj.find("#district")),"区域") || isNull($(jqueryObj.find("#customerPhone")),"客服电话") || isNull($(jqueryObj.find("#longitude")),"经度")
			|| isNull($(jqueryObj.find("#latitude")),"纬度") ){
			return false;
		} else if (!checkLen($(jqueryObj.find("#branchName")),"门店名称",10) || !checkLen($(jqueryObj.find("#mobile")),"手机号",11) 
				|| !checkLen($(jqueryObj.find("#address")),"门店地址",20) || !checkLen($(jqueryObj.find("#postcode")),"邮编",6)
				|| !checkLen($(jqueryObj.find("#mobile")),"手机号",11)|| !checkLen($(jqueryObj.find("#contacts")),"联系人",10) ){
				return false;
		}else if ( !isNumber($(jqueryObj.find("#postcode")).val(),"邮编")){
				return false;
		}else if ( !isMobile($(jqueryObj.find("#mobile")).val())){
				return false;
		}else if ( $(jqueryObj.find("#branchIp")).val() && ! isIp($(jqueryObj.find("#branchIp")))){
				return false;
		}else if ( ! isLongitude($(jqueryObj.find("#longitude")))){
				return false;
		}else if ( ! isLatitude($(jqueryObj.find("#latitude")))){
				return false;
		}else{
			return true;
		}	 
 }
 
 function cleanCountCheck(){
	 var jqueryObj = $(window.frames["frame"].document);
	 if (isNull($(jqueryObj.find("#content")),"半日保洁上限")){
			return false;
		} else if (!isNumber($(jqueryObj.find("#content")).val(),"保洁上限")){
			return false;
		} else {
			return true;
		}
 }
 
 function cleanCash() {
	 var jqueryObj = $(window.frames["frame"].document); 
	 if (isNull($(jqueryObj.find("#cleanMoney")),"统一保洁费用")){
		 return false;
	 } else if (!moneyReg.test($(jqueryObj.find("#cleanMoney")).val())){
		 showMsg("统一保洁费用格式有误，参照：0.00,可为正金额！");
		 return false;
	 } else {
		 return true;
	 }
 }
 
	function pageInit(){
	  var lastsel;
	  jQuery("#dataGrid").jqGrid({
	  	url: "turnToRoomTypeAdd.do?branchType="+branchType+"&branchId="+branchId,
		mtype: "POST",
	    datatype : "json",
	    height : '80%',
	    width : '95%',
	    cellEdit:true,
		cellsubmit: "clientArray",
		shrinkToFit:true, 
	    viewrecords: true,
	    altRows: true,
		altclass: "ui-state-focus",
	    colNames : [ '序号','选择','归属门店','房型编码' ,'房型名称', '床位人数', '床型', '宽带' ,'分店类型','状态','录入人','录入时间','标签'],
	    colModel : [ 
	    {name: "id", index: "id", width:65, hidden:true, align: "center"},
	    { name: "CHECK", index: "CHECK", width: 93, align: "center", frozen: true, editable: false, edittype: "checkbox",
			formatter: function cboxFormatter(cellvalue, options, rowObject) {
				  return '<div class="checkbox"><input id="gcbox' + options.rowId + '" name="gcbox" type="checkbox" value="' + options.rowId + '" style="display:none" ' 
				  	+ ' onclick="onChecked(' + options.rowId + ')"/><label for="gcbox' + options.rowId + '" style="margin-left:-18px"></label></div>';
			}
		},
		{name : 'roomTypeKey.branchId',index : 'roomTypeKey.branchId',width : 200 ,align : "center" ,editable:true,edittype:'text',editrules:{required:true}, defvalue:branchId, hidden:true,}, 
	    {name : 'roomTypeKey.roomType',index : 'roomTypeKey.roomType',width : 200 ,align : "center" ,editable:true,edittype:'text',editoptions: {size:3, maxlength: 3},editrules:{required:true, custom:true, custom_func:UniqueInDB}}, 
	    {name : 'roomName',index : 'roomName',width : 200 ,align : "center" ,editable:true,edittype:'text',editoptions: {size:10, maxlength: 15},editrules:{required:true}},
	    {name : 'roomBed',index : 'roomBed',width : 200 ,align : "center" ,editable:true,editoptions: {size:10, maxlength: 2},editrules:{ required:true,number:true }}, 
	    {name : 'bedDesc',index : 'bedDesc',width : 200 ,align : "center", editable:true, edittype : "select", formatter:'select', editoptions : {value : "1:大床;2:双床;3:大/双;4:多床"},editselected:'bedDesc' },
	    {name : 'broadband',index : 'broadband',width : 200 ,align : "center" ,editable:true, edittype : "select", formatter:'select',editoptions : {value : "1:有线免费;2:免费"}},
	    {name : 'theme',index : 'theme',width : 100 ,align : "center" ,hidden:true,editrules:{edithidden:true, required:true},defvalue:branchType}, 
	    {name : 'status',index : 'status',width : 100 ,align : "center" ,hidden:true,editrules:{edithidden:true, required:true},defvalue:'1'},
	    {name : 'recordUser',index : 'recordUser',width : 100 ,align : "center" ,hidden:true,editrules:{edithidden:true, required:true},defvalue:recordUser},
	    {name : 'recordTime',index : 'recordTime',width : 100 ,align : "center" ,hidden:true, edittype:'date',
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
		},sorttype:"date",defvalue:"sysdate"},
		{name : 'roomLabel',index : 'roomLabel',width : 100 ,align : "center" ,hidden:true,editrules:{edithidden:true, required:true},defvalue:"111111111111111111"},],
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
	   
	  id_name = new Array();  
	  id_name.push("roomTypeKey.branchId");       
	  id_name.push("roomTypeKey.roomType");     
	  id_name.push("roomName");   
	  id_name.push("roomBed");   
	  id_name.push("bedDesc");   
	  id_name.push("broadband");
	  id_name.push("theme");
	  id_name.push("status");
	  id_name.push("recordUser");
	  id_name.push("recordTime");   
	  id_name.push("roomLabel");   
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
			$("#dataGrid").jqGrid("addRowData", lastrow_id, {}, "after", lastrow_id - 1);
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
			}else if (cm[index].name == "roomTypeKey.branchId"){
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
			}else if (cm[index].name == "roomLabel"){
				$("#dataGrid").jqGrid("setCell", lastrow_id, index, "111111111111111111", false, false, true);
			}
		});
		window.setTimeout(function () {
			$("#dataGrid").jqGrid("nextCell", lastrow_id - 8, firstEditCell);
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
				newString = arrayTurnToString(rowData["roomTypeKey.roomType"]);
			}
		})
		newString = newString.replace(/roomTypeKey.roomType/g,"roomType");
		newObjString ="";
		//alert("最终要删除的初始化的list:"+newString)
		$.ajax({
				url: base_path + "/turnToRoomTypeAdd.do",
				type: "post",
				dataType: "json",
				data: { branchType:branchType, delArray:newString ,branchId:branchId},
				success: function(json) {
						//console.log(json.rows)
						//$('#dataGrid').trigger("reloadGrid");
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
		frameParams = $("myform").serialize();
		//将复合主键的两个name前面的复合主键标识去掉，便于调用框架方法
		args = delComplexKey(args);
		$.ajax({
			url: base_path + "/dealroomTypeData.do",
			type: "post",
			dataType: "json",
			data: {  args: args, keyConfig: "RoomTypeKey(BRANCHID:ROOMTYPE)", currTarget:"RoomType",
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
		//cm = cm.reverse();
		$.each(arg, function(index) {
			var rowData = $("#dataGrid").jqGrid("getRowData", arg[index]);
			
			$.each(cm, function(i) {
				 if (cm[i].editrules && cm[i].editrules["required"] && !rowData[cm[i].name] && rule == validate_tag) {
					showMsg("请补齐房型信息!");
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
			if(data_changed.length == 0 && args !="undefined") {
			    showMsg("数据未更改!");
				return false;
			}
			if(args.length == 0){
				return false;
			}
		}else{
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
		var c = args.replace(/roomTypeKey.branchId/g,"branchId");
		var d = c.replace(/roomTypeKey.roomType/g,"roomType");
		return d.replace(/\s+/g,"");
	}
	
	//房价初始化
	function rpInitialByRoomType(){
		$.ajax({
			url: base_path + "/rpInitialByRoomType.do",
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
	
	 function UniqueInDB(value, colname) {  
     var flag;
     //在前台判断
      var obj = $("#dataGrid").jqGrid("getRowData");
		    var newRoomTypeString ="";
		    jQuery(obj).each(function(){
		        var oldRoomType = this["roomTypeKey.roomType"];
		        newRoomTypeString = newRoomTypeString + oldRoomType +",";     
		  });
     if(newRoomTypeString.indexOf(value) >= 0){
     	flag = false;
     } else {
			$.ajax({
				url: base_path + "/uniqueInDb.do",
				type: "post",
				dataType: "json",
				async: false,
				data: { target: "RoomType", colName: "roomTypeKey.roomType", colValue: value, branchId: "roomTypeKey.branchId" ,branchIdValue: branchId},
				success: function(json) {
					flag = json.result;
				},
				error: function(json) {
				}
			});
		}
		return [flag, flag ? "" : colname + "违反唯一性,该数据已存在!"];        
  }
	 
	 /**
	  * 验证房型信息填写是否完善，是否保存
	  * @returns {String}
	  */
	 function roomTypeInDatabaseCheck(){
		 var isSaveFlag = 'false';
		 var jqueryObj = $(window.frames["frame"].document);
		 var branchId = $(jqueryObj.find("#branchId")).val();
		 // 验证房型录入信息是否完善
		 var title1 = $(jqueryObj.find("#dataGrid tr"));
		 var titleList = $(title1).length;
		 var flag = true;
		 for (var j = 1; j < titleList; j++) {
			 var titleChildren = $($(title1)[j]).children();
			 $(titleChildren).each(function(i){
				if (i+2 < 6) {
					if($.trim($(titleChildren[i+2]).context.attributes[2].value) == ""){
						 flag = false;
						 return;
					 }
				} else {
					return false;
				}
				 if (!flag) {
					return false;
				}
			 } );
		 }
		 
		 if (!flag) {
			 showMsg("请完善信息！");
			 return isSaveFlag;
		} else {
			$.ajax({
				url: base_path + "/roomTypeInDatabaseCheck.do",
				type: "post",
				dataType: "json",
				data: { branchId : branchId,
						titleList : titleList-1},
				async: false,
				success: function(json) {
					if(json.result == 1){
						isSaveFlag = 'true';
					}else{
						isSaveFlag = 'false';
						showMsg("请保存房间类型!");
					}	
				},
				error: function(json) {
					isSaveFlag = 'false';
				}
			});	
			return isSaveFlag;	 
		}
	 }
	 
	 /**
	  * 验证填写的房间及房型是否录入完整，及房间是否保存
	  * @returns {String}
	  */
	 function roomInDatabaseCheck(){
		 var isSaveFlag = 'false';
		 var jqueryObj = $(window.frames["frame"].document);
		 var branchId = $(jqueryObj.find("#branchId")).val();
		 
		// 验证房型录入信息是否完善
		 var title1 = $(jqueryObj.find("#dataGrid tr"));
		 
		 var titleList = $(title1).length;
		 var flag = true;
		 for (var j = 1; j < titleList; j++) {
			 var titleChildren = $($(title1)[j]).children();
			 $(titleChildren).each(function(i){
				if (i+2 < 6) {
					if($.trim($(titleChildren[i+2]).context.attributes[2].value) == ""){
						 flag = false;
						 return;
					 }
				} else {
					return false;
				}
				 if (!flag) {
					return false;
				}
			 } );
		 }
		 
		 $.ajax({
				url: base_path + "/roomInDatabaseCheck.do",
				type: "post",
				dataType: "json",
				async: false,
				data: { branchId : branchId,
						titleList : titleList-1,
						flag : flag},
				success: function(json) { 
					if(json.result == 1){
						isSaveFlag = 'true';
					}else if(json.result == 2){
						isSaveFlag = 'false';
						showMsg("请完善信息！");
					} else {
						isSaveFlag = 'false';
						showMsg("请保存房间!");
					}	
				},
				error: function(json) {
					isSaveFlag = 'false';
				}
			});	
		 return isSaveFlag;
	 }	 
	 
	 function roomPriceInDatabaseCheck(){
		 var isSaveFlag;
		 var jqueryObj = $(window.frames["frame"].document);
		 var branchId = $(jqueryObj.find("#branchId")).val();
		 $.ajax({
				url: base_path + "/roomPriceInDBCheck.do",
				type: "post",
				dataType: "json",
				async: false,
				data: { branchId : branchId },
				success: function(json) { 
					if(json.result == 1){
						isSaveFlag = 'true';
					}else{
						isSaveFlag = 'false';
						showMsg("请先房价初始化!");
					}	
				},
				error: function(json) {
					isSaveFlag = 'false';
				}
			});	
		 return isSaveFlag;
		 
	 }
	 
 function toPrevious(){
	 var currentId = $("#wizard .bwizard-steps li[class='active']").attr("id");
	 var nextIdValue = parseInt(currentId.substr(currentId.length-1,1))-1;
	 var liListSize = $("#wizard .bwizard-steps li").size();
	 if(nextIdValue >= 1){
		 loadPreviousSteps(nextIdValue);
	 }
 }
 
 function loadPreviousSteps(e){
	 var currentUrl ="";
	 if(e==1){
		 loadCheckStyle(e);
		 currentUrl ="instruction.do";
	 }else if(e==2){
		 loadCheckStyle(e);
		 currentUrl ="turnToBranchPage.do";
	 }else if(e==3){
    	 loadCheckStyle(e);
    	 currentUrl ="turnToParameterAdd.do?turnFlag=turnFlag";
	 }else if (e==4){
    	loadCheckStyle(e);
    	currentUrl ="saveRightAndTurnToRoomTypeAddView.do?turnFlag=turnFlag";
	 }else if (e==5){
		 loadCheckStyle(e);
		 currentUrl ="turnToRoomsAdd.do?branchId="+encodeURIComponent($($(window.frames["frame"].document).find("#branchId")).val()) ;
     }else if (e==6){
    	 loadCheckStyle(e);
    	 var currentUrl ="toEnd.do?branchId="+encodeURIComponent($($(window.frames["frame"].document).find("#branchId")).val()) ;
     }
	 $("#frame").attr('src',currentUrl);
	 
 }

 
 ////进行PostAndStaff页面的input框体的前台校验
 function checkPostAndStaffInput(){
	 var jqueryObj = $(window.frames["frame"].document);
	 if (isNull($(jqueryObj.find("#staffName")),"用户名") || isNull($(jqueryObj.find("#loginName")),"登录名") || isNull($(jqueryObj.find("#idcard")),"身份证")
			|| isNull($(jqueryObj.find("#mobile")),"手机号") || isNull($(jqueryObj.find("#birthdays")),"生日") ||  isNull($(jqueryObj.find("#entryTimes")),"入职时间")
			|| isNull($(jqueryObj.find("#gendor")),"性别") || isNull($(jqueryObj.find("#post")),"职位") ){
			return false;
		} else if (!checkLen($(jqueryObj.find("#staffName")),"用户名",10) || !checkLen($(jqueryObj.find("#loginName")),"登录名",10) || !checkLen($(jqueryObj.find("#idcard")),"身份证",18)
				|| !checkLen($(jqueryObj.find("#mobile")),"手机号",11) ){
				return false;
		}else if ( !isIdcardInitial($(jqueryObj.find("#idcard")))){
				return false;
		}else if ( !isMobile($(jqueryObj.find("#mobile")).val())){
				return false;
		}else{
			return true;
		}	 
 }
 
 function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg, fn);
		}
	}
 

 

 
 function cashBoxCheck(){
	 var jqueryObj = $(window.frames["frame"].document);
	 if (isNull($(jqueryObj.find("#cashCount")),"备用金额")){
			return false;
		} else if (!isMoney($(jqueryObj.find("#cashCount")).val(),"备用金额")){
			return false;
		}else{
			return true;
		}
 }
 

 
 function checkLen(content, title, max, min){
		var length = content.val().length;
		var msg =""; 
		if(title) msg = title + msg;
		if(min){
			if(length <= max && length >= min){
				return true;
			}else {
				msg = msg + "长度在于" + min + "-" + max + "之间!";
				showMsg(msg);
				return false;
			}
		}else{
			if(length <= max){
				return true;
			}else{
				msg = msg + "长度应小于" + max + "!";
				showMsg(msg);
				return false;
			}
		}
	}
 
 function isIp(content){
	 var IpValue = content.val();
	 if(IpReg.test(IpValue)) {
		 return true;
	 } else {
		 if(IpValue) {
			 var msg = "Ip地址不合法!";
			 showMsg(msg);
		 }
		 return false;
	 }
 }
 
 function isLongitude(content){
	 var IpValue = content.val();
	 if(LongitudeReg.test(IpValue)) {
		 return true;
	 } else {
		 if(IpValue) {
			 var msg = "经度-180.0～+180.0，必须输入1到10位小数！";
			 showMsg(msg);
		 }else{
			 var msg = "经度不可为空！";
			 showMsg(msg);
		 }
		 return false;
	 }
	 
 }
 
 function isLatitude(content){
	 var IpValue = content.val();
	 if(LatitudeReg.test(IpValue)) {
		 return true;
	 } else {
		 if(IpValue) {
			 var msg = "纬度-90.0～+90.0，必须输入1到10位小数！";
			 showMsg(msg);
		 }else{
			 var msg = "纬度不可为空！";
			 showMsg(msg);
		 }
		 return false;
	 }
 }
 
 
 function isIdcardInitial(content){
	 var idcard = content.val();
		if(idcardReg.test(idcard)){
			return true;
		}else{
			if(idcard){
				var msg = "身份证不合法!";
			showMsg(msg);
		}
		return false;
	}
}
 
 
 

 
////Staff页面的input框体的手机号，登录名，身份证查重
 function inputCheckRepeat(){
	 var isRepeat;
	 var jqueryObj = $(window.frames["frame"].document);
	 var staffId = $(jqueryObj.find("#staffId")).val();
	 var mobile = $(jqueryObj.find("#mobile")).val();
	 var idcard = $(jqueryObj.find("#idcard")).val();
	 var loginName = $(jqueryObj.find("#loginName")).val();
	 $.ajax({
			url: base_path + "/checkPersonRepeat.do",
			type: "post",
			dataType: "json",
			async: false,
			data: { mobile : mobile,
		 			idcard : idcard,
		 			staffId : staffId,
		 			loginName : loginName },
			success: function(json) { 
				if(json.result == 1){
					isRepeat = 'true';
					showMsg("已有该手机号，请重新输入!");
				} else if (json.result == 2){
					isRepeat = 'true';
					showMsg("已有该登录名，请重新输入!");
				} else if (json.result == 3){
					isRepeat = 'true';
					showMsg("已有该身份证号，请重新输入!");
				} else if (json.result == 4){
					isRepeat = 'false';
				} else {
					isRepeat = 'true';
					showMsg("操作失败!");
				}	
			},
			error: function(json) {
				isRepeat = 'true';
				showMsg("操作失败!");
			}
		});
	 return isRepeat;

 }