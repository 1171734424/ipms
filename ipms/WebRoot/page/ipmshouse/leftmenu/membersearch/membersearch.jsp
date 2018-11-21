 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>会员查询</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body>
  <div class="member_padding">
  	<h3>会员查询</h3>
  	  <span class="close_span">
	  	<i class="imooc imooc_membersearch" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i>
	  </span>
	  <div class="table_member">
	  	<label class="label_name label_sp">会员查询</label>
	  	<input id="msoidcard" name="msoidcard" class="input_ms spec" type="text" placeholder="手机号或身份证" maxlength="18"/>
	  	<input type="button" class="button_margin submitbotton_membersearch one_button" onclick="msidsearch()" value="查询"/>
	  	<input type="button" class="button_margin submitbotton_membersearch one_button" onclick="readIdcard()" value="读取证件"/>
	  </div>
     <iframe name="" id="msIframe" frameborder="0" width="100%" height="100%" src="memberSearchdata.do" style="height: 81.5%"></iframe>
 </div>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/membersearch.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
</html>
