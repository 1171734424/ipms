$(function(){
	bindTabClick();
})

function bindTabClick() {
	var movement = [ "HouseCheckAll", "HouseToDayWaitCheckOut", "HouseCheckOut", "HouseCheckOutOver"];
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
    $("#arrivaltime").val(time);
});

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}
function submitFrom(){
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

//高级查询    
$(".highsearch").on("click",function(){

	$.ajax({
		type:"POST",
		url:"getSource.do",
		data:{},
		dataType:"json",
		success:function(json){
			resetform();
			$("#source").empty();
			var data = '<option value="0" selected="selected">全部</option>';
			$.each(json,function(index){
		 		data = data + '<option value=' + json[index]["memberId"] + '>' + json[index]["name"] + '</option>';
		 	});
			$("#source").append(data);
		}
	})
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
		$("#condition").attr("action","HouseCheckAll.do");
		$("#checkinTime_none").css("display","block");
		$("#checkoutTime_none").css("display","block");
	}else if(select == 1){
		$("#condition").attr("action","HouseToDayWaitCheckOut.do");
		$("#checkinTime_none").css("display","none");
		$("#checkoutTime_none").css("display","none");
	}else if(select == 2){
		$("#condition").attr("action","HouseCheckOut.do");
		$("#checkinTime_none").css("display","block");
		$("#checkoutTime_none").css("display","block");
	}else if(select == 3){
		$("#condition").attr("action","HouseCheckOutOver.do");
		$("#checkinTime_none").css("display","block");
		$("#checkoutTime_none").css("display","block");
	}
}
function resetform(){
	$(':input','#loginfo')  
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('checked')  
	 .removeAttr('selected');  
}
