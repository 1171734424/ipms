var checkuserpath = getRootPath();
//getallmemberrank();
//loadCheckuser(status);

/**
 * checkinType代表查订单入住人，或者房单入住人
 * 1代表订单入住人
 * 2代表房单入住人
 * checkDo代表动作是否执行
 * 1代表执行
 * 0代表不执行
 */
var checkinType = "1";
var checkDo = "1";

/**
 * 
 * @param type 传入参数checkinType
 * @param action 传入参数checkDo
 * @return
 */
function initcheckusercss(type, action){
	if(type) checkinType = type;
	if(action) checkDo = action;
	loadCheckuser();
	getallmemberrank();
	checkfunctioncolor();
}

function checkfunctioncolor(){
	if(checkDo == 1){
		$(".checkuser_bt .button_margin").addClass("button_color");
	}
	else{
		//$(".checkuser_bt .button_margin").removeClass("button_color");
		$(".checkuser_bt .button_margin").addClass("button_color_cancel");
	}
	
}

function showcheck(){
	alert(checkuseroption);
}

function interfacecheckuser(){
	/*	$.getJSON(checkuserpath + "/page/ipmspage/room_statistics/checkuserdata.json", function (data){
		const Rand = Math.random();
		const Range = data.length - 1;
		const index = 0 + Math.round(Rand * Range);
		const checkuserdata = data[index];
	$.each(data, function(index){
			if(data[index].idcard == '632624197607224882'){
				checkuserdata = data[index];
			}
		});
	})*/
	var checkuserdata = "";
	addCheckuser(checkuserdata);
}

function getallmemberrank(){
	$.ajax({
		 url: checkuserpath + "/getallmemberrank.do",
		 type: "post",
		// data : {checkuserid : checkuserid},
		 success: function(json) {
			 $.each(json.data, function(index){
				 $("#memberrankname").append("<option value='" + json.data[index].memberRank + "' >" + json.data[index].rankName+"</option>");
			 });
		 },
		 error: function(json) {}
	});
}

/** 入住人增删查  **/
function loadCheckuser(){
	$.ajax({
		 url: checkuserpath + "/loadAllCheckuser.do",
		 type: "post",
		 data : {
			checkid : checkid,
			checkinType : checkinType
		 },
		 success: function(json) {
			 if(json.data){
				 json.data.sort(function(a, b){
					 return a.checkuserType - b.checkuserType;
				 });
			 }
			if(json.result == 1){
				var childnode = "";
				$.each(json.data, function(index){
					if(index == 0){
						childnode = childnode + "<li class='check_name active" + 
						adddl_status(json.data[index].status) + "'><label style='display: none'>"+ 
						json.data[index].checkuserId +"</label><span>" + 
						addStar(json.data[index].checkuserType) + "<span>" + 
						beyondContent(json.data[index].checkuserName, 4) +"</span></span></li>";
						loadCustomer(json.data[index].checkuserId);
					}else{
						childnode = childnode + "<li class='check_name" +
						adddl_status(json.data[index].status) + "'><label style='display: none'>"+ 
						json.data[index].checkuserId +"</label><span>" + 
						addStar(json.data[index].checkuserType) + "<span>" + 
						beyondContent(json.data[index].checkuserName, 4) +"</span></span></li>";
					}
				});
			}
			
			$("#guest").html(childnode);
			bindcustomerclick();
		 },
		 error: function(json) {}
	});
}

function adddl_status(status){
	return status == 0 ? " dl_status " : "";
}

function addStar(checkuserType){
	return checkuserType == 1 ? "<i class='fa fa-star'></i>" : "";
}

function loadCustomer(checkuserid){
	$.ajax({
		 url: checkuserpath + "/loadGuestData.do",
		 type: "post",
		 data : {checkuserid : checkuserid},
		 success: function(json) {
			$("#username").val(json.checkuserName);
			$("#gender").val(json.gender);
			$("#checkphone").val(json.mobile);
			$("#idcard").val(json.idcard);
			//$("#memberid").val(json["member"]["memberId"]);
			$("#memberrankname").val(json.rankName);
			$("#address").val(json.address);
			$("#customerreamrk").val(json.remark);
		 },
		 error: function(json) {}
	});
}

