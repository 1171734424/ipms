<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("basePath", request.getContextPath()); %>


<%--<%
request.setAttribute("repairdata", request.getAttribute("repairdata"));
request.setAttribute("operid", request.getAttribute("operid"));
request.setAttribute("pagetype", request.getAttribute("pagetype"));
request.setAttribute("audittype", request.getAttribute("audittype"));
%>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
  		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/common/dialog.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css"
			rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/reset.css"
			rel="stylesheet" type="text/css" media="all" />
		<link
			href="<%=request.getContextPath()%>/css/ipms/auditInfoDetail.css"
			rel="stylesheet" type="text/css" media="all" />
  </head>
  <style>
  	.appinfo_repair table td{
	  	vertical-align: middle;
	    font-size: 0.85rem;
	    white-space: nowrap;
	    text-align: right;
  	}
   .dbl_repair_input{
	    margin: 4px !important;
	    width: 63% !important;
        padding: 0 5px 0;
   }
   .table {
	    width: 98.1%;
	    margin: 1% 1%;
	    border: none;
	    height: 16%;
}
   .table_input{
	    background-color: #f0f0f0;

}
   .repair_dblremark {
        height: 60px !important;
	    line-height: 20px !important;
	    margin: -1px;
	    margin-top: 5px;
	    width: 91.4%;
 }
	 .dealandcancle_button{
	    padding: 3px;
	    font-size: 0.75rem;
	    background: #FC8A15;
	    color: #fff;
	    height: 30px;
	    width: 39px;
	}
  </style>
  <body>
		<div style="height: 99%; overflow: auto;">
<!--			<input id="pagetype" type="hidden" class="" value="${pagetype}"-->
<!--				readonly />-->
<!--			<input id="audittype" type="hidden" class="" value="${audittype}"-->
<!--				readonly />-->
			<form action="" name="myFrom" id="myForm">
				<div class="appinfo_repair">
					<c:forEach items="${result}" var="re">
						<table class="table">
							<tr>
								<td>
									<label>
										合同编号：
									</label>
									<input id=roomid type="text" class="table_input dbl_repair_input "
										value="${result[0]['CONTRACT_ID']}" readonly />
								</td>
								<td>
									<label>
										门店名称：
									</label>
									<input id="applystaffname" type="text" class="apply_member_input table_input dbl_repair_input "
										value="${result[0]['BRANCHNAME']}" readonly />
								</td>
								<td>
									<label>
										预约人：
									</label>
									<input id="equipment" type="text" class="table_input dbl_repair_input"
										value="${result[0]['RESERVEDPERSON']}" readonly />
								</td>
								<td>
									<label>
										手机号：
									</label>
									<input id="emergent" class="table_input dbl_repair_input"
										value="${result[0]['MOBILE']}" readonly>
									</input>
								</td>
							</tr>
							<tr>
								
								<td>
									<label>
										保洁日期：
									</label>
									<input id="r" type="text" class="table_input dbl_repair_input"
										value="${result[0]['CLEANTIME']}" readonly />
								</td>
								<td>
									<label>
										时段：
									</label>
									<input id="" type="text" class="apply_input table_input dbl_repair_input"
										value="${result[0]['TIMEAREA']}" readonly />
								</td>
								<td>
									<label>
										申请日期：
									</label>
									<input id="equipment" type="text" class="table_input dbl_repair_input"
										value="${result[0]['APPLICATIONTIME']}" readonly>
								</td>
							</tr>
							
							<tr>
								<td>
									<label>
										保洁类型：
									</label>
									<input id="" type="text" class="apply_input table_input dbl_repair_input"
										value="${result[0]['CLEANSTYLE']}" readonly />
								</td>
								
							</tr>
							
							<tr>
								<td colspan="4">
									<label>
										备注：
									</label>
									<textarea id="auditMessage" class="table_textarea repair_dblremark table_input" rows="2" readonly>${result[0]['REMARK']}</textarea>
								</td>
							</tr>
						</table>
						
					</c:forEach>
				</div>
			</form>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
		<%-- <script src="<%=request.getContextPath()%>/script/apartment/apartmentLeftmenu/apartmentclean/auditInfoDetail.js"></script> --%>
        <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	</body>
</html>
