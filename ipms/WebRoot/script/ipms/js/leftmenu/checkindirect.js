var limitFlag;//点击事件限制开关
var hourRoomFlag = false;//钟点房判断开关true为钟点房,false不是钟点房
var firsttime;//true为首次
var noConnectionFlag;
var checkinFlag = true;
String.prototype.trim=function(){return this.replace(/(^\s+)|(\s+$)/g,'')};
	$(this).keyup(function(e){
	e.stopPropagation();
	e.preventDefault();
	var idCard= $("#memberselect").val();//input框身份证号码,需校验只有是身份证号码的时候才触发enter13
	var idcardRegex = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;//身份证校验
	var IdReader = null;
	if(idCard != null ){
		var currKey = e.keyCode || e.which || e.charCode;
		if(currKey == "13" && document.activeElement.id == "memberselect"){
			if(IdReader == null){//硬件返回对象,现在无设备为空
				if(!idcardRegex.test(idCard)){
					showMsg("请输入正确的身份证号码");
					return false;
				}
				//getGuestInfo(idCard);
				loginInCheckUser(idCard);
			}else{//暂时不写
				
			}
		}
	}
	if ($("#companyOrMemberSelect").is(":focus")) {
		var currKey = e.keyCode || e.which || e.charCode;
		if(currKey == "13" && document.activeElement.id == "companyOrMemberSelect") {
			queryFirstPriceInCheck();
		}
	}
}); 


$(function(){
	
	$(document).bind("contextmenu",
		    function(){
		        return false;
		    }
		);
	
	/**
	 * 会员类型改变事件,刷房价,断网手动入住下
	 */
//	$("#guestInfo").eq(0).find("li #memberrank_select").on("change",function(){
//		if(noConnectionFlag){//只在手动情况下生效
//			var memberrank = $("#guestInfo").eq(0).find("li #memberrank_select").val();//第一位为主客人的会员等级
//			var roomtype = $("#roomtype").val();
//			$.ajax({//依据会员级别刷房价
//				url: path+"/queryRoomPrice.do",
//				type: "post",
//				data: {"roomtype" : roomtype,
//						"memberrank" : memberrank,
//						"hourRoomFlag" : hourRoomFlag
//			},
//			success: function(json){
//				$("#rpname").val(json[0].RPNAME);
//				$("#rpId").val(json[0].RPID);
//				$("#activity").val(json[0].PRICE);
//			},
//			error: function(){}
//			});
//		};
//	}
//	)
//	$(this).keydown(function(e){
//		if(e.which == "13"){
//			getGuestInfo();
//		}
//		
//	})
	
	
	//更具入住人的会员级别刷新会上的房价（已废弃）
	/*$(document).on("change","#guestInfo:first li #memberrank_select",function(){
		var memberrank = $("#guestInfo").eq(0).find("li #memberrank_select").val();//第一位为主客人的会员等级
		var roomtype = $("#roomtype").val();
		$.ajax({//依据会员级别刷房价
			url: path+"/queryRoomPrice.do",
			type: "post",
			data: {"roomtype" : roomtype,
					"memberrank" : memberrank,
					"hourRoomFlag" : hourRoomFlag
		},
		success: function(json){
			$("#rpname").val(json[0].RPNAME);
			$("#rpId").val(json[0].RPID);
			$("#activity").val(json[0].PRICE);
			$("#checkuser").val($("#username").val());
		},
		error: function(){}
		});
	});*/
	
	
	//判断是否断网
	$.ajax({//依据会员级别刷房价
		url: path+"/checkConnection.do",
		type: "post",
		data: {
	},
	success: function(json){
		noConnectionFlag = json.data;
	},
	error: function(){}
	});
	
});
/*//查询会员
function querymember(){
	var idcardnumber = $("#memberselect").val();
	var idcardReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	if(idcardReg.test(idcardnumber)){
		$.ajax({
			url: path + "/querymember.do",
			type: "post",
			data : {"idcardnumber" : idcardnumber},
			success: function(json) {
				if(json.result){
					showMsg(json.message);
				}else{
					//判断主客人和从客人
					if($("#guest li:first span span").text()=="主客人"){//无主客人则添加主客人呢
						showMsg("确认该会员为入住人?",function(){
							$("#checkuser").val(json[0].MEMBERNAME);
							$("#memberId").val(json[0].MEMBERID);
							$("#memberrank").val(json[0].MEMBERRANK);
							$("#rpId").val(json[0].RPID);
							$("#rpName").val(json[0].RPNAME);
							loadMainGuest(json);//刷新主客人	
						});
						
					}else{//有主客人则添加新客人
						//判断是否点击了添加新客人按钮,未点击则提示先添加
						if($("#guest li:last span span").text()=="新客人"){
							var idcards = $("#guest").parent().find("li #idcard");
							for ( var i = 0; i < idcards.length; i=i+1) {
								if(json[0].MEMBERNAME==$(idcards[i]).val()){
									showMsg("请勿重复添加相同客人");
									return;
								}else{
									showMsg("确认添加新客人吗?",function(){
										loadNewGTuest(json);//刷新从客人
									});
								}
							}
						}else{
							showMsg("请先添加一位新客人");
						}
					}
				}
			},
			error: function(json) {}
		});
	}else{
		showMsg("请输入正确的身份证号码");
	}
	}*/
/*查会员信息*/
function querymember(){
	var phone = $("#memberselect").val();
	var phoneReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if(phoneReg.test(phone)){
//		window.JDialog.open("会员信息",base_path+"/querymember.do?phone="+phone,750,350);
		 $.ajax({
		        url: path + "/querymember.do",
				 type: "post",
				 data : {"phone" : phone },
				 success: function(json) {
					 if(json.result =="1"){
						 showMsg(json.message);
						 return;
					 }
					 if(json == ""){
						 showMsg("未查到会员相关信息")
					 }else{
						 var data = JSON.stringify(json);
						 window.JDialog.open("会员信息",base_path+"/page/ipmspage/leftmenu/repair/memberUpdate.jsp?data="+data,750,350);
					 }
				 },
				 error: function() {}
			}); 
	}else{
		showMsg("请输入正确的手机号码");
	}
	}
