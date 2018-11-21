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
    
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/secpage.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/common/jquery.mloading.css"/>
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
 		<div class="cols_div" style="margin-left:-19px">
  			<label class="label_name">退房方式:</label>
  			<select id="selectAge" class="select" onchange="canclecheck()">
  				<option value="">--请选择--</option>
			    <option value="0">有责退房</option>
		        <option value="1">免责退房</option>
  			</select>
  		</div>

        <ul id="money_div2" class="cols_div2" style="display:show;margin-left: -39px;font-size: 13px;">		

  			
  		</ul>
  		<div id="money_div" class="cols_div" style="display:none;margin-left:-20px">
  			<label class="label_name">不退金额:</label>
  			<input style="background-color: #f0f0f0;" id="money" value="" readonly/>
  		</div>
  		<div style="font-size: 14px; margin-left:-40px">
  			<label class="label_name">备注:</label>
  			<textarea id="remark"></textarea>
  		</div>
  		<div class="height-div">
			<span style="width:88px;" class="add_conroom query" role="button" onclick="cancleorder()">确定</span>
		</div>
  	</div>
  </body>
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  <script src="<%=request.getContextPath()%>/script/common/jquery.mloading.js"></script>
  <script>
  	function loading(){
		$("body").mLoading({
		    text:"正在退房...",//加载文字，默认值：加载中...
		    //icon:"",//加载图标，默认值：一个小型的base64的gif图片
		    html:false,//设置加载内容是否是html格式，默认值是false
		    content:"",//忽略icon和text的值，直接在加载框中显示此值
		    mask:true//是否显示遮罩效果，默认显示
		});
	}
  	function canclecheck(){
  		var status = $("#selectAge").val();
		if(status == 0){
			$("#money_div").hide();
			$("#money_div2").empty();
		}else if(status == 1){
			$.ajax({
				url: path + "/getHouseOrderDetail.do",
				type: "post",
				datatype: "json",
				data: { 
					orderId: $("#orderId").val()
				},
				success: function (json) {
					var list = '';
					
					var totalMoney = 0.0;
					for(var i = 0;i<json.length;i++ ){
						var data = '<li><input id="'+json[i].projectId+'" type="checkbox" onclick="checkboxOnclick(this)"  name="mm" value="'+json[i].pay+'"/>'+json[i].describe+'：'+json[i].pay+'</li>';
						if(json[i].projectId == '2004'){
							data = '<li><input id="'+json[i].projectId+'" type="checkbox" onclick="checkboxOnclick(this)"  name="mm" value="'+json[i].pay+'"/>'+'房费'+'：'+json[i].pay+'</li>';
							//data = data.replace('[project]',json[i].projectId).replace('[price]',json[i].pay).replace('[name]','房费').replace('[money]',json[i].pay);
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
  	
  	var path = "<%=request.getContextPath() %>";
	function cancleorder() {
		var status = $("#selectAge").val();
		if(status == ''){
			window.parent.parent.showMsg("请选择取消方式!");
			return false;
		}
		/* var r = /^\+?[1-9][0-9]*$/;
		if($("#money").val() != ''){
			if(!r.test($("#money").val())){
				window.parent.parent.showMsg("请输入正整数!");
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
		var objarr = '';
		for(var i = 0;i<obj.length;i++){
			if(obj[i].checked != true){
				objarr += $(obj[i]).attr("id") + ",";
			}
		}
		objarr = objarr.substring(0, objarr.length - 1);
		loading();
		 $.ajax({
			url: path + "/OperationHouseCheckOut.do",
			type: "post",
			datatype: "json",
			data: { 
				housecheckId: $("#orderId").val(),
				outStatus: status,
				remark:$("#remark").val(),
				money:$("#money").val(),
				objarrs:objarr
			},
			success: function (json) {
				if (json.result == 0) {
					showMsg(json.message);
					var leftorderserach = $(window.parent.parent.document).find("#left_order_search");
					if( leftorderserach.length == 1){
						leftorderserach.click();
						window.parent.parent.JDialog.close();
					} else {
						$(window.parent.parent.parent.document.getElementById('menu_904')).click();
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
