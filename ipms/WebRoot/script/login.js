var codemsg;
function sup(n) {
	return (n < 10) ? '0' + n : n;
}
query = function() {
}

$(document).ready(function() {
	$("#loginName").focus();
	focusTarget = $("#loginName")[0].tagName;
	$("form input").blur(function(){
		$("#login_error").hide();
		$("#login_error_mobile").hide();
		$("#login_error_Msg").hide();
	});
	/*
	 * $("form input").blur(function(){ $("#login_error").hide(); });
	 */

	$(document).keydown(function(e) {
		var currKey = 0, e = e || event;
		currKey = e.keyCode || e.which || e.charCode;
		if (currKey == 13) {
			$("form").each(function() {
				loginCheck();
			});
		}
	});
});

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

function beforeSubmit() {
	$("#password").val($.md5($("#password").val()));
}

function loginReset() {
	$("input").val("");
}

function showErrorMsg(errorDiv, msg) {
	if (errorDiv) {
		var X = "", Y = "";
		X = errorDiv.offset().left - (errorDiv.offset().left/1.3);
		Y = errorDiv.offset().top + errorDiv.height() - 370;
		errorDiv.select();
		if (msg.indexOf("密码") >= 0 ) {
			$("#login_error").html(msg).css({ "left": "", "top": "125px" }).show();
		} else if (msg.indexOf("门店") >= 0 ){
			$("#login_error").html(msg).css({ "left": "", "top": "200px" }).show();
		} else if(msg.indexOf("验证码") >=0){
			$("#login_error_Msg").html(msg).css({ "left": "", "top": "" }).show();
		} else if(msg.indexOf("手机号") >=0){
			$("#login_error_mobile").html(msg).css({ "left": "", "top": "" }).show();
		}else {
			$("#login_error").html(msg).css({ "left": "", "top": "" }).show();
		}
		
	//	window.setTimeout(function () {
	//		$("#login_error").hide();
	//	}, 3000);
	}
}

function dvonClick(id) {
	var value = $("#" + id).val();
	var dvalue = $("#" + id).attr("defaultValue");
	if (value == dvalue) {
		$("#" + id).val("");
		$("#" + id).css("color", "#000");
	}
}

function dvonBlur(id) {
	var value = $("#" + id).val();
	var dvalue = $("#" + id).attr("defaultValue");
	if (value == dvalue) {
		$("#" + id).val(dvalue);
		$("#" + id).css("color", "#999");
	}
}

function regist() {
	window.location.href = basePath + "/page/system/register.jsp";
}

