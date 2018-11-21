function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg, fn);
		}
	}
function updateScore(){
	var integration = $("#integration").val();
	var accountid = $("#accountid").val();
	if("" == integration){
		showMsg("积分不可为空!");
		return false;
	}else if(isNaN(integration)){
		window.parent.showMsg("请填写数字!");
		return false;
	}
	$.ajax({
		url: "changeIntegration.do",
		dataType: "json",
		type: "post",
		data: { 'integration' : integration,
				'accountid' : accountid
           		},
		success: function(json) {
				console.log(json.message)
		        window.parent.showMsg(json.message);
		        window.setTimeout("window.parent.location.reload(true)",1800);
			   
		},
		error:function(json){
				window.parent.showMsg("添加失败!");
			    window.setTimeout("window.parent.location.reload(true)",1800);
		}
	})
}