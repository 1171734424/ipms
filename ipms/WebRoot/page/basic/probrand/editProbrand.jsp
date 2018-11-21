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
				.big_div{
				height:auto;
				overflow:auto;
				position:static;
			}
			
		/*	@media screen and (max-width:1500px){
			::-webkit-scrollbar{
				display:none;
			}
					.big_div{
						position:relative;
						height:500px;
						overflow-y:scroll;
					}
			}	*/
		</style>
	</head>
	<body>
		<div class="big_div">
			<form id="beasform" name="beasform" class="beasform" method="POST" enctype="multipart/form-data">
			<div class="ul_div">
				<ul class="div_ul">
					<li class="div_li">
						<label class="must">故事</label>
						<select id="types" name="types" maxlength="12" type="text" style="margin-left: -66px;width: 81%; height: 35px;" onchange="selectType()" disabled>
							<option value="0">
								--请选择--
							</option>
							<c:forEach items="${typeInfoList }" var="tl">
								<option value="${tl.typeName },${tl.Typeid }" <c:if test="${tl.Typeid eq proBrand.types}">selected</c:if> >
									${tl.typeName }
								</option>
							</c:forEach>
						</select>
					</li>
					<li class="div_li" id="brandType_li">
					 	<c:if test="${newBrandType ne '幕后' }">
					 		<label class="must">${newBrandType }</label>
					 	</c:if>
					 	<c:if test="${!empty city}">
						 	<input id="brandtype" name="brandtype" value="${city.adminiName }" maxlength="30" type="text" style="margin-left: -66px;width: 80%;" onclick="selectBrandTypes()" disabled/>
							<input id="brandtype_CUSTOM_VALUE" name="brandtype_CUSTOM_VALUE" value="${city.adminiCode }" type="hidden" />
						</c:if>
						<c:if test="${!empty goods}">
						 	<input id="brandtype" name="brandtype" value="${goods.goodsName }" maxlength="30" type="text" style="margin-left: -66px;width: 80%;" onclick="selectBrandTypes()" disabled/>
							<input id="brandtype_CUSTOM_VALUE" name="brandtype_CUSTOM_VALUE" value="${goods.goodsId }" type="hidden" />
						</c:if>
						<c:if test="${!empty house}">
						 	<input id="brandtype" name="brandtype" value="${house.housename }" maxlength="30" type="text" style="margin-left: -66px;width: 80%;" onclick="selectBrandTypes()" disabled/>
							<input id="brandtype_CUSTOM_VALUE" name="brandtype_CUSTOM_VALUE" value="${house.houseId }" type="hidden" />
						</c:if>
					</li>
					<li class="div_li div_li_title">
						<span class="div_label_title"><label class="must">故事主题</label></span>
						<input id="title" name="title" maxlength="30" type="text" value="${proBrand.title }" style="margin-left: -73px;width: 76%;"/>
					</li>
					
					<li class="div_li sub_title ">
						<span class="div_label_title"><label>故事备注</label></span>
						<input id="sub_title" name="sub_title" maxlength="30" type="text" value="${proBrand.subTitle }" style="margin-left: -73px;width: 76%;" onchange="subTitleInput(this)"/>

					</li>
					<li class="div_li third_title">
						<span class="div_label_title"><label>作者故事</label></span>
						<input id="third_title" name="third_title" maxlength="30" value="${proBrand.contentDesc }" type="text" style="margin-left: -73px;width: 76%;"/>

					</li>
					<li class="div_li author_title">
						<span class="div_label_title must"><label>作者</label></span>
						<input id="author" name="author" maxlength="20" type="text" value="${proBrand.author }" style="margin-left: -73px;width: 76%;"/>
					</li>
					<li class="div_li div_li_pictureId">
						<div>
							<label style="width: 45px;" >图片</label>
							<input id="left_button" width="5%" height="35px" type="button" value="添加" onclick="addDynameic(this)"/>
							<input id="pictureArray" name="pictureArray" value="" type="hidden" />
							<input id="contentId" name="contentId" value="${proBrand.contentId }" type="hidden" />
							<div id="picture_div">
								<c:forEach items="${pictureNames }" var="pictureNames">
									<input id="pictureId" name="pictureId" type="file" value="${pictureNames }"/>
									<input id="right_button" type="button" value="查看" onclick="seeDynameic(this)"/>
									<input id="right_button" type="button" value="关闭" onclick="closeDy(this)"/>
								</c:forEach>
							</div>
						</div>
					</li>
					<li class="div_li div_big_li info">
						<label style="width: 15%;" class="must">详情</label>
						<textarea id="content" name="content" maxlength="1000" style="width: 86%;height: 295px;vertical-align: top;;margin-top: -27px; margin-left: 71px">${proBrand.content }</textarea>
					</li>
				</ul>
			</div>
		</form>
			<div class="button_div" >
				<button class="left_button" onclick="window.parent.JDialog.close()" >关闭</button>
				<button class="right_button" onclick="Addknowledge()">修改</button>
			</div>
		</div>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath() %>";
			
			// 初始化加载页面信息
			$(function(){
				// 如果故事分类不为'幕后故事'，则不显示图片
				var types = $("#types").val();
				typesVal = types.substring(0,types.indexOf(","));
				if (typesVal != '幕后故事') {
					$(".div_li_pictureId").css("display","none");
				}
				// 如果二级标题没有内容，就隐藏三级标题及作者名称输入项
				var subTitle = $("#sub_title").val();
				if(subTitle.trim() == ""){
					$(".third_title").css("display","none");
					$(".author_title").css("display","none");
				}
				
			});
			/*
			 * 二级标题录入功能显示三级目录及作者
			 */
			function subTitleInput(e){
				var SecondInputVal = $(e).val();
				if(SecondInputVal.trim() != ""){
					// 显示第三标题及作者
					$(".third_title").css("display","block");
					$(".author_title").css("display","block");
				} else {
					// 清空及隐藏第三标题及作者
					$("#third_title").val("");
					$("#author").val("");
					$(".third_title").css("display","none");
					$(".author_title").css("display","none");
				}
			}
			
			/*
			 * 点击修改提交按钮
			 */
			function Addknowledge(){
				$("#types").attr("disabled",false);
				// 定义一个字段接收文件名称
				var filePic = "p";
				var flag = true;
				// 查看上传的图片个数
				var fileList = document.getElementsByName("pictureId");
				// 循环遍历上传的图片
				for (var i = 0; i < fileList.length; i++) {
					// 当图片value有值得时候 不需要读写图片名称
					if (fileList[i].value != "") {
						flag = checkFileImage(fileList[i].value);
						// 检查上传图片的格式是否正确，如果不正确则立即跳出循环
						if(!flag){
							break;
						}
					// 当图片的value为空时 将读取图片名称
					}else if ($(":file")[i].defaultValue != "") {
						// 循环添加图片
						filePic += $(":file")[i].defaultValue + ",";
						// 检查上传图片的格式是否正确，如果不正确则立即跳出循环
						flag = checkFileImage($(":file")[i].defaultValue);
						if(!flag){
							break;
						}
					}
				}
				
				// 如果上述flag为false时 则跳出提交
				if(!flag){
					return false;
				}
				// 将循环读取的数据传入隐藏对象中
				$("#pictureArray").val(filePic);
				var formData = new FormData($("#beasform")[0]);
				var title = $("#title").val();
				var types = $("#types").val();
				var subTitle = $("#sub_title").val();
				var thirdTitle = $("#third_title").val();
				var author = $("#author").val();
				var content = $("#content").val();
				if (types == "0") {
					showMsg("请选择故事！");
					return false;
				}
				// 当故事为"幕后故事"时，分类不能为空！
				if(typesVal != "幕后故事"){
					var brandtype = $("#brandtype").val();
					if (brandtype == "") {
						showMsg($("#lable_name").val()+"不能为空！");
						return false;
					}
				}
				
				if (title == "") {
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
					showMsg("作者信息最大字符不能超过30个！");
					return false;
				}
				
				if (thirdTitle.length > 40) {
					showMsg("作者故事最大字符不能超过40个！");
					return false;
				}
				
				
 
				

				
				if ($(":file").length > 0) {
					flag = changerFile();
					if(!flag){
						return false;
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
	  		         url: path + "/updateProbrandInfo.do",
	  				 contentType: false,
	  		         processData: false,
	  				 dataType : "json",
	  				 type : "post",
	  		         data: formData,
	  				 success: function(json) {
	  					if(json.result == 1){
	  						showMsg("修改成功!");
	  					 	window.setTimeout("window.JDialog.close();",900);
	  						window.setTimeout("window.parent.location.reload(true)", 1000);
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
				var kind = "";
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
				var brandType = "<label id='lable_name'>"+ kind +"</label> " +
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
	    	var count =  +("${picNo }");
			
	    	/*
		   	 *新增一个添加上传按钮
		   	 */
		    function addDynameic(but) {
	    		if (count < 4) {
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
			    	right.setAttribute("value", "关闭");
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
		   	 * 删除动态加载节点
		     */
		   function closeDynameic(but) {
				// 当我现有上传功能不少于1则可以删除
			   /*  if (count > 0) { */
			    	// 获取需要删除的节点信息（删除节点为当前对象的父类的父类节点）
					$(but).prev().remove();
					$(but).remove();
			    	// 删除后将变量自减1
				    count--;
				/* }else {
					// 否则就提示弹出框提示用户
					showMsg("已超过删除下限！");
				}    */
		    }
		    
		    /**
		   	 * 删除遍历节点
		     */
		   function closeDy(but) {
				// 当我现有上传功能不少于1则可以删除
				$(but).prev().prev().remove();
		    	$(but).prev().remove();
				$(but).remove();
		    	// 删除后将变量自减1
			    count--;
		    }
		    /**
		     * 用于限制上传文件窗口未添加文件
		     */
		    function changerFile() {
		    	var flag = true;
		    	// 获取所有上传的文件的节点
		    	var fileList = document.getElementsByName("pictureId");
		    	for (var int = 0; int < fileList.length; int++) {
					if (fileList[int].value=="" && fileList[int].defaultValue=="") {
						showMsg("第"+(int+1)+"个上传窗口未上传文件！");
						flag = false;
					}
				}
				return flag;
		    }
			
		    /*
		     * 显示预览图片信息
		     */
		    function seeDynameic(see) {
		    	var pictureId = $(see).prev()[0].defaultValue;
		    	JDialog.open("图片预览", path + "/showProbrandImage.do?pictureId="+pictureId, 280,280);
		    }
		    
		</script>
	</body>
</html>