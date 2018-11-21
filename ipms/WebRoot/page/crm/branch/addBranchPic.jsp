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
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/IMGUP.css" rel="stylesheet" type="text/css" media="all" />
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
			width:70%;
			text-align:left;
			float:left;
		    margin-left: 23px;
		}
		#branchHeadPic,#branchId{
			width: 447px;
		    height: 36px;
		    border: 1px solid #dddddd;	
		}
		input,select,flie{
			margin: 15px 0;
		}
		.basicInfo{
			width:100%;
		}
		.tdthree{
		 	width: 50px;
		    text-align: left;
		    float: left;
		}
		.tdfour{
			width: 50px;
		    text-align: left;
		    float: left;
		}
		.file{ 
			cursor:pointer;
			z-index:100;
			left:0;
			top:0;
			width: 200px;
			height: 32px;
			opacity:0;
			filter:alpha(opacity=0);
			margin: 0;
		}
		.checkIn{	
		    padding: 6px 16px;
		    background-color: #f1a43a;
		    border: none;
		    font-size: 12px;
		    color: #fff;
		    border-radius: 4px;
		}
		input.one{
			background-color:#5A5D9C;
			margin-left:-19px;
		}
		.specpic{
			margin-left:50px;
		}
		.table_basicInfo{
			height: 324px;
    		overflow-y: scroll;
		}
	</style>
  </head>
  
  <body>
 
 <%--  <input name="branchId" value="${branchId}" type="text"/> --%>
  <form id="basicInfo" name="basicInfo" class="basicInfo"  method="POST" enctype="multipart/form-data">
  <div class="table_basicInfo">
	<table>
		<tr>
			<td class="tdone">门店头图</td>
			<td class="tdtwo"><input id="branchHeadPic" name="branchHeadPic" type="file" placeholder="请选择图片!" name="roomTypePic"></input></td>
		</tr>
		<tr>
			<td class="tdone">门店内景</td>
			<td class="tdtwo"><input id="div_imgfile" type="button" value="选择图片"></input></td>
		</tr>
		<input type="hidden" name="branchId" value="${branchId}" id="branchId" />
		<!--图片预览容器-->
		<tr>
			<td colspan="2">
				<div id="div_imglook">
					<div style="clear: both;"></div>
				</div>
			</td>
		</tr>
	</table>
</div>
<div class="contractbutton">
	<div class="pdepartsubmit" onclick="submitRoomType()">提交</div>
	<div class="pdepartcancle" onclick="window.parent.JDialog.close()">取消</div>
</div>
</form>
	<%-- <script src="<%=request.getContextPath()%>/script/commitBranchPic.js"></script> --%>
  </body>
  <script type="text/javascript">
  var base_path = '<%=request.getContextPath()%>';
 // var IMG_AJAXPATH = base_path+"/submitBranchPics.do";//异步传输服务端位置
 
 function submitRoomType(id){
//	showMsg(branchId);
	var formData = new FormData($("#basicInfo")[0]); 
	var branchHeadPic=$("#branchHeadPic").val();
	var branchId=$("#branchId").val();
	/*//添加房型内景图片
	if ($(".lookimg").length <= 0) {
		showMsg("还未选择需要上传的内景图片");
        return;
    }

    //全部图片上传完毕限制
    if ($(".lookimg[ISUP=1]").length == $(".lookimg").length) {
    	showMsg("图片已全部上传完毕！");
        return;
    }*/

  //  upload(0,id);
    
	/* if(branchHeadPic==""){
		
	}
	else{ */
		$.ajax({
			url: base_path+"/addBranchHeadPicData.do",
			type: "post",
			dataType: "json",
			data: formData,
			contentType: false,
	        processData: false,
			success: function(json) {
			},
			error: function(){
				}
			});
	//}
	showMsg("添加成功！");
	window.setTimeout("window.parent.location.reload(true)", 1000);
	window.setTimeout("window.parent.JDialog.close();",1000);
	
		      
}

  </script>
</html>
