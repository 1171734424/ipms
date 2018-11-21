var paramname;
var IntegerReg =/^(\d|[1-9]\d|100)$/;//0-100正整数
var initPriceReg =/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;//0-100正整数
//var Reg = /^(\d|[1-9]\d|100)$/;//保留两位小数
var Reg = /^(\d|[1-9]\d|100)(\.\d{1,2})?$/;
$(function(){
	var tableHtml = "<thead><tr><th><span>序号</span></th><th><span>门店</span></th><th><span>参数名</span></th><th><span>参数值</span></th><th><span>修改</span></th><th style='display:none;'><span>标识</span></th></tr></thead>";
	for(var i=0;i<json.length;i++){
	tableHtml +=  "<tr><td class='squence' style='width:60px'>"+ (i+1)
						   + "</td><td class='name'>"
						   + json[i].BRANCHNAME
						   + "</td><td class='name'>"
						   + json[i].PARAMETER1
						   + "</td><td class='value'>"
						   + json[i].CONTENT
						   + "</td><td class='updatebtn' onclick='modefy(this)'>"
						   +"修改"
						   +"</td><td class='sign' style='display:none'>"
						   +json[i].PARAM_ID
						   +"</td><td class='type' style='display:none'>"
						   +json[i].PARAM_TYPE
						   +"</td></tr>";
	}
	if(json.length != 0){
	$("#showInfoTable").html(tableHtml);	 		
	}
/*	$(".updatebtn").bind("click",function(){
		paramname = $(this).parent().find(".sign").text();
		paramType = $(this).parent().find(".type").text();
		$(".div_update").show();
	});*/
});

function updateParameter(e){
	var data = $("#inputData").val();
	if(paramType.indexOf('timepoint') >=0 && !IntegerReg.test(data)){
		showMsg("请输入0-100的正整数");
		$("#inputData").val("");
		return;
		
	}
	if(paramType.indexOf('reservationfee') >= 0 && !Reg.test(data)){
		showMsg("请输入0-100的正数,至多保留两位小数");
		$("#inputData").val("");
		return;
		
	}
	if(paramType.indexOf('CLEANAMOUNT') >= 0 && !IntegerReg.test(data)){
		showMsg("请输入0-100的正整数");
		$("#inputData").val("");
		return;
		
	}
	if(paramType.indexOf('APARTMENTINITPRICE') >= 0 && !initPriceReg.test(data)){
		showMsg("请输入正数");
		$("#inputData").val("");
		return;
		
	}
	$.ajax({
        url: base_path + "/updateApartmentParam.do",
		 type: "post",
		 data : {"paramname" : paramname,
				 "data" : data},
		 success: function(json) {
					 if(json.result == '1'){
						 showMsg(json.message);
						 $(".div_update").hide();
						 window.setTimeout("location.reload();",1000);
						 
					 }
					 if(json.result == '2'){
						 showMsg(json.message);
					 }
		 },
		 error: function() {
			 
		 }
	});
}
function Close(){
	$(".div_update").hide();
}
function queryParam(){
	var data = $("#branchName").val();
	$.ajax({
		url:base_path+"/apartmentParameterQuery.do",
		type:"post",
		data:{
			branchName:data
		},
		success:function(json){
			var json = json.data;
			var tableHtml = "<thead><tr><th><span>序号</span></th><th><span>门店</span></th><th><span>参数名</span></th><th><span>参数值</span></th><th><span>修改</span></th><th style='display:none;'><span>标识</span></th></tr></thead>";
			for(var i=0;i<json.length;i++){
			tableHtml +=  "<tr><td class='squence'>"+ (i+1)
								   + "</td><td class='name'>"
								   + json[i].BRANCHNAME
								   + "</td><td class='name'>"
								   + json[i].PARAMETER1
								   + "</td><td class='value'>"
								   + json[i].CONTENT
								   + "</td><td class='updatebtn' onclick='modefy(this)'>"
								   +"修改"
								   +"</td><td class='sign' style='display:none'>"
								   +json[i].PARAM_ID
								   +"</td><td class='type' style='display:none'>"
								   +json[i].PARAM_TYPE
								   +"</td></tr>";
			}
			if(json.length != 0){
			$("#showInfoTable").html(tableHtml);	
			}
		},
		error:function(err){
			
		}
	})
}

function modefy(e){
	paramname = $(e).parent().find(".sign").text();
	paramType = $(e).parent().find(".type").text();
	$(".div_update").show();
}

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}