var html;
$(function(){
	$(".date").datepicker({ dateFormat: "yy/mm/dd" })
	$("#startTime").css('font-size','0.9em');
	$("#endTime").css('font-size','0.9em');
})

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

function search(){
	//alert("checkId="+$("#checkId").val()+"&status="+$("#status").val()+"&checkUser="+$("#checkUser").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val())
	if($("#startTime").val()==null || $("#startTime").val()=="" || $("#endTime").val()== null ||  $("#endTime").val()== ""){
		showMsg("请输入相应的时间段！");
	}else if(comptime($("#startTime").val(),$("#endTime").val())== 1){
		showMsg("结束时间不能小于开始时间！");
	}else{
		$("#myForm").submit();
	}
}


//时间对比函数，如果a>b返回1，如果a<b返回-1，相等返回0   
function comptime(a,b)   
{   
var dateA = new Date(a);   
var dateB = new Date(b);   
if(isNaN(dateA) || isNaN(dateB)) return null;   
if(dateA > dateB) return 1;   
if(dateA <= dateB) return -1;   
return 0;   
} 

