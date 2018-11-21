<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<%
	request.setAttribute("basePath", request.getContextPath());
 %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<title>欢迎</title>
      	<link href="<%=request.getContextPath()%>/images/shortcut.png" rel="shortcut icon" />
		<link href="<%=request.getContextPath()%>/css/login.css" rel="stylesheet" type="text/css" media="all" />
		<!--<link href="<%=request.getContextPath()%>/css/common/jquery-keyboard.css" rel="stylesheet" type="text/css" media="all" />-->
	</head>
	<body class="indexbody">
		<div id="mainBody">
	      <div id="cloud1" class="cloud"></div>
	      <div id="cloud2" class="cloud"></div>
	    </div>  	
		<div class="logintop">    
	    	<span>管理系统</span>      
	    </div>	    
	    <div class="loginbody">
		    <span class="systemlogo"></span> 
		    <div class="loginbox">
				<form id="loginform" onsubmit="beforeSubmit();" method="post">
					<div class="container">
						<div>
							<input type="text" name="userInfo" class="username" id="userInfo" placeholder="请输入您的登陆名"  
								   style="background-color: transparent" />
							<input type="password" name="psw" class="password" id="psw" placeholder="请输入您的密码"
								   style="background-color: transparent" />
						</div>
					    <div>   
							<ul>
								<li>
									<a href="#" id="btn_submit" class="btn_login" onclick="loginCheck()">
										<font class="font_normal" >登&nbsp;&nbsp;&nbsp;录</font>	
									</a>
								</li>
								<li class="li_padding">
									<a href="#" id="btn_submit" class="btn_regist" onclick="regist()">
										<font class="font_normal" >注&nbsp;&nbsp;&nbsp;册</font>	
									</a>
								</li>
								<li class="li_padding">
									<a href="#" id="btn_reset" class="btn_reset" onclick="loginReset()">
										<font class="font_normal" >重&nbsp;&nbsp;&nbsp;置</font>	
									</a>
								</li>
							</ul>
						</div>			
					</div>
				</form>
			</div>
		</div>
		<div id="login_error" class="error" style="clear: both; margin-left:410px; margin-top:210px;"></div>
	    <div class="loginbm" >版权所有 &copy 南京迪软软件有限公司(Ideas Software Co.,Ltd.)保留一切权利。苏ICP备05064346号</div>
		<script>
	  		var basePath = "${ basePath }";
	  	</script>
	  	<%@ include file="../common/script.jsp"%>
	  	<script src="<%=request.getContextPath()%>/script/common/cloud.js"></script>
	  	<!--<script src="<%=request.getContextPath()%>/script/common/jquery.keyboard.js"></script>-->
	  	<script src="<%=request.getContextPath()%>/script/login.js"></script>

		<script language="javascript">
			/*function showKeyboard(obj) {
				initKeyboard(obj);
				$(obj).getkeyboard().reveal();
			}
			
			function initKeyboard(obj) {
				$(obj).keyboard({
					openOn : null,
					stayOpen : true,
					layout : 'international',
					usePreview: false
				});
			}*/
			
		</script> 	
	</body>
</html>
