var currentMove = '5';
var orderUserTemp,mphoneTemp,arrivalTimeTemp,arrtimesTemp;
$(function(){
	$("#orderFrame").attr("src","orderInfoAll.do?movement=" + currentMove);
	if(currentMove == 5){
		var orderUserTemp = $(window.frames["orderFrame"].document).find("#orderUser").val();
		var mphoneTemp = $(window.frames["orderFrame"].document).find("#mphone").val();
		var arrivalTimeTemp = $(window.frames["orderFrame"].document).find("#arrivalTime").val();
		var arrtimesTemp = $(window.frames["orderFrame"].document).find("#arrtimes").val();

		if(orderUserTemp != null && orderUserTemp != ""){
			$("#orderUser").val(orderUserTemp);
		}
		if(mphoneTemp != null && mphoneTemp != ""){
			("#mphone").val(mphoneTemp);
		}
		if(arrivalTimeTemp != null && arrivalTimeTemp != ""){
			("#arrivalTime").val(arrivalTimeTemp);
		}
		if(arrtimesTemp != null && arrtimesTemp != ""){
			("#arrtimes").val(arrtimesTemp);
		}
	}
	/*var html = "";
	$(".margintop").empty();
	//$("#condition").attr("action","orderInfoAll.do?movement=" + movement[$(this).attr("index")]);
	html += "<ul class='clearfix'><li><label class='label_name'>预订人</label><input name='orderUser' id='orderUser' type='text' value='' id='id' class='text_search'></li>";
	html += "<li><label class='label_name'>手机号</label><input name='mphone' id='mphone' type='text' value='' id='id' class='text_search'></li>";
	html += "<li><label class='label_name'>抵店日期</label>	<input id='arrivalTime' name='arrivalTime' type='text' value='' class='check date text_search'></li>";
	html += "<li><label class='label_name'>至</label><input name='arrtimes' id='arrtimes' type='text' value='' class='check date text_search'></li>";
	html += "</ul><ul class='clearfix'><li><input type='button' value='确认' class='button_margin confirm' onclick='queryDataNew()'></li></ul>";
	$(".margintop").html(html);*/
	
	bindClickEvent();
})

function bindClickEvent() {
	var movement = [ "5", "1", "2", "3", "4", "0"];
	$(".header_order_li").on("click", function() {
		$("#orderFrame").attr("src",  "orderInfoAll.do?movement=" + $(this).attr("index"));
		currentMove = $(this).attr("index");
		$(".highsearch").html('高级查询');
		$("#newButtonss").remove();
		$(".high_headerNB").remove();
	})
}

$(".header_order_li").on("click",function(){
	$(this).addClass("active");
	$(this).siblings().removeClass("active");
	if($(this).id == "orderNews" ){
		$(".highsearch").html('高级查询');
	}	
});

