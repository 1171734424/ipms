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
       <!--<input type="button" name="" id="payall" class="gdsbutton manysale" value="全选"/>-->
       <input type="button" name="" id="" class="gdsbutton manysale"  value="刷新" onclick="pettypayrefresh()"/><!--
       <input type="button" name="" id="savebutton" class="gdsbutton manysale"  value="保存" onclick="pettypaysave()"/>
       --></div>
   </div>
  	   <form id="pagerForm" name="pagerForm" action="" target="_self">
	    <div class="petty_div"> 
	    	  <table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr >
					    <th class="header petty_hidden"><input name="chooseAll" id="chooseAll" type="checkbox"/>选择</th>
					    <th class="header">操作时间</th>
					    <th class="header petty_hidden">数据号</th>
					    <th class="header">账单号</th>
						<th class="header">结算项目</th>
						<th class="header">结算描述</th>
						<th class="header">消费（元）</th>
						<th class="header">支付（元）</th>
						<th class="header">结算方式</th>
						<th class="header">冲减原因</th>
						<th class="header">状态</th>
						<th class="header">操作人</th>
						<th class="header">投缴凭证</th>
						<th class="header">备注</th>
						<th class="header">账单类型</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${shiftbilldatanew}" var="shiftbilldatanew" varStatus="num">
						<tr ondblclick="shiftbillinfo(this)">
							<td class="petty_td petty_hidden"><input id="${num.index}pettycheck" name="pettycheckbox" type="checkbox" class="allChoose"/></td>
							<td class="petty_td">${shiftbilldatanew["RECORDTIME"]}</td>
							<td class="petty_td petty_hidden">${shiftbilldatanew["DATAID"]}</td>
							<td class="petty_td">${shiftbilldatanew["BILLID"]}</td>
							<td class="petty_td">${shiftbilldatanew["PROJECTNAME"]}</td>
							<td class="petty_td">${shiftbilldatanew["DESCRIBE"]}</td>
							<td class="petty_td">${shiftbilldatanew["COST"]}</td>
							<td class="petty_td">${shiftbilldatanew["PAY"]}</td>
							<td class="petty_td">${shiftbilldatanew["PAYMENT"] }</td>
							<td class="petty_td">${shiftbilldatanew["OFFSET"] }</td>
							<td class="petty_td">${shiftbilldatanew["STATUS"] }</td>
						    <td class="petty_td">${shiftbilldatanew["RECORDUSER"] }</td>
						    <td class="petty_td">${shiftbilldatanew["SHIFTVOUCHER"] }</td>
							<td class="petty_td">${shiftbilldatanew["REMARK"] }</td>
							<td class="petty_td">${shiftbilldatanew["DATATYPE"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div id="pager"></div>             
		</div>
	</form>
	</div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script type="text/javascript">var base_path = "<%=request.getContextPath()%>";</script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/pettycash/shiftcash.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
	   Pager.renderPager(<%=pagination%>);
	   var path = "<%=request.getContextPath()%>";
	</script>
  </body>
</html>
