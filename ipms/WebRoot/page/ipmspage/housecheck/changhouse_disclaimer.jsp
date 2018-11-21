<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>民宿换房</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/secpage.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<style>
		.select{
			padding: 8px 5px;
		    width: 180px;
		    margin-left: 10px;
		    font-size: 14px;
	    }
	</style>
  </head>
  
  <body>
  	 <div class="update_myorder">	
 		<input type="text" id="orderId" value="<%=request.getParameter("orderId")%>" hidden="true"/>
 		<div class="cols_div">
  			<label class="label_name">取消方式:</label>
  			<select id="selectAge" class="select" onchange="canclecheck()">
  				<option value="">--请选择--</option>
			    <option value="0">有责换房</option>
		        <option value="1">免责换房</option>
  			</select>
  		</div>
  		<div id="money_div" class="cols_div" style="display:none;">
  			<label class="label_name">差价金额:</label>
  			<input id="money" value="" />
  		</div>
  		<div style="font-size: 14px;">
  			<label class="label_name">备注:</label>
  			<textarea id="remark"></textarea>
  		</div>
  		<div class="height-div">
			<span style="width:88px;" class="add_conroom query" role="button" onclick="changeHouse()">确定</span>
		</div>
  	</div>
  </body>
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  <script>
  	function canclecheck(){
  		var status = $("#selectAge").val();
  		if(status == 0){
  			$("#money_div").show();
  		}else if(status == 1){
  			$("#money_div").hide();
  		}
  		getPriceDifference();
  	}
  	var path = "<%=request.getContextPath() %>";
	function getPriceDifference() {
		var status = $("#selectAge").val();
		var chosehouseid = '<%=request.getParameter("chosehouseid")%>';
		$.ajax({
		     url: path + "/getPriceDifference.do",
			 type: "post",
			 data : {
			 	chosehouseid : chosehouseid,
			 	houseorderid: $("#orderId").val(),
				outStatus: status
			 },
			 success: function(json) {
			 	if(json.result == 1){
			 		$("#money").val("");
			 	}else if(json.result == 0){
			 		$("#money").val(json.data);
			 	}
			 },
			 error: function(json) {}
		});	
	}
	
	function changeHouse(){
		var status = $("#selectAge").val();
		var chosehouseid = '<%=request.getParameter("chosehouseid")%>';
		if(status == ''){
			window.parent.parent.showMsg("请选择取消方式!");
			return false;
		}
		var r = /^\+?[1-9][0-9]*$/;
		if($("#money").val() != ''){
			if(!r.test($("#money").val())){
				//window.parent.parent.showMsg("请输入正整数!");
				//return false;
			}
		}
		if($("#remark").val() != ''){
			if($("#remark").val().length > 20){
				window.parent.parent.showMsg("不能大于20个字数!");
				return false;
			}
		}
		
		$.ajax({
		     url: path + "/changeHouse.do",
			 type: "post",
			 data : {
			 	chosehouseid : chosehouseid,
			 	houseorderid: $("#orderId").val(),
				outStatus: status,
				remark:$("#remark").val(),
				money:$("#money").val()
			 	
			 },
			 success: function(json) {
			 	if(json.result == 1){
			 		showMsg(json.message);
			 	}else if(json.result == 0){
			 		showMsg(json.message);
			 	}
			 	window.setTimeout("window.parent.parent.JDialog.close()", 1800);
			 },
			 error: function(json) {}
		});
  	}
	
  </script>
</html>