/*增加客人*/
function addCheckuser(data){
	//判断动作
	if(checkDo != "1")
		return false;
	if(!data){
		var searchMessage = $("#searchmembercondotion").val();
	}
	if(searchMessage){
		//查找框输入了手机号或者身份证号
		if(searchMessage.length < 13){
			if(!mobReg.test(searchMessage)){
				$("#checkinMessage").text("不是有效的手机号!");
				setTimeout("$('#checkinMessage').text('')", 3000);
				return false;
			}
		}
		else {
			if(!idcardReg.test(searchMessage)){
				$("#checkinMessage").text("身份证不合法!");
				setTimeout("$('#checkinMessage').text('')", 3000);
				return false;
			}
		}
	}
	else{
		//查找框未输入信息，由接口刷入
		
	}
	var username = data["username"];
	var sex = data["sex"];
	var idcard = data["idcard"];
	if(isHaveNewone())
		return false;
	$.ajax({
		 url: checkuserpath + "/addCheckuser.do",
		 type: "post",
		 data : {
			username : username,
			sex : sex,
			idcard : idcard,
			checkid : checkid,
			searchMessage : searchMessage,
			checkinType : checkinType
		 },
		 success: function(json) {
			if(json.result == 1){
				//添加新的入住人
				var strdnode = "<li class='check_name active'><label style='display: none'>"+ 
							json.data.checkuserId +"</label><span>" + 
							addStar(json.data.checkuserType) + "<span>" + 
							beyondContent(json.data.checkuserName, 4) +"</span></span></li>";
				var childnode = $(strdnode);
				var oUl =$(".check_ul");
				if( oUl.length == 1 && !oUl.find("label:hidden").text())
					oUl.empty();
				oUl.append(childnode);
				$(childnode).siblings().removeClass("active");
				loadCustomer(json.data.checkuserId);
				bindcustomerclick();
				//showMsg(json.message);
			}
			else if (json.result == 0){
				$("#checkinMessage").text(json.message);
				setTimeout("$('#checkinMessage').text('')", 3000);
			}
			else if (json.result == 2){
				//跳到已经存在的入住人tab上
				var oUl =$(".check_ul");
				var lis = oUl.find("li");
				$.each(lis, function(index){
					var dlli = $(lis[index]);
					var kk = dlli.find("label");
					var checkuserid = dlli.find("label:hidden").text();
					if(checkuserid == json.data.checkuserId){
						if(dlli.hasClass("dl_status")){
							dlli.removeClass("dl_status");
							dlli.addClass("active");
							dlli.siblings().removeClass("active");
						}
						return false;
					}
				});
			}
		 },
		 error: function(json) {}
	});
}

//手动添加入住人,跳出tab新页面
function addCustomerHand(){
	//判断动作
	if(checkDo != "1")
		return false;
	var countnewguest = 0;
	$.each($(".check_ul li"),function(index){
		if($($(".check_ul li")[index]["childNodes"][0]).html() == ''){
		countnewguest = countnewguest + 1;
		}
	});
	if (countnewguest < 1){
		var childnode=$("<li class='check_name'><label style='display: none'></label><span>新客人</span></li>");
		$(".check_ul").append(function(n){
			return childnode;
		});
		$(childnode).addClass("active");
		$(childnode).siblings().removeClass("active");
		resetCustomer();
		modifiable();
		bindcustomerclick();
	}
	if (countnewguest == 1){
		var newGuest = $(".check_ul li:last");
		$(newGuest).addClass("active");
		$(newGuest).siblings().removeClass("active");
		resetCustomer();
		modifiable();
		bindcustomerclick();
	}
}

//绑定onclick事件，加颜色和刷新数据
function bindcustomerclick(){
	
	//先解除之前的绑定事件
	$(".check_ul li").unbind();
	$(".check_ul li").on("click",function() {
		//if($(this).hasClass("dl_status"))
		//	return false;
		$(this).addClass("active");
		$(this).siblings().removeClass("active");
		if( $(this["childNodes"][0]).html() == ''){
			resetCustomer();
			modifiable();
		}else {
			loadCustomer($(this["childNodes"][0]).html());
			unmodifiable();
		}
	});
}

