<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>用户注册</title>
		<meta http-equiv="pragma" content="no-cache">    
	    <meta http-equiv="cache-control" content="no-cache">    
	    <meta http-equiv="expires" content="0"> 
		<%@ include file="../common/css.jsp"%>
      	<link href="<%=request.getContextPath()%>/images/shortcut.png" rel="shortcut icon" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/pageloading.css"/>
	</head>
	<body>
		<form id="registerForm">
			<div class="registerPlat">
				<div class="registerTitle">
					<span>用户注册</span>
				</div>
				<div class="registerInfo">
					<fieldset class="registerFiled">
						<legend>
							<label class="fieldFont">用户信息</label>
						</legend>
						<div class="fieldWrapper">
							<table border="0" contactNolspacing="0" contactNolpadding="0" width="100%" height="90%">
								<tr>
									<td><span>用户名</span></td>
									<td>
										<input type="text" id="loginName" name="loginName" title="用户名"/>
									</td>
									<td colspan="2">
										<span class="fieldTip">*用户名由6~32位的数字、字母、下划线构成。</span>
									</td>
								</tr>
								<tr>
									<td><span>密码</span></td>
									<td>
										<input type="password" id="psw" name="psw" title="密码"/>
									</td>
									<td colspan="2">
										<span class="fieldTip">*密码由6~14位的数字、字母构成。</span>
									</td>
								</tr>
								<tr>
									<td><span>确认密码</span></td>
									<td>
										<input type="password" id="confirmPassword" name="confirmPassword" title="确认密码"/>
									</td>
									<td width="15%"><span>性别</span></td>
									<td width="35%">
										<select id="gendor" name="gendor" title="性别">
											<option value="0">女</option>
											<option value="1">男</option>
										</select>
									</td>
								</tr>
							</table>
						</div>
					</fieldset>
				</div>
				<div class="registerInfo">
					<fieldset class="registerFiled">
						<legend class="fieldFont">
							<label>基础信息</label>
						</legend>
						<div class="fieldWrapper">
							<table border="0" contactNolspacing="0" contactNolpadding="0" width="100%" height="90%">
								<tr>
									<td><span>身份证</span></td>
									<td>
										<input type="text" id="IDCard" name="IDCard" title="身份证"/>
									</td>
									<td width="15%"><span>手机</span></td>
									<td width="35%">
										<input type="text" id="contactNo" name="contactNo" title="手机"/>
									</td>
								</tr>
								<tr>
									<td><span>邮箱</span></td>
									<td>
										<input type="text" id="email" name="email" title="邮箱"/>
									</td>
									<td><span>所属单位</span></td>
									<td>
										<input type="text" id="departName" name="departName" title="所属单位"/>
									</td>
								</tr>
								<tr>
									<td><span>联系地址</span></td>
									<td>
										<input type="text" id="contactAddr" name="contactAddr" title="联系地址"/>
									</td>
								</tr>
							</table>
						</div>
					</fieldset>
				</div>
				<div class="registerBottom">
					<span class="registerButton" id="rgb" onclick="doRegister();">注册</span>
					<span class="registerButton" id="bkb" style="display: none;" onclick="doBack();">返回</span>
				</div>
				<div id="registerError" class="error" style="clear: both;"></div>
			</div>
		</form>
		
		<script>
			var basePath = "<%=request.getContextPath()%>";
		</script>
		<%@ include file="../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/register.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/pageloading.js"></script>
	</body>
</html>

