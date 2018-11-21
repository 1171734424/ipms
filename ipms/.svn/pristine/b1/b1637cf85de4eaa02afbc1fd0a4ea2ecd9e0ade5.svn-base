<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
	request.setAttribute("logintime", request.getAttribute("logintime"));
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
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/theme/default/laydate.css" />
  </head>
   <body id="">
   <input id="logintime" name="logintime" type="hidden" value="${logintime}"/>
   <input id="recordUser" name="recordUser" type="hidden" value = "${recordUser }"/>
      <form action="cashUpdate.do" id="pagerForm" name="pagerForm" target="_self">
  	<div class="">
  	<div class="petty_title">
  	   <div class="petty_search">
  	   <!--
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
	    -->
	    <input type="text" name="start" id="start" class="search_select petty_voucher"  placeholder="起始时间"/>
	    <input type="text" name="end" id="end" class="search_select petty_voucher"  placeholder="结束时间"/>
	    <input type="text" name="" id="voucher" class="search_select petty_voucher"  placeholder="凭证号"/>
	    <select id="state" name="" class="search_select">
	        <option  value="">账单类型</option> 
		    <option  value="房账">房账</option> 
			<option  value="工作账">工作账</option> 			
		</select>
		<select id="paytype" name="" class="search_select">
	        <option  value="">是否投缴</option>
		    <option  value="投">投缴</option>
			<option  value="未">未投缴</option>
		</select>
		<input type="button" name="" id="" class="gdsbutton manysale" value="查询" onclick="pettycashsearch()"/>
	   </div>
	   <div class="content" style="height:630px">
							<iframe name="frame" id="frame" class="myTable" frameborder="0" width="100%" height="100%" ></iframe>
	</div>
	  
   </div>
   </div>
	</form>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script type="text/javascript">var base_path = "<%=request.getContextPath()%>";</script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipmshotel/leftmenu/pettycash/shiftcash.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/laydate.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
	   Pager.renderPager(<%=pagination%>);
	</script>
  </body>
</html>
