<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("themename", request.getAttribute("themename"));
   request.setAttribute("branchname", request.getAttribute("branchname"));
   request.setAttribute("rpkindname", request.getAttribute("rpkindname"));
   request.setAttribute("rpstatus", request.getAttribute("rpstatus"));
%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>房租价申请</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/dialog.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/pmsmanage/roomprice.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
		<style>
        .rpapplystatus_select{
         border: 1px solid #4A5064;
         font-size: 15px;
        }
		</style>
	</head>
	<body>
	    <input id="kindname" name="kindname" type="hidden" value="${rpkindname}"/>
	    <form action="" name="form_rpapply" id="form_rpapply">
	    <div class="rp_right_title">
            <span id="themename" name="themename">${themename}</span>
			<span>|</span>
			<span id="branchname" name="branchname">${branchname}</span>
			<span>|</span>
			<span id="rpkindname" name="rpkindname">${rpkindname}</span>
			<span>|</span>
			<select id="rpstatus" name="rpstatus" class="check rpapplystatus_select" onchange="changestatus()">
		      <c:forEach var="ra" items="${rpstatus}">
			    <option value="${ra.content}">
				   ${ra.paramname}
			     </option>
		      </c:forEach>
		    </select>
		</div>
		<iframe name="roompriceIframe" id="roompriceIframe" frameborder="0" width="100%" height="100%" src="rpBasicApply.do" style = "height:597px;"></iframe>					
		</form>
		<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
	 	var base_path = '<%=request.getContextPath()%>';
	    </script>
	    <script>
	     $(document).ready(function(){
	     var kindname = $("#kindname").val();
	     if(kindname=="时租"){
	        var hourprice = "2";
	        $("#roompriceIframe").attr("src","rpBasicApply.do?hourprice="+hourprice);
	        }
	     })	    
	   /*改变状态事件 */ 
	   function changestatus(){
	      if($("#rpstatus").val()=="2"){
	          var status = $("#rpstatus").val();
	          var kindname = $("#kindname").val();
	         if(kindname=="时租"){
	              var hourprice = "2";
	              $("#roompriceIframe").attr("src","rpBasicApply.do?status="+status+"&hourprice="+hourprice);
	           }else{
	              $("#roompriceIframe").attr("src","rpBasicApply.do?status="+status);
	           }
	      }else{
	           location.reload(true)
	      }
	   }
	       
	 </script>
	</body>
</html>
