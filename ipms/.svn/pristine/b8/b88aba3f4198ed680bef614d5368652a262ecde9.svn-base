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
    
    <title>添加门店图片</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=request.getContextPath()%>/script/crm/manage/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/crm/manage/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/IMGUP.css" rel="stylesheet" type="text/css" media="all" />
	
	<style type="text/css">
	td.tdone{
	width:18%;
	text-align:center;
	vertical-align: middle;
    border: 0 none;
    padding: 2px;
    white-space: nowrap;
	}
	td.tdtwo{
	width:82%;
	text-align:left;
	}
	
	
	#branchHeadPic,#branchId{
	width: 402px;
    height: 36px;
    border-bottom-right-radius: 6px;
    border-bottom-left-radius: 6px;
    border-top-right-radius: 6px;
    border-top-left-radius: 6px;
    border: 1px solid #dddddd;
    background-color: white;
	}
	</style>
  </head>
  
  <body>
 
 <%--  <input name="branchId" value="${branchId}" type="text"/> --%>
  
  <form id="basicInfo" name="basicInfo" class="basicInfo"  method="POST"
						enctype="multipart/form-data">
  <table class="crm-table"> 
  
         	<tr><td  class="tdone">门店</td><td class="tdtwo">
         	<select id="branchId" name="branchId" disabled>
         	<c:if test="${theme ne '民宿'}">
         	<c:forEach items="${branchList}" var="branch">
         	<option value="${branch.branchId}" <c:if test="${branchIdTrue eq branch.branchId}">selected = "selected"</c:if>>${branch.branchName}</option>
         	
         	</c:forEach>
         	</c:if>
         	<c:if test="${theme eq '民宿'}">
         	<c:forEach items="${branchList}" var="house">
         	<option value="${house.houseId}" <c:if test="${branchIdTrue eq branch.branchId}">selected = "selected"</c:if>>${house.houseNo}</option>
         	</c:forEach>
         	</c:if>
         	</select></td></tr>
  
     	<tr><td  class="tdone">房型图片</td><td class="tdtwo"><input   id="branchHeadPic" name="branchHeadPic" type="file" placeholder="请选择图片！" name="roomTypePic"></input></td></tr>
   		<tr><td  class="tdone">房型内景</td><td class="tdtwo"><input id="div_imgfile" type="button" value="选择图片"></input></td></tr>
   		<input type="hidden" name="roomType" value="${roomType}" id="roomType"/>
   <!--图片预览容器-->
	<tr>
	
	<td colspan="2">
	<div id="div_imglook" >
		<div style="clear: both; position:relative;margin-left:10px" ></div>
	</div>
	</td>
	</tr>
 </table>
	<div class="contractbutton" style="margin-top:7px;">
	 <div class="pdepartsubmit" onclick="submitRoomType()">提交</div>
	 <div class="pdepartcancle" onclick="window.parent.JDialog.close()">取消</div>
	 </div>
	</form>
	<script src="<%=request.getContextPath()%>/script/addRoomTypePicture.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
  <script type="text/javascript">
  var base_path = '<%=request.getContextPath()%>';
  var IMG_AJAXPATH = base_path+"/submitPics.do";//异步传输服务端位置
  var branchRank = '${branchRank}';
  </script>
</html>
