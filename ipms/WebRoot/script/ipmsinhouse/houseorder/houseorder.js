$(function(){
	bindTabClick();
})
function bindTabClick() {
	var movement = [ "HouseOrderAll", "HouseOrderCheckIn", "HouseOrderNoCheckIn", "HouseOrderCancel","HwaitAgreCountTwo","HouseOrderChecked","HouseOrderNew","HouseOrderCountTwo", "HouseOrderOnDue"];//已入住订单,新丁订单
	$(".header_roomlist_li").on("click", function() {                                                 //waitAgree//HwaitAgreCount                           //HouseOrderCount
		$("#logFrame").attr("src", movement[$(this).attr("index")] + ".do");
		change($(this).attr("index"));
		document.getElementById("selectAge")[$(this).attr("index")].selected=true;
		if($("#selectAge").val()=='0'){
			$("#orderstatus").css("display","block");
			$("#selectstatus").val("");
		} else {
			$("#orderstatus").css("display","none");
		}
	})
}

$(".header_roomlist_li").on("click",function(){
	$(this).addClass("active");
	$(this).siblings().removeClass("active");
	$(".highsearch").html("高级查询");
});
$(".date").datepicker({ dateFormat: "yy/mm/dd " });
$("#starttime").css('font-size','0.9em');
$("#endtime").css('font-size','0.9em');
//$(function(){
//	var now = new Date();    
//    var year = now.getFullYear();       //年   
//    var month = now.getMonth() + 1;     //月   
//    var day = now.getDate();
//    var time = year+"/"+month+"/"+day;
//    $("#arrivaltime").val(time);
//});

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
	if($(".highsearch").html() == '返回上页'){
		var arrivaltime = $(window.frames["logFrame"].document).find("#arrivaltime").val();
		var arrivaltime2 = $(window.frames["logFrame"].document).find("#arrivaltime2").val();
        var mobile = $(window.frames["logFrame"].document).find("#mobile").val();
        var type = $(window.frames["logFrame"].document).find("#type").val();
	    var memberName = $(window.frames["logFrame"].document).find("#memberName").val();
	    var submitType = "highsearch";
	    if(type == 'wait'){
			/*$("#logFrame").attr("src",
					"HwaitAgreCount.do?arrivaltime="+arrivaltime+"&arrivaltime2="+arrivaltime2+"&mobile="+mobile+"&memberName="+memberName+"&submitType="+submitType);
*/
	    	$("#logFrame").attr("src",
					"HwaitAgreCountTwo.do?arrivaltime="+arrivaltime+"&arrivaltime2="+arrivaltime2+"&mobile="+mobile+"&memberName="+memberName+"&submitType="+submitType);

	    }else{
			/*$("#logFrame").attr("src",
					"HouseOrderCount.do?arrivaltime="+arrivaltime+"&arrivaltime2="+arrivaltime2+"&mobile="+mobile+"&memberName="+memberName+"&submitType="+submitType);
*/
	    	$("#logFrame").attr("src",
					"HouseOrderCountTwo.do?arrivaltime="+arrivaltime+"&arrivaltime2="+arrivaltime2+"&mobile="+mobile+"&memberName="+memberName+"&submitType="+submitType);

	    }
		$(".highsearch").html('高级查询');
		
	}else{
		//此处查出datasource表中的数据
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
		
		if($("#selectAge").val()=='0'){
			$("#orderstatus").css("display","block");
			$("#selectstatus").val("");
		} else {
			$("#orderstatus").css("display","none");
		}
		
	}
	
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
	flag=0;
	if(select == 0){
		$("#condition").attr("action","HouseOrderAll.do");
	}else if(select == 1){
		$("#condition").attr("action","HouseOrderCheckIn.do");
	}else if(select == 2){
		$("#condition").attr("action","HouseOrderNoCheckIn.do");
	}else if(select == 3){
		$("#condition").attr("action","HouseOrderCancel.do");
	}else if(select == 4){
		//$("#condition").attr("action","waitAgree.do");
		//$("#condition").attr("action","HwaitAgreCount.do");
		$("#condition").attr("action","HwaitAgreCountTwo.do");
	}else if(select == 5){
		$("#condition").attr("action","HouseOrderChecked.do");
	}else if(select == 6){
		$("#condition").attr("action","HouseOrderNew.do");
	}else if(select == 7){
		//$("#condition").attr("action","HouseOrderCount.do");
		$("#condition").attr("action","HouseOrderCountTwo.do");
	}else if(select == 8){
		$("#condition").attr("action","HouseOrderOnDue.do");
	}
}
function resetform(){
	$('input','#loginfo')  
	 .not(':button, :submit, :reset, :hidden')  
	 .val('');  
	$("#source").val(0);
}

