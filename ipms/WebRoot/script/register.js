
$(document).ready(function() {
	init();
});

function init() {
	$.ajax({
		url: basePath + "/registerInfoInit.do",
		type: "post",
		dataType: "json",
		success: function(json) {
			if(json.user) {
				$("#rgb").hide();
				$("#bkb").show();
				
				setInfo(json.user);
				$("#confirmPassword").val($("#psw").val());
				
				if (json.param) {
					$("#departName").val(json.param["paraName"]);
				}
			} else if (json.error) {
				PageLoading.beginPageLoading("数据异常,请联系管理员...");
			}
				
		}
	});
}

function setInfo(info) {
	$.each(info, function(index) {
		if($("#" + index).length > 0) {
			$("#" + index).val(info[index]);
		}
	});
}

function doBack() {
	window.location.href = basePath + "/page/ipmspage/login/login.jsp";
}

function beforeSubmit() {
	$("#psw").val($.md5($("#psw").val()));
	$("#confirmPassword").val($.md5($("#confirmPassword").val()));
}

function doRegister() {
	if (doRegisterCheck()) {
		PageLoading.beginPageLoading("用户注册,申请中...");
		beforeSubmit();
		$.ajax({
			url: basePath + "/registerInfo.do",
			type: "post",
			dataType: "json",
			data: $("#registerForm").serialize(),
			success: function(json) {
				if (json.result == 0) {
					PageLoading.beginPageLoading("注册失败！");
					window.setTimeout("PageLoading.endPageLoading()", 2000);
				} else {
					PageLoading.beginPageLoading("注册成功！正在跳转！");
					window.setTimeout("doBack()", 2000);
				}
			},
			error: function(json) {
				PageLoading.beginPageLoading("注册失败！");
				window.setTimeout("PageLoading.endPageLoading()", 2000);
			}
		});
	}
}

function doRegisterCheck() {
	var infos = $("input[type=text]"), result = true;
	$.each(infos, function(index) {
		if(!$(infos[index]).val()) {
			showErrorMsg($(infos[index]), $(infos[index]).attr("title") + "不能为空！");
			result = false;
			return false;
		}
	});
	
	if (result) {
		if (!/^[0-9a-zA-Z]{6,32}$/g.test($("#loginName").val())) {
			showErrorMsg($("#loginName"), "用户名输入不正确！");
			result = false;
		} else if ($("#psw").val().length < 6 || $("#psw").val().length > 14) {
			showErrorMsg($("#psw"), "密码长度不正确！");
			result = false;
		} else if ($("#psw").val() != $("#confirmPassword").val()) {
			showErrorMsg($("#confirmPassword"), "密码输入不一致！");
			result = false;
		} else if (!/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[x])$)$/i.test($("#IDCard").val())) {
			showErrorMsg($("#IDCard"), "请输入正确的身份证号码！");
			result = false;
		} else if (!/^((13|15|17|18)+\d{9})$/i.test($("#contactNo").val())) {
			showErrorMsg($("#contactNo"), "请输入正确的手机号码！");
			result = false;
		} else if (!/^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+(\.[a-zA-Z]{2,3})+$/i.test($("#email").val())) {
			showErrorMsg($("#email"), "请输入正确的邮箱！");
			result = false;
		}
	}
	
	return result;
}

function showErrorMsg(errorDiv, msg) {
	if(errorDiv) {
		var X = "",Y = "";
		Y = errorDiv.position().top + errorDiv.height();
		X = errorDiv.position().left + 20;
		errorDiv.select();
		$("#registerError").html(msg).css({ "left": X, "top": Y }).show();
		window.setTimeout(function () {
			$("#registerError").hide();
		}, 3000);
	}
}

