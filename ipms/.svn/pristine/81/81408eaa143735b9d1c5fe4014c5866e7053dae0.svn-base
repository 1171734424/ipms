var old_password;
var new_password;
var confirm_password;

$(document).ready(function() {
	//bindEvent();
	$("#psw").focus();
	$("form input").blur(function() {
		$("#error_info").hide();
	});
	
	$(document).keydown(function(event) { 
		if (event.keyCode == 13) { 
			$("form").each(function() {
				passwordcheck(); 
			}); 
		} 
	});
});

function passwordcheck() {
	old_password = $("#psw").val();
	new_password = $("#new_psw").val();
	confirm_password = $("#cfm_psw").val();
	
	var checkInfo, element;
	
	if(!old_password){
		checkInfo = "请输入原密码!";
		element = $("#psw");
	} else if (!new_password) {
		checkInfo = "请输入新密码!";
		element = $("#new_psw");
	} else if (!confirm_password) {
		checkInfo = "请再次确认新密码!";
		element = $("#cfm_psw");
	} else if (new_password != confirm_password) {
		checkInfo = "密码不一致!";
		element = $("#cfm_psw");
	} else if (new_password.length < 6) {
		checkInfo = "密码长度不能低于6位数!";
		element = $("#new_psw");
	}
	/*补充密码格式验证
		else if () {
			etc.....
		}
	*/
	
	if (checkInfo) {
		element.focus();
		showErrorMsg(element, checkInfo);
		return false;
	}

	old_password = $.md5(old_password);
	new_password = $.md5(new_password);
	
	$.ajax({
		url: basePath + "/change_password.do",
		dataType: "json",
		type: "post",
        data: { oldPassword: old_password, newPassword: new_password },
		success: function(json) {
			if (json.error) {
				var info = json.error.replace(/^[\s]*|[\s]*$/g,'');
				if (info == "PASSWORD_INCORRECT"){
					$("#psw").focus();
					showErrorMsg($("#psw"), "密码错误!");
				}
			} else if (json.sucess) {
				//if(confirm("密码修改成功!")) {
					showMsg("密码修改成功");
					//window.parent.setTimeout("window.parent.location.reload('true')", 1800);
					setTimeout("enSure()",1000);
					window.parent.$("#psw").val("");
					window.parent.$("#new_psw").val("");
					window.parent.$("#cfm_psw").val("");
					window.parent.$("#psw").focus();			
				//}
			}
		}
	});
}


function enSure(){
	window.location.href=basePath + "/password.do";
	window.close();
}

function reset() {
	$("input").val("");
}

function showErrorMsg(errorDiv,msg) {
	if(errorDiv) {
		var X = "",Y = "";
		Y = errorDiv.offset().top;
		X = errorDiv.offset().left;
		var height = errorDiv.height();
		errorDiv.select();
		$("#error_info").html(msg).css({"left":X, "top":(Y+height)}).show();
	}
}


	
	var icons = $(".one");
	$(icons).on("mouseup", function() {
		$(icons).attr("class","fa fa-eye one");
		$("#" + $(this).attr("for")).attr("type", "text");
	});
	$(icons).on("mouseout", function() {
		$(icons).attr("class","fa fa-eye-slash one");
		$("#" + $(this).attr("for")).attr("type", "password");
	});
	
	var icons_two = $(".two");
	$(icons_two).on("mouseup", function() {
		$(icons_two).attr("class","fa fa-eye two");
		$("#" + $(this).attr("for")).attr("type", "text");
	});
	$(icons_two).on("mouseout", function() {
		$(icons_two).attr("class","fa fa-eye-slash two");
		$("#" + $(this).attr("for")).attr("type", "password");
	});
	
	var icons_three = $(".three");
	$(icons_three).on("mouseup", function() {
		$(icons_three).attr("class","fa fa-eye three");
		$("#" + $(this).attr("for")).attr("type", "text");
	});
	$(icons_three).on("mouseout", function() {
		$(icons_three).attr("class","fa fa-eye-slash three");
		$("#" + $(this).attr("for")).attr("type", "password");
	});
	
	
	
function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}	
	
	
	
	
	
	
	
	