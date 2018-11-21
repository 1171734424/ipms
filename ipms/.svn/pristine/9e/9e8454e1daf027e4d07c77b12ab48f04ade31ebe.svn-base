<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/ipms/addSmsTemplates.css" rel="stylesheet" type="text/css" />
		<title>新增模板</title>
	</head>
	<body style="background: rgb(234, 237, 241);">
		<form action="" id="myForm" name="myFrom">
			<div class="add_box">
				<c:forEach items="${templateInfo}" var="tt">
				<input id="templateId" name="templateId" type="hidden" value="${tt.templateId}" />
				<input id="templateNameTemp" name="templateNameTemp" type="hidden" value="${tt.templateName}" />
				<input id="SmsCategoryTemp" name="SmsCategoryTemp" type="hidden" value="${tt.smsCategory}" />
				<input id="branchIdTemp" name="branchIdTemp" type="hidden" value="${tt.branchId}" />
				<input id="statusTemp" name="statusTemp" type="hidden" value="${tt.status}" />
					<table class="">
						<tr>
							<td class="state">
								<label>是否启用：</label>
							</td>
							<td>
								<select id="status" name="status">
									<option value="0">
										未启用
									</option>
									<option value="1">
										启用
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td  class="state">
								<span>模板名称：</span>
							</td>
							<td>
								<select name="TemplateName" id="TemplateName" class="ui-select">
									<c:forEach items="${templateTypeList}" var="tl">
										<option value="${tl.content}">
											${tl.paramName}
										</option>
									</c:forEach>
									<option value="">
										消费通知
									</option>
							    </select>
							</td>
						</tr>
						<tr>
							<td  class="state">
								<span>模板类型：</span>
							</td>
							<td>
								<select id="SmsCategory" name="SmsCategory">
									<option value="1">
										开锁密码
									</option>
									<option value="2">
										短信通知
									</option>
								</select>
							</td>
						</tr>
						<!--<tr>
							<td  class="state">
								<span>归属门店：</span>
							</td>
							<td>
								<select name="branchId" id="branchId" class="ui-select">
									<c:forEach items="${branchList}" var="bl">
										<option value="${bl.branchId}">
											${bl.branchName}
										</option>
									</c:forEach>
									<option value="sssss">
									</option>
								</select>
							</td>
						</tr>
						-->
						<tr>
							<td class="state">
								<label>模板参数：</label>
							</td>
							<td>
								<textarea id="varTemplate" name="varTemplate" class="content" rols="4" disabled = "disabled">${tt.varTemplate}</textarea>
							</td>
						</tr>
						<tr>
							<td class="state">
								<span >模板内容：</span>
							</td>
							<td>
								<textarea id="templateContent" name="templateContent" >${tt.templateContent}</textarea>
							</td>
						</tr>
						<tr>
							<td class="state">
								<span>备注：</span>
							</td>
							<td>
								<textarea id="remark" name="remark" class="">${tt.remark}</textarea>
							</td>
						</tr>
					</table>
					<div class="">
							<!--<div class="button div_button close" onclick="Smsclose();">
								<span>关闭</span>
							</div>
							--><div class="button div_button update" onclick="editSmsTemplateNo();">
								<span>修改</span>
							</div>
							<div class="button div_button  reset" onclick="SmsPageReset();">
								<span>重置</span>
							</div>
							
						</div>
				</c:forEach>	
			</div>
		</form>
		<script src="<%=request.getContextPath()%>/script/crm/manage/smsAddAndEdit.js"></script>
	    <script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
	 		var base_path = '<%=request.getContextPath()%>';
	 		$(function() {
	 		//模板名称
		 		for ( var j = 0; j < $("#TemplateName option").length; j++) {
					if ($("#templateNameTemp").val() == $("#TemplateName option")[j].value) {
						$("#TemplateName option")[j].selected = true;
					}
			    }
	 		//模板类型
	 			for ( var j = 0; j < $("#SmsCategory option").length; j++) {
					if ($("#SmsCategoryTemp").val() == $("#SmsCategory option")[j].value) {
						$("#SmsCategory option")[j].selected = true;
					}
			    }
	 		//模板归属部门
	 			for ( var j = 0; j < $("#branchId option").length; j++) {
					if ($("#branchIdTemp").val() == $("#branchId option")[j].value) {
						$("#branchId option")[j].selected = true;
					}
			    }
			//模板启用状态    
				for ( var j = 0; j < $("#status option").length; j++) {
					if ($("#statusTemp").val() == $("#status option")[j].value) {
						$("#status option")[j].selected = true;
					}
			    }
	 		})
	 	</script>
	</body>
</html>