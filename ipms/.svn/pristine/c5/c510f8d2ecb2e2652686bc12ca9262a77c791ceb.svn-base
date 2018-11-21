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

/*function search(){
	if(!$("#startTime").val()|| !$("#endTime").val()){
		showMsg("请输入相应的时间段！");
	}else{
		$.ajax({
			url: base_path + "/goodsSaleDetailByCon.do?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&recordUser="+$("#recordUser").val(),
			type: "post",
			dataType: "json",
			success: function(json) {
			$(json.result).each(function (i){
				html += "<tr id='"+this["LOGID"] == null ? "": this["LOGID"]+"'>";
				html += "<td>"+this["LOGID"] == null ? "":this["LOGID"] +"</td>";
				html += "<td>"+this["DEBTS"] == null ? "":this["DEBTS"]+"</td>";
				html += "<td>"+this["CHECKID"] == null ? "":this["CHECKID"]+"</td>";
				html += "<td>"+this["ROOMID"] == null ? "":this["ROOMID"]+"</td>";
				html += "<td>"+this["GOODID"] == null ? "":this["GOODID"]+"</td>";
				html += "<td>"+this["GOODNAME"] == null ? "":this["GOODNAME"]+"</td>";
				html += "<td>"+this["CATEGORYID"] == null ? "":this["CATEGORYID"] +"</td>";
				html += "<td>"+this["SPECIFICATIONS"]== null ? "":this["SPECIFICATIONS"]+"</td>";
				html += "<td>"+this["UNIT"] == null ? "":this["UNIT"] +"</td>";
				html += "<td>"+this["SALETYPE"] == null ? "":this["SALETYPE"]+"</td>";
				html += "<td>"+this["PRICE"] == null ? "":this["PRICE"]+"</td>";
				html += "<td>"+this["AMOUNT"] == null ? "":this["AMOUNT"]+"</td>";
				html += "<td>"+this["PAYTYPE"] == null ? "":this["PAYTYPE"]+"</td>";
				html += "<td>"+this["RECORDTIME"] == null ? "":this["RECORDTIME"]+"</td>";
				html += "<td>"+this["RECORDUSER"] == null ? "":this["RECORDUSER"]+"</td>";
				html += "<td>"+this["REMARK"] == null ? "": this["REMARK"]+"</td>";
				html += "</tr>";
			});
			$("#tbodyInfo").html("");
			$("#tbodyInfo").html(html);
			html="";
			Pager.renderPager(json.pagination);
			}
		});
	}
	
	
		
}
*/
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