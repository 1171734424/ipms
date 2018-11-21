   function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}


	$(document).ready(function(){
		    $(".date").datepicker({ dateFormat: "yy/mm/dd " });
		    //$("#ui-datepicker-div").css('font-size','0.5em');
		     $("#exbegintime").css('font-size','0.9em');
		     $("#exendtime").css('font-size','0.9em');
		
	});
	
	
	function exceptiondata(){
		var exbegintime = $("#exbegintime").val();
		var exendtime = $("#exendtime").val();
		var exceptiontype = $("#exceptiontype").val();
		var exceptionstatus = $("#exceptionstatus").val();
		
		if((exbegintime!="")&&(exendtime=="")){
			
			showMsg("请填写结束日期");
			
		}else if((exbegintime=="")&&(exendtime!="")){
			
			showMsg("请填写开始日期");
			
		}else{
			
			$("#exceptionIframe").attr("src","exceptionCondition.do?exbegintime="+exbegintime+"&exendtime="+exendtime+"&exceptiontype="+exceptiontype+"&exceptionstatus="+exceptionstatus);	
		}
		
		
	}
	
	
	$("input[name='exceptiondo']").bind("click",function() {
        var exceptionid = $(this).parent().parent().children("td").eq(1).html();
        $.ajax({
			url : base_path + "/exceptioncs.do",
			type : "post",
			dataType : "json",
			data : {
				'exceptionid' : exceptionid
			},
			success : function(json) {
				if (1 == json.result) {
				    showMsg("处理成功！");
				    window.setTimeout("location.reload(true)", 1800);
				    window.setTimeout("window.parent.JDialog.close();", 1800);
					
				} else {
					showMsg("处理失败！");
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