var html = '';
/*$(function(){
	$("#roomType").bind("click",function(){
	 	JDialog.open("特惠房型", base_path + "/selectRoomType.do",450,350);
	});
})*/

/*function search(){
	alert("dddd"+$("#roomType_CUSTOM_VALUE").val()+"&roomId="+$("#roomId").val()+"&checkUser="+$("#checkUser").val())
		$.ajax({
			url: base_path + "/queryRoomingByCon.do?roomType="+$("#roomType_CUSTOM_VALUE").val()+"&roomId="+$("#roomId").val()+"&checkUser="+$("#checkUser").val(),
			type: "post",
			dataType: "json",
			success: function(json) {
			$(json.result).each(function (i){
				html += "<tr id='"+(this["CHECKUSERID"] == null ? "" : this["CHECKUSERID"]) + "'><td>";
				html += (this["CHECKID"]  == null ? "" : this["CHECKID"] )+"</td><td>";
				//html += (this["BRANCHID"]  == null ? "" : this["BRANCHID"]) +"</td><td>";
				html += (this["ROOMID"]  == null ? "" : this["ROOMID"] )+"</td><td>";
				html += (this["ROOMTYPE"]  == null ? "" : this["ROOMTYPE"] )+"</td><td>";
				html += (this["RPID"]  == null ? "" : this["RPID"])+"</td><td>";
				html += (this["ROOMPRICE"]  == null ? "" : this["ROOMPRICE"])+"</td><td>";
				html += (this["CHECKUSER"]  == null ? "" : this["CHECKUSER"])+"</td><td>";
				html += (this["CHECKINTIME"]  == null ? "" :this["CHECKINTIME"] ) +"</td><td>";
				html += (this["CHECKOUTTIME"]  == null ? "" : this["CHECKOUTTIME"]) +"</td><td>";
				html += (this["DEPOSIT"]  == null ? "" : this["DEPOSIT"]) +"</td><td>";
				html += (this["TTV"]  == null ? "" : this["TTV"])+"</td><td>";
				html += (this["CURRENTCOST"]  == null ? "" : this["CURRENTCOST"])+"</td><td>";
				html += (this["ACCOUNTFEE"]  == null ? "" : this["ACCOUNTFEE"])+"</td><td>";
				html += (this["TOTALFEE"] == null ? "" : this["TOTALFEE"])+"</td><td>";
				html += (this["SWITCHID"]  == null ? "" :this["SWITCHID"]) +"</td><td>";
				html += this["REMARK"]  == null ? "" : this["REMARK"];
				html += "</td></tr>";
			});
			console.log(html)
			$("#tbodyInfo").html("");
			$("#tbodyInfo").html(html);
			html="";
				Pager.renderPager(json.pagination);
			}
		});	
	
}*/

/*$("tr[class='odd']").dblclick(function(){
	alert(this.id)
	var thisId = this.id;
	$.ajax({
		url: base_path + "/queryRoomGuestDetail.do?checkUser="+thisId,
		type: "post",
		dataType: "json",
		success: function(json) {
	
		$(json).each(function (i){
			html += "<tr id='"+this["MEMBERID"]+"'>";
			html += "<td>"+this["MEMBERNAME"]+"</td>";
			html += "<td>"+this["PHOTOS"]+"</td>";
			html += "<td>"+this["LOGINNAME"]+"</td>";
			html += "<td>"+this["MEMBERRANK"]+"</td>";
			html += "<td>"+this["IDCARD"]+"</td>";
			html += "<td>"+this["GENDOR"]+"</td>";
			html += "<td>"+this["BIRTHDAYS"]+"</td>";
			html += "<td>"+this["MOBLIE"]+"</td>";
			html += "<td>"+this["EMAIL"]+"</td>";
			html += "<td>"+this["ADDRESS"]+"</td>";
			html += "<td>"+this["POSTCODE"]+"</td>";
			html += "<td>"+this["OLDSOURCE"]+"</td>";
			html += "<td>"+this["VALIDTIME"]+"</td>";
			html += "<td>"+this["REGISTERTIME"]+"</td>";
			html += "<td>"+this["RECORDTIME"]+"</td>";
			html += "<td>"+this["STATUS"]+"</td>";
			html += "<td>"+this["REMARK"]+"</td>";
			html += "</tr>";
		});
		$("#myTable").html(html);
			//Pager.renderPager(json.pagination);
		}
	});
	
})*/

$("tr[class='odd']").dblclick(function(){
	var thisId = this.id;
	window.parent.JDialog.open("会员资料", base_path +"/queryRoomGuestDetail.do?checkId="+thisId,1200,350);
})

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}


function search(){
//校验input框的值todo
	$("#myForm").submit();

}