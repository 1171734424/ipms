function savaMemberRank(){
	var money = $("#money").val();
	var accountid = $("#accountid").val();
	var memberid = $("#memberid").val();
	var memberrank = $("#memberrank").val();
	var paytype = $("#paytype").val();
	if("" == paytype){
		window.parent.parent.showMsg("请选择支付方式!");
		return false;
	}
	$.ajax({
		url: "savaMemberRank.do",
		dataType: "json",
		type: "post",
		data: { 'accountid' : accountid,
                'money' : money,
                'memberid' : memberid,
                'memberrank' : memberrank,
                'paytype' : paytype
           		},
		success: function(json) {
		        window.parent.parent.showMsg("添加成功!");
		        window.setTimeout("window.parent.parent.location.reload(true)",1800);
			   
		},
		error:function(json){
				window.parent.parent.showMsg("添加失败!");
			    window.setTimeout("window.parent.location.reload(true)",1800);
		}
	})
	
}