function change(select){
	var html = "";
	if(select == 5 ){
		$(".margintop").empty();
		if(select != currentMove){
			$("#orderFrame").attr("src","orderInfoAll.do?movement=" + currentMove);
		}
		html += "<ul class='clearfix' style='margin-left: 5%;'><li><label class='label_name'>预订人</label><input name='orderUser' id='orderUser' type='text' value='' id='id' class='text_search'></li>";
		html += "<li><label class='label_name'>手机号</label><input name='mphone' id='mphone' type='text' value='' id='id' class='text_search'></li></ul>";
		html += "<ul class='clearfix' style='margin-left: 5%;'><li><label class='label_name'>抵店日期</label>	<input id='arrivalTime' name='arrivalTime' type='text' value='' class='check date text_search'></li>";
		html += "<li><label class='label_name'>至</label><input name='arrtimes' id='arrtimes' type='text' value='' class='check date text_search'></li></ul>";					      
		html += "<ul class='clearfix'><li><input type='button' value='确认' class='button_margin confirm' onclick='queryDataNew()'></li></ul>";
		$(".margintop").html(html);
		$(".high_header.fadeInDown").css({
			"height": "300px",
			"width": "600px"
			
		});
		$(".button_margin.confirm").css({
			"margin-right": "42px"
		});
		if(orderUserTemp != null && orderUserTemp != ""){
			$("#orderUser").val(orderUserTemp);
		}
		if(mphoneTemp != null && mphoneTemp != ""){
			$("#mphone").val(mphoneTemp);
		}
		if(arrivalTimeTemp != null && arrivalTimeTemp != ""){
			$("#arrivalTime").val(arrivalTimeTemp);
		}
		if(arrtimesTemp != null && arrtimesTemp != ""){
			$("#arrtimes").val(arrtimesTemp);
		}
		$("#arrivalTime").on("click",function(){
			laydate({
			   	elem: '#arrivalTime'
			});
		});
		
		$("#arrtimes").on("click",function(){
			laydate({
			   	elem: '#arrtimes'
			})
		});
	} else {
		$(".margintop").empty();
		$("#orderFrame").attr("src","orderInfoAll.do?movement=" + currentMove);
		html += "<ul class='clearfix'><li><label class='label_name' >订单号</label><input name='orderId' type='text' id='orderId' class='text_search'></li>";
		html += "<li><label class='label_name'>房型</label><select name='roomType' class='text_search' id='roomType'></select></li>";
		html += "<li><label class='label_name'>来源</label><select name='source' id='source' class='text_search'></select></li>";
		html += "<li><label class='label_name'>担保类型</label><select name='guarantee' id='guarantee' class='text_search'><option value=''>--请选择担保类型--</option>";
		html += "<option value='1'>无担保</option><option value='2'>有担保</option></select></li>";
		html += "<li><label class='label_name'>预订人</label><input name='orderUser' id='orderUser' type='text' value='' class='text_search'></li>";
		html += "<li><label class='label_name'>预定手机</label> <input name='mphone' id='mphone' type='text' value='' class='text_search'></li>";
		html += "<li><label class='label_name'>状态</label><select name='status' id='status' class='text_search'><option value=''>--请选择状态类型--</option>";
		html += "<option value='1'>新订</option><option value='2'>未到</option></select></li></ul>";
		html += "<ul class='clearfix'><li><label class='label_name'>预定日期</label><input id='orderTime' name='orderTime' type='text' value='' class='date text_search'></li>";  
		html += "<li><label class='label_name'>至</label><input name='ordtimes' id='ordtimes' type='text' value='' class='date text_search'></li></ul>";  
		html += "<ul class='clearfix'><li><input type='button' value='确认' class='button_margin confirm' onclick='querydata()'></li></ul>";		
    	$(".margintop").html(html);
    	$(".high_header.fadeInDown").css({
			"height": "500px",
			"width": "800px"
		});
    	$(".button_margin.confirm").css({
			"margin-right": "8px"
		});
    	loadsearchroomtype();
    	loadordersource();
    	
    	$("#orderTime").on("click",function(){
        	laydate({
        	   	elem: '#orderTime'
        	});
		});
    	$("#ordtimes").on("click",function(){
    		laydate({
        	   	elem: '#ordtimes'
        	});
		});
    	
    	
	}
	$(".highsearch").html('高级查询');

}

$(function(){
	/**$(".date").datepicker({ dateFormat: "yy/mm/dd " });
	$("#starttime").css('font-size','0.9em');
	$("#endtime").css('font-size','0.9em');
	var now = new Date();    
    var year = now.getFullYear();       //年   
    var month = now.getMonth() + 1;     //月   
    var day = now.getDate();
    var time = year+"/"+month+"/"+day;**/
	//loadsearchroomtype();
	//loadordersource();
	//loadorderguarantee();
});


function loadsearchroomtype(){
	$.ajax({
         url: path + "/loadsearchroomtype.do",
		 type: "post",
		 data : {},
		 success: function(json) {
		 	var data = "<option value=''>房间类型</option>";
		 	$.each(json,function(index){
		 		data = data + "<option value='" +json[index]["ROOMTYPE"] + "'>" +json[index]["ROOMNAME"] + "</option>"
		 	});
		 	$("#roomType").html(data);
			/**$(".mySelect").select({
				width: "175px"
			});*/
		 },
		 error: function(json) {}
	});
}

function loadordersource() {
	$.ajax({
         url: path + "/queryordersource.do",
		 type: "post",
		 data : {},
		 success: function(json) {
		 	var data = "<option value=''>请选择类型</option>";
		 	$.each(json,function(index){
		 		data = data + "<option value='" +json[index]["content"] + "'>" +json[index]["paramName"] + "</option>"
		 	});
		 	$("#source").html(data);
			/**$(".mySelect").select({
				width: "175px"
			});*/
		 },
		 error: function(json) {}
	});
}

