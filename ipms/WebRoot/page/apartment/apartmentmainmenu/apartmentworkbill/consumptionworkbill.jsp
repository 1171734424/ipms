<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>冲减</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
  </head>
  <style>
  	#jd_iframe{
  		width:700px !important;
  	}
  	#offset{
	    height: 64%;
	    width: 87%;
	    margin-left: 33px;
  	}
  </style>
  <body>
   <!--入账开始 -->
		<section class="detail_six">
			<div class="high_header">
				<!--  <i class="imooc detail_imooc" style="color:#3B3E40;"></i>	-->
					<div class="margintop">
						<form id="bill_date">
							<ul class="clearfix">
						      <li><label class="label_name">冲减原因：</label> <textarea id="offset" name="offset" col="100" rols="10"></textarea></li>
						      <li onclick="submitoffset()"><span role="button" class="button confirm" >确定</span></li>
			       		   </ul>
			       		   <input type="hidden" id="projectid" name="projectid">
		       		   </form>
	    		 </div>
			</div>
		</section>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/apartment/apartmentLeftmenu/apartmentrefund/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
		var strdetailid = "<%=request.getParameter("strdetailid")%>";
 			function submitoffset(){
 			//console.log()
 			//$(window.parent.parent.document.all[160].contentDocument.logFrame.document.forms[0]).submit();
  				var offset = $("#offset").val();
  				if(!checkLength(offset, "备注：", 100)){
					return false;
				}
  				$.ajax({
  			         url: path + "/consumptionworkbill.do",
  					 type: "post",
					 data : {
					 	strdetailid : strdetailid,
					 	offset : offset
					 },
  					 success: function(json) {
	  					 if (json.result == 1) {
	 						 $(window.parent.parent.document).find(".tab_two").click();
	 						 window.parent.JDialog.close();
	 					 	 showMsg(json.message);
	 					 	 setTimeout("refreshpage()", 2000);
	 						 //$(window.parent.parent.document).find(".tab_two").click();
	 						 //$(window.parent.parent.parent.document.all[160].contentDocument.logFrame.document.forms[0]).submit();
	 						 //window.parent.JDialog.close();
	  					 } else {
	  						showMsg(json.message);
	  						return false;
			  			 }
  					 },
  					 error: function(json) {}
  				});
  			}
 			
 			function refreshpage(){
 				 $(window.parent.parent.document).find(".tab_two").click();
 				 $(window.parent.parent.parent.document.all[160].contentDocument.logFrame.document.forms[0]).submit();
 				 window.parent.JDialog.close();
 			}
		</script>
  </body>
</html>
