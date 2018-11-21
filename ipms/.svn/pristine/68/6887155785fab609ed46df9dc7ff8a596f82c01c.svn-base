//初始化页面
$(document).ready(function() {

	// 加载交接数据页面
		$("#frame").attr(
				'src',
				"pettypayData.do?cashbox=" + $("#cashbox").val() + "&voucher="
						+ $("#voucher").val() + "&state=" + $("#state").val());

		// 根据登录人的post判断加载不同的按钮
		var recordUser = $("#recordUser").val();
		if (recordUser == "%") {
			$("#paybutton").css("display", "block");
			$("#savebutton").css("display", "none");
		}

		// 计算投缴金额数据
		var allpay = $("#allpay").val();
		var havepay = $("#havepay").val();
		var shavepay = (parseFloat(allpay) - parseFloat(havepay)) + "元";
		$("#shavepay").val(shavepay);

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

// checkbox已勾选数据计入数组
var content = new Array();
$("input[name='pettycheckbox']").bind("click", function() {
	var logid = $(this).parent().parent().children("td").eq(1).html();
	if ($(this).is(':checked')) {
		content.push(logid);
	} else {
		for ( var i = 0; i < content.length; i++) {
			var s = content[i];
			if (s == logid) {
				content.splice(i, 1);
				i--;
			}
		}
	}
});

// 投缴按钮
function pettypaysubmit() {
	if (!content.length) {
		showMsg("请至少选择一条交接数据进行投缴")
	} else {
		var newarr = new Array();
		var arrstr = '[';
		$.each(content, function(index) {
			arrstr = arrstr + JSON.stringify(content[index]) + ',';
		})
		var newstr = arrstr.substring(0, arrstr.length - 1);
		newstr = newstr + ']';
		JDialog.open("投缴", base_path + "/forPay.do?newstr=" + newstr, 350, 200);
	}
}

// 判断输入框必须为数字（保留俩位小数）
function num(obj) {
	obj.value = obj.value.replace(/[^\d.]/g, ""); // 清除"数字"和"."以外的字符
	obj.value = obj.value.replace(/^\./g, ""); // 验证第一个字符是数字
	obj.value = obj.value.replace(/\.{2,}/g, "."); // 只保留第一个, 清除多余的
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
			".");
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); // 只能输入两个小数
}

// showMsg
function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

// 刷新按钮
function pettypayrefresh() {
	location.reload(true);
}

//全选按钮

$("#payall").click(function(){
	  var x=document.getElementsByName("pettycheckbox");
      for(var i=0; i<x.length; i++ ){
      var weekendid =x[i].id;
      var chk = document.getElementById(weekendid);
      var checked = $("#"+weekendid).is(":checked");
      if(checked){
    	  chk.checked = false;
      }else{
    	  chk.checked = true;
      }
    
      }
      
      
      //var logid = $(this).parent().parent().children("td").eq(1).html();
  	//if ($(this).is(':checked')) {
  		//content.push(logid);
  //	} else {
  		//for ( var i = 0; i < content.length; i++) {
  			//var s = content[i];
  			//if (s == logid) {
  				//content.splice(i, 1);
  				//i--;
  			//}
  		//}
  	//}
});



// 查询按钮（根据查询条件展示不同的数据项）
function pettypaysearch() {
	var starttime = $("#start").val();
	var endtime = $("#end").val();
	if ((starttime.length > 0) && (endtime.length > 0)) {
		if (((Date.parse(endtime) - Date.parse(starttime)) / 3600 / 1000) <= 0) {
			showMsg("开始时间不能等于或晚于结束时间")
		} else {
			$("#frame").attr(
					'src',
					"pettypayData.do?start=" + $("#start").val() + "&end="
							+ $("#end").val() + "&voucher="
							+ $("#voucher").val() + "&state="
							+ $("#state").val());

		}
	} else if ((!starttime) && (!endtime)) {
		$("#frame").attr(
				'src',
				"pettypayData.do?start=" + $("#start").val() + "&end="
						+ $("#end").val() + "&voucher=" + $("#voucher").val()
						+ "&state=" + $("#state").val());
	} else {
		showMsg("如需按照时间查询，则开始时间和结束时间必须都要填写")
	}
}

