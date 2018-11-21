$("input[name='dealrecord']").bind("click",function() {
        var recordid = $(this).parent().parent().children("td").eq(0).html();
//        alert(recordid)
        $.ajax({
			url : base_path + "/updaterecordstatus.do",
			type : "post",
			dataType : "json",
			data : {
				'recordid' : recordid
			},
			success : function(json) {
				   showMsg(json.message);
			        window.setTimeout("location.reload(true)",1800);
			},
			error : function() {
				showMsg("操作失败，请重新操作！");
				window.setTimeout("location.reload(true)", 1800);
				 window.setTimeout("window.parent.JDialog.close();", 1800);
			}
		    });
      
   })
   
function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}