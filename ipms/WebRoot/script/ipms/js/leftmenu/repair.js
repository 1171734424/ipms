
$(function(){
	$(document).bind("contextmenu",
		    function(){
		        return false;
		    }
		);
});




//确定按钮添加维修申请记录
function addRepairRecord(){
	var contrartid = $("#contrartid").val();
	var roomid = $("#roomid").val();
	var person = $("#applyname").val();
	var mobile = $("#applymobile").val();
	var equipment = $("#equipment_select").val();
	var equipmentName = $("#equipment_select option:selected").text();
	var emergent = $("#emegent_select").val();
	var repairtime = $("#repairTime").val();
	var remark = $("#repairRemark").val();
	if(checkNull($("#roomid")) || checkNull($("#repairTime"))){
		return false;
	};
	$.ajax({
        url: path + "/addRepairApplicationRecord.do",
		 type: "post",
		 data : {"roomId":roomid,
				 "person":person,
				 "mobile":mobile,
				 "equipment":equipment,
				 "equipmentName":equipmentName,
				 "emergent":emergent,
				 "repairtime":repairtime,
				 "contrartid":contrartid,
				 "remark":remark },
		 success: function(json) {
					 if(json.result =="1" ||json.result=="4" || json.result=="6"){
						 showMsg(json.message);
						 setTimeout(" window.parent.JDialog.close();",1500);
						 $(window.parent.document.getElementById("repairIframe")).attr("src","repairData.do"); 
					 }else{
						 showMsg(json.message);
					 }
		 },
		 error: function(json) {
			 showMsg(json.message);
		 }
	});
}
//确定按钮添加维修申请记录(民宿)
function addRepairRecordH(){
	var orderId = $("#orderId").val();
	var houseId = $("#houseId").val();
	var houseNo = $("#houseNo").val();
	var houseType = $("#houseType").val();
	var memberId = $("#memberId").val();
	var memberPhone = $("#applymobile").val();
	var memberName = $("#applyname").val();
	var equipment = $("#equipment_select").val();
	var equipmentName = $("#equipment_select option:selected").text();
	var emergent = $("#emegent_select").val();
	var repairtime = $("#repairTime").val();
	var remark = $("#repairRemark").val();
	if(checkNull($("#roomid")) || checkNull($("#repairTime"))){
		return false;
	};
	$.ajax({
        url: path + "/addRepairApplicationRecordH.do",
		 type: "post",
		 data : {"orderId":orderId,
				 "houseId":houseId,
				 "houseNo":houseNo,
				 "houseType":houseType,
				 "memberId":memberId,
				 "memberPhone":memberPhone,
				 "memberName":memberName,
				 "equipment":equipment,
				 "equipmentName":equipmentName,
				 "emergent":emergent,
				 "repairtime":repairtime,
				 "remark":remark },
		 success: function(json) {
					 if(json.result =="1" ||json.result=="4" || json.result=="6"){
						 showMsg(json.message);
						 setTimeout(" window.parent.JDialog.close();",1500);
						 $(window.parent.document.getElementById("repairIframe")).attr("src","repairInHouseData.do"); 
					 }else{
						 showMsg(json.message);
					 }
		 },
		 error: function(json) {
			 showMsg(json.message);
		 }
	});
}
/*
 * 维修提示空格字段的名称
 */
function checkNull(e){
	if(e.val()==""){
		showMsg(e.siblings().text()+"不能为空!");
		return true;
	}else{
		return false;
	}
}
//查询维修申请详情高级查询
function queryRepairDetail(){
	var startdate = $("#startdate").val();
	var enddate = $("#enddate").val();
	var roomid = $("#roomid").val();
	var equipment = $("#equipment").val();
	var checkstatus = $("#checkstatus").val();
	var mobile = $("#mobile").val();
	if(equipment == "0" ){
		equipment="";
	}
	if(checkstatus == "66"){
		checkstatus="";
	}
	$("#repairIframe").attr("src","queryRepairDetail.do?startdate="+startdate+"&enddate="+enddate+"&roomid="+roomid+"&equipment="+equipment+"&checkstatus="+checkstatus+"&mobile="+mobile);
}
function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

