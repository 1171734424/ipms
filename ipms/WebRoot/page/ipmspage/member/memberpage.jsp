 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
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
    <link rel="stylesheet"  id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/member/member.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body>
  <div class="member_margin">
  	<section class="detail_one">
		<table class="table_ms">
			<col width="10%"/>
			<col width="23%"/>
			<col width="10%"/>
			<col width="23%"/>
			<col width="10%"/>
			<col width="5%"/>
			<tr>
				<td align="right"><span>会员等级</span></td>
				<td colspan="5"><input id="memberRank" name="memberRank" class="input_ms" type="text" disabled="true" value="${rank.rankName }" /></td>
			</tr>
			<tr>
				<td align="right"><span>姓名</span></td>
				<td><input id="memberName" name="memberName" class="input_ms" type="text" disabled="true" value="${member.memberName }" /></td>
				<td align="right"><span>性别</span></td>
				<td><input id="gendor" name="gendor" class="input_ms" type="text" disabled="true" value="${gendor } "/></td>
			</tr>
			<tr>
				<td align="right"><span>身份证</span></td>
				<td><input id="idcard" name="idcard" class="input_ms" type="text" disabled="true" value="${member.idcard } "/></td>
				<td align="right"><span>手机号</span></td>
				<td><input id="mobile" name="mobile" class="input_ms" type="text" disabled="true" value="${member.mobile } "/></td>
			</tr>
		</table>
		<input type="hidden" id="memberId" name="memberId" value="${member.memberId }"/>
		<input type="hidden" id="accountId" name="accountId" value="${memberAccount.accountId }"/>
		<input type="hidden" id="memberRankId" name="memberRankId" value="${member.memberRank }"/>
   	 </section>
    </div>
    <div class="button_div clearfix">
   	 	<input class="button_margin submitbotton_membersearch one_button" type="button" value="升级" onclick="openMemberRank()" />
   	</div>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/member/member.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
</html>
