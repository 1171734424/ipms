function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

$("#defaultAudit").bind("click", function() {
	JDialog.open("查询", base_path + "/selectAuditer.do?", 450, 350);
});

function auditUserSure() {
	var auditId = $("#defaultAuditId").val();
	$.ajax({
		url : base_path + "/auditUserSure.do",
		type : "post",
		dataType : "json",
		data : {
			'auditId' : auditId
		},
		success : function(json) {
			if (1 == json.result) {
				showMsg("设置成功!");
				window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("设置失败！");
				window.setTimeout("location.reload(true)", 1800);
			}
		},
		error : function() {
			showMsg("操作失败，请重新操作！");
			window.setTimeout("location.reload(true)", 1800);
		}
	});

}

function defaultReset() {
	location.reload(true);
}