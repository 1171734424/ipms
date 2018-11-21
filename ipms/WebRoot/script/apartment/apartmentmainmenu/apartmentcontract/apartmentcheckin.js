$(function(){
	bindTabClick();
})

function bindTabClick() {
	var movement = [ "queryApartment", "queryApartmentCheckIn", "queryApartmentNoCheckIn", "queryApartmentCheckOff"];
	$(".header_roomlist_li").on("click", function() {
		$("#logFrame").attr("src", movement[$(this).attr("index")] + ".do");
		change($(this).attr("index"));
		document.getElementById("selectAge")[$(this).attr("index")].selected=true;
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
function submitFrom(){
	/*var start = $("#starttime").val();
	var end = $("#endtime").val();
	var starttime = new Date(start);
	var endtime = new Date(end);
	if(Date.parse(starttime) > Date.parse(endtime)){
		window.showMsg("开始时间不能大于结束时间");
		return false;
	}*/
	var mobile = $("#mobile").val();
	if("" != mobile){
		if(!(/^1[34578]\d{9}$/.test(mobile))){
			window.showMsg("请填写正确的手机号!");
			return false;
		}
	}
	$("#condition").submit();
	$(".high_header").css("display","none");
}

//合同新增
function addnewcontrart(){
	window.parent.JDialog.open("合同新增", base_path + "/turnToContrartAddPage.do", 950, 480);	
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
		console.log('单击');
	}, 300);
}
function bb() {
	clearTimeout(timer);
	window.location.href="order_details.jsp";
}
function change(select){
	if(select == 0){
		$("#condition").attr("action","queryApartment.do");
	}else if(select == 1){
		$("#condition").attr("action","queryApartmentCheckIn.do");
	}else if(select == 2){
		$("#condition").attr("action","queryApartmentNoCheckIn.do");
	}else if(select == 3){
		$("#condition").attr("action","queryApartmentCheckOff.do");
	}
}
function resetform(){
	$(':input','#loginfo')  
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('checked')  
	 .removeAttr('selected');  
}
