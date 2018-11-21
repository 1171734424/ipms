<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>保洁申请选择房间页面</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
        <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	</head>
	<style>
	.clean-data{
	    height: 100%;
	    min-height: 201px;
	    overflow-y: scroll;
	}
	.clean-data .view-all{
		height:100%;
		padding: 10px;
	}
	.clean-box .view-all .roomid_contrate{
	   width: 49px;
	   font-weight:bold;
	   cursor:pointer;
       color: #5A5D9C;
	}
	/*设置滚动条样式*/
	::-webkit-scrollbar  
	{  
	    width: 5px;  
	    height: 5px;  
	    background-color: transparent;  
	}  
	  
	/*定义滚动条轨道 内阴影+圆角*/  
	::-webkit-scrollbar-track  
	{  
	    border-radius: 10px;  
	    background-color: transparent;  
	}  
	  
	/*定义滑块 内阴影+圆角*/  
	::-webkit-scrollbar-thumb  
	{  
	    border-radius: 10px;  
	    background-color: transparent;
	} 
	.roomid_contrate{
	 float : left;
	}
	</style>

	<body style="overflow: scroll;">
	<input type="hidden" id="timearea" name="timearea" value="<%=request.getParameter("timearea")%>"/>
    <input type="hidden" id="inputdate" name="inputdate" value="<%=request.getParameter("inputdate")%>"/>
	<div class="clean-data clean-box">
		<div class="view-all" id="project_data">
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
		var path = "<%=request.getContextPath()%>";
  		$(function(){
  			getallroom();
  		});
  		
  		function getallroom(){
			$.ajax({
		         url: path + "/getallroom.do",
				 type: "post",
				 data : {},
				 success: function(json) {
				 	loadallroom(json);
				 },
				 error: function(json) {}
			});
  		}
  		
  		function loadallroom(json){
				 var tabledata = "";
			 	$.each(json, function(indextype){
			 		tabledata = tabledata + "<p class='data-title'>" + indextype + ":"+
			 								"</p><div class='data-list data-list-hot'><ul class='clearfix'>";
			 		$.each(json[indextype], function(index){
			 			tabledata = tabledata + "<li class='roomid_contrate' onclick='checkoneroomid(this)'><a>" + json[indextype][index]["ROOMID"] + "</a><span style='display: none'>"+ json[indextype][index]["ROOMTYPE"] +"</span></li>";
			 		});
			 		
			 		tabledata = tabledata + "</ul></div>";
			 		/*tabledata = tabledata + "<tr class='odd' '>" +
			 		"<td>" + json[index]["BILLID"] + "</td>" +
			 		"<td>" + json[index]["PROJECTID"] + "</td>" +
			 		"<td>" + json[index]["PROJECTNAME"] + "</td>" +
			 		"<td>" + converttosomething(json[index]["COST"], null, '') + "</td>" +
			 		"<td>" + converttosomething(json[index]["PAY"], null, '') +"</td>" +
			 		"<td>" + json[index]["RECORDUSER"] + "</td>" +
			 		"<td>" + dealDate(json[index]["RECORDTIME"]) + "</td>" +
			 		"<td>" + json[index]["PAYMENT"] + "</td>"+
			 		"<td>" + json[index]["REMARK"] + "</td>" +
			 		"</tr>";*/
			 	});
			 	if(json.length == 0){
			 		$("#project_data").html("");
			 	}else{
			 		$("#project_data").html(tabledata);			 	
			 	}
  		}
  		function checkoneroomid(e){
  		var inputdate = $("#inputdate").val();
		var timearea = $("#timearea").val();
  		
  		var select_roomid  = $(e["childNodes"][0]).html();
  		var select_roomtype = $(e["childNodes"][1]).html();
  		$.ajax({
  		 url: path + "/getApplicationDate2.do",
				 type: "post",
				 data : {
				 "roomid" :  select_roomid,
				 "roomtype" : select_roomtype,
				 "inputdate":inputdate,
				 "timearea":timearea
				 },
				 success: function(json) {
				 if(json.result == 0){
				 window.parent.showMsg(json.message);
				 }else{
				 window.parent.$("#roomid").val($(e["childNodes"][0]).html());
  			     window.parent.$("#roomtype").val($(e["childNodes"][1]).html());
				 window.parent.$("#contrartid").val(json.contrartid);
 				 window.parent.$("#reservedperson").val(json.membername);
 				 window.parent.$("#mobile").val(json.mobile);
 				 window.parent.$("#memberId").val(json.memberId);
 				 window.parent.$("#allroomid").css("display","none");
				 }},
				 error: function(json) { 
				 showMsg("无该房间！");
				 window.setTimeout("window.parent.$('#allroomid').css('display','none')",1800);
				 
				  }
  		});
  		}
 function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}
	</script>
	</body>
</html>
