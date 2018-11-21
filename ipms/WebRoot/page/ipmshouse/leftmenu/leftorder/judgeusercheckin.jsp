<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
request.setAttribute("memberdata", request.getAttribute("memberdata"));
request.setAttribute("roomprice", request.getAttribute("roomprice")); 
request.setAttribute("guarantee", request.getAttribute("guarantee"));
request.setAttribute("rpId", request.getAttribute("rpId"));  
request.setAttribute("discount", request.getAttribute("discount"));  
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>预订人确认</title>
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
      var rprank = "${rpId}";
	</script>
  </head>
  <body class="whitebg_judge">
	<input id="rp"	name= "rp"	value="${roomprice}" type="hidden"/>
	<input id="discount"	name= "discount"	value="${discount}" type="hidden"/>
    <div class="">
			<form action="" method="post">
				<section class="detail_one section_judge">
					<ul class="clearfix">
					 <c:forEach items="${memberdata}" var="md" varStatus="aaa">
					    <li><label class="label_name"></label><input id="memberid" name="memberid" type="text" value="${md.memberid}" class="check" disabled></li>
						<li><label class="label_name"></label><input id="membername" name="membername" type="hidden" value="${md.membername}" class="check" disabled></li>
						<li><label class="label_name"></label> <input id="memberrank" name="memberrank" type="hidden" value="${md.memberrank}" class="check" disabled></li>
						<li><label class="label_name"></label> <input id="rpid" name="rpid" type="hidden" value="${md.rpid}" class="check" disabled></li>
						<li><label class="label_name"></label> <input id="rankname" name="rankname" type="hidden" value="${md.rankname}" class="check" disabled></li>
						<li><label class="label_name"></label> <input id="membermobile" name="membermobile" type="hidden" value="${md.mobile}" class="check" disabled></li>
						<li><label class="label_name"></label> <input id="membergendor" name="membergendor" type="text" value="${md.gendor}" class="check" disabled></li>
						<li><label class="label_name"></label> <input id="memberidcard" name="memberidcard" type="tetx" value="${md.idcard}" class="check" disabled></li>
				    </c:forEach>
				    <c:forEach items="${guarantee}" var="gu" varStatus="bbb">
						<li><label class="label_name"></label><input id="${gu.paramname}" name="guaranteetime" type="hidden" value="${gu.content}" class="check" disabled></li>
				    </c:forEach>
					</ul>
				</section>
				<div class="divalign_judge">
					<span>确认该会员为入住人？</span>
				</div>
				<section class="detail_four sectionb_judge">
					<div class="" role="button">
						<ul class="clearfix ul_judge">
						    <li><span class="button_margin" onclick="canceljudge()">取消</span></li>
							<li><span class="button_margin" onclick="submitjudge()">确定</span></li>
						</ul>
					</div>
				</section>
		</div>
	<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/order/judgeusercheckin.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
</html>
