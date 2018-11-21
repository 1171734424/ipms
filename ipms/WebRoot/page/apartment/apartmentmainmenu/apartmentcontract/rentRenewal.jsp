<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>房租续约</title>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/rentrenewal/rentrenewal.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>	
	<style type="text/css">
		.div_h{
			padding-left: 10px;
		    width: 100%;
		    line-height: 34px;
    		height: 34px;
		    background: #4A5064;
		    color: #fff;
    		font-size: 16px;
		    border-top-left-radius: 4px;
		    border-top-right-radius: 4px;
		}
		.imooc_log{
		    left: 94%;
		    top: 2px;
		    font-size: 30px;
		    transition: 0.3s all ease-in-out;
		}
		.imooc_log:hover{
		    transform: rotate(90dge);
		    -ms-transform: rotate(90deg);
		    -webkit-transform: rotate(90deg);
		}
		.margintop ul li input.text_search{
			padding: 10px 5px;
		}
	</style>
  </head>
  
  <body>
  	<div style="height:100%;">
		<h3 class="div_h">房租续约</h3>
		<i class="imooc imooc_log" style="color:#fff;" onclick="window.parent.JDialog.close();">&#xe900;</i>
		<div class="div_from">
  		<div class="margintop">
  			<input type="hidden" id="contractId" name="contractId" value="${contractId }"/>
				<ul>
					<li class="high_header_li"><label class="label_name">会员</label><input name="memberName" type="text" id="memberName" readonly="readonly" class="text_search" value="${memberName }"></li>
			      	<li class="high_header_li"><label class="label_name">房间</label><input name="roomId" type="text" id="roomId" readonly="readonly" class="text_search" value="${roomId }"></li>
	     		 	<li class="high_header_li"><label class="label_name">租赁方式</label><input name="typeofpaymentName" type="text" id="typeofpaymentName" readonly="readonly" class="text_search" value="${typeofpaymentName }"></li>
	     		 	<li class="high_header_li"><label class="label_name">单价</label><input name="unitPrice" type="text" id="unitPrice" readonly="readonly" class="text_search" value="${unitPrice }"></li>
			      	<li class="high_header_li"><label class="label_name">下次交租</label><input name="endTime" type="text" id="endTime" readonly="readonly" class="text_search" value="${endTime }"></li>
	      			<li class="high_header_li">
		      			<label class="label_name">续费金额</label>
		      			<input type="text" id="money" readonly="readonly" class="text_search" value="${money }">
		      		</li>
		      	</ul>
	  		</div>
		</div>
		<div class="div_button">
  			<input class="cancel" type="button" value="提交" onclick="submit_rent()"/>  
	    </div>
  	</div>
  </body>
  <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  <script type="text/javascript">
  	function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg, fn);
		}
	}
  	function submit_rent(){
  		var contractId = $("#contractId").val();
  		var rent_typeofpayment = $("#rent_typeofpayment").val();
  		var rent_money = $("#rent_typeofpayment").val();
  		if("" == rent_money){
  			showMsg("请选择交几个月的房租!");
  			return false;
  		}else{
  			$.ajax({
  	  			url: "changeApartmentRent.do",
  	  			dataType: "json",
  	  			type: "post",
  	  			data:{
  	  				'contractId' : contractId,
  	  				'rent_typeofpayment' : rent_typeofpayment
  	  			},
  	  			success: function(json) {
  			        showMsg("续费成功!");
  			      	window.setTimeout("window.parent.document.getElementById('menu_910').click()", 1000);
  		  			window.setTimeout("window.parent.JDialog.close()",1000);
  	  				   
  	  			},
  	  			error:function(json){
  					showMsg("续费失败!");
  					window.setTimeout("window.parent.document.getElementById('menu_910').click()", 1000);
  			  		window.setTimeout("window.parent.JDialog.close()",1000);
  	  			}
  	  		});
  		}
  	}
  	function Money(){
  		var rent_money = $("#rent_typeofpayment").val();
		var money = rent_money * $("#unitPrice").val();
		$("#money").val(money);
  	}
  </script>
</html>
