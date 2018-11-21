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
			.div_li_title {
				position: absolute;
			    margin-top: 54px;
			    margin-left: -1px;
			}
			.div_li_pictureId {
				position: absolute;
			    margin-top: 60px;
			    margin-left: 400px;
			}
			.info {
				position: absolute;
			    margin-top: 260px;
			    margin-left: -1px;
			}
			.button_div {
			    position: absolute;
			    margin-top: 525px;
			    margin-left: -296px;
			}
			#pictureId {
				width: 213px;
				border: 0;
				margin-left: -75px;
			}
			#left_button {
				height: 25px;
			    width: 40px;
			    border: 1px solid #ADADAD;
			    text-align: center;
			}
			
			#right_button {
				height: 25px;
			    width: 40px;
			    border: 1px solid #ADADAD;
			    text-align: center;
			}
			.li_pictureId {
			    position: relative;
			    margin-top: 91px;
			    margin-left: 125px;
			}
			#picture_div {
				padding-left: 125px;
			}
			.sub_title{
				position: absolute;
			    margin-top: 107px;
			    margin-left: -1px;
			}
			.third_title{
				position: absolute;
			    margin-top: 212px; 
			    margin-left: -1px;
			}
			.author_title {
				position: absolute;
			     margin-top: 160px;
			    margin-left: -1px;
			}
			.must{
			color:red;
			}
		</style>
	</head>
	<body>
		<div class="big_div">
		<form id="beasform" name="beasform" class="beasform" method="POST" enctype="multipart/form-data">
			<div class="ul_div">
				<ul class="div_ul">
					<li class="div_li">
						<label class="must">故事</label>
						<select id="types" name="types" maxlength="12" type="text" style="margin-left: -66px;width: 81%; height: 35px;" onchange="selectType()">
							<option value="0">
								--请选择--
							</option>
							<c:forEach items="${typeInfoList }" var="tl">
								<option value="${tl.typeName },${tl.Typeid }">
									${tl.typeName }
								</option>
							</c:forEach>
						</select>
					</li>
					<li class="div_li" hidden="hidden" id="brandType_li">
					</li>
					<li class="div_li div_li_title">
						<span class="div_label_title"><label class="must">故事主题</label></span>
						<input id="title" name="title" maxlength="30" type="text" style="margin-left: -73px;width: 76%;"/>

					</li>
					<li class="div_li sub_title ">
						<span class="div_label_title"><label>故事备注</label></span>
						<input id="sub_title" name="sub_title" maxlength="30" type="text" style="margin-left: -73px;width: 76%;" onchange="subTitleInput(this)"/>

					</li>
					
					<li class="div_li author_title">
						<span class="div_label_title must"><label>作者</label></span>
						<input id="author" name="author" maxlength="20" type="text" style="margin-left: -73px;width: 76%;"/>
					</li>
					<li class="div_li third_title">
						<span class="div_label_title"><label>作者故事</label></span>
						<input id="third_title" name="third_title" maxlength="30" type="text" style="margin-left: -73px;width: 76%;"/>
					</li>
					<li class="div_li div_li_pictureId">
						<div >
							<label style="width: 45px;">图片</label>
							<input id="left_button" width="5%" height="35px" type="button" value="添加" onclick="addDynameic(this)"/>
							<div id="picture_div">
							</div>
						</div>
					</li>
					
					<li class="div_li div_big_li info">
						<label style="width: 15%;" class="must">详情</label>
						<textarea id="content" name="content" maxlength="500" style="width: 86%;height: 295px;vertical-align: top;;margin-top: -27px; margin-left: 71px"></textarea>
					</li>
				</ul>
			</div>
		</form>
			<div class="button_div" >
				<button class="left_button" onclick="window.parent.JDialog.close()" >关闭</button>
				<button class="right_button" onclick="Addknowledge()">发布</button>
			</div>
		</div>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	
		<script type="text/javascript">
			var path = "<%=request.getContextPath() %>";
			
			$(function(){
				$(".div_li_pictureId").css("display","none");
				$(".third_title").css("display","none");
				$(".author_title").css("display","none");
			});
			var kind = "";
			/*
			 * 二级标题录入功能显示三级目录及作者
			 */
			function subTitleInput(e){
				var SecondInputVal = $(e).val();
				if(SecondInputVal.trim() != ""){
					// 显示第三标题及作者
					
					$(".author_title").css("display","block");
					$(".third_title").css("display","block");
				} else {
					// 清空及隐藏第三标题及作者
					$("#third_title").val("");
					$("#author").val("");
					
					$(".author_title").css("display","none");
					$(".third_title").css("display","none");
				}
			}
			
			/*
			 * 提交添加功能
			 */
			function Addknowledge(){
				var formData = new FormData($("#beasform")[0]);
				var title = $("#title").val();
				var subTitle = $("#sub_title").val();
				var thirdTitle = $("#third_title").val();
				var author = $("#author").val();
				var pictureId = $("#pictureId").val();
				var types = $("#types").val();
				typesVal = types.substring(0,types.indexOf(","));
				var content = $("#content").val();
				if (types == "0") {
					showMsg("请选择故事！");
					return false;
				}else{
					if($("#brandType_li input").length > 0){
						if( ($("#brandtype_CUSTOM_VALUE").val()== '' || $("#brandtype_CUSTOM_VALUE").val() == undefined)){
							showMsg("请选择"+$("#lable_name").text());
							return false;
						}
					}
					
				}
				
				if (title.trim() == "") {
					showMsg("故事主题不能为空！");
					return false;
				}
				if (title.length < 3) {
					showMsg("故事主题长度不能小于3个字符！");
					return false;
				} 
				
				if (title.length > 30) {
					showMsg("故事主题长度不能大于30个字符！");
					return false;
				} 
			
				if(subTitle.trim() != ""){
					if (subTitle.length > 40) {
						showMsg("故事备注最大字符不能超过40个！");
						return false;
					}
					
					if(author == "") {
						showMsg("请填写作者信息！");
						return false;
					}
				} 
				
				if (author.length > 20) {
					showMsg("作者署名最大字符不能超过20个！");
					return false;
				}
				
				if (thirdTitle.length > 40) {
					showMsg("个人故事最大字符不能超过40个！");
					return false;
				}
				

				
				// 当动态添加的图片输入框大于1 时才需要判断图片信息
				if (count > 1) {
					if (pictureId.length > 0) {
						var flag = checkFileImage($("#pictureId").val());
						flag = changerFile();
						if(!flag){
							return false;
						}
					}
				}
				
				

				
				
				if (content == "") {
					showMsg("详情不能为空！");
					return false;
				}
				
				if (content.length < 10) {
					showMsg("详情长度不能小于10个字符");
					return false;
				}
				
				var getFormatCode= content.replace(/\r\n/g, '<br/>').replace(/\n/g, '<br/>').replace(/\s/g, ' ');
			    
				formData.append("formatCode",getFormatCode);
	  			$.ajax({
	  		         url: path + "/saveProbrandInfo.do",
	  				 contentType: false,
	  		         processData: false,
	  				 dataType : "json",
	  				 type : "post",
	  		         data: formData,
	  				 success: function(json) {
	  					if(json.result == 1){
	  						showMsg("保存成功!");
	  					 	window.setTimeout("window.JDialog.close();",900);
	  						window.setTimeout("window.parent.location.reload(true)", 1000);
	  					} else if(json.result == 2){
	  						
	  						showMsg("您选择的"+ kind +"故事已存在！");
	  					} else {
	  						showMsg("数据异常，请检查格式！");
	  					}
	  				 },
	  				 error: function(json) {}
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
			/*
			 * 验证图片格式
			 */
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
			
			/*
			 * 当select框修改后的操作
			 */
			function selectType(){
				// 定义局部变量
				
				// 获取选中数据
				var typesAll = $("#types").val();
				types = typesAll.substring(0,typesAll.indexOf(","));
				// 分别判断是选中数据为
				if(types == '城市故事'){
					kind = "城市";
					$(".div_li_pictureId").css("display","none");
				}
				if(types == '民宿故事'){
					kind = "民宿";
					$(".div_li_pictureId").css("display","none");
				}
				if(types == '商品故事'){
					kind = "商品";
					$(".div_li_pictureId").css("display","none");
				}
				if(types == '景点故事'){
					kind = "景点";
					$(".div_li_pictureId").css("display","none");
				}
				if(types == '幕后故事'){
					$(".div_li_pictureId").css("display","block");
				}
				// 动态加载input框
				var brandType = "<label id='lable_name' class='must'>"+ kind +"</label> " +
				"<input id='brandtype' name='brandtype' maxlength='30' type='text' style='margin-left: -66px;width: 80%;' onclick='selectBrandTypes()'/>" +
				"<input id='brandtype_CUSTOM_VALUE' name='brandtype_CUSTOM_VALUE' type='hidden' />";
				
				// 添加input并将其样式修改为可见状态
				$("#brandType_li").empty();
				$("#brandType_li").append(brandType);
				$("#brandType_li").css("display","block");
				
				// 如果类别等于幕后故事 则清空所有input框
				if (types == '幕后故事') {
					$("#brandType_li").empty();
				} 
				if (typesAll == '0') {
					$("#brandType_li").empty();
				} 
			}
			
			/*
			 * 点击故事类别框后触发事件，弹出查询页面
			 */
			function selectBrandTypes() {
				// 获取参数
				var types = $("#types").val();
				types = types.substring(0,types.indexOf(","));
				// 截取接收的参数并拼接为想要的字段用于显示弹出框的标题
				var newTypes = "选择" + types.substring(0,types.indexOf("故事"));
				// 跳转到对应的弹出页面
				JDialog.open(newTypes, path + "/selectBrandTypes.do?types="+types, 450,350);
			}
			
			// 定义一个全局变量，用于限制删除条件
	    	var count = 1;
	    	/*
		   	 *新增一个添加上传按钮
		   	 */
		    function addDynameic(but) {
	    		if (count < 5) {
			    	// 创建li节点
			    	var li = document.createElement("li");
						li.setAttribute("class", "div_li div_li_pictureId li_pictureId");
			    	// 创建input节点，并增加属性
			    	// 1.上传功能
			    	var finput = document.createElement("input");
			    	finput.setAttribute("type", "file");
			    	finput.setAttribute("name", "pictureId");
			    	finput.setAttribute("id", "pictureId");
			    	// 2.关闭功能
			    	var right = document.createElement("input");
			    	right.setAttribute("id", "right_button");
			    	right.setAttribute("value", "删除");
			    	right.setAttribute("onclick", "closeDynameic(this)");
			    	// 获取tbody对象
			    	var picture = $("#picture_div");
			    	picture.append(finput);
			    	picture.append(right);
			    	// 增加后将变量自加1
			    	count ++;
	    		} else {
	    			// 否则就提示弹出框提示用户
					showMsg("已超过添加上线限！");
	    		}
		   }
		    
		    /**
		   	 * 删除节点
		     */
		   function closeDynameic(but) {
				// 当我现有上传功能不少于1则可以删除
			    if (count > 1) {
			    	// 获取需要删除的节点信息（删除节点为当前对象的父类的父类节点）
					$(but).prev().remove();
					$(but).remove();
			    	// 删除后将变量自减1
				    count--;
				}else {
					// 否则就提示弹出框提示用户
					showMsg("已超过删除下限！");
				}   
		    }
		    /**
		     * 用于限制上传文件窗口未添加文件
		     */
		    function changerFile() {
		    	var flag = true;
		    	// 获取所有上传的文件的节点
		    	var fileList = document.getElementsByName("pictureId");
		    	for (var int = 0; int < fileList.length; int++) {
					if (fileList[int].value=="") {
						showMsg("第"+(int+1)+"个上传窗口未上传文件！");
						flag = false;
					}
				}
				return flag;
		    }
			
		    
		</script>
	</body>
</html>