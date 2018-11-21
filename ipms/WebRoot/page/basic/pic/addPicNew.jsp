<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addBranchPic.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">	
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/picadd/webuploader.min.css" rel="stylesheet" type="text/css" media="all" />
	<script src="<%=request.getContextPath()%>/script/crm/manage/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/picAdd/webuploader.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/picAdd/uploadImgPreview.min.js"></script>
	
	<style type="text/css">
		._filelist li{/*这个选择器一定要有宽高，否则图片显示不出来，因为它里面的内容是绝对定位的*/
                width: 180px;
                height: 160px;
            }
            .choose-file-btn{
                width: 180px;
                height: 80px;
                background-color: #f60;
                overflow: hidden;
            }
            .basicInfo{
            	overflow: auto;
    			height: 100%;
            }
	</style>
  </head>
  
  <body>
 
	<form id="basicInfo" name="basicInfo" class="basicInfo" >
	<input type="hidden" name="branchId" value="${branchId}" id="branchId" />
	  	<div class="table_basicInfo">
		  	<div id="uploader2"></div>
	       	<div class="choose-file-btn" id="choose_file2">选择图片</div>
	        <button type="button" id="upload_now2">上传图片</button>
		</div>
		<div>
		  	<div class="filled">
			  <ul class="_filelist">
			  	<c:forEach items="${pictures}" var="p">
				    <li id="${p.pic_id}" class="state-complete dataDel">
				      <img src="${p.pic_url}" style="position: absolute; top: 50%; left: 50%; height:120px; width:240px; margin-top: -60px; margin-left: -120px;"></p>    
				      <div class="file-panel filedata" style="overflow: hidden; height: 0px;"><span class="cancel" onclick="deletePic(this,'${p.pic_id}')">删除</span></div> 
				    </li>
			    </c:forEach>
			  </ul>
		    </div>
		</div>
	
	</form>
  </body>
  <script type="text/javascript">
	var base_path = '<%=request.getContextPath()%>';
	function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg, fn);
		}
	}
  </script>
  <script type="text/javascript">
  $(function (){
	  $(".dataDel").mouseover(function(e){
		 $(this).find(".filedata").css("height","30px");
	  });
	  $(".dataDel").mouseout(function(e){
			 $(this).find(".filedata").css("height","0px");
	  });

      var uploader2 = uploadImage({
           wrap: "#uploader2", //包裹整个上传控件的元素，必须。可以传递dom元素、选择器、jQuery对象
           /*预览图片的宽度，可以不传，如果宽高都不传则为包裹预览图的元素宽度，高度自动计算*/
           //width: "160px", 
           height: 120,//预览图片的高度
           fileVal: "file", // [默认值：'file'] 设置文件上传域的name。
           btns: {//必须
               uploadBtn: $("#upload_now2"), //开始上传按钮，必须。可以传递dom元素、选择器、jQuery对象
               retryBtn: null, //用户自定义"重新上传"按钮
               chooseBtn: '#choose_file2',//"选折图片"按钮，必须。可以传递dom元素、选择器、jQuery对象
               chooseBtnText: "选择图片" //选择文件按钮显示的文字
           },
           pictureOnly: true,//只能上传图片
           resize: false,
           //是否可以重复上传，即上传一张图片后还可以再次上传。默认是不可以的，false为不可以，true为可以
           duplicate: true,
           multiple: true, //是否支持多选能力
           swf: "http://127.0.0.1:8080/ipms/Uploader.swf", //swf文件路径，必须
           url: base_path+"/addBranchHeadPic.do?branchId="+$("#branchId").val(), //图片上传的路径，必须
           maxFileTotalSize: null, //最大上传文件大小，默认10M
           maxFileSize: 50485760, //单个文件最大大小，默认2M
           showToolBtn: true, //当鼠标放在图片上时是否显示工具按钮,
           onFileAdd: null, //当有图片进来后所处理函数
           onDelete: null, //当预览图销毁时所处理函数
           /*当单个文件上传失败时执行的函数，会传入当前显示图片的包裹元素，以便用户操作这个元素*/
           uploadFailTip: null, 
           onError:null, //上传出错时执行的函数
           notSupport: null, //当浏览器不支持该插件时所执行的函数
           /*当上传成功（此处的上传成功指的是上传图片请求成功，并非图片真正上传到服务器）后所执行的函数，会传入当前状态及上一个状态*/
           onSuccess: null
       });
   });
  </script>
  <script type="text/javascript">
  	function addBranchHeadPicData(){
  		var formData = document.getElementById("tfile").files[0];
  		var text = $("#tfile").next().next().text();
  		if(formData == undefined && text == '未上传'){
  			showMsg("插入图片不可为空");
  			return false;
  		} else if (formData == undefined && text == '已存在') {
  			submitBranchPics();
  		} else if (formData == undefined && text == '已选择') {
  			showMsg("插入图片不可为空");
  			return false;
  		} else {
  			var form = new FormData();
  	        form.append("file", formData);
  	        form.append("branchId", $("#branchId").val());
  			$.ajax({
  		         url: base_path + "/addBranchHeadPicDataTwo.do",
  		         data: form,
  				 contentType: false,
  		         processData: false,
  				 dataType : "json",
  				 type : "post",
  				 success: function(json) {
  					var thisTable = document.getElementById("tdInfo").rows;
  					if(thisTable.length <= 0){
  						showMsg("保存成功!");
  					 	window.setTimeout("window.JDialog.close();",900);
  						window.setTimeout("window.location.reload(true)", 1000);
  					} else {
  						submitBranchPics();
  					}
  				 },
  				 error: function(json) {}
  			});
  		}
  	}
  	function submitBranchPics(){
 
  		var thisTable = document.getElementById("tdInfo").rows;
  		if(thisTable.length > 0){
  			for(var i = 0; i < thisTable.length; i++){
  	 			var id = thisTable[i].cells[1].getElementsByTagName("input")[0].id;
  	 			var formData = document.getElementById(id).files[0];
  	 			var textId = thisTable[i].cells[1].getElementsByTagName("p")[0].id;
  	 			var text = $("#"+textId).text();
  	 	  		if(formData == undefined && text == '已存在'){
	  	 	  		if(i + 1 == thisTable.length){
						showMsg("添加成功!");
						window.setTimeout("window.JDialog.close();",900);
						window.setTimeout("window.location.reload(true)", 1000);
					}
  					continue;
  	 	  		} else if (formData == undefined && text == '') {
	  	 	  		if(i + 1 == thisTable.length){
						showMsg("添加成功!");
						window.setTimeout("window.JDialog.close();",900);
						window.setTimeout("window.location.reload(true)", 1000);
					}
  	 	  			continue;
  	 	  		} else if (formData == undefined && text == '未上传') {
	  	 	  		if(i + 1 == thisTable.length){
						showMsg("添加成功!");
						window.setTimeout("window.JDialog.close();",900);
						window.setTimeout("window.location.reload(true)", 1000);
					}
  	 	  			continue;
  	 	  		} else {
  	 	  			var form = new FormData();
  	 	  			form.append("file", formData);
  	 	 	        form.append("branchId", $("#branchId").val());
  	 	 	        form.append("index",i);
  	 	 			$.ajax({
  	 	 		        url: base_path + "/submitBranchPicsTwo.do",
  	 	 		        data: form,
  	 	 				contentType: false,
  	 	 		        processData: false,
  	 	 		        async : false,
  	 	 				dataType : "json",
  	 	 				type : "post",
  	 	 				success: function(json) {
  	 	 					if(i + 1 == thisTable.length){
  	  							 showMsg("添加成功!");
  	 	 	 				 	 window.setTimeout("window.parent.JDialog.close();",900);
  	 	 	 					 window.setTimeout("window.parent.location.reload(true)", 1000);
  	 	 					 }
  	 	 				},
  	 	 				error: function(json) {}
  	 	 			});
  	 	  		}
  	        }
  		} else {
  			showMsg("保存成功!");
  		}
  	}
  	
  	function reload(){
  		window.location.href = base_path + "/addBranchPic.do?branchId="+$("#branchId").val();
  	}
  	

	function deletePic(e, pictureId){
  		$(e).parent().parent().remove();
  		$.ajax({
	         url: base_path + "/delPicsTwo.do",
	         data: {
	        	 pictureId : pictureId,
	        	 branchId : $("#branchId").val()
	         },
			 dataType : "json",
			 type : "post",
			 success: function(json) {
				window.location.reload(true);
			 },
			 error: function(json) {}
		});
	}
	
  </script>
</html>
