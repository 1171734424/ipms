
function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}
function queryMember(){
	var msoidcard = $("#msoidcard").val();
	var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	var idcard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	if(msoidcard.length > 11){
		if(!idcard.test(msoidcard)){
			showMsg("手机号或身份证输入错误!");
			return false;
		}
	}else{
		if(!mobile.test(msoidcard)){
			showMsg("手机号或身份证输入错误!");
			return false;
		}
	}
	$.ajax({
		url: "selectMemberInfo.do",
		dataType: "json",
		type: "post",
		data: { 'msoidcard' : msoidcard
           		},
		success: function(json) {
			if(json.result == 1){
				showMsg(json.message);
			}else{
				$("#msIframe").attr("src","queryMemberInfo.do?msoidcard="+msoidcard);
			}
		},
		error:function(json){
			showMsg("添加失败!");
		    window.setTimeout("window.location.reload(true)",1800);
		}
	})
}

function openMemberRank(){
	var accountId = $("#accountId").val();
	var memberId = $("#memberId").val();
	var memberRank = $("#memberRankId").val();
	if(memberRank == '0'){
		showMsg("公司会员不可以升级!");
	}else if(memberRank == '1'){
		showMsg("请注册会员!");
	}else if(memberRank == '5'){
		showMsg("请查看升级黑钻会员资格!");
	}else if(memberRank == '6'){
		showMsg("该会员已到当前最高级别!");
	}else{
		JDialog.open("会员升级", base_path + "/memberUpGrade.do?accountid="+accountId+"&memberid="+memberId,600,450);
	}
}