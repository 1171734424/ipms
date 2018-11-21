<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>城市新增</title>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/addCustom.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
  </head>
  <style>
  	.editCity{
  		
  		
  	}
  </style>
  <body>
 	<div style="height:394px;overflow:auto;overflow-x:hidden">
		<div>
			<ul class="editCity">
				<li class="addCustom_li">
					<label style="color:red">名称:</label>
					<input id="content" naem="content" type="text" value=""/>
				</li>
			</ul>
		</div>
		<div class="contractbutton parents-content">
			  <input class="pdepartsubmit crm-confirmbt" type="button" value="提交" onclick="submit()"/>
		
			  <input class="pdepartcancle crm-cancelbt" type="button" value="关闭" onclick="window.parent.JDialog.close()"/>
	    </div>
	</div>
	
  </body>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">var base_path = '<%=request.getContextPath()%>';</script>
	<script>
		function submit(){
			var tipsName = $("#content").val();
			if(tipsName.length > 100){
				showMsg("名称不能超过100个字符");
				return;
			}
			$.ajax({
		        url: base_path + "/addTipsContent.do",
				 type: "post",
				 data : {"content":tipsName},
				 success: function(json) {
					 if(json.result == "1"){
						 showMsg(json.message);
						 window.setTimeout("window.parent.JDialog.close()", 1800);
						 window.parent.refreshGrid();
					 }
				 },
				 error: function(json) {
					 showMsg("操作失败");
				 }
			});
		}
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
	</script>
</html>
