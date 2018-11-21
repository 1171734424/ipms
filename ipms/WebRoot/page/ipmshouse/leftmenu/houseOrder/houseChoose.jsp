<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<%
request.setAttribute("basePath", request.getContextPath());
request.setAttribute("roomtype", request.getAttribute("roomtype"));
request.setAttribute("theme", request.getAttribute("theme"));
request.setAttribute("roomprice", request.getAttribute("roomprice"));
request.setAttribute("guarantee", request.getAttribute("guarantee"));
request.setAttribute("rpsetup", request.getAttribute("rpsetup"));
request.setAttribute("outlineroomprice", request.getAttribute("outlineroomprice"));
request.setAttribute("discount", request.getAttribute("discount"));
%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>预订页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/common/tipInfo.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/select/jquery.mCustomScrollbar.min.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/select/select.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
	</head>
<body>
	<div>
		<c:forEach items="${result}" var="rt">
			<table>
			
			<tr onclick="chooseHouse(this)">
				<td>${rt['HOUSENAME']}</td>
				<td>${rt['HOUSENO']}</td>
				<td style="display:none" id="houseId">${rt['HOUSEID']}</td>
			</tr>
				
			</table>
		</c:forEach>
	</div>
<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
<script src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.select.js"></script>
<script src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.mCustomScrollbar.concat.min.js"></script> 
<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/order/order.js"></script>
<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
<script>
   var base_path = "<%=request.getContextPath()%>";
   var startTime = '${param.startTime}';
   var endTime = '${param.endTime}';
</script>
<script>
	function chooseHouse(e){
		var houseId = $(e).children("#houseId").text();
		var houseName = $(e).children().eq(0).text();
		$.ajax({
	        url: base_path + "/houseChoose.do",
			 type: "post",
			 data : {"houseId":houseId,
					 "startTime":startTime,
					 "endTime":endTime},
			 success: function(json) {
					if(json.result ==="0"){
						showMsg(json.message);
						return;
					}
					if(json.result ==="1"){//民宿可订
						window.parent.document.getElementById("houseName").innerHtml(houseName);
						window.parent.document.getElementById("price").innerHtml(json.message);
						window.JDialog.close();
					}
			 },
			 error: function(json) {
				 showMsg(json.message);
			 }
		});
	}
</script>
	</body>
</html>
