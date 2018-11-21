<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>系统初始化结束</title>
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bwizard.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	</head>
	<body>
		<div class="well" style="height:540px;">
			<div id="step1" role="tabpanel" class="bwizard-activated" aria-hidden="false">
				<input type="hidden" id="branchSubType" name="branchSubType" value="${branchSubType}"/>
				<p>
					亲爱的用户您好！
				</p>
				<p>
					初始化参数设置已完成，此处只是简单引导设置流程，更详细的基础资料的录入，请与基础资料管理系统处录入!
				</p>
				<br>
				<span>当前登录：</span><span style="color:red;">${loginInfo.staff.staffName}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>权限：</span><span style="color:red;">初始化管理员</span>
				<br>
				<br>
				<br>
				<br>
				<br>
				<p>
					此账户在此流程设置完成之后将不可登陆系统!
				</p>
				<button id="buttonId" type="button" class="btn btn-large btn-info"  style="margin-left: 45%;margin-top: 10%;" onclick = "turnToMainSysTem();">进入系统</button>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/initialData/jquery.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/initialData/bootstrap.min.js"></script>
		<!--<script src="<%=request.getContextPath()%>/script/initial/bwizard.min.js"></script>-->
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/initialData/initialPage.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
		 var base_path ="<%=request.getContextPath()%>";
		   $(document).ready(function(){
		   	//$("#wizard").bwizard();
		   
		   });

		   function turnToMainSysTem(){
			   $.ajax({
   				url: base_path+"/updateInitialState.do",
   				type: "post",
   				success: function(json) {	
   					window.parent.location.href = base_path+"/page/ipmspage/login/login.jsp";
   				},
   				error: function() {
   				}
   			});
				
			}
		</script>
	</body>
</html>

