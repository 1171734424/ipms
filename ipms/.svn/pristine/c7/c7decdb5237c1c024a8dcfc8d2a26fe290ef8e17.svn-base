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
	    .new_house {
	    	padding: 12px 50px;
	    }
	    #money_div{
	    	padding: 0px 50px 12px 50px;
	    }
	    .new_remark{
	    	padding:0px 50px 0px 50px;
	    }
	    .height-div{
	    	position: absolute;
		    top: 189px;
		    left: 120px;
	    }
	</style>
  </head>
  
  <body>
  	 <div class="update_myorder">	
 		<input type="text" id="orderId" value="<%=request.getParameter("orderId")%>" hidden="true"/>
 		<div class="cols_div new_house">
  			<label class="label_name">换房方式:</label>
  			<select id="selectAge" class="select" onchange="canclecheck()">
  				<option value="">--请选择--</option>
			    <option value="0">有责换房</option>
		        <option value="1">免责换房</option>
  			</select>
  		</div>
  		<div id="money_div" class="cols_div" style="display:none;">
  			<label class="label_name">待补差价:</label>
  			<input id="money" value="" readonly style="width: 159px;"/>
  		</div>
  		<div style="font-size: 14px;" class="new_remark">
  			<label class="label_name">备注:</label>
  			<textarea id="remark"></textarea>
  		</div>
  	</div>
  	<div class="height-div">
		<span style="width:88px;" class="add_conroom query" role="button" onclick="changehouse()">确定</span>
	</div>
  </body>
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipmsinhouse/workbillroomInHouse/tipInfo-lj.js"></script>
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
	
	function prechangeHouse(){
		var status = $("#selectAge").val();
		var chosehouseid = '<%=request.getParameter("chosehouseid")%>';
		if(status == ''){
			window.parent.parent.showMsg("请选择换房方式!");
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
		     url: path + "/prechangeHouse.do",
			 type: "post",
			 data : {
			 	chosehouseid : chosehouseid,
			 	houseorderid: $("#orderId").val(),
				outStatus: status,
				remark:$("#remark").val(),
				money:$("#money").val()
			 	
			 },
			 success: function(json) {
				 	if(json.result == 2){
				 		showMsg(json.message, confirm, cancel);
				 	}else if(json.result == 0){
				 		showMsg(json.message);
				 	}else if(json.result == 1){
				 		showMsg(json.message);
				 		setTimeout("refresh()", 2000);
				 	}
			 },
			 error: function(json) {}
		});
		
  	}
  	
  	function confirm(){
  		var status = $("#selectAge").val();
  		var chosehouseid = '<%=request.getParameter("chosehouseid")%>';
  		changehouse(chosehouseid, status, 'confirm');
  	}
  	
  	function cancel(){
  		var status = $("#selectAge").val();
  		var chosehouseid = '<%=request.getParameter("chosehouseid")%>';
  		changehouse(chosehouseid, status, 'cancel');
  	}
  	
  	function changehouse(){
  		var status = $("#selectAge").val();
		var chosehouseid = '<%=request.getParameter("chosehouseid")%>';
		if(status == ''){
			window.parent.parent.showMsg("请选择换房方式!");
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
				money:$("#money").val(),
				memberupdate: 'cancel'
			 	
			 },
			 success: function(json) {
			 	if(json.result == 1){
			 		if( status == 0){
			 			showMsg(json.message);
			 		}else{
			 			showMsg(json.message);
			 		}
			 		setTimeout("refresh()", 2000);
			 	}else if(json.result == 0){
			 		showMsg(json.message);
			 	}
			 	setTimeout("refresh()", 2000);
			 },
			 error: function(json) {}
		});
  	}
  	
  	function refresh(){
  		$(window.parent.parent.parent.parent.document).find(".header_toggle_ul .active").click();
  		window.parent.parent.parent.JDialog.close();
  	}
	
  </script>
</html>
