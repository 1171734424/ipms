<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%String a = (String)request.getParameter("billId"); %>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>账单备注</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
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
<%-- 			       		   <input type="hidden" name="billId" id="billId" value="<%=a %>">
 --%>		       		   </form>
	    		 </div>
			</div>
		</section>
			<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
			<script type="text/javascript">
			var path = "<%=request.getContextPath() %>";
			var remark = "<%=request.getParameter("remark") %>";
			var id= "<%=request.getParameter("id") %>";
			var data= "<%=request.getParameter("data") %>";
				$(document).ready(function(){
					$("#reamrk").focus().val(remark);
				})
				/*确认刷新父页面并将数据保存至后台*/
				function submitRemark(){
					var remark = $("#reamrk").val();
					if(remark.length > 200){
						showMsg("备注提示长度不能超过100个!");
						return false;
					}
					$.ajax({
				         url: path + "/addRemarkToOrder.do",
						 type: "post",
						 data : {id : id,
						 		remark : remark,
						 		data : data
						 },
						 success: function(json) {
						 	if(json.result == "1"){//修改成功
								 //$(window.parent.document).find(".tab_two").click();
						 		window.setTimeout("window.parent.location.reload(true)",1000);
								//window.parent.JDialog.close();	
						 	}
						 },
						 error: function(json) {}
					});
				}
			function refresh(){
			   // $(window.parent.document).find(".tab_four").click();
				//window.parent.JDialog.close();
			}
			
			</script>
  </body>
</html>
