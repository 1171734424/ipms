 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("shiftcashin", request.getAttribute("shiftcashin"));
   request.setAttribute("shiftcashout", request.getAttribute("shiftcashout"));
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>投缴页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/> 
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body class="shiftpay_body">
     <div id="" class="">
             <span class="close_span_sp close_cash">
				<i class="imooc imooc_cash" style="color:#3B3E40;" onclick=window.parent.JDialog.close();>&#xe900;</i>
			</span>
		       <input type="hidden" class="pettypaydata" id="shiftcashin" value="${shiftcashin}"/>
		       <input type="hidden" class="pettypaydata" id="shiftcashout" value="${shiftcashout}"/>
		       <table class="shiftpay_table">
			     <col width="31%"/>
			     <col width="69%"/>
			    <tr><td align="right"><span>应缴金额</span></td><td><input class="pettypaydata" id="totalprice" value="0" disable="true" disabled="true"/></td></tr>
			    <tr><td align="right"><span>本次缴纳</span></td><td><input class="pettypayinput" id="currpay"  onkeyup="num(this)" /></td></tr>
			    <tr><td align="right"><span>缴纳凭证</span></td><td><input class="pettypayinput" id="voucherpay"  onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="50"/></td></tr>
			    </table>			
           <div class="sbotton_pettyshift">
             <input id="nowpay" name="" type="button" class="submitbotton_gdspage clean enter" style="float:right;" value="投缴" onclick="pay()"/>
             <input id="" name="" type="button" class="submitbotton_gdspage clean" style="float:right;" value="重置" onclick="location.reload(true)"/>
           </div>
        </div>
        <script>
	$(document).ready(function() {
		  $("#voucherpay").focus(); 
		   var shiftcashin = $("#shiftcashin").val(); 
		   var shiftcashout = $("#shiftcashout").val(); 
		   var shoupay =  parseFloat(shiftcashin)+parseFloat(shiftcashout); 
		   $("#totalprice").val(shoupay.toFixed(2)) ;
		    $("#currpay").val(shoupay.toFixed(2)) ;
     });
	</script>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/pettycash/shiftcash.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
</html>
