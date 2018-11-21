// 修改可预约房间数量1
function setcleanrestAmount() {
	var re = /^[0-9]{1,2}$/ ;
	var time = $("#time").val();
	var timearea = $("#timearea").val();
	var restamount = $("#restamount").val();
	if(""==restamount){
		window.parent.showMsg("剩余可保洁房间数不能为空!");
		return false;
	}else{ 
		if(isNaN(restamount)){
			window.parent.showMsg("请填写数字!");
		    return false;	
	     }else if(!re.test(restamount)){
	    		 //restamount.length>2 || restamount<0){
		    window.parent.showMsg("请输入100以内的正整数!");
		    return false;
	   }
	}
	$.ajax({
		url : path + "/setamount.do",
		dataType : "json",
		type : "post",
		data : {
			'time' : time,
			'timearea' : timearea,
			'restamount' : restamount
		},
		success : function(json) {
			window.parent.showMsg(json.message);
			window.setTimeout("window.parent.location.reload(true)", 1800);
		},
		error : function(json) {
			window.parent.showMsg("设置失败!");
			window.setTimeout("window.parent.location.reload(true)", 1800);
		}
	});

}
function setcleanrestAmount2() {
	var re = /^[0-9]{1,2}$/ ;

	var restamount2 = $("#restamount2").val();
	if(""==restamount2){
		window.parent.showMsg("保洁初始值不能为空!");
		return false;
	}else{ 
		if(isNaN(restamount2)){
			window.parent.showMsg("请填写数字!");
		    return false;	
	     }else if(!re.test(restamount2)){
	    		 //restamount.length>2 || restamount<0){
		    window.parent.showMsg("请输入100以内的正整数!");
		    return false;
	   }
	}
	$.ajax({
		url : path + "/setamountinit.do",
		dataType : "json",
		type : "post",
		data : {

			'restamount' : restamount2
		},
		success : function(json) {
			window.parent.showMsg(json.message);
			window.setTimeout("window.parent.location.reload(true)", 1800);
		},
		error : function(json) {
			window.parent.showMsg("设置失败!");
			window.setTimeout("window.parent.location.reload(true)", 1800);
		}
	});

}
// 修改可预约房间数量2
function updateAmount() {
	var time = $("#date").val();
	var inputdate = new Date(Date.parse(time));
	var today = new Date();
	var year = today.getFullYear();
	var month = today.getMonth() + 1;
	var day = today.getDate()
	var new_today = new Date(year, month - 1, day)
	var restamount = $("#restamount").val();
	// 日期校验
	if (inputdate < new_today) {
		showMsg("日期不能小于当天!");
		return false;
	} else if (inputdate > getLastDay(year, month)) {
		window.parent.showMsg("日期不能大于当月!");
		return false;

	} else if (time == "") {
		window.parent.showMsg("请填写日期!");
		return false;
	} else if (restamount == "") {
		window.parent.showMsg("请填写可预约次数!");
		return false;
	}
	var timearea = $("#timeArea").val();
	$.ajax({
		url : path + "/setamount.do",
		dataType : "json",
		type : "post",
		data : {
			'time' : time,
			'timearea' : timearea,
			'restamount' : restamount
		},
		success : function(json) {
			window.parent.showMsg(json.message);
			window.setTimeout("window.parent.location.reload(true)", 1800);
		},
		error : function(json) {
			window.parent.showMsg("设置失败!");
			window.setTimeout("window.parent.location.reload(true)", 1800);
		}
	})
}

// 处理保洁申请
function dealCleanApply(btn,status) {
	var cleanApplicationId = $(btn).parent().parent().children("td").eq(0).html();
	var getCleanTime = $(btn).parent().parent().children("td").eq(3).html();
	var getTimeArea = $(btn).parent().parent().children("td").eq(4).html();
	var branchId = $("#branchId").val();
	var roomid = $(btn).parent().parent().children("td").eq(7).html();
	var memberid = $(btn).parent().parent().children("td").eq(12).html();
	
if(status == 1){
	
	    var inputdate = new Date(Date.parse(getCleanTime));
	    var today = new Date();
	    var year = today.getFullYear(); 
	    var month = today.getMonth() + 1;
	    var day = today.getDate();
	    var new_today = new Date(year,month -1,day);

	//日期校验
	if(inputdate < new_today){
		window.parent.showMsg("日期不能小于当天!");
		return false;
	}
	$.ajax({
		url : base_path + "/queryrest.do",
		dataType : "json",
		type : "post",
		data : {
			'cleanTime' : getCleanTime,
			'timeArea' : getTimeArea,
			'branchid' :branchId
		},
		success : function(json) {
		if(json.result == 1){
			var amount = json.data.count;
			var branchname = json.data.branchname;
			//console.log(amount)
			if (amount > 0) {
				JDialog.open(
								"安排保洁人员",
								base_path
										+ "/page/ipmspage/leftmenu/handleapply/arrangeClean.jsp?cleanApplication="
										+ cleanApplicationId
										+ "&getCleanTime="
										+ getCleanTime + "&branchId="
										+ branchId + "&getTimeArea="
										+ getTimeArea + "&amount="
										+ amount + "&roomid=" + roomid+"&branchname="+branchname+"&memberid="+memberid,
								825, 250);
			} else {
				//console.log("进入小于0的情况")
				showMsg(json.message);
			}
		}else{
			showMsg(json.message);
		}
			
		},
		error : function(json) {
			showMsg("查询剩余房间数时失败!");
			window.setTimeout("window.parent.location.reload(true)",
					1800);
		}
	});

	
}else{
	
	$.ajax({
		url : base_path + "/canclecleanapply.do",
		dataType : "json",
		type : "post",
		data : {
			'cleanApplicationId' : cleanApplicationId
			
		},
		success :function (json){
			window.parent.showMsg(json.message);
			window.setTimeout("window.parent.location.reload(true)", 1800);
		},
		error : function (json){
			
		}
	});
}
	
}
function showDetail(e){
	var cleanApplicationId = $(e).children("td").eq(0).html();
	var status = $(e).children("td").eq(11).html();
	window.parent.JDialog.open("保洁申请详情",base_path+"/showcleanapplydetail.do?cleanApplicationId="+cleanApplicationId
			+"&status="+status,1000,250);

}

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}
