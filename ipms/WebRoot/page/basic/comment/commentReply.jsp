<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="../../common/script.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新增回复</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/probrand/probrand.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" media="all" />
    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style>
	.replyContent{
		width: 90%;
	    height: 310px;
	    vertical-align: top;
	    margin-top: -32px;
	    margin-left: 67px;
	}
</style>
  </head>
  
  <body>
    	<form id="beasform" name="beasform" class="beasform" method="POST" enctype="multipart/form-data">
			<div class="ul_div">
				<ul class="div_ul">
					<li class="div_li div_big_li info">
						<label style="width: 15%;">回复内容：</label>
						<c:if test="${reply ne null }">
						<textarea id="content" name="content" maxlength="1000" disabled class="replyContent">${reply.commentContent }</textarea>
						</c:if>
						<c:if test="${reply eq null }">
						<textarea id="contentFill" name="content" maxlength="1000" class="replyContent"></textarea>
						<input type="hidden" value="${commentId }" id="commentId">
						</c:if>
					</li>
				</ul>
			</div>
		</form>
		<div class="button_div" >
				<button class="left_button" onclick="window.parent.JDialog.close()" >关闭</button>
				<button class="right_button" onclick="addReply()">发布</button>
			</div>
			<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  </body>
  <script type="text/javascript">
  function addReply(){
	  var reply = $("#contentFill").val();
	  var commentId =  $("#commentId").val();
	  $.ajax({
	         url: "addReply.do",
			 type: "post",
			 dataType: "json",
			 data : {
				 reply : reply,
				 commentId : commentId
			 },
			 success: function(json) {
				 if (json.result == 1) {
					 showMsg("回复成功");
					 window.setTimeout("window.parent.location.reload();", 500);
					 window.setTimeout("window.parent.JDialog.close();", 510);
				}else{
					showMsg("录入失败，请检查格式！");
				}
			 }
		});
  }
//弹出的提示框
	function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg, fn);
		}
	}
  </script>
</html>
