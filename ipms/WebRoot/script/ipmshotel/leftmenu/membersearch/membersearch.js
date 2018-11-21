
$(document).ready(
		function() {
			$("#msoidcard").focus();
			var gendorid = $("#gendorid").val();
			 $("#gender option[value='"+gendorid+"']").attr("selected",true);
		});

function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}

function msidsearch(){
	var msoidcard = $("#msoidcard").val();
	var mphone ="";
	var mcard = "";
	var data = "";
	if((msoidcard.length)>11){
		mcard = msoidcard;
	}else if((msoidcard.length)=11){
		mphone = msoidcard;
	}
	if(msoidcard){
	var re = /^(1[358][0-9]{9})$/;
	var reg = /^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\d{4}(([1][9]\d{2})|([2]\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\d{3}[0-9xX]$/;
		if(re.test(msoidcard) || reg.test(msoidcard)){		
			 $.ajax({
					url : base_path + "/hotelmemberJudge.do",
					type : "post",
					dataType : "json",
					data : {
						'data' :msoidcard
					},
					success : function(json) {
						if (1 == json.result) {
						    if(json.message){
						        showMsg(json.message);
						       }else{
						    	   $("#msIframe").attr("src","hotelmemberSearchdata.do?data="+msoidcard);
						       }
							
						} else {
							showMsg("没有该会员的信息，请输入正确的会员卡号或手机号！");
							window.setTimeout("location.reload(true)", 1800);
						}
					},
					error : function() {
						showMsg("操作失败，请重新操作！");
						window.setTimeout("location.reload(true)", 1800);
					}
				    });
					
		}else{
			showMsg("请先输入正确的手机号或身份证号格式（数字或字母）！")
		}
	}else{
	 showMsg("请先输入手机号或身份证号！")	
	}
}


function editmemberdata(){
	var memberid = $("#msmemberid").val();
	var mobile = $("#msmobile").val();
	var email = $("#msemail").val();
	var address = $("#msadress").val();
	var remark = $("#msremark").val();
	var idcard = $("#idcard").val();
	if(idcard){
	if(!isIdcard(idcard)){
		return false;
	}
	}
	if(!isEmail(email) && email!=""){
		return false;
	}
	if(!checkLength(address, '地址：', 30) && address !="" ){
		return false;
	}
	if(!checkLength(remark, '备注：', 100) && remark !="" ){
		return false;
	}
	jQuery.ajax( {
		url : "hoteleditmemberData.do",
		dataType : "json",
		type : "post",
		data : {
			"memberid" : memberid,
			"mobile" : mobile,
			"email" :email,
			"address" : address,
			"remark" : remark,
			"idcard" : idcard
		},
		success : function(json) {
			if (1 == json.result) {
				showMsg(json.message);
				 window.setTimeout("location.reload(true)", 1800);
				 window.setTimeout("window.parent.parent.JDialog.close();", 1800);
			} else {
				showMsg(json.message);
				 window.setTimeout("location.reload(true)", 1800);
				 window.setTimeout("window.parent.parent.JDialog.close();", 1800);
			}
		}
	});
}


function readIdcard(){
	showMsg("暂未开放证件接口！");
}