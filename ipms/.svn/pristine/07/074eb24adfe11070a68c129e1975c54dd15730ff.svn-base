/* 
================================================================== 

是否为空，只判断字符串 
null或0长为空，经过trim 
IsStringNull(string) 

================================================================== 

*/ 

function IsStringNull(str) { 
    if (str == null) 
        return true; 
    var trimStr = Trim(str); 
    if (trimStr.length == 0) 
        return true; 
    return false; 
} 
/* 

================================================================== 

LTrim(string):去除左边的空格 

================================================================== 

*/ 

function LTrim(str) { 
    var whitespace = new String(" \t\n\r"); 
    var s = new String(str); 

    if (whitespace.indexOf(s.charAt(0)) != -1) { 
        var j = 0, i = s.length; 
        while (j < i && whitespace.indexOf(s.charAt(j)) != -1) { 
            j++; 
        } 
        s = s.substring(j, i); 
    } 
    return s; 
} 


/* 

================================================================== 

RTrim(string):去除右边的空格 

================================================================== 

*/ 

function RTrim(str) { 
    var whitespace = new String(" \t\n\r"); 
    var s = new String(str); 
    if (whitespace.indexOf(s.charAt(s.length - 1)) != -1) { 
        var i = s.length - 1; 
        while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1) { 
            i--; 
        } 
        s = s.substring(0, i + 1); 
    } 
    return s; 
} 


/* 

================================================================== 

Trim(string):去除前后空格 

================================================================== 

*/ 

function Trim(str) { 
    return RTrim(LTrim(str)); 
} 

/* 

================================================================== 

IsOutOfLength(string,int):判断字符串是长度是否超出长度，中文为2个字符 

================================================================== 

*/ 

function IsOutOfLength(str, len) { 
	
    var strLength = 0; 
    for (var i = 0; i < str.length; i++) { 
        if (str.charCodeAt(i) > 256) { 
            strLength++; 
        } 
        strLength++; 
        if (strLength > len) { 
            return true; 
        } 
    } 
    return false; 
} 
/* 
================================================================== 

IsOutOfLength(string,int):判断字符串是长度是否超出长度，中文为3个字符 

================================================================== 

*/ 

function IsOutOfLength3(str, len) { 
    var cArr = str.match(/[^\x00-\xff]/ig); 
    var len_address = str.length + (cArr == null ? 0 : cArr.length * 2); 
    if (len_address > len) 
        return true; 
    else 
        return false; 
} 


/* 

================================================================== 

IsNumeric(string):判断字符串是是否为数字 

================================================================== 

*/ 


function IsNumeric(strNumber) { 
    if (strNumber.length == 0) { 
        return false; 
    } 
    return (strNumber.search(/^(-|\+)?\d+(\.\d+)?$/) != -1); 
} 
function IsNumericAdd(strNumber) { 
    if (strNumber.length == 0) { 
        return false; 
    } 
    return (strNumber.match(/^[0-9]*[1-9][0-9]*$/) == null); 
} 
/* 

================================================================== 

IsInt(string,string,int or string):(测试字符串,+ or - or empty,empty or 0) 

功能：判断是否为整数、正整数、负整数、正整数+0、负整数+0 

================================================================= 
*/ 