//删除入住人，更改状态，同时更改颜色
function deleteCustomer(){
	//判断动作
	if(checkDo != "1")
		return false;
	var msg = "";
	if(isHaveNewone())
		return false;
	var oUl =$(".check_ul");
	var datamem = oUl.find("li");
	var b = oUl.find(".active");
	var i = b.find("i");
	if(b.hasClass("dl_status")){
		msg = "当前入住人已删除!";
		showMsg(msg);
		return false;
	}
	if(i.length >0 ){
		msg = "主客人无法删减!";
		showMsg(msg);
		return false;
	}
	var checkuserId = b.find("label:hidden").text();
	if(!checkuserId)
		return false;
	$.ajax({
		 url: checkuserpath + "/dlcheckuser.do",
		 type: "post",
		 data : {
			checkuserId : checkuserId,
			status : "0"
		 },
		 success: function(json) {
			if(json.result == 1){
				b.addClass("dl_status");
			}else if(json.result == 0){
				showMsg(json.message);
			}
		 },
		 error: function(json) {}
	});
}

//手动添加客人入住按钮
function checkusercheckin(){
	if(isNull($("#username"), "入住人姓名"))
		return false;
	if(isSelected($("#memberrankname"), "会员等级"))
		return false;
	if($("#checkphone").val()){
		if(!isMobile($("#checkphone").val()))
			return false;
	}
	if(!isIdcard($("#idcard").val()))
		return false;
	if(!checkLength($("#address").val(), "住址：", 30))
		return false;
	if(!checkLength($("#customerreamrk").val(), "备注：", 100))
		return false;
	$.ajax({
        url: checkuserpath + "/addcustomerHand.do",
		 type: "post",
		 data : $("#checkuserinfo").serialize() + "&checkinType=" + checkinType,
		 success: function(json) {
				//添加的新的入住人
				if(json.result == 1){
					var oUl =$(".check_ul");
					var newli = oUl.find("li:last");
					newli.find("label:hidden").text(json.data.checkuserId);
					newli.find("span").empty();
					if(json.data.checkuserType == 1)
						newli.find("span").append("<i class='fa fa-star'></i>");
					newli.find("span").append("<span>" + json.data.checkuserName + "</span>");
					loadCustomer(json.data.checkuserId);
					bindcustomerclick();
					unmodifiable();
				}
				else if (json.result == 0){
					showMsg(json.message);
					cnacelcustom();
				} else if (json.result == 3){
					//实名校验未通过
					showMsg(json.message);
				}
				//已存在入住人，重新入住了一遍
				else if (json.result == 2){
					var oUl =$(".check_ul");
					var lis = oUl.find("li");
					$.each(lis, function(index){
						var dlli = $(lis[index]);
						var kk = dlli.find("label");
						var checkuserid = dlli.find("label:hidden").text();
						if(checkuserid == json.data.checkuserId){
							if(dlli.hasClass("dl_status")){
								dlli.removeClass("dl_status");
							}
							dlli.addClass("active");
							dlli.siblings().removeClass("active");
							cnacelcustom("recheckin");
							return false;
						}
					});
				}
		 },
		 error: function(json) {}
	});
}

//手动添加客人取消按钮
function cnacelcustom(e){
	var oUl =$(".check_ul");
	var newli = oUl.find("li:last");
	var li  = oUl.find("li");
	if(li.length > 1){
		newli.remove();
		newli = oUl.find("li:last");
		var checkuserid = newli.find("label:hidden").text();
		if(!e){
			newli.addClass("active");
		}
		loadCustomer(checkuserid);
		unmodifiable();
	}else if(li.length == 1){
		if(li.find("i").length == 1)
			unmodifiable();
	}
}

