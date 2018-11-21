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
	<script src="<%=request.getContextPath()%>/script/crm/manage/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script src="<%=request.getContextPath()%>/script/crm/manage/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet"  media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/IMGUP.css" rel="stylesheet" type="text/css" media="all" />
	
	<style type="text/css">
		td.tdone{
			width:15%;
			text-align:center;
			vertical-align: middle;
		    border: 0 none;
		    padding: 2px;
		    white-space: nowrap;
	        text-align: right;	
		}
		td.tdtwo{
			position:relative;
			width:60%;
			text-align:center; 
		    padding-left: 15px;
		}
		#branchHeadPic,#branchId{
			width: 447px;
		    height: 36px;
		    border: 1px solid #dddddd;	
		}
		input,select{
			margin: 15px 0;
		}
		.basicInfo{
			width:100%;
			padding: 2%;
		}
		.tdthree{
		 	width: 50px;
		    text-align: left;
		}
		.tdfour{
			width: 50px;
		    margin: 0 14px;
		}
		.file{ 
		    cursor: pointer;
		    z-index: 100;
		    left: 0;
		    top: 30px;
		    width: 100px;
		    height: 32px;
		    opacity: 0;
		    filter: alpha(opacity=0);
		    margin: 0;
		    position: absolute;
		}
		.a{
	        background: #f1a43a;
		    display: block;
		    width: 87px;
		    height: 32px;
		    text-align: center;
		    line-height: 32px;
		    color: white;
		    margin-top: -11px;
		    font-size: 14px;
		    font-weight: normal;
		    border-radius: 4px;
		}
		.em{    
			margin-left: 250px;
		    width: 56px;
		    color: #999;
		    font-size: 13px;
		    margin-top: -25px;
		}
		.checkIn{	
		    padding: 5px 10px;
		    background-color: #5a5d9c;
		    border: none;
		    font-size: 12px;
		    color: #fff;
    		margin-right: -13px;
		}
		input.one{
			margin-left:-19px;
		}
		.specpic{
			margin-left:50px;
		}
		.table_basicInfo{
            height: 323px;
    		overflow-y: scroll;
    		overflow-x: hidden;
    		padding-right: 16px;
		}
		table{
			width: 100%;
		    height: 30%;
		    margin-top: 1%;
		    margin-left: -6%;
		}
		input.dele{
			background-color:#f1a43a;
		}
		.addnew{
			margin-left:15px;
		}
	</style>
  </head>
  
  <body>
 
	<form id="basicInfo" name="basicInfo" class="basicInfo" method="POST" enctype="multipart/form-data">
	  <div class="table_basicInfo">
		  <table class="">
			     <div>
			   		<p style="color:red;">注：商品分类图片最低像素为120*120,同比例增长</p>
	             </div>
		 		<tbody id="tdInfo">
		 				<tr class="basicInfo">
					 		<td class="tdone"></td>
					 		<td class="tdtwo"><input type="file" class="file" id="file" name="filename" onchange="addPicture(this,this.value,this.files)" ><a class="a">上传图片</a>
					 		<c:if test="${url != null && url != ''}"><p class="em" id="em">图标已存在</p></c:if>
					 		<c:if test="${url == null || url == ''}"><p class="em" id="em">图标未上传</p></c:if>
					 		</td>
					 		<c:if test="${url != null && url != ''}"><td class="tdthree"><input type="button" onclick="queryPicture('${url}')" class="checkIn" value="查看"/></input></td>
</c:if>
					 		<c:if test="${url == null || url == ''}"><td class="tdthree"></td>
</c:if>
				 		</tr>
		 		</tbody>
		 		<input type="hidden" name="CATEGORYID" value="${CATEGORYID}" id="CATEGORYID"/>
			</table>
		</div>
		<div class="contractbutton">
			<div class="pdepartsubmit" onclick="submitCityPics()">提交</div>
			<div class="pdepartcancle" onclick="window.parent.JDialog.close()">取消</div>
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
  	function addPicture(file,name,files){
  	//图片格式校验
	    if(name.length>1 && name != ''){
	        var lodt = name.lastIndexOf(".");
	        var type = name.substring(lodt+1);
	        if( type != "gif" &&
	        		type != "jpeg" &&
	        		type != "jpg" &&
	        		type != "png" &&
	        		type != "svg" ){
	           showMsg("请上传图片文件!");
	           file.value = "";
	            return false;
	        }else{
	        	//校验图片像素尺寸
	   /*     	var filePath = file.value;  
	      	    if(filePath){  
	      	        //读取图片数据  
	      	        var filePic = file.files[0];  
	      	        var reader = new FileReader();  
	      	        reader.onload = function (e) {  
	      	            var data = e.target.result;  
	      	            //加载图片获取图片真实宽度和高度  
	      	            var image = new Image();  
	      	            image.onload=function(){  
	      	                var width = image.width;  
	      	                var height = image.height;  
	      	                if (width == 120 && height == 120  ){  */
	      	                	$("#em").text('图标已选择');
	    /*  	                }else {  
	      	                	showMsg("文件尺寸应为：120*120！");  
	      	                    file.value = "";  
	      	                    return false;  
	      	                }  
	      	            };  
	      	            image.src= data;  
	      	        };  
	      	        reader.readAsDataURL(filePic);  
	      	    }else{  
	      	        return false;  
	      	    }  */
	        }
	    }
  		
  	}
  </script>
  <script type="text/javascript">
  
  	function queryPicture(src){
  		
  		if (src == '') {
  			showMsg("未上传图标!");
  		} else {
  			JDialog.open("查看图标", base_path + "/showImageScale.do?src=" + src, 450, 300, true);
  		}
  	}
  	
	function submitCityPics(){

	 
			 var formData = new FormData($("#basicInfo")[0]); 
			$.ajax({
				url: base_path +"/addNewCityPictureMark.do",
				 data : formData,
				 type: "post",
				 contentType: false,
		         processData: false,
				 dataType : "json",
				 success: function(json) {
					 showMsg("操作成功!");
					 window.setTimeout("window.JDialog.close();",900);
					 window.setTimeout("window.parent.location.reload(true)", 1000);
				 },
				 error: function(json) {
					 showMsg("操作失败!");
				 }
			});
			
			
			
			
	 
	  }

  </script>
</html>