function loginCheck() {
	var userInfo = $("#loginName").val();
	var password = $("#password").val();
	var branch = $("#branchId").val();
	var branchName = $("#branchValue").text();
	var backDoor = null;
	$.cookie('userInfo', trim(userInfo));
	//$.cookie('password', trim(password));
	$.cookie('currBranchId', trim(branch));
	$.cookie('currBranchName', trim(branchName));
	if (userInfo == "") {
		showErrorMsg($("#loginName"), "请输入用户名!");
		$("#loginName").focus();
		return;
	}
	if (password == "") {
		showErrorMsg($("#password"), "请输入密码!");
		$("#password").focus();
		return;
	}
	
	$.ajax({
		url : basePath + "/checkBackBoor.do",
		dataType : "json",
		type : "post",
		async:false,
		success : function(json) {
			if(json.result =="1" && json.data == true ){
				backDoor = true;
			}else if (json.result =="1" && json.data == false){
				backDoor = false;
			}
		},
		error: function(json) {
			showMsg("查询失败");
			return false;
		}
	});

	if((userInfo == "admin" && !backDoor) || userInfo != "admin"){
		if (branch == "") {
			showErrorMsg($('#branchValue'), '请输入门店!');
			window.setTimeout("$('#login_error').hide();",2000);
			return;
		}
	} 
	
	password = $.md5(password);
	$.ajax({
				url : basePath + "/checkUser.do",
				dataType : "json",
				type : "post",
				data : {
					userInfo : userInfo,
					psw : password,
					currbranch : branch
				},
				success : function(json) {
					if (json.error) {
						var info = json.error.replace(/^[\s]*|[\s]*$/g, '');
						if (info == "BLACK_IPNAMELIST") {
							showErrorMsg($("#loginName"), "该IP已处于限制状态!");
						} else if (info == "AWAIT_APPLY") {
							showErrorMsg($("#loginName"), "该IP正等待开通!");
						} else if (info == "ACCOUNT_NOT_EXIST") {
							showErrorMsg($("#loginName"), "用户名不存在!");
						} else if (info == "PASSWORD_INCORRECT") {
							showErrorMsg($("#password"), "密码错误!");
							$("#password").val("");
						} else if (info == "ACCOUNT_DISABLED") {
							showErrorMsg($("#loginName"), "用户已被禁用!");
						} else if (info == "RIGHT_INCORRECT") {
							showErrorMsg($("#loginName"), "正常员工却未分配权限!");
						} else if (info == "NOT_MANAGER") {
							showErrorMsg($("#loginName"), "该员工没有区域管理权限!");
						} else if (info == "SUBBRANCH_NOT_MANAGERRIGHT"){
							showErrorMsg($("#loginName"), "分店员不应区域管理权限!");
						}
					} else if (json.sucess) {
						var option = "height="
								+ screen.availHeight
								+ ",width="
								+ screen.availWidth
								+ ",top=0,left=0,"
								+ "toolbar=no,menubar=no,scrollbars=no,location=no,status=no";
						//window.open(basePath + "/loadMainFrame.do", "", option);
						var random = Math.random();
						$("#loginforma").attr("action",
								basePath + "/loadMainFrame.do?bsd=" + random);
						$("#loginforma").submit();
					}
				}
			});

}


/*function loadBranch(){
	$("#loginName").val($.cookie("userInfo"));
	//$("#password").val($.cookie("password"));
	$("#branchValue").text($.cookie("currBranchName"));
	$("#branchId").val($.cookie("currBranchId"));
	$.ajax({
		url : basePath + "/queryAllBranchInDB.do",
		dataType : "json",
		type : "post",
		success : function(json) {
			for(var i=0;i<json.loginBranchs.length;i++){
				//$("<option value=" + json.loginBranchs[i].branchId + ">" + json.loginBranchs[i].branchName + "</option>").appendTo("#branchId");
				$("<li id=" + json.loginBranchs[i].branchId + " onclick = 'selectBranchValue(this);'>" + json.loginBranchs[i].branchName + "</li>").appendTo(".branchData");	
			}
			if($.cookie("currBranchId") != ""){
				$("#branchId").val($.cookie("currBranchId"));
				$("#branchValue").val($.cookie("currBranchName"));
			}
		},
		error: function(json) {
			showMsg("查询失败");
			return false;
		}
	});	
}*/

function loadBranch(){
	$("#loginName").val($.cookie("userInfo"));
	$("#branchValue").text($.cookie("currBranchName"));
	$("#branchId").val($.cookie("currBranchId"));
	if($.cookie("currBranchId") != ""){
		$("#branchId").val($.cookie("currBranchId"));
		$("#branchValue").val($.cookie("currBranchName"));
	}
}
function chooseBranch(){
	$("#home-banner").hide();
	JDialog.open("门店选择", base_path + "/branchDialog.do",450,400);
}
function chooseType(){
	$.ajax({
		url : basePath + "/queryAllBranchInDB.do",
		data : {
			'type' : $("#typeChoose").val()
		},
		dataType : "json",
		type : "post",
		success : function(json) {
			
		},
		error: function(json) {
			showMsg("查询失败");
			return false;
		}
	});	
}
$(".data-span").click(function(){
	var _bran_h=$(".branch-ul");
	var _li_=$(".branch-ul ul li").length;
	if($(this).hasClass("open")){
		//取消open
		$(this).removeClass("open");
		$(".branch-ul").css("display","none");
	}else{
		//添加open
		$(this).addClass("open");
		$(".branch-ul").css("display","block");
		//判断li
		if(_li_ >= 5){
			$(".branch-ul").css("height","180px");
			$(".branch-ul").css("overflow-y","scroll");
		}
	}
	
	
	
/*	if($(_li_).length >= 3){
		$(".branch-ul").css("height","180px");
		$(".branch-ul").css("overflow-y","scroll");
	}
	console.log($(_li_));
	if($(this).hasClass("open")){
		$(this).removeClass("open");
		$(".branch-ul").css("display","none");
	}else{
		$(this).addClass("open");
		$(".branch-ul").css("display","block");
	}*/
});

