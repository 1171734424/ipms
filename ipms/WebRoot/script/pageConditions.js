
jQuery(document).ready(function() {
	queryOperateLogStatus();
});

var loaded, selected, selectVal, isCtrl;
jQuery(document).keydown(function(e) {
	var code;
	if (!e) e = window.event;
	if (e.keyCode) {
		code = e.keyCode;
	} else if (e.which) {
		code = e.which;
	}
	
	//CTRL
    if(code == 17) isCtrl=true;
    if (isCtrl) return;
    
	if (code == 37 || code == 38) {
		movePrev();
		loaded = true;
	} else if (code == 39 || code == 40) {
		moveNext();
		loaded = true;
	} else if (e.keyCode == 13) {
		if (selectVal) {
			onInfoSelect(selectVal,selectVal);
			loaded = true;
		}
	} else {
		loaded = false;
	}
});

function queryByInput() {
	if (loaded) return;
	var e = window.event;
	if (e.keyCode == 17) isCtrl = false;
	
	if (jQuery("#queryInfo li")) {
		jQuery("#queryInfo li").remove();
	}
	var inputStr = jQuery("#cond_QueryInfo").val();
	if (args) {
		jQuery.ajax({
			url: basePath + "/queryByInput.do",
			type: "post",
			dataType: "json",
			data: { inputStr: inputStr }, 
			success: function(json) {
				//if (json.result == 1) {
					//var datas = json.data.split(",");
					var queryInfo = jQuery("#queryInfo");
					jQuery.each(json, function(index) {
						var li = "<li class='unselected' onclick=onInfoSelect('" + json[index].custName.replace(" ", "")
						//+ " onmouseover='toggleInfoClass(this, 1);' onmouseout='toggleInfoClass(this, 0);'"
						+ "', '" + json[index].custId + "')>" + json[index].custName + "</li>";
						jQuery(li).appendTo(queryInfo);
					});
					queryInfo.show();
				//}
			},
			error: function(json) {
			}
		});
	}
}

function toggleInfoClass() {
	jQuery(arguments[0]).attr("class", arguments[1] == 0 ? "unselected" : "selected");
}

function onInfoSelect(name, id) {
	jQuery("#cond_QueryInfo").val(name);
	jQuery("#cond_QueryCode").val(id);
	removeQueryInfo();
}

function removeQueryInfo() {
	jQuery("#queryInfo li").remove();
	jQuery("#queryInfo").hide();
	selectVal = "";
	loaded = false;
}

function movePrev() {
	var index = jQuery("#queryInfo>li + .selected").prevAll().length;
	selected = jQuery("#queryInfo>li").attr("class", "unselected").eq(index == 0 ? jQuery("#queryInfo>li").length - 1 : index - 1);
	selected.attr("class", "selected");
	selectVal = selected.html();
}

function moveNext() {
	var index = jQuery("#queryInfo>li + .selected").prevAll().length;
	selected = jQuery("#queryInfo>li").attr("class", "unselected").eq(index == jQuery("#queryInfo>li").length - 1 ? 0 : index + 1);
	selected.attr("class", "selected");
	selectVal = selected.html();
}

/***************************************************************自定义***************************************************************************/

$("#cond_USINGTYPE").bind("click", function() {
	var javaName = "SysParam";
	var colsName = "content,paramName";
	var queryConditions = "paramType=THEME";
	var inputId = "cond_USINGTYPE";
	var url = base_path + "/pageConditionOpenJDialog.do?inputId=" + inputId + "&javaBeanName=" + javaName + "&colsName="+colsName+"&queryConditions="+queryConditions;
	JDialog.open("查询", url, 450, 350);
	})
	
	$("#cond_SALETYPE").bind("click", function() {
	var javaName = "SysParam";
	var colsName = "content,paramName";
	var queryConditions = "paramType=SALETYPE";
	var inputId = "cond_SALETYPE";
	var url = base_path + "/pageConditionOpenJDialog.do?inputId=" + inputId + "&javaBeanName=" + javaName + "&colsName="+colsName+"&queryConditions="+queryConditions;
	JDialog.open("查询", url, 450, 350);
	})
	
	$("#cond_GOODCATEGORYID").bind("click", function() {
	var javaName = "GoodsCategory";
	var colsName = "categoryId,categoryName";
	var queryConditions = "branchId=$BRANCHID,status=1";
	var inputId = "cond_GOODCATEGORYID";
	var url = base_path + "/pageConditionOpenJDialog.do?inputId=" + inputId + "&javaBeanName=" + javaName + "&colsName="+colsName+"&queryConditions="+queryConditions;
	JDialog.open("查询", url, 450, 350);
	})
	
