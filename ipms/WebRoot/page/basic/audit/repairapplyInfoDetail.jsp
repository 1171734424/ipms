<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("basePath", request.getContextPath()); %>
<%
request.setAttribute("repairdata", request.getAttribute("repairdata"));
request.setAttribute("operid", request.getAttribute("operid"));
request.setAttribute("pagetype", request.getAttribute("pagetype"));
request.setAttribute("audittype", request.getAttribute("audittype"));
%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>详细信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/common/dialog.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css"
			rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/reset.css"
			rel="stylesheet" type="text/css" media="all" />
		<link
			href="<%=request.getContextPath()%>/css/ipms/auditInfoDetail.css"
			rel="stylesheet" type="text/css" media="all" />
			<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/common/jquery.mloading.css"/>
		<style>
</style>
	</head>
	<body class="apinfobg">
		<div style="height: 99%; overflow: auto;">
			<input id="pagetype" type="hidden" class="" value="${pagetype}"
				readonly />
			<input id="audittype" type="hidden" class="" value="${audittype}"
				readonly />
			<form action="" name="myFrom" id="myForm">
				<div class="appinfo_repair">
					<c:forEach items="${repairdata}" var="rd">
						<table class="table">
							<col width="33%" />
							<col width="33%" />
							<col width="33%" />
							<tr>
							 <td>
									<label>
										申请单号:
									</label>
									<input id="operid" type="text" class="table_input"
										value="${operid}" readonly />
								</td>
								<td>
									<label>
										申请人员:
									</label>
									<input id="applystaff" type="hidden" class="table_input"
										value="${rd.RESERVEDPERSON}" readonly />
									<input id="applystaffname" type="text" class="table_input"
										value="${rd.USERNAME}" readonly />
								</td>
								<td>
									<label>
										申请日期:
									</label>
									<input id="recordTime" type="text" class="table_input"
										value="${rd.APPLICATIONDATE}" readonly />
								</td>
							</tr>
							<tr>
							  <td>
							  	<c:choose>
							  	
								  	<c:when test="${ rd.CONTRACTID eq null || rd.CONTRACTID eq ''}">
								  		<label>
											民宿编号:
										</label>
										<input id="contractid" type="text" class="table_input"
											value="${rd.BRANCHID}" readonly />
								  	</c:when>
								  	<c:otherwise>
										<label>
											合同编号:
										</label>
										<input id="contractid" type="text" class="table_input"
											value="${rd.CONTRACTID}" readonly />	
								  	</c:otherwise>
							  	</c:choose>
								</td> 
							  <td>
									<label>
										所在门店:
									</label>
									<input id="branchid" type="hidden" class="table_input"
										value="${rd.BRANCHID}" readonly />
									<input id="recordTime" type="text" class="table_input"
										value="${rd.BRANCHNAME}" readonly />
								</td>
								 <td>
									<label>
										设备类型:
									</label>
									<input id="equipmenttype" type="text" class="table_input"
										value="${rd.PARAMNAME}" readonly />
								</td> 
							</tr>
							<tr>
							   <td>
									<label>
										维修房型:
									</label>
									<input id="roomtype" type="hidden" class="table_input"
										value="${rd.ROOMTYPE}" readonly />
									<input id="roomname" type="text" class="table_input"
										value="${rd.ROOMNAME}" readonly />
								</td> 
								<td>
									<label>
										维修房号:
									</label>
									<input id=roomid type="text" class="table_input"
										value="${rd.ROOMID}" readonly />
								</td>
								<td>
									<label>
										预修日期:
									</label>
									<input id=repairtime type="text" class="table_input"
										value="${rd.REPAIRTIME}" readonly />
								</td>
							</tr>
							<tr>
							<td>
									<label>
										紧急程度:
									</label>
									<select id="emergent" class="table_input clearicon"
										value="${rd.EMERGENT}" disabled="true">
										<option value="1">
											紧急
										</option>
										<option value="2">
											非紧急
										</option>
									</select>
								</td>	
								<td></td>	
								<td></td>
							</tr>
							<tr>
								<td colspan="3">
									<label>
										申请备注:
									</label>
									<textarea id="remark" type="text" class="table_textarea"
										readonly>${rd.REMARK}</textarea>
								</td>
							</tr>
						</table>
					</c:forEach>
				</div>
				<div id="tableInfoTitle" class="address">
					<label>
						审核意见:
					</label>
					<textarea id="auditMessage" class="auditMessage"></textarea>
					<table class="SubmitTable" border="0" cellspacing="0"
						cellpadding="0">
						<tbody>
							<tr>
								<td>
									<div class="div_button button_submit" role="button"
										onclick="auditSubmitOK();">
										<a id="sData">通过</a>&nbsp;
									</div>
									<div class="div_button button_refluse" role="button"
										onclick="auditSubmitFail();">
										<a id="sData">驳回</a>&nbsp;
									</div>
									<!--<div class="div_button button" role="button"
										onclick="window.parent.JDialog.close();">
										<a id="sData">关闭</a>
									</div>-->
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--<div id="tableInfoTitleLog" class="tableInfoTitleLog">
					<table class="SubmitTable" border="0" cellspacing="0"
						cellpadding="0">
						<tbody>
							<tr>
								<td align="center">
									<div class="div_button button"
										onclick="window.parent.JDialog.close();">
										<a id="sData">关闭</a>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>-->
			</form>
		</div>
		
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/script/crm/audit/auditInfoDetail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.mloading.js"></script>
		<script>
	 	var base_path = '<%=request.getContextPath()%>';
	 	$(document).ready(function() {
			var pagetype = $("#pagetype").val();
			if(pagetype=="auditlog"){
				$("#tableInfoTitle").hide();
			}else if(pagetype=="audit"){
				$("#tableInfoTitleLog").hide();
			}
})
	 </script>
	</body>
</html>
