<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
	request.setAttribute("allpay", request.getAttribute("allpay"));
	request.setAttribute("havepay", request.getAttribute("havepay"));
%>
<!DOCTYPE HTML>
<html>
  <head>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/cash/cashupdate.css" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body>
  	<div class="excep_margin">
  	 <div class="cash_wrapper">
			<div class="cash_one">
				<form action="" method="post">
					<div class="cash_header">
						<label >开始营业日期</label>
						<input id="begintime" type="text" value="" name="" id="" class="search_text"/>
						<label >结束营业日期</label>
						<input id="endtime" type="text" value="" name="" id=""  class="search_text"/>
						<label>金柜</label>
						<select id="cash" name="cash" class="check">
						            <option value="">
										请选择金柜
									</option>
									<option value="A">
										柜A
									</option>
									<option value="B">
										柜B
									</option>
							</select>
						<input type="submit" name="" id="" value="查询" class="osearch"/>
					</div>
				</form>	
			</div>
  	   <form id="pagerForm" name="pagerForm" action="" target="_self">
  	      <div class="color">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">营业日</th>
						<th class="header">交接时间</th>
						<th class="header">班次</th>
						<th class="header">金柜</th>
						<th class="header">现金收入</th>
						<th class="header">现金支出</th>
						<th class="header">补上班次备用金额</th>
						<th class="header">投缴金额</th>
						<th class="header">交接金额</th>
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${cash}" var="cash">
						<tr>
							 <td>${cash["CASHDAY"]}</td>
							 <td>${cash["CASHTIME"]}</td>
							 <td>${cash["SHIFTNAME"]}</td>
							 <td>${cash["CASHBOX"]}</td>
							 <td>${cash["CASHIN"]}</td>
							 <td>${cash["CASHOUT"]}</td>
							 <td>${cash["LASTSHIFTVALUE"]}</td>
							 <td>${cash["PAYMENTVALUE"]}</td>
							 <td>${cash["SHIFTVALUE"]}</td>
							 <td>${cash["REMARK"]}</td>	
						</tr>
					</c:forEach>
				</tbody>
			</table>
		 </div>
		<div id="pager"></div>
	</div>
	</form>
	<%@ include file="../../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
		
		
		laydate({
		elem: '#begintime',
		
	})
	laydate({
		elem: '#endtime',
	})
	</script>
  </body>
</html>





<!--<


<!DOCTYPE HTML>
<html>
  <head>   
    <title>备用金修改</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/cash/cashupdate.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
  </head>
  
  <body>
   
			 <div class="color">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">营业日</th>
						<th class="header">交接时间</th>
						<th class="header">班次</th>
						<th class="header">金柜</th>
						<th class="header">现金收入</th>
						<th class="header">现金支出</th>
						<th class="header">补上班次备用金额</th>
						<th class="header">投缴金额</th>
						<th class="header">交接金额</th>
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach var="cash" items="${cash}">
						<tr>
							 <td>${cash[0].CASHDAY}</td>
											<td>${cash[0].CASHTIME}</td>
											<td>${cash[0].SHIFT}</td>
											<td>${cash[0].CASHBOX}</td>
											<td>${cash[0].CASHIN}</td>
											<td>${cash[0].CASHOUT}</td>
											<td>${cash[0].LASTSHIFTVALUE}</td>
											<td>${cash[0].PAYMENTVALUE}</td>
											<td>${cash[0].SHIFTVALUE}</td>
											<td>${cash[0].REMARK}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		 </div>
		<div id="pager"></div>
	</div>
			<div class="cash">
				<div class="content_member_show">
					<section class="box_content_widget fl">
						<div class="content">
							<table id="myTable" class="myTable" border="0">
								<thead>
									<tr>
										<th class="header">营业日</th>
										<th class="header">交接时间</th>
										<th class="header">班次</th>
										<th class="header">金柜</th>
										<th class="header">现金收入</th>
										<th class="header">现金支出</th>
										<th class="header">补上班次备用金额</th>
										<th class="header">投缴金额</th>
										<th class="header">交接金额</th>
										<th class="header">备注</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="cash" items="${cash}">
									    <td>${cash[0].CASHDAY}</td>
										<td>${cash[0].CASHTIME}</td>
										<td>${cash[0].SHIFT}</td>
										<td>${cash[0].CASHBOX}</td>
										<td>${cash[0].CASHIN}</td>
										<td>${cash[0].CASHOUT}</td>
										<td>${cash[0].LASTSHIFTVALUE}</td>
										<td>${cash[0].PAYMENTVALUE}</td>
										<td>${cash[0].SHIFTVALUE}</td>
										<td>${cash[0].REMARK}</td>
								</c:forEach> 
								</tbody>
							</table>
						</div>
					</section>
				</div>
			</div>
		
			
	</script>
  </body>
</html>
-->