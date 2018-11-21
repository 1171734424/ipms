<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("gdsroomId", request.getAttribute("gdsroomId"));  
%>

<!DOCTYPE HTML>
<html>
	<head>
	    <title>房号选择</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomstate.css" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>	
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<script>
	      var base_path = "<%=request.getContextPath()%>";
		</script>
   </head>
	<body>
		<div class="main_wrapper" style="overflow: auto;">
			<span class="span_gdsrs">请选择挂账房间：</span>
			<input type="hidden" id="allCost" name="allCost" value="${allCost}" />
			<div class="main_content">
				<div id="myrt">
					<ul id="mylabel">
						<c:forEach var="rs" items="${gdsroomId}" varStatus="aaa">
						    <li class="live">
								<span>${rs.roomtypename}</span>
								<c:if test="${theme == 3}">	
								<span class="roomid" style="display:none">${rs.roomid}</span>
								<span class="houseid">${rs.houseid}</span>
								</c:if>
								<c:if test="${theme == 1}">	
								<span class="roomid" >${rs.roomid}</span>
								</c:if>
								<span style="display:none">${rs.checkusername}</span>
								<span class="span_gdsrs_hide" style="display:none">${rs.checkid}</span>   
							</li>  
						</c:forEach>
					</ul>
				</div>
			</div>
			<!--<div class="gdsfooter_content">
			
				<input type="button" class="gdsroomselectbotton button_margin confirm" value="取消" onclick="window.parent.JDialog.close()"/>
				<input type="button" class="gdsroomselectbotton  button_margin cancel" value="确定" onclick="gdsrmselect()"/>
			</div>
		-->
		</div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
			var base_path ="<%=request.getContextPath()%>";
			$(".footer_content ul .footer_li").on("click",function(){
				$(this).addClass("active");
				$(this).siblings().removeClass("active");
				
			});
			
			 //$(".main_wrapper ul li").on("click",function(){
	 	     //$(this).addClass("active");
	 	     //$(this).removeClass("active");
	         // })	
			function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
			
			var roomid = "";
			var houseid = "";
			var checkid= "";
			var lis = Array.prototype.slice.call(document.getElementById('myrt').getElementsByTagName('li'));
	        var len = lis.length;
			
			$(function() {
				$("#myrt ul li").dblclick(function() {
					var activeLen = document.querySelectorAll('.active').length;
					if (this.classList.contains('active')) {
						this.classList.remove('active')
					} else {
						if (!activeLen) {
							this.classList.add('active');
							roomid = $(this).find(".roomid").html();//mingcheng
							houseid = $(this).find(".houseid").html();
							if(houseid != "" && houseid != undefined){
								roomid = houseid;
							}
							checkid = $(this).find("span:last").html();
							window.parent.$("#gdsroomid").val(roomid);
							window.parent.$("#gdscheckid").val(checkid);
							window.parent.$("#roomidSpecial").val($(this).find("span:eq(1)").html())
							window.parent.JDialog.close();
						} else {
							lis.forEach(function(item) {
								item.classList.remove('active')
							})
							this.classList.add('active')
							roomid = $(this).find("span:eq(0)").html();
							checkid = $(this).find("span:eq(3)").html();
							window.parent.$("#gdsroomid").val(roomid);
							window.parent.$("#gdscheckid").val(checkid);
							window.parent.$("#roomidSpecial").val($(this).find("span:eq(1)").html())
							window.parent.JDialog.close();
						}
						
						/*$.ajax({
							url:base_path + '/allOfCostIsEnough.do',
							type:"post",
							data : { roomid : roomid ,
									checkid : checkid,
									allCost : $("#allCost").val()}, 
							success : function(json){
								if(json.result == 1){
									window.parent.$("#gdsroomid").val(roomid);
									window.parent.$("#gdscheckid").val(checkid);
									window.parent.JDialog.close();
								}else{
									window.parent.showMsg("押金金额不够，不可挂账！");
									window.setTimeout("window.parent.location.reload(true)", 1000);
								}
							},
							error:function(json){
								window.parent.showMsg("操作异常！"+json);
								window.setTimeout("window.parent.location.reload(true)", 1000);
							},
						})*/

						//roomid = $(this).find("span:eq(0)").html();
						//checkid = $(this).find("span:eq(3)").html();
					}
				})
			})
			
			function gdsrmselect() {
				if (roomid == "") {
					showMsg("请选择一个房间挂账！")
				} else {
					window.parent.$("#gdsroomid").val(roomid);
					window.parent.$("#gdscheckid").val(checkid);
					window.parent.JDialog.close();
				}
			}
</script>
	</body>
</html>