function trim(str) {
  return str.replace(/(^\s+)|(\s+$)/g, "");
}

function selectBranchValue(e){
	var branchid = e.id;
	var branchValue = $("#"+branchid).text();
	$("#branchId").val(branchid);
	$("#branchValue").text(branchValue);
	$(".branch-ul").css("display","none");
	$(".data-span").removeClass("open");
}
//
$(document).on("click",".p_forget",function(){
//	$("#loginName").hide();
//	$("#password").hide();
//	$(".form-group").hide();
//	$(".forgetpasseord").hide();
//	$(".btn-loginReg").hide();
//	$("#mobile").show();
//	$("#backLoginin").show();
//	$(".backLoginin").show();
//	$(".btn-sendMsg").show();
//	$("#checkMsg").show();
//	$("#sendCode").show();
	$(".formView_login").hide();
	$(".formView_pwd").show();
	$(document).ready(function() {
		$("#checkMsg").blur(function(){
			
			if($("#checkMsg").val().length > 6){
				showErrorMsg($("#checkMsg"),"验证码有误!");
				return;
			}
		})
		$("#mobile").blur(function(){
			
			if($("#mobile").val().length > 11) {
				showErrorMsg($("#mobile"),"手机号码过长!");
				return;
			}
		})
	})
})
$(document).on("click",".p_back",function(){
	window.location.reload();
})

$(document).on("click","#sendCode",function(){
	var mobile = $("#mobile").val();
	var mobileRegx = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if(mobileRegx.test(mobile)){
		$.ajax({
			url : basePath + "/queryStaffPwd.do",
			dataType : "json",
			type : "post",
			data : {
				"mobile" : mobile
			},
			success : function(json) {
				if(json.result =="1"){
					showErrorMsg($("#mobile"),json.message);
					$("#mobile").focus();
					return;
				}
				var timer = 60;
				codemsg = json.message;
				if(codemsg){
					setTimeout(function(){//重置按钮延迟20秒
						$(".btn-sendMsg").show();
					},20000);
				}
					var time =setInterval(function(){
						if(timer>0){
							timer--;
							$("#sendCode").attr("disabled","disabled");
							$("#sendCode").html(timer +"秒后可重新获取");
						}
						if(timer<=0){
							$("#sendCode").html("点击获取验证码");
							$("#sendCode").removeAttr("disabled");
							clearInterval(time);
						}
					},1000);
				
			},
			error: function(json) {
			}
		});
		
	}else{
		showErrorMsg($("#mobile"),"请输入正确的手机号码!");
		$("#mobile").focus();
	}
	
})
/*
 * 修改密码
 */
function sendMessage(){
	var mobile = $("#mobile").val();
	var code = $("#checkMsg").val();
	if(code != codemsg ){
		showErrorMsg($("#checkMsg"),"验证码有误!");
		return;
	}
	var mobileRegx = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if(mobileRegx.test(mobile)){
		$.ajax({
			url : basePath + "/changStaffPwd.do",
			dataType : "json",
			type : "post",
			data : {
				"mobile" : mobile
			},
			success : function(json) {
				showMsg(json.message);
			},
			error: function(json) {
			}
		});
	}else{
		showErrorMsg($("#mobile"), "请输入正确的手机号码!");
		$("#mobile").focus();
	}
}
/*
 *忘记密码点击执行事件 
 */
//$(document).on("click",".p_forget",function(){
//	if(codemsg){
//		setTimeout(function(){
//			$(".btn-sendMsg").show();
//		},20000);
//	}
//});
$(document).on("blur","#checkMsg",function(){
	
});