/*默认入住人为主客人*/
function loadMainGuest(json){
	if(json.result=="2"){//临时会员
		$("#checkphone").removeAttr("disabled");
		$("#guest li:first span span").text(json.data.membername);
		$("#username").val(json.data.membername);
		$("#gender").val(json.data.gendor);
		$("#memberrank_select").val("1");
//		$("#memberid").val(json.data.memberdata[0].MEMBERID);
		$("#idcard").val(json.data.idcard);
		$("#idnumber").val(json.data.idcard);
	}else{//会员
			$("#guest li:first span span").text(json.data.memberdata[0].memberName);
			$("#username").val(json.data.memberdata[0].memberName);
			$("#gender").val(json.data.memberdata[0].gendor);
			$("#memberrank_select").val(json.data.memberdata[0].memberRank);
			$("#memberid").val(json.data.memberdata[0].memberId);
			$("#checkphone").val(json.data.memberdata[0].mobile);
			$("#idcard").val(json.data.memberdata[0].idcard);
			$("#idnumber").val(json.data.memberdata[0].idcard);
			$("#checkuserMain").val(json.data.memberdata[0].memberId);
			if(json.data.memberdata[0].mobile==null){
				$("#checkphone").removeAttr("disabled");
			}
	}
}
/*添加新客人*/
function loadNewGTuest(json){
	if(json.result== '2'){//临时会员
		$("input[name='checkphone']:disabled").removeAttr("disabled");
		$("#guest li:last span span").text(json.data.membername);
		$("#guest").parent().children().last().find("li #username").val(json.data.membername);
		$("#guest").parent().children().last().find("li #gender").val(json.data.gendor);
		$("#guest").parent().children().last().find("li #memberrank_select").val("1");
//		$("#guest").parent().children().last().find("li #memberid").val(json.data.memberdata[0].MEMBERID);
		$("#guest").parent().children().last().find("li #idcard").val(json.data.idcard);
		
	}else{//会员
		$("#guest li:last span span").text(json.data.memberdata[0].memberName);
		$("#guest").parent().children().last().find("li #username").val(json.data.memberdata[0].memberName);
		$("#guest").parent().children().last().find("li #gender").val(json.data.memberdata[0].gendor);
		$("#guest").parent().children().last().find("li #memberrank_select").val(json.data.memberdata[0].memberRank);
		$("#guest").parent().children().last().find("li #memberid").val(json.data.memberdata[0].memberId);
		$("#guest").parent().children().last().find("li #checkphone").val(json.data.memberdata[0].mobile);
		$("#guest").parent().children().last().find("li #idcard").val(json.data.memberdata[0].idcard);
		if(json.data.memberdata[0].mobile==null){//如果会员没有手机号信息,则允许手工增加
			$("#guest").parent().children().last().find("li #checkphone").removeAttr("disabled");
		}
	}
}

/*
 * 读取证件,刷新当前显示的li下的客人信息(非主客人)
 */
function loadOnshowGuest(json){
	//获取当前显示的li对应的身份信息ul
	var li = document.getElementsByClassName("check_name");
	var newindex="";
	for(var i = 0; i <li.length; i=i+1){
		 if($(li[i]).hasClass("onShow")){
			 newindex= i;
		 }
		 
	}
	if(json.result == '1'){
		$("input[name='checkphone']:disabled").removeAttr("disabled");
		$("#guest li").eq(newindex).find("span span").text(json.data.name);
		$("#guestInfo").parent().find("ul").eq(newindex+1).find("li #username").val(json.data.name);
		$("#guestInfo").parent().find("ul").eq(newindex+1).find("li #gender").val(json.data.name);
		$("#guestInfo").parent().find("ul").eq(newindex+1).find("li #memberrank_select").val("1");
		$("#guestInfo").parent().find("ul").eq(newindex+1).find("li #idcard").val(json.data.idnumber);
	}else{
		$("#guest li").eq(newindex).find("span span").text(json.data.memberdate[0].memberName);
		$("#guestInfo").parent().find("ul").eq(newindex+1).find("li #username").val(json.data.memberdate[0].memberName);
		$("#guestInfo").parent().find("ul").eq(newindex+1).find("li #gender").val(json.data.memberdate[0].gendor);
		$("#guestInfo").parent().find("ul").eq(newindex+1).find("li #memberrank_select").val(json.data.memberdate[0].memberRank);
		$("#guestInfo").parent().find("ul").eq(newindex+1).find("li #memberid").val(json.data.memberdate[0].memberId);
		$("#guestInfo").parent().find("ul").eq(newindex+1).find("li #checkphone").val(json.data.memberdate[0].mobile);
		$("#guestInfo").parent().find("ul").eq(newindex+1).find("li #idcard").val(json.data.memberdate[0].idcard);
		if(json.data.memberdate[0].MOBILE==null){//如果会员没有手机号信息,则允许手工增加
			$("#guestInfo").parent().find("ul").eq(newindex+1).find("li #checkphone").removeAttr("disabled");
		}
	}

}

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg,fn);
	}
}

