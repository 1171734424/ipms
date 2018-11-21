<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/script.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/probrand/probrand.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css" media="all" />
	</head>
	<body>
		<div class="big_div">
		<form id="beasform" name="beasform" class="beasform" method="POST" enctype="multipart/form-data">
			<div class="ul_div">
				<ul class="div_ul">
					<li class="div_li">
						<label>图片</label>
						<input id="pictureId" name="pictureId" style="width: 80%;border: 1px;margin-left: -64px;" type="file" />
					</li>
					<input type="hidden" name="commentId" value="${comment.COMMENT_ID}"/>
					<li class="div_li">
						<label>品宣编号/名称</label>
						<select name="proBrandId" id="proBrandId" disabled>
						<c:forEach items="${listProBrand}" var = "listProBrand">
							<option value="${listProBrand.contentId }" <c:if test="${comment.CONTENT_ID eq listProBrand.contentId }">selected</c:if>>${listProBrand.contentId }/${listProBrand.title }</option>
						</c:forEach> 
						</select>
					</li> 
					<li class="div_li div_big_li info">
						<label style="width: 15%;">详情</label>
						<textarea id="content" name="content" maxlength="1000" style="width: 95%;height: 310px;vertical-align: top;margin-left: 49px;margin-top: -27px; margin-left: 50px">${comment.COMMENT_CONTENT}</textarea>
					</li>
				</ul>
			</div>
		</form>
			<div class="button_div" >
				<button class="left_button" onclick="window.parent.JDialog.close()" >关闭</button>
				<button class="right_button" onclick="updateComment()">发布</button>
			</div>
		</div>




	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>

	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
 		<script src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
		<script src="<%=request.getContextPath()%>/ueditor/ueditor.all.js"></script>
		<script src="<%=request.getContextPath()%>/ueditor/zh-cn.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath() %>";
			UE.getEditor('content');
			function updateComment(){
				/* var knowledgeInfo = window.frames["iframeInfo"].window.UE.getEditor("knowledgeInfo").getContent(); */
				var formData = new FormData($("#beasform")[0]);
				var content = UE.getEditor('content').getContent();
				
			
				/* if ($("#pictureId").val().length == 0) {
					showMsg("图片不能为空，请插入图片！");
					return false;
				}
				
				if ($("#pictureId").val().length > 0) {
					var flag = checkFileImage($("#pictureId").val());
					if(!flag){
						return false;
					}
				} */
				
			
			
				if (content == "") {
					showMsg("详情不能为空！");
					return false;
				}
				
				if (content.length < 10) {
					showMsg("详情长度不能小于10个字符");
					return false;
				}
				
				$.ajax({
			         url: "updateComment.do",
					 type: "post",
					 dataType: "json",
					 data : formData,
					 contentType : false,
				     processData : false,
					 success: function(json) {
						 if (json.result == 1) {
							 showMsg("编辑成功");
							 window.setTimeout("window.parent.location.reload();", 500);
						}else{
							showMsg("录入失败，请检查格式！");
						}
					 }
				});
				var MyTest = document.getElementById("pictureId").files[0];
			/* 	if(MyTest != undefined){
				  	var reader = new FileReader();
				  	reader.readAsDataURL(MyTest);
			 	 	reader.onload = function(theFile) {
					  	var image = new Image();
				 	 	image.src = theFile.target.result;
					  	image.onload = function(){
					  		 if(this.width > 540 || this.height > 320){
				  				showMsg("图片的最大宽度为540像素，最大高度为 320 像素!");
					  		  	return false; 
				  		 	} else { 
				  		 	
				  		 	 } 
					 	};
				 	};
				} */
			}
			
			//弹出的提示框
			function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
			
			function checkFileImage(filename){
				var flag = false;
				var arr = ["jpg","png","gif"];
				var index = filename.lastIndexOf(".");
				var ext = filename.substr(index+1);
				for(var i=0;i<arr.length;i++){
					if(ext == arr[i]){
					 flag = true; 
					 break;
					}
				}
				if(!flag){
					showMsg("上传jpg,png,gif类型图片!");
				}
				return flag;
			}
			
			function testDemo() {
				JDialog.open("民宿查询",path + "/page/probrand/MyJsp", 450,800);
			}
			
			
		</script>
	</body>
</html>