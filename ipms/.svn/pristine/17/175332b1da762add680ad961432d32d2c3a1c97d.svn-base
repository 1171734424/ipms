<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
  <head>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
  </head>
  <body>
   <div class="condition_div">
  	   <form id="pagerForm" name="pagerForm" action="" target="_self">
	    <div class="div_part_one">
	    	<table id="myTable" class="myTable" border="0" width="100">
	    	     <col width="5%">
	    	     <col width="6%">
	    	     <col width="4%">
	    	     <col width="4%">
	    	     <col width="7%">
	    	     <col width="5%">
	    	     <col width="7%">
	    	     <!--<col width="7%"> -->
	    	     <col width="5%">
	    	     <col width="5%">
	    	     <col width="5%">
	    	     <col width="5%">
	    	     <col width="6%">
	    	     <col width="7%">
	    	     <!-- <col width="6%">-->
	    	     <col width="6%">
	    	     <col width="6%">
	    	     <col width="5%">     
				<thead id="log">
					<tr>
						<th class="header ornumber">订单号</th>
						<th class="header">预定时间</th>
						<th class="header">预定渠道</th>
						<th class="header">预订人</th>
						<th class="header">预订人手机</th>
						<th class="header">房型</th>
						<th class="header">房价</th>	
						<!--<th class="header">入住人</th>-->
						<th class="header">入住日期</th>
						<th class="header">预离日期</th>					
						<th class="header">担保类型</th>
						<th class="header">保留时间</th>
						<th class="header">预付（现金）</th>
					    <th class="header">预付（银行卡）</th>
						<!--<th class="header">预付（积分）</th>
						--><th class="header">客房备注</th>
						<th class="header">接待备注</th>
						<th class="header">订单状态</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${orderlist}" var="orderlist">
						<tr ondblclick="checkorderinfo(this)">
							<td class="orderdata_td">${orderlist["ORDERID"]}</td>
							<td class="orderdata_td">${orderlist["RECORDTIME"]}</td>
							<td class="orderdata_td">${orderlist["SOURCE"] }</td>
							<td class="orderdata_td">${orderlist["ORDERUSER"] }</td>
							<td class="orderdata_td">${orderlist["MPHONE"] }</td>
							<td class="orderdata_td">${orderlist["ROOMTYPE"]}</td>
							<td class="orderdata_td">${orderlist["ROOMPRICE"]}</td>
							<!--<td class="orderdata_td">${orderlist["USERCHECKIN"] }</td>-->
							<td class="orderdata_td">${orderlist["ARRIVALTIME"] }</td>
							<td class="orderdata_td">${orderlist["LEAVETIME"] }</td>
							<td class="orderdata_td">${orderlist["GUARANTEE"]}</td>
							<td class="orderdata_td">${orderlist["LIMITED"] }</td>
							<td class="orderdata_td">${orderlist["ADVANCECASH"] }</td>
							<td class="orderdata_td">${orderlist["ADVANCECARD"] }</td>
							<!--<td class="orderdata_td">${orderlist["ADVANCESCORE"] }</td>-->
							<td class="orderdata_td">${orderlist["ROOMREMARK"] }</td>
							<td class="orderdata_td">${orderlist["RECEPTIONREMARK"] }</td>
							<td class="orderdata_td">${orderlist["STATUS"] }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager"></div>
				<div>
					<input type="hidden" id="source" name="source" value="" />
					<input type="hidden" id="status" name="status" value="" />
				</div>
			</form>
  </div>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/ordercheckin/orderdata.js"></script>、
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
		var path = "<%=request.getContextPath()%>";
	</script>
  </body>
</html>
