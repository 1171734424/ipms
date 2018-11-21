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
			<div class="add_header add_box">
				<table class="add_table">
					<tr>
						<td class="state">
							<label>是否启用：</label>
						</td>
						<td>
							<select id="status" name="status" class="ui_selct">
								<option value="0">
									未启用
								</option>
								<option value="1" selected>
									启用
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="state">
							<label>模板名称：</label>
						</td>
						<td>
							<select name="TemplateName" id="TemplateName" class="ui_select">
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
						<td class="state">
							<label>模板类型：</label>
						</td>
						<td>
							<select id="SmsCategory" name="SmsCategory" class="ui_select">
								<option value="1">
									开锁密码
								</option>
								<option value="2" selected>
									短信通知
								</option>
							</select>
						</td>
					</tr>
					<!--<tr>
						<td class="state">
							<label>归属门店：</label>
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
							<textarea id="varTemplate" name="varTemplate" class="content" rols="4"></textarea>
						</td>
					</tr>
					<tr>
						<td class="state">
							<label>模板内容：</label>
						</td>
						<td>
							<textarea id="templateContent" name="templateContent" class="content" rols="4"></textarea>
						</td>
					</tr>
					<tr>
						<td class="state">
							<label>备注：</label>
						</td>
						<td>
							<textarea id="remark" name="remark" class="content" rols="4" ></textarea>
						</td>
					</tr>
				</table>
				<div class="">
					<!--<div class="button div_button close" onclick="Smsclose();">
						<span>关闭</span>
					</div>
					
					--><div class="button div_button reset" onclick="SmsPageReset();">
						<span>重置</span>
					</div>
					<div class="button div_button confirm" onclick="addSmsTemplateNo();">
						<span>确认</span>
					</div>
			 </div>
			</div>
		</form>
		<script src="<%=request.getContextPath()%>/script/crm/manage/smsAddAndEdit.js"></script>
	    <script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
	 		var base_path = '<%=request.getContextPath()%>';
	 		
	 		
	 	</script>
	</body>
</html>