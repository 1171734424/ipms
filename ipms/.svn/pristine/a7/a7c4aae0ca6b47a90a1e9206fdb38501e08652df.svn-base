<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%String a = (String)request.getParameter("checkid"); %>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>新增提示记录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
  </head>
  
  <body>
		<section class="detail_six add_pro">
			<div class="high_header">
					<div class="margintop">
						<form id="bill_date">
							<ul class="clearfix">
						      <li><label class="label_name">房号</label><input id="roomid" name="roomid" style="width: 278px;"  class="text_number" readonly></li>
						      <li><label class="label_name">内容</label><textarea id="prompt_content" name="content"></textarea></li>
						      <li onclick="submitPromptRecord()"><span role="button" style="margin-right: 6px;"class="button confirm" >确定</span></li>
			       		   </ul>
			       		   <input type="hidden" name="checkId" id="checkId" value="<%=a %>">
		       		   </form>
	    		 </div>
			</div>
		</section>
			<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
			<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
			<script type="text/javascript">
			var path = "<%=request.getContextPath() %>";
				$(document).ready(function(){
					$("#prompt_content").focus();
					$("#roomid").val($(window.parent.document.all.roomid).text());
				})
				function submitPromptRecord(){
					if (isNull($("#prompt_content"))){
						return false;
					}
					console.log(window.parent.parent)
					$.ajax({
				         url: path + "/addPromptRecord.do",
						 type: "post",
						 data : $("#bill_date").serialize(),
						 success: function(json) {
						 	// $(".hot").html(json[0]["NUM"]);
							 showMsg(json.message);
							 setTimeout("refresh()", 1000);	
						 },
						 error: function(json) {
							
						}
					});
				}
			function refresh(){
			    $(window.parent.document).find(".tab_four").click();
				window.parent.JDialog.close();
			}
			
				/*提示*/
			function changefour(){
				//document.getElementById("c").innerHTML='<iframe src="' + base_path +'/page/ipmspage/order/prompt.jsp?checkid='+checkid+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
				document.getElementById("customer").innerHTML='<iframe src="' + base_path +'/loadOrderPromptData.do?checkid='+checkid+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
			</script>
  </body>
</html>
