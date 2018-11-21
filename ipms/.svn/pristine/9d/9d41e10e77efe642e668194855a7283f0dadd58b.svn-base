var mobReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //手机校验
var idcardReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
var numberReg = /^[0-9]*[1-9][0-9]*$/; //校验正整数
var moneyReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/; //校验是金额

function getRootPath() {
    //获取当前网址，如： http://localhost:8088/test/test.jsp
    var curPath = window.document.location.href;
    //获取主机地址之后的目录，如： test/test.jsp
    var pathName = window.document.location.pathname;
    var pos = curPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8088
    var localhostPath = curPath.substring(0, pos);
    //获取带"/"的项目名，如：/test
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (projectName);//发布前用此
}

//content输入内容，num保留字符个数
function beyondContent(content, num){
	if(!content)
		return '';
	if(!num || !numberReg.test(num) || num <=0)
		num = 4;
	if(content.length > num){
		content = content.substring(0, num) + "...";
	}
	return content;
}

//模拟orcale中的decode方法
function decode(){
	if(arguments.length < 3){
		TipInfo.Msg.alert("缺少参数!");
//		alert("缺少参数!");
		return false;				
	}
	var parity;
	var flagchecked = false;
	if((arguments.length % 2) == 0){
		parity = true;
	}
	else{
		parity = false;
	}
	
	var defaultresult = '';
	for(var i = 1; i < arguments.length; i = i + 2){
		if(arguments[0] == arguments[i]){
			flagchecked = true;
			defaultresult = arguments[i+1];
			break;
		}
	}
	if(!flagchecked && parity){
		defaultresult = arguments[arguments.length-1];
	}
	return defaultresult;
}

function isWorkbillCheckout(workbillid){
	flag = false;
	msg = "";
	if( !workbillid || workbillid == ""){
		msg = "无此房单!"
		showMsg(msg);
		return false;
	}
	$.ajax({
        url: path + "/apartmentisWorkbillCheckout.do",
		 type: "post",
		 data : {workbillid : workbillid},
		 async:false,
		 success: function(json) {
			 if(json.result == 1){
				 showMsg(json.message);
				flag = true;
			 }
		 },
		 error: function(json) {}
	});
	return flag;
}

function isCheckout(checkid){
	flag = false;
	msg = "";
	if( !checkid || checkid == ""){
		msg = "房单空号!"
		showMsg(msg);
		return false;
	}
	$.ajax({
        url: path + "/apartmentisCheckout.do",
		 type: "post",
		 data : {checkid : checkid},
		 async:false,
		 success: function(json) {
			 if(json.result == 1){
				showMsg(json.message);
				flag = true;
			 }
		 },
		 error: function(json) {}
	});
	return flag;
}

function isCustomout(checkid){
	flag = false;
	msg = "";
	if( !checkid || checkid == ""){
		msg = "房单空号!"
		showMsg(msg);
		return false;
	}
	$.ajax({
        url: path + "/apartmentisCustomout.do",
		 type: "post",
		 data : {checkid : checkid},
		 async:false,
		 success: function(json) {
			 if(json.result == 1){
				showMsg(json.message);
				flag = true;
			 }
		 },
		 error: function(json) {}
	});
	return flag;
}

function isMobile(mobile){
	if(mobReg.test(mobile)){
		return true;
	}else{
		var msg = "不是有效的手机号!";
		showMsg(msg);
		return false;
	}
}

function isIdcard(idcard){
	var msg = "";
	if(idcardReg.test(idcard)){
		return true;
	}else{
		if(!idcard){
			msg = "身份证为空!";
		}else{
			msg = "身份证不合法!";
		}
		showMsg(msg);
		return false;
	}
}

function isEmail(email){
	if(emailReg.test(email)){
		return true;
	}else{
		if(email){
			var msg = "邮箱不合法!";
			showMsg(msg);		
		}
		return false;
	}
}

function dealDateOne(myT){
	console.log(myT)
	myT.replace("-", "/");
	return myT;
}

function dealDate(myT){
	if(myT){
		var result;
		var year = myT.year + 1900;
		var month = myT.month + 1;
		var date = myT.date;
		var hour = myT.hours;
		var minute = myT.minutes;
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (date >= 1 && date <= 9) {
			date = "0" + date;
		}
		if (hour >= 0 && hour <= 9) {
			hour = "0" + hour;
		}
		if (minute >= 0 && minute <= 9) {
			minute = "0" + minute;
		}
		result = year + "/" +month + "/" + date + " " + hour + ":" + minute;
		return(result)
	}else{
		return '';
	}
}

function dealLocalDate(myT){
	var year = myT.getFullYear();
	var month = myT.getMonth() + 1;
	var strDate = myT.getDate();
	var hour = myT.getHours();
	var minute = myT.getMinutes();
	
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
	if (hour >= 0 && hour <= 9) {
		hour = "0" + hour;
	}
	if (minute >= 0 && minute <= 9) {
		minute = "0" + minute;
	}
    var currentdate = year + "/" + month + "/" + strDate
    + " " + hour + ":" + minute;
    return currentdate;
}



function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

function converttosomething(oldsome, reason, newsome){
	return oldsome == reason ? newsome : oldsome;
}

//flag表示刷新父页面表示， true代表关闭父页面， false代表不关闭父页面
function showMsg(msg, fn, fn1, flag) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn, fn1);
	}
}

//min可以不输入
/**
 * content 内容
 */
function checkLength(content, title, max, min){
	var length = content.length;
	var msg =""; 
	if(title) msg = title + msg;
	if(min){
		if(length <= max && length >= min){
			return true;
		}else {
			msg = msg + "长度在于" + min + "-" + max + "位之间!";
			showMsg(msg);
			return false;
		}
	}else{
		if(length <= max){
			return true;
		}else{
			msg = msg + "长度应小于" + max + "位!";
			showMsg(msg);
			return false;
		}
	}
}

function checkedbytruns(e){
	var checkbox = $(e).find(':checkbox');
	var checked = checkbox.prop("checked");
	//checkbox.checked = !checked;
    checkbox.prop('checked', !checked);
}

function isNull(e, title){
	if(e.val().length == 0){
		var msg = "不能为空!";
		if(title) msg = title + msg;
		showMsg(msg);
		e.focus();
		return true;
	}else {
		return false;
	}
}

function isSelected(e, title){
	if(!e.val()){
		var msg = "不能为空!";
		if(title) msg = title + msg;
		showMsg(msg);
		e.focus();
		return true;
	}else {
		return false;
	}
}

function isNumber(number,title){
	if(numberReg.test(number)){
		return true;
	}else{
		if(number && title){
			var msg = "[" + title + "] 输入正确的数字格式!";
			showMsg(msg);
		}
		return false;
	}
}

function isMoney(money,title){
	if(moneyReg.test(money)){
		return true;
	}else{
		if(money && title){
			var msg = "[" + title + "] 输入正确的金额格式!";
			showMsg(msg);		
		}
		return false;
	}
}
//获取下月最大日期	
function getLastDay(year,month) { 
    var new_year = year;    //取当前的年份          
    var new_month = ++month;//取下一个月的第一天，方便计算（最后一天不固定）       
    if(month>11) {         
     new_month -=12;        //月份减          
     new_year++;            //年份增          
    }         
    var new_date = new Date(new_year,new_month,1); //往后推迟2个月的一号
    // var new_date = new Date(new_year,0,1); 
       //alert(new_date)        //取当年当月中的第一天          
    return (new Date(new_date.getTime()-1000*60*60*24));//获取当月最后一天日期          
} 
