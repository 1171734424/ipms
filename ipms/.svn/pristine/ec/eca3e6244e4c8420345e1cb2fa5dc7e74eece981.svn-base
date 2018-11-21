<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>退房</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
  </head>
  <style>
  	#multicheckout{
	  	left: 465px;
	  	display: none;
  	}
  	.newdatail_six ul li:nth-child(7){
  		position:relative !important;
  		width: auto;
    	top: auto;
    	z-index: 0;
  	}
  	#projectid{
  	    margin-left: 5px;
  	}
  	#remark{
  		height: 45%;
  	}
  	.line{
  		height:1px;
  		width:100%;
  		border:1px solid #ccc;
  	}
  	.newdatail_six ul li:nth-child(8) label{
		float: left;
  	}
  	.tips{
	  	line-height: 5;
	    padding-left: 105px;
	    color: red;
	    display: none;
  	}
  </style>
  <body>
   <!--入账开始 -->
		<section class="detail_six newdatail_six">
			<div class="high_header">
				<!--  <i class="imooc detail_imooc" style="color:#3B3E40;"></i>	-->
					<div class="margintop">
						<form id="bill_date">
							<ul class="clearfix">
						      <li><label class="label_name">房单号：</label><input name="checkId" type="text"  id="checkId" class="text_edit" readonly></li>
						      <li><label class="label_name">日租：</label><input id="roomprice" name="roomprice" type="text" class="text_number" readonly></li>
						      <li><label class="label_name">账面金额：</label><input id="accountfee" name="accountfee" type="text" class="text_number" readonly></li>
<%--						      <li><label class="label_name">平账金额：</label><input id="totalfee" name="totalfee" type="text" class="text_number" readonly></li>--%>
						      <hr style="height:1px;border:0px;background-color:#D5D5D5;color:#D5D5D5;width:90%;"/>
						      <li style="position:relative;">
							      <label class="label_name">支付方式：</label>
									<select name="projectid" id="projectid" class="text_search">
										<option value="">--请选支付方式--</option>
										<option value="2001">现金支出</option>
										<option value="2002">现金收取</option>
										<option value="2003">银行卡</option>
									</select>
						      <!-- 
							      <input id="project" name="project" type="text" value="" class="text_edit margin_text" onclick="onbill()">
							      <div id="ontime" class="ontime fadeIn"></div> -->
						      </li>
						      <li><label class="label_name">金额：</label><input id="amount" name="amount" type="number" min=0 value="" class="text_number" ></li>
						      <li><label class="label_name">备注：</label> <textarea id="remark" name="remark" onchange="autosaveRemark()" ></textarea></li>
						      <li onclick="checkout(1)">
						      <span role="button" class="button confirm" >结算</span>
						      </li>
			       		   </ul>
			       		   <!-- <input type="hidden" id="projectid" name="projectid"> -->
			       		   <input type="hidden" id="subprice" name="subprice">
			       		   <input type="hidden" id="checktype" name="checktype" value="singlecheckout">
		       		   </form>
	    		 </div>
	    		 <div class="tips">注意：该房单为主关联房，集体结算前需确保点击自动转账!</div>
	    		 <span role="button" id="multicheckout" class="button confirm" onclick="checkout(2)">集体结算</span>
			</div>
		</section>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/apartment/apartmentmainmenu/apartmentcontract/util.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath() %>";
			var checkid = '<%=request.getParameter("checkid")%>';
			$(function(){
				loadCheckData();
				loadpayandcost();
				$("#checkId").val(checkid);
			});
			
			
			function loadCheckData(){
				$.ajax({
			         url: path + "/loadApartmentCheckData.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
						console.log(json)
					 	$("#roomprice").val(json.unitPrice);
					 },
					 error: function(json) {}
				});
			}
			
			function loadpayandcost(){
				$.ajax({
			         url: path + "/loadApartmentPayandCost.do",
					 type: "post",
					 data : {checkid : checkid},
					 success: function(json) {
						 $("#accountfee").val((json["cost"] - json["pay"]).toFixed(2));
						 $("#amount").val(  Math.abs((json["cost"] - json["pay"])).toFixed(2)  );
						 if(json["pay"] == (json["cost"])){
						 	$("#amount,#project,#reamrk").attr("disabled", "disabled");
						 }
						 if(json["pay"] - json["cost"] > 0){
						 	$("#projectid").val("2001");
						 }else {
						 	$("#projectid").val("2002");
						 }
					 	
					 },
					 error: function(json) {}
				});
			}
			
			$(".margin_text").on("click",function(){
				$(".ontime").css("display","block");
			})
			
			function autosaveRemark(){
				var remark = $("#remark").val();
				if(remark){
					$.ajax({
				         url: path + "/autosaveRemark.do",
						 type: "post",
						 data : {
						 	checkid : checkid,
						 	remark : remark
						 },
						 success: function(json) {
						 	if(json.result == 0){
						 		showMsg(json.message);
						 	}
						 },
						 error: function(json) {}
					});
				}
			}
			
			function veritfycheckout(){
				$.ajax({
			         url: path + "/veritfycheckout.do",
					 type: "post",
					 data : { checkid : checkid},
					 success: function(json) {
					 	if(json.result == 1){
					 		$(".tips").show();
					 		$("#multicheckout").show();
					 	}else if(json.result == 0){
					 	
					 	}
					 },
					 error: function(json) {}
				});
			}
			
			function checkout(e){
			  if(e == 2){
					$("#checktype").val("multicheckout");
				}else {
					$("#checktype").val("singlecheckout");
				}
				$.ajax({
			         url: path + "/apartmentCheckOutBill.do",
					 type: "post",
					 data : $("#bill_date").serialize(),
					 success: function(json) {
						 showMsg(json.message);
						 setTimeout("refreshpage()", 2000);
					 },
					 error: function(json) {}
				}); 
			}
			
			function refreshpage(){
			 	//window.parent.parent.parent.JDialog.close();
			 	window.parent.parent.parent.location.reload();
			}
			
			function onbill(){
				document.getElementById("ontime").innerHTML='<iframe src="' + path +'/page/ipmspage/common_addbill/commbill.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
		</script>
  </body>
</html>
