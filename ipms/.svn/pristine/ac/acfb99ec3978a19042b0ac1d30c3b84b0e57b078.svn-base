function showMsg(msg, fn) {
	if (!fn) {
	TipInfo.Msg.alert(msg);
	   } else {
	TipInfo.Msg.confirm(msg, fn);
	  }
	}
function alive(){
	alert(1);
	$(".a").die("click");
}
$("input[name='gdsdown']").bind("click",function() {
	     var saletype = "singlesale";
         var gdsid = $(this).parent().parent().children("td").eq(1).html();
         var gdsname = $(this).parent().parent().children("td").eq(2).html();
         var gdsprice = $(this).parent().parent().children("td").eq(4).html();
         $.ajax({
				url : base_path + "/gdsDown.do",
				type : "post",
				dataType : "json",
				data : {
					'gdsid' : gdsid
				},
				success : function(json) {
					if (1 == json.result) {
					    showMsg("下架成功！");
					    window.setTimeout("location.reload(true)", 1800);
					    window.setTimeout("window.parent.JDialog.close();", 1800);
						
					} else {
						showMsg("下架失败！");
						window.setTimeout("location.reload(true)", 1800);
						 window.setTimeout("window.parent.JDialog.close();", 1800);
					}
				},
				error : function() {
					showMsg("操作失败，请重新操作！");
					window.setTimeout("location.reload(true)", 1800);
					 window.setTimeout("window.parent.JDialog.close();", 1800);
				}
			    });
    })
    
    
    
    $("input[name='gdsup']").bind("click",function() {
	     var saletype = "singlesale";
         var gdsid = $(this).parent().parent().children("td").eq(1).html();
         var gdsname = $(this).parent().parent().children("td").eq(2).html();
         var gdsprice = $(this).parent().parent().children("td").eq(4).html();
         $.ajax({
				url : base_path + "/gdsUp.do",
				type : "post",
				dataType : "json",
				data : {
					'gdsid' : gdsid
				},
				success : function(json) {
					if (1 == json.result) {
					    showMsg("上架成功！");
					    window.setTimeout("location.reload(true)", 1800);
					    window.setTimeout("window.parent.JDialog.close();", 1800);
						
					} else {
						showMsg("上架失败！");
						window.setTimeout("location.reload(true)", 1800);
						 window.setTimeout("window.parent.JDialog.close();", 1800);
					}
				},
				error : function() {
					showMsg("操作失败，请重新操作！");
					window.setTimeout("location.reload(true)", 1800);
					 window.setTimeout("window.parent.JDialog.close();", 1800);
				}
			    });
    })