/*房间单击事件*/
$(document).on("click","#roomid",function(){
	var roomtype = $("#roomtype").val();
	if(roomtype == 0){
		showMsg("请先选择入住的房间类型!");
	}else{
		$.ajax({//依据房型查房间号
			url: path+"/queryRoomId.do",
			type: "post",
			data: {"roomtype" : roomtype},
			success: function(json){
				if(json.result =="1"){
					showMsg(json.message);
					return;
				}
				var li ="";
				$.each(json,function(index){
					li += "<li><span id='roomid"+index+"' onclick='addroomid(this)'>"+json[index]["ROOMID"]+"</span></li>";
				});
				$("#roomul").html("");
				$("#roomul").html(li);
				$(".detail_five").show();
			},
			error: function(){}
		});
	}
})
/*选择房间入住*/
function addroomid(e){
	var roomid = $(e).text();
	$("#roomid").val(roomid);
	$(".detail_five").hide();
	
}
/*获取有效客人信息*/
function getListByName(namem){
	var namets = document.getElementsByName(namem);
	var namet="";
	for ( var i = 0; i < namets.length; i=i+1) {
		if($(namets[i]).parent().parent().hasClass("hidden")){
			continue;
		}
		var val = $(namets[i]).val();
		if(val==""){
			val=" ";
		}
		namet+=val+",";
	}
	return namet;
}
/*获取刷过身份证的所有客人信息*/
function getFullListByName(namem){
	var namets = document.getElementsByName(namem);
	var namet="";
	for ( var i = 0; i < namets.length; i=i+1) {
		if($(namets[i]).parent().parent().hasClass("hidden")){
			
			var val = $(namets[i]).val()+"unabled";
		}else{
			var val = $(namets[i]).val();
			if(val ==""){
				val = " ";
			}
		}
		namet+=val+",";
	}
	return namet;
}
/*入住*/
function checkin(){
	if(!checkinFlag){
		return;
	}
	var reg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //手机校验^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
	var idcardReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;//身份证校验
	var depostReg = /^([1-9]\d*|0)(\.\d{1,2})?$/;
	var mId = /^1\d{7}$/;
	var checkUser = $("#checkuser").val();//积分账号
	var UserInUse = $("#checkuserMain").val();//入住人
//	var OrdeUser = $("#idnumber").val();//入住人身份证
	var roomType = $("#roomtype").val();//房型
	var roomId = $("#roomid").val();//房号
	var checkoutTime;//预离时间
	var companyOrMemberSelect = $("#companyOrMemberSelect").val()
	if(roomtype == 0){
		showMsg("请选择房间类型");
		return;
	}
	
	if ($("#handSpan").is(":visible")){
		showMsg("请关闭手动添加模式!");
		return;
	}
	
	if(companyOrMemberSelect == ""){
		showMsg("请输入手机号/公司编号");
		return;
	}
	
	if(hourRoomFlag){//钟点房+hours小时
		var hours = $("#hours").val();
		var date = new Date();
		checkoutTime = new Date(date.getTime()+hours*60*60*1000);
		checkoutTime = dealLocalDate(checkoutTime);
	}else{
		checkoutTime = $("#checkouttime").val();
		if($("#checkouttime").val() == "" || formatDate(new Date())>checkoutTime){
			showMsg("请录入正确的日期");
			return;
		}
	}
	if(checkUser==""){
		showMsg("请输入会员以便积分");
		return;
	}

	if(roomId==""){
		showMsg("请选择入住房间");
		return;
	}
	
	if(UserInUse==""){
		showMsg("请输入至少一位入住人");
		return;
	}
	
	var rpId = $("#rpId").val();//房价码
	var roomPrice = $("#activity").val();//房价
	var deposit = $("#deposit").val();//押金
	if(!depostReg.test(deposit)){
		showMsg("请输入正确的押金金额");
		return;
	}
	var remark = $("#remark").val();//入住人备注信息
	var guestname = getListByName("username");//客人姓名
	var gender = getListByName("gender");
	var idcardnumber = getListByName("idcard");//客人身份证
	var cuRemark = getListByName("checkuserremark");//客人备注信息
	if(remark.length >99 || cuRemark.length > 99){
		showMsg("请输入200以内的汉字");
		return;
	}
	var phone = getListByName("checkphone");//客人手机号码
	var rank = getListByName("memberrank_select");//会员类型
	var memberid = getListByName("memberid");//客人会员卡
	var guestnameAll = getFullListByName("username");//入住人用
	var genderAll = getFullListByName("gender");//入住人用
	var idcardnumberAll = getFullListByName("idcard");//入住人用
	var phoneAll = getFullListByName("checkphone");//入住人用
	var rankAll = getFullListByName("memberrank_select");//会员级别
	var newphone = phone.substring(0, phone.length-1);
	var newarray = newphone.split(",");
	for ( var i = 0; i < newarray.length; i=i+1) {
		if(newarray[i].trim().length!=0){
			if(!reg.test(newarray[i])){
				showMsg("手机号:"+newarray[i]+"格式有误");
				return;
			}
		}else{
			showMsg("请录入入住人手机号");
			return;
		}
	}
	//断网情况下要检验手动输入的身份证
	if(noConnectionFlag){
		var newidcard = idcardnumber.substring(0, idcardnumber.length-1);
		var newarr = newidcard.split(",");
		for ( var j = 0; j < newarr.length; j++) {
			if(!idcardReg.test(newarr[j])){
				showMsg("身份证:"+newarr[j]+"格式错误");
				return;
			}
		}
		if($("#guestInfo").eq(0).find("li #memberrank_select").val() == "7"){//主客人
			showMsg("请设置入住人会员类型!");
			return;
		}
		//每一个会员的会员类型为非非会员和会员类型时,它对应的memberid不能为空
		var newrank = rank.substring(0, rank.length-1);
		var newmemberid = memberid.substring(0, memberid.length-1);
		var newguestname = guestname.substring(0, guestname.length-1);
		var rankarr = newrank.split(",");
		var memberidarr = newmemberid.split(",");
		var namearr = newguestname.split(",");
		for ( var k = 0; k < rankarr.length; k++) {
			if(rankarr[k] != "1" &&rankarr[k] != "7"){
				if(memberidarr[k].trim() ==""){
					showMsg("请录入"+namearr[k]+"编号信息");
					return;
				}
				if(!mId.test(memberidarr[k].trim())){
					showMsg("会员编号:"+memberidarr[k].trim()+"有误");
					return;
				}
			}
			if(rankarr[k] == "7"){
				showMsg("请设置入住人会员类型!");
				return;
			}
		}
		
		//断网开始入住
		$.ajax({
			url: path+"/CheckInDirectWithNoConnection.do",
			type: "post",
			data: {"checkUser" : checkUser,
			"roomType" : roomType,
			"roomId" : roomId,
			"checkoutTime" : checkoutTime,
			"rpId" : rpId,
			"roomPrice" : roomPrice,
			"deposit" : deposit,
			"remark" : remark,
			"guestname" : guestname,
			"guestnameAll" : guestnameAll,
			"gender" : gender,
			"genderAll" : genderAll,
			"phone" : phone,
			"phoneAll" : phoneAll,
			"idcardnumber" :idcardnumber,
			"idcardnumberAll" : idcardnumberAll,
			"cuRemark" : cuRemark,
			"rank" : rank,
			"rankAll" : rankAll,
			"memberid" : memberid,
			"hourRoomFlag" : hourRoomFlag,
			"companyOrMemberSelect" : companyOrMemberSelect
		},
		success: function(json){
			showMsg(json.message);
			checkinFlag = true;
			if(json.result=="2"){
				setTimeout(" window.parent.parent.JDialog.close();",1500);
			}
		},
		error: function(){}
		});
	}else{		
		//联网入住开始
		$.ajax({
			url: path+"/CheckInDirectWithConnection.do",
			type: "post",
			data: {"checkUser" : checkUser,
			"roomType" : roomType,
			"roomId" : roomId,
			"checkoutTime" : checkoutTime,
			"rpId" : rpId,
			"roomPrice" : roomPrice,
			"deposit" : deposit,
			"remark" : remark,
			"guestname" : guestname,
			"guestnameAll" : guestnameAll,
			"gender" : gender,
			"genderAll" : genderAll,
			"phone" : phone,
			"phoneAll" : phoneAll,
			"idcardnumber" :idcardnumber,
			"idcardnumberAll" : idcardnumberAll,
			"cuRemark" : cuRemark,
			"rank" : rank,
			"rankAll" : rankAll,
			"memberid" : memberid,
			"hourRoomFlag" : hourRoomFlag,
			"companyOrMemberSelect" : companyOrMemberSelect,
			"discountPerson" : $("#discountPerson").val()
		},
		success: function(json){
			showMsg(json.message);
			checkinFlag = true;
			if(json.result=="2"){
				//入住成功
				//if($(window.parent.parent.document.getElementById("tolive")).hasClass("active")){
					//$(window.parent.document.getElementById("tolive")).click();
					//window.setTimeout("window.parent.location.reload(true)", 1800);
					setTimeout(" window.parent.parent.JDialog.close();",1500);
				//}
				
				
			}
		},
		error: function(){}
		});
		
		
		
	}
	
	
	

//	}
		
}

