var checkuserpath = getRootPath();
loadCheckuser();

function interfacecheckuser(){
		
	$.getJSON(checkuserpath + "/page/ipmspage/room_statistics/checkuserdata.json", function (data){
		
		const Rand = Math.random();
		const Range = data.length - 1;
		const index = 0 + Math.round(Rand * Range);
		const checkuserdata = data[index];
/*		$.each(data, function(index){
			if(data[index].idcard == '632624197607224882'){
				checkuserdata = data[index];
			}
		});*/
		addCheckuser(checkuserdata);
	})
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

function initcheckusercss(){
	
}

/** 入住人增删查  **/
function loadCheckuser(){
	
	$.ajax({
		 url: checkuserpath + "/loadAllCheckuser.do",
		 type: "post",
		 data : {
			checkid : checkid
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
						json.data[index].checkuserName +"</span></span></li>";
						loadCustomer(json.data[index].checkuserId);
					}
					else{
						childnode = childnode + "<li class='check_name" +
						adddl_status(json.data[index].status) + "'><label style='display: none'>"+ 
						json.data[index].checkuserId +"</label><span>" + 
						addStar(json.data[index].checkuserType) + "<span>" + 
						json.data[index].checkuserName +"</span></span></li>";
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
	let username = data["username"];
	let sex = data["sex"];
	let idcard = data["idcard"];
	if(isHaveNewone())
		return false;
	$.ajax({
		 url: checkuserpath + "/addCheckuser.do",
		 type: "post",
		 data : {
			username : username,
			sex : sex,
			idcard : idcard,
			checkid : checkid
		 },
		 success: function(json) {
			if(json.result == 1){
				let strdnode = "<li class='check_name active'><label style='display: none'>"+ 
							json.data.checkuserId +"</label><span>" + 
							addStar(json.data.checkuserType) + "<span>" + 
							json.data.checkuserName +"</span></span></li>";
				let childnode = $(strdnode);
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
				//showMsg(json.message);
			}
			else if (json.result == 2){
				var oUl =$(".check_ul");
				var lis = oUl.find("li");
				$.each(lis, function(index){
					let dlli = $(lis[index]);
					let kk = dlli.find("label");
					let checkuserid = dlli.find("label:hidden").text();
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