//新增维修设备房间编号显示的和隐藏div
function selectRoomIdDetail(){
	$("#roomtype_class").css("display","block");
	//获取公寓户型
	$.ajax({
		url: path+"/queryRoomType.do",
		type: "post",
		data: {},
		success: function(json){
			var li ="";
			$.each(json,function(index){
				li += "<li><span  id='roomid' onclick='showSimpleRoom(this)'>"+json[index]["ROOM_NAME"]+"("+json[index]["ROOM_TYPE"]+")</span></li>";
			});
			$(".roomtypeul").html("");
			$(".roomtypeul").html(li);
		},
		error: function(){}
	});
}
//民宿新增维修申请门店名称点击事件
function selectRoomIdDetailH(){
	$("#roomtype_class").css("display","block");
	//获取公寓户型
	$.ajax({
		url: path+"/queryRoomType.do",
		type: "post",
		data: {},
		success: function(json){
			var li ="";
			$.each(json,function(index){
				li += "<li><span  id='roomid' onclick='selectHouse(this)'>"+json[index]["HOUSENAME"]+"("+json[index]["HOUSENO"]+")</span><input type='hidden' value='"+json[index]["HOUSEID"]+"' /></li>";
			});
			$(".roomtypeul").html("");
			$(".roomtypeul").html(li);
		},
		error: function(){}
	});
}
//民宿点击门店名称事件
function selectHouse(e){
		var houseType = $(e).text().substring(0,$(e).text().indexOf("("));
		var houseNo = $(e).text().substring($(e).text().indexOf("(")+1,$(e).text().indexOf(")"))
	 	var houseId = $(e).next().val();
		
		$.ajax({//自动获取
			url: path + "/getApplicationDateH.do",
			type: "post",
			data : {"houseId" : houseId},
			success: function(json) {
				$("#roomtype_class").css("display","none");
				$("#roomid").val(houseType);
				$("#applyname").val(json.memberName);
				$("#applymobile").val(json.memberPhone);
				$("#houseId").val(json.houseId);
				$("#houseNo").val(json.houseNo);
				$("#houseType").val(json.houseType);
				$("#orderId").val(json.orderId);
				$("#memberId").val(json.memberId);
				$("#housename").val(houseType);
			},
			error: function() {}
		});
		}
document.documentElement.onclick=function(e){
	e= window.event ? window.event : e;
	var e_tar=e.srcElement ? e.srcElement : e.target;
	if(e_tar.id=="roomid"){
		return;
	}else{
		$("#roomtype_class").css("display", "none");
	}
};
 //单击显示所有房间
 function showSimpleRoom(e){
	 var roomtype = $(e).text();
	 var newstr = roomtype.substring(roomtype.indexOf("(")+1,roomtype.indexOf(")"));
	 $.ajax({
         url: path + "/showSimpleRoom.do",
		 type: "post",
		 data : {"roomtype" : newstr},
		 success: function(json) {
			 loadSimpleRoom(json);
			 $("#roomtype").val($(e).text());
		 },
		 error: function() {}
	}); 
 }
 ;function loadSimpleRoom(json){
	
		var tablere="";
		$.each(json,function(index){
			tablere +=
			"<span id='roomid'><input type='radio' id='roomid' name='roomid' value='1'/>"+json[index]["ROOMID"]+"</span>";
			});
		if(json.length == 0){
	 		$("#room_date").html("");
	 	}else{
	 		$("#room_date").html(tablere);			 	
	 	}
		}
 