function loadorderguarantee() {
	$.ajax({
         url: path + "/queryorderguarantee.do",
		 type: "post",
		 data : {},
		 success: function(json) {
		 	var data = "<option value=''>请选择类型</option>";
		 	$.each(json,function(index){
		 		data = data + "<option value='" +json[index]["content"] + "'>" +json[index]["paramName"] + "</option>"
		 	});
		 	$("#source").html(data);
			/**$(".mySelect").select({
				width: "175px"
			});*/
		 },
		 error: function(json) {}
	});
}

$(".highsearch").on("click",function(){
	if($(".highsearch").html() == '返回上页'){
		/*var arrivalStart = $(window.frames["orderFrame"].document).find("#arrivalTime").val();
		var arrivalEnd = $(window.frames["orderFrame"].document).find("#arrtimes").val();
		var arrivalOrderUser = $(window.frames["orderFrame"].document).find("#orderUser").val();
		var arrivalUserPhone = $(window.frames["orderFrame"].document).find("#mphone").val();
		$("#orderFrame").attr("src",
				"orderInfoAll.do?movement=5&arrivalTime="+arrivalStart+"&arrtimes="+arrivalEnd+"&mphone="+arrivalUserPhone+"&orderUser="+arrivalOrderUser);*/
		var arrivalStart = $(window.frames["orderFrame"].document).find("#arrivalTimePrev").val();
		var arrivalEnd = $(window.frames["orderFrame"].document).find("#arrtimesPrev").val();
		var arrivalOrderUser = $(window.frames["orderFrame"].document).find("#orderUserPrev").val();
		var arrivalUserPhone = $(window.frames["orderFrame"].document).find("#mphonePrev").val();
		$("#orderFrame").attr("src",
				"orderInfoAllNew.do?movement=5&arrivalTime="+arrivalStart+"&arrtimes="+arrivalEnd+"&mphone="+arrivalUserPhone+"&orderUser="+arrivalOrderUser);
		$(".highsearch").html('高级查询');
		//$("#newButtonss").css("display","none");
		//$(".high_headerNB").css("display","none");
		$("#newButtonss").remove();
		$(".high_headerNB").remove();
		
	} else {
		$(".high_header").css("display","block");
		
		change(currentMove);
		
		var now = new Date();    
	    var year = now.getFullYear();       //年   
	    var month = now.getMonth() + 1;     //月   
	    if(currentMove == '5'){
	    	if (month.length = 1 ){
	        	month = "0" + month;
	        }
	    } else {
	    	 var day = now.getDate();
	 	    var time = year+"/"+month+"/"+day;
	 	    //$("#arrivalTime").val(time);
	 	    //$("#arrtimes").val(time);
	 	    $("#leaveTime").val(time);
	 	    $("#leavetimes").val(time);
	 	    $("#orderId").val("");
	 	    $("#saleStaff").val("");
	 	    $("#userCheckin").val("");
	 	    $("#guarantee").val("");
	 	    $("#orderUser").val("");
	 	    $("#source option:first").prop("selected","selected");
	 	    $("#mphone").val("");
	 	    $("#roomType option:first").prop("selected","selected");
	 	    $("#status option:first").prop("selected","selected");
	    }
	    
	   
	}
	
    
})

$(".imoocOld").on("click",function(){
	$(".high_header").fadeOut(500);
});


function close(){
	$(".high_header").css("display","none");
}

function closesss(){
	$("#selectTimeTemp").val("");
	$("#memberId").val("");
	$("#status").val("");
	$("#guarantee").val("");
	$(".high_headerNB").css("display","none");
}

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
	window.location.href="<%=request.getContextPath()%>/page/ipmspage/order/order_details.jsp";
}
$(".header_order_li").on("click",function(){
	$(this).addClass("active");
	$(this).siblings().removeClass("active");
});

