<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
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
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" media="all" />
	<style type="text/css">
		input {
		    height: 35px;
		    width: 35%;
		    margin-left: 15px;
		    margin-right: 25px;
		    border: 1px solid #ADADAD;
		}
		#pictureId {
			width: 35%;
	    	border: 1px;
	    	margin-left: 15px;
		}
		
		.securityInput {
			margin-left: 0px;
		}
		
		.first_divM {
			margin-left: 45px;
		}
		.button_div {
		    height: 11%;
		    width: 800px;
		    padding: 85px 0;
		    margin-left: -300px;
	        position: absolute;
		}
		#commentContent {
			position: absolute;
		    width: 80%;
		    margin-left: 21px;
		    height: 135px;
		}
	</style>
	</head>
	<body>
		<div class="big_div">
		<form id="beasform" name="beasform" class="beasform" method="POST" enctype="multipart/form-data">
			<div class="ul_div">
				<div>
					<label style="color:red">民宿</label>
					<input class="first_divM" name="brandtype" id = "brandtype" onclick="toChooseHouse()"  placeholder="民宿选择">
					<input id="brandtype_CUSTOM_VALUE" name="brandtype_CUSTOM_VALUE"  type="hidden"/>
					<label>图片</label>
					<input class="first_divM" id="pictureId" name="pictureId" type="file" />
				</div>
				<div>
					<label style="color:red">卫生评分</label>
					<input name="serviceScore" id = "serviceScore" maxlength="3" onkeyup="value=value.replace(/^[^0,1,2,3,4,5]{1}$/g,'')" placeholder="请输入0-5的数字" />
					<label>卫生评论</label>
					<input name="serviceComment" id = "serviceComment" maxlength="100" placeholder="卫生评论" />
				</div>
				<div style="margin-top: 15px; margin-bottom: 18px;">
					<label style="color:red">设施评分</label>
					<input name="facilityScore" id = "facilityScore" maxlength="3" onkeyup="value=value.replace(/^[^0,1,2,3,4,5]{1}$/g,'')" placeholder="请输入0-5的数字"/>
					<label>设施评论</label>
					<input name="facilityComment" id = "facilityComment" maxlength="100" placeholder="设备评论" />
				</div>
				<div style="margin-top: 18px; margin-bottom: 18px;">
					<label style="color:red">安全感评分</label>
					<input class="securityInput" name="securityScore" id = "securityScore" maxlength="3" onkeyup="value=value.replace(/^[^0,1,2,3,4,5]{1}$/g,'')" placeholder="请输入0-5的数字" />
					<label>安全感评论</label>
					<input class="securityInput" name="securityComment" id = "securityComment" maxlength="100" placeholder="安全感评论" />
				</div>
				<div style="margin-top: 18px; margin-bottom: 18px;">
					<label style="color:red">评论人</label>
					<input name="recorduser" id = "recorduser"  style="margin-left: 32px;"  placeholder="评论人" maxlength="3"/>
				</div>
				<div>
					<label>评论描述</label>
					<textarea maxlength="100" name="commentContent" id = "commentContent" placeholder="评论描述有效字符在100以内"></textarea>
				</div>
				<!-- <div>
					<label>快捷标签</label>
					<input name="shortcutName" id = "shortcutName" onclick="toShortcutName()" style="margin-left: 17px;"  placeholder="快捷标签选择"/>
					<input id="shortcutName_CUSTOM_VALUE" name="shortcutName_CUSTOM_VALUE"  type="hidden"/>
				</div> -->
			</div>
		</form>
			<div class="button_div" >
				<button class="left_button" onclick="window.parent.JDialog.close()" >关闭</button>
				<button class="right_button" onclick="addComment()">发布</button>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath() %>";
			
			/*
			 * 提交保存按钮
			 */
			function addComment(){
				var formData = new FormData($("#beasform")[0]);
				var brandtype = $("#brandtype").val();
				var facilityScore = $("#facilityScore").val();
				var facilityComment = $("#facilityComment").val();
				var serviceScore = $("#serviceScore").val();
				var serviceComment = $("#serviceComment").val();
				var securityScore = $("#securityScore").val();
				var securityComment = $("#securityComment").val();
				var pictureId = $("#pictureId").val();
				var recorduser = $("#recorduser").val();
				if(recorduser.length == 0){
					showMsg("请填写评论人!");
					return false;
				}
				if (brandtype.length == 0) {
					showMsg("民宿为必填项！");  
					return false;
				}
				
				/* if (pictureId.length == 0) {
					showMsg("图片不能为空，请插入图片！");
					return false;
				}*/
				
				if (pictureId.length > 0) {
					var flag = checkFileImage($("#pictureId").val());
					if(!flag){
						return false;
					}
				} 
				
				if (!verifyScore(serviceScore,"卫生")) {
					return false;
				}
				
				if (!verifyScore(facilityScore,"设施")) {
					return false;
				}
				if (!verifyScore(securityScore,"安全感")) {
					return false;
				}
				
				/* if (serviceComment.length == 0) {
					showMsg("卫生评论为必填项！");  
					return false;
				}
				
				if (facilityComment.length == 0) {
					showMsg("设备评论为必填项！");  
					return false;
				}
			
				if (securityComment.length == 0) {
					showMsg("安全感评论为必填项！");  
					return false;
				} */
				
  		 		$.ajax({
			         url: "addOrderComment.do",
					 type: "post",
					 dataType: "json",
					 data : formData,
					 contentType : false,
				     processData : false,
					 success: function(json) {
						 if (json.result == 1) {
							 showMsg("新增成功");
							 window.setTimeout("window.parent.location.reload();", 500);
						}else{
							showMsg("录入失败，请检查格式！");
						}
					 }
				});
			}
			
			/*
			 * 验证评分规则
			 * Score:对应评分
			 * ScoreStr：提示字符	 
			 */
			function verifyScore(Score,ScoreStr){
				var flag = true;
				if(isRealNum(Score)){
					if (Score > 5 || Score < 0) {
						showMsg(ScoreStr + "评分必须大于0分小于5分！");  
						flag = false;
					} else {
						if (Score % 0.5 != 0) {
							showMsg(ScoreStr + "评分必须为0.5的倍数！"); 
							flag = false;
						}
					}
				} else {
					showMsg(ScoreStr + "评分必须数字！");  
					flag = false;
				}
				return flag;
			}
			
			/*
			 * isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
			 */
			function isRealNum(val){
			   
			    if(val === "" || val ==null){
			        return false;
			    }
			    if(!isNaN(val)){
			        return true;
			    }else{
			        return false;
			    }
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
			
			function toShortcutName(){
				JDialog.open("快捷标签选择", path + "/selectToShortcutName.do?", 450,350);
			}
			
			function toChooseHouse(){
				JDialog.open("民宿选择", path + "/selectHouseOnAddComment.do?types=民宿", 450,350);
			}
			
		</script>
	</body>
</html>