$(document).ready(function() {

	// 加载交接数据页面
	   var logintime = $("#logintime").val();
		$("#frame").attr('src',"shiftbillData.do?logintime=" +logintime);

		// 根据登录人的post判断加载不同的按钮
		var recordUser = $("#recordUser").val();
		if (recordUser == "%") {
			$("#paybutton").css("display", "block");
			$("#savebutton").css("display", "none");
		}
		
		// 时间控件
		laydate.render( {
			elem : '#start',
			type : 'datetime',
			format : 'yyyy/MM/dd HH:mm:ss'
		});

		laydate.render( {
			elem : '#end',
			type : 'datetime',
			format : 'yyyy/MM/dd HH:mm:ss'
		});

	})
	
	function shiftbillinfo(e) {
			var billid = $(e.children[3]).html();
			var datatype = $(e.children[14]).html();
			var recordtime = $(e.children[1]).html();
			var myDate = new Date();
			var y = myDate.getFullYear();    
			var m = myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
			var d = myDate.getDate();  
            var t = y+"/"+m+"/"+d+" "+"04:00:00";   
			if(datatype=="房账"){
			  if(((Date.parse(t) - Date.parse(recordtime)) / 3600 / 1000) > 0){
				  showMsg("非当日交接数据不能操作")
			  }else{
			         window.parent.parent.JDialog.openWithNoTitle("", path + "/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=" + billid,1179,733); 
			  }
			 
			}else if(datatype=="工作账"){
				 if(((Date.parse(t) - Date.parse(recordtime)) / 3600 / 1000) > 0){
					  showMsg("非当日交接数据不能操作")
				  }else{
			  window.parent.parent.JDialog.openWithNoTitle("", path + "/page/ipmshotel/work_account/work_account_check.jsp?workbillid=" +billid,1180,738);
				  }
			}
			
		}
	
	
	// 查询按钮（根据查询条件展示不同的数据项）
function pettycashsearch() {
	var starttime = $("#start").val();
	var endtime = $("#end").val();
	if ((starttime.length > 0) && (endtime.length > 0)) {
		if (((Date.parse(endtime) - Date.parse(starttime)) / 3600 / 1000) <= 0) {
			showMsg("开始时间不能等于或晚于结束时间")
		} else {
			$("#frame").attr(
					'src',
					"shiftbillData.do?start=" + $("#start").val() + "&end="
							+ $("#end").val() + "&voucher="
							+ $("#voucher").val() + "&state="
							+ $("#state").val()+"&paytype="+$("#paytype").val());

		}
	} else if ((!starttime) && (!endtime)) {
		$("#frame").attr(
				'src',
				"shiftbillData.do?start=" + $("#start").val() + "&end="
						+ $("#end").val() + "&voucher=" + $("#voucher").val()
						+ "&state=" + $("#state").val()+"&paytype="+$("#paytype").val());
	} else {
		showMsg("如需按照时间查询，则开始时间和结束时间必须都要填写")
	}
}

//showMsg
function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

//刷新按钮
function pettypayrefresh() {
	location.reload(true);
}

//跳转投缴页面
function pettypaysubmit() {
	var nowweek = "";  
	var week = new Date().getDay(); 
	var days ="2";
	if(week == 1){
		days ="3";
	}
	if ((week == 2)||(week == 4)||(week == 6)||(week == 0)) {  
		showMsg("今日非投缴日（投缴日：周一、三、五）")
	} else{
		$.ajax({
			url : base_path + "/shiftpayCount.do",
			type : "get",
			data : {
				'days' : days

			},
			success : function(shiftcashmap) {
				var shiftcashin = shiftcashmap.cashin;
				var shiftcashout = shiftcashmap.cashout;
				    JDialog.openWithNoTitle("", base_path + "/cashShiftpay.do?shiftcashin="+shiftcashin+"&shiftcashout="+shiftcashout+"&days="+days,450,250);
				},
				error : function() {
				}
		});

	}  
}

//input保留俩位小数
function num(obj) {
	 obj.value = obj.value.replace(/[^\d.]/g, ""); // 清除"数字"和"."以外的字符
	 obj.value = obj.value.replace(/^\./g, ""); // 验证第一个字符是数字
	 obj.value = obj.value.replace(/\.{2,}/g, "."); // 只保留第一个, 清除多余的
	 obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
				".");
	 obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); // 只能输入两个小数
	}

//投缴
function pay(){
var currpay = $("#currpay").val();
var shouldpay = $("#totalprice").val();
var voucherpay = $("#voucherpay").val();
var nowweek = "";  
var week = new Date().getDay(); 
var days ="2";
if(week == 1){
	days ="3";
}
 if(!currpay){
   showMsg("投缴金额不能为空");
 }else if(!voucherpay){
    showMsg("凭证号不能为空");
 }else if(!/^\d+|\d+\.\d{1,2}$/gi.test(currpay)){
    showMsg("投缴金额必须为数字或者保留俩位小数");
 
 }else if(!/^\d+$/.test(voucherpay)){
    showMsg("凭证号必须为数字");
 
 }else if(currpay!=shouldpay){
    showMsg("输入金额必须与应缴金额一致");
 
 }else{
 

 $.ajax({
	url : base_path + "/shiftpayUpdate.do",
	type : "post",
	dataType : "json",
	data : {
		'voucherpay' : voucherpay,
		'shouldpay' : shouldpay,
		'days' :days

	},
	success : function(json) {
		if (1 == json.result) {
		 if(json.message){
		   showMsg(json.message);
		 }else{
		   showMsg("投缴成功");
		   window.setTimeout("window.parent.JDialog.close();",
					1800);
		 }
		} 
	},
	error : function() {
		showMsg("投缴失败");
	}
});
 
 }

}


