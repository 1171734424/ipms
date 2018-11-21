<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>系统初始化设置</title>
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bwizard.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	</head>
	<body>
		<div class="well" style="height:540px;">
			<div id="step1" role="tabpanel" class="bwizard-activated" aria-hidden="false">
				<p>
					亲爱的用户您好！
				</p>
				<p>
					初次登录系统需要将相关基本数据录入，方可进行正常的营业活动，该流程将指导您进行相应的操作，详细基础资料录入请去基础资料后台管理系统的相关模块录入！
				</p>
				<p style="color:red;">
					基本步骤为：门店管理 →参数设置 →房型管理 →房间管理 →导向完成  
				</p>
				<p>
					1)当前门店的录入是总店录入信息，并设置当前账号${loginInfo.staff.loginName}，具有初始化基础资料权限，<span style="color:red;">但此账号仅用于此导向流程录入基础资料信息，不可用于正常营业时的业务操作！</span>
				</p>
				<p>
					2)参数设置根据当前门店类型来决定是设置酒店账户，还是设置保洁参数！
				</p>
				<p>
					3)房型管理，若之前已设置过，该流程中只展示，反之将有页面进行录入房型数据，待以后流程使用！
				</p>
				<p>
					4)房价申请通过点击房价初始化按钮进行录入房型价格数据，待以后流程使用！
				</p>
				<br>
				<span>当前登录：</span><span style="color:red;">${loginInfo.staff.loginName}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>权限：</span><span style="color:red;">初始化管理员</span>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/initialData/jquery.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/initialData/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/initialData/initialPage.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
		 var base_path ="<%=request.getContextPath()%>";
		 $(document).ready(function(){
			 
		 });
		</script>
	</body>
</html>

