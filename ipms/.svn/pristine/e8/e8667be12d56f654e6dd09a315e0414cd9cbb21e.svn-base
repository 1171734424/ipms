var paramname;
var IntegerReg =/^(\d|[1-9]\d|100)$/;//0-100正整数
//var Reg = /^(\d|[1-9]\d|100)$/;//保留两位小数
var Reg = /^(\d|[1-9]\d|100)(\.\d{1,2})?$/;
$(function(){
	var index = 0;
	var tableHtml = "<thead><tr><th><span>序号</span></th><th><span>参数名</span></th><th><span>参数值</span></th><th><span>修改</span></th><th style='display:none;'><span>标识</span></th></tr></thead>";
	for(var key in json){
	index++;
	tableHtml +=  "<tr><td class='squence'>"+ (index)
						   + "</td><td class='name'>"
						   + key
						   + "</td><td class='value'>"
						   + json[key].substring(0,json[key].indexOf(":"))
						   + "</td><td class='updatebtn'>"
						   +"修改"
						   +"</td><td class='sign' style='display:none'>"
						   +json[key].substring(json[key].indexOf(":")+1)
						   +"</td></tr>";
	}
	if(json.length != 0){
	$("#showInfoTable").html(tableHtml);	 		
	}
	$(".updatebtn").bind("click",function(){
		paramname = $(this).parent().find(".sign").text();
		$(".div_update").show();
	});
});

function updateParameter(e){
	var data = $("#inputData").val();
	if(paramname.indexOf('timepoint') >=0 && !IntegerReg.test(data)){
		showMsg("请输入0-100的正整数");
		$("#inputData").val("");
		return;
		
	}
	if(paramname.indexOf('reservationfee') >= 0 && !Reg.test(data)){
		showMsg("请输入0-100的正数,至多保留两位小数");
		$("#inputData").val("");
		return;
		
	}
	if(paramname.indexOf('CLEANAMOUNT') >= 0 && !IntegerReg.test(data)){
		showMsg("请输入0-100的正整数");
		$("#inputData").val("");
		return;
		
	}
	$.ajax({
        url: base_path + "/update.do",
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

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}