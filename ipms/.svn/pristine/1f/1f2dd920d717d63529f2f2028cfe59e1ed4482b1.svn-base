<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("submitperson", request.getAttribute("submitperson"));  
%>

<!DOCTYPE HTML>
<html>
	<head>
	    <title>选择确认人</title>
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
		<script src="<%=request.getContextPath()%>/script/ipms/js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>	
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
	      var base_path = "<%=request.getContextPath()%>";
		</script>
   </head>
	<body class="cash-body">
		<div class="main_wrapper">
			<span class="span_gdsrs">请选择交接确认人：</span>
			<div class="main_content">
				<div id="myrt">
					<ul id="mylabel">
						<c:forEach var="sp" items="${submitperson}" varStatus="aaa">
						    <li class="live">
								<span class="staffname">${sp.staffname}</span>
								<span class="span_gdsrs_hide" style="display:none">${sp.staffid}</span>   
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
		<script src="script/ipms/js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script>
			
			var staffname = "";
			var staffid= ""
			var lis = Array.prototype.slice.call(document.getElementById('myrt').getElementsByTagName('li'));
	        var len = lis.length;
			$(function(){		 
	               $("#myrt ul li").dblclick(function(){
	                    var activeLen = document.querySelectorAll('.active').length;
			        if (this.classList.contains('active')) {
			            this.classList.remove('active')
			        } else {
			            if (!activeLen) {
			                this.classList.add('active');
			                 staffname = $(this).find("span:eq(0)").html();
	                         staffid = $(this).find("span:eq(1)").html();
	                         window.parent.$("#submitstaffname").val(staffname);
				             window.parent.$("#submitstaff").val(staffid);
				             window.parent.JDialog.close();
			            }else{
			                lis.forEach(function(item){
			                    item.classList.remove('active')
			                })
			                this.classList.add('active')
			                 staffname = $(this).find("span:eq(0)").html();
	                         staffid = $(this).find("span:eq(1)").html();
	                         window.parent.$("#submitstaffname").val(staffname);
				             window.parent.$("#submitstaff").val(staffid);
				             window.parent.JDialog.close();
			            }
	               	}
	           	})
	         })
			  
		 </script>
	</body>
</html>
