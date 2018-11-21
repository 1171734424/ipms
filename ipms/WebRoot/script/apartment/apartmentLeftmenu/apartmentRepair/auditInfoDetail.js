
$(document).ready(function() {
	var pagetype = $("#pagetype").val();
	if(pagetype=="auditlog"){
		$("#tableInfoTitle").hide();
		 $(".appinfo_check").css("height", "100%");
		 $(".appinfo_repair").css("height", "100%");
		 $(".rpauditdata_table").css("height", "70%");
		 $(".appinfo_c").css("height", "90%");
	}else if(pagetype=="audit"){
		$("#tableInfoTitleLog").hide();
	}
})


function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}


function  auditSubmitOK(){
	 var audittype = $("#audittype").val();
	 var operid = $("#operid").val();
     var branchid = $("#branchid").val();
	 var applystaff =  $("#applystaff").val();
	 var auditMessage =  $("#auditMessage").val();
	 $.ajax({
			url : base_path + "/updateAuditSubmitOk.do",
			type : "post",
			dataType : "json",
			data : {
		        'audittype' : audittype,
				'operid' : operid,
				'applystaff' : applystaff,
				'branchId': branchid,
				'auditMessage' : auditMessage
			},
			success : function(json) {
				if (1 == json.result) {
					showMsg("审核成功!");
					window.setTimeout("window.parent.location.reload(true)", 1800);
					window.setTimeout("window.parent.JDialog.close()",2000);
				} else {
					showMsg("审核失败！");
					window.setTimeout("window.parent.location.reload(true)", 1800);
					window.setTimeout("window.parent.JDialog.close()",2000);
				}
			},
			error : function() {
				showMsg("操作失败，请重新操作！");
				window.setTimeout("window.parent.location.reload(true)", 1800);
				window.setTimeout("window.parent.JDialog.close()",2000);
			}
		});
}


function  auditSubmitback(){
	var audittype = $("#audittype").val();
	var operid = $("#operid").val();
    var branchid = $("#branchid").val();
	 var applystaff =  $("#applystaff").val();
	 var auditMessage =  $("#auditMessage").val();
	 $.ajax({
			url : base_path + "/updateAuditSubmitback.do",
			type : "post",
			dataType : "json",
			data : {
		        'audittype' : audittype,
				'operid' : operid,
				'applystaff' : applystaff,
				'branchId': branchid,
				'auditMessage' : auditMessage
			},
			success : function(json) {
				if (1 == json.result) {
					showMsg("打回成功!");
					window.setTimeout("window.parent.location.reload(true)", 1800);
					window.setTimeout("window.parent.JDialog.close()",2000);
				} else {
					showMsg("打回失败！");
					window.setTimeout("window.parent.location.reload(true)", 1800);
					window.setTimeout("window.parent.JDialog.close()",2000);
				}
			},
			error : function() {
				showMsg("操作失败，请重新操作！");
				window.setTimeout("window.parent.location.reload(true)", 1800);
				window.setTimeout("window.parent.JDialog.close()",2000);
			}
		});
}


function  auditSubmitFail(){
	 var audittype = $("#audittype").val();
	 var operid = $("#operid").val();
	 var branchid = $("#branchid").val();
	 var applystaff =  $("#applystaff").val();
	 var auditMessage =  $("#auditMessage").val();
	 $.ajax({
			url : base_path + "/updateAuditSubmitFail.do",
			type : "post",
			dataType : "json",
			data : {
		        'audittype' : audittype,
		        'operid' : operid,
		        'branchId': branchid,
			    'applystaff' : applystaff,
			    'auditMessage' : auditMessage
			},
			success : function(json) {
				if (1 == json.result) {
					showMsg("驳回成功!");
					window.setTimeout("window.parent.location.reload(true)", 1800);
					window.setTimeout("window.parent.JDialog.close()",2000);
				} else {
					showMsg("驳回失败！");
					window.setTimeout("window.parent.location.reload(true)", 1800);
					window.setTimeout("window.parent.JDialog.close()",2000);
				}
			},
			error : function() {
				showMsg("操作失败，请重新操作！");
				window.setTimeout("window.parent.location.reload(true)", 1800);
				window.setTimeout("window.parent.JDialog.close()",2000);
			}
		});
}