function querydata(){
	var datas = $("#myForm").serialize();
	$("#orderFrame").attr("src","queryOrderData.do?" + datas);
	$(".high_header").css("display","none");
	$(".header_order_li").removeClass("active");
}
//返回上一页
function querydataInTab(){
	datas = $("#myFormNew").serialize();
	var orderIdPrev = $(window.frames["orderFrame"].document).find("#orderIdPrev").val();
	var roomTypePrev = $(window.frames["orderFrame"].document).find("#roomTypePrev").val();
	var sourcePrev = $(window.frames["orderFrame"].document).find("#sourcePrev").val();
	var guaranteePrev = $(window.frames["orderFrame"].document).find("#guaranteePrev").val();
	var orderUserPrev = $(window.frames["orderFrame"].document).find("#orderUserPrev").val();
	var mphonePrev = $(window.frames["orderFrame"].document).find("#mphonePrev").val();
	var statusPrev = $(window.frames["orderFrame"].document).find("#statusPrev").val();
	var orderTimePrev = $(window.frames["orderFrame"].document).find("#orderTimePrev").val();
	var ordtimesPrev = $(window.frames["orderFrame"].document).find("#ordtimesPrev").val();
	var arrivalTimePrev = $(window.frames["orderFrame"].document).find("#arrivalTimePrev").val();
	var arrtimesPrev = $(window.frames["orderFrame"].document).find("#arrtimesPrev").val();
	var movementPrev = $(window.frames["orderFrame"].document).find("#movementPrev").val();
	var turnToOldPagePrev = $(window.frames["orderFrame"].document).find("#turnToOldPagePrev").val();
	var selectTimePrev = $(window.frames["orderFrame"].document).find("#selectTimePrev").val();
	var memberIdPrev = $(window.frames["orderFrame"].document).find("#memberIdPrev").val();

	$("#orderFrame").attr("src",
			"queryOrderDataInTab.do?" + datas +"&orderIdPrev="
			+orderIdPrev+"&roomTypePrev="+roomTypePrev+"&sourcePrev="+sourcePrev+"&guaranteePrev="
			+guaranteePrev+"&orderUserPrev="+orderUserPrev+"&mphonePrev="+mphonePrev
			+"&statusPrev="+statusPrev+"&orderTimePrev="+orderTimePrev+"&arrivalTimePrev="+arrivalTimePrev
			+"&ordtimesPrev="+ordtimesPrev+"&arrtimesPrev="+arrtimesPrev
			+"&movementPrev="+movementPrev+"&turnToOldPagePrev="
			+turnToOldPagePrev+"&selectTimePrev="+selectTimePrev+"&memberIdPrev="+memberIdPrev);
	$(".header_order_li").removeClass("active");
	$("#selectTimeTemp").val("");
	$("#memberId").val("");
	$(".high_headerNB").css("display","none");
	$(".header_order_li:first").addClass("active");
}

function queryDataNew(){
	$("#orderFrame").attr("src","queryOrderData.do?" + $("#myForm").serialize()+"&movement=5");
	$(".high_header").css("display","none");
	$(".header_order_li").removeClass("active");
	$(".header_order_li:first").addClass("active");
}

$(".highsearchNB").on('click',function(){
	$("#orderTime").on("click",function(){
    	laydate({
    	   	elem: '#orderTime'
    	});
	});
	$("#ordtimes").on("click",function(){
		laydate({
    	   	elem: '#ordtimes'
    	});
	});
	})

/*$(".highsearchNB").on('click',function(){
				$(".high_headerNB").css("display","block");
				loadsearchroomtype();
				loadordersource();
				$("#orderTime").on("click",function(){
		        	laydate({
		        	   	elem: '#orderTime'
		        	});
				});
		    	$("#ordtimes").on("click",function(){
		    		laydate({
		        	   	elem: '#ordtimes'
		        	});
				});
				//将前面页面的值拿到上面input框里
				var selectTimeTemp = $(window.frames["orderFrame"].document).find("#selectTime").val();
				var memberIdTemp = $(window.frames["orderFrame"].document).find("#memberId").val();
				
				$("#selectTimeTemp").val(selectTimeTemp);
				$("#memberId").val(memberIdTemp);
				});*/

function INtabSearch(){
	$(".high_headerNB").css("display","block");
	loadsearchroomtype();
	loadordersource();
	$("#orderTime").on("click",function(){
    	laydate({
    	   	elem: '#orderTime'
    	});
	});
	$("#ordtimes").on("click",function(){
		laydate({
    	   	elem: '#ordtimes'
    	});
	});
	//将前面页面的值拿到上面input框里
	var selectTimeTemp = $(window.frames["orderFrame"].document).find("#selectTimePrev").val();
	var memberIdTemp = $(window.frames["orderFrame"].document).find("#memberId").val();
	
	$("#selectTimeTemp").val(selectTimeTemp);
	$("#memberId").val(memberIdTemp);
	$("#orderId").val("");
	$("#orderTime").val("");
	$("#ordtimes").val("");
	$("#guarantee option:first").prop("selected","selected");
	$("#source option:first").prop("selected","selected");
	$("#roomType option:first").prop("selected","selected");
	$("#status option:first").prop("selected","selected");
	
	
	
}				
				