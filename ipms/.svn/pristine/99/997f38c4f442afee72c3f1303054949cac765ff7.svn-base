<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>会员升级</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/memberUpGrade.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   	<div>
   		 <table>
	    	<tr>
	    		<td class="membercard_td membercard_td_one">
	    			${memberrankname}
	    		</td>
	    		<td class="membercard_td">
	    			<input class="membercard" type="text" value="一年" readonly="true"/>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td class="membercard_td membercard_td_one">
	    			金额
	    		</td>
	    		<td class="membercard_td">
	    			<input class="membercard" id="money" type="text" value="${memberrankmoney }" readonly="true" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<td class="membercard_td membercard_td_one">
	    			支付方式
	    		</td>
	    		<td class="membercard_td">
		    		<select class="membercard" name="paytype" id="paytype" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="1" selected="selected">现金</option>
					    <!-- <option value="2">银行卡</option>
					    <option value="3">微信</option>
					    <option value="4">支付宝</option> -->
				    </select>
	    			<!-- <input class="membercard" type="text" value="储值卡"/> -->
	    		</td>
	    	</tr>
	    </table>
	    <input id="accountid" type="hidden" value="${accountid }" />
	    <input id="memberid" type="hidden" value="${memberid }" />
	    <input id="memberrank" type="hidden" value="${memberrank }" />
   	</div>
   	<%--<div>
		<input class="cancel" type="button" value="取消" onclick="window.parent.JDialog.close()"/>
	</div>
	--%><div class="submit_div">
		<input class="pdepartsubmit" type="button"  value="确定" onclick="savaMemberRank()"/>
	</div>
  </body>
  <script src="<%=request.getContextPath()%>/script/crm/member/payUpGradeMemberLevel.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
</html>
