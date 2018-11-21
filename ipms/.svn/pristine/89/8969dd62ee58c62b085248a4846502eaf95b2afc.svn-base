var html;
$(function(){
	$(".date").datepicker({ dateFormat: "yy/mm/dd " });
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
			url: base_path + "/accountInTotalByCon.do?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val(),
			type: "post",
			dataType: "json",
			success: function(json) {
			$(json.result).each(function (i){
				html += "<tr id='"+this["RECORDID"] == null ? "": this["RECORDID"] +"'>";
				html += "<td>"+this["RECORDTYPE"] == null ? "": this["RECORDTYPE"]+"</td>";
				html += "<td>"+this["COUNTNUM"] == null ? "": this["COUNTNUM"]+"</td>";
				html += "<td>"+this["SUMCOST"] == null ? "": this["SUMCOST"]+"</td>";
				html += "<td>"+this["SUMPAY"] == null ? "": this["SUMPAY"]+"</td>";
				html += "</tr>";
			});
			$(json.accountRecordSum).each(function (i){
				html += "<tr>";
				html += "<td>小计</td>";
				html += "<td>"+this["COUNTNUM"] == null ? "" : this["COUNTNUM"]+"</td>";
				html += "<td>"+this["SUMCOST"] == null ? "" : this["SUMCOST"]+"</td>";
				html += "<td>"+this["SUMPAY"] == null ? "" : this["SUMPAY"]+"</td>";
				html += "</tr>";
			});
			$("#tbodyInfo").html("");
			$("#tbodyInfo").html(html);
			html="";
			Pager.renderPager(json.pagination);
			}
		});
	}
	
	
		
}*/

function search(){
	//alert("checkId="+$("#checkId").val()+"&status="+$("#status").val()+"&checkUser="+$("#checkUser").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val())
	var msg;
	if(!$("#startTime").val() || !$("#endTime").val()){
		msg = "请输入相应的时间段！";
	}else if(new Date($("#startTime").val()) > new Date($("#endTime").val())){
		msg =  "结束时间不能小于开始时间！";
	}
	
	if(msg) {
		showMsg(msg);
		return false;
	}
	
	$("#myForm").submit();
}
