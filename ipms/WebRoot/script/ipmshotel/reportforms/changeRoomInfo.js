/*var html;
function search(){
	$.ajax({
		url: base_path + "/changeRoomTableByCon.do?checkId="+$("#checkId").val()+"&status="+$("#status").val()+"&checkUser="+$("#checkUser").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val(),
		type: "post",
		dataType: "json",
		success: function(json) {
		$(json.result).each(function (i){
			html += "<tr id='"+(this["LOGID"] ==null ? "" : this["LOGID"])+"'>";
			//html += "<td>"+(this["LOGID"]==null ? "" :this["LOGID"])+"</td>";
			//html += "<td>"+(this["BRANCHID"]==null ? "" :this["BRANCHID"])+"</td>";
			html += "<td>"+(this["CHECKID"]==null ? "" :this["CHECKID"])+"</td>";
			html += "<td>"+(this["CHECKUSER"]==null ? "" :this["CHECKUSER"])+"</td>";
			html += "<td>"+(this["STATUS"]==null ? "" :this["STATUS"])+"</td>";
			html += "<td>"+(this["RPID"]==null ? "" :this["RPID"])+"</td>";
			html += "<td>"+(this["LASTROOMTYPE"]==null ? "" :this["LASTROOMTYPE"])+"</td>";
			html += "<td>"+(this["LASTROOMID"]==null ? "" :this["LASTROOMID"])+"</td>";
			html += "<td>"+(this["LASTROOMPRICE"]==null ? "" :this["LASTROOMPRICE"])+"</td>";
			html += "<td>"+(this["CURRRPID"]==null ? "" :this["CURRRPID"])+"</td>";
			html += "<td>"+(this["CURRROOMTYPE"]==null ? "" :this["CURRROOMTYPE"])+"</td>";
			html += "<td>"+(this["CURRROOMID"]==null ? "" :this["CURRROOMID"])+"</td>";
			html += "<td>"+(this["CURRROOMPRICE"]==null ? "" :this["CURRROOMPRICE"])+"</td>";
			html += "<td>"+(this["RECORDTIME"]==null ? "" :this["RECORDTIME"])+"</td>";
			//html += "<td>"+(this["RECORDUSER"]==null ? "" :this["RECORDUSER"])+"</td>";
			html += "</tr>";
		});
		$("#tbodyInfo").html("");
		$("#tbodyInfo").html(html);
		html="";
		Pager.renderPager(json.pagination);
		}
	});	
}*/

function search(){
	//alert("checkId="+$("#checkId").val()+"&status="+$("#status").val()+"&checkUser="+$("#checkUser").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val())
	if($("#startTime").val()==null || $("#startTime").val()=="" || $("#endTime").val()== null ||  $("#endTime").val()== ""){
		showMsg("请输入相应的时间段！");
	}else if(comptime($("#startTime").val(),$("#endTime").val())!= -1){
		showMsg("结束时间不能小于开始时间！");
	}else{
		$("#myForm").submit();
	}
}


function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

function comptime(a,b)   
{   
var dateA = new Date(a);   
var dateB = new Date(b);   
if(isNaN(dateA) || isNaN(dateB)) return null;   
if(dateA > dateB) return 1;   
if(dateA <= dateB) return -1;   
return 0;   
} 