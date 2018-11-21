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
    
    <title>民宿退房</title>
    
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
  	 <div class="update_myorder" style="margin-left: -18px;">	
 		<input type="text" id="orderId" value="<%=request.getParameter("orderId")%>" hidden="true"/>
 		<div class="cols_div">
  			<label class="label_name">取消方式</label>
  			<select id="selectAge" class="select" onchange="canclecheck()">
  				<option value="">--请选择--</option>
			    <option value="0">有责取消</option>
		        <option value="1">免责取消</option>
  			</select>
  		</div>
  		<ul id="money_div2" class="cols_div2" style="display:show;margin-left: -39px;font-size: 13px;">		
  			
  		</ul>
  		
  		<div id="money_div" class="cols_div" style="display:none;" >		
  			<label class="label_name">不退金额</label>
  			<input id="money" value="" type="text" readonly="readonly"/>
  		</div>
  		<div style="font-size: 14px;">
  			<label class="label_name">备注</label>
  			<textarea id="remark"></textarea>
  		</div>
  		<div class="height-div">
			<span style="width:88px;margin-left: 31px;" class="add_conroom query" role="button" onclick="cancleorder()">确定</span>
		</div>
  	</div>
  </body>
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  <script>
  var path = "<%=request.getContextPath() %>";
  var jspStatus = '<%=request.getParameter("jspStatus")%>';
  function canclecheck(){
		var status = $("#selectAge").val();
		if(status == 0){
			$("#money_div").hide();
			$("#money_div2").empty();
		}else if(status == 1){
			$.ajax({
				url: path + "/getOrderDetail.do",
				type: "post",
				datatype: "json",
				data: { 
					orderId: $("#orderId").val()
				},
				success: function (json) {
					var list = '';
					
					
					var totalMoney = 0.0;
					for(var i = 0;i<json.length;i++ ){
						var data = '<li><input id="'+'[project]'+'" type="checkbox" onclick="checkboxOnclick(this)"  name="mm" value="'+'[price]'+'"/>'+'[name]'+'：'+'[money]'+'</li>';
						if(json[i].projectId == '1011'){
							data = data.replace('[project]',json[i].projectId).replace('[price]',json[i].pay).replace('[name]','服务费').replace('[money]',json[i].pay);
						}else if(json[i].projectId == '2009'){
							data = data.replace('[project]',json[i].projectId).replace('[price]',json[i].pay).replace('[name]','保洁费').replace('[money]',json[i].pay);
						}else if(json[i].projectId == '2008'){
							data = data.replace('[project]',json[i].projectId).replace('[price]',json[i].pay).replace('[name]','押金').replace('[money]',json[i].pay);
						}else if(json[i].projectId == '2004'){
							data = data.replace('[project]',json[i].projectId).replace('[price]',json[i].pay).replace('[name]','房费').replace('[money]',json[i].pay);
						}
						list +=data;
						//totalMoney +=Number(json[i].pay);
					}
					if(json.length > 0){
						list += '<li style="color:red;">勾选后的名目将不退款</li>';
					}
					$("#money_div2").html(list);
		           $("#money").val(totalMoney);
				},
				error: function(json) {
					showMsg("操作失败！");
				}
			});
			
			
			$("#money_div").show();
		}
	}
  	function checkboxOnclick(e){
  		if (e.checked == true){  
  			var mon = $("#money").val();
  			mon = Number(mon) + Number(e.value);
  			$("#money").val(doubleToPrice(mon));
  		}else{  
  			var mon = $("#money").val();
  			mon = Number(mon) - Number(e.value);
  			$("#money").val(doubleToPrice(mon));
  		} 
  	}
  	
  	function doubleToPrice(price){
		var thousand =  Math.floor(price*1000)/1000;
		
		var hundred =Math.floor(price*100)/100;
		
		if(thousand > hundred){
			var result =Math.ceil(price*100)/100;
			return result;
		}else{
			return hundred;
		}
	}
  	
	function cancleorder() {
		var status = $("#selectAge").val();
		if(status == ''){
			window.parent.parent.showMsg("请选择取消方式!");
			return false;
		}
		var r = /^\+?[1-9][0-9]*$/;
	/* 	if($("#money").val() != ''){
			if(!r.test($("#money").val())){
				window.parent.parent.showMsg("金额请为正整数!");
				return false;
			}
		} */
		if($("#remark").val() != ''){
			if($("#remark").val().length > 20){
				window.parent.parent.showMsg("不能大于20个字数!");
				return false;
			}
		}
		var obj=document.getElementsByName('mm');
		var objarr = [];
		for(var i = 0;i<obj.length;i++){
			if(obj[i].checked != true){
				
				objarr.push($(obj[i]).attr("id")); 
			}
		}
		
		$.ajax({
			url: path + "/cancleorderInHouse.do",
			type: "post",
			datatype: "json",
			data: { 
				orderId: $("#orderId").val(),
				status: status,
				remark:$("#remark").val(),
				money:$("#money").val(),
				objarr:objarr
			},
			traditional: true,
			success: function (json) {
				if (json.result == 0) {
					var leftorderserach = $(window.parent.parent.document).find("#left_order_search");
					console.log(leftorderserach)
					if( leftorderserach.length == 1){
						leftorderserach.click();
						window.parent.parent.JDialog.close();
					}
					else{
						if(jspStatus == 'null'){
							$(window.parent.parent.document).find("#contentFrame_houseone").click();
				 		}else{
				 			window.parent.parent.location.reload();
				 		}
						window.parent.parent.JDialog.close();
					}
				} else if (json.result == 1) {
					window.parent.parent.showMsg(json.message);
					window.setTimeout("window.parent.JDialog.close()", 1800);
					return false;
				} else if (json.result == 2) {
					window.parent.parent.showMsg(json.message);
					window.setTimeout("window.parent.JDialog.close()", 1800);
					return false;
				}
			},
			error: function(json) {
				showMsg("操作失败！");
			}
		})
	}
  </script>
</html>