// 保存按钮（保存修改的数据并提交到数据库）
function pettypaysave() {
	var tableInfo = "";
	var tableObj = document.getElementById("myTable");
	var savestatus = "success";
	for ( var i = 1; i < tableObj.rows.length; i++) { // 遍历Table的所有Row
		var logid = tableObj.rows[i].cells[1].innerText;
		var cashin = tableObj.rows[i].cells[8].firstChild.value;
		var cashout = tableObj.rows[i].cells[9].firstChild.value;
		var paymentvalue = tableObj.rows[i].cells[10].firstChild.value;
		var shiftvalue = tableObj.rows[i].cells[11].firstChild.value;
		var lastshiftvalue = tableObj.rows[i].cells[12].firstChild.value;
		var currshiftvalue = tableObj.rows[i].cells[13].firstChild.value;
		var cardbalance = tableObj.rows[i].cells[14].firstChild.value;
		var cards = tableObj.rows[i].cells[15].firstChild.value;
		var depositno = tableObj.rows[i].cells[16].firstChild.value;
		var invoiceno = tableObj.rows[i].cells[17].firstChild.value;
		if (!cashin) {
			showMsg("当前页面表格第" + i + "行现金收入不能为空，为空请输入0");
			break;
		} else if (!cashout) {
			showMsg("当前页面表格第" + i + "行现金支出不能为空，为空请输入0");
			break;
		} else if (!paymentvalue) {
			showMsg("当前页面表格第" + i + "行投缴金额不能为空，为空请输入0");
			break;
		} else if (!shiftvalue) {
			showMsg("当前页面表格第" + i + "行备用金交接金额不能为空，为空请输入0");
			break;
		} else if (!lastshiftvalue) {
			showMsg("当前页面表格第" + i + "行补上班次金额不能为空，为空请输入0");
			break;
		} else if (!currshiftvalue) {
			showMsg("当前页面表格第" + i + "行本班需补金额不能为空，为空请输入0");
			break;
		} else if (!cardbalance) {
			showMsg("当前页面表格第" + i + "行银行卡收入不能为空，为空请输入0");
			break;
		} else if (!cards) {
			showMsg("当前页面表格第" + i + "行银行卡笔数不能为空，为空请输入0");
			break;
		} else if (!/^\d+|\d+\.\d{1,2}$/gi.test(cashin)) {
			showMsg("当前页面表格第" + i + "行现金收入必须为数字或者保留俩位小数");
			break;
		} else if (!/^\d+|\d+\.\d{1,2}$/gi.test(cashout)) {
			showMsg("当前页面表格第" + i + "行现金支出必须为数字或者保留俩位小数");
			break;
		} else if (!/^\d+|\d+\.\d{1,2}$/gi.test(paymentvalue)) {
			showMsg("当前页面表格第" + i + "行投缴金额必须为数字或者保留俩位小数");
			break;
		} else if (!/^\d+|\d+\.\d{1,2}$/gi.test(shiftvalue)) {
			showMsg("当前页面表格第" + i + "行备用金交接金额必须为数字或者保留俩位小数");
			break;
		} else if (!/^\d+|\d+\.\d{1,2}$/gi.test(lastshiftvalue)) {
			showMsg("当前页面表格第" + i + "行补上班次金额必须为数字或者保留俩位小数");
			break;
		} else if (!/^\d+|\d+\.\d{1,2}$/gi.test(currshiftvalue)) {
			showMsg("当前页面表格第" + i + "行本班需补金额必须为数字或者保留俩位小数");
			break;
		} else if (!/^\d+|\d+\.\d{1,2}$/gi.test(cardbalance)) {
			showMsg("当前页面表格第" + i + "行银行卡收入必须为数字或者保留俩位小数");
			break;
		} else if (!/^\d+$/.test(cards)) {
			showMsg("当前页面表格第" + i + "行银行卡笔数必须为数字");
			break;
		} else {
			if (depositno) {
				if (!/^\d+$/.test(depositno)) {
					showMsg("当前页面表格第" + i + "行押金收据必须为数字");
					return;
				}
			}

			if (invoiceno) {
				if (!/^\d+$/.test(invoiceno)) {
					showMsg("当前页面表格第" + i + "发票号必须为数字");
					return;
				}
			}
			$.ajax({
				url : base_path + "/payUpdate.do",
				type : "post",
				dataType : "json",
				data : {
					'logid' : logid,
					'cashin' : cashin,
					'cashout' : cashout,
					'paymentvalue' : paymentvalue,
					'shiftvalue' : shiftvalue,
					'lastshiftvalue' : lastshiftvalue,
					'currshiftvalue' : currshiftvalue,
					'cardbalance' : cardbalance,
					'cards' : cards,
					'depositno' : depositno,
					'invoiceno' : invoiceno

				},
				success : function(json) {
					if (1 == json.result) {
					} else {
						savestatus = "fail"
					}
				},
				error : function() {
					savestatus = "fail"
				}
			});

		}
	}

	if (savestatus == "success") {
		window.parent.parent(showMsg("保存成功"));
		window.setTimeout("location.reload(true)", 1800);
	} else if (savestatus == "fail") {
		showMsg("操作失败");
		window.setTimeout("location.reload(true)", 1800);
	}

}
