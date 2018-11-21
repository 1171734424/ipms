<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
	request.setAttribute("allpay", request.getAttribute("allpay"));
	request.setAttribute("havepay", request.getAttribute("havepay"));
	request.setAttribute("recordUser", request.getAttribute("recordUser"));
	request.setAttribute("cashboxkind", request.getAttribute("cashboxkind"));
	request.setAttribute("start", request.getAttribute("start"));
	request.setAttribute("end", request.getAttribute("end"));
%>
<!DOCTYPE HTML>
<html>
  <head>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
  </head>
  <body id="">
    <input id="recordUser" name="recordUser" type="hidden" value = "${recordUser}"/>
  	<div class="">
  	<div class="petty_title"><!--
        <div class="petty_search">
  	   <select id="shiftid" name="" class="search_select">
					        <option  value="">所有班次</option> 
						 <c:forEach var="th" items="${categoryCondition}">  
                               <option  value="${th.categoryid}"> ${th.categoryname} </option>  
                               </c:forEach>  
		</select>
		 <select id="cashbox" name="" class="search_select">
					        <option  value="">所有金柜</option> 
						 <c:forEach var="ck" items="${cashboxkind}">  
                               <option  value="${ck.content}"> ${ck.paramname} </option>  
                               </c:forEach>  
		</select>
	    <input type="text" name="" id="voucher" class="search_select"  placeholder="凭证号"/>
	    <select id="state" name="" class="search_select">
	        <option  value="">所有状态</option> 
		    <option  value="0">未投缴</option> 
			<option  value="1">已投缴</option> 			
		</select>
		<input type="button" name="" id="" class="gdsbutton manysale" value="查询" onclick="pettypaysearch()"/>
	   </div>
	   --><div class="petty_button">
       <input type="button" name="" id="paybutton" class="gdsbutton manysale petty_hidden" value="投缴" onclick="pettypaysubmit()"/>
       <input type="button" name="" id="payall" class="gdsbutton manysale" value="全选"/>
       <input type="button" name="" id="" class="gdsbutton manysale"  value="刷新" onclick="pettypayrefresh()"/><!--
       <!--<input type="button" name="" id="savebutton" class="gdsbutton manysale"  value="保存" onclick="pettypaysave()"/>
       --></div>
   </div>
  	   <form id="pagerForm" name="pagerForm" action="" target="_self">
	    <div class="petty_div"> 
	    	  <table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr >
					    <th class="header"><input name="chooseAll" id="chooseAll" type="checkbox"/>选择</th>
					    <th class="header petty_hidden">数据号</th>
						<th class="header">操作时间</th>
						<th class="header">交接人</th>
						<th class="header">确认人</th>
						<th class="header petty_hidden">门店</th>
						<th class="header">班次</th>
						<!--<th class="header">金柜</th>
						--><th class="header">现金收入</th>
						<th class="header">现金支出</th>
						<th class="header">投缴金额</th>
						<th class="header">备用金交接</th>
						<th class="header">补上班次</th>
						<th class="header">本班需补</th>
						<th class="header">银行卡收入</th>
						<th class="header">银行卡笔数</th>
						<th class="header">押金收据</th>
						<th class="header">发票号</th>
						<th class="header">凭证号</th>
						<th class="header">是否投缴</th>
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${pettypaydata}" var="pettypaydata" varStatus="num">
						<tr>
							<td class="petty_td"><input id="${num.index}pettycheck" name="pettycheckbox" type="checkbox" class="allChoose"/></td>
							<td class="petty_hidden petty_td">${pettypaydata["LOGID"]}</td>
							<td class="petty_td">${pettypaydata["RECORDTIME"]}</td>
							<td class="petty_td">${pettypaydata["HANDUSER"]}</td>
							<td class="petty_td">${pettypaydata["CONFIRMUSER"]}</td>
							<td class="petty_hidden">${pettypaydata["BRANCHID"]}</td>
							<td class="petty_td">${pettypaydata["SHIFT"]}</td>
							<!--<td class="petty_td">${pettypaydata["CASHBOX"] }</td>
							--><td class="petty_td"><input id="" name="" type="text" class="pp_input" value="${pettypaydata['CASHIN'] }"  onkeyup="num(this)"  maxlength="9" readonly="true"/></td>
							<td class="petty_td"><input id="" name="" type="text" class="pp_input" value="${pettypaydata['CASHOUT'] }" onkeyup="num(this)" maxlength="9" readonly="true"/></td>
							<td class="petty_td" name="paymentvalue"><input id="" name="" type="text" class="pp_input" value="${pettypaydata['PAYMENTVALUE'] }"  onkeyup="num(this)" maxlength="9" readonly="true"/></td>
							<td class="petty_td"><input id="" name="" type="text" class="pp_input" value="${pettypaydata['SHIFTVALUE'] }" onkeyup="num(this)" maxlength="9" readonly="true"/></td>
							<td class="petty_td"><input id="" name="" type="text" class="pp_input" value="${pettypaydata['LASTSHIFTVALUE'] }"  onkeyup="num(this)" maxlength="9" readonly="true"/></td>
							<td class="petty_td"><input id="" name="" type="text" class="pp_input" value="${pettypaydata['CURRSHIFTVALUE'] }" onkeyup="num(this)" maxlength="9" readonly="true"/></td>
							<td class="petty_td"><input id="" name="" type="text" class="pp_input" value="${pettypaydata['CARDBALANCE'] }"  onkeyup="num(this)" maxlength="9" readonly="true"/></td>
							<td class="petty_td"><input id="" name="" type="text" class="pp_input" value="${pettypaydata['CARDS'] }"  onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="2" readonly="true"/></td>
							<td class="petty_td"><input id="" name="" type="text" class="pp_input" value="${pettypaydata['DEPOSITNO'] }"  onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="28" readonly="true"/></td>
							<td class="petty_td"><input id="" name="" type="text" class="pp_input" value="${pettypaydata['INVOICENO'] }"  onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="28" readonly="true"/></td>
							<td class="petty_td">${pettypaydata["VOUCHER"] }</td>
							<td class="petty_td">${pettypaydata["STATE"] }</td>
							<td class="petty_td">${pettypaydata["REMARK"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<table class="count_table">
		<tr>
		<td align="center"><span>总收入：</span></td>
		<td align="center"><input id="allpay" name="" type="text" class="count_td" value="${allpay}元" readonly/></td>
		<td align="center"><span>已投缴：</span></td>
		<td align="center"><input id="havepay" name="" type="text" class="count_td" value="${havepay}元" readonly/></td>
		<td align="center"><span>未投缴：</span></td>
		<td align="center"><input id="shavepay" name="" type="text" class="count_td" readonly/></td>
		</tr>
		</table>
		<div id="pager"></div>                                                                             
	</form>
	</div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script type="text/javascript">var base_path = "<%=request.getContextPath()%>";</script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/pettypay/pettypay.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
	   Pager.renderPager(<%=pagination%>);
	    $(document).ready(function(){
	    });

	</script>
  </body>
</html>
