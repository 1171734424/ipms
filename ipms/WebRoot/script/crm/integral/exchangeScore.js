function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}
function updateReserve(){
	var re = /^[0-9]*[1-9][0-9]*$/;
	var score = $("#score").val();
	var money = $("#money").val();
	if("" == score){
		showMsg("积分不可为空!");
		return false;
	}else{
		if(isNaN(score)){
			showMsg("积分请填写数字!");
			return false;
		}else if(!re.test(score)){
			showMsg("积分请填写大于0的数字!");
			return false;
		}
	}
	if("" == money){
		showMsg("金额不可为空!");
		return false;
	}else{
		if(isNaN(money)){
			showMsg("金额请填写数字!");
			return false;
		}else if(!re.test(money)){
			showMsg("金额请填写大于0的数字!");
			return false;
		}
	}
	$.ajax({
		url: "updateExchangeScoreRule.do",
		dataType: "json",
		type: "post",
		data: { 'score' : score,
                'money' : money
           		},
		success: function(json) {
		        showMsg("添加成功!");
		        window.setTimeout("window.location.reload(true)",1800);
			   
		},
		error:function(json){
				showMsg("添加失败!");
			    window.setTimeout("window.location.reload(true)",1800);
		}
	})
}
function del(){
	 $("#score").val("");
	 $("#money").val("");
}