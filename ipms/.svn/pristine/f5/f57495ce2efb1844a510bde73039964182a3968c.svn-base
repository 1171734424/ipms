<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html class="whitebg">
  <head>
    <title>会员注册</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/mebregister.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
    <link rel="stylesheet"  id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />	
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<style type="text/css">
		.register_one{
			padding: 53px 24px;
		}
		.remark_label{
			width: 53px;
		}
	</style>
  </head>
  <body>
  <div class="register_margin">
     <form action="" method="post" id="omemberadd" name="omemberadd">
	      <section class="register_one" style="padding-top:6px; ">
	        <ul class="clearfix">
			  <!-- <li><label class="label_name">房价</label><input id="amount" name="amount" value="0.00"  style="width: 250px" class="input_ms" type="number"/></li> -->
			  <li style="padding-top:5px;"><label>房价涨幅</label><input id="amount" name="amount" style="width: 298px;height: 35px;vertical-align:top;"value="0.00" class="input_ms"  type="number" ></input></li>
			  <li><label class="remark_label">备注</label><textarea id="remark" name="remark" value="必填项" style="width: 298px;height: 65px;vertical-align:top;" class="input_ms"></textarea></li>
			  <!-- <li style="padding-top: 30px;">
			   	<br><input type="button" class="button_margin ms_re" style="width: 75px;" onclick="resetprice()" value="调整"/></br>
			  </li> -->
			  </ul>
			  <ul>
			  	<li style=" padding-left: 287px;">
			  		<input type="button" class="button_margin ms_re" style="width: 75px;" onclick="resetprice()" value="调整"/>
			  	</li>
			  </ul>
		   </section> 
		 </form>
   </div>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
		var checkid = "<%=request.getParameter("checkid")%>";
		$(function (){
			loadCheckData();
		})
	
		function loadCheckData(){
			$.ajax({
		         url: path + "/loadCheckData.do",
				 type: "post",
				 data : {checkid : checkid},
				 success: function(json) {
				 	//$("#amount").val(json[0]["ROOMPRICE"]);
				 },
				 error: function(json) {}
			});
		}
		
		function resetprice(){
			var moneyReg = /(^(\-)?[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^(\-)?[0-9]\.[0-9]([0-9])?$)/; //校验是金额
			var amount = $("#amount").val();
			var remark = $("#remark").val();
			if($("#amount").val().length == 0){
				showMsg("金额：不能为空!");
			}
		   if(isNaN($("#amount").val())){
				showMsg("金额请填写数字!");
			    return false;	
		     }else if(moneyReg.test($("#amount").val()) == false){
		    		showMsg("金额格式错误!");
		    		$("#amount").val("");
					$("#amount").focus();
			    return false;
		   }
			
			if (remark.length < 1) {
				showMsg("备注字符不能为空！");
				return false;
			}
			if (remark.length > 100) {
				showMsg("备注字符不能大于100个！");
				return false;
			}
			$.ajax({
		         url: path + "/resetprice.do",
				 type: "post",
				 data : {
				 	checkid : checkid,
				 	amount : amount,
				 	remark : remark
				 },
				 success: function(json) {
				 	if(json.result == 1){
				 		showMsg(json.message);
				 		setTimeout("refresh()", 1000);
				 		
				 	}else {
				 		showMsg(json.message);
				 	}
				 },
				 error: function(json) {}
			});
		}
		
		function refresh(){
			$(window.parent.document).find(".tab_two").click();
	 		window.parent.JDialog.close();
	 		
		}
		
		//弹出的提示框
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}	
		
		
	</script>
  </body>
</html>
