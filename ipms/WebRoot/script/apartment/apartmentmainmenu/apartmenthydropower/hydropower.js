$(function(){
	bindTabClick();	
})

function bindTabClick() {
	var movement = [ "apartmentleavedHydropowerLog","apartmentroomContractHydropower", "apartmentroomingHydropowerLog", "apartmentpayHydropower"];
	$(".header_roomlist_li").on("click", function() {
		$("#logFrame").attr("src", movement[$(this).attr("index")] + ".do?type="+type);
		change($(this).attr("index"));
		document.getElementById("selectAge")[$(this).attr("index")].selected=true;
		if (movement[$(this).attr("index")] == "apartmentpayHydropower") {
			$(".batchPay").css("display","block");
		}else{
			$(".batchPay").css("display","none");
		}
	})
}

$(".header_roomlist_li").on("click",function(){
	$(this).addClass("active");
	$(this).siblings().removeClass("active");
});

$(".date").datepicker({ dateFormat: "yy/mm/dd " });
$("#starttime").css('font-size','0.9em');
$("#endtime").css('font-size','0.9em');
$(function(){
	var now = new Date();    
    var year = now.getFullYear();       //年   
    var month = now.getMonth() + 1;     //月   
    var day = now.getDate();
    var time = year+"/"+month+"/"+day;
    $("#starttime").val(time);
    $("#endtime").val(time);
});

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}


//高级查询    
$(".highsearch").on("click",function(){
	$(".high_header").css("display","block");
})
$(".imooc").on("click",function(){
	$(".high_header").fadeOut(500);
});
var timer = null;
function aa() {
	clearTimeout(timer);
	if (event.detail == 2)
		return;
	timer = setTimeout(function() {
	}, 300);
}
function bb() {
	clearTimeout(timer);
	window.location.href="order_details.jsp";
}
function change(select){
	$("#loginfo").empty();
	var html = "";
	var timeHtml = "";
	if(select == 0){
		$("#condition").attr("action","apartmentleavedHydropowerLog.do");
		html += "<li><label class='label_name'>合同号</label><input name='contractId' id='contractId' type='text' value='' id='id' class='text_search'></li>";
		html += "<li><label class='label_name'>房间号</label><input name='roomId' id='roomId' type='text' value='' id='id' class='text_search'></li>";
		html += "<li><label class='label_name'>手机号</label><input name='mobile' id='mobile' type='text' value='' id='id' class='text_search'></li>";
		html += "<li><label class='label_name'>合同状态</label>" +
		"<select name='ctStatus' id='ctStatus' id='id' class='text_search'>" +
		"<option value=''>--请选择--</option>" +	
		/*"<option value='1'>有效</option>" +*/
		"<option value='2'>退租</option>" +
		"<option value='3'>已退未结</option>" +
		/*"<option value='4'>在住</option>" +*/
		"<option value='0'>取消</option>" +
	    "</select></li>";
		$("#loginfo").html(html);
		if($("#timeShow").html()!=''){
			$("#timeShow li .label_name").html('离店时间');
		} else {
			var varTime = "<li><label class='label_name'>离店时间</label><input id='leavetime' name='leavetime' type='text' value='' class='text_search check date'></li>";
			$("#timeShow").html(varTime);
		}
	}else if(select == 2){
		$("#condition").attr("action","apartmentroomingHydropowerLog.do");
		html += "<li><label class='label_name'>合同号</label><input name='contractId' id='contractId' type='text' value='' id='id' class='text_search'></li>";
		html += "<li><label class='label_name'>房间号</label><input name='roomId' id='roomId' type='text' value='' id='id' class='text_search'></li>";
		html += "<li><label class='label_name'>记录状态</label>" +
		"<select name='ctStatus' id='ctStatus' class='text_search'>" +
		"<option value=''>--请选择--</option>" +	
		"<option value='1'>未支付</option>" +
		"<option value='2'>已支付</option>" +
		"<option value='3'>有效</option>" +
		"<option value='0'>取消</option>" +
	    "</select></li>";
		$("#loginfo").html(html);
		if($("#timeShow").html()!=''){
			$("#timeShow li .label_name").html('记录时间');
		} else {
			var varTime = "<li style = 'margin-left: -6px;'><label class='label_name'>记录时间</label><input id='leavetime' name='leavetime' type='text' value='' class='text_search check date'></li>";
			$("#timeShow").html(varTime);
		}	
	} else if(select == 1){
		$("#condition").attr("action","apartmentroomContractHydropower.do");
		html += "<li><label class='label_name'>预订人</label><input name='memberName' id='memberName' type='text' value='' id='id' class='text_search'></li>";
		html += "<li><label class='label_name'>手机号</label><input name='mobile' id='mobile' type='text' value='' id='id' class='text_search'></li>";
		$("#loginfo").html(html);
		$("#timeShow").html('');
	} else if(select == 3){
		$("#condition").attr("action","apartmentpayHydropower.do");
		html += "<li><label class='label_name'>合同号</label><input name='contractId' id='contractId' type='text' value='' id='id' class='text_search'></li>";
		html += "<li><label class='label_name'>房间号</label><input name='roomId' id='roomId' type='text' value='' id='id' class='text_search'></li>";
		html += "<li><label class='label_name'>记录状态</label>" +
		"<select name='ctStatus' id='ctStatus' class='text_search'>" +
		"<option value=''>--请选择--</option>" +	
		"<option value='1'>未支付</option>" +
		"<option value='2'>已支付</option>" +
		"<option value='3'>有效</option>" +
		"<option value='0'>取消</option>" +
	    "</select></li>";
		$("#loginfo").html(html);
		$("#timeShow").html('');
		/*$(".time_div").css("display","none");*/
		/*if ($("#timeShow").html()!='') {
			$("#timeShow li .label_name").html('记录时间');
		}else {
			var varTime = "<li style = 'margin-left: -6px;'><label class='label_name'>记录时间</label><input id='leavetime' name='leavetime' type='text' value='' class='text_search check date'></li>";
			$("#timeShow").html(varTime);
		}*/
	}

}



function resetform(){
	$(':input','#loginfo').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
}

function submitFrom(){
	/*var start = $("#starttime").val();
	var end = $("#endtime").val();
	var starttime = new Date(start);
	var endtime = new Date(end);
	if(Date.parse(starttime) > Date.parse(endtime)){
		window.showMsg("开始时间不能大于结束时间");
		return false;
	}*/

	$("#condition").submit();
	$(".high_header").css("display","none");
}

$("tr[class='odd']").dblclick(function(){
	var thisId = this.id;
	var typeTsf = "2";
	window.parent.JDialog.open("水电详情", base_path +"/apartmentroomingHydropowerLog.do?contractIdTsf="+thisId+"&typeTsf="+ typeTsf,1600,500);
})

/**
 * 用于水电批量缴费事件
 */
function payHydropower() {
	var checked1 = $($(window.frames["logFrame"].document).find("#info").children()).find("input[type='checkbox']:checked");
	if (checked1.length > 0) {
		// 获取选中的数据
		var myorderId = $(window.frames["logFrame"].document).find("#myorderId").val();
		var mymonth = $(window.frames["logFrame"].document).find("#mymonth").val();
		// 使用JDialog进Controller逻辑页面
		window.parent.JDialog.open("水电批量支付", base_path + "/apartmentbatchPayHydropower.do?myorderId="+myorderId+"&mymonth="+mymonth,750, 430);
	}else if (checked1.length <= 0) {
		showMsg("请选择要支付的水电账单！");
	}
}