var formatDate = function(data){
	var year = data.getFullYear();
	var month = data.getMonth()+1;
	month = month < 10 ? "0"+month :month;
	var day = data.getDate();
	day = day < 10 ? "0"+day : day
	return year + "/" + month + "/" + day;
}
/*钟点房入住,早6-晚10*/
function hourCheckIn(){
//	if(limitFlag == false){
//		showMsg("你已开始入住,无法设置钟点房!");
//		return;
//	}
	var memberId = $("#memberId").val();
	var memberRank = $("#memberrank").val();
	var rpId = $("#rpId").val();
	var idNumber = $("#idnumber").val();
	var roomtype = $("#roomtype").val();
	var nowdate = new Date();
	var nowhours = nowdate.getHours();
	var companyOrMemberSelect = $("#companyOrMemberSelect").val();
	//查询当前录入会员的第一天的价格，房价码，预定人，预定人的手机号

	if(hourRoomFlag == true){
			showMsg("确认切换为普通房吗?",function(){
				hourRoomFlag = false;
				if(roomtype == 0){
					showMsg("请选择房间类型");
					return;
				}
				
				if(companyOrMemberSelect == ""){
					showMsg("请输入手机号/公司编号");
					return;
				}
				$.ajax({
					url: path+"/showRoomPriceDirectLive.do",
					type: "post",
					data: {
					"roomtype" : roomtype,
					"idCard" : companyOrMemberSelect,
					"hourRoomFlag" : hourRoomFlag,
					"firsttime" : firsttime
				},
				success: function(json) {
					if(json.result=='1'){
						showMsg(json.message);
						hourRoomFlag = true;
					} else if (json.result=='2'){
						showMsg(json.message);
						hourRoomFlag = true;
					} else {
						//console.log(json);
						$("#checkuser").val(json.data.MEMBERNAME);
						$("#rpname").val(json.data.RPNAME);
						$("#activity").val(json.data.PRICE);
						$("#memberId").val(json.data.MEMBERID);
						$("#memberrank").val(json.data.MEMBERRANK);
						$("#rpId").val(json.data.RPID);
						$("#rpName").val(json.data.RPNAME);
						$("#idnumber").val(json.data.IDCARD);
						$("#discountPerson").val(json.data.MEMBERID);
						$("#checkuserMain").val(json.data.MEMBERID);
						$("#checkouttime").attr("disabled",false);
						$(".houflag").hide();
						$(".leavetime").show();
						$("#hours").attr("disabled",true);	
						$(".timeroom").text("钟点房");
					}
				},
				error: function(){}
				});
			});
	}
	if(!$("#checkouttime").prop("disabled")){
		if(nowhours >= 6 && nowhours < 22){		
			showMsg("确认切换为钟点房吗?",function(){
				hourRoomFlag = true;
				if(roomtype == 0){
					showMsg("请选择房间类型");
					return;
				}
				
				if(companyOrMemberSelect == ""){
					showMsg("请输入手机号/公司编号");
					return;
				}
				$.ajax({
					url: path+"/showRoomPriceDirectLive.do",
					type: "post",
					data: {
					"roomtype" : roomtype,
					"idCard" : companyOrMemberSelect,
					"hourRoomFlag" : hourRoomFlag,
					"firsttime" : firsttime
				},
				success: function(json){
					if(json.result=='1'){
						showMsg(json.message);	
						hourRoomFlag = false;
					} else if (json.result=='2'){
						showMsg(json.message);
						hourRoomFlag = false;
					} else {
						//console.log(json);
						$("#checkuser").val(json.data.MEMBERNAME);
						$("#rpname").val(json.data.RPNAME);
						$("#activity").val(json.data.PRICE);
						$("#memberId").val(json.data.MEMBERID);
						$("#memberrank").val(json.data.MEMBERRANK);
						$("#rpId").val(json.data.RPID);
						$("#rpName").val(json.data.RPNAME);
						$("#idnumber").val(json.data.IDCARD);
						$("#discountPerson").val(json.data.MEMBERID);
						$("#checkuserMain").val(json.data.MEMBERID);
						$("#checkouttime").attr("disabled",true);
						$(".houflag").show();
						$(".leavetime").hide();
						$("#hours").removeAttr("disabled");	
						$(".timeroom").text("普通房");
					}
					
				},
				error: function(){}
				});
			});

		}else{
			showMsg("当前系统时间无钟点房!");
		}	
	}
}
/*设置主客人按钮*/ 
function setMainGuest(){
	//这个是用来校验前面的主客人
	/*if($("#checkuserMain").val()==""){
		showMsg("请先添加至少一位入住人!");
	}else*/
	if($(".onShow span span").text()=="新客人"){
		showMsg("请先添加客人信息!");
	}else if($(".onShow").hasClass("dl_status")){
		showMsg("该客人已失效!");
	}else{//进入主客人设置
		//获取当前的li的个数,一个默认为主客人无需设置
		if($(".onShow").siblings().length>0 && $(".onShow").prev().length>0){
			$("i .fa,.fa-star").remove();
			var ll = "<i class= 'fa fa-star'></i>"
			$(".onShow").insertBefore($(".onShow").parent().children(":first"));//移动至第一位
			$(".onShow").addClass("active");
			$(".onShow span span").prepend(ll);
			$(".onShow").siblings().removeClass("active");
			//移动显示的ul至第一位并显示
			 var ul = $("#guest").siblings();
			 for ( var i = 0; i < ul.length; i=i+1) {
				if($(ul[i]).is(":visible")){//显示状态
					$(ul[i]).insertAfter($(ul[i]).parent().children(":first"));//移动至第一位
//					return;
					}
			}
			 if(noConnectionFlag){
				 $("#guestInfo").eq(0).find("li #memberrank_select").on("change",function(){
					 if(noConnectionFlag){//只在手动情况下生效
						 var memberrank = $("#guestInfo").eq(0).find("li #memberrank_select").val();//第一位为主客人的会员等级
						 var roomtype = $("#roomtype").val();
						/* $.ajax({//依据会员级别刷房价
							 url: path+"/queryRoomPrice.do",
							 type: "post",
							 data: {"roomtype" : roomtype,
							 "memberrank" : memberrank,
							 "hourRoomFlag" : hourRoomFlag
						 },
						 success: function(json){
							 $("#rpname").val(json[0].RPNAME);
							 $("#activity").val(json[0].PRICE);
							 $("#checkuser").val($(".onShow").find("span span").text());
						 },
						 error: function(){}
						 });*/
					 }
				 });
				 
			 }else{
				 
				 var memberrank = $("#guestInfo").eq(0).find("li #memberrank_select").val();//第一位为主客人的会员等级
				 var roomtype = $("#roomtype").val();
				/* $.ajax({//依据会员级别刷房价
					 url: path+"/queryRoomPrice.do",
					 type: "post",
					 data: {"roomtype" : roomtype,
					 "memberrank" : memberrank,
					 "hourRoomFlag" : hourRoomFlag
				 },
				 success: function(json){
					 $("#rpname").val(json[0].RPNAME);
					 $("#activity").val(json[0].PRICE);
					 $("#checkuser").val($(".onShow").find("span span").text());
				 },
				 error: function(){}
				 });*/
				 
			 }
			 
			}else{
				showMsg("当前已为主客人,无需设置");
			}
	}
}
/*添加新客人,*/
function addCustomer(){
	var mainguest = $("#guest li:first span span").text();
	var otherguest = $("#guest li:last span span").text();
	if($("#username").prop("disabled")){//会员入住添加新客人
		if(mainguest=="主客人"){
			showMsg("请先设置主客人!");
		}else if(otherguest=="新客人"){
			showMsg("请依次添加新客人")
		}else{
			$("#guest").append("<li class='check_name onShow' onclick='thisGuestInfo(this)'><label class='guestmemberid' style='display:none'></label><span><span>新客人</span></span></li>");
			$("#guest").siblings().hide();
			addul();
		}
		
	}else{//非会员入住添加新客人
		if($(".onShow span span").text()=="主客人" || $(".onShow span span").text()=="新客人"){
				if($("input[name='username']:visible").val()==""){
					showMsg("请依次添加客人信息");
					return;
				}else{
					$("#guest").append("<li class='check_name onShow' onclick='thisGuestInfo(this)'><label class='guestmemberid' style='display:none'></label><span><span>新客人</span></span></li>");
					$("#guest").siblings().hide();
					addul();
					$("input").removeAttr("disabled");//页面变为可编辑状态
				}	
		}
	}
}
/*断网添加新客人*/
function addCustomManual(){
	var mainguest = $("#guest li:first span span").text();
	var otherguest = $("#guest li:last span span").text();
	if(mainguest != "主客人"){
		$("#guest").append("<li class='check_name onShow' onclick='thisGuestInfo(this)'><label class='guestmemberid' style='display:none'></label><span><span>新客人</span></span></li>");
		$("#guest").siblings().hide();
		addul();
		
	}
} 
/*添加新客人ul*/
function addul(){
	//给最后一个li添加onShow
	$("#guest").find("li:last").addClass("onShow");
	//移除其他元素的onShow
	$("#guest").find("li:last").siblings().removeClass("onShow");
	var a = memberranks.substring(2,memberranks.length-2);
	var array = a.split("\},\{")
	var val="";
	var text="";
	var arr = new Array();
	arr[0] = "";
	for ( var i = 0; i <array.length; i=i+1) {
		val = array[i].substring(array[i].indexOf(":")+2,array[i].indexOf(":")+3);
		text = array[i].substring(array[i].lastIndexOf(":")+2,array[i].lastIndexOf('"'));
		arr.push("<option value='"+val+"'>"+text+"</option>")
	}
//	var memberselect="";
//	for ( var i = 0; i <array.length; i=i+1) {
//		val = array[i].substring(array[i].indexOf(":")+2,array[i].indexOf(":")+3);
//		text = array[i].substring(array[i].lastIndexOf(":")+2,array[i].lastIndexOf('"'));
//		memberselect += "<option value='"+val+"'>"+text+"</option>"
//	}
	$("#guest").parent().append("<ul class='customer_info check_div clearfix' id='guestInfo'>"+
								"<li><label class='label_name'>姓名</label><input name='username' type='text' id='username' class='check' disabled></li>"+
								"<li>"+
									"<label class='label_name' for=''>性别</label>"+
									"<select id='gender' name='gender' class='gender-select' disabled='disabled' style='width:150px;height:36px;margin-left:10px'>"+
									"<option value='1'>男</option>"+
									"<option value='0'>女</option>"+
								"</select>"+
								"</li>"+
								"<li>"+
									"<label class='label_name' for='' >会员类型</label>"+
									"<select id='memberrank_select' name='memberrank_select' class='info_write_select' disabled='disabled'>"+
										"<option value='7' id='memberRank'></option>"+
										arr.join("")+						
									"</select>"+
								"</li>"+
								"<li><label class='label_name'>会员编码</label><input id='memberid' name='memberid' type='text' value='' class='check' disabled></li>"+
								"<li><label class='label_name'>手机号码</label> <input id='checkphone' name='checkphone' type='text' value='' class='check'></li>"+
								"<li>"+
									"<label class='label_name' for=''>证件类型</label>"+
									"<select class='info_write_select' name='cars' disabled='disabled'>"+
										"<option value='0'>身份证</option>"+
//										"<option value='1'>居住证</option>"+
									"</select></li>"+
								"<li><label class='label_name'>证件号码</label> <input name='idcard' type='text' value='' id='idcard' class='check' disabled></li>" +
								"<li><label class='label_name'>备注</label><textarea  id='checkuserremark' name='checkuserremark' class='checkIn_textarea' rows='2' cols='124.5' ></textarea></li></ul>");
	
}
/*单击新客人显示对应的客人信息*/
function thisGuestInfo(e){
	//拿到当前点击的li的下标
	var li = document.getElementsByClassName("check_name");
	var newindex="";
	for(var i = 0; i <li.length; i=i+1){
		 if(e==li[i]){
			 newindex= i;
		 }
		 
	}
	$(e).find("label").show();
	$(e).siblings().removeClass("onShow");//其他兄弟节点移除显示状态
	$(e).addClass("onShow");//添加显示状态
	$(e).parent().siblings().hide();
	$(e).parent().parent().find("ul").eq(newindex+1).show();
}
/*减少客人*/
function deleteCustomer(){
	if($(".onShow").siblings().not(".dl_status").length>0 ){
		//主客人不可删除
		if($("#guest").find("li:first").hasClass("onShow")){
			showMsg("主客人不可删除！");
			return;
		}
		
		if($(".onShow").hasClass("dl_status")){
			showMsg("请勿重复删除");
			return;
		}
		$(".onShow").addClass("dl_status");
		var ul = $("#guest").siblings();
		for(var i = 0; i <ul.length; i=i+1){
			 if($(ul[i]).is(":visible")){
				 $(ul[i]).addClass("hidden");
			 }
			 
		}
		return;
	}
	showMsg("请至少保留一个入住人信息")
	 
}
/*读取证件,模拟入住*/
function getGuestInfo(idCard){
//	limitFlag = false;//限制钟点房点击按钮
	var roomtype = $("#roomtype").val();
	if(roomtype == 0 ){
		showMsg("请先设置入住房间类型");
		return;
	}
	$.ajax({
		url: path+"/getGuestInfo.do",
		type: "post",
		data: {
		"roomtype" : roomtype,
		"idCard" : idCard,
		"hourRoomFlag" : hourRoomFlag,
		"firsttime" : firsttime
	},
	success: function(json){
		if(json.result=='1'){//断网
			if($("#guest li:first span span").text()=="主客人"){//添加入住人
				showMsg(json.message,function(){//检查客人重复
						noConnectionFlag = true;
						//移除页面所有的disabled属性
						$(":disabled").prop("disabled",false);
						$("#memberRank").text("会员类型");
						$("#checkuser").val(json.data.name);
						$("#guest li:first span span").text(json.data.name);
						$("#username").val(json.data.name);
						$("#gender").val(json.data.gendor);
						$("#idcard").val(json.data.idcard);
						if(hourRoomFlag){
							$("#checkouttime").attr("disabled","disabled");
						}
					
					
				});
			}else{
				if(!CheckWhetherRepeatForGuest(json)){
					addCustomManual();
					if($("#guest li:last span span").text()=="新客人"){
						$(":disabled").prop("disabled",false);
						$("#guestInfo").parent().find("ul:last").find("#memberRank").text("会员类型");//当前显示的
						$("#guest li:last span span").text(json.data.name);
						$("#guest").parent().children().last().find("li #username").val(json.data.name);
						$("#guest").parent().children().last().find("li #gender").val(json.data.gendor);
						$("#guest").parent().children().last().find("li #idcard").val(json.data.idcard);
						if(hourRoomFlag){
							$("#checkouttime").attr("disabled","disabled");
						}
					}
					
				}else{
					//showMsg("请勿重复添加");
					$("#errorMsg").text("请勿重复添加!").show();
					setTimeout("$('#errorMsg').hide()",1500);
					return;
				}
			}
		}else if(json.result=='2'){//临时会员联网
			if($("#guest li:first span span").text()=="主客人"){
//				showMsg("确认该会员为入住人?",function(){
//					$("#checkuser").val(json.data.membername);
//					$("#memberrank").val("1");
//					$("#rpId").val("MSJ");
//					$("#activity").val(json.data.memberdata[0].PRICE)
//					$("#rpname").val("门市价");
//					$("#rpName").val("门市价");
//					firsttime = false;
//					loadMainGuest(json);
//				});
				showMsg(json.message);
				
			}else{
				addCustomer();
				if($("#guest li:last span span").text()=="新客人"){
					if(!CheckWhetherRepeatForGuest(json)){
//						showMsg("确认添加新客人吗?",function(){
							loadNewGTuest(json);
//						});	
					}
					
				}else{
					if($(".onshow").prev().length > 0){
						//刷新当前显示的li的客人信息
						loadOnshowGuest(json);
						
					}else{//刷新主客人并且刷新房价
						loadMainGuest(json);
						$("#checkuser").val(json.data.name);
						$("#memberrank").val("1");
						$("#rpId").val("MSJ");
						$("#rpName").val("门市价");
						$("#activity").val(json.data.price);
					}
//					showMsg("请先添加一位新客人");
				}
			}
		}else{
			//判断主客人和从客人
			if($("#guest li:first span span").text()=="主客人"){//无主客人则添加主客人
				$("#memberselect").blur();
				showMsg("确认该会员为入住人?",function(){
						$("#checkuser").val(json.data.memberdata[0].MEMBERNAME);
						$("#memberId").val(json.data.memberdata[0].MEMBERID);
						$("#memberrank").val(json.data.memberdata[0].MEMBERRANK);
						$("#rpId").val(json.data.memberdata[0].RPID);
						$("#rpname").val(json.data.memberdata[0].RPNAME);
						$("#rpName").val(json.data.memberdata[0].RPNAME);
						if(json.data.memberdata[0].MEMBERRANK == "0"){
							$("#activity").val(json.data.price);
						}else{
							$("#activity").val(json.data.memberdata[0].PRICE);
							
						}
						loadMainGuest(json);//刷新主客人	
				});	
				return false;
			}else{//有主客人则添加新客人
				//判断是否点击了添加新客人按钮,未点击则提示先添加
//				if(!CheckWhetherRepeatForGuest(json)){
					if($("#guest li:last span span").text()!="主客人"){
						if(!CheckWhetherRepeatForGuest(json)){
							addCustomer();
//						showMsg("确认添加新客人吗?",function(){
							loadNewGTuest(json);//刷新从客人
//						});
						}else{
							//$("#memberselect").val("请勿重复添加!");
						//	$("#memberselect").css({"color":"red","font-size":"17px"});
							//setTimeout("$('#memberselect').hide()",1500);
							$("#errorMsg").text("请勿重复添加!").show();
							setTimeout("$('#errorMsg').hide()",1500);
							return;
						}
					}else{
						if($(".onshow").prev().length > 0){
							//刷新当前显示的li的客人信息
							loadOnshowGuest(json);
							
						}else{//刷新主客人并且刷新房价
							
							if(!CheckWhetherRepeatForGuest(json)){
								$("#checkuser").val(json.data.memberdate[0].MEMBERNAME);
								$("#memberId").val(json.data.memberdate[0].MEMBERID);
								$("#memberrank").val(json.data.memberdate[0].MEMBERRANK);
								$("#rpId").val(json.data.memberdate[0].RPID);
								$("#rpName").val(json.data.memberdate[0].RPNAME);
								loadMainGuest(json);//刷新主客人	
							}
						}
//					showMsg("请先添加一位新客人");
					}
					
//				}else{
//					show
//				}
				
			}
		}
	},
	error: function(){}
	});
}