//查找入住人会员信息,并添加入住人
function searchmember(){
	if(checkDo != 1)
		return false;
	if(isHaveNewone())
		return false;
	var datamember = $("#searchmembercondotion").val();
	if(isNull($("#searchmembercondotion"), '会员查询：'))
		return false;
	$.ajax({
         url: checkuserpath + "/searchmember.do",
		 type: "post",
		 data : {
		 	datamember : datamember,
		 	checkid : checkid,
			checkinType : checkinType
		 	},
		 success: function(json) {
				if(json.result == 1){
					var strdnode = "<li class='check_name active'><label style='display: none'>"+ 
								json.data.checkuserId +"</label><span>" + 
								addStar(json.data.checkuserType) + "<span>" + 
								json.data.checkuserName +"</span></span></li>";
					var childnode = $(strdnode);
					var oUl =$(".check_ul");
					if( oUl.length == 1 && !oUl.find("label:hidden").text())
						oUl.empty();
					oUl.append(childnode);
					$(childnode).siblings().removeClass("active");
					loadCustomer(json.data.checkuserId);
					bindcustomerclick();
					//showMsg(json.message);
				}
				else if (json.result == 0){
					showMsg(json.message);
					/*var oUl =$(".check_ul");
					var lis = oUl.find("li");
					$.each(lis, function(index){
						var dlli = $(lis[index]);
						var kk = dlli.find("label");
						var checkuserid = dlli.find("label:hidden").text();
						if(checkuserid == json.data.checkuserId){
							if(dlli.hasClass("dl_status")){
								dlli.removeClass("dl_status");
								dlli.addClass("active");
								dlli.siblings().removeClass("active");
							}
							return false;
						}
					});*/
					
					
				}
				else if (json.result == 2){
					var oUl =$(".check_ul");
					var lis = oUl.find("li");
					$.each(lis, function(index){
						var dlli = $(lis[index]);
						var kk = dlli.find("label");
						var checkuserid = dlli.find("label:hidden").text();
						if(checkuserid == json.data.checkuserId){
							if(dlli.hasClass("dl_status")){
								dlli.removeClass("dl_status");
								dlli.addClass("active");
								dlli.siblings().removeClass("active");
							}
							return false;
						}
					});
				}
		 },
		 error: function(json) {}
	});
}

function resetCustomer(){
	$(".customer_info input").val("");
	$("#memberrank").html("");
	$("#address").val("");
	$("#customerreamrk").val("");
}

function unmodifiable(){
	$(".customer_info input").attr("disabled","disabled");
	$(".customer_info select").attr("disabled","disabled");
	//$("#memberrank").attr("disabled","disabled");
	$("#address").attr("disabled","disabled");
	$("#customerreamrk").attr("disabled","disabled");
	$(".checkuser_wrapper .checkuser_button_right .addhand").hide();
	$(".handleguest").hide();
}

function modifiable(){
	$(".customer_info input").removeAttr("disabled");
	$(".customer_info select").removeAttr("disabled");
	//$("#memberrank").removeAttr("disabled");
	$("#address").removeAttr("disabled");
	$("#customerreamrk").removeAttr("disabled");
	$(".checkuser_wrapper .checkuser_button_right .addhand").show();
	$(".handleguest").show();
}

function isHaveNewone(){
	var countnewguest = 0;
	var newGuest;
	$.each($(".check_ul li"),function(index){
		if($($(".check_ul li")[index]["childNodes"][0]).html() == ''){
		newGuest = $($(".check_ul li")[index]);
		countnewguest = countnewguest + 1;
		}
	});
	if (countnewguest == 1){
		if(newGuest.find("i").length == 0){
			showMsg("正在手动添加新客人!");
			return true;
		}else{
			return false;
		}
	}else if(countnewguest == 0){
		return false;
	}
}

function numCheckuser(){
	var lis =$(".check_ul li");
	var numchecks = 0;
	$.each(lis, function(index){
		if($(lis[index]).find("label:hidden").text() != ""){
			numchecks = numchecks + 1;
		}
	})
	return numchecks;
}

function sethostCustomer(){
	//判断动作
	if(checkDo != "1")
		return false;
	if(isHaveNewone())
		return false;
	var oUl =$(".check_ul");
	var b = oUl.find(".active");
	if(b.hasClass("dl_status")){
		showMsg("入住人已删除!");
		return false;
	}
	//a为主客人
	var a = oUl.find("i");
	var hostcheckuserId = a.parent().parent().find("label:hidden").text();
	var checkuserId = b.find("label:hidden").text();
	if( checkuserId == ""){
		showMsg("无客人!");
		return false;
	}
	if( hostcheckuserId == checkuserId){
		showMsg("已是主客人!");
		return false;
	}
	$.ajax({
		 url: checkuserpath + "/sethousecheckuser.do", 
		 type: "post",
		 data : {
			checkuserId : checkuserId,
			hostcheckuserId : hostcheckuserId
		 },
		 success: function(json) {
			if(json.result == 1){
				b.find("span").prepend(a);
				a.remove();
				showMsg(json.message);
			}else if(json.result == 0){
				showMsg(json.message);
			}
		 },
		 error: function(json) {}
	});
}