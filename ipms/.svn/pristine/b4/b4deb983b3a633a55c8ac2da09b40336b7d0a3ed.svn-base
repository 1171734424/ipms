<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html style="width: 100%;height: 100%; overflow:hidden;">
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<%@ include file="../common/css.jsp"%>
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	</head>
	<style type="text/css">
body {
	background-color: rgb(234, 237, 241);
	width: 100%;
	height: 100%;
	overflow: hidden;
	color:#333;
}

.top-table tr {
	height: 80px;
}

table.top-table {
	width: 100%;
	border-collapse: collapse;
	border-spacing: 0;
}

* {
	font-size: 12pt;
	border: 0;
	margin: 0;
	padding: 0;
}
input[type="password"] {
    margin-right: 10px;
    position: relative;
    
}
input[type="text"], input[type="password"] {
    height: 33px;
    border: 1px solid #c2c2c2;
    width: 220px;
    padding: 0 5px;
    color:#666;
}
::-webkit-input-placeholder { /* WebKit browsers */
	color: #757575;
	font-size: 13px;
}

::-moz-input-placeholder { /* Mozilla Firefox 19+ */
	color: #757575;
	font-size: 13px;
}

::-ms-input-placeholder { /* Internet Explorer 10+ */
	color: #757575;
	font-size: 13px;
}

.button-group {
	margin-bottom: 15px;
	position: relative;
	height: 50px;
	width: 100%;
}

.button.current {
    background-color: #fff;
    border: 1px solid #ddd;
    position: absolute;
    left: 1px;
    top: 15px;
    color:#333;
}
.button.current:hover {
	background: #f0f0f0;
}
.button {
	display: inline-block;
	padding: 0 14px;
	height: 32px;
	line-height: 32px;
	background-color: #f1f1f1;
	border: 1px solid #c2c2c2;
	border-radius: 3px;
	color: #333;
	font-size: 14px;
	cursor: pointer;
	margin-right: 6px;
}

.button.current .button-label {
	color: #333;
}
.button .button-label {
	float: left;
	height: 16px;
	margin-top: 8px;
	font-size: 14px;
	color: #000;
	line-height: 16px;
}

table.top-table td.kv-label,table.top-table td.kv-content {
	height: 68%;
    padding: 5px 84px;
    border-bottom: 1px solid #dadada;
    font-size: 14px;
    padding-left: 20px;
}

table.top-table .kv-label {
	padding: 0 10px;
	background: #f5f5f5;
	border: 1px solid #dadada;
	border-top: none;
}

table.top-table tr:first-child td.kv-label:first-child {
	border-top-color: #00C1DE;
}
table.top-table tr:first-child td.kv-content,table.top-table tr:first-child td.kv-label
	{
	border-top: 3px solid #4A5064;
}
table.top-table tr td.kv-content:last-child {
    border-right: 1px solid #dadada;
}
.top-table .kv-content{
	padding:10px;
}
.text-tip {
    font-size: 12px;
    color: #b3b3b3;
}
.fa{
	position: absolute;
    z-index: 1;
    left: 45%;
    font-size: 16px;
    color: #666;
}
.fa.one{
	top: 12.3%;
}
.fa.two{
	top: 18.9%;
}
.fa.three{
	top: 25.7%;
}
</style>
	<body oncontextmenu="return false;" ondragstart="return false;" onselectstart="return false;">
		<form id="passform">
			<div class="fieldWrapper" align="center">
				<div class="button-group">
					<div class="button current add" onclick="passwordcheck();">
						<span class="button-label">保存</span>
					</div>
				</div>
				<table class="top-table">
					<tbody>
						<tr>
							<td class="kv-label">
								原始密码：
							</td>
							<td class="kv-content" style="padding:10px;">
								<i for="psw"  class="fa fa-eye-slash one"></i>
								<input type="password" name="psw" id="psw"class="psw"/>
								<span class="text-tip">(原始密码不能为空)</span>
							</td>
						</tr>
						<tr>
							<td class="kv-label">
								新密码：
							</td>
							<td class="kv-content" style="padding: 10px;">
								<i for="psw"  class="fa fa-eye-slash two"  ></i>
								<input type="password" name="new_psw" id="new_psw" class="psw"/>
								<span class="text-tip">(原始密码不能为空)</span>
							</td>        
						</tr>                                                                 
						<tr>
							<td class="kv-label">
								确认密码：
							</td>
							<td class="kv-content" style="padding: 10px;">
								<i for="psw"  class="fa fa-eye-slash three"  ></i>
								<input type="password" name="cfm_psw" id="cfm_psw"/>
								<span class="text-tip">(原始密码不能为空)</span>
							</td>
						</tr>
					</tbody>

				</table>
				<p>
					密码由6-14位字符组成（初始密码888888）
				</p>
			</div>
		</form>
		<div id="error_info" class="error" style="clear: both;"></div>
		<script>
			var basePath = '<%=request.getContextPath()%>';
		</script>
		<%@ include file="../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>	
		<script src="<%=request.getContextPath()%>/script/password.js"></script>
	</body>
</html>