/**
 * 钟点房按钮刷房价
 */
function refreshRoomPrice(){ 
	var roomtype = $("#roomtype").val();
	var rpId = $("#rpId").val(); 
	$.ajax({
		url: path+"/refreshRoomPrice.do",
		type: "post",
		data: {
		"roomtype" : roomtype,
		"rpId" : rpId
	},
		success: function(json){
			$("#activity").val(json.data.price);
	},
		error: function(){}
	});
}	



/**
 * 检查客人是否重复
 */
function CheckWhetherRepeatForGuest(json) {
	var idcards = $(".info_write").find("#guestInfo:not(.hidden) li #idcard");
	for ( var i = 0; i < idcards.length; i = i + 1) {
		if (json.result == "2" || json.result == "1" || json.result=="3") {// 临时会员
			if (json.data.idcard == $(idcards[i]).val() || json.data.memberdata[0].idcard == $(idcards[i]).val()) {
				$("#errorMsg").text("请勿重复添加!").show();
				setTimeout("$('#errorMsg').hide()",1500);
				return true;
			}

		} 
//		if()

	}
	return false;
}
/**
 * 手动添加客人
 */
function addCustomerByHand(){
	limitFlag = false;//限制钟点房点击按钮
	var mainguest = $("#guest li:first span span").text();
	var lastguest = $("#guest li:last span span").text();
	if(mainguest == "主客人"){
		$("#guest").next().find(":disabled").removeAttr("disabled");
		$("#handSpan").show();
		$("#cancleHandSpan").show();
		return;
	}
	if(lastguest =="新客人"){
		showMsg("请依次添加客人信息");
		return;
	}
	$("#handSpan").show();
	$("#cancleHandSpan").show();
//	$(".onShow").children().removeAttr("disabled");
	$("#guest").append("<li class='check_name onShow' onclick='thisGuestInfo(this)'><label class='guestmemberid' style='display:none'></label><span><span>新客人</span></span></li>");
	$("#guest").siblings().hide();
	addul();
	$("#guest").siblings("ul:last").find(":disabled").removeAttr("disabled");
	
//	$("#guestInfo").eq(0).find("li #memberrank_select").on("change",function(){
//			var memberrank = $("#guestInfo").eq(0).find("li #memberrank_select").val();//第一位为主客人的会员等级
//			var roomtype = $("#roomtype").val();
//			$.ajax({
//				url: path+"/queryRoomPrice.do",
//				type: "post",
//				data: {"roomtype" : roomtype,
//						"memberrank" : memberrank,
//						"hourRoomFlag" : hourRoomFlag
//			},
//			success: function(json){
//				$("#rpname").val(json[0].RPNAME);
//				$("#rpId").val(json[0].RPID);
//				$("#activity").val(json[0].PRICE);
//				$("#checkuser").val($("#username").val());
//			},
//			error: function(){}
//			});
//	}
//	);
    
}

