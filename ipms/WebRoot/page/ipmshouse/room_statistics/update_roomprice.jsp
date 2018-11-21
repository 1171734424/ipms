<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改房价</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/chummageplan.css"/>

  </head>
  
  <body style="background:#fff;">
  	<form id="roomprice_data">
	  	<ul class="clearfix">
	  		<li><label class="label_name">房价码：</label><input name="rpid" type="text" value="" id="rpid" class="text_edit"></li>
		  	<li><label class="label_name">房价：</label><input id="roomprice" name="roomprice" type="text" class="text_edit"/></li>
		  	<li class="li_mark"><label class="label_name">备注：</label><textarea id="remark" name="remark" type="text" class="text_edit mark"></textarea></li>
	  		<li><button onclick="window.parent.JDialog.close();" class="button_margin cancel">取消</button>
	  			<button onclick="submitupdateroomprice()" class="button_margin">确定</button>
	  		</li>
	  	</ul>
  	</form>
  	
  	
  	<script type="text/javascript">
  		function submitupdateroomprice(){
			$.ajax({
		         url: path + "/submitupdateroomprice.do",
				 type: "post",
				 data : $("#roomprice_data").serialize(),
				 success: function(json) {
					 loadroombilldata(json);
				 },
				 error: function(json) {}
			});
  		}
  	</script>
  	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
</html>
