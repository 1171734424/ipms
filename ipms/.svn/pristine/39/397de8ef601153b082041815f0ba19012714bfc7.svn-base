<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>公共入账页面</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css" />
        <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	</head>
	<style>
		.open{
			display:block;
		}
		.close{
			display:none;
		}
		body{
		overflow-y: scroll;
		}
	</style>
	<body>
	<div class="data-container">
		<div class="view-all" id="project_data">
		</div>
	</div>
	<script type="text/javascript">
		var path = "<%=request.getContextPath()%>";
  		$(function(){
  			getallproject();
  		});
  	  	$(document).click(function() {
  	  		$(".ontime").hide();
  	  	})
  		function getallproject(){
			$.ajax({
		         url: path + "/getproject.do",
				 type: "post",
				 data : {},
				 success: function(json) {
				 	console.log(json)
				 	loadroomtype(json);
				 	
				 },
				 error: function(json) {}
			});
  		}
  		
  		function loadroomtype(json){
				 var tabledata = "";
			 	$.each(json, function(indextype){
			 		tabledata = tabledata + "<p class='data-title'id ='" + indextype + "' onclick='showowner(this)'>" + indextype + 
			 								"</p><div class='data-list data-list-hot data-line open'><ul class='clearfix'>";
			 		$.each(json[indextype], function(index){
			 			tabledata = tabledata + "<li onclick='setvalue(this)'><a>" + json[indextype][index] + "</a><span style='display: none'>"+ index +"</span></li>";
			 		})
			 		
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
			 		$("#结算").next().removeClass("open");
			 		$("#结算").next().addClass("close");
			 	}
  		}
  		/**function showowner(e){
  			if($(e).next().hasClass("open")){
  				$(e).next().removeClass("open");
  				$(e).next().addClass("close");
  				$(e).next().next().next().addClass("close");
  				$(e).next().next().next().removeClass("open");
  			} else if($(e).prev().hasClass("open")){
  				$(e).prev().removeClass("open");
  				$(e).prev().addClass("close");
  			}else{
  				$(e).next().addClass("open");
  				$(e).next().removeClass("close");
  				$(e).next().next().next().removeClass("close");
  				$(e).next().next().next().addClass("open");
  			}
  		}**/
  		
  		function showowner(e) {
			if ($(e).attr("id") == '消费') {
	  			$(e).next().addClass("open");
	  			$(e).next().removeClass("close");
	  			$(e).next().next().next().removeClass("open");
	  			$(e).next().next().next().addClass("close");
			} else {
	  			$(e).next().addClass("open");
	  			$(e).next().removeClass("close");
  	  			$(e).prev().removeClass("open");
  				$(e).prev().addClass("close");
 			}
  		}
  		function setvalue(e){
  			window.parent.$("#project").val($(e["childNodes"][0]).html());
  			window.parent.$("#projectid").val($(e["childNodes"][1]).html());
  			window.parent.$("#ontime").css("display","none");
  		}
	</script>
	</body>
</html>