/**
 * 手动添加客人入住按钮
 */
function checkInHand(){
	var reg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //手机校验^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
	var idcardReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;//身份证校验
//	//获取当前显示的onShowli的下标
//	var li = document.getElementsByClassName("check_name");
//	var index="";
//	for(var i = 0; i <li.length; i=i+1){
//		 if($(li[i]).hasClass("onShow")){
//			 index= i;
//		 }
//		 
//	}
	//获取最后一个li的下标
	var li = document.getElementsByClassName("check_name");
	var index=li.length-1;
	//获取当前显示的客人信息ul
	var ul = $(" li.onShow").parent().parent().find("ul").eq(index+1);
	var username = $(ul).find("#username").val();
	var gender = $(ul).find("#gender option:selected").text();
	var memberrank = $(ul).find("#memberrank_select").val();
	var memberId = $(ul).find("#memberid").val();
	var checkphone = $(ul).find("#checkphone").val();
	var idcard = $(ul).find("#idcard").val();
	if(username.trim()==""){
		showMsg("请输入姓名");
		return;
	}
	if(gender.trim()=="" || (gender.trim() !="男" && gender.trim() !="女")){
		showMsg("请输入性别");
		return;
	}
	if(memberrank.trim()=="" || memberrank.trim() == "7"){
		showMsg("请输入会员级别");
		return;
	}
	
	/*if(memberrank.trim() != "1" && memberrank.trim() != ""){
		if(memberId.trim() ==""){
			showMsg("请录入会员号");
			return;
		}
	}*/
	if(!reg.test(checkphone)){
		showMsg("请输入正确的手机号");
		return;
	}
	if(!idcardReg.test(idcard)){
		showMsg("请输入正确的身份证");
		return;
	}
	//将姓名刷到新客人li
	$("#guest li:last").find("span span").text(username);
	$("#handSpan").hide();
	$("#cancleHandSpan").hide();
	if(!noConnectionFlag){
		$(ul).children().find("#username").attr("disabled",true)
		$(ul).children().find("#memberid").attr("disabled",true)
		$(ul).children().find("#checkphone").attr("disabled",true)
		$(ul).children().find("#idcard").attr("disabled",true)
		$(ul).children().find(".info_write_select").attr("disabled",true)
		$(ul).children().find("#gender").attr("disabled",true)

	}

}

