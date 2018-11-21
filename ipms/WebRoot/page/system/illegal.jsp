<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" pageEncoding="UTF-8"%>
<head>
	<title>提示</title>
	<style>
	.info {
		color: red;
    	font-size: 20px;
    	display: flex;
   	 	justify-content: center;
    	align-items: center;
    	height: 90%;
	}
	</style>
</head>

<body>
	<div class="info">
		<span>您没有权限进行该项操作！</span>
	</div>
	<script type="text/javascript">
		function resizeWindow() {
		   var fn;
		   window.parent.JDialog.init('警告', 'error.do', 600, 300, fn, true, false);
		  //window.resizeTo(300, 200);
		  //window.moveTo(screen.availWidth / 2 - 150, screen.availHeight / 2 - 100);
		}
		resizeWindow();
	</script>
</body>