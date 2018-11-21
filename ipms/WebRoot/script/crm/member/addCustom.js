$(function() {
	$("#total_recharge").bind("click",function(){
	 	JDialog.open("充值活动", "selectCampaign.do?memberRank="+$("#memberRank").val(),450,350);
	});
});
function updateReserve(){
	var dataId = $("#total_recharge_CUSTOM_VALUE").val();
	var account_Id = $("#account_Id").val();
	var member_Id = $("#member_Id").val();
	var discount_gift = $("#discount_gift").val();
	if("" == dataId && "" == discount_gift){
		window.parent.showMsg("请选择活动!</br>或</br>填写充值金额!");
		return false;
	}
	if("" != dataId && "" != discount_gift){
		window.parent.showMsg("活动,充值金额只能选填一项!!!");
		return false;
	}
	if(Number(discount_gift) > 10000000){
		window.parent.showMsg("充值金额过大!!!");
		return false;
	}
	$.ajax({
		url: "changeMemberScoreAndReserve.do",
		dataType: "json",
		type: "post",
		data:{
			'account_Id' : account_Id,
			'dataId' : dataId,
			'member_Id' : member_Id,
			'discount_gift' : discount_gift
		},
		success: function(json) {
		        window.parent.showMsg("充值成功!");
		        window.setTimeout("window.parent.location.reload(true)",800);
			   
		},
		error:function(json){
				window.parent.showMsg("充值失败!");
			    window.setTimeout("window.parent.location.reload(true)",1000);
		}
	})
}
function empty(){
	$("#total_recharge").val("");
	$("#total_recharge_CUSTOM_VALUE").val("");
	$("#discount_gift").val("");
}