/**
 * 手动入住取消按钮
 */
function cancleHand(){
	
	if($("#guest li:last span span").text()=="新客人"){
		//显示前一个元素li 和ul
		$("#guest li:last").prev().show().addClass("onShow");
		$("#guest").siblings("ul:last").prev().show().addClass("onShow");
		$("#guest li:last").remove();
		$("#guest").siblings("ul:last").remove();
		$("#handSpan").hide();
		$("#cancleHandSpan").hide();
		return;
	}
	if($("#guest li:last span span").text()=="主客人"){
		$("#guestInfo").children().find("#username").attr("disabled",true).val("");
		$("#guestInfo").children().find("#memberid").attr("disabled",true).val("");
		$("#guestInfo").children().find("#checkphone").attr("disabled",true).val("");
		$("#guestInfo").children().find("#idcard").attr("disabled",true).val("");
		$("#guestInfo").children().find(".info_write_select").attr("disabled",true).val("7");
		$("#guestInfo").children().find("#gender").attr("disabled",true).val("1");
		$("#handSpan").hide();
		$("#cancleHandSpan").hide();
		return;
//		showMsg("请勿删除主客人");
//		return;
	}
}
/*
 * 钟点房刷房价事件
 */
function refreshPrice(roomtype,rpId,hourRoomFlag){
	if(roomtype == 0){
		showMsg("请选择房间类型")
		return;
	}
	$.ajax({
		url: path+"/refreshPrice.do",
		type: "post",
		data: {
		"roomtype" : roomtype,
		"rpId" : rpId,
		"hourRoomFlag" : hourRoomFlag
	},
		success: function(json){
		$("#rpId").val(json.data.rpId);
		$("#rpname").val(json.data.rpname);
		$("#activity").val(json.data.price);
	},
		error: function(){}
	});
}
//公司会员价格
function changePrice(){
	var corporationIdSelect = $("#corporationIdSelect").val();
	var roomtype = $("#roomtype").val();
	if(corporationIdSelect==""){
		showMsg("请输入公司会员编号");
		return;
	}
	if(roomtype ==0){
		showMsg("请选择房间类型");
		return;
	}
	$.ajax({
		url: path+"/discountPrice.do",
		type: "post",
		data: {
		"roomtype" : roomtype,
		"corporationIdSelect" : corporationIdSelect,
		"hourRoomFlag" : hourRoomFlag
	},
		success: function(json){
			if (json.result == 2) {
				showMsg(json.message);
			} else {
				$("#activity").val(json.data.substring(0,json.data.indexOf(":")));
				$("#discountPerson").val(json.data.substring(json.data.indexOf(":")+1));
			}
	},
		error: function(){}
	});
}

//@author ideas_jsm
//页面上会员查询取第一天的价格，分为会员和公司会员两种
function queryFirstPriceInCheck(){
	var roomtype = $("#roomtype").val();
	var companyOrMemberSelect = $("#companyOrMemberSelect").val()
	if(roomtype == 0){
		showMsg("请选择房间类型");
		return;
	}
	
	if(companyOrMemberSelect == ""){
		showMsg("请输入手机号/公司编号");
		return;
	}
	//查询当前录入会员的第一天的价格，房价码，预定人，预定人的手机号
	$.ajax({
		url: path+"/showRoomPriceDirectLive.do",
		type: "post",
		data: {
		"roomtype" : roomtype,
		"idCard" : companyOrMemberSelect,
		"hourRoomFlag" : hourRoomFlag,
		"firsttime" : firsttime
	},
	success: function(json){
		if(json.result=='1'){
			showMsg(json.message);	
		} else if (json.result=='2'){
			showMsg(json.message);
		} else {
			//console.log(json);
			$("#checkuser").val(json.data.MEMBERNAME);
			$("#rpname").val(json.data.RPNAME);
			$("#activity").val(json.data.PRICE);
			$("#memberId").val(json.data.MEMBERID);
			$("#memberrank").val(json.data.MEMBERRANK);
			$("#rpId").val(json.data.RPID);
			$("#rpName").val(json.data.RPNAME);
			$("#idnumber").val(json.data.IDCARD);
			$("#discountPerson").val(json.data.MEMBERID);
			$("#checkuserMain").val(json.data.MEMBERID);
		}
		
	},
	error: function(){}
	});
	
}

