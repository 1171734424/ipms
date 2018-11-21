<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加房型图片</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=request.getContextPath()%>/script/crm/manage/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/crm/manage/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/IMGUP.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" media="all" />
	<style>
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
			width:60%;
			text-align:left;
		    padding-left: 10px;
		    position: relative;
		}
		#branchHeadPic,#branchId{
			width: 285px;
		    height: 36px;
		    border: 1px solid #dddddd;	
		}
		.basicInfo input,.basicInfo select{
			margin: 15px 0;
		}
		.basicInfo{
			width:100%;
		}
		.tdthree{
		 	width: 50px;
		    text-align: center;
		}
		.tdfour{
			width: 50px;
		    margin: 0 14px;
		}
		.file{ 
		    cursor: pointer;
		    z-index: 100;
		    left: 3px;
		    top: -2px;
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
		    font-size: 14px;
		    font-weight: normal;
		    border-radius: 4px;
		}
		.em{
		    width: 180px;
		    color: #999;
		    font-size: 13px;
		    margin: 0;
		    float: right;
		    margin-top: -25px;	
		}
		.checkIn{	
		    padding: 5px 10px;
		    background-color: #5a5d9c;
		    border: none;
		    font-size: 12px;
		    color: #fff;
		}
		.specpic{
		}
		.table_basicInfo{
			height: 324px;
    		overflow-y: scroll;
    		overflow-x: hidden;
    		padding-right: 16px;
		}
		table{
			width:100%;
		}
		input.dele{
			background-color:#f1a43a;
		}
		.basicInfo .addbranch{
			margin-left:11px;
		}
	</style>
  </head>
  
  <body>
  	<form id="basicInfo" name="basicInfo" class="basicInfo"  method="POST" enctype="multipart/form-data">
  	  <div class="table_basicInfo">
		<table class=""> 
		       	<tr>
		       		<td  class="tdone">门店</td>
		       		<td class="tdtwo">
		       			<select id="branchId" name="branchId" disabled>
		       				<c:if test="${theme eq '酒店' || theme eq '公寓'}">	
								<c:forEach items="${branchList}" var="branch">
									<option value="${branch.branchId}" <c:if test="${branchIdTwo eq branch.branchId}">selected = "selected"</c:if>>${branch.branchName}</option>
								</c:forEach>
							</c:if>
							<c:if test="${theme eq '民宿'}">
								<c:forEach items="${branchList}" var="house">
									<option value="${house.houseId}" <c:if test="${branchIdTwo eq branch.branchId}">selected = "selected"</c:if>>${house.houseNo}</option>
								</c:forEach>
							</c:if>
		    			</select>
		    		</td>
		    	</tr>
				<tr>
			   		<td class="tdone">门店头图</td>
			   		<td class="tdtwo"><input type="file" class="file" id="tfile" onchange="addPicture(this,this.value)"><a class="a">上传图片</a><p class="em" id="em">${picture == null ? '未上传':'已存在'}</p></td>
			   		<td class="tdthree"><input type="button" onclick="queryPicture('${picture.pictureUrl}')" class="checkIn specpic" value="查看"/></input></td>
			   		<td class="tdfour"></td>
		   		</tr>
		 		<tr>
			 		<td class="tdone">门店内景</td>
			 		<td><input type="button" onclick="addTd()" class="checkIn addbranch" value="新增"/></td>
		 		</tr>
		 		<tbody id="tdInfo">
		 			<c:forEach items="${pictures}" var="list" varStatus="status">
		 				<tr class="basicInfo">
					 		<td class="tdone"></td>
					 		<td class="tdtwo"><input type="file" class="file" id="file${ status.index + 1}" onchange="addPicture(this,this.value)" disabled><a class="a">上传图片</a><p class="em" id="em${ status.index + 1}">已存在</p></td>
					 		<td class="tdthree"><input type="button" onclick="queryPicture('${list.pictureUrl}')" class="checkIn one" value="查看"/></input></td>
					 		<td class="tdfour"><input type="button" onclick="delTd(this,'${list.pictureId}')"  class="checkIn dele" value="删除"/></td>
				 		</tr>
		 			</c:forEach>
		 		</tbody>
				<input type="hidden" name="roomType" value="${roomType}" id="roomType"/>
				<input type="hidden" name="branchIdTwo" value="${branchIdTwo}" id="branchIdTwo"/>
			</table>
		</div>
		<div class="contractbutton" style="margin-top:7px;">
		 	<div class="pdepartsubmit" onclick="addRoomHeadPicData()">提交</div>
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
  </script>
  <script type="text/javascript">
  	function addTd(){
  		var index = document.getElementById("tdInfo").rows.length + 1;
  		$("#tdInfo").append("<tr class='basicInfo'><td class='tdone'></td><td class='tdtwo'><input type='file' class='file' id='file" + index + "' size='1' onchange='addPicture(this,this.value)'><a class='a'>上传图片</a><p class='em' id='em" + index + "'>未上传</p></td>"+
  				"<td class='tdthree'></td>"+
  				"<td class='tdfour'><input type='button' class='checkIn dele' onclick='delTd(this)' value='删除'/></td></tr>");
  	}
  	function delTd(e, pictureId){
  		$(e).parent().parent().remove();
  		$.ajax({
	         url: base_path + "/delRoomPicsTwo.do",
	         data: {
	        	 pictureId : pictureId,
	        	 branchId : $("#branchIdTwo").val(),
	        	 roomType : $("#roomType").val()
	         },
			 dataType : "json",
			 type : "post",
			 success: function(json) {
				window.location.reload(true);
			 },
			 error: function(json) {}
		});
  	}
  	function queryPicture(src){
  		if (src == '') {
  			showMsg("未上传图片!");
  		} else {
  			JDialog.open("查看图片", base_path + "/showImageScale.do?src=" + src, 450, 300, true);
  		}
  	}
  	function addPicture(e,name){
  		$(e).next().next().text('已选择');
  	}
  </script>
  <script type="text/javascript">
  	function addRoomHeadPicData(){
  		var formData = document.getElementById("tfile").files[0];
  		var text = $("#tfile").next().next().text();
  		if(formData == undefined && text == '未上传'){
  			showMsg("插入图片不可为空");
  			return false;
  		} else if (formData == undefined && text == '已存在') {
  			submitRoomPics();
  		} else if (formData == undefined && text == '已选择') {
  			showMsg("插入图片不可为空");
  			return false;
  		} else {
  			var form = new FormData();
  	        form.append("file", formData);
  	        form.append("branchId", $("#branchIdTwo").val());
  	      	form.append("roomType", $("#roomType").val());
  			$.ajax({
  		         url: base_path + "/addRoomHeadPicDataTwo.do",
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
  						submitRoomPics();
  					}
  				 },
  				 error: function(json) {}
  			});
  		}
  	}
  	function submitRoomPics(){
  		var thisTable = document.getElementById("tdInfo").rows;
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
   	  			form.append("roomType", $("#roomType").val());
     	 		form.append("branchId", $("#branchIdTwo").val());
 				form.append("index",i);
   	  			$.ajax({
   	 		         url: base_path + "/submitRoomPicsTwo.do",
   	 		         data: form,
   	 				 contentType: false,
   	 		         processData: false,
   	 		     	 async : false,
   	 				 dataType : "json",
   	 				 type : "post",
   	 				 success: function(json) {
  	 					 if(i + 1 == thisTable.length){
   							 showMsg("添加成功!");
  	 	 				 	 window.setTimeout("window.JDialog.close();",900);
  	 	 					 window.setTimeout("window.location.reload(true)", 1000);
  	 					 }
   	 				 },
   	 				 error: function(json) {}
   	 			});
   	  		}
        }
  	}
  </script>
</html>