function IsInt(objStr, sign, zero,msg) { 
    var reg; 
    var bolzero; 


    if (Trim(objStr) == "") { 
    	alert("请输入" + msg +"!");
        return false; 
    } 
    else { 
        objStr = objStr.toString(); 
    } 


    if ((sign == null) || (Trim(sign) == "")) { 
        sign = "+-"; 
    } 


    if ((zero == null) || (Trim(zero) == "")) { 
        bolzero = false; 
    } 
    else { 
        zero = zero.toString(); 
        if (zero == "0") { 
            bolzero = true; 
        } 
        else { 
            alert("检查是否包含0参数，只可为(空、0)"); 
        } 
    } 


    switch (sign) { 
        case "none": 
            if (!bolzero) { 
                reg = /^[0-9]*[1-9][0-9]*$/; 
            } 
            else { 
                reg = /^[0-9]*[0-9][0-9]*$/; 
            } 
            break; 
        case "+-": 
        //整数 
            reg = /(^-?|^\+?)\d+$/; 
            break; 
        case "+": 
            if (!bolzero) { 
                //正整数 
                reg = /^\+?[0-9]*[1-9][0-9]*$/; 
            } 
            else { 
                //正整数+0 
                //reg=/^\+?\d+$/; 
                reg = /^\+?[0-9]*[0-9][0-9]*$/; 
            } 
            break; 
        case "-": 
            if (!bolzero) { 
                //负整数 
                reg = /^-[0-9]*[1-9][0-9]*$/; 
            } 
            else { 
                //负整数+0 
                //reg=/^-\d+$/; 
                reg = /^-[0-9]*[0-9][0-9]*$/; 
            } 
            break; 
        default: 
            alert("检查符号参数，只可为(空、+、-)"); 
            return false; 
            break; 
    } 
    var r = objStr.match(reg); 
    if (r == null) { 
    	alert("请正确输入"+msg+"!");
        return false; 
    } else { 
        return true; 
    } 
} 


/* 
================================================================== 

checkIsValidDate(string) 

功能：判断是否为正确的日期类型。必须为yyyy-MM-dd 

================================================================= 
*/ 
function checkIsValidDate(str) { 
    //如果为空，则通过校验 
    if (str == "") 
        return true; 
    var pattern = /^\d{4}\/\d{1,2}\/\d{1,2}$/g; 
    if (!pattern.test(str)) 
        return false; 
    //alert("【" +str+"】1"); 
    var arrDate = str.split("/"); 
    var date = new Date(arrDate[0], (parseInt(arrDate[1], 10) - 1) + "", parseInt(arrDate[2], 10) + ""); 
    //alert("a:【" +date.getFullYear()+"】【" + date.getMonth() + "】【" + date.getDate() + "】"); 
    //alert("b:【" +arrDate[0]+"】【" + parseInt(arrDate[1],10) + "】【" + parseInt(arrDate[2],10) + "】"); 
    if (date.getFullYear() == arrDate[0] 
            && date.getMonth() == (parseInt(arrDate[1], 10) - 1) + "" 
            && date.getDate() == parseInt(arrDate[2], 10) + "") 
        return true; 
    else 
    //alert("【" +str+"】2"); 
        return false; 
} 
/* 
================================================================== 

checkIsValidTime(string) 

功能：判断是否为正确的时间类型。必须为hh:mm:ss 

================================================================= 
*/ 
function checkIsValidTime(str) { 
    //如果为空，则通过校验 
    if (str == "") 
        return true; 
    var pattern = /^\d{1,2}:\d{1,2}:\d{1,2}$/g; 
    if (!pattern.test(str)) 
        return false; 
    //alert("【" +str+"】1"); 

    return true; 
} 

/* 

================================================================== 

CheckedCount(containForm,chkFormName):计算一个form中选中相的数目 
check表单包括radiobox和checkbox 
参数：包含check项的form,check表单的名称 
================================================================== 

*/ 
function CheckedCount(containForm, chkFormName) { 
    var chkCount = 0; 
    for (i = 0; i < containForm.elements.length; i++) { 
        if (containForm.elements[i].name == chkFormName) { 
            if (containForm.elements[i].type == 'checkbox' || containForm.elements[i].type == 'radio') { 
                if (containForm.elements[i].checked) { 
                    chkCount++; 
                } 
            } 
        } 
    } 
    return chkCount; 

} 

/** 
 * 判断是不是有效的email地址 
 */ 
function IsValidateEmail(str) { 
    //如果为空，则通过校验 
    if (str == "" || str.length == 0) { 
        return false; 
    } 

    //正则表达式 
    //var pattern = /^\w{1,}@[\.,\w]{1,}$/; 
    var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; 
    if (!pattern.test(str)) { 
        return false; 
    } 
    return true; 
} 