//添加房间id确定按钮单击事件
 function chooseRoom(){
	 	var equipment = $("equipment_select").val();
	 	var emergent = $("emegent_select").val();
	 	var repairTime = $("repairTime").val();
 		var array = $("input:radio");
 		var type = $("#roomtype").val();
 		var roomtype = type.substring(type.indexOf("(")+1,type.indexOf(")"));
 		var val = "";
 		$.each(array,function(index){
 	 		if(array[index]["checked"]){
 		 		val = val +$(array[index]["parentNode"]).text()+",";
 		 		}
 	 		});
 		newstr = val.substring(0,val.length-1);
 		if(newstr){
 			$('#roomid').val(newstr);
 			$("#roomtype_class").css("display", "none");
 		}else{
 			showMsg("请您选择至少一个房间");
 		}
 		$.ajax({//自动获取
 			url: path + "/getApplicationDate.do",
 			type: "post",
 			data : {"roomid" : newstr,"roomtype" : roomtype },
 			success: function(json) {
 				$("#contrartid").val(json.contrartid);
 				$("#applyname").val(json.membername);
 				$("#applymobile").val(json.mobile);
 			},
 			error: function() {}
 		});
 		}
 
 //双击事件
 function showDetail(e){
	var applyId = $($(e).find("td").get(1)).html();
	window.parent.JDialog.open("维修申请记录详情",base_path+"/dblRepairDetail.do?applyId="+applyId,950,300);
 }
 
 //更改状态为取消,取消申请
 function cancelApplication(e){
	var applyId = $($(e).parent().parent().find("td").get(1)).html();
	var mainstatus = $($(e).parent().parent().find(".status")).html();
	 $.ajax({
         url: path + "/cancel.do",
		 type: "post",
		 data : {"applyId" : applyId,"mainstatus" : mainstatus },
		 success: function(json) {
				window.parent.showMsg(json.message);
				setTimeout(" window.parent.JDialog.close();",1000);
				setTimeout("location.reload(true)",1500);	
		 },
		 error: function() {}
	});
 }
 //处理已修復申請記錄,打开页面
 function doneApplication(e){
	 var applyId = $($(e).parent().parent().find("td").get(1)).html();
	 var applystatus = $($(e).parent().parent().find(".status")).html();
	 var post = $($(e).parent().parent().find(".post")).html();
	 var auditRemark = $($(e).parent().parent().find(".auditremark")).html();
	 if((applystatus == "已审核" && post == "*") || applystatus == "未修复"){
		 window.parent.JDialog.open("添加修复信息", base_path+"/doneApplication.do?applyId="+applyId, 700,400);
	 }else{
		 if(applystatus == "已审核"){
			 window.parent.showMsg("多级审核中,无法修复!");
			 
		 }else{
			 window.parent.showMsg("该记录"+applystatus+",无法修复!");
		 }
	 }
 }
 //添加维修日志修复确定按钮单击事件
 function addRepairLog(){
	 var problemtab = $($(".add_repair").find("li").get(0)).find("input").not(":first");
	 var newstr ="";
	 for ( var int = 0; int < problemtab.length; int++) {
		newstr += $(problemtab[int]).val()+",";
	}
	 var problemdesc = $("#problemdesc").val();
	 var repairPerson = $("#repairPerson_CUSTOM_VALUE").val();
	 var timearea = $("#timearea_select").val();
	 var status = $("#status_select").val();
	 var repairLogRemark = $("#repairLogRemark").val();
	 var applyId = $("#applyId").val();
	 var repairTime = $("#repairDate").val();
	 $.ajax({
		 url: path + "/addRepairLogRecord.do",
		 type: "post",
		 data : {"applyId" : applyId,
		 "problemtab" : newstr,
		 "problemdesc" : problemdesc,
		 "repairPerson" : repairPerson,
		 "timearea" : timearea,
		 "status" : status,
		 "repairLogRemark" : repairLogRemark,
		 "repairTime" :repairTime
	 },
	 success: function(json) {
		 if(json.result=="4"){
			 showMsg(json.message);
			 setTimeout(" window.parent.JDialog.close();",1500);
			 if(json.data == true){
				 $(window.parent.document.getElementById("repairIframe")).attr("src","repairInHouseData.do");
			 }else{
				 
				 $(window.parent.document.getElementById("repairIframe")).attr("src","repairData.do");
			 }
			 
		 }else{
			 showMsg(json.message);
		 }
	 },
	 error: function() {}
	 }); 
 }
 /**
  * 日志详情
  */
function showLog(e){
	 var applyId = $($(e).parent().parent().find("td").get(1)).html();
	 window.parent.JDialog.open("维修日志详情",base_path+"/loadRepairLog.do?applyId="+applyId,1200,300);

}	 
 /**
  * 维修处理点击员工弹出所有职工可选择
  */
$(document).on("click","#repairPerson",function(){

	JDialog.open("维修人员 ", path +"/selectRepairPerson.do",450,350);
}); 
 
 
 
 
 
 

 


