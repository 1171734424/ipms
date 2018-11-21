<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/script.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	//JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
	//request.setAttribute("checkid",request.getParameter("checkid"));
%>

<!DOCTYPE HTML>
<html>
  <head>
    
    <title>日志</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
<!--	<link rel="stylesheet" type="text/css" href="css/log.css"/>-->
<!--	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>-->
<!--		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>-->
<!--	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />-->
<!--	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />-->
<!--	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />-->
<!--	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/customer.css"/>-->
<!--    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />-->
	
	
  </head>
  
  <body>
	  <form>
	   <div class="log_width">
  			<input id="checkid" name="checkid" type="hidden" value="${checkid}">
			<div class="log_one">
				<section class="box_content_widget fl">
					<div class="content log_table">
						<iframe name="frame" id="frame" class="myTable" frameborder="0" width="100%" height="100%" ></iframe>
					</div>
				</section>
			</div>
	    </div>
		</form>	
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/apartment/apartmentLeftmenu/apartmentrefund/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
		var path="<%=request.getContextPath() %>";
		var checkid = $(window.parent.document.all.checkid).val();
		if(!checkid){
			checkid = '<%=request.getParameter("checkid")%>';
		}
		var type = '<%=request.getParameter("type")%>';
           $(function(){
            //alert('<input id="checkid" name="checkid" type="hidden" value="${checkid}">')
           	$("#frame").attr('src',path+ "/showLogData.do?checkid="+checkid+"&type="+type);
           });

		</script>
  </body>
  
</html>