//身份证读件刷入住人
function loginInCheckUser(idCard){
	$.ajax({
		url: path+"/getGuestInfoDirectLive.do",
		type: "post",
		data: {
		"idCard" : idCard	
	},
	success: function(json){
		if(json.result=='1'){//断网
			if($("#guest li:first span span").text()=="主客人"){//添加入住人
				showMsg(json.message,function(){//检查客人重复
						noConnectionFlag = true;
						//移除页面所有的disabled属性
						$(":disabled").prop("disabled",false);
						$("#memberRank").text("会员类型");
						$("#checkuser").val(json.data.name);
						$("#guest li:first span span").text(json.data.name);
						$("#username").val(json.data.name);
						$("#gender").val(json.data.gendor);
						$("#idcard").val(json.data.idcard);
						if(hourRoomFlag){
							$("#checkouttime").attr("disabled","disabled");
						}
					
					
				});
			}else{
				if(!CheckWhetherRepeatForGuest(json)){
					addCustomManual();
					if($("#guest li:last span span").text()=="新客人"){
						$(":disabled").prop("disabled",false);
						$("#guestInfo").parent().find("ul:last").find("#memberRank").text("会员类型");//当前显示的
						$("#guest li:last span span").text(json.data.name);
						$("#guest").parent().children().last().find("li #username").val(json.data.name);
						$("#guest").parent().children().last().find("li #gender").val(json.data.gendor);
						$("#guest").parent().children().last().find("li #idcard").val(json.data.idcard);
						if(hourRoomFlag){
							$("#checkouttime").attr("disabled","disabled");
						}
					}
					
				}else{
					//showMsg("请勿重复添加");
					$("#errorMsg").text("请勿重复添加!").show();
					setTimeout("$('#errorMsg').hide()",1500);
					return;
				}
			}
		}else if(json.result=='2'){//临时会员联网
			if($("#guest li:first span span").text()=="主客人"){
//				showMsg("确认该会员为入住人?",function(){
//					$("#checkuser").val(json.data.membername);
//					$("#memberrank").val("1");
//					$("#rpId").val("MSJ");
//					$("#activity").val(json.data.memberdata[0].PRICE)
//					$("#rpname").val("门市价");
//					$("#rpName").val("门市价");
//					firsttime = false;
//					loadMainGuest(json);
//				});
				showMsg(json.message);	
			}else{
				addCustomer();
				if($("#guest li:last span span").text()=="新客人"){
					if(!CheckWhetherRepeatForGuest(json)){
							loadNewGTuest(json);
					}
				}else{
					if($(".onshow").prev().length > 0){
						//刷新当前显示的li的客人信息
						loadOnshowGuest(json);
						
					}else{//刷新主客人并且刷新房价
						loadMainGuest(json);
						$("#checkuser").val(json.data.name);
						$("#memberrank").val("1");
						$("#rpId").val("MSJ");
						$("#rpName").val("门市价");
						$("#activity").val(json.data.price);
					}
				}
			}
		}else{
			//判断主客人和从客人
			if($("#guest li:first span span").text()=="主客人"){//无主客人则添加主客人
				$("#memberselect").blur();
				showMsg("确认该会员为入住人?",function(){
						//$("#checkuser").val(json.data.memberdata[0].memberName);
						//$("#memberId").val(json.data.memberdata[0].memberId);
						//$("#memberrank").val(json.data.memberdata[0].memberRank);
						//$("#rpId").val(json.data.memberdata[0].RPID);
						//$("#rpname").val(json.data.memberdata[0].RPNAME);
						//$("#rpName").val(json.data.memberdata[0].RPNAME);
						//if(json.data.memberdata[0].MEMBERRANK == "0"){
						//	$("#activity").val(json.data.price);
						//}else{
						//	$("#activity").val(json.data.memberdata[0].PRICE);
							
						//}
						loadMainGuest(json);//刷新主客人	
				});	
				return false;
			}else{//有主客人则添加新客人
				//判断是否点击了添加新客人按钮,未点击则提示先添加
//				if(!CheckWhetherRepeatForGuest(json)){
					if($("#guest li:last span span").text()!="主客人"){
						if(!CheckWhetherRepeatForGuest(json)){
							if(!turnLeavedCustomerToLiving(json)){
								addCustomer();
//								showMsg("确认添加新客人吗?",function(){
									loadNewGTuest(json);//刷新从客人
							}
//						});
						}else{
							//$("#memberselect").val("请勿重复添加!");
						//	$("#memberselect").css({"color":"red","font-size":"17px"});
							//setTimeout("$('#memberselect').hide()",1500);
							$("#errorMsg").text("请勿重复添加!").show();
							setTimeout("$('#errorMsg').hide()",1500);
							return;
						}
					}else{
						if($(".onshow").prev().length > 0){
							//刷新当前显示的li的客人信息
							loadOnshowGuest(json);
							
						}else{//刷新主客人并且刷新房价
							
							if(!CheckWhetherRepeatForGuest(json)){
								//$("#checkuser").val(json.data.memberdate[0].MEMBERNAME);
								//$("#memberId").val(json.data.memberdate[0].MEMBERID);
								//$("#memberrank").val(json.data.memberdate[0].MEMBERRANK);
								//$("#rpId").val(json.data.memberdate[0].RPID);
								//$("#rpName").val(json.data.memberdate[0].RPNAME);
								loadMainGuest(json);//刷新主客人	
							}
						}

					}
			}
		}
	},
	error: function(){}
	});

}

//房型改变换价格

$("#roomtype").change(function(){
	
	var roomtype = $("#roomtype").val();
	var companyOrMemberSelect = $("#companyOrMemberSelect").val();
	if(roomtype != "0"){
		if(companyOrMemberSelect != ""){
			queryFirstPriceInCheck("1");
		}
	}
	
});

//将灰掉的会员恢复
		function turnLeavedCustomerToLiving(json){
			var idcards = $(".info_write").find(".hidden").find("li #idcard");
			for ( var i = 0; i < idcards.length; i = i + 1) {
				if (json.result == "2" || json.result == "1" || json.result=="3") {// 临时会员
					if (json.data.idcard == $(idcards[i]).val() || json.data.memberdata[0].idcard == $(idcards[i]).val()) {
						var idcardsAll = $(".info_write").find("#guestInfo li #idcard");
						for ( var j = 0; j < idcardsAll.length; j = j + 1) {
							var idcardsInTitle = $("#guest").find("li");
							if(json.data.memberdata[0].idcard == $(idcardsAll[j]).val() && !$(idcardsInTitle[j]).hasClass("dl_satus")){
								$(idcardsInTitle[j]).removeClass("dl_status");
								$(idcardsInTitle[j]).addClass("onShow");
								$(idcardsInTitle[j]).click();
								$(idcardsAll[j]).parent().parent().removeClass("hidden");
								return true;
							}
						}
					}

				} 
//					if()

			}
			return false;
			
		}

