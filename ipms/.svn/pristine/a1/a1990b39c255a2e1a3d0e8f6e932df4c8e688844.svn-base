<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<%
	request.setAttribute("basePath", request.getContextPath());
%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>打卡</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/common/tipInfo.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/clockIn.css" />
	</head>
	<body>
	<div class="walk_margin" style="padding:0;background:#fff;border:none;">
		<div class="walk_form" style="height:300px;">
				<form action="" method="post" class="order_form order_form_later" >
					<section class="detail_one">
					<ul class="clearfix">
						<li>
							<label class="orlabel_name">状态：</label>
							<select id="status" name="status" class="check order_input" disabled="disabled">
								<option value="1" <c:if test="${workstatus eq 0 }">selected="selected"</c:if>>上班</option>
								<option value="0" <c:if test="${workstatus eq 1 }">selected="selected"</c:if>>下班</option>
							 </select>
						</li>
					</ul>
				
					</section>
					<section class="detail_four order_four" style="margin-top:7%;">
						<span id="order-button" class="button_margin order_order" onclick="submit()">确认</span>
					</section>
				</form>
			</div>
		</div>
		
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script>
		      var base_path = "<%=request.getContextPath()%>";
		      function submit(){
		    	  var status = document.getElementById("status").value;
		    	  $.ajax({
		  			url: base_path+"/submitClockIn.do",
		  			type: "post",
		  			dataType: "json",
		  			data:{
		  				status:status,
		  				flag:"0"
		  			},
		  			success: function(json) {
		  				var result = json.result;
		  				if(result == 2){
		  					showMsg(json.message,function(){
		  						submitAgain(status);
		  					});
		  				}else if(result == 1){
		  					showMsg(json.message);
		  					window.setTimeout("window.parent.JDialog.close()", 1800);
		  					
		  				 }
		  			},
		  			error: function(json) {
		  				showMsg("操作失败！");
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
		      
		      function submitAgain(e){
		    	  $.ajax({
			  			url: base_path+"/submitClockIn.do",
			  			type: "post",
			  			dataType: "json",
			  			data:{
			  				status:e,
			  				flag:"1"
			  			},
			  			success: function(json) {
			  				var result = json.result;
			  				 if(result == 1){
			  					showMsg(json.message);
			  					window.setTimeout("window.parent.JDialog.close()", 1800);
			  				 }
			  			},
			  			error: function(json) {
			  				showMsg("操作失败！");
			  			}
			  		});
		      }
		</script>
	</body>
</html>