function queryOperateLogStatus(){
	jQuery.ajax({
		url: base_path + "/queryOperateLogStatus.do",
		type: "post",
		dataType: "json",
		data: {}, 
		success: function(json) {
			 var tabledata = "";
			$.each(json, function(indextype){
		 			tabledata = tabledata + "<option value='"+json[indextype].content+"'>"+json[indextype].paramName+"</option>";
			});
			 
			 	if(json.length == 0){
			 		$("#cond_OPERMODULE").html("");
			 	}else{
			 		$("#cond_OPERMODULE").html(tabledata);			 	
			 	}
		},
		error: function(json) {
		}
	});
	
}


/***************************************************************自定义***************************************************************************/

$("#cond_SUPERKIND").bind("click", function() {
	var javaName = "SysParam";
	var colsName = "content,paramName";
	var queryConditions = "paramType=EQUIPSUPERKIND";
	var inputId = "cond_SUPERKIND";
	var url = base_path + "/pageConditionOpenJDialog.do?inputId=" + inputId + "&javaBeanName=" + javaName + "&colsName="+colsName+"&queryConditions="+queryConditions;
	JDialog.open("查询", url, 450, 350);
})
	
$("#cond_EQUIPCATEGORY").bind("click", function() {
	var javaName = "EquipmentCategory";
	var colsName = "equipmentCategoryKey.categoryId,categoryName";
	var queryConditions = "status = []1[] and ( equipmentCategoryKey.branchId like decode($BRANCHID,[]100001[],[]%[],$BRANCHID) or equipmentCategoryKey.branchId like decode($BRANCHID,(select c.branchId from Branch c where c.rank = []0[]),[]%[],$BRANCHID))";
	var inputId = "cond_EQUIPCATEGORY";
	var url = base_path + "/pageConditionOpenJDialogTwo.do?inputId=" + inputId + "&javaBeanName=" + javaName + "&colsName="+colsName+"&queryConditions="+encodeURIComponent(queryConditions);
	JDialog.open("查询", url, 450, 350);
})

$("#cond_ECONCENTRATOR").bind("click", function() {
	var javaName = "Equipment";
	var colsName = "dataId,serialNo";
	var queryConditions = "superKind =[]2[] and status = []1[] and ( branchId like decode($BRANCHID,[]100001[],[]%[],$BRANCHID) or branchId like decode($BRANCHID,(select c.branchId from Branch c where c.rank = []0[]),[]%[],$BRANCHID))";
	var inputId = "cond_ECONCENTRATOR";
	var url = base_path + "/pageConditionOpenJDialogTwo.do?inputId=" + inputId + "&javaBeanName=" + javaName + "&colsName="+colsName+"&queryConditions="+encodeURIComponent(queryConditions);
	JDialog.open("查询", url, 450, 350);
})

$("#cond_EROUTE").bind("click", function() {
	var javaName = "Equipment";
	var colsName = "dataId,serialNo";
	var queryConditions = "superKind =[]1[] and status = []1[] and ( branchId like decode($BRANCHID,[]100001[],[]%[],$BRANCHID) or branchId like decode($BRANCHID,(select c.branchId from Branch c where c.rank = []0[]),[]%[],$BRANCHID))";
	var inputId = "cond_EROUTE";
	var url = base_path + "/pageConditionOpenJDialogTwo.do?inputId=" + inputId + "&javaBeanName=" + javaName + "&colsName="+colsName+"&queryConditions="+encodeURIComponent(queryConditions);
	JDialog.open("查询", url, 450, 350);
})
	