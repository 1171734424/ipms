<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/taglib.jsp"%>
<%request.setAttribute("roomtype", request.getAttribute("roomtype")); %>
<!DOCTYPE>
<html>
  <head>
    
    <title>增加关联房</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/select/jquery.mCustomScrollbar.min.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/select/select.css" />
	<style type="text/css">
	.content{
		height: 364px;
	    overflow: auto;
	}
	
	</style>
  </head>    
  
  <body>
  		<div class="connec_room">
			<form id="room_search" action="" method="post">
				<div class="margintop">
					<ul class="clearfix">
						<li><label class="label_name">房单号</label><input name="checkid" type="text" value="" class="room_search"></li>
						<li><label class="label_name">房型</label>
							<select class="room_search select_search mySelect"  name="roomtype" >
				                <option id="choosert" value="">请选择房间类型</option>  
                                <c:forEach var="rt" items="${roomtype}">  
                                <option value="${rt.roomname}"> ${rt.roomname} </option>  
                                </c:forEach>
							</select>
						</li>
						<li><label class="label_name">房号</label> <input name="roomid" type="text" value="" class="room_search"></li>
						<li><label class="label_name">入住人</label><input name="checkuser" type="text" value="" class="room_search"></li>
						<li><label class="label_name">预订人</label><input name="orderuser" type="text" value="" class="room_search"></li>
						<li class="search_li"><input type="button" value="查询" class="button_margin" onclick="multiquery()"></li>
						<li><input type="button" value="确定" class="button_margin" onclick="setroommap()"></li>
					</ul>
				</div>
				<input name="hostcheckid" id="hostcheckid" type="hidden"/>
				<input name="status" id="status" type="hidden" value="1">
			</form>
		</div>
		<div class="control_margin">
	  		<section class="box-content-section box_margin  fl">
				<section class="box_content_widget fl">
					<div class="content">
						<table id="roomCheckGrid" class="myTable" border="0" width="100">
							<thead>
								<tr>
									<th class="header">选择</th>
									<th class="header">房单号</th>
									<th class="header">房型</th>
									<th class="header">房号</th>
									<th class="header">预订人</th>
									<th class="header">预订人手机</th>
									<th class="header">入住人</th>
								</tr>
							</thead>
							<tbody id="check_data">
								
							</tbody>
							
						</table>
					</div>
				</section>
			</section>
		</div>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.select.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.mCustomScrollbar.concat.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
		var path = "<%=request.getContextPath()%>";
		var hostcheckid = "<%=request.getParameter("checkid")%>";
		$(function(){
			$(".mySelect").select({
				   width: "163px"
			});
			getRoomList();
			$("#hostcheckid").val(hostcheckid);
		});
		strcheckid = "'301','305'";
		
		function getRoomList(){
			$.ajax({
		         url: path + "/selectroommapping.do",
				 type: "post",
				 data : {
				 		status : "1",
				 		hostcheckid : hostcheckid
				 	},
				 success: function(json) {
				 	loadcheckdata(json);
				 },
				 error: function(json) {}
			});
		}
		
		function multiquery(){
			$.ajax({
		         url: path + "/selectroommapping.do",
				 type: "post",
				 data : $("#room_search").serialize(),
				 success: function(json) {
				 	loadcheckdata(json)
				 },
				 error: function(json) {}
			});
		}
		
		function loadcheckdata(json){
			 var tabledata;
		 	$.each(json, function(index){
		 		tabledata = tabledata + "<tr class='odd' onclick='aa(this)' >" +
		 		"<td><input type='checkbox'></td>" +
		 		"<td>" + json[index]["CHECKID"] + "</td>" +
		 		"<td>" + json[index]["ROOMNAME"] + "</td>" +
		 		"<td>" + json[index]["ROOMID"] + "</td>" +
		 		"<td>" + json[index]["ORDERUSERNAME"] + "</td>" +
		 		"<td>" + json[index]["MPHONE"] + "</td>" +
		 		"<td>" + json[index]["FIRSTCHECKUSERNAME"] + "</td>" +
		 		"</tr>";
		 	});
		 	if(json.length == 0){
		 		$("#check_data").html("");
		 	}else{
		 		$("#check_data").html(tabledata);			 	
		 	}
		 	$("#roomCheckGrid :checkbox").click(function(e){
				e.stopPropagation();
			});
		}

		$(".highsearch").on("click", function() {
			$(".high_header").css("display", "block");
		})
		$(".imooc").on("click", function() {
			$(".high_header").fadeOut(300);
		})
		var timer = null;
		function aa(e) {
			checkedbytruns(e);
			//$(e).find(":checkbox").attr("checked", !$(e).find(":checkbox").is(":checked"));
		}
		
		function setroommap(){
			$("input[type='checkbox']").is(':checked');
			var arr = $("input[type='checkbox']");
			var strcheckid = "";
			$.each(arr, function(index){
				if(arr[index]["checked"]){
					strcheckid = strcheckid + $(arr[index]["parentNode"]["parentNode"]["childNodes"][1]).html() + ",";
				}
			});
			strcheckid = strcheckid.substring(0,strcheckid.length-1);
			if(strcheckid == ''){
				showMsg("查询无果/房间未勾选!");
				return false;
			}
			$.ajax({
		         url: path + "/setroommap.do",
				 type: "post",
				 data : {
				 	strcheckid : strcheckid,
				 	hostcheckid : hostcheckid
				 },
				 success: function(json) {
				 	if(json.result == 1){
				 		//loadroommapping();
				 		window.parent.document.getElementById("customer").innerHTML='<iframe src="' + path +'/page/ipmshotel/room_statistics/customer.jsp?checkid=' + hostcheckid +'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
				 		window.parent.JDialog.close();
				 		
				 	//	window.parent.location.reload(true);
				 	}else if(json.result == 0){
				 		showMsg(json.message)
				 	}
				 	
				 },
				 error: function(json) {}
			});
		}
	</script>
  </body>
</html>
