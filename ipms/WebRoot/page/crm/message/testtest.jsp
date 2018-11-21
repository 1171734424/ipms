<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/ipms/addSmsTemplates.css" rel="stylesheet" type="text/css" />
		
		 <script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
		 <title>ddddd </title>
	</head>
<body>
<form id="myForm">
<div class="button div_button confirm" onclick="test();">
						<span>确认</span>
					</div>
					</form>
</body>
<script>
var base_path = '<%=request.getContextPath()%>';

/* function dd(){
//alert("dddd")
} */
function test(){
/* alert("ddddddddddddddddd") */
$.ajax({	
			url : base_path + '/ggggg.do',
			dataType : "json",
			type : "post",
			data : $("#myForm").serialize(),
			success : function(json){
				if(json.result == 1){
				/* alert("ddddsss") */
					
					window.parent.JDialog.close();
				}else{
					
					window.setTimeout("window.parent.location.reload(true)", 1000);
				}
			},
			error:function(json){
				
				window.setTimeout("window.parent.location.reload(true)", 1000);
			}
		})	
		}
</script>
</html>