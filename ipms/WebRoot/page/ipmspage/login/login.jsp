<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%
	request.setAttribute("basePath", request.getContextPath());
 %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>幕后致选</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<meta name="author" content="Matthew Wagerfield"/>
		<meta name="keywords" content="flat,surface,shader,Float32Array"/>
		<meta name="description" content="Simple, lightweight Flat Surface Shader for rendering lit triangles."/>
		<meta property="og:description" content="Simple, lightweight Flat Surface Shader for rendering lit triangles."/>
		<meta property="og:site_name" content="Flat Surface Shader"/>
		<meta property="og:title" content="Flat Surface Shader"/>
		<meta property="og:type" content="website"/>
		
		<link rel="Shortcut Icon" href="<%=request.getContextPath()%>/css/ipms/img/backgroundimg/deer.png" type="image/x-icon">
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/ipms/css/reset.css" /><%--
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/themes/demo.css" />
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/themes/easydropdown.css" />
		--%><link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/animate.css" />		
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/ipms/css/loginfile/login.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<%--<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/system/footer.css" />
 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/select/jquery.mCustomScrollbar.min.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/select/select.css" /> 
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/j-select.css" />--%>
		<script>
          var base_path = "<%=request.getContextPath()%>";
	    </script>
	</head>
	<style>
		::-webkit-input-placeholder {
			/* WebKit browsers */
			color: #ccc;
			font-size: 14px;
		}
		::-moz-input-placeholder {
			/* Mozilla Firefox 19+ */
			color: #ccc;
			font-size: 14px;
		}
		::-ms-input-placeholder {
			/* Internet Explorer 10+ */
			color: #ccc;
			font-size: 14px;
		}
		#jd_dialog_m{
		margin:auto;
	   background-color: rgba(255, 255, 255, 0.2)!important;
	   border:1px solid rgba(255,255,255,0.4);
		}
		#jd_dialog_m_h{
		background-color:transparent;
		height:44px;
		text-align:center
		}
	
		
		#jd_dialog_m_h_l{
		color:white;
		text-align:center;
		line-height:40px;
		padding-left:30px;
		font-size:18px;
		}
		.table_cond ul li{
		margin:0
		}
		select#selectType{
		height:40px!important;
		padding-left:4px;
		}
		#jd_dialog{
		height: 450px;
      position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    margin: auto;}
  
 
	</style>
	<body>
		<div id="container" class="container">
			<%--<div id="output" class="container"></div>
			<div id="vignette" class="overlay vignette"></div>
			<div id="noise" class="overlay noise"></div>
			--%><div id="ui" class="wrapper">
				<div id="home-banner">
					<!--login start-->
					<div class="loginReg-viewbox login-margin login-weight" id="box">
						<div class="loginReg-panel clearfix">
							<div class="design-img">
								<img src="<%=request.getContextPath()%>/css/ipms/img/backgroundimg/login.png" alt="系统logo"/>
								<span class="chtitle">智能物业管理系统</span>
								<span class="entitle">property management system</span>
							</div>
							<input type="hidden" id="" name="CodeToken">
							<div class="formView formView_login" >
								<form role="form" class="form-horizontal" id="loginforma" onsubmit="beforeSubmit();" method="post" >
									<input type="hidden" id="" name="id" value="1">
									<div class="form-group">
										<label class="inputTit usertitle"></label>
										<input id="loginName" name="loginName" type="text"
											class="form-control inputInf" placeholder="请输入用户名" />
										<span id="login_error" class="error fadeInDown"></span>
										</div>
										<div class="form-group">
										<label class="inputTit passwordtitle"></label>
										<input id="password" name="password" type="text" onfocus="this.type='password'"
											maxlength="20" class="form-control inputInf" placeholder="请输入密码" autocomplete="off"/>
									</div>
									<div class="form-group">
										<label class="inputTit branchtitle"></label>
										<%--<select name="branchId" id="branchId" class="dropdown scrollable  form-control inputInf" >
											<option value="">a</option>
										</select>										
									--%>
										<div class="form-control dropdown inputInf" >
											<input id="branchId" type="hidden" value=""/>
											<span id="branchValue" class="data-span" onclick="chooseBranch()">请选择门店</span>
											<!-- <i class="carat"></i> -->
											<div class="branch-ul fadeInDown">
												<ul class="branchData  clearfix">
												</ul>
											</div>
										</div>
									</div>
									<div class="form-group">
										<!-- 提示信息 -->
										<div class="errMsg" id="p_message1"></div>
										<button type="button" class="btn-loginReg" onclick="loginCheck();">
											登&nbsp;&nbsp;&nbsp;&nbsp;录
										</button>
									</div>
									<div class="forgetpasseord" >
										<p class="p_forget">忘记密码?</p>
									</div>
								</form>
							</div>
							<div class="formView formView_pwd" style="display:none;">
								<form role="form" class="form-horizontal" id="loginform" onsubmit="beforeSubmit();" method="post" >
									<input type="hidden" id="" name="id" value="1">
									<div class="form-group">
										<label class="inputTit usertitle"></label>
										<input id="mobile" name="mobile" type="text"
											class="form-control inputInf" placeholder="请输入手机号"/>
										<span id="login_error_mobile" class="error fadeInDown"></span>
										</div>
										<div class="form-group">
										<label class="inputTit passwordtitle"></label>
										<input id="checkMsg" name="checkMsg" type="text" onfocus="this.type='checkMsg'"
											maxlength="20" class="form-control inputInf" placeholder="短信验证码" autocomplete="off"/>
										<span id="login_error_Msg" class="error fadeInDown"></span>
										<button id="sendCode" type="button" class="sendcode">点击发送验证码</button>
									</div>
									<div class="form-group">
										<!-- 提示信息 -->
										<div class="errMsg" id="p_message1"></div>
										<button type="button" class="btn-sendMsg" onclick="sendMessage();" style="display:none;">
											重置密码
										</button>
									</div>
									<div class="backLoginin">
										<p class="p_back">返回登录</p>
									</div>
								</form>
							</div>
						</div>
						<!--  login-over-->
					</div>
				</div>
			</div>	
		</div>

		<%-- <div class="footer"><%@include file="../system/footer.jsp" %></div> --%>
		<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery-ui.js"></script>
		<script src="<%=request.getContextPath()%>/script/loginDialog.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/jquery.cookie.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.md5.js"></script>
		<script src="<%=request.getContextPath()%>/script/login.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
		var basePath = "${ basePath }";
		var errorCode = "${ errorCode }";
		$(document).ready(function() {
			loadBranch();
		});
			//一、获取整个页面（浏览器的可视区）的宽度、高度。
			
			//二、然后获取自身DIV盒子的宽度、高度
			//三、用浏览器可视区的宽度、高度，减去DIV自身的宽度，高度，然后除以2。就可以获取到DIV距离页面的top值，left值。在赋值给DIV相应的top值，left值。
			//四、随着我们的浏览器窗口的改变，让DIV盒子也能随着页面的改变而居中。（利用onresize事件）
			
		    /*window.onload = function(){
	  		  function box(){
			    var oBox = document.getElementById('box');
			    var L1 = oBox.offsetWidth;
			    var H1 = oBox.offsetHeight;
			    var Left = (document.documentElement.clientWidth-L1)/2;
			    var top = (document.documentElement.clientHeight-H1)/2;
			    oBox.style.left = Left+'px';
			    oBox.style.top = top+'px';
			    }
			    box();
			    window.onresize = function(){
			        box();
			    } 
			}*/
		</script>
		
		<%--<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery.easydropdown.min.js"></script>
		
	--%>

	</body>
</html>
