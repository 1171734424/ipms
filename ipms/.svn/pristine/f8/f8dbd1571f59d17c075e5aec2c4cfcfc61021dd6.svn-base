<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html style="width: 100%;height: 100%; overflow:hidden;">
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<%@ include file="../../common/css.jsp"%>
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

.ipt_group{
	    display: flex;
   		 flex-direction: column;
   		 align-item:center;
}
#passform{
	width:360px;
	padding:0px 35px 20px 35px;
	box-sizing:border-box;
	position:absolute;
	left:50%;
	top:50%;
	transform:translate(-50%,-50%);
	background:#fff;
	z-index:0;
	
}
.title{
	text-align:center;
	line-height:50px;
}
.ipt_group input{
	margin-bottom:20px;
	height:30px;
	border:1px solid #d6d7dc;
	text-indent:1em;
}
p{
	padding:0;
	margin:0;
	color:#b6b6b6;
	margin-bottom:20px;
	font-size:12px
}
.btn_group{
	width:100%;
	display:flex;
justify-content: space-between;
}
.btn_group input{
border-radius:4px;
	width:48%;
	height:30px;
	border: 1px solid #d6d6da;
	color: #584f60;
	outline:none;
	cursor:pointer;
	 background:#f6f6f6;
}
.btn_group input:hover{
	background:#584f60;
	color:white;
}
</style>
	<body oncontextmenu="return false;" ondragstart="return false;" onselectstart="return false;">
	<!--  	<form id="passform">
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
		</form>-->
		<form id="passform">
		<div>
	
				<div class='title'>
						密码修改
				</div>
				<div	class='ipt_group'>
						<input type="password" name="psw" id="psw"class="psw" placeholder='请输入你的原密码' />
						<input type="password" name="new_psw" id="new_psw" class="psw" placeholder='请输入你的新密码' />
						<input type="password" name="cfm_psw" id="cfm_psw" placeholder='请确认你的新密码' />
				
				</div>
				<p>密码由6-14位字符组成</p>
				<div class='btn_group'>
						<input type="button" onclick="passwordcheck();" value="保存"/>
						<input type="button" onclick="location.reload();" value="重置"/>
				</div>
				
			</div>
		</form>
		<div id="error_info" class="error" style="clear: both;"></div>
		<script>
			var basePath = '<%=request.getContextPath()%>';
		</script>
		<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>	
		<script src="<%=request.getContextPath()%>/script/password.js"></script>
	</body>
</html>
