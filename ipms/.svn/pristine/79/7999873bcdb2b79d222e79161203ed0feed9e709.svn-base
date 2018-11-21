<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>投资人管理</title>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
  </head>
  <style>
    .pdepartsubmit{
    height: 31px;
    line-height: 31px;
    font-size: 14px;
    text-align: center;
    width: 72px;
    background: #5A5D9D;
    color: #fff;
    color: #fff;
    cursor: pointer;
    border-radius: 2px;
    float: right;
    margin-right: 159px;
    border-radius: 4px !important;
    margin-top: 35px !important;
    }
  </style>
  <body>
 	<div>
 	<input type="hidden" id="INVESTERAPPLYID" value="${INVESTERAPPLYID}">
 	<div style="margin: 36px 10px 10px 36px;">
 	<ul class="">
		<li><label class="label_name" style="line-height: 64px;">处理意见：</label>
			<textarea  style="width: 69%;" id="remark" name="remark" rows="4" class=""  maxlength="90">${remark}</textarea>
		</li>
	</ul>
	</div>
	<c:if test="${remark == '' || remark == null }">
	<div class="contractbutton" style="margin-top:7px;">
		 	<div class="pdepartsubmit" onclick="submitremark()">确认</div>
	 </div>
	 </c:if>
	</div>
	
  </body>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
	var base_path = '<%=request.getContextPath()%>';
	function submitremark(){
		var  id= $("#INVESTERAPPLYID").val() ;
		var remark =  $("#remark").val();
		 $.ajax({
				url: base_path+"/updateinvester.do",
				type: "post",
				dataType: "json",
				data: {
					id : id,
					remark : remark
				},
				success: function(json) {
					if(json.result==1){
						showMsg("操作成功！");
						window.setTimeout("window.parent.location.reload(true)", 1000);
					}else if(json.result==-1){
						showMsg("无权操作！");
						window.setTimeout("window.parent.location.reload(true)", 1000);
					}
				},
				error : function(data){
					}
				}); 
	}
	
	function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg, fn);
		}
	}
	
	</script>
</html>
