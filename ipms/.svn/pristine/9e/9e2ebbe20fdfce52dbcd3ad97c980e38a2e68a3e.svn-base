<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%String checkid = (String)request.getParameter("checkId"); %>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>退房备注</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/pageloading.css"/>
  </head>
  
  <body>
		<section class="detail_six add_pro">
			<div class="high_header">
					<div class="margintop">
						<form id="bill_date">
							<ul class="clearfix">
							  <li><label class="label_name">备注：</label><textarea id="reamrk" name="content" spellcheck="false" maxlength="100" ></textarea></li>
						      <li onclick="submitRemark()"><span role="button" style="margin-right: 6px;"class="button confirm" >保存</span></li>
			       		   </ul>
 		       		   </form>
	    		 </div>
			</div>
		</section>
			<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/pageloading.js"></script>
			<script type="text/javascript">
			var path = "<%=request.getContextPath() %>";
			var checkid= "<%=request.getParameter("checkId") %>";
			var remark;
				$(document).ready(function(){
					$("#reamrk").focus().val("");
				})
				/*确认刷新父页面并将数据保存至后台*/
				function submitRemark(){
					remark = $("#reamrk").val();
					if(remark.length > 50){
						showMsg("备注提示长度不能超过100个!");
						return false;
					}
					roomout();
				}
				function roomout() {
					PageLoading.beginPageLoading("用户退房中,请稍后...");
				
					$.ajax({
				         url: path + "/roomout.do",
						 type: "post",
						 data : {
						 	checkid : checkid,
						 	remark  : remark
						 },
						 success: function(json) {
						 	if(json.result == 1){
						 		if(window.parent.pagea == -1){
									$(window.parent.parent.document).find("#menu_904").click();
									window.parent.parent.JDialog.close();
								}else {
									$(window.parent.parent.parent.document).find("#contentFrame_first").click();
									window.parent.parent.JDialog.close();
								}
						 	}
						 },
						 error: function(json) {
							window.setTimeout("PageLoading.endPageLoading()", 2000);
						 }
					});
					
				}
			
			</script>
  </body>
</html>