/** 
 * 判断是不是有效的汉字 
 */ 
function checkIsHanzi(str) { 
    //如果为空，则通过校验 
    if (str == "" || str.length == 0) { 
        return true; 
    } 

    //正则表达式 
    var pattern = /[^\u4E00-\u9FA5]/g; 
    if (pattern.test(str)) { 
        return false; 
    } 
    return true; 
} 
/** 
 * 判断是不是有效的英文字母+(空格） 
 */ 
function checkIsLetter(str) { 
    //如果为空，则通过校验 
    if (str == "" || str.length == 0) { 
        return true; 
    } 

    //正则表达式 
    var pattern = /[^a-zA-Z\s]/g; 
    if (pattern.test(str)) { 
        return false; 
    } 
    return true; 
} 
/** 
*判断是不是有效的英文字母+(空格或点） 
*/ 
function checkIsLetterOrSpaceDot(str) { 
    //如果为空，则通过校验 
    if (str == "" || str.length == 0) { 
        return true; 
    } 

    //正则表达式 
    var pattern = /[^a-zA-Z\s\.]/g; 
    if (pattern.test(str)) { 
        return false; 
    } 
    return true; 
} 
/** 
 * 判断是不是有效的英文字母和数字 
 */ 
function checkIsLetterNumber(str) { 
    //如果为空，则通过校验 
    if (str == "" || str.length == 0) { 
        return true; 
    } 

    //正则表达式 
    var pattern = /[^a-zA-Z0-9\s]/g; 
    if (pattern.test(str)) { 
        return false; 
    } 
    return true; 
} 
/** 
 * 判断是不是有效的数字（检查证件号码，密码用） 
 */ 
function checkIsNumber(str) { 
    //如果为空，则通过校验 
    if (str == "" || str.length == 0) { 
        return true; 
    } 

    //正则表达式 
    var pattern = /[^0-9\s]/g; 
    if (pattern.test(str)) { 
        return false; 
    } 
    return true; 
} 
/** 
 * 判断是不是有效的百分比数字 
 */ 
function checkIsPercent(str) { 
    //如果为空，则通过校验 
    if (str == "" || str.length == 0) { 
        return true; 
    } 

    //正则表达式 
    var pattern = /^[1-9][0-9]*%$/g; 
    if (!pattern.test(str)) { 
        return false; 
    } 
    return true; 
} 

/** 
 * check is validate time 
 */ 
function isValidateTime(str) { 
    if (parseInt(str) == 0) { 
        return true; 
    } 
    var regexp = /^(([0-9])|(0[0-9])|(1[0-9])|(2[0-3]))[0-5][0-9]$/ 
    if (str == "" || str.length == 0) { 
        return false; 
    } 
    if (!regexp.test(str)) { 
        return false; 
    } 
    return true; 
} 


/** 
 * 判断是不是有效的手机号码 
 * 格式正确返回true,否则false. 
 */ 
function IsValidateMobile(str) { 
    var pattern = /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/; 
    if (str == '' || str.length == 0) { 
        return false; 
    } 
    if (!pattern.test(str)) { 
        return false; 
    } 
    return true; 
} 


/** 
 * 判断是不是有效的电话号码; 
 * 电话号码格式正确返回true,否则false. 
 */ 
function IsValidatePhone(str) { 
    var pattern = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/; 
    if (str == '' || str.length == 0) { 
        return false; 
    } 
    if (!pattern.test(str)) { 
        return false; 
    } 
    return true; 
} 


/** 
 * 判断是不是有效的邮政编码; 
 * 格式正确返回true,否则false. 
 */ 
function IsValidateZipcode(str) { 
    var pattern = /^[1-9]\d{5}$/; 
    if (str == '' || str.length == 0) { 
        return false; 
    } 
    if (!pattern.test(str)) { 
        return false; 
    } 
    return true; 
} 
