<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>冲减</title>
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/apartment/apartmentmainmenu/apartmentcontract/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
		var strbillid = "<%=request.getParameter("strbillid")%>";
		var checkid = "<%=request.getParameter("checkid")%>";
			function submitoffset(){
				console.log(${checkid});
				var offset = $("#offset").val();
				$.ajax({
			         url: path + "/apartmentConsumption.do",
					 type: "post",
					 data : {
					 	strbillid : strbillid,
					 	offset : offset,
					 	checkid : checkid},
					 success: function(json) {
					 	if(json.result == 0){
							showMsg(json.message);
							setTimeout("window.parent.JDialog.close()", 1000);			 	
					 	}else{
					 		parent.location.reload();
						 	window.parent.JDialog.close();
					 	}
					 },
					 error: function(json) {}
				});
			}
		</script>
  </body>
</html>
