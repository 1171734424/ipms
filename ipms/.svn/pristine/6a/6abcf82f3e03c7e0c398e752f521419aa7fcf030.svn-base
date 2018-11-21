<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("basePath", request.getContextPath()); %>
<%@ include file="../../../common/taglib.jsp"%>
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
  <body>
		<div style="height: 99%; overflow: auto;">
			<input id="pagetype" type="hidden" class="" value="${pagetype}"
				readonly />
			<input id="audittype" type="hidden" class="" value="${audittype}"
				readonly />
			<form action="" name="myFrom" id="myForm">
				<div class="appinfo_repair">
					<c:forEach items="${result}" var="re">
						<table class="table">
							<col width="25%" />
							<col width="25%" />
							<col width="25%" />
							<col width="25%" />
							<tr>
								<td>
									<c:if test="${flag =='2'}">
									<label>
										维修房号:
									</label>
									<input id=roomid type="text" class="table_input dbl_repair_input"
										value="${re.ROOMID}" readonly />
									</c:if>
									<c:if test="${flag =='1'}">
									<label>
										门店名称:
									</label>
									<input id=roomid type="text" class="table_input dbl_repair_input"
										value="${re.BRANCHID}" readonly />
									</c:if>
								</td>
								<td>
									<label>
										申请人员:
									</label>
									<c:if test="${re['reservedPerson'] == null}">
									<input id="applystaffname" type="text" class="apply_member_input table_input dbl_repair_input"
										value="admin" readonly />									</c:if>
									<c:if test="${re['reservedPerson'] != null}">
									<input id="applystaffname" type="text" class="apply_member_input table_input dbl_repair_input"
										value="${re['RESERVEDPERSON']}" readonly />
									</c:if>
								</td>
								<td>
									<label>
										维修类型:
									</label>
									<input id="equipment" type="text" class="table_input dbl_repair_input"
										value="${re.EQUIPMENT}" readonly />
								</td>
								<td>
									<label>
										紧急程度:
									</label>
									<input id="emergent" class="table_input dbl_repair_input"
										value="${re.EMERGENT}" readonly">

									</input>
								</td>
							</tr>
							<tr>
								<td>
									<label>
										维修房型:
									</label>
									<input id="roomname" type="text" class="table_input dbl_repair_input"
										value="${re.ROOMTYPE}" readonly />
								</td>
								<td>
									<label>
										所在门店:
									</label>
									<input id="r" type="text" class="table_input dbl_repair_input"
										value="${re.BRANCHID}" readonly />
								</td>
								<td>
									<label>
										维修状态:
									</label>
									<input id="equipment" type="text" class="table_input dbl_repair_input"
										value="${re.STATUS}" readonly>
								</td>
								<td>
									<label>
										申请日期:
									</label>
									<input id="" type="text" class="table_input dbl_repair_input"
										value="<fmt:formatDate type='time' value='${re["APPLICATIONDATE"]}' pattern="yyyy/MM/dd" />
										" readonly />
								</td>
							</tr>
							<tr>
								<!--<td>${re["names"]}</td>
								--><%-- <td>
									<c:if test="${flag =='1'}">
									<label>
										房单编号:
									</label>
									</c:if>
									<c:if test="${flag =='2'}">
									<label>
										合同编号:
									</label>
									</c:if>
									<input id="contractid" type="text" class=" contract_input table_input dbl_repair_input"
										value="${re.CONTRACTID}" readonly />
								</td> --%>
								<td>
									<label>
										申请单号:
									</label>
									<input id="" type="text" class="apply_input table_input dbl_repair_input"
										value="${re['REPAIRAPPLYID']}" readonly />
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<label>
										申请备注:
									</label>
									<textarea id="auditMessage" class="table_textarea repair_dblremark" readonly>${re.REMARK}</textarea>
								</td>
							</tr>	
							<tr>
								<td colspan="4">
									<label>
										审核描述:
									</label>
									<textarea id="remark" type="text" class="table_textarea repair_dblaudit" readonly>${re.AUDITDESCRIPTION}</textarea>
								</td>
							</tr>
						</table>
					</c:forEach>
				</div>
			</form>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/script/crm/audit/auditInfoDetail.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	</body>
</html>
