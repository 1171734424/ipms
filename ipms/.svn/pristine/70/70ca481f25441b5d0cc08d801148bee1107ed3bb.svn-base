
//初始化焦点定位
$(document).ready(function() {
	
	$("#orderid").focus();
	
});

//showMsg
function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}


//按条件查询数据
function osearchdata() {
	var orderid = $("#orderid").val();
	var orderuser = $("#orderuser").val();
	var roomtype = $("#roomtype").val();
	var mobile = $("#mobile").val();
	var memberid = $("#memberid").val();
    $("#orderIframe").attr("src","orderData.do?orderid=" + orderid + "&orderuser="+ orderuser + "&roomtype=" + roomtype+ "&mobile=" + mobile + "&memberid=" + memberid